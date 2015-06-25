package battleengine.entities.items;

import battleengine.entities.items.types.JeweleryType;

/**
 * Created by wawszcza on 6/25/2015.
 */
public abstract class BaseJewelery extends BaseItem{
    private JeweleryType type;

    public BaseJewelery(JeweleryType type) {
        this.type = type;
    }

    public JeweleryType getType() {
        return type;
    }
}
