package battleengine.action.player.weapon;

import battleengine.action.player.weapon.effects.NoEffect;
import battleengine.entities.player.Player;
import battleengine.entities.player.components.items.BaseWeapon;
import com.google.common.collect.ImmutableMap;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * Created by wawszcza on 7/2/2015.
 */
public class WeaponEffectFactory
{
    private WeaponEffectFactory() {
    }

    private static final Map<Class<? extends BaseWeapon>, Class<? extends WeaponEffect>> WEAPON_EFFECTS =
            ImmutableMap.<Class<? extends BaseWeapon>, Class<? extends WeaponEffect>> builder()
                    //.put(null, null)//TODO: fill with weapon effects
                    .build();

    public static WeaponEffect getWeaponEffect(Player owner, Player target) {
        WeaponEffect effect;
        if(WEAPON_EFFECTS.containsKey(owner.getMainWeapon().getClass())) {
            effect = retrieveWeaponEffect(owner, target);
        } else {
            effect = new NoEffect(owner, target);
        }
        return effect;
    }

    private static WeaponEffect retrieveWeaponEffect(Player owner, Player target) {
        WeaponEffect effect;
        try {
            Constructor<? extends WeaponEffect> constructor = WEAPON_EFFECTS.get(owner.getMainWeapon().getClass()).getConstructor(Player.class, Player.class);
            effect = constructor.newInstance(owner, target);
        } catch (Exception e) {
            throw new WeaponEffectRetrievingException(e);
        }
        return effect;
    }

    public static class WeaponEffectRetrievingException extends RuntimeException{
        public WeaponEffectRetrievingException(Exception e) {
            setStackTrace(e.getStackTrace());
        }
    }
}
