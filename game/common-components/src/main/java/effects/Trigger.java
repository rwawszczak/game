package effects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wawszcza on 1/15/2015.
 */
public enum Trigger {
    ACTION;

    private List<Trigger> parents;

    Trigger(Trigger... triggers) {
        parents = new ArrayList<Trigger>();
        for(Trigger trigger : triggers){
            parents.add(trigger);
        }
    }
}
