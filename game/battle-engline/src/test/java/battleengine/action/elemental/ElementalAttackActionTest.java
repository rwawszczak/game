package battleengine.action.elemental;

import battleengine.action.Actions;
import battleengine.player.Player;
import battleengine.player.elemental.Elemental;
import battleengine.player.elemental.Elements;
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
        ElementalAttackAction earthAttack = new ElementalAttackAction(new Elemental(Elements.EARTH),player);
        ElementalAttackAction waterAttack = new ElementalAttackAction(new Elemental(Elements.WATER),player);
        ElementalAttackAction shadowAttack = new ElementalAttackAction(new Elemental(Elements.SHADOW),player);
        ElementalAttackAction fireAttack = new ElementalAttackAction(new Elemental(Elements.FIRE),player);
        ElementalAttackAction windAttack = new ElementalAttackAction(new Elemental(Elements.WIND),player);
        ElementalAttackAction lightAttack = new ElementalAttackAction(new Elemental(Elements.LIGHT),player);
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