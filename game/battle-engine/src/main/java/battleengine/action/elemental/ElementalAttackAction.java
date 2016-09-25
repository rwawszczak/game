package battleengine.action.elemental;

import battleengine.action.Actions;
import battleengine.action.Targetable;
import battleengine.action.log.LogItem;
import battleengine.entities.BattleEntity;
import battleengine.entities.elemental.Elemental;
import battleengine.entities.player.Player;
import battleengine.gateway.CoefficientGateway;


public class ElementalAttackAction
    extends BaseElementalAction
    implements Targetable
{
    private final Elemental owner;
    private final Player target;


    public ElementalAttackAction( Elemental owner, Player target )
    {
        super(CoefficientGateway.getManaCost().ofElementalAttack());
        this.owner = owner;
        this.target = target;
        setInitiativeModifier( CoefficientGateway.getInitiative().ofElementalAttackAction() );
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
    public LogItem performElementalAction( Actions pushedActions )
    {
        LogItem logItem = new LogItem(this.getClass().getSimpleName());
        int damage = (int)(CoefficientGateway.getBase().ofElementalAttackDamage() * (1-target.getElementalDamageReduction(owner.getType())));
        if(damage > 0)
            target.decreaseHP( damage );
        else if (damage < 0)
            target.increaseHP(-damage);
        logItem.setValue(damage);
        return logItem;
    }


    @Override
    public void finishElementalAction()
    {
        // No finish actions
    }

    @Override
    protected void fillLog(LogItem item) {
        item.setTarget(target);
        item.setOwner(owner);
    }
}
