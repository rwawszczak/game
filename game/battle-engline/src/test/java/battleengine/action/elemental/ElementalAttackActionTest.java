package battleengine.action.elemental;

import battleengine.action.Actions;
import battleengine.entities.player.Player;
import battleengine.entities.elemental.Elemental;
import battleengine.entities.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ElementalAttackActionTest {
    @Mock
    Player player;

    @Test
    public void elementalAttackActionsShouldBeCorrectlySortedAccordingToType() throws Exception {
        ElementalAttackAction earthAttack = new ElementalAttackAction(new Elemental(Element.EARTH),player);
        ElementalAttackAction waterAttack = new ElementalAttackAction(new Elemental(Element.WATER),player);
        ElementalAttackAction shadowAttack = new ElementalAttackAction(new Elemental(Element.SHADOW),player);
        ElementalAttackAction fireAttack = new ElementalAttackAction(new Elemental(Element.FIRE),player);
        ElementalAttackAction windAttack = new ElementalAttackAction(new Elemental(Element.WIND),player);
        ElementalAttackAction lightAttack = new ElementalAttackAction(new Elemental(Element.LIGHT),player);
        Actions actions = new Actions();
        actions.add(waterAttack);
        actions.add(fireAttack);
        actions.add(lightAttack);
        actions.add(windAttack);
        actions.add(earthAttack);
        actions.add(shadowAttack);

        actions.sort();

        assertEquals(lightAttack,actions.get(0));
        assertEquals(windAttack,actions.get(1));
        assertEquals(fireAttack,actions.get(2));
        assertEquals(shadowAttack,actions.get(3));
        assertEquals(waterAttack,actions.get(4));
        assertEquals(earthAttack,actions.get(5));
    }


}