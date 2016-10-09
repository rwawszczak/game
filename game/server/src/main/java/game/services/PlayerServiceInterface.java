package game.services;


import game.model.domain.Player;

public interface PlayerServiceInterface {
    Player login(String login, String password);
}
