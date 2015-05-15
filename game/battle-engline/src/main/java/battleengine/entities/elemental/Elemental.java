package battleengine.entities.elemental;

import battleengine.entities.Element;
import battleengine.gateway.CoefficientGateway;
import battleengine.entities.BattleEntity;

/**
 * Created by RaV on 10.05.15.
 */
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

    @Override
    public int getInitiative() {
        return CoefficientGateway.getInitiative().ofElement(type);
    }

    private int getCurrentMana() {
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
