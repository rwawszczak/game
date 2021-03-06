package game.webserver.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)

    @Column(name = "name")
    private RoleName name;

    public Role() {

    }

    public Role(RoleName name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
}
