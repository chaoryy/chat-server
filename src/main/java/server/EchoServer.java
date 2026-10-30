package server;

import server.commands.ResponseProcessor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    private final int port;
    private final ExecutorService pool = Executors.newCachedThreadPool();


    private EchoServer(int port) {
        this.port = port;
    }

    public static EchoServer bindToPort(int port) {
        return new EchoServer(port);
    }

    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            while (!server.isClosed()) {
                Socket clientSocket = server.accept();
                pool.submit(() -> handle(clientSocket));
            }
        } catch (IOException e) {
            System.out.printf("Вероятнее всего порт %s занят. %n", port);
            e.printStackTrace();
        }
    }

    private void handle(Socket socket) {
        System.out.printf("Подключен клиент: %s%n", socket);

        try (
                socket;
                Scanner reader = getReader(socket);
                PrintWriter writer = getWriter(socket);
        ) {
            sendResponse("Привет" + socket, writer);
            while (true) {
                String message = reader.nextLine().strip();
                if (isEmpty(message) || isQuitMsg(message)) {
                    break;
                }
                System.out.printf("Client %s: %s%n", socket, message);
                String messageToClient = responseProcessor.processResponse(message);
                sendResponse(messageToClient.toUpperCase(), writer);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Client dropped connection");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Client [%s] disconnected%n", socket);
    }
}
