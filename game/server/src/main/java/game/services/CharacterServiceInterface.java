package game.services;


import game.model.domain.Character;

public interface CharacterServiceInterface {
    Character load(long id);
    Character create(Character character);
    Character update(Character character);
    Character delete(Character character);
}
