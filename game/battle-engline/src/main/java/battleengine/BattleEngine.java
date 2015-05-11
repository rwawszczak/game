package battleengine;

import battleengine.action.Actions;

/**
 * Created by wawszcza on 1/13/2015.
 */
public class BattleEngine {
    public void processTurn(Actions actions) {
        actions.sort();
        for(int i = 0; i<actions.actionCount(); i++){
            actions.get(i).perform();
        }
        for(int i = 0; i<actions.actionCount(); i++){
            actions.get(i).finish();
        }
    }
}
