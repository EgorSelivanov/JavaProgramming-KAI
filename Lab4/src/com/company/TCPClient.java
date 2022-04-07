package com.company;

import com.company.properties.Properties;

import java.io.*;
import java.net.*;
import java.util.Scanner;

//1. Реализовать приложения клиент и сервер (0 – TCP протокол).
//2. Реализовать в клиенте указание адреса и порта сервера, так: 3 – из файла настроек.
//3. Реализовать указание порта для сервера, так: 2 – из командной строки.
//4. Сообщения, получаемые клиентом с сервера должны записываться в файл
//«Журнала клиента» путь к которому определяется следующим образом: 2 – из командной строки.
//5. Сообщения, получаемые сервером от клиента должны записываться в файл
//«Журнала сервера» путь к которому определяется следующим образом: 3 – из файла настроек.

public class TCPClient implements Runnable {
    private static final int TIME_SEND_SLEEP = 100;
    private String fileName = null;

    public TCPClient(String fileName){
        this.fileName = fileName;
    }
    public void run(){
        String response = "";
        String expression = "";
        try{
            Socket socket = new Socket(
                    Properties.getProperty(Properties.CLIENT_HOST),
                    Integer.parseInt(Properties.getProperty(Properties.CLIENT_PORT)));

            System.out.println("Введите выражение (окончание выражения - пробел): ");

            OutputStream out = socket.getOutputStream();
            OutputStreamWriter streamWriter = new OutputStreamWriter(out);
            PrintWriter pWriter = new PrintWriter(streamWriter);

            while(true){
                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();
                expression += message;

                pWriter.println(message);
                pWriter.flush();

                if (message.contains("="))
                    break;
            }

            InputStream in = socket.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(streamReader);
            response = reader.readLine();

            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(writer);

            try{
                bw.write("Клиент: " + response + "\n");
            } finally {
                bw.close();
            }
            pWriter.close();
            reader.close();
        } catch (IOException e) {
            System.err.println("исключение: " + e.toString());
        }

        System.out.println("Клиент получил значение выражения: " + expression +
                ". Результат: " + response);
    }

    public static void main(String[] args) {
        String fileName = args[0];
        File file = new File(fileName);
        try{
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e){
            System.out.println(e);
            return;
        }

        TCPClient ja = new TCPClient(fileName);
        Thread th = new Thread(ja);
        th.start();

    }
}
