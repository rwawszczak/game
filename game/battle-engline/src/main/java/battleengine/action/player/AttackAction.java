package battleengine.action.player;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.action.LuckCalculator;
import battleengine.action.Targetable;
import battleengine.action.log.LogItem;
import battleengine.action.player.weapon.WeaponEffect;
import battleengine.action.player.weapon.WeaponEffectFactory;
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
    private static final double ADVANTAGE_MODIFIER = 1.5;
    private final Player owner;
    private final Player target;
    private boolean hit = true;
    private boolean crit = false;
    private WeaponEffect weaponEffect;


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
        int difficulty = (int)(target.getAttributes().getDexterity() * ADVANTAGE_MODIFIER) - owner.getAttributes().getDexterity();
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
            onHitEffects(pushedActions, logItem);
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

    private void onHitEffects(Actions pushedActions, LogItem logItem) {
        if(owner.getMainWeapon() != null) {
            weaponEffect = WeaponEffectFactory.getWeaponEffect(owner, target);
            logItem.setInnerLog(weaponEffect.perform(pushedActions));
        }

    }

    private void finishOnHitEffects() {
        if(owner.getMainWeapon() != null)
            weaponEffect.finish();
    }
}
