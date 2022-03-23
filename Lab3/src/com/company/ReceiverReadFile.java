package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReceiverReadFile implements IEv{
    @Override
    public void Handler(String fileName) {
        File file = new File(fileName);
        try{
            if (!file.exists())
                file.createNewFile();
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(writer);
            try{
                bw.write("Произошло обращение к потоку ввода из указанного файла.\n");
            } finally {
                bw.close();
            }
        } catch (IOException e){
            System.out.println(e);
            throw new RuntimeException("Error while writing data to file!");
        }
    }
}
