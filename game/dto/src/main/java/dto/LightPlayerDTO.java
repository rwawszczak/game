package dto;

import java.io.Serializable;

public class LightPlayerDTO extends DTO implements Serializable {
    private String name;
    private long id;

    public LightPlayerDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
