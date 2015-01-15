package effects;

/**
 * Created by wawszcza on 1/15/2015.
 */
public class Effect {
    protected EffectType type;
    private Impact impact;

    public Effect() {
        type=EffectType.NORMAL;
    }

    public EffectType getType() {
        return type;
    }

    public Impact getImpact() {
        return impact;
    }
}
