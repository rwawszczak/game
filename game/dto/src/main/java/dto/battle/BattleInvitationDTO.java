package dto.battle;

import dto.DTO;
import dto.UserDTO;

import java.util.List;

public class BattleInvitationDTO extends DTO {
    private long battleId;
    private List<UserDTO> users;

    private BattleInvitationDTO(List<UserDTO> users) {
        this.users = users;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public static class Builder{
        private BattleInvitationDTO dto;
        public Builder(List<UserDTO> users){
            dto = new BattleInvitationDTO(users);
        }

        public Builder withBattleId(long battleId){
            dto.battleId = battleId;
            return this;
        }

        public Builder withConversationId(long conversationId){
            dto.conversationId = conversationId;
            return this;
        }

        public BattleInvitationDTO build(){
            return dto;
        }
    }
}
