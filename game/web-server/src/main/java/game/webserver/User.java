package game.webserver;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {
    @Id
    private String uid;
    private String email;
    private String userName;

    public User() {
        uid = UUID.randomUUID().toString();
    }

    public User(String uid, String email, String userName) {
        this.uid = uid;
        this.email = email;
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
