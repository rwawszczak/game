package battleengine.action.elemental;

import battleengine.action.Action;
import battleengine.coefficient.CoefficientGateway;
import battleengine.player.Player;
import battleengine.player.elemental.Elemental;

/**
 * Created by RaV on 10.05.15.
 */
public class HealAction extends Action {
    private final Elemental owner;
    private final Player target;

    public HealAction(Elemental owner, Player target) {
        super();
        this.owner = owner;
        this.target = target;
    }

    @Override
    public int getInitiative() {
        return owner.getInitiative()+ CoefficientGateway.getInitiative().ofHealAction();
    }

    @Override
    public void perform() {
        int healValue = CoefficientGateway.getAbilityValue().ofHealingAmount();
        int missingHP = target.getMissingHP();
        if(missingHP>0){
            target.increaseHP(healValue>missingHP?missingHP:healValue);
        }
    }

    @Override
    public void finish() {
        //No finish actions
    }
}
