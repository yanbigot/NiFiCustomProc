package com.socgen.drgh.processors.Processors;

import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.components.PropertyDescriptor;
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
import java.util.stream.Collectors;

public class CustomPostionnalToJson extends AbstractProcessor{
    public static final PropertyDescriptor FLATTEN = new PropertyDescriptor
            .Builder().name("FLATTEN")
            .description("Example Property")
            .required(false)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();
    public static final PropertyDescriptor OUTPUT_PATH = new PropertyDescriptor
            .Builder().name("OUTPUT_PATH")
            .description("Absolute path with output directory & filename")
            .required(false)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .defaultValue("D:\\APPS\\nifi-1.4.0\\ouput\\output.json")
            .build();
    public static final PropertyDescriptor SCRIPT = new PropertyDescriptor
            .Builder().name("SCRIPT")
            .description("Script content")
            .expressionLanguageSupported(true)
            .required(false)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();
    public static final PropertyDescriptor SCRIPT_ENGINE = new PropertyDescriptor
            .Builder().name("SCRIPT_ENGINE")
            .description("Script engine which will execute the code")
            .expressionLanguageSupported(true)
            .allowableValues("groovy", "nashorn")
            .required(false)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();
    public static final Relationship SUCCESS = new Relationship.Builder()
            .name("SUCCESS")
            .description("Example relationship")
            .build();

    private List<PropertyDescriptor> descriptors;

    private Set<Relationship> relationships;

    @Override
    protected void init(final ProcessorInitializationContext context) {
        final List<PropertyDescriptor> descriptors = new ArrayList<PropertyDescriptor>();
        descriptors.add(FLATTEN);
        descriptors.add(OUTPUT_PATH);
        descriptors.add(SCRIPT);
        descriptors.add(SCRIPT_ENGINE);
        this.descriptors = Collections.unmodifiableList(descriptors);

        final Set<Relationship> relationships = new HashSet<Relationship>();
        relationships.add(SUCCESS);
        this.relationships = Collections.unmodifiableSet(relationships);
    }

    @Override
    public Set<Relationship> getRelationships() {
        return this.relationships;
    }

    @Override
    public final List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return descriptors;
    }

    @OnScheduled
    public void onScheduled(final ProcessContext context) {

    }

    @Override
    public void onTrigger(final ProcessContext context, final ProcessSession session) throws ProcessException {
        FlowFile flowFile = session.get();
        if ( flowFile == null ) {
            return;
        }
        else {
            String fileName = "c://lines.txt";
            List<String> list = new ArrayList<>();

            try ( BufferedReader br = new BufferedReader(new InputStreamReader(session.read(flowFile))) ) {

                //br returns as stream and convert it into a List
                list = br
                        .lines()
//                        .map(line -> )
                        .collect(Collectors.toList());

            } catch (IOException e) {
                e.printStackTrace();
            }

            list.forEach(System.out::println);

            String csvFile = flowFile.getAttribute("absolute.path") + flowFile.getAttribute("filename");



            flowFile = session.putAttribute(flowFile, "result.status", "nice");
            flowFile = session.write(flowFile,
                    outputStream -> outputStream.write(" ".getBytes())
            );
            session.transfer(flowFile, SUCCESS);
        }
    }

}
