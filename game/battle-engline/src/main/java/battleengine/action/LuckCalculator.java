package battleengine.action;

/**
 * Created by RaV on 10.05.15.
 */
public final class LuckCalculator {

    private static final double TO_PERCENTAGE_MULTIPLIER = 100d;

    private LuckCalculator() {
    }

    public static boolean isSuccess(int luck, int difficulty) {
        return checkIfPositive(luck) && getSuccess(luck, difficulty);
    }

    private static boolean checkIfPositive(int luck) {
        return luck > 0;
    }

    private static boolean getSuccess(int luck, int difficulty) {
        return Math.random()*TO_PERCENTAGE_MULTIPLIER < getProbability(luck, difficulty);
    }

    private static double getProbability(int luck, int difficulty) {
        double numerator = luck * TO_PERCENTAGE_MULTIPLIER;
        double denominator = luck + difficulty;
        return numerator / denominator;
    }
}
