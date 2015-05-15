package battleengine.action.elemental;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.action.Targetable;
import battleengine.gateway.CoefficientGateway;
import battleengine.entities.BattleEntity;
import battleengine.entities.player.Player;
import battleengine.entities.elemental.Elemental;


/**
 * Created by wawszcza on 5/14/2015.
 */
public class ElementalAttackAction
    extends Action
    implements Targetable
{
    private final Elemental owner;
    private final Player target;


    public ElementalAttackAction( Elemental owner, Player target )
    {
        this.owner = owner;
        this.target = target;
        setInitiativeModifier( CoefficientGateway.getInitiative().ofElementalAttackAction() );
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
        // TODO: add implementation depending on target elemental resistance
    }


    @Override
    public void finish()
    {

    }
}
