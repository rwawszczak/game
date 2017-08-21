package dto.battle;

import dto.DTO;
import dto.user.CharacterDTO;
import dto.user.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleStateDTO extends DTO {
    private long battleId;
    private List<UserDTO> users;
    private Map<Long, CharacterDTO> characters;

    private BattleStateDTO(List<UserDTO> users, Map<Long, CharacterDTO> characters) {
        this.users = new ArrayList<>(users);
        this.characters = new HashMap<>(characters);
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public long getBattleId() {
        return battleId;
    }

    public static class Builder {
        private BattleStateDTO dto;

        public Builder(List<UserDTO> users, Map<Long, CharacterDTO> characters) {
            dto = new BattleStateDTO(users, characters);
            for (CharacterDTO characterDTO : characters.values()) {
                System.out.println(characterDTO.getCharacterId() + " " + characterDTO.getPlayerId() + " " + characterDTO.getName() + " " + characterDTO.getHp() + " " + characterDTO.getMana());
            }
        }

        public Builder withBattleId(long battleId) {
            dto.battleId = battleId;
            return this;
        }

        public Builder withConversationId(long conversationId) {
            dto.conversationId = conversationId;
            return this;
        }

        public BattleStateDTO build() {
            return dto;
        }
    }
}
