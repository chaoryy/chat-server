package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String name;
    private Socket socket;
    private Scanner reader;
    private PrintWriter printWriter;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = getReader(socket);
        this.printWriter = getWriter(socket);
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

    private String readLine() {
        return reader.nextLine().strip();
    }

    public void send(String msg) {
        printWriter.println(msg);
    }

    private void sendResponse(String response) throws IOException {
        printWriter.write(response);
        printWriter.write(System.lineSeparator());
        printWriter.flush();
    }

    private boolean isQuitMsg(String msg) {
        return "bye".equalsIgnoreCase(msg);
    }

    private void close() {
        try {
            printWriter.close();
            reader.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error while closing client. Error description: \n" + e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
