package dto;

import java.io.Serializable;

public class PlayerDTO extends DTO implements Serializable {
    private String name;
    private long id;
    private PlayerDetailsDTO details;

    private PlayerDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public PlayerDetailsDTO getDetails() {
        return details;
    }

    public static class Builder {
        private PlayerDTO dto;

        public Builder(long id, String name) {
            dto = new PlayerDTO(id, name);
        }

        public Builder withDetails(PlayerDetailsDTO details) {
            dto.details = details;
            return this;
        }

        public PlayerDTO build() {
            return dto;
        }
    }
}
