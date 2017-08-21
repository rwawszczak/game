package dto.user;

import dto.DTO;

public class CharacterDTO extends DTO {
    private long characterId;
    private long playerId;

    private String name;
    private int hp;
    private int mana;

    private int currentHp;
    private int currentMana;

    private float hpPart = 1f;
    private float manaPart = 1f;

    private CharacterDTO(long characterId, long playerId, String name) {
        this.characterId = characterId;
        this.playerId = playerId;
        this.name = name;
    }

    public long getCharacterId() {
        return characterId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMana() {
        return mana;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public float getHpPart() {
        return hpPart;
    }

    public float getManaPart() {
        return manaPart;
    }

    public static class Builder {
        private CharacterDTO dto;

        public Builder(long characterId, long playerId, String name) {
            this.dto = new CharacterDTO(characterId, playerId, name);
        }

        public CharacterDTO build() {
            return dto;
        }

        public Builder withHp(int hp) {
            dto.hp = hp;
            return this;
        }

        public Builder withMana(int mana) {
            dto.mana = mana;
            return this;
        }

        public Builder withCurrentHp(int hp) {
            dto.currentHp = hp;
            return this;
        }

        public Builder withCurrentMana(int mana) {
            dto.currentMana = mana;
            return this;
        }

        public Builder withHpPart(float hpPercentage) {
            dto.hpPart = hpPercentage;
            return this;
        }

        public Builder withManaPart(float manaPercentage) {
            dto.manaPart = manaPercentage;
            return this;
        }

        public Builder withConversationId(long id) {
            dto.conversationId = id;
            return this;
        }
    }
}
