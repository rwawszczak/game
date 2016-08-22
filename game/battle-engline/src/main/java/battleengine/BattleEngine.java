package battleengine;

import battleengine.action.Actions;
import battleengine.action.log.BattleLog;

public class BattleEngine {
    public EngineOutput processTurn(Actions actions) {
        BattleLog battleLog = new BattleLog();
        Actions actionsCopy = new Actions(actions);
        Actions pushedActions = new Actions();
        EngineOutput engineOutput = new EngineOutput(pushedActions,battleLog);
        actionsCopy.sort();
        for(int i = 0; i<actionsCopy.actionCount(); i++){
            battleLog.addItem(actionsCopy.get(i).perform(pushedActions));
        }
        for(int i = 0; i<actionsCopy.actionCount(); i++){
            actionsCopy.get(i).finish();
        }
        return engineOutput;
    }
}
