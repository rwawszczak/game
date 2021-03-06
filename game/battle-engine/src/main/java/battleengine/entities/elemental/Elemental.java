package battleengine.entities.elemental;

import battleengine.entities.Element;
import battleengine.gateway.CoefficientGateway;
import battleengine.entities.BattleEntity;

public class Elemental implements BattleEntity {
    private String name;
    private int mana = CoefficientGateway.getBase().ofElementalMana();
    private Element type;

    public Elemental(Element type) {
        this(String.format("%s Elemental", type), type);
    }

    public Elemental(String name, Element type) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Element getType() {
        return type;
    }

    @Override
    public int getInitiative() {
        return CoefficientGateway.getInitiative().ofElement(type.toString());
    }

    public int getCurrentMana() {
        return mana;
    }

    public void decreaseMana(int amount) {
        mana -= amount;
        if (mana < 0) mana = 0;
    }

    public void increaseMana(int amount) {
        mana += amount;
    }
}
