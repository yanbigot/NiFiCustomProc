/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.socgen.drgh.processors.Processors;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.*;
import org.apache.nifi.annotation.behavior.ReadsAttribute;
import org.apache.nifi.annotation.behavior.ReadsAttributes;
import org.apache.nifi.annotation.behavior.WritesAttribute;
import org.apache.nifi.annotation.behavior.WritesAttributes;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.SeeAlso;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;

import java.io.*;
import java.util.*;


@Tags({"example"})
@CapabilityDescription("Provide a description")
@SeeAlso({})
@ReadsAttributes({@ReadsAttribute(attribute="", description="")})
@WritesAttributes({@WritesAttribute(attribute="", description="")})
public class CustomCSVToJson extends AbstractProcessor {

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

            String csvFile = flowFile.getAttribute("absolute.path") + flowFile.getAttribute("filename");

            String result = csvAsJsonString(csvFile);

            flowFile = session.putAttribute(flowFile, "result.status", "nice");
            flowFile = session.write(flowFile,
                    outputStream -> outputStream.write(result.getBytes())
            );
            session.transfer(flowFile, SUCCESS);
        }
    }

    private String csvAsJsonString(String csvFile){
        String result = "";
        try (InputStream in = new FileInputStream(csvFile);) {
        CSV csv = new CSV(true, ',', in);
        List<String> fieldNames = null;
        if ( csv.hasNext() ) fieldNames = new ArrayList<>(csv.next());
        List<Map<String,String>> list = new ArrayList<>();
        while (csv.hasNext()) {
            List<String> x = csv.next();
            Map<String,String> obj = new LinkedHashMap<>();
            for (int i = 0 ; i < fieldNames.size() ; i++) {
                obj.put(fieldNames.get(i), x.get(i));
            }
            list.add(obj);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        result = mapper.writeValueAsString(list);

    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    } catch (JsonGenerationException e) {
        e.printStackTrace();
    } catch (JsonMappingException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
            return result;
        }
    }

}
