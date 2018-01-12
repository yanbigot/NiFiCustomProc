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

import com.socgen.drgh.processors.Processors.mapper.proc.MappingProcessor;
import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.List;

/**
 * {
 "id": "TestF",
 "listMapping": [
 {
 "type": "SubMappingElement",
 "subMapper": {
 "id": "Test",
 "listMapping": [
 {
 "type": "MappingElementSimple",
 "mappingElementType": "SIMPLE_MAPPING",
 "source": "$.category",
 "target": "test.categorie"
 },
 {
 "type": "MappingElementSimple",
 "mappingElementType": "SIMPLE_MAPPING",
 "source": "$.author",
 "target": "test.auteur"
 },
 {
 "type": "MappingElementRules",
 "sources": {
 "author": "aut",
 "category": "cate"
 },
 "rules": "if(1\\u003d\\u003d1) result\\u003d\\u0027je test\\u0027 else result\\u003d\\u0027c est pas normal\\u0027 ","
 "mappingElementType": "FUNCTION_MAPPING",
 "target": "test.rulesResult"
 }
 ]
 },
 "mappingElementType": "SUB_MAPPING",
 "source": "$.store.book[*]",
 "target": "magasin.livre"
 }
 ]
 };

 */



public class MapperProcessorTest {

    private TestRunner testRunner;
    private String mappingScript;
    private String data;

    @Before
    public void init() {
        testRunner = TestRunners.newTestRunner(MappingProcessor.class);
        mappingScript = "{\n" +
                "  \"id\": \"TestF\",\n" +
                "  \"listMapping\": [\n" +
                "    {\n" +
                "      \"type\": \"SubMappingElement\",\n" +
                "      \"subMapper\": {\n" +
                "        \"id\": \"Test\",\n" +
                "        \"listMapping\": [\n" +
                "          {\n" +
                "            \"type\": \"MappingElementSimple\",\n" +
                "            \"mappingElementType\": \"SIMPLE_MAPPING\",\n" +
                "            \"source\": \"$.category\",\n" +
                "            \"target\": \"test.categorie\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"MappingElementSimple\",\n" +
                "            \"mappingElementType\": \"SIMPLE_MAPPING\",\n" +
                "            \"source\": \"$.author\",\n" +
                "            \"target\": \"test.auteur\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"MappingElementRules\",\n" +
                "            \"sources\": {\n" +
                "              \"author\": \"aut\",\n" +
                "              \"category\": \"cate\"\n" +
                "            },\n" +
                "            \"rules\": \"if(1\\u003d\\u003d1) result\\u003d\\u0027je test\\u0027 else result\\u003d\\u0027c est pas normal\\u0027 \",\n" +
                "            \"mappingElementType\": \"FUNCTION_MAPPING\",\n" +
                "            \"target\": \"test.rulesResult\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"mappingElementType\": \"SUB_MAPPING\",\n" +
                "      \"source\": \"$.store.book[*]\",\n" +
                "      \"target\": \"magasin.livre\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        //"{\"id\":\"TestF\",\"listMapping\":[{\"subMapper\":{\"id\":\"Test\",\"listMapping\":[{\"mappingElementType\":\"SIMPLE_MAPPING\",\"source\":\"$.category\",\"target\":\"test.categorie\"},{\"mappingElementType\":\"SIMPLE_MAPPING\",\"source\":\"$.author\",\"target\":\"test.auteur\"},{\"sources\":{\"author\":\"aut\",\"category\":\"cate\"},\"rules\":\"if(1\\u003d\\u003d1) result\\u003d\\u0027je test\\u0027 else result\\u003d\\u0027c est pas normal\\u0027 \",\"mappingElementType\":\"FUNCTION_MAPPING\",\"target\":\"test.rulesResult\"}]},\"mappingElementType\":\"SUB_MAPPING\",\"source\":\"$.store.book[*]\",\"target\":\"magasin.livre\"}]}\n" ;
        data = "{\r\n" +
                "    \"store\": {\r\n" +
                "        \"book\": [\r\n" +
                "            {\r\n" +
                "                \"category\": \"reference\",\r\n" +
                "                \"author\": \"Nigel Rees\",\r\n" +
                "                \"title\": \"Sayings of the Century\",\r\n" +
                "                \"price\": 8.95\r\n" +
                "            },\r\n" +
                "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"Evelyn Waugh\",\r\n" +
                "                \"title\": \"Sword of Honour\",\r\n" +
                "                \"price\": 12.99\r\n" +
                "            },\r\n" +
                "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"Herman Melville\",\r\n" +
                "                \"title\": \"Moby Dick\",\r\n" +
                "                \"isbn\": \"0-553-21311-3\",\r\n" +
                "                \"price\": 8.99\r\n" +
                "            },\r\n" +
                "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" +
                "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" + "            {\r\n" +
                "                \"category\": \"fiction\",\r\n" +
                "                \"author\": \"J. R. R. Tolkien\",\r\n" +
                "                \"title\": \"The Lord of the Rings\",\r\n" +
                "                \"isbn\": \"0-395-19395-8\",\r\n" +
                "                \"price\": 22.99\r\n" +
                "            }\r\n" +
                "        ],\r\n" +
                "        \"bicycle\": {\r\n" +
                "            \"color\": \"red\",\r\n" +
                "            \"price\": 19.95\r\n" +
                "        }\r\n" +
                "    },\r\n" +
                "    \"expensive\": 10\r\n" +
                "}";
    }

    @Test
    public void testStringTooBig() {
        testRunner.setProperty(MappingProcessor.PROP_MAPPING_COLLECTION, mappingScript);
        testRunner.setProperty(MappingProcessor.PROP_BUFFER_SIZE, "100000000000 B");
        byte[] testData = data.getBytes();//"this string is too long".getBytes();
        testRunner.enqueue(testData);
        testRunner.run();

        // testRunner.assertAllFlowFilesTransferred(REL_FAILURE);

        testRunner.setProperty(MappingProcessor.PROP_TO_UPPERCASE, "true");
        testRunner.enqueue(testData);
        testRunner.run();
        //testRunner.assertAllFlowFilesTransferred(REL_FAILURE);
    }

    @Test
    public void testUppercase() throws Exception {
        testRunner.setProperty(MappingProcessor.PROP_BUFFER_SIZE, "100000000000 B");
        testRunner.setProperty(MappingProcessor.PROP_MAPPING_COLLECTION, mappingScript);

        byte[] testData = data.getBytes();
        testRunner.enqueue(testData);
        testRunner.run();

        testRunner.assertAllFlowFilesTransferred(MappingProcessor.REL_SUCCESS);
        List<MockFlowFile> ff = testRunner.getFlowFilesForRelationship(MappingProcessor.REL_SUCCESS);
        //Assert.assertNotNull(ff);
        //Assert.assertEquals("wrong number of messages", 1, ff.size());
        //ff.get(0).assertContentEquals(testData);


        testRunner.clearTransferState();
        testRunner.setProperty(MappingProcessor.PROP_BUFFER_SIZE, "100000000000000 B");
        testRunner.enqueue(testData);
        testRunner.run();
        //     testRunner.assertAllFlowFilesTransferred(REL_FAILURE);

    }
}
