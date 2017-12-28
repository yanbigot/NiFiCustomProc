package com.socgen.drgh.processors.Processors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tester {

    private static Pattern pattern;

    public static void main(String[] args){
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("groovy");
        try {
          Object result =  scriptEngine.eval("if(1==1){42} else {'BAD script engine'}");
          System.out.println(result);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        String file    = "211mark   4\n" +
                "311bigot  XY \n" +
                "401astronaute 10\n" +
                "212saskia 6\n" +
                "312bigot  XX \n" +
                "402ninja      08\n" +
                "502PARIS\n";

        String IGG = "IGG";
        String FIRSTNAME = "FIRSTNAME";
        String ZCODE = "ZCODE";
        String NAME = "NAME";
        String GENRE = "GENRE";
        String SKILLCODE = "SKILLCODE";
        String CITY = "CITY";
        String SKILL = "SKILL";

        String block21 = "^21(?<IGG>.{1})(?<FIRSTNAME>.{7})(?<ZCODE>.{1})";
        String block31 = "^31(\\k<IGG>)(?<NAME>NAME.{7})(?<GENRE>GENRE.{3})";
        String block40 = "^40(\\k<IGG>)(?<SKILL>SKILL.{11})(?<SKILLCODE>SKILLCODE.{2})";
        String block50 = "^50(\\k<IGG>)(?<CITY>CITY.{5})";
        String result = "";

        String blocks21and31 = block21 + "|" + block31;
        String allBlocks = block21 + "|" + block31+ "|" + block40+ "|" + block50;
        String[] lines = file.split("\n");

        pattern = Pattern.compile(allBlocks);

        Arrays.stream(file.split("\n")).forEach(
                line -> {
                    Matcher m = pattern.matcher(line);
                    String json = m
                            .replaceAll(
                                    buildRawReplacementPattern(IGG)
                                            + buildRawReplacementPattern(FIRSTNAME)
                                            + buildRawReplacementPattern(NAME)
                                            + buildRawReplacementPattern(GENRE)
                                            + buildRawReplacementPattern(SKILL)
                                            + buildRawReplacementPattern(SKILLCODE)
                                            + buildRawReplacementPattern(CITY)
                                            + buildRawReplacementPattern(ZCODE)
                            );
                    System.out.println("json => " + json);
                    //json = json.replaceAll(emptyValue(), "")
                            ;
                    System.out.println("json => " + json);
                }
        );
    }

    public static String START = "µ";
    public static String END = "~";
    public static String SEP = "%";
    public static String buildReplacementPattern(String fieldName){
        return "\\{ \""+fieldName+"\": \"${"+fieldName+"}\" }";
    }
    public static String buildRawReplacementPattern(String fieldName){
        return START+fieldName+SEP+"${"+fieldName+"}"+END;
    }

    public static String emptyValue(){
        System.out.println(START+ ".*"+ SEP +END);
        return START+ ".*"+ SEP +END;
    }
}
