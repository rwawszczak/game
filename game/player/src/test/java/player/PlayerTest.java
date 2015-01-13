package player;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by wawszcza on 1/13/2015.
 */
public class PlayerTest {
    @Test
    public void canCreateInstance() throws Exception {
        Player player = new Player();
        assertNotNull(player);
    }
}
