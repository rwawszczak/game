package game.model.domain;

public class Player {
    private long id;
    private String name;
    private PlayerDetails details;

    public Player(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Player(long id, String name, PlayerDetails details) {
        this.id = id;
        this.name = name;
        this.details = details;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PlayerDetails getDetails() {
        return details;
    }
}
