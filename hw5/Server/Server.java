package hw5.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8080;
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        System.out.println("Сервер запущен!");
    }

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Подключен новый клиент!");
                Thread thread = new Thread(new ClientManager(socket));
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Аварийнеая остановка сервера!");
            stop();
        }
    }

    public void stop() {
        System.out.println("Завершение работы.");
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new Server(new ServerSocket(PORT)).start();
        } catch (IOException e) {
            System.out.println("Ошибка запуска сервера!");
        }
    }
}
