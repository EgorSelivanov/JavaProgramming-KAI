package com.company.fileReader;

import com.company.HandlerString;
import com.company.IEv;

import java.io.*;

public class ReaderFromTXTFile implements IReaderFromFile {
    private String fileNameOutput;
    private IEv fileReaderEv;

    public ReaderFromTXTFile(IEv fileReader){
        this.fileReaderEv = fileReader;
    }

    private void genEvFileReader(){
        fileReaderEv.Handler(fileNameOutput);
    }

    @Override
    public String readData(String fileNameInput) {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(fileNameInput);
        if (file.exists()){
            try{
                BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));

                try{
                    String fileLines;
                    while ((fileLines = br.readLine())!= null){
                        stringBuilder.append(fileLines);
                        stringBuilder.append("\n");
                    }
                } finally {
                    br.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error while reading!");
            }
            fileNameOutput = HandlerString.getOutputFileName(stringBuilder.toString());
            genEvFileReader();
            return stringBuilder.toString();
        }
        throw new RuntimeException("File not exist!");
    }
}
