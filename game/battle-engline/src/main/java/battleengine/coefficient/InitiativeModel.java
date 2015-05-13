package battleengine.coefficient;

/**
 * Created by RaV on 10.05.15.
 */
public class InitiativeModel {
    private static final int ATTACK_ACTION = 250;
    private static final int DEFEND_ACTION = 550;
    private static final int BOOST_ATTACK_ACTION = 260;

    private static final int HEAL_ACTION = 0;

    private static final int FIRE_ELEMENT = 400;
    private static final int EARTH_ELEMENT = 100;
    private static final int WATER_ELEMENT = 200;
    private static final int WIND_ELEMENT = 500;
    private static final int SHADOW_ELEMENT = 300;
    private static final int LIGHT_ELEMENT = 600;

    public static int ofAttackAction() {
        return ATTACK_ACTION;
    }

    public static int ofDefendAction() {
        return DEFEND_ACTION;
    }

    public static int ofHealAction() {
        return HEAL_ACTION;
    }

    public int ofBoostAttackAction() {
        return BOOST_ATTACK_ACTION;
    }

    public static int ofFireElement() {
        return FIRE_ELEMENT;
    }

    public static int ofEarthElement() {
        return EARTH_ELEMENT;
    }

    public static int ofWaterElement() {
        return WATER_ELEMENT;
    }

    public static int ofWindElement() {
        return WIND_ELEMENT;
    }

    public static int ofShadowElement() {
        return SHADOW_ELEMENT;
    }

    public static int ofLightElement() {
        return LIGHT_ELEMENT;
    }
}
