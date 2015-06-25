package battleengine.entities.player;

import battleengine.entities.items.types.ArmorType;
import battleengine.entities.items.BaseArmor;
import battleengine.entities.items.BaseItem;
import battleengine.entities.items.BaseJewelery;
import battleengine.entities.items.BaseWeapon;
import battleengine.entities.items.types.JeweleryType;
import battleengine.entities.items.types.WeaponType;

import java.util.ArrayList;
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
        this.bag = new ArrayList<BaseItem>();
    }

    public Equipment(List<BaseItem> items) {
        this.bag = new ArrayList<BaseItem>(items);
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

    public BaseArmor getArmor(ArmorType type) {
        return armor.get(type);
    }

    public void setArmor(ArmorType type, BaseArmor armorPiece) {
        armor.put(type,armorPiece);
    }

    public BaseJewelery getJewelery(JeweleryType type) {
        return jewelery.get(type);
    }

    public void setJewelery(JeweleryType type, BaseJewelery jeweleryPiece) {
        jewelery.put(type,jeweleryPiece);
    }

    public BaseWeapon getWeapon(WeaponType type) {
        return weapons.get(type);
    }

    public void setWeapon(WeaponType type, BaseWeapon weapon) {
        weapons.put(type,weapon);
    }
}
