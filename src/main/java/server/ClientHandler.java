package server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    public ClientHandler(Socket socket) throws IOException {

    }

    @Override
    public void run() {
        System.out.printf("Подключен клиент: %s%n", socket);
        try {

        }
    }

    private boolean isEmpty(String msg) {
        return msg == null || msg.isBlank();
    }
}
