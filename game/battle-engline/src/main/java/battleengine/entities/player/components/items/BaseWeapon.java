package battleengine.entities.player.components.items;

import battleengine.entities.player.components.items.types.WeaponType;
import battleengine.entities.player.components.attributes.Attributes;
import battleengine.entities.player.Player;

/**
 * Created by wawszcza on 6/25/2015.
 */
public abstract class BaseWeapon extends BaseEquipable{
    private WeaponType type;
    private boolean twoHanded;

    //TODO: do something about pacage cycle caused by Player dependency
    public abstract void performOnHit(Player owner, Player target, boolean isCritical);

    public abstract void finishOnHit(Player owner, Player target, boolean isCritical);

    public BaseWeapon(Attributes attributes, WeaponType type, boolean twoHanded) {
        super(attributes);
        this.type = type;
        this.twoHanded = twoHanded;
    }

    public WeaponType getType() {
        return type;
    }

    public boolean isTwoHanded() {
        return twoHanded;
    }
}
