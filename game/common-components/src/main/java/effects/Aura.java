package effects;

/**
 * Created by wawszcza on 1/15/2015.
 */
public class Aura extends Effect {
    private int duration;

    public Aura(Impact impact, int duration) {
        super(impact);
        this.duration = duration;
        type=EffectType.AURA;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
