package client.model.domain;

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

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerDetails getDetails() {
        return details;
    }

    public void setDetails(PlayerDetails details) {
        this.details = details;
    }
}
