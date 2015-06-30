package battleengine.action.player;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.action.LuckCalculator;
import battleengine.action.Targetable;
import battleengine.action.log.LogItem;
import battleengine.entities.BattleEntity;
import battleengine.entities.player.Player;
import battleengine.gateway.CoefficientGateway;


/**
 * Created by RaV on 09.05.15.
 */
public class AttackAction
    extends Action
    implements Targetable
{
    private final Player owner;
    private final Player target;
    private boolean hit = true;
    private boolean crit = false;


    public AttackAction( Player owner, Player target )
    {
        super();
        this.owner = owner;
        this.target = target;
        setInitiativeModifier( CoefficientGateway.getInitiative().ofAttackAction() );
    }


    private boolean isHit()
    {
        return Math.random() * owner.getAttributes().getDexterity() > target.getAttributes().getDexterity() *
            CoefficientGateway.getBase().ofHitChanceMultiplier();
    }


    private boolean isCritical()
    {
        return LuckCalculator.isSuccess( owner.getAttributes().getLuck(), getDifficulty() );
    }


    private int getDifficulty()
    {
        int difficulty = (int)(target.getAttributes().getDexterity() * 1.5) - owner.getAttributes().getDexterity();
        return difficulty > 0 ? difficulty : 0;
    }


    @Override
    public BattleEntity getOwner()
    {
        return owner;
    }


    @Override
    public BattleEntity getTarget()
    {
        return target;
    }


    @Override
    public LogItem perform( Actions pushedActions )
    {
        LogItem logItem = new LogItem(this.getClass().getSimpleName());
        logItem.setOwner(owner);
        logItem.setTarget(target);
        hit = isHit();
        logItem.setSuccess(hit);
        if( hit )
        {
            onHitEffects();
            int damage = (int)(owner.getAttributes().getAttack() * target.getPhysicalDamageReduction());
            if( isCritical() )
            {
                damage *= CoefficientGateway.getBase().ofCriticalStrikeMultiplier();
                crit = true;
                logItem.setInfoCode(CoefficientGateway.getLogValue().ofCriticalStrike());
            }
            target.decreaseHP( damage );
            logItem.setValue(damage);
        }
        return logItem;
    }


    @Override
    public void finish()
    {
        if( hit )
        {
            finishOnHitEffects();
        }
    }

    private void onHitEffects() {
        if(owner.getMainWeapon() != null) {
            owner.getMainWeapon().performOnHit(owner, target, crit);
        }
    }

    private void finishOnHitEffects() {
        if(owner.getMainWeapon() != null)
            owner.getMainWeapon().finishOnHit(owner, target, crit);
    }
}
