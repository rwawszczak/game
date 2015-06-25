package battleengine.entities.items;

import battleengine.entities.items.types.ArmorType;

/**
 * Created by wawszcza on 6/25/2015.
 */
public abstract class BaseArmor extends BaseItem{
    private ArmorType type;

    public BaseArmor(ArmorType type) {
        this.type = type;
    }

    public ArmorType getType() {
        return type;
    }
}
