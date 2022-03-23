package com.company.fileWriter;

import com.company.IEv;

import java.io.*;

public class WriterToTXTFile implements IWriterToFile{
    private String fileName;
    private IEv fileWriterEv;

    public WriterToTXTFile(String fileName, IEv fileWriter) {
        this.fileName = fileName;
        this.fileWriterEv = fileWriter;
    }

    private void genEvFileWriter(){
        fileWriterEv.Handler(fileName);
    }

    @Override
    public void writeToFile(String data){
        File file = new File(fileName);
        try{
            if (!file.exists())
                file.createNewFile();
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(writer);
            try{
                genEvFileWriter();
                bw.write("Результат: ");
                bw.write(data + "\n");
            } finally {
                bw.close();
            }
        } catch (IOException e){
            System.out.println(e);
            throw new RuntimeException("Error while writing data to file!");
        }
    }
}
