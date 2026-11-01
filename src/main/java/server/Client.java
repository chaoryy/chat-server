package server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private volatile String name;
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
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new Scanner(inputStreamReader);
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        return new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
    }

    public String readLine() {
        return reader.nextLine().strip();
    }

    public void send(String msg) {
        printWriter.println(msg);
    }

    private boolean isQuitMsg(String msg) {
        return "bye".equalsIgnoreCase(msg);
    }

    public void close() {
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
