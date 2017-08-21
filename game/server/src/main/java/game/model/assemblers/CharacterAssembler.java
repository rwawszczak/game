package game.model.assemblers;

import dto.user.CharacterDTO;
import game.model.domain.Character;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterAssembler {
    public Map<Long, CharacterDTO> assembleCharacters(List<Character> characters, Long mainCharacterId) {
        Map<Long, CharacterDTO> characterDTOs = new HashMap<>();
        for (Character character : characters) {
            long id = character.getId();
            if (id == mainCharacterId) {
                characterDTOs.put(id, assembleWithDetails(character));
            } else {
                characterDTOs.put(id, assembleWithoutDetails(character));
            }
        }
        return characterDTOs;
    }

    private CharacterDTO assembleWithoutDetails(Character character) {
        return new CharacterDTO.Builder(character.getId(), character.getPlayerId(), character.getName())
                .build();
    }

    private CharacterDTO assembleWithDetails(Character character) {
        return new CharacterDTO.Builder(character.getId(), character.getPlayerId(), character.getName())
                .withHp(character.getHp())
                .withMana(character.getMana())
                .withCurrentHp(character.getHp())
                .withCurrentMana(character.getMana())
                .build();
    }
}
