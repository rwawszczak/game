package game.server;

import game.model.domain.Battle;
import game.model.domain.User;

import java.util.*;

public class ServerData {
    private ServerData() {
    }

    private static long nextBattleId = 1;
    private static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());
    private static List<ServerThread> threads = Collections.synchronizedList(new ArrayList<ServerThread>());
    private static Map<Long, Battle> battles = Collections.synchronizedMap(new HashMap<Long, Battle>());

    public static synchronized Map<Long, User> getUsers() {
        return users;
    }

    public static synchronized List<ServerThread> getThreads() {
        return threads;
    }

    public static synchronized Map<Long, Battle> getBattles() {
        return battles;
    }


    public synchronized static long getNextBattleId(){
        return nextBattleId++;
    }
}
