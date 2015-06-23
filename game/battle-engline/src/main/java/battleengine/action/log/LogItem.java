package battleengine.action.log;

import battleengine.entities.BattleEntity;

/**
 * Created by wawszcza on 6/23/2015.
 */
public class LogItem {
    private String action;
    private BattleEntity owner;
    private BattleEntity target;
    private boolean success;
    private int value = 0;
    private int duration = 0;
    private int infoCode = 0;

    public LogItem(String action) {
        this.action = action;
    }

    public void setOwner(BattleEntity owner) {
        this.owner = owner;
    }

    public void setTarget(BattleEntity target) {
        this.target = target;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getAction() {
        return action;
    }

    public BattleEntity getOwner() {
        return owner;
    }

    public BattleEntity getTarget() {
        return target;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getDuration() {
        return duration;
    }

    public int getValue() {
        return value;
    }

    public int getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(int infoCode) {
        this.infoCode = infoCode;
    }

    @Override
    public String toString() {
        return "LogItem{" +
                "action='" + action + '\'' +
                ", owner=" + owner +
                ", target=" + target +
                ", success=" + success +
                ", value=" + value +
                ", duration=" + duration +
                ", infoCode=" + infoCode +
                '}';
    }
}
