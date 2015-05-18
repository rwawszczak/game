package battleengine.action.elemental;

import battleengine.action.Actions;
import battleengine.action.Targetable;
import battleengine.entities.BattleEntity;
import battleengine.entities.elemental.Elemental;
import battleengine.entities.player.Player;
import battleengine.gateway.CoefficientGateway;


/**
 * Created by RaV on 10.05.15.
 */
public class HealAction
    extends BaseElementalAction
    implements Targetable
{
    private final Elemental owner;
    private final Player target;


    public HealAction( Elemental owner, Player target )
    {
        super(CoefficientGateway.getManaCost().ofHealManaCost());
        this.owner = owner;
        this.target = target;
        setInitiativeModifier( CoefficientGateway.getInitiative().ofHealAction() );
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
        int healValue = (int)(CoefficientGateway.getAbilityValue().ofHealingMultiplier() * target.getAttributes().getMaxHP());
        int missingHP = target.getMissingHP();
        if( missingHP > 0 )
        {
            target.increaseHP( healValue > missingHP ? missingHP : healValue );
        }
    }


    @Override
    public void finishElementalAction()
    {
        // No finish actions
    }
}
