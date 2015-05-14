package battleengine.action;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

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