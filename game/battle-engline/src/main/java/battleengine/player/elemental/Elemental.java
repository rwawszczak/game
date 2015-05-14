package battleengine.player.elemental;

import battleengine.coefficient.CoefficientGateway;
import battleengine.player.BattleEntity;

/**
 * Created by RaV on 10.05.15.
 */
public class Elemental implements BattleEntity {
    private String name;
    private int mana = CoefficientGateway.getBase().ofElementalMana();
    private Elements type;

    public Elemental(Elements type) {
        this(String.format("%s Elemental", type), type);
    }

    public Elemental(String name, Elements type) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getInitiative() {
        return type.getBaseInitiative();
    }

    private int getCurrentMana() {
        return mana;
    }

    private void decreaseMana(int amount) {
        mana -= amount;
        if (mana < 0) mana = 0;
    }

    private void increaseMana(int amount) {
        mana += amount;
    }
}
