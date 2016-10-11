package game.server;

import game.model.domain.Player;

import java.util.*;

public class ServerData {
    private ServerData() {
    }

    private static Map<Long, Player> players = Collections.synchronizedMap(new HashMap<Long, Player>());
    private static List<ServerThread> threads = Collections.synchronizedList(new ArrayList<ServerThread>());

    public static synchronized Map<Long, Player> getPlayers() {
        return players;
    }
    public static List<ServerThread> getThreads() {
        return threads;
    }
}
