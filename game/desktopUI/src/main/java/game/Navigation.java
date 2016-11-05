package game;

import client.model.domain.User;
import game.controller.BattlePromptController;
import game.controller.chat.ChatController;
import game.controller.LobbyController;
import game.controller.LoginController;

public interface Navigation {
    LoginController gotoLogin();
    LobbyController gotoLobby(User logged);
    ChatController openChat(User selected, User sender);
    BattlePromptController showBattlePrompt(User other);
    boolean isChatShown();
    boolean isBattlePromptShown();
    void close();
}
