package battleengine;

import battleengine.action.Actions;

/**
 * Created by wawszcza on 1/13/2015.
 */
public class BattleEngine {
    public Actions processTurn(Actions actions) {
        actions.sort();
        Actions pushedActions = new Actions();
        for(int i = 0; i<actions.actionCount(); i++){
            actions.get(i).perform(pushedActions);
        }
        for(int i = 0; i<actions.actionCount(); i++){
            actions.get(i).finish();
        }
        return pushedActions;
    }
}
