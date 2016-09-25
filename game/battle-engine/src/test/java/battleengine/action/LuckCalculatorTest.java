package battleengine.action;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class LuckCalculatorTest {
    @Test
    public void whenLuckIsZeroShouldAlwaysReturnFalse() throws Exception {
        boolean success = LuckCalculator.isSuccess(0,0);

        assertFalse(success);
    }

    @Test
    public void whenLuckGreaterThanZeroWhileDifficultyIsZeroShouldAlwaysReturnTrue() throws Exception {
        boolean success = LuckCalculator.isSuccess(1,0);

        assertTrue(success);
    }
}