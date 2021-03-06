package battleengine.action.player;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.action.Targetable;
import battleengine.action.log.LogItem;
import battleengine.action.player.weapon.WeaponEffectFactory;
import battleengine.action.player.weapon.effects.BaseWeaponEffect;
import battleengine.entities.BattleEntity;
import battleengine.entities.player.Player;
import battleengine.gateway.CoefficientGateway;


public class AttackAction
    extends Action
    implements Targetable
{
    private final Player owner;
    private final Player target;
    private BaseWeaponEffect weaponEffect;


    public AttackAction( Player owner, Player target )
    {
        super();
        this.owner = owner;
        this.target = target;
        setInitiativeModifier( CoefficientGateway.getInitiative().ofAttackAction() );
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
        weaponEffect = WeaponEffectFactory.getWeaponEffect(owner, target);
        return weaponEffect.perform(pushedActions);
    }


    @Override
    public void finish()
    {
        weaponEffect.finish();
    }
}
