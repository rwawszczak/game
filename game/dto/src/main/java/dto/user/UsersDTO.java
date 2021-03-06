package dto.user;

import dto.DTO;

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

        public Builder withConversationId(long id){
            dto.conversationId = id;
            return this;
        }
    }
}
