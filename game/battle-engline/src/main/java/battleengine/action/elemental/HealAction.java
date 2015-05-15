package battleengine.action.elemental;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.action.Targetable;
import battleengine.gateway.CoefficientGateway;
import battleengine.entities.BattleEntity;
import battleengine.entities.player.Player;
import battleengine.entities.elemental.Elemental;


/**
 * Created by RaV on 10.05.15.
 */
public class HealAction
    extends Action
    implements Targetable
{
    private final Elemental owner;
    private final Player target;


    public HealAction( Elemental owner, Player target )
    {
        super();
        this.owner = owner;
        this.target = target;
        setInitiativeModifier( CoefficientGateway.getInitiative().ofHealAction() );
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
        int healValue = (int)(CoefficientGateway.getAbilityValue().ofHealingMultiplier() * target.getAttributes().getMaxHP());
        int missingHP = target.getMissingHP();
        if( missingHP > 0 )
        {
            target.increaseHP( healValue > missingHP ? missingHP : healValue );
        }
    }


    @Override
    public void finish()
    {
        // No finish actions
    }
}
