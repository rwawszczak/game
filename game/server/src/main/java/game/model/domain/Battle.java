package game.model.domain;

import java.util.HashMap;
import java.util.Map;

public class Battle {
    private long id;
    private Map<User, Status> users = new HashMap<>();
    private long timeCreated;

    public Battle(long id) {
        this.id = id;
        timeCreated = System.currentTimeMillis();
    }

    public Map<User, Status> getUsers() {
        return users;
    }

    public long getId() {
        return id;
    }

    public enum Status {
        PENDING, ACCEPTED, DECLINED
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public long statusCount(Status status) {
        return users.values().stream().filter(s -> s == status).count();
    }

    public boolean allAccepted(){
        return statusCount(Status.ACCEPTED) == users.size();
    }
}
