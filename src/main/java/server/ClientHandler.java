package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler {
    public ClientHandler(Socket socket) throws IOException {
        Scanner scanner = getReader(socket);
    }

    private Scanner getReader(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return new Scanner(inputStreamReader);
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        return new PrintWriter(outputStream);
    }

    private void sendResponse(String response, Writer writer) throws IOException {
        writer.write(response);
        writer.write(System.lineSeparator());
        writer.flush();
    }

    private boolean isQuitMsg(String msg) {
        return "bye".equalsIgnoreCase(msg);
    }

    private boolean isEmpty(String msg) {
        return msg == null || msg.isBlank();
    }
}
