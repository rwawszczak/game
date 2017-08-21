package game.model.domain;

public class Character {
    private long id;
    private long playerId;

    private String name;
    private int hp;
    private int mana;

    private Character() {
    }

    private Character(Character character) {
        this.id = character.id;
        this.playerId = character.playerId;
        this.name = character.name;
        this.hp = character.hp;
        this.mana = character.mana;
    }

    public long getId() {
        return id;
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

    public static class Builder {
        private Character character;

        public Builder() {
            this.character = new Character();
        }

        public Builder(Character character) {
            this.character = new Character(character);
        }

        public Builder withCharacterId(long id) {
            character.id = id;
            return this;
        }

        public Builder withPlayerId(long id) {
            character.playerId = id;
            return this;
        }

        public Builder withName(String name) {
            character.name = name;
            return this;
        }

        public Builder withHp(int hp) {
            character.hp = hp;
            return this;
        }

        public Builder withMana(int mana) {
            character.mana = mana;
            return this;
        }

        public Character build(){
            return character;
        }
    }

}
