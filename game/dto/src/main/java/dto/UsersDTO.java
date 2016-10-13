package dto;

import java.util.List;

public class UsersDTO extends DTO {
    private List<UserDTO> users;

    private UsersDTO(List<UserDTO> users) {
        this.users = users;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public static class Builder {
        private UsersDTO dto;

        public Builder(List<UserDTO> players) {
            dto = new UsersDTO(players);
        }

        public UsersDTO build() {
            return dto;
        }
    }
}
