package battleengine.entities.player.components.items;

import battleengine.entities.player.components.items.types.JeweleryType;
import battleengine.entities.player.components.attributes.Attributes;

public abstract class BaseJewelery extends BaseEquipable{
    private JeweleryType type;

    public BaseJewelery(Attributes attributes, JeweleryType type) {
        super(attributes);
        this.type = type;
    }

    public JeweleryType getType() {
        return type;
    }
}
