package effects;

/**
 * Created by wawszcza on 1/15/2015.
 */
public class Trap extends Effect {
    private Trigger trigger;

    public Trap(Trigger trigger) {
        this.trigger = trigger;
        type=EffectType.TRAP;
    }
}
