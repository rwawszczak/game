package battleengine.entities;

import battleengine.gateway.CoefficientGateway;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by RaV on 10.05.15.
 */
public enum Element
{
    FIRE, EARTH, WATER, WIND, SHADOW, LIGHT;

    private static final Map<Element, Element> STRONG_AGAINST =
            ImmutableMap.<Element, Element> builder()
                    .put(EARTH, WATER)
                    .put(WATER, FIRE)
                    .put(SHADOW, EARTH)
                    .put(FIRE, WIND)
                    .put(WIND, LIGHT)
                    .put(LIGHT, SHADOW)
                    .build();
    private static final Map<Element, Element> WEAK_AGAINST =
            ImmutableMap.<Element, Element> builder()
                    .put(WATER, EARTH)
                    .put(FIRE, WATER)
                    .put(EARTH, SHADOW)
                    .put(WIND, FIRE)
                    .put(LIGHT, WIND)
                    .put(SHADOW, LIGHT)
                    .build();

    public double effectiveAgainst(Element element){
        double effectiveness = 1;
        if(this == element)effectiveness += 2* CoefficientGateway.getBase().ofElementalTypeResistanceCoefficient();
        else{
            if(isStrongAgainst(element)) effectiveness += CoefficientGateway.getBase().ofElementalTypeResistanceCoefficient();
            if(isWeakAgainst(element)) effectiveness -= CoefficientGateway.getBase().ofElementalTypeResistanceCoefficient();
        }
        return effectiveness;
    }

    private boolean isStrongAgainst(Element element){
        return STRONG_AGAINST.get(this) == element;
    }

    private boolean isWeakAgainst(Element element){
        return WEAK_AGAINST.get(this) == element;
    }


}
