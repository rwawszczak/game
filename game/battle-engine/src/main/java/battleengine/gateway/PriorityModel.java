package battleengine.gateway;

public class PriorityModel {
    private static final int BOOST_ATTACK_ACTION = 1;
    private static final int BOOST_BLEED_EFFECT = -1;

    public int ofBoostAttackAction() {
        return BOOST_ATTACK_ACTION;
    }
    public int ofBleedEffect() {
        return BOOST_BLEED_EFFECT;
    }
}
