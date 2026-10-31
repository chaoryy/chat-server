package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{

    public ClientHandler(Socket socket) throws IOException {
        Scanner scanner = getReader(socket);
    }

    @Override
    public void run() {

    }

    private boolean isEmpty(String msg) {
        return msg == null || msg.isBlank();
    }
}
