package game;

import client.model.domain.Player;
import game.controller.LobbyController;
import game.controller.LoginController;

public interface Navigation {
    LoginController gotoLogin();
    LobbyController gotoLobby(Player logged);
    void close();
}
