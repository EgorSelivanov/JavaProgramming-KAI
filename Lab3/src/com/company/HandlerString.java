package com.company;

public class HandlerString {
    public static String getOutputFileName(String line){
        int index = line.indexOf("\n");
        return line.substring(index + 1, line.length() - 1);
    }

    public static String getSequence(String line){
        int index = line.indexOf("\n");
        return line.substring(0, index);
    }
}
