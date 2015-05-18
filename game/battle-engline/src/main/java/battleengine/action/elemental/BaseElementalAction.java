package battleengine.action.elemental;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.entities.elemental.Elemental;

/**
 * Created by wawszcza on 5/18/2015.
 */
public abstract class BaseElementalAction extends Action{
    private int manaCost;
    private boolean success=true;

    protected BaseElementalAction(int manaCost) {
        this.manaCost = manaCost;
    }

    protected abstract void performElementalAction(Actions pushedActions);

    protected abstract void finishElementalAction();

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getManaCost() {
        return manaCost;
    }

    @Override
    public final void perform(Actions pushedActions){
        if(getOwner().getCurrentMana()>=manaCost){
            getOwner().decreaseMana(manaCost);
            performElementalAction(pushedActions);
        } else {
            success = false;
        }
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
