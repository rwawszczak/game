package battleengine.action.player.weapon;

import battleengine.action.player.weapon.effects.NoEffect;
import battleengine.entities.player.Player;
import battleengine.entities.player.components.items.BaseWeapon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeaponEffectFactoryTest {
    @Test
    public void whenWeaponEffectNotRegisteredShouldReturnNoEffect() throws Exception {
        Player owner = mock(Player.class);
        Player target = mock(Player.class);
        BaseWeapon weapon = mock(BaseWeapon.class);
        when(owner.getMainWeapon()).thenReturn(weapon);

        WeaponEffect effect = WeaponEffectFactory.getWeaponEffect(owner, target);

        assertEquals(owner, effect.getOwner());
        assertEquals(target, effect.getTarget());
        assertTrue(NoEffect.class.isAssignableFrom(effect.getClass()));
    }
}