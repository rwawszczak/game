package battleengine.entities.player.components.attributes;

public class Attributes {
    private int maxHP;
    private int maxMana;
    private int attack;
    private int dexterity;
    private int defence;
    private int intelligence;
    private int speed;
    private int luck;

    public Attributes(int maxHP, int maxMana, int attack, int dexterity, int defence, int intelligence, int speed, int luck) {
        this.maxHP = maxHP;
        this.maxMana = maxMana;
        this.attack = attack;
        this.dexterity = dexterity;
        this.defence = defence;
        this.intelligence = intelligence;
        this.speed = speed;
        this.luck = luck;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getAttack() {
        return attack;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getDefence() {
        return defence;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getSpeed() {
        return speed;
    }

    public int getLuck() {
        return luck;
    }

    public void increaseMaxHP(int amount) {
        maxHP += amount;
    }

    public void decreaseMaxHP(int amount) {
        maxHP -= amount;
        if (maxHP < 0) maxHP = 0;
    }

    public void increaseMaxMana(int amount) {
        maxMana += amount;
    }

    public void decreaseMana(int amount) {
        maxMana -= amount;
        if (maxMana < 0) maxMana = 0;
    }

    public void increaseAttack(int amount) {
        attack += amount;
    }

    public void decreaseAttack(int amount) {
        attack -= amount;
    }

    public void increaseDexterity(int amount) {
        dexterity += amount;
    }

    public void decreaseDexterity(int amount) {
        dexterity -= amount;
    }

    public void increaseDefence(int amount) {
        defence += amount;
    }

    public void decreaseDefence(int amount) {
        defence -= amount;
    }

    public void increaseIntelligence(int amount) {
        intelligence += amount;
    }

    public void decreaseIntelligence(int amount) {
        intelligence -= amount;
    }

    public void increaseSpeed(int amount) {
        speed += amount;
    }

    public void decreaseSpeed(int amount) {
        speed -= amount;
    }

    public void increaseLuck(int amount) {
        luck += amount;
    }

    public void decreaseLuck(int amount) {
        luck -= amount;
    }
}
