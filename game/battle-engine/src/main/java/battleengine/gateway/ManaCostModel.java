package battleengine.gateway;

public class ManaCostModel {
    private static final int BOOST_ATTACK_MANA_COST = 3;
    private static final int ELEMENTAL_ATTACK_MANA_COST = 2;
    private static final int HEAL_MANA_COST = 4;

    public int ofBoostAttack() {
        return BOOST_ATTACK_MANA_COST;
    }

    public int ofElementalAttack() {
        return ELEMENTAL_ATTACK_MANA_COST;
    }

    public int ofHeal() {
        return HEAL_MANA_COST;
    }
}
