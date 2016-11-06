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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
