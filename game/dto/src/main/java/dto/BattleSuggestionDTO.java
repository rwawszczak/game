package dto;

import java.util.List;

public class BattleSuggestionDTO extends DTO {
    private List<Long> playerIds;

    private BattleSuggestionDTO(List<Long> playerIds) {
        this.playerIds = playerIds;
    }

    public List<Long> getPlayerIds() {
        return playerIds;
    }

    public static class Builder{
        private BattleSuggestionDTO dto;
        public Builder(List<Long> playerIds){
            dto = new BattleSuggestionDTO(playerIds);
        }

        public Builder withConversationId(long conversationId){
            dto.conversationId = conversationId;
            return this;
        }

        public BattleSuggestionDTO build(){
            return dto;
        }
    }
}
