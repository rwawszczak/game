package battleengine;

import battleengine.action.Actions;
import battleengine.action.log.BattleLog;

public class EngineOutput {
    private final Actions actions;
    private final BattleLog battleLog;

    public EngineOutput(Actions actions, BattleLog battleLog) {
        this.actions = actions;
        this.battleLog = battleLog;
    }

    public Actions getActions() {
        return actions;
    }

    public BattleLog getBattleLog() {
        return battleLog;
    }
}
