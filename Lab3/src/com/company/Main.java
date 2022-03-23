package com.company;

import com.company.app.SorterZerosAndOnes;
import com.company.app.exceptions.StringCharactersException;
import com.company.app.exceptions.StringHasCharException;
import com.company.fileReader.ReaderFromTXTFile;
import com.company.fileWriter.WriterToTXTFile;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    //1. Обращение к потоку вывода на консоль
    //2. Обращение к потоку вывода в указанный файл
    //3. Обращение к потоку ввода из указанного файла
    //Указание пути к файлу «журнала» из файла
    //Способ реализации событий: Явная реализация события
    public static void main(String[] args) {
        System.out.println("Please, enter a path to file: ");

        //Считывание пути до файла
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();

        //Обращение к потоку ввода из указанного файла
        ReceiverReadFile receiverFile = new ReceiverReadFile();
        ReaderFromTXTFile readerFile = new ReaderFromTXTFile(receiverFile);
        String data = readerFile.readData(fileName);

        String outputFileName = HandlerString.getOutputFileName(data);
        String sequence = HandlerString.getSequence(data);

        SorterZerosAndOnes sorter = new SorterZerosAndOnes();

        ArrayList<Integer> list;
        try {
            list = sorter.processParameters(sequence);
        } catch (StringCharactersException | StringHasCharException e) {
            System.out.println(e);
            return;
        }

        String dataOut = "";
        for (Object o : list) {
            dataOut += o + " ";
        }

        //Обращение к потоку вывода на консоль
        ReceiverWriteToConsole receiverWriteToConsole = new ReceiverWriteToConsole();
        ConsoleWriter consoleWriter = new ConsoleWriter(outputFileName, receiverWriteToConsole);
        consoleWriter.outPrint(dataOut);

        //Обращение к потоку вывода в указанный файл
        ReceiverWriteToFile receiverWriteToFile = new ReceiverWriteToFile();
        WriterToTXTFile writer = new WriterToTXTFile(outputFileName, receiverWriteToFile);
        writer.writeToFile(dataOut);
    }
}
