package battleengine.action.player;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.coefficient.CoefficientGateway;
import battleengine.player.BattleEntity;
import battleengine.player.Player;


/**
 * Created by RaV on 09.05.15.
 */
public class DefendAction
    extends Action
{
    private Player owner;


    public DefendAction( Player owner )
    {
        super();
        this.owner = owner;
        setInitiativeModifier( CoefficientGateway.getInitiative().ofDefendAction() );
    }


    @Override
    public BattleEntity getOwner()
    {
        return owner;
    }


    @Override
    public void perform( Actions pushedActions )
    {
        owner.getAttributes().increaseDefence( CoefficientGateway.getBase().ofDefendBonus() );
    }


    @Override
    public void finish()
    {
        owner.getAttributes().decreaseDefence( CoefficientGateway.getBase().ofDefendBonus() );
    }
}
