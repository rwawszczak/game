package battleengine.entities.player.components.items;

import battleengine.entities.player.components.items.types.ArmorType;
import battleengine.entities.player.components.attributes.Attributes;

/**
 * Created by wawszcza on 6/25/2015.
 */
public abstract class BaseArmor extends BaseEquipable{
    private ArmorType type;

    public BaseArmor(Attributes attributes, ArmorType type) {
        super(attributes);
        this.type = type;
    }

    public ArmorType getType() {
        return type;
    }
}
