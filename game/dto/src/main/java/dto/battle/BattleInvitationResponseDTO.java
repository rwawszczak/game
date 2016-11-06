package dto.battle;

import dto.DTO;
import dto.UserDTO;

public class BattleInvitationResponseDTO extends DTO {
    private long battleId;
    private UserDTO user;
    private Response response;

    private BattleInvitationResponseDTO(long battleId) {
        this.battleId = battleId;
    }

    public UserDTO getUser() {
        return user;
    }

    public long getBattleId() {
        return battleId;
    }

    public Response getResponse() {
        return response;
    }

    public static class Builder {
        private BattleInvitationResponseDTO dto;

        public Builder(long battleId) {
            dto = new BattleInvitationResponseDTO(battleId);
        }

        public Builder withUser(UserDTO user) {
            dto.user = user;
            return this;
        }

        public Builder withConversationId(long conversationId) {
            dto.conversationId = conversationId;
            return this;
        }

        public Builder withResponse(Response response) {
            dto.response = response;
            return this;
        }

        public BattleInvitationResponseDTO build() {
            return dto;
        }
    }

    public enum Response {
        ACCEPTED, DECLINED
    }
}
