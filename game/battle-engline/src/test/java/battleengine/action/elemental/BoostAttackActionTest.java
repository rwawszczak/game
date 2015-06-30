package battleengine.action.elemental;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.entities.Element;
import battleengine.entities.elemental.Elemental;
import battleengine.entities.player.components.attributes.Attributes;
import battleengine.entities.player.Player;
import battleengine.gateway.CoefficientGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoostAttackActionTest {
    private static final int P1_INCREASE_ATTACK = 3;
    @Mock
    Player player;
    @Mock
    Attributes attributes;

    private static final int P1_ATTACK = 10;

    @Before
    public void setUp() throws Exception {
        when(player.getAttributes()).thenReturn(attributes);
    }

    @Test
    public void manaCostShouldBePaidOnlyOnce() throws Exception {
        when(attributes.getAttack()).thenReturn(P1_ATTACK);
        Elemental elemental = new Elemental(Element.EARTH);
        Action action = new BoostAttackAction(elemental,player);
        Actions actions = new Actions(action);

        do {
            action = actions.get(0);
            actions.clear();
            action.perform(actions);
        } while (actions.actionCount()>0);

        int expectedMana = CoefficientGateway.getBase().ofElementalMana()-CoefficientGateway.getManaCost().ofBoostAttack();
        assertEquals(expectedMana, elemental.getCurrentMana());
    }

    @Test
    public void attackShouldBeBoostedAtFirstTurnAndReturnedToNormalAtLast() throws Exception {
        when(attributes.getAttack()).thenReturn(P1_ATTACK);
        Elemental elemental = new Elemental(Element.EARTH);
        Action action = new BoostAttackAction(elemental,player);
        Actions actions = new Actions();

        action.perform(actions);
        verify(attributes).increaseAttack(P1_INCREASE_ATTACK);
        for(int i=1; i<CoefficientGateway.getAbilityValue().ofBoostAttackTurns();i++)
            action.perform(actions);
        action.finish();

        verify(attributes,times(1)).increaseAttack(P1_INCREASE_ATTACK);
        verify(attributes,times(1)).decreaseAttack(P1_INCREASE_ATTACK);
    }
}