package battleengine.gateway;

/**
 * Created by RaV on 09.05.15.
 */
public class BaseCoefficientModel {
    private static final int DAMAGE_REDUCTION_CONSTANT = 100;
    private static final int DEFEND_BONUS = 10;
    private static final int ELEMENTAL_MANA = 11;
    private static final int PRIORITY_MULTIPLIER = 1000;
    private static final double CRITICAL_STRIKE_MULTIPLIER = 1.5;
    private static final double HIT_CHANCE_MULTIPLIER = 0.125;
    private static final double PLAYER_SPEED_INITIATIVE_COEFFICIENT = 10;

    public static int ofDamageReduction() {
        return DAMAGE_REDUCTION_CONSTANT;
    }

    public static int ofDefendBonus() {
        return DEFEND_BONUS;
    }

    public static int ofElementalMana() {
        return ELEMENTAL_MANA;
    }

    public int ofPriorityMultiplier() {
        return PRIORITY_MULTIPLIER;
    }

    public double ofCriticalStrikeMultiplier() {
        return CRITICAL_STRIKE_MULTIPLIER;
    }

    public double ofHitChanceMultiplier() {
        return HIT_CHANCE_MULTIPLIER;
    }

    public double ofPlayerSpeedInitiativeCoefficient() {
        return PLAYER_SPEED_INITIATIVE_COEFFICIENT;
    }
}
