package battleengine.gateway;

public final class CoefficientGateway
{
    private static BaseCoefficientModel baseCoefficientModel = new BaseCoefficientModel();
    private static InitiativeModel initiativeModel = new InitiativeModel();
    private static AbilityValueModel abilityValueModel = new AbilityValueModel();
    private static PriorityModel priorityModel = new PriorityModel();
    private static ManaCostModel manaCostModel = new ManaCostModel();
    private static LogValueModel logValueModel = new LogValueModel();


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

    public static PriorityModel getPriority() {
        return priorityModel;
    }

    public static ManaCostModel getManaCost() {
        return manaCostModel;
    }

    public static LogValueModel getLogValue() { return logValueModel; }
}
