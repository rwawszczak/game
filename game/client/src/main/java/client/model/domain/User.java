package client.model.domain;

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

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDetails getDetails() {
        return details;
    }

    public void setDetails(UserDetails details) {
        this.details = details;
    }
}
