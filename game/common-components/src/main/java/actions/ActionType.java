package actions;

/**
 * Created by wawszcza on 1/15/2015.
 */
public enum ActionType {
    INSTANT(3), AURA(2), NORMAL(1), TRAP(0);

    private static final int INITIATIVE_MULTIPLICATOR = 100;

    private int initiative;

    ActionType(int priority) {
        initiative = priority*INITIATIVE_MULTIPLICATOR;
    }

    public int getInitiative() {
        return initiative;
    }
}
