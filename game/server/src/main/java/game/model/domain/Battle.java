package game.model.domain;

import java.util.HashMap;
import java.util.Map;

public class Battle {
    private long id;
    private Map<User, Status> users = new HashMap<>();

    public Battle(long id) {
        this.id = id;
    }

    public Map<User, Status> getUsers() {
        return users;
    }

    public long getId() {
        return id;
    }

    public enum Status{
        PENDING, ACCEPTED, DECLINED
    }
}
