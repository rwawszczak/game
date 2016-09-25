package battleengine.entities.player.components.items.weapons;

import battleengine.entities.player.components.attributes.Attributes;
import battleengine.entities.player.components.items.BaseWeapon;
import battleengine.entities.player.components.items.types.WeaponType;

public class SharpKnife extends BaseWeapon {
    public SharpKnife() {
        super(new Attributes(0,0,0,0,0,0,0,0), WeaponType.MAINHAND, false);
    }

    @Override
    public void equip() {

    }

    @Override
    public void unequip() {

    }
}
