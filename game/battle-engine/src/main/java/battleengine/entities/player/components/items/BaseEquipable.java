package battleengine.entities.player.components.items;

import battleengine.entities.player.components.attributes.Attributes;

public abstract class BaseEquipable extends BaseItem{
    private Attributes attributes;

    public BaseEquipable(Attributes attributes) {
        this.attributes = attributes;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public abstract void equip();
    public abstract void unequip();
}
