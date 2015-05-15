package battleengine.action.player;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.action.LuckCalculator;
import battleengine.action.Targetable;
import battleengine.gateway.CoefficientGateway;
import battleengine.entities.BattleEntity;
import battleengine.entities.player.Player;


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


    private double calculateReduction()
    {
        double base = target.getAttributes().getDefence() + CoefficientGateway.getBase().ofDamageReduction();
        return 1 - (target.getAttributes().getDefence() / base);
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
    public void perform( Actions pushedActions )
    {
        hit = isHit();
        // TODO: add on hit effects
        if( hit )
        {
            int damage = (int)(owner.getAttributes().getAttack() * calculateReduction());
            if( isCritical() )
            {
                damage *= CoefficientGateway.getBase().ofCriticalStrikeMultiplier();
            }
            target.decreaseHP( damage );
        }
    }


    @Override
    public void finish()
    {
        if( hit )
        {
            finishOnHitEffects();
        }
    }

    private void finishOnHitEffects() {
        // TODO: add finish item effects
    }
}
