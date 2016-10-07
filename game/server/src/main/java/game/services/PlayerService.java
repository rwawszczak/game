package game.services;

import game.model.Player;

public class PlayerService implements PlayerServiceInterface { //TODO: this is mock implementation
    private long currentId = 0;
    @Override
    public Player login(String login, String password) {
        if (login.equals(password)) {
            return new Player(++currentId, login);
        }
        return null;
    }
}
