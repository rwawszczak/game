package battleengine.entities.player;

import battleengine.entities.player.components.Equipment;
import battleengine.entities.player.components.items.BaseArmor;
import battleengine.entities.player.components.items.BaseJewelery;
import battleengine.entities.player.components.items.BaseWeapon;
import battleengine.entities.player.components.items.types.ArmorType;
import battleengine.entities.player.components.items.types.JeweleryType;
import battleengine.entities.player.components.items.types.WeaponType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EquipmentTest {
    private Equipment equipment = new Equipment();

    @Test
    public void whenSameTypeEquippedShouldUnequipPrevious() throws Exception {
        BaseWeapon newWeapon = mock(BaseWeapon.class);
        BaseWeapon oldWeapon = mock(BaseWeapon.class);
        when(newWeapon.getType()).thenReturn(WeaponType.MAINHAND);
        when(newWeapon.isTwoHanded()).thenReturn(false);
        when(oldWeapon.getType()).thenReturn(WeaponType.MAINHAND);
        when(oldWeapon.isTwoHanded()).thenReturn(false);
        equipment.setWeapon(oldWeapon);

        equipment.equip(newWeapon);

        verify(oldWeapon).unequip();
        verify(newWeapon).equip();
    }

    @Test
    public void whenDiffrentTypeEquippedShouldUnequipPrevious() throws Exception {
        BaseWeapon newWeapon = mock(BaseWeapon.class);
        BaseWeapon oldWeapon = mock(BaseWeapon.class);
        when(newWeapon.getType()).thenReturn(WeaponType.MAINHAND);
        when(newWeapon.isTwoHanded()).thenReturn(false);
        when(oldWeapon.getType()).thenReturn(WeaponType.OFFHAND);
        when(oldWeapon.isTwoHanded()).thenReturn(false);
        equipment.setWeapon(oldWeapon);

        equipment.equip(newWeapon);

        verify(oldWeapon).getType();
        verifyNoMoreInteractions(oldWeapon);
        verify(newWeapon).equip();
    }

    @Test
    public void whenTwoHandWeaponEquippedShouldUnequipOffHand() throws Exception {
        BaseWeapon newWeapon = mock(BaseWeapon.class);
        BaseWeapon oldWeapon = mock(BaseWeapon.class);
        when(newWeapon.getType()).thenReturn(WeaponType.MAINHAND);
        when(newWeapon.isTwoHanded()).thenReturn(true);
        when(oldWeapon.getType()).thenReturn(WeaponType.OFFHAND);
        when(oldWeapon.isTwoHanded()).thenReturn(false);
        equipment.setWeapon(oldWeapon);

        equipment.equip(newWeapon);

        verify(oldWeapon).unequip();
        verify(newWeapon).equip();
        assertNull(equipment.getEquipped(WeaponType.OFFHAND));
    }

    @Test
    public void whenOffhandEquippedShouldUnequipTwoHandWeapon() throws Exception {
        BaseWeapon newWeapon = mock(BaseWeapon.class);
        BaseWeapon oldWeapon = mock(BaseWeapon.class);
        when(newWeapon.getType()).thenReturn(WeaponType.OFFHAND);
        when(newWeapon.isTwoHanded()).thenReturn(false);
        when(oldWeapon.getType()).thenReturn(WeaponType.MAINHAND);
        when(oldWeapon.isTwoHanded()).thenReturn(true);
        equipment.setWeapon(oldWeapon);

        equipment.equip(newWeapon);

        verify(oldWeapon).unequip();
        verify(newWeapon).equip();
        assertNull(equipment.getEquipped(WeaponType.MAINHAND));
    }

    @Test
    public void whenUnequipShouldRemoveFromEquipment() throws Exception {
        BaseWeapon weapon = mock(BaseWeapon.class);
        BaseArmor armor = mock(BaseArmor.class);
        BaseJewelery jewelery = mock(BaseJewelery.class);
        when(weapon.getType()).thenReturn(WeaponType.MAINHAND);
        when(weapon.isTwoHanded()).thenReturn(false);
        when(armor.getType()).thenReturn(ArmorType.CHEST);
        when(jewelery.getType()).thenReturn(JeweleryType.RING);
        equipment.setWeapon(weapon);
        equipment.setArmor(armor);
        equipment.setJewelery(jewelery);

        equipment.unequip(WeaponType.MAINHAND);
        equipment.unequip(ArmorType.CHEST);
        equipment.unequip(JeweleryType.RING);

        verify(weapon).unequip();
        verify(armor).unequip();
        verify(jewelery).unequip();
        assertNull(equipment.getEquipped(WeaponType.MAINHAND));
        assertNull(equipment.getEquipped(ArmorType.CHEST));
        assertNull(equipment.getEquipped(JeweleryType.RING));
    }
}