package battleengine.action.log;

import java.util.ArrayList;
import java.util.List;

public class BattleLog {

    private final List<LogItem> log;

    public BattleLog() {
        log = new ArrayList<LogItem>();
    }

    public int size(){
        return log.size();
    }

    public LogItem getItem(int index){
        return log.get(index);
    }

    public void addItem(LogItem item){
        log.add(item);
    }

    public List<LogItem> getList() {
        return new ArrayList<LogItem>(log);
    }
}
