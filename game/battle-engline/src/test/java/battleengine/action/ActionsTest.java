package battleengine.action;

import battleengine.action.elemental.HealAction;
import battleengine.action.player.AttackAction;
import battleengine.action.player.DefendAction;
import org.junit.Test;

import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ActionsTest {

    @Test
    public void getActionsFromReturnSubListOfActions() throws Exception {
        Action attackAction = new AttackAction(null,null);
        Action defendAction = new DefendAction(null);
        Actions actions = new Actions(attackAction, defendAction);

        Actions subActions = actions.getActionsFrom(1);

        assertEquals(1,subActions.actionCount());
        assertSame(subActions.get(0), defendAction);
    }

    @Test
    public void getActionByTypeReturnSubListOfGivenTypeActions() throws Exception {
        Actions actions = new Actions(new AttackAction(null,null), new DefendAction(null), new HealAction(null, null), new DefendAction(null));

        Actions subActions = actions.getActionsOfType(DefendAction.class);

        assertEquals(2, subActions.actionCount());
        for(int i = 0; i< subActions.actionCount(); i++){
            assertTrue(DefendAction.class.isAssignableFrom(subActions.get(i).getClass()));
        }
    }
}