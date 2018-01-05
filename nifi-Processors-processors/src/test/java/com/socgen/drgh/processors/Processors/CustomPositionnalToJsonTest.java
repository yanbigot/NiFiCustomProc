package com.socgen.drgh.processors.Processors;

import org.apache.nifi.stream.io.ByteArrayInputStream;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;

public class CustomPositionnalToJsonTest {

    private TestRunner runner;

    @Before
    public void init() {
        runner = TestRunners.newTestRunner(CustomPostionnalToJson.class);
    }


    @Test
    public void testResult() {
        String strContent =
                        "211mark   4\n" +
                        "311bigot  XY\n" +
                        "401astronaute 10\n" +
                        "212saskia 6\n" +
                        "312bigot  XX\n" +
                        "402ninja      08\n" +
                        "502PARIS\n" +
                        "6021201201212024";

        InputStream content = new ByteArrayInputStream(strContent.getBytes());

        runner.setValidateExpressionUsage(false);

        runner.setProperty(CustomPostionnalToJson.OUTPUT_FORMAT, "json");
        runner.setProperty("block_21","blockid=2,igg=1,firstName=7,zcode=1");
        runner.setProperty("block_31","blockid=2,igg=1,name=7,gender=2");
        runner.setProperty("block_40","blockid=2,igg=1,skill=11,skillcode=2");
        runner.setProperty("block_50","blockid=2,igg=1,city=5");
        runner.setProperty("block_60","blockid=2,igg=1,b=8,t=3,p=2");

        // Add the content to the runner
        runner.enqueue(content);

        // Run the enqueued content, it also takes an int = number of contents queued
        runner.run(1);

        // All results were processed with out failure
        runner.assertQueueEmpty();
    }
}
