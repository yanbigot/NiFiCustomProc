package com.socgen.drgh.processors.Processors;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Block {

    public static final Map<Integer, Block> registry = new HashMap<>();
    public int number;
    public List<Field> fields = new ArrayList<>();

    public Block(int number, Field... fields) {
        this.number = number;
        this.fields = Arrays.asList(fields);
        registry.putIfAbsent(number, this);
    }

    public Pattern getCompiledPattern(){
        return Pattern.compile(matchPattern());
    }

    public String matchPattern(){
        return "^" +
                fields.stream()
                .map(field -> field.matchPattern())
                .collect(Collectors.joining())
                + "$";
    }

    public String replacePattern(){
        return fields.stream()
                .map(field -> field.replacePattern())
                .collect(Collectors.joining());
    }

    public static void main(String[] args){
        test();
    }

    public static void test(){
        Field f = new Field("n", 56);
        Block b = new Block(12, f);
        b.registry.get(12).fields.stream().forEach(ff ->System.out.println(ff.name + " : "+ ff.lenght));
        System.out.println(b.registry.get(12).getCompiledPattern());
    }
}
