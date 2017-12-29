package com.socgen.drgh.processors.Processors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Tester {

    private static Pattern pattern;
    private static String file    =
            "211mark   4\n" +
                    "311bigot  XY\n" +
                    "401astronaute 10\n" +
                    "212saskia 6\n" +
                    "312bigot  XX\n" +
                    "402ninja      08\n" +
                    "502PARIS\n";
    private static String BLOCKID = "BLOCKID";
    private static String IGG = "IGG";
    private static String FIRSTNAME = "FIRSTNAME";
    private static String ZCODE = "ZCODE";
    private static String NAME = "NAME";
    private static String GENRE = "GENRE";
    private static String SKILLCODE = "SKILLCODE";
    private static String CITY = "CITY";
    private static String SKILL = "SKILL";

    private static void t(){
        String line = "_IGG,1!_FIRSTNAME,mark   !_NAME,!_GENRE,!_SKILL,!_SKILLCODE,!_CITY,!_ZCODE,4!";
        String pattern = "(_[a-zA-Z0-9]+\\s?,[a-zA-Z0-9]?+\\s?!)";
    }

    public static String buildFieldPattern(String name, int lenght){
        //(?<BLOCKID50>[0-9]{2})
        return "(?<"+name+">.{"+lenght+"}";
    }

    public static void testScript(){
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("groovy");
        try {
            Object result =  scriptEngine.eval("if(1==1){42} else {'BAD script engine'}");
            System.out.println(result);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public static final String EXAMPLE_TEST = "x\ny\ne\n";

    public static void beach() {
        Pattern pattern = Pattern.compile("(?<myX>^x$)|(?<myY>^y$)", Pattern.MULTILINE);
        // in case you would like to ignore case sensitivity,
        // you could use this statement:
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(EXAMPLE_TEST);
        // check all occurance
        while (matcher.find()) {
            System.out.print("Start index: " + matcher.start());
            System.out.print(" End index: " + matcher.end() + " ");
            System.out.println("Group: "+ matcher.group());
        }
        // now create a new pattern and matcher to replace whitespace with tabs
//        Pattern replace = Pattern.compile("\\s+");
//        Matcher matcher2 = replace.matcher(EXAMPLE_TEST);
//        System.out.println(matcher2.replaceAll("\t"));
    }
    public static void main(String[] args){
        beach();
        //withoutBackReferences();
        //withBackReferences();
    }

    private static void withoutBackReferences() {

        String block21 =
                "^(" +
                        "(?<BLOCKID21>[0-9]{2})" +
                        "(?<IGG21>.{1})" +
                        "(?<FIRSTNAME>.{7})" +
                        "(?<ZCODE>.{1})" +
                        ")$";
        String block31 =
                "^(" +
                        "(?<BLOCKID31>[0-9]{2})" +
                        "(?<IGG31>.{1})" +
                        "(?<NAME>.{7})" +
                        "(?<GENRE>.{2})" +
                        ")$";
        String block40 =
                "^(" +
                        "(?<BLOCKID40>[0-9]{2})" +
                        "(?<IGG40>.{1})" +
                        "(?<SKILL>.{11})" +
                        "(?<SKILLCODE>.{2})" +
                        ")$";
        String block50 =
                "^(" +
                        "(?<BLOCKID50>[0-9]{2})" +
                        "(?<IGG50>.{1})" +
                        "(?<CITY>.{5})" +
                        ")$";

        String allBlocks =
                "(("+block21
                        + ")|(" + block31
                        + ")|(" + block40
                        + ")|(" + block50 + "))";

        System.out.println("allBlocks => " + allBlocks);
        String[] lines = file.split("\n");
        pattern = Pattern.compile(allBlocks);

        Arrays.stream(file.split("\n")).forEach(
                line -> {
                    Matcher m = pattern.matcher(line);
                    String replaced = m
                            .replaceAll(
                                    buildRawReplacementPattern(BLOCKID+"21")
                                            + buildRawReplacementPattern(BLOCKID+"31")
                                            + buildRawReplacementPattern(BLOCKID+"40")
                                            + buildRawReplacementPattern(BLOCKID+"50")
                                            + buildRawReplacementPattern(IGG+"21")
                                            + buildRawReplacementPattern(IGG+"31")
                                            + buildRawReplacementPattern(IGG+"40")
                                            + buildRawReplacementPattern(IGG+"50")
                                            + buildRawReplacementPattern(FIRSTNAME)
                                            + buildRawReplacementPattern(NAME)
                                            + buildRawReplacementPattern(GENRE)
                                            + buildRawReplacementPattern(SKILL)
                                            + buildRawReplacementPattern(SKILLCODE)
                                            + buildRawReplacementPattern(CITY)
                                            + buildRawReplacementPattern(ZCODE)
                            );
                    System.out.println("replaced => " + replaced);
                    String removedEmptyValues = replaced.replaceAll(emptyValue(), "")
                            ;
                    System.out.println("removedEmptyValues => " + removedEmptyValues);
                }
        );
    }

    private static void withBackReferences() {
         String block21 =
            "^" +
            "(?<BLOCKID>[0-9]{2})" +
            "(?<IGG>.{1})" +
            "(?<FIRSTNAME>.{7})" +
            "(?<ZCODE>.{1})" +
            "$";
        String block31 =
            "^" +
            "(\\k<BLOCKID>)" +
            "(\\k<IGG>)" +
            "(?<NAME>.{7})" +
            "(?<GENRE>.{2})" +
            "$";
        String block40 =
            "^" +
            "(\\k<BLOCKID>)" +
            "(\\k<IGG>)" +
            "(?<SKILL>.{11})" +
            "(?<SKILLCODE>.{2})" +
            "$";
        String block50 =
            "^" +
            "(\\k<BLOCKID>)" +
            "(\\k<IGG>)" +
            "(?<CITY>.{5})" +
            "$";

        String allBlocks = //"(?m)" +
                    block21
                + "|" + block31
                + "|" + block40
                + "|" + block50

                ;

        System.out.println("allBlocks => " + allBlocks);
        String[] lines = file.split("\n");
        pattern = Pattern.compile(allBlocks, Pattern.MULTILINE);

        Arrays.stream(file.split("\n")).forEach(
                line -> {
                    Matcher m = pattern.matcher(line);
                    String replaced = m
                            .replaceAll(
                                    buildRawReplacementPattern(BLOCKID)
                                            + buildRawReplacementPattern(IGG)
                                            + buildRawReplacementPattern(FIRSTNAME)
                                            + buildRawReplacementPattern(NAME)
                                            + buildRawReplacementPattern(GENRE)
                                            + buildRawReplacementPattern(SKILL)
                                            + buildRawReplacementPattern(SKILLCODE)
                                            + buildRawReplacementPattern(CITY)
                                            + buildRawReplacementPattern(ZCODE)
                            );
                    System.out.println("replaced => " + replaced);
                    String removedEmptyValues = replaced.replaceAll(emptyValue(), "")
                            ;
                    System.out.println("removedEmptyValues => " + removedEmptyValues);
                }
        );
    }

    public static String START = "<";
    public static String END = ">";
    public static String SEP = ";";
    public static String buildReplacementPattern(String fieldName){
        return "\\{ \""+fieldName+"\": \"${"+fieldName+"}\" }";
    }
    public static String buildRawReplacementPattern(String fieldName){
        return START+fieldName+SEP+"${"+fieldName+"}"+END;
    }

    public static String emptyValue(){
        String emptyValue = START+ "[a-zA-Z0-9]+\\s*"+ SEP +END;
        //System.out.println("emptyValue: "+emptyValue);
        //(@start[a-zA-Z0-9]+\s*@value@end)
        return emptyValue;
    }

    public static void parseFromHeader(){


        String[] lines = file.split("\n");

        Arrays.stream(file.split("\n"))
                .forEach(
                line -> {

                });
    }

    public static String blockHeader =
            "(?<BLOCKID>[0-9]{2})" +
                    "(?<IGG>.{1})";
    public static String block21 ="^(" +
            blockHeader +
            "(?<FIRSTNAME>.{7})" +
            "(?<ZCODE>.{1})" +
            ")$";
    public static String block31 ="^(" +
            blockHeader +
            "(?<NAME>.{7})" +
            "(?<GENRE>.{2})" +
            ")$";
    public static String block40 ="^(" +
            blockHeader +
            "(?<SKILL>.{11})" +
            "(?<SKILLCODE>.{2})" +
            ")$";
    public static String block50 ="^(" +
            blockHeader +
            "(?<CITY>.{5})" +
            ")$";
    public static Pattern block21Pattern = Pattern.compile(block21);
    public static Pattern block31Pattern = Pattern.compile(block31);
    public static Pattern block40Pattern = Pattern.compile(block40);
    public static Pattern block50Pattern = Pattern.compile(block50);


    public static String json(String line){
        int blockNumber = Integer.parseInt(line.substring(0,2));
        Block block = Block.registry.get(blockNumber);
        return  processBlock(line, block);
    }

    public static String processBlock(String line, Block block){
        Matcher m = block.getCompiledPattern().matcher(line);

        String replacement = block.replacePattern();

        String replaced = "";
        while(m.find()){
            replaced += m.replaceAll(replacement);
        }

        return replaced;
    }

    public static String processBlock(String line, Pattern blockPattern, String... fieldNames){
        Matcher m = blockPattern.matcher(line);

        String replacement =
                Arrays.stream(fieldNames)
                        .map(name -> buildRawReplacementPattern(name))
                        .collect(Collectors.joining(",", "{", "}"));

        String replaced = "";
        while(m.find()){
            replaced += m.replaceAll(replacement);
        }

        return replaced;
    }
}
