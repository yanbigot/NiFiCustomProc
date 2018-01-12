    package com.socgen.drgh.processors.Processors;

import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.expression.AttributeExpression;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.*;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CustomPostionnalToJson extends AbstractProcessor{
    public static final PropertyDescriptor OUTPUT_FORMAT = new PropertyDescriptor
            .Builder().name("OUTPUT_FORMAT")
            .description("Either Json or XML")
            .required(true)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();
    public static final PropertyDescriptor INFORMATION = new PropertyDescriptor
            .Builder().name("Information")
            .description("The block name shoud be <BLOCKNAME>_<BLOCKNUMER>")
            .required(false)
            .build();
    public static final Relationship SUCCESS = new Relationship.Builder()
            .name("SUCCESS")
            .description("Confidence: only success !!! ")
            .build();

    private List<PropertyDescriptor> descriptors;

    private Set<Relationship> relationships;

    @Override
    protected void init(final ProcessorInitializationContext context) {
        final List<PropertyDescriptor> descriptors = new ArrayList<PropertyDescriptor>();
        descriptors.add(OUTPUT_FORMAT);
        this.descriptors = Collections.unmodifiableList(descriptors);

        final Set<Relationship> relationships = new HashSet<Relationship>();
        relationships.add(SUCCESS);
        this.relationships = Collections.unmodifiableSet(relationships);
    }

    @Override
    protected PropertyDescriptor getSupportedDynamicPropertyDescriptor(final String propertyDescriptorName) {
        return new PropertyDescriptor.Builder()
                .name(propertyDescriptorName)
                .required(false)
                .addValidator(StandardValidators.createAttributeExpressionLanguageValidator(AttributeExpression.ResultType.STRING, true))
                .addValidator(StandardValidators.ATTRIBUTE_KEY_PROPERTY_NAME_VALIDATOR)
                .expressionLanguageSupported(true)
                .dynamic(true)
                .build();
    }

    @Override
    public Set<Relationship> getRelationships() {
        return this.relationships;
    }

    @Override
    public final List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return descriptors;
    }
//
//    @OnScheduled
//    public void onScheduled(final ProcessContext context) {
//
//    }

    Function<String, Field> createFieldFromString = fieldAndValue -> new Field(
            fieldAndValue.split("=")[0],
            Integer.parseInt(fieldAndValue.split("=")[1])
    );

    @Override
    public void onTrigger(final ProcessContext context, final ProcessSession session) throws ProcessException {
        FlowFile flowFile = session.get();

        Set<PropertyDescriptor> dynamicProps = context.getProperties().keySet().stream().filter(p -> p.isDynamic()).collect(Collectors.toSet());

        List<Block> blocks = new ArrayList<>();
        dynamicProps.stream().forEach(
                prop -> {
                    String strBlock = context.getProperty(prop.getName()).getValue();
                    int blockNumber = Integer.parseInt(prop.getName().split("_")[1]);
                    List<Field> fields= Arrays.stream(strBlock.split(","))
                            .map(createFieldFromString)
                            .collect(Collectors.toList());
                    Block block = new Block(blockNumber, fields.toArray(new Field[fields.size()]));
                    blocks.add(block);
                }
        );

        if ( flowFile == null ) {
            return;
        }
        else {
            List<String> fileAsStringList = new ArrayList<>();

            try ( BufferedReader br = new BufferedReader(new InputStreamReader(session.read(flowFile))) ) {
                fileAsStringList = br
                        .lines()
                        .map(
                                line -> {
                                    int blockNumber = Integer.parseInt(line.substring(0,2));
                                    Block block = Block.registry.get(blockNumber);
                                    String result = processBlock(line, block);

                                    System.out.println("blockId: " + blockNumber);
                                    System.out.println("line: " + line);
                                    System.out.println("matchPattern: " + block.matchPattern());
                                    System.out.println("replacePattern: " + block.replacePattern());
                                    System.out.println(result);

                                    return result;
                                }
                        )
                        .collect(Collectors.toList());

            } catch (IOException e) {
                e.printStackTrace();
            }

            fileAsStringList.forEach(System.out::println);

            String fileAsString = fileAsStringList.stream().collect(Collectors.joining("\n"));

            flowFile = session.putAttribute(flowFile, "result.status", "nice");
            flowFile = session.write(flowFile,
                    outputStream -> outputStream.write(fileAsString.getBytes())
            );
            session.transfer(flowFile, SUCCESS);
        }
    }

    public static String processBlock(String line, Block block){
        Matcher m = block.getCompiledPattern().matcher(line);
        String replacement = block.replacePattern();
        String replaced = m.replaceAll(replacement);
        return toJson(replaced);
    }

    public static String toJson(String line){
        return line
                .replaceAll(Field.START, Field.JSON_START)
                .replaceAll(Field.END, Field.JSON_END)
                .replaceAll(Field.SEP, Field.JSON_SEP);
    }

}
