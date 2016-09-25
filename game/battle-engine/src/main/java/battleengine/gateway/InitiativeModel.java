package battleengine.gateway;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class InitiativeModel {
    private static final int ATTACK_ACTION = 250;
    private static final int DEFEND_ACTION = 550;
    private static final int BOOST_ATTACK_ACTION = 260;

    private static final int HEAL_ACTION = 0;

    private static final Map<String, Integer> ELEMENTS =
            ImmutableMap.<String, Integer> builder()
                    .put("EARTH", 100)
                    .put("WATER", 200)
                    .put("SHADOW", 300)
                    .put("FIRE", 400)
                    .put("WIND", 500)
                    .put("LIGHT", 600)
                            .build();

    public int ofAttackAction() {
        return ATTACK_ACTION;
    }

    public int ofDefendAction() {
        return DEFEND_ACTION;
    }

    public int ofHealAction() {
        return HEAL_ACTION;
    }

    public int ofBoostAttackAction() {
        return BOOST_ATTACK_ACTION;
    }

    public int ofElement(String element) {
        return ELEMENTS.get(element);
    }

    public int ofElementalAttackAction() {
        return ATTACK_ACTION;
    }
}
