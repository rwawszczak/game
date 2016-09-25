package battleengine.entities.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Players {
    private final List<Player> players;

    public Players() {
        players = new ArrayList<Player>();
    }

    public Players(Player... player) {
        players = Arrays.asList(player);
    }

    public Player get(int index){
        if(index<0||index>=players.size()) throw new CantFindPlayerException(index);
        return players.get(index);
    }

    public Player get(String name){
        for(Player p : players){
            if(name.equals(p.getName()))return p;
        }
        throw new CantFindPlayerException(name);
    }

    public void add(Player player) {
        players.add(player);
    }

    public static class CantFindPlayerException extends RuntimeException{
        public CantFindPlayerException(String name) {
            super(String.format("Can't find player with name: \"%s\"",name));
        }
        public CantFindPlayerException(int index) {
            super(String.format("Can't find player with index number: \"%d\"",index));
        }
    }
}
