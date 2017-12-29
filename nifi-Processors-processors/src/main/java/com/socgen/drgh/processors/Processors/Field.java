package com.socgen.drgh.processors.Processors;

public class Field
{
    public static String START = "<";
    public static String END = ">";
    public static String SEP = ";";

    public static String JSON_START = "{\"";
    public static String JSON_END = "\"}";
    public static String JSON_SEP= "\": \"";

    public String name;
    public int    lenght;
    public int    order;

    public Field(String name, int lenght) {
        this.name = name;
        this.lenght = lenght;
    }

    public String matchPattern(){
        return "(?<"+name+">.{"+lenght+"})";
    }

    public String replacePattern() {
        return START + name + SEP + "${" + name + "}" + END;
    }
}
