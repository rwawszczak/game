package effects;

/**
 * Created by wawszcza on 1/15/2015.
 */
public class Instant extends Effect {
    public Instant(Impact impact) {
        super(impact);
        type=EffectType.INSTANT;
    }
}
