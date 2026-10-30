package server;

import java.net.Socket;

public class Client {
    private String name;
    private Socket socket;

    public Client(Socket socket) {
        this.name = NameGenerator.generateName();
        this.socket = socket;
    }
}
