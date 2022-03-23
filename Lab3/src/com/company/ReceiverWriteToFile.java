package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReceiverWriteToFile implements IEv{
    @Override
    public void Handler(String fileName) {
        File file = new File(fileName);
        try{
            if (!file.exists())
                file.createNewFile();
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(writer);
            try{
                bw.write("Обращение к потоку вывода в указанный файл.\n");
            } finally {
                bw.close();
            }
        } catch (IOException e){
            System.out.println(e);
            throw new RuntimeException("Error while writing data to file!");
        }
    }
}
