package actions;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by wawszcza on 1/13/2015.
 */
public class ActionTest {
    @Test
    public void canCreateInstance() throws Exception {
        Action action = new Action();
        assertNotNull(action);
    }
}
