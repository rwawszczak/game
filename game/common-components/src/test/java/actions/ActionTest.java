package actions;

import effects.*;
import org.junit.Test;

import static effects.EffectType.*;
import static org.junit.Assert.*;

/**
 * Created by wawszcza on 1/13/2015.
 */
public class ActionTest {

    @Test
    public void correctlyCompareDifferentTypeActions() throws Exception {
        Action instantAction = new Action(0, new Instant());
        Action auraAction = new Action(0, new Aura(1));
        Action normalAction = new Action(0, new Effect());
        Action trapAction = new Action(0, new Trap(Trigger.ACTION));

        assertSame(1,instantAction.compareTo(auraAction));
        assertSame(-1, auraAction.compareTo(instantAction));
        assertSame(1,instantAction.compareTo(normalAction));
        assertSame(-1, normalAction.compareTo(instantAction));
        assertSame(1,instantAction.compareTo(trapAction));
        assertSame(-1, trapAction.compareTo(instantAction));
        assertSame(1,auraAction.compareTo(normalAction));
        assertSame(-1, normalAction.compareTo(auraAction));
        assertSame(1,auraAction.compareTo(trapAction));
        assertSame(-1, trapAction.compareTo(auraAction));
        assertSame(1,normalAction.compareTo(trapAction));
        assertSame(-1, trapAction.compareTo(normalAction));
        assertSame(0, instantAction.compareTo( new Action(0, new Instant()) ) );
        assertSame(0, auraAction.compareTo(new Action(0, new Aura(1)) ) );
        assertSame(0, normalAction.compareTo(new Action(0, new Effect()) ) );
        assertSame(0, trapAction.compareTo(new Action(0, new Trap(Trigger.ACTION)) ) );
    }
}
