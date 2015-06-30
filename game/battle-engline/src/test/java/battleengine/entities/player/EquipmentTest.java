package battleengine.entities.player;

import battleengine.entities.player.components.Equipment;
import battleengine.entities.player.components.items.BaseWeapon;
import battleengine.entities.player.components.items.types.WeaponType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

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
    }
}