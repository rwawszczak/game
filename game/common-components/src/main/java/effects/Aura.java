package effects;

/**
 * Created by wawszcza on 1/15/2015.
 */
public class Aura extends Effect {
    private int duration;

    public Aura(int duration) {
        this.duration = duration;
        type=EffectType.AURA;
    }
}
