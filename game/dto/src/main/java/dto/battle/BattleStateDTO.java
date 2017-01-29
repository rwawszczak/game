package dto.battle;

import dto.DTO;
import dto.UserDTO;

import java.util.List;

public class BattleStateDTO extends DTO {
    private long battleId;
    private List<UserDTO> users;

    private BattleStateDTO(List<UserDTO> users) {
        this.users = users;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public long getBattleId() {
        return battleId;
    }

    public static class Builder{
        private BattleStateDTO dto;
        public Builder(List<UserDTO> users){
            dto = new BattleStateDTO(users);
        }

        public Builder withBattleId(long battleId){
            dto.battleId = battleId;
            return this;
        }

        public Builder withConversationId(long conversationId){
            dto.conversationId = conversationId;
            return this;
        }

        public BattleStateDTO build(){
            return dto;
        }
    }
}
