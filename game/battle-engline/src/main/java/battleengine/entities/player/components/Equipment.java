package battleengine.entities.player.components;

import battleengine.entities.player.components.items.BaseEquipable;
import battleengine.entities.player.components.items.types.ArmorType;
import battleengine.entities.player.components.items.BaseArmor;
import battleengine.entities.player.components.items.BaseItem;
import battleengine.entities.player.components.items.BaseJewelery;
import battleengine.entities.player.components.items.BaseWeapon;
import battleengine.entities.player.components.items.types.JeweleryType;
import battleengine.entities.player.components.items.types.WeaponType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wawszcza on 6/25/2015.
 */
public class Equipment {
    private List<BaseItem> bag;

    private Map<ArmorType, BaseArmor> armor;
    private Map<JeweleryType, BaseJewelery> jewelery;
    private Map<WeaponType, BaseWeapon> weapons;

    public Equipment() {
        this(new ArrayList<BaseItem>());
    }

    public Equipment(List<BaseItem> items) {
        this.bag = new ArrayList<BaseItem>(items);
        armor = new HashMap<ArmorType, BaseArmor>();
        jewelery = new HashMap<JeweleryType, BaseJewelery>();
        weapons = new HashMap<WeaponType, BaseWeapon>();
    }

    public void putToBag(BaseItem item){
        bag.add(item);
    }

    public void putToBag(List<BaseItem> items){
        bag.addAll(items);
    }

    public void removeFromBag(BaseItem item){
        bag.remove(item);
    }

    public void removeFromBag(int index){
        bag.remove(index);
    }

    public BaseItem getFromBag(int index){
        return bag.get(index);
    }

    public List<BaseItem> getFromBag(){
        return new ArrayList<BaseItem>(bag);
    }

    public int getTotalWeight(){
        int weight = 0;
        for(BaseItem item : getAllEquippedItems())
           weight += item.getWeight();
        for(BaseItem item : bag)
            weight += item.getWeight();
        return weight;
    }

    public List<BaseItem> getAllEquippedItems(){
        List<BaseItem> equipped = new ArrayList<BaseItem>();
        for(ArmorType key :  armor.keySet())
            equipped.add(armor.get(key));
        for(JeweleryType key :  jewelery.keySet())
            equipped.add(jewelery.get(key));
        for(WeaponType key :  weapons.keySet())
            equipped.add(weapons.get(key));

        return equipped;
    }

    public BaseArmor getEquipped(ArmorType type) {
        return armor.get(type);
    }

    public void setArmor(BaseArmor armorPiece) {
        armor.put(armorPiece.getType(),armorPiece);
    }

    public BaseJewelery getEquipped(JeweleryType type) {
        return jewelery.get(type);
    }

    public void setJewelery(BaseJewelery jeweleryPiece) {
        jewelery.put(jeweleryPiece.getType(),jeweleryPiece);
    }

    public BaseWeapon getEquipped(WeaponType type) {
        return weapons.get(type);
    }

    public void setWeapon(BaseWeapon weapon) {
        weapons.put(weapon.getType(),weapon);
    }

    public void equip(BaseEquipable equipment){
        if(BaseArmor.class.isAssignableFrom(equipment.getClass())){
            equipArmor((BaseArmor) equipment);
        } else if(BaseJewelery.class.isAssignableFrom(equipment.getClass())) {
            equipJewelery((BaseJewelery) equipment);
        } else if(BaseWeapon.class.isAssignableFrom(equipment.getClass())) {
            equipWeapon((BaseWeapon) equipment);
        }
    }

    public void unequip(WeaponType type){
        BaseWeapon weapon = weapons.get(type);
        if(weapon != null){
            weapon.unequip();
            weapons.remove(type);
        }
    }

    public void unequip(ArmorType type){
        BaseArmor armorPiece = armor.get(type);
        if(armorPiece != null){
            armorPiece.unequip();
            armor.remove(type);
        }
    }

    public void unequip(JeweleryType type){
        BaseJewelery jeweleryPiece = jewelery.get(type);
        if(jeweleryPiece != null){
            jeweleryPiece.unequip();
            jewelery.remove(type);
        }
    }

    private void equipWeapon(BaseWeapon equipment) {
        BaseWeapon current = equipment;
        BaseWeapon previous = weapons.get(current.getType());
        reequip(current, previous);
        if(current.getType()== WeaponType.MAINHAND && weapons.get(WeaponType.OFFHAND) != null && current.isTwoHanded()) {
            weapons.get(WeaponType.OFFHAND).unequip();
            weapons.remove(WeaponType.OFFHAND);
        }
        if(current.getType()==WeaponType.OFFHAND && weapons.get(WeaponType.MAINHAND) != null && weapons.get(WeaponType.MAINHAND).isTwoHanded()) {
            weapons.get(WeaponType.MAINHAND).unequip();
            weapons.remove(WeaponType.MAINHAND);
        }
        setWeapon(current);
    }

    private void equipJewelery(BaseJewelery equipment) {
        BaseJewelery current = equipment;
        BaseJewelery previous = jewelery.get(current.getType());
        reequip(current, previous);
        setJewelery(current);
    }

    private void equipArmor(BaseArmor equipment) {
        BaseArmor current = equipment;
        BaseArmor previous = armor.get(current.getType());
        reequip(current, previous);
        setArmor(current);
    }

    private void reequip(BaseEquipable current, BaseEquipable previous) {
        if (previous != null) previous.unequip();
        current.equip();
    }
}
