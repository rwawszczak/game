package game.services;

import game.model.Player;

public class PlayerService implements PlayerServiceInterface {

    @Override
    public Player login(String login, String password) {
        if (login.equals(password)) {
            return new Player(login);
        }
        return null;
    }
}
