package game;

import client.model.domain.User;
import game.controller.LobbyController;
import game.controller.LoginController;

public interface Navigation {
    LoginController gotoLogin();
    LobbyController gotoLobby(User logged);
    void close();
}
