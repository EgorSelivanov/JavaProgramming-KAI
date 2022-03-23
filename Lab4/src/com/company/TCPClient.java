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
    //public static final int CLIENT_COUNT = 3;
    private String message = null;
    private String fileName = null;

    public TCPClient(String message, String fileName){
        this.message = message;
        this.fileName = fileName;
    }
    public void run(){
        String response = "";
        try{
            Socket socket = new Socket(
                    Properties.getProperty(Properties.CLIENT_HOST),
                    Integer.parseInt(Properties.getProperty(Properties.CLIENT_PORT)));

            OutputStream out = socket.getOutputStream();
            OutputStreamWriter streamWriter = new OutputStreamWriter(out);
            PrintWriter pWriter = new PrintWriter(streamWriter);

            String messageCopy = message;
            messageCopy = messageCopy.replace(" ", "");
            if (!messageCopy.contains("=")){
                System.out.println("Ошибка в выражении!");
                return;
            }

            int index = 0;
            char current;

            while(true){
                current = messageCopy.charAt(index);
                if (Character.isDigit(current) || current == '.') {
                    index++;
                    continue;
                }
                else {
                    String send = messageCopy.substring(0, index + 1);
                    index = -1;
                    messageCopy = messageCopy.replace(send, "");
                    pWriter.println(send);
                    pWriter.flush();

                    if (current == '=')
                        break;

                    Thread.sleep(TIME_SEND_SLEEP);
                }
                index++;
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
        } catch (IOException | InterruptedException e) {
            System.err.println("Исключение: " + e.toString());
        }

        System.out.println("Клиент получил значение выражения: " + message +
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

        System.out.println("Введите выражение: ");
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();

        TCPClient ja = new TCPClient(message, fileName);
        Thread th = new Thread(ja);
        th.start();

    }
}
