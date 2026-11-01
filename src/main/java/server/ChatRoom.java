package server;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoom {
    private final ConcurrentHashMap <String, Client> clients = new ConcurrentHashMap<>();
    public void join(Client client) {
        String name;
        do {
            name = NameGenerator.generateName();
        } while (clients.putIfAbsent(name, client) != null);
        client.setName(name);
    }

    public void leave(Client client) {
        clients.remove(client.getName(), client);
    }

    public void broadcast(Client sender, String msg) {
        for (Client c : clients.values()) {
            if (c != sender) {
                c.send(sender.getName() + ": " + msg );
            }
        }
    }

    public List<String> getClientsName() {
        return new ArrayList<>(clients.keySet());
    }

    public void rename(Client client, String newName) {
        if (newName == null ||
                !newName.matches("[a-zA-Z0-9_]+")) {

            throw new IllegalArgumentException("Invalid username format!");
        }
        if (newName.equals(client.getName())) {
            throw new IllegalArgumentException("You already have this name!");
        }
        if (clients.putIfAbsent(newName, client) != null) {
            throw new IllegalArgumentException("This username is already used!!");
        }
        clients.remove(client.getName(), client);
        client.setName(newName);
    }

    public Client findByName(String searchedName) {
        return clients.get(searchedName);
    }

    public void notifyClients(String msg) {
        for (Client c : clients.values()){
            c.send("---" + msg + "---");
        }
    }
}
