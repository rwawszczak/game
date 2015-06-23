package battleengine;

import battleengine.action.Actions;
import battleengine.action.log.BattleLog;

/**
 * Created by wawszcza on 1/13/2015.
 */
public class BattleEngine {
    public EngineOutput processTurn(Actions actions) {
        BattleLog battleLog = new BattleLog();
        Actions pushedActions = new Actions();
        EngineOutput engineOutput = new EngineOutput(pushedActions,battleLog);
        actions.sort();
        for(int i = 0; i<actions.actionCount(); i++){
            battleLog.addItem(actions.get(i).perform(pushedActions));
        }
        for(int i = 0; i<actions.actionCount(); i++){
            actions.get(i).finish();
        }
        return engineOutput;
    }
}
