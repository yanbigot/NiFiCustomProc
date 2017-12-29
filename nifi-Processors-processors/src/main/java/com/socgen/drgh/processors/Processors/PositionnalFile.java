package com.socgen.drgh.processors.Processors;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;

public class PositionnalFile {


    public static String JSON_START = "{\"";
    public static String JSON_END = "\"}";
    public static String JSON_SEP= "\": \"";

    public static void init(){
        Field blockId = new Field("block", 2);
        Field igg = new Field("igg", 1);
        Field firstName = new Field("firstName", 7);
        Field zCode = new Field("zCode", 1);
        Field reName = new Field("reName", 7);
        Field genre = new Field("genre", 2);
        Field skill = new Field("skill", 11);
        Field skillCode = new Field("skillCode", 2);
        Field city = new Field("city", 5);

        Block block21 = new Block(21, blockId, igg, firstName, zCode);
        Block block31 = new Block(31, blockId, igg, reName, genre);
        Block block40 = new Block(40, blockId, igg, skill, skillCode);
        Block block50 = new Block(50, blockId, igg, city);
        Block block55 = new Block(55, blockId, igg, firstName, zCode, city, skill);

        Block.registry.putIfAbsent(block21.number, block21);
        Block.registry.putIfAbsent(block31.number, block31);
        Block.registry.putIfAbsent(block40.number, block40);
        Block.registry.putIfAbsent(block50.number, block50);
        Block.registry.putIfAbsent(block55.number, block55);
    }

    public static void main(String... args){
        init();

        Arrays.stream(CONTENT.split("\n")).forEach(
                line -> {
                    int blockNumber = Integer.parseInt(line.substring(0,2));
                    Block block = Block.registry.get(blockNumber);
                    String result = processBlock(line, block);

                    System.out.println("blockId: " + blockNumber);
                    System.out.println("line: " + line);
                    System.out.println("matchPattern: " + block.matchPattern());
                    System.out.println("replacePattern: " + block.replacePattern());
                    System.out.println(result);
                }
        );
    }

    public static String processBlock(String line, Block block){
        Matcher m = block.getCompiledPattern().matcher(line);
        String replacement = block.replacePattern();
        String replaced = m.replaceAll(replacement);
        return toJson(replaced);
    }

    public static String toJson(String line){
        return line
                .replaceAll(Field.START, JSON_START)
                .replaceAll(Field.END, JSON_END)
                .replaceAll(Field.SEP, JSON_SEP);
    }

    private static String CONTENT    =
                    "211mark   4\n" +
                    "311bigot  XY\n" +
                    "401astronaute 10\n" +
                    "212saskia 6\n" +
                    "312bigot  XX\n" +
                    "402ninja      08\n" +
                    "502PARIS\n";
}
