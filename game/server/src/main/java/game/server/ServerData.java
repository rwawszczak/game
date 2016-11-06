package game.server;

import game.model.domain.Battle;
import game.model.domain.User;

import java.util.*;

public class ServerData {
    private ServerData() {
    }

    private static long nextBattleId = 1;
    private static final Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());
    private static final List<ServerThread> threads = Collections.synchronizedList(new ArrayList<ServerThread>());
    private static final Map<Long, Battle> battles = Collections.synchronizedMap(new HashMap<Long, Battle>());

    public static synchronized Map<Long, User> getUsers() {
        return users;
    }

    public static synchronized List<ServerThread> getThreads() {
        return threads;
    }

    public static synchronized Map<Long, Battle> getBattles() {
        synchronized (battles) {
            return battles;
        }
    }

    public static void removeBattle(Battle battle) {
        synchronized (battles) {
            battles.remove(battle.getId());
        }
    }


    public synchronized static long getNextBattleId(){
        return nextBattleId++;
    }
}
