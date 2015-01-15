package effects;

/**
 * Created by wawszcza on 1/15/2015.
 */
public class Effect {
    protected EffectType type;
    private Impact impact;

    public Effect(Impact impact) {
        this.impact=impact;
        type=EffectType.NORMAL;
    }

    public EffectType getType() {
        return type;
    }

    public Impact getImpact() {
        return impact;
    }
}
