package server;

import java.util.NoSuchElementException;

public class ClientHandler implements Runnable {
    private final Client client;
    private final ChatRoom chatRoom;

    public ClientHandler(Client client, ChatRoom chatRoom) {
        this.client = client;
        this.chatRoom = chatRoom;
    }

    @Override
    public void run() {
        chatRoom.join(client);
        System.out.printf("%s connected as %s%n", client.getSocket(), client.getName());
        client.send("You connected as " + client.getName());
        try {
            while (true) {
                String msg = client.readLine();
                if (isEmpty(msg)) continue;
                if (isQuitMsg(msg)) {
                    break;
                }
                chatRoom.broadcast(client, msg);

            }
        } catch (NoSuchElementException e) {
            System.out.printf("Client: %s lost connection%n", client.getName());
        } finally {
            chatRoom.leave(client);
            System.out.printf("%s(%s) left chat%n", client.getName(), client.getSocket());
            chatRoom.notifyClients(client.getName() + " left chat room");
            client.close();
        }
    }

    private boolean isEmpty(String msg) {
        return msg == null || msg.isBlank();
    }

    private boolean isQuitMsg(String msg) {
        return "/bye".equalsIgnoreCase(msg);
    }
}
