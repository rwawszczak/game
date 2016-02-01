package battleengine.action.player.weapon.effects;

import battleengine.action.Actions;
import battleengine.action.LuckCalculator;
import battleengine.action.Performable;
import battleengine.action.Targetable;
import battleengine.action.log.LogItem;
import battleengine.entities.player.Player;
import battleengine.gateway.CoefficientGateway;

/**
 * Created by wawszcza on 7/2/2015.
 */
public class BaseWeaponEffect implements Targetable, Performable {
    private static final double ADVANTAGE_MODIFIER = 1.5;
    private boolean hit = true;
    private Player owner;
    private Player target;

    public BaseWeaponEffect(Player owner, Player target) {
        this.owner = owner;
        this.target = target;
    }

    public final Player getTarget(){
        return target;
    }

    public final Player getOwner(){
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
            performOnHit(logItem, pushedActions);
        else
            performOnMiss(logItem, pushedActions);
        performAlways(logItem, pushedActions);
        return logItem;
    }

    @Override
    public final void finish() {
        if( hit )
            finishOnHit();
        else
            finishOnMiss();
    }

    protected void performAlways(LogItem logItem, Actions pushedActions) {
    }

    protected void performOnHit(LogItem logItem, Actions pushedActions) {
        boolean critical = testCritical();
        double critModifier = critical?CoefficientGateway.getBase().ofCriticalStrikeMultiplier():1d;
        int damage = (int) (calculateBaseDamage()*critModifier);
        logItem.setValue( damage );
        if(critical){
            performOnCrit(logItem, pushedActions);
        } else{
            performOnNoCrit(logItem, pushedActions);
        }
        target.decreaseHP( damage );
    }

    protected void performOnCrit(LogItem logItem, Actions pushedActions) {
        logItem.setInfoCode(CoefficientGateway.getLogValue().ofCriticalStrike());
    }

    protected void performOnNoCrit(LogItem logItem, Actions pushedActions) {
    }

    protected void performOnMiss(LogItem logItem, Actions pushedActions) {
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
