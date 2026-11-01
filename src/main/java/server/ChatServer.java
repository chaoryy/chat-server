package server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private final int port;
    private final ExecutorService pool = Executors.newCachedThreadPool();
    private final ChatRoom chatRoom = new ChatRoom();

    private ChatServer(int port) {
        this.port = port;
    }

    public static ChatServer bindToPort(int port) {
        return new ChatServer(port);
    }

    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            while (!server.isClosed()) {
                Socket clientSocket = server.accept();
                try {
                    Client client = new Client(clientSocket);
                    pool.submit(new ClientHandler(client, chatRoom));
                } catch (IOException e) {
                    System.out.println("Could not connect socket " + clientSocket + " to server.");
                    try {
                        clientSocket.close();
                    } catch (IOException ioe) {
                        System.out.println("Could not close socket " + clientSocket);
                    }
                }
            }
        } catch (IOException e) {
            System.out.printf("Port %s might be busy. %n", port);
            e.printStackTrace();
        }
    }
}
