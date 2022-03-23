package com.company;

public class ConsoleWriter {
    private String fileName;
    private IEv consoleWrite;

    public ConsoleWriter(String fileName, IEv consoleWrite){
        this.fileName = fileName;
        this.consoleWrite = consoleWrite;
    }

    private void genEvConsoleWrite(){
        consoleWrite.Handler(fileName);
    }

    public void outPrint(String message){
        genEvConsoleWrite();
        System.out.println(message);
    }
}
