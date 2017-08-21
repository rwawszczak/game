package game.model.assemblers;

import dto.battle.BattleStateDTO;
import dto.user.UserDTO;
import game.model.domain.Battle;
import game.model.domain.Character;
import game.model.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BattleAssembler {
    private static CharacterAssembler characterAssembler = new CharacterAssembler();
    public static Map<Long, BattleStateDTO> toBattleStateDTOs(Battle battle) {
        Map<Long, BattleStateDTO> dtos = new HashMap<>();
        Set<User> users = battle.getUsers().keySet();
        List<UserDTO> userDTOs = UserAssembler.toDTOs(users);
        List<Character> characters = battle.getCharacters();
        for(User user : users){
            dtos.put(user.getId(),
                    new BattleStateDTO.Builder(userDTOs, characterAssembler.assembleCharacters(characters, user.getDetails().getCharacterIds().get(0))) //TODO: Character nr 0 is hardcoded for now
                    .withBattleId(battle.getId())
                    .build()
            );
        }
        return dtos;
    }
}
