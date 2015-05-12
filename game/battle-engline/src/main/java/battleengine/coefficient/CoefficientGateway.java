package battleengine.coefficient;

/**
 * Created by RaV on 10.05.15.
 */
public final class CoefficientGateway
{
    private static BaseCoefficientModel baseCoefficientModel = new BaseCoefficientModel();
    private static InitiativeModel initiativeModel = new InitiativeModel();
    private static AbilityValueModel abilityValueModel = new AbilityValueModel();


    private CoefficientGateway()
    {
    }


    public static BaseCoefficientModel getBase()
    {
        return baseCoefficientModel;
    }


    public static InitiativeModel getInitiative()
    {
        return initiativeModel;
    }


    public static AbilityValueModel getAbilityValue()
    {
        return abilityValueModel;
    }
}
