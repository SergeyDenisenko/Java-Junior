package hw5.Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    private String name;
    public static ArrayList<ClientManager> clients = new ArrayList<>();

    public ClientManager(Socket socket) {
        try {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            name = in.readLine();
            broadcastMessage("Server: " + name + " присоеденился к чату.");
        } catch (IOException e) {
            close(socket, in, out);
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                broadcastMessage(in.readLine());
            } catch (IOException e) {
                close(socket, in, out);
                break;
            }
        }
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

    public void broadcastMessage(String message) {
        removeClient();
        try {
            for (ClientManager client: clients) {
                if (!client.name.equals(name)) {
                    client.out.write(message);
                    client.out.newLine();
                    client.out.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка отправки сообщений!");
            close(socket, in, out);
        }
    }

    public void removeClient() {
        clients.remove(this);
        broadcastMessage("Server: " + name + " покинул чат.");
    }
}
