package com.company;

import com.company.properties.Properties;

import java.io.*;
import java.net.*;


//Сервер возвращает клиенту результат выражения (допустимые операции «+», «-»).
//Операнды и операции передаются за раз по одному (например, выражение «3.4+1.6-
//5=» нужно передавать с помощью трёх сообщений: «3.4+», «1.6-» и «5=», где «=» -
//признак конца выражения). В случае не возможности разобрать сервером полученную
//строку или при переполнении, возникшем при вычислении полученного выражения,
//сервер присылает клиенту соответствующее уведомление.

public class TCPServer {
    private ServerSocket servSocket;
    private String fileName;

    public static void main(String[] args) {
        if (args.length != 1)
        {
            System.out.println("Не задан порт сервера!");
            return;
        }

        int port = 0;
        try{
            port = Integer.parseInt(args[0]);
        }
        catch (Exception e){
            System.out.println(e.toString());
            return;
        }

        String fileName = Properties.getProperty(Properties.SERVER_LOG);
        File file = new File(fileName);
        try{
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e){
            System.out.println(e);
            return;
        }

        TCPServer tcpServer = new TCPServer(port, fileName);
        tcpServer.go();
    }

    public TCPServer(int port, String fileName){
        try{
            servSocket = new ServerSocket(port);
        }catch(IOException e){
            System.err.println("Не удаётся открыть сокет для сервера: " + e.toString());
        }
        this.fileName = fileName;
    }
    public void go(){
        class Listener implements Runnable{
            Socket socket;
            public Listener(Socket aSocket){
                socket = aSocket;
            }
            public void run(){
                String response = "";
                String expression = "";
                Boolean sign = null;
                Double intermediate = null;
                Double result = null;
                boolean isError = false;
                try{
                    System.out.println("Слушатель запущен");

                    InputStream in = socket.getInputStream();
                    InputStreamReader streamReader = new InputStreamReader(in);
                    BufferedReader reader = new BufferedReader(streamReader);

                    FileWriter fileWriter = new FileWriter(fileName, true);
                    BufferedWriter bw = new BufferedWriter(fileWriter);

                    while (!expression.contains("=")){
                        expression = reader.readLine();

                        bw.write(expression + "\n");

                        char current;

                        for (int i = 0; i < expression.length(); i++){
                            current = expression.charAt(i);
                            if (Character.isDigit(current) || current == '.')
                                continue;
                            else if (current == '+' || current == '-' || current == '='){
                                String considered = expression.substring(0, i);

                                try {
                                    intermediate = Double.parseDouble(considered);
                                } catch (Exception e){
                                    isError = true;
                                    break;
                                }

                                if (sign != null){
                                    if (sign)
                                        result += intermediate;
                                    else
                                        result -= intermediate;
                                }
                                else result = intermediate;

                                switch (current) {
                                    case '+' -> sign = true;
                                    case '-' -> sign = false;
                                }
                                
                                if (current == '=') {
                                    break;
                                }
                            }
                            else{
                                isError = true;
                                break;
                            }
                        }
                        if (isError) {
                            response = "Ошибка в выражении!\n";
                            break;
                        }
                        response = result.toString();
                    }
                    bw.close();

                    OutputStream out = socket.getOutputStream();
                    OutputStreamWriter writer = new OutputStreamWriter(out);
                    PrintWriter pWriter = new PrintWriter(writer);
                    pWriter.println(response);
                    pWriter.flush();


                }catch(IOException e){
                    System.err.println("Exception: " + e.toString());
                }
            }
        }
        System.out.println("Сервер запущен...");
        while(true){
            try{
                Socket socket = servSocket.accept();
                Listener listener = new Listener(socket);
                Thread thread = new Thread(listener);
                thread.start();
            }catch(IOException e){
                System.err.println("Exception: " + e.toString());
            }
        }
    }
}