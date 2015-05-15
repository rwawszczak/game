package battleengine.gateway;

import battleengine.entities.Element;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by RaV on 10.05.15.
 */
public class InitiativeModel {
    private static final int ATTACK_ACTION = 250;
    private static final int DEFEND_ACTION = 550;
    private static final int BOOST_ATTACK_ACTION = 260;

    private static final int HEAL_ACTION = 0;

    private static final Map<Element, Integer> ELEMENTS =
            ImmutableMap.<Element, Integer> builder()
                    .put(Element.EARTH, 100)
                    .put(Element.WATER, 200)
                    .put(Element.SHADOW, 300)
                    .put(Element.FIRE, 400)
                    .put(Element.WIND, 500)
                    .put(Element.LIGHT, 600)
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

    public int ofElement(Element element) {
        return ELEMENTS.get(element);
    }

    public int ofElementalAttackAction() {
        return ATTACK_ACTION;
    }
}
