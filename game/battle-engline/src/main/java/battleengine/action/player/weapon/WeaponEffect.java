package battleengine.action.player.weapon;

import battleengine.action.Performable;
import battleengine.action.Targetable;
import battleengine.entities.BattleEntity;
import battleengine.entities.player.Player;

/**
 * Created by wawszcza on 7/2/2015.
 */
public abstract class WeaponEffect implements Targetable, Performable {
    protected Player owner;
    protected Player target;

    public WeaponEffect(Player owner, Player target) {
        this.owner = owner;
        this.target = target;
    }

    public final BattleEntity getTarget(){
        return target;
    }

    public final BattleEntity getOwner(){
        return owner;
    }
}
