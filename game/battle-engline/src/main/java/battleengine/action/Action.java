package battleengine.action;

import battleengine.coefficient.CoefficientGateway;

/**
 * Created by RaV on 09.05.15.
 */
public abstract class Action implements Comparable<Action> {

    private int priority = 0;

    public abstract int getInitiative();
    public abstract void perform();
    public abstract void finish();

    public final int getPriority() {
        return priority;
    }

    public final void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Action o) {
        return o.getFinalInitiative()- getFinalInitiative();
    }

    private int getFinalInitiative(){
        return getInitiative()+getPriority()* CoefficientGateway.getBase().ofPriorityMultiplier();
    }
}
