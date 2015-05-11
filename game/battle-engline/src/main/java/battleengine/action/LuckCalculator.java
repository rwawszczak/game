package battleengine.action;

/**
 * Created by RaV on 10.05.15.
 */
public class LuckCalculator {
    private LuckCalculator() {
    }

    public static boolean isSuccess(int luck, int difficulty) {
        return luck > 0 && Math.random() * 100 < (luck * 100) / (luck + difficulty);
    }
}
