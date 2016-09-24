package game.services;


import game.model.Player;

public interface PlayerServiceInterface {
    Player login(String login, String password);
}
