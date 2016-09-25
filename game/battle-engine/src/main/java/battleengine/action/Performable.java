package battleengine.action;

import battleengine.action.log.LogItem;

public interface Performable {
    LogItem perform(Actions pushedActions);

    void finish();
}
