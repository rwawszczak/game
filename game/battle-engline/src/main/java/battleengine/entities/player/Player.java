package battleengine.entities.player;

import battleengine.entities.BattleEntity;
import battleengine.entities.Element;
import battleengine.entities.elemental.Elemental;
import battleengine.entities.elemental.Elementals;
import battleengine.entities.player.components.attributes.Attributes;
import battleengine.entities.player.components.Equipment;
import battleengine.entities.player.components.items.BaseWeapon;
import battleengine.entities.player.components.items.types.WeaponType;
import battleengine.gateway.CoefficientGateway;

/**
 * Created by RaV on 09.05.15.
 */
public class Player implements BattleEntity { //Todo: equip/unequip items
    private String name;
    private int currentHP;
    private int currentMana;
    private Attributes attributes;
    private Equipment equipment;

    private Elementals elementals = new Elementals();

    public Player(String name, Attributes attributes) {
        this(name, attributes, new Equipment());
    }

    @Override
    public int getInitiative() {
        return (int) (attributes.getSpeed()* CoefficientGateway.getBase().ofPlayerSpeedInitiativeCoefficient());
    }

    public Player(String name, Attributes attributes, Equipment equipment) {
        this.name = name;
        this.attributes = attributes;
        this.equipment = equipment;
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

    public void addElemental(Elemental elemental) {
        elementals.add(elemental);
    }

    public Elemental getElemental(int index) {
        return elementals.get(index);
    }

    public Elemental getElemental(String name) {
        return elementals.get(name);
    }

    public double getPhysicalDamageReduction(){
        double base = getAttributes().getDefence() + CoefficientGateway.getBase().ofDamageReduction();
        return 1 - (getAttributes().getDefence() / base);
    }

    public double getElementalDamageReduction(Element type){
        return getReductionFromElementals(type);
    }

    public BaseWeapon getMainWeapon() {
        return equipment.getWeapon(WeaponType.MAINHAND);
    }

    private double getReductionFromElementals(Element type) {
        double effectiveness = 0;
        for(int i = 0; i < elementals.getCount(); i++){
            effectiveness += type.effectiveAgainst(getElemental(i).getType())-1;
        }
        double reduction = effectiveness / elementals.getCount();

        return reduction * CoefficientGateway.getBase().ofElementalDamageNegationCoefficient();
    }
}
