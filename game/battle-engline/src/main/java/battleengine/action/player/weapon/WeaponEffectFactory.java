package battleengine.action.player.weapon;

import battleengine.action.player.weapon.effects.BaseWeaponEffect;
import battleengine.action.player.weapon.effects.BleedWeaponEffect;
import battleengine.entities.player.Player;
import battleengine.entities.player.components.items.BaseWeapon;
import battleengine.entities.player.components.items.weapons.SharpKnife;
import com.google.common.collect.ImmutableMap;

import java.lang.reflect.Constructor;
import java.util.Map;

public class WeaponEffectFactory
{
    private WeaponEffectFactory() {
    }

    private static final Map<Class<? extends BaseWeapon>, Class<? extends BaseWeaponEffect>> WEAPON_EFFECTS =
            ImmutableMap.<Class<? extends BaseWeapon>, Class<? extends BaseWeaponEffect>> builder()
                    //.put(null, null)//TODO: fill with weapon effects (has everything to be hardcoded?)
                    .put(SharpKnife.class, BleedWeaponEffect.class)
                    .build();

    public static BaseWeaponEffect getWeaponEffect(Player owner, Player target) {
        BaseWeaponEffect effect;
        if(owner.getMainWeapon() != null && WEAPON_EFFECTS.containsKey(owner.getMainWeapon().getClass())) {
            effect = retrieveWeaponEffect(owner, target);
        } else {
            effect = new BaseWeaponEffect(owner, target);
        }
        return effect;
    }

    private static BaseWeaponEffect retrieveWeaponEffect(Player owner, Player target) {
        BaseWeaponEffect effect;
        try {
            Constructor<? extends BaseWeaponEffect> constructor = WEAPON_EFFECTS.get(owner.getMainWeapon().getClass()).getConstructor(Player.class, Player.class);
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
