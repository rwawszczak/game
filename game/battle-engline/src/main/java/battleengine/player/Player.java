package battleengine.player;

import battleengine.coefficient.CoefficientGateway;
import battleengine.player.elemental.Elemental;
import battleengine.player.elemental.Elementals;

/**
 * Created by RaV on 09.05.15.
 */
public class Player implements BattleEntity {
    private String name;
    private int currentHP;
    private int currentMana;
    private Attributes attributes;

    private Elementals elementals = new Elementals();



    public Player(String name, Attributes attributes) {
        this.name = name;
        this.attributes = attributes;
        this.currentHP = attributes.getMaxHP();
        this.currentMana = attributes.getMaxMana();
    }

    public String getName() {
        return name;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public int getMissingHP(){
        return attributes.getMaxHP()-currentHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void increaseHP(int amount) {
        currentHP += amount;
    }

    public void decreaseHP(int amount) {
        currentHP -= amount;
        if (currentHP < 0) currentHP = 0;
    }

    public void increaseMana(int amount) {
        currentMana += amount;
    }

    public void decreaseMana(int amount) {
        currentMana -= amount;
        if (currentMana < 0) currentMana = 0;
    }

    public void addElementals(Elemental elemental) {
        elementals.add(elemental);
    }

    public Elemental getElemental(int index) {
        return elementals.get(index);
    }

    public Elemental getElemental(String name) {
        return elementals.get(name);
    }

    @Override
    public int getInitiative() {
        return (int) (attributes.getSpeed()* CoefficientGateway.getBase().ofPlayerSpeedInitiativeCoefficient());
    }
}
