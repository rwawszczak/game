package battleengine.action;

import battleengine.action.log.LogItem;

/**
 * Created by wawszcza on 7/2/2015.
 */
public interface Performable {
    LogItem perform(Actions pushedActions);

    void finish();
}
