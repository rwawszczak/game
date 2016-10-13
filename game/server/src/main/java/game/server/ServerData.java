package game.server;

import game.model.domain.User;

import java.util.*;

public class ServerData {
    private ServerData() {
    }

    private static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());
    private static List<ServerThread> threads = Collections.synchronizedList(new ArrayList<ServerThread>());

    public static synchronized Map<Long, User> getUsers() {
        return users;
    }
    public static List<ServerThread> getThreads() {
        return threads;
    }
}
