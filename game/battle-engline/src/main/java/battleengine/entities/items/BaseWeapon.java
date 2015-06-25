package battleengine.entities.items;

import battleengine.entities.items.types.WeaponType;

/**
 * Created by wawszcza on 6/25/2015.
 */
public abstract class BaseWeapon extends BaseItem{
    private WeaponType type;

    public BaseWeapon(WeaponType type) {
        this.type = type;
    }

    public WeaponType getType() {
        return type;
    }
}
