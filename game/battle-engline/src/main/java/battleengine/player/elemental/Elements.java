package battleengine.player.elemental;

import battleengine.coefficient.CoefficientGateway;

/**
 * Created by RaV on 10.05.15.
 */
public enum Elements {
    FIRE(CoefficientGateway.getInitiative().ofFireElement()),
    EARTH(CoefficientGateway.getInitiative().ofEarthElement()),
    WATER(CoefficientGateway.getInitiative().ofWaterElement()),
    WIND(CoefficientGateway.getInitiative().ofWindElement()),
    SHADOW(CoefficientGateway.getInitiative().ofShadowElement()),
    LIGHT(CoefficientGateway.getInitiative().ofLightElement());

    private int baseInitiative;

    Elements(int baseInitiative) {
        this.baseInitiative = baseInitiative;
    }

    public int getBaseInitiative() {
        return baseInitiative;
    }
}
