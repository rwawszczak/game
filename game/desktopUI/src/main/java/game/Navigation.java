package game;

import client.model.domain.Player;

public interface Navigation {
    void gotoLogin();
    void gotoLobby(Player logged);
    void close();
}
