package battleengine.action.player.weapon.effects;

import battleengine.action.Actions;
import battleengine.action.log.LogItem;
import battleengine.action.player.weapon.WeaponEffect;
import battleengine.entities.player.Player;

/**
 * Created by wawszcza on 7/2/2015.
 */
public final class NoEffect extends WeaponEffect {
    public NoEffect(Player owner, Player target) {
        super(owner, target);
    }

    @Override
    public LogItem perform(Actions pushedActions) {
        return null;
    }

    @Override
    public void finish() {
    }
}
