package battleengine.action.elemental;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.coefficient.CoefficientGateway;
import battleengine.player.Player;
import battleengine.player.elemental.Elemental;


/**
 * Created by wawszcza on 5/13/2015.
 */
public class BoostAttackAction
    extends Action
{
    private final Elemental owner;
    private final Player target;
    private int turns = CoefficientGateway.getAbilityValue().ofBoostAttackTurns();
    private int boostAmount;


    public BoostAttackAction(Elemental owner, Player target)
    {
        super();
        this.owner = owner;
        this.target = target;
        setPriority( CoefficientGateway.getPriority().ofBoostAttackAction() );
        this.boostAmount = 0;
    }


    private BoostAttackAction(Elemental owner, Player target, int turns, int boostAmount)
    {
        super();
        this.owner = owner;
        this.target = target;
        this.turns = turns;
        this.boostAmount = boostAmount;
    }


    @Override
    public int getInitiative()
    {
        return CoefficientGateway.getInitiative().ofBoostAttackAction();
    }


    @Override
    public void perform( Actions pushedActions )
    {
        if( boostAmount == 0 ) {
            boostAmount = (int) (target.getAttributes().getAttack() * CoefficientGateway.getAbilityValue().ofBoostAttackMultiplier());
            target.getAttributes().increaseAttack(boostAmount);
        }
        if (turns>1)
            pushedActions.add(new BoostAttackAction(owner, target,turns-1,boostAmount));
    }


    @Override
    public void finish()
    {
        if(turns==1)
            target.getAttributes().decreaseAttack(boostAmount);
    }
}
