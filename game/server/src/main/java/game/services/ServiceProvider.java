package game.services;

public class ServiceProvider {
    private static UserServiceInterface userService = new UserService();
    private static BattleServiceInterface battleService = new BattleService();

    private ServiceProvider(){
    }

    public static UserServiceInterface getUserService() {
        return userService;
    }

    public static BattleServiceInterface getBattleService() {
        return battleService;
    }
}
