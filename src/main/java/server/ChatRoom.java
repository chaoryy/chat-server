package server;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoom {
    private final static ConcurrentHashMap <String, Client> clients = new ConcurrentHashMap<>();
    public static void join(Client client) {
        String name;
        do {
            name = NameGenerator.generateName();
        } while (clients.putIfAbsent(name, client) != null);
        client.setName(name);
    }

    public static void leave(Client client) {
        clients.remove(client.getName(), client);
    }

    public static void broadcast(Client sender, String msg) {
        clients.forEach((name, client) -> {
            if (!sender.getName().equals(name)){
                client.send(sender.getName() + ": " + msg);
            }
        });
    }

    public static List<String> getClientsName() {
        return new ArrayList<>(clients.keySet());
    }

    public static boolean rename(Client client, String newName) {
        String oldName = client.getName();
        if (newName == null ||
                !newName.matches("[a-zA-Z0-9_]+")) {
            System.out.println("Invalid username format!");
            return false;
        }
        if (newName.equals(client.getName())) {
            System.out.println("You already have this name!");
            return false;
        }
        if (clients.putIfAbsent(newName, client) != null) {
            System.out.println("This username is already used!!");
            return false;
        }
        clients.remove(oldName, client);
        client.setName(newName);
        return true;
    }

    public static Client findByName(String searchedName) {
        return clients.get(searchedName);
    }
}
