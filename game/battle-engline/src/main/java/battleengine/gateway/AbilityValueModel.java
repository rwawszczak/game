package battleengine.gateway;

/**
 * Created by wawszcza on 5/12/2015.
 */
public class AbilityValueModel {
    private static final double HEALING_MULTIPLIER = 0.3;
    private static final double BOOST_ATTACK_MULTIPLIER = 0.3;
    private static final double BLEED_MULTIPLIER = 0.04;
    private static final int BOOST_ATTACK_TURNS = 3;
    private static final int BLEED_TURNS = 3;

    public double ofHealingMultiplier() {
        return HEALING_MULTIPLIER;
    }

    public int ofBoostAttackTurns() {
        return BOOST_ATTACK_TURNS;
    }

    public double ofBoostAttackMultiplier() {
        return BOOST_ATTACK_MULTIPLIER;
    }

    public double ofBleedkMultiplier() {
        return BLEED_MULTIPLIER;
    }

    public int ofBleedTurns() {
        return BLEED_TURNS;
    }
}
