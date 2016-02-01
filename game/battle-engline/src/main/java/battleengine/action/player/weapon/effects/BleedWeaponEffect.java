package battleengine.action.player.weapon.effects;

import battleengine.action.Action;
import battleengine.action.Actions;
import battleengine.action.Targetable;
import battleengine.action.log.LogItem;
import battleengine.entities.player.Player;
import battleengine.gateway.CoefficientGateway;

/**
 * Created by Rafal on 2016-02-01.
 */
public class BleedWeaponEffect extends BaseWeaponEffect {

    public BleedWeaponEffect(Player owner, Player target) {
        super(owner, target);
    }

    @Override
    protected void performOnCrit(LogItem logItem, Actions pushedActions) {
        super.performOnCrit(logItem, pushedActions);
        logItem.setValue(logItem.getValue() + bleedDamage());
        logItem.setDuration(CoefficientGateway.getAbilityValue().ofBleedTurns() - 1);
        pushedActions.add(new BleedEffect(getOwner(), getTarget(), CoefficientGateway.getAbilityValue().ofBleedTurns() - 1));
        bleed();
    }

    private void bleed() {
        getTarget().decreaseHP(bleedDamage());
    }

    private int bleedDamage() {
        return (int) (getTarget().getAttributes().getMaxHP() * CoefficientGateway.getAbilityValue().ofBleedkMultiplier());
    }


    private class BleedEffect extends Action implements Targetable {
        private Player owner;
        private Player target;
        private int turns;

        public BleedEffect(Player owner, Player target, int turns) {
            this.owner = owner;
            this.target = target;
            this.turns = turns;
            setPriority(CoefficientGateway.getPriority().ofBleedEffect());
        }

        @Override
        public Player getOwner() {
            return owner;
        }

        @Override
        public Player getTarget() {
            return target;
        }

        @Override
        public LogItem perform(Actions pushedActions) {
            if (turns > 1) {
                pushedActions.add(this);
            }
            turns--;
            bleed();
            return createLogItem();
        }

        private LogItem createLogItem() {
            final LogItem logItem = new LogItem(this.getClass().getSimpleName());
            logItem.setValue(bleedDamage());
            logItem.setOwner(getOwner());
            logItem.setTarget(getTarget());
            logItem.setSuccess(true);
            logItem.setDuration(turns);
            return logItem;
        }

        @Override
        public void finish() {

        }
    }

}
