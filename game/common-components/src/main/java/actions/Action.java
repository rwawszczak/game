package actions;

/**
 * Created by wawszcza on 1/13/2015.
 */
public class Action implements Comparable<Action> {
    private ActionType type;
    private int initiative;

    public Action(ActionType type, int initiative) {
        this.type = type;
        this.initiative = initiative;
    }

    @Override
    public int compareTo(Action o) {
        int a = getFinalInitiative();
        int b = o.getFinalInitiative();
        return a > b ? +1 : a < b ? -1 : 0;
    }

    public int getFinalInitiative(){
        return initiative+type.getInitiative();
    }
}
