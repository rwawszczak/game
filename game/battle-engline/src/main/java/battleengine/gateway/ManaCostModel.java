package battleengine.gateway;

/**
 * Created by RaV on 09.05.15.
 */
public class ManaCostModel {
    private static final int BOOST_ATTACK_MANA_COST = 3;
    private static final int ELEMENTAL_ATTACK_MANA_COST = 2;
    private static final int HEAL_MANA_COST = 4;

    public static int ofBoostAttackManaCost() {
        return BOOST_ATTACK_MANA_COST;
    }

    public static int ofElementalAttackManaCost() {
        return ELEMENTAL_ATTACK_MANA_COST;
    }

    public static int ofHealManaCost() {
        return HEAL_MANA_COST;
    }
}
