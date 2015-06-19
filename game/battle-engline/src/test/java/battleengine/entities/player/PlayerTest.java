package battleengine.entities.player;

import battleengine.entities.Element;
import battleengine.entities.elemental.Elemental;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;


public class PlayerTest {

    @Test
    public void when4of5ElementalsSameTypeThisTypeResistanceGreaterThan1() throws Exception {
        Player player = new Player("Player", new Attributes(1, 1, 1, 1, 1, 1, 1, 1));
        player.addElemental(new Elemental(Element.FIRE));
        player.addElemental(new Elemental(Element.FIRE));
        player.addElemental(new Elemental(Element.FIRE));
        player.addElemental(new Elemental(Element.FIRE));
        player.addElemental(new Elemental(Element.LIGHT));

        assertTrue(player.getElementalDamageReduction(Element.FIRE) > 1);
    }
}