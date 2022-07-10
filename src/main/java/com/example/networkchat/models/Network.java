package com.example.networkchat.models;

import com.example.networkchat.controllers.ChatController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8888;

    private final String host;
    private final int port;
    private DataOutputStream out;
    private DataInputStream in;
    private ChatController chatController;


    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Network() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public void connect() {
        try {
            Socket socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Не удалось установить соединение с сервером!");
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка отправки сообщения!");
        }
    }

    public void waitMessage(ChatController chatController) {
        Thread t = new Thread(() -> {
            try {
                while (true) {

                    String message = in.readUTF();
//
                    chatController.addMessage(message);
                    System.out.println("Сервер: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true);
        t.start();
    }


    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

    public DataInputStream getIn() {
        return in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }


}
