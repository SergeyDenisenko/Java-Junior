package hw5.Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private String name;

    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;

    public Client(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Ошибка подключения!");
            close(socket, in, out);
        }
    }

    public void start() throws IOException {
        out.write(name);
        out.newLine();
        out.flush();

        listenForMessage();
        sendMessage();
    }

    public void sendMessage() {
        Scanner scanner = new Scanner(System.in);
        while (socket.isConnected()) {
            try {
                out.write(name + scanner.hasNextLine());
                out.newLine();
                out.flush();
            } catch (IOException e) {
                System.out.println("Ошибка отправки сообщения!");
            }
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromGroup;
                while (socket.isConnected()) {
                    try {
                        messageFromGroup = in.readLine();
                        System.out.println(messageFromGroup);
                    } catch (IOException e) {
                        System.out.println("Ошибка получения сообщений!");
                        close(socket, in, out);
                    }
                }
            }
        }).start();
    }

    public void close(Socket socket, BufferedReader in, BufferedWriter out) {
        System.out.println("Завершение работы.");
        try {
            if (socket != null) socket.close();
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите своё имя: ");
            new Client(new Socket(HOST, PORT), scanner.nextLine()).start();
        } catch (Exception e) {
            System.out.println("Ошибка подключения!");
        }
    }
}
