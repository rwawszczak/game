package game.model.domain;

public class User {
    private long id;
    private String name;
    private UserDetails details;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(long id, String name, UserDetails details) {
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

    public UserDetails getDetails() {
        return details;
    }
}
