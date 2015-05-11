package battleengine.action.player;

import battleengine.action.Action;
import battleengine.action.LuckCalculator;
import battleengine.coefficient.CoefficientGateway;
import battleengine.player.Player;

/**
 * Created by RaV on 09.05.15.
 */
public class AttackAction extends Action {
    private final Player owner;
    private final Player target;

    public AttackAction(Player owner, Player target) {
        super();
        this.owner = owner;
        this.target = target;
    }

    @Override
    public int getInitiative() {
        return CoefficientGateway.getInitiative().ofAttackAction();
    }

    @Override
    public void perform() {
        //TODO: add on hit effects
        int damage = (int) (owner.getAttributes().getAttack() * calculateReduction());
        if (isCritical()) {
            damage *= CoefficientGateway.getBase().ofCriticalStrikeMultiplier();
        }
        target.decreaseHP(damage);
    }

    private boolean isCritical() {
        return LuckCalculator.isSuccess(owner.getAttributes().getLuck(), getDifficulty());
    }

    private int getDifficulty() {
        int difficulty = (int) (target.getAttributes().getDexterity() * 1.5) - owner.getAttributes().getDexterity();
        return difficulty>0?difficulty:0;
    }

    private double calculateReduction() {
        double base = target.getAttributes().getDefence() + CoefficientGateway.getBase().ofDamageReduction();
        return 1 - (target.getAttributes().getDefence() / base);
    }

    @Override
    public void finish() {
        //TODO: add finish item effects
        //No finish actions
    }
}
