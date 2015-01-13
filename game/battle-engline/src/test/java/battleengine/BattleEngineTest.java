package battleengine;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by wawszcza on 1/13/2015.
 */
public class BattleEngineTest {
    @Test
    public void canCreateInstance() throws Exception {
        BattleEngine battleEngine = new BattleEngine();
        assertNotNull(battleEngine);
    }
}
