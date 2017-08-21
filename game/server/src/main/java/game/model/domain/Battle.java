package game.model.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Battle {
    private long id;
    private Map<User, Status> users = new HashMap<>();
    private List<Character> characters = new ArrayList<>();
    private long timeCreated;

    public Battle(long id) {
        this.id = id;
        timeCreated = System.currentTimeMillis();
    }

    public Map<User, Status> getUsers() { //TODO: dont give direct access to users
        return users;
    }

    public List<Character> getCharacters() {  //TODO: dont give direct access to characters
        return characters;
    }

    public long getId() {
        return id;
    }

    public enum Status {
        PENDING, ACCEPTED, DECLINED, ONGOING, ENDED
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public long statusCount(Status status) {
        return users.values().stream().filter(s -> s == status).count();
    }

    public boolean allAccepted() {
        return statusCount(Status.ACCEPTED) == users.size();
    }
}
