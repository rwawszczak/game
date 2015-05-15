package battleengine.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by RaV on 09.05.15.
 */
public class Actions {
    private final List<Action> actions;

    public Actions() {
        actions = new ArrayList<Action>();
    }

    public Actions(Action... action) {
        this.actions = Arrays.asList(action);
    }

    private Actions(List<Action> actions) {
        this.actions = actions;
    }

    public void add(Action action) {
        actions.add(action);
    }

    public void sort() {
        Collections.sort(actions);
    }

    public int actionCount(){
        return actions.size();
    }

    public Action get(int index){
        return actions.get(index);
    }

    public Actions getActionsFrom(int index) {
        return new Actions(actions.subList(index,actionCount()));
    }

    public Actions getActionsOfType(Class<? extends Action> actionType) {
        Actions subActions = new Actions();
        for(Action a : actions){
            if(actionType.isAssignableFrom(a.getClass()))
                subActions.add(a);
        }
        return subActions;
    }
}
