package battleengine.action.elemental;

import battleengine.action.Actions;
import battleengine.action.Targetable;
import battleengine.entities.BattleEntity;
import battleengine.entities.elemental.Elemental;
import battleengine.entities.player.Player;
import battleengine.gateway.CoefficientGateway;


/**
 * Created by wawszcza on 5/13/2015.
 */
public class BoostAttackAction
    extends BaseElementalAction
    implements Targetable
{
    private final Elemental owner;
    private final Player target;
    private int turns = CoefficientGateway.getAbilityValue().ofBoostAttackTurns();
    private int boostAmount;
    private boolean shouldBoost;


    public BoostAttackAction( Elemental owner, Player target )
    {
        super(CoefficientGateway.getManaCost().ofBoostAttack());
        this.owner = owner;
        this.target = target;
        this.boostAmount = 0;
        setPriority(CoefficientGateway.getPriority().ofBoostAttackAction());
        setInitiativeModifier(CoefficientGateway.getInitiative().ofBoostAttackAction());
        shouldBoost = true;
    }


    @Override
    public Elemental getOwner()
    {
        return owner;
    }


    @Override
    public BattleEntity getTarget()
    {
        return target;
    }


    @Override
    public void performElementalAction( Actions pushedActions )
    {
        turns--;
        if( shouldBoost )
        {
            setManaCost(0);
            boostAmount = (int)(target.getAttributes().getAttack() * CoefficientGateway.getAbilityValue().ofBoostAttackMultiplier());
            target.getAttributes().increaseAttack(boostAmount);
            shouldBoost = false;
        }
        if( turns > 0 )
            pushedActions.add( this );
    }


    @Override
    public void finishElementalAction()
    {
        if( turns == 0 )
            target.getAttributes().decreaseAttack( boostAmount );
    }
}
