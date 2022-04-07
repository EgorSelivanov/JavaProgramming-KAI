package com.company;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient implements Runnable {
    private Socket socket;
    private File file;

    public TCPClient(File file, String host, int port) {
        try {
            this.file = file;
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                OutputStream outputStream = socket.getOutputStream();
                OutputStreamWriter outStreamReader = new OutputStreamWriter(outputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outStreamReader);

                FileWriter fileWriter = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fileWriter);

                //Получение от сервера трех двумерных массивов
                System.out.println("There are 3 arrays: ");
                System.out.println();
                System.out.println("Array of integer elements: ");
                String strArrayInt = bufferedReader.readLine();
                System.out.println(strArrayInt);
                bw.write(strArrayInt + "\n");
                System.out.println("Array of double elements: ");
                String strArrayDouble = bufferedReader.readLine();
                System.out.println(strArrayDouble);
                bw.write(strArrayDouble + "\n");
                System.out.println("Array of string elements: ");
                String strArrayString = bufferedReader.readLine();
                System.out.println(strArrayString);
                bw.write(strArrayString + "\n");
                System.out.println();

                //Отправка на сервер номера массива и двух индексов
                Scanner scanner = new Scanner(System.in);
                System.out.println("Input number of array (1 - int, 2 - double, 3 - string) and two indexes:");
                bufferedWriter.write(scanner.nextLine());
                bufferedWriter.newLine();
                bufferedWriter.flush();

                String answer = bufferedReader.readLine();
                if (!answer.isEmpty()) {
                    System.out.println(answer);
                } else {
                    //Получение от сервера информации об обнаружении защищенного индекса
                    if (Boolean.parseBoolean(bufferedReader.readLine())) {
                        //Получение от сервера сообщения об ошибке
                        String errorMessage = bufferedReader.readLine();
                        System.out.println(errorMessage);
                        bw.write(errorMessage + "\n");
                    } else {
                        //Получение от сервера элемента указанного массива на указанной позиции
                        String elemPosition = bufferedReader.readLine();
                        System.out.println("The element in this position is " + elemPosition);
                        bw.write(elemPosition + "\n");

                        //Отправка на сервер значения для замены
                        Scanner secondScanner = new Scanner(System.in);
                        System.out.println("Input value to replace: ");
                        bufferedWriter.write(secondScanner.nextLine());
                        bufferedWriter.newLine();
                        bufferedWriter.flush();

                        //Получение от сервера массива после замены
                        String arrAfterReplacement = bufferedReader.readLine();
                        System.out.println("Array after replacement: ");
                        System.out.println(arrAfterReplacement);
                        bw.write(arrAfterReplacement + "\n");
                    }
                }

                bufferedReader.close();
                bufferedWriter.close();
                bw.close();
            } catch (UnknownHostException e) {
                System.err.println("Exception: " + e.toString());
            } catch (IOException e) {
                System.err.println("Exception: " + e.toString());
            }
    }

    public static void main(String[] args) {
        //Считывание адреса и номера порта из командной строки
        if (args.length != 3)
        {
            System.out.println("The server address or port or client log file is not set!");
            return;
        }
        String host = "";
        try{
            host = args[0];
        }
        catch (Exception e){
            System.out.println(e.toString());
            return;
        }
        int port = 0;
        try{
            port = Integer.parseInt(args[1]);
        }
        catch (Exception e){
            System.out.println(e.toString());
            return;
        }
        String fileName = "";
        try{
            fileName = args[2];
        }
        catch (Exception e){
            System.out.println(e.toString());
            return;
        }
        File file = new File(fileName);
        try{
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e){
            System.out.println(e);
            return;
        }
        TCPClient ja = new TCPClient(file, host, port);
        Thread th = new Thread(ja);
        th.start();
    }
}