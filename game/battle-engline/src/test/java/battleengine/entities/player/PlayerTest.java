package battleengine.entities.player;

import battleengine.entities.Element;
import battleengine.entities.elemental.Elemental;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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

    @Test
    public void when5NeutralElementalsResistanceIs0() throws Exception {
        Player player = new Player("Player", new Attributes(1, 1, 1, 1, 1, 1, 1, 1));
        player.addElemental(new Elemental(Element.LIGHT));
        player.addElemental(new Elemental(Element.LIGHT));
        player.addElemental(new Elemental(Element.LIGHT));
        player.addElemental(new Elemental(Element.LIGHT));
        player.addElemental(new Elemental(Element.LIGHT));

        assertEquals(0, player.getElementalDamageReduction(Element.FIRE), 0);
    }

    @Test
    public void when5NegativeElementalsResistanceIsLessThan0() throws Exception {
        Player player = new Player("Player", new Attributes(1, 1, 1, 1, 1, 1, 1, 1));
        player.addElemental(new Elemental(Element.WATER));
        player.addElemental(new Elemental(Element.WATER));
        player.addElemental(new Elemental(Element.WATER));
        player.addElemental(new Elemental(Element.WATER));
        player.addElemental(new Elemental(Element.WATER));

        System.out.println(player.getElementalDamageReduction(Element.FIRE));

        assertTrue(player.getElementalDamageReduction(Element.FIRE) < 0);
    }

    @Test
    public void negativeAndPositiveElementResistanceShouldBeOpposite() throws Exception {
        Player player1 = new Player("Player", new Attributes(1, 1, 1, 1, 1, 1, 1, 1));
        Player player2 = new Player("Player", new Attributes(1, 1, 1, 1, 1, 1, 1, 1));

        player1.addElemental(new Elemental(Element.WATER));
        player1.addElemental(new Elemental(Element.WATER));
        player1.addElemental(new Elemental(Element.WATER));
        player1.addElemental(new Elemental(Element.WATER));
        player1.addElemental(new Elemental(Element.WATER));

        player2.addElemental(new Elemental(Element.WIND));
        player2.addElemental(new Elemental(Element.WIND));
        player2.addElemental(new Elemental(Element.WIND));
        player2.addElemental(new Elemental(Element.WIND));
        player2.addElemental(new Elemental(Element.WIND));


        assertEquals(player1.getElementalDamageReduction(Element.FIRE), -player2.getElementalDamageReduction(Element.FIRE), 0);
    }
}