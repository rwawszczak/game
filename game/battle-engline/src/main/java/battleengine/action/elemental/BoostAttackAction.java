package battleengine.action.elemental;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.action.Targetable;
import battleengine.gateway.CoefficientGateway;
import battleengine.entities.BattleEntity;
import battleengine.entities.player.Player;
import battleengine.entities.elemental.Elemental;


/**
 * Created by wawszcza on 5/13/2015.
 */
public class BoostAttackAction
    extends Action
    implements Targetable
{
    private final Elemental owner;
    private final Player target;
    private int turns = CoefficientGateway.getAbilityValue().ofBoostAttackTurns();
    private int boostAmount;


    public BoostAttackAction( Elemental owner, Player target )
    {
        super();
        this.owner = owner;
        this.target = target;
        this.boostAmount = 0;
        setPriority( CoefficientGateway.getPriority().ofBoostAttackAction() );
        setInitiativeModifier( CoefficientGateway.getInitiative().ofBoostAttackAction() );
    }


    private BoostAttackAction( Elemental owner, Player target, int turns, int boostAmount )
    {
        super();
        this.owner = owner;
        this.target = target;
        this.turns = turns;
        this.boostAmount = boostAmount;
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
        if( boostAmount == 0 )
        {
            boostAmount = (int)(target.getAttributes().getAttack() * CoefficientGateway.getAbilityValue().ofBoostAttackMultiplier());
            target.getAttributes().increaseAttack( boostAmount );
        }
        if( turns > 1 )
            pushedActions.add( new BoostAttackAction( owner, target, turns - 1, boostAmount ) );
    }


    @Override
    public void finish()
    {
        if( turns == 1 )
            target.getAttributes().decreaseAttack( boostAmount );
    }
}
