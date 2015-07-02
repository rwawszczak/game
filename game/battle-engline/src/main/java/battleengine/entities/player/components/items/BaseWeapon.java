package battleengine.entities.player.components.items;

import battleengine.entities.player.components.attributes.Attributes;
import battleengine.entities.player.components.items.types.WeaponType;

/**
 * Created by wawszcza on 6/25/2015.
 */
public abstract class BaseWeapon extends BaseEquipable{
    private WeaponType type;
    private boolean twoHanded;

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
