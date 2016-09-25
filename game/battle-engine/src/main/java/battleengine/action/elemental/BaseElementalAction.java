package battleengine.action.elemental;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.action.log.LogItem;
import battleengine.entities.elemental.Elemental;

public abstract class BaseElementalAction extends Action{
    private int manaCost;
    private boolean success=true;

    protected BaseElementalAction(int manaCost) {
        this.manaCost = manaCost;
    }

    protected abstract LogItem performElementalAction(Actions pushedActions);

    protected abstract void finishElementalAction();

    protected abstract void fillLog(LogItem item);

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getManaCost() {
        return manaCost;
    }

    @Override
    public final LogItem perform(Actions pushedActions){
        LogItem logItem;
        if(getOwner().getCurrentMana()>=manaCost){
            getOwner().decreaseMana(manaCost);
            logItem = performElementalAction(pushedActions);
        } else {
            logItem = new LogItem(this.getClass().getSimpleName());
            success = false;
        }
        logItem.setSuccess(success);
        fillLog(logItem);
        return logItem;
    }

    @Override
    public final void finish(){
        if(success){
            finishElementalAction();
        }
    }

    @Override
    public abstract Elemental getOwner();
}
