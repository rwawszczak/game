package battleengine.action.player.weapon.effects;

import battleengine.action.Actions;
import battleengine.action.LuckCalculator;
import battleengine.action.Performable;
import battleengine.action.Targetable;
import battleengine.action.log.LogItem;
import battleengine.entities.BattleEntity;
import battleengine.entities.player.Player;
import battleengine.gateway.CoefficientGateway;

/**
 * Created by wawszcza on 7/2/2015.
 */
public final class BaseEffect implements Targetable, Performable {
    private static final double ADVANTAGE_MODIFIER = 1.5;
    private boolean hit = true;
    protected Player owner;
    protected Player target;

    public BaseEffect(Player owner, Player target) {
        this.owner = owner;
        this.target = target;
    }

    public final BattleEntity getTarget(){
        return target;
    }

    public final BattleEntity getOwner(){
        return owner;
    }

    @Override
    public final LogItem perform(Actions pushedActions) {
        LogItem logItem = new LogItem(this.getClass().getSimpleName());
        logItem.setOwner(owner);
        logItem.setTarget(target);
        hit = isHit();
        logItem.setSuccess(hit);
        if( hit )
            performOnHit(logItem);
        else
            performOnMiss(logItem);
        return logItem;
    }

    @Override
    public final void finish() {
        if( hit )
            finishOnHit();
        else
            finishOnMiss();
    }

    protected void performOnHit(LogItem logItem) {
        boolean critical = testCritical();
        double critModifier = critical?CoefficientGateway.getBase().ofCriticalStrikeMultiplier():1d;
        int damage = (int) (calculateBaseDamage()*critModifier);
        if(critical)
            logItem.setInfoCode(CoefficientGateway.getLogValue().ofCriticalStrike());
        target.decreaseHP( damage );
        logItem.setValue( damage );
    }

    protected void performOnMiss(LogItem logItem) {
    }

    protected void finishOnHit() {
    }

    protected void finishOnMiss() {
    }


    protected final int calculateBaseDamage() {
        return (int)(owner.getAttributes().getAttack() * target.getPhysicalDamageReduction());
    }


    protected final boolean testCritical()
    {
        return LuckCalculator.isSuccess(owner.getAttributes().getLuck(), getCritDifficulty());
    }


    private boolean isHit()
    {
        return Math.random() * owner.getAttributes().getDexterity() > target.getAttributes().getDexterity() *
                CoefficientGateway.getBase().ofHitChanceMultiplier();
    }
    private int getCritDifficulty()
    {
        int difficulty = (int)(target.getAttributes().getDexterity() * ADVANTAGE_MODIFIER) - owner.getAttributes().getDexterity();
        return difficulty > 0 ? difficulty : 0;
    }
}
