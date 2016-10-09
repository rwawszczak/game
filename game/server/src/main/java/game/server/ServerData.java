package game.server;

import game.model.domain.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServerData {
    private ServerData() {
    }

    private static Map<Long, Player> players = Collections.synchronizedMap(new HashMap<Long, Player>());

    public static synchronized Map<Long, Player> getPlayers() {
        return players;
    }
}
