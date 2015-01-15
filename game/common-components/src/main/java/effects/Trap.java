package effects;

/**
 * Created by wawszcza on 1/15/2015.
 */
public class Trap extends Effect {
    private Trigger trigger;

    public Trap(Impact impact, Trigger trigger) {
        super(impact);
        this.trigger = trigger;
        type=EffectType.TRAP;
    }
}
