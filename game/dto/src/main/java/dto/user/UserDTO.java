package dto.user;

import dto.DTO;

import java.io.Serializable;

public class UserDTO extends DTO implements Serializable {
    private String name;
    private long id;
    private UserDetailsDTO details;

    private UserDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public UserDetailsDTO getDetails() {
        return details;
    }

    public static class Builder {
        private UserDTO dto;

        public Builder(long id, String name) {
            dto = new UserDTO(id, name);
        }

        public Builder withDetails(UserDetailsDTO details) {
            dto.details = details;
            return this;
        }

        public UserDTO build() {
            return dto;
        }
    }
}
