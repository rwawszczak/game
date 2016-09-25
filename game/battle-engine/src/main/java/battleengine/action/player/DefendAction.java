package battleengine.action.player;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.action.log.LogItem;
import battleengine.gateway.CoefficientGateway;
import battleengine.entities.BattleEntity;
import battleengine.entities.player.Player;


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
    public LogItem perform( Actions pushedActions )
    {
        LogItem logItem = new LogItem(this.getClass().getSimpleName());
        logItem.setOwner(owner);
        logItem.setTarget(owner);
        logItem.setSuccess(true);

        owner.getAttributes().increaseDefence( CoefficientGateway.getBase().ofDefendBonus() );

        return logItem;
    }


    @Override
    public void finish()
    {
        owner.getAttributes().decreaseDefence( CoefficientGateway.getBase().ofDefendBonus() );
    }
}
