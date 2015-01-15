package actions;

import effects.Effect;
import effects.EffectType;

/**
 * Created by wawszcza on 1/13/2015.
 */
public class Action implements Comparable<Action> {
    private EffectType type;
    private int initiative;
    private Effect effect;

    public Action(int initiative, Effect effect) {
        this.initiative = initiative;
        this.effect = effect;
        this.type = effect.getType();
    }

    @Override
    public int compareTo(Action o) {
        int a = getFinalInitiative();
        int b = o.getFinalInitiative();
        return a > b ? +1 : a < b ? -1 : 0;
    }

    public int getFinalInitiative(){
        return initiative+type.getInitiative();
    }
}
