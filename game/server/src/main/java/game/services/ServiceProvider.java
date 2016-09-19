package game.services;

public class ServiceProvider {
    private static PlayerServiceInterface playerService = new PlayerService();
    private static BattleServiceInterface battleService = new BattleService();

    private ServiceProvider(){
    }

    public static PlayerServiceInterface getPlayerService() {
        return playerService;
    }

    public static BattleServiceInterface getBattleService() {
        return battleService;
    }
}
