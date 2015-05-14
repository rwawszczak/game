package battleengine.action;

import battleengine.coefficient.CoefficientGateway;
import battleengine.player.BattleEntity;

/**
 * Created by RaV on 09.05.15.
 */
public abstract class Action implements Comparable<Action> {

    private int priority = 0;
    private int initiativeModifier = 0;

    public abstract BattleEntity getOwner();
    public abstract void perform(Actions pushedActions);
    public abstract void finish();

    public final int getPriority() {
        return priority;
    }

    public final void setPriority(int priority) {
        this.priority = priority;
    }

    public void setInitiativeModifier(int initiativeModifier) {
        this.initiativeModifier = initiativeModifier;
    }

    @Override
    public int compareTo(Action o) {
        return o.getFinalInitiative()- getFinalInitiative();
    }

    private int getFinalInitiative(){
        return getInitiative()+getPriority()* CoefficientGateway.getBase().ofPriorityMultiplier();
    }
    private int getInitiative(){
        return getOwner().getInitiative()+initiativeModifier;
    }
}
