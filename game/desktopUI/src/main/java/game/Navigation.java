package game;

import client.model.domain.User;
import game.controller.battle.BattlePromptController;
import game.controller.chat.ChatController;
import game.controller.LobbyController;
import game.controller.LoginController;

public interface Navigation {
    LoginController gotoLogin();
    LobbyController gotoLobby(User logged);
    ChatController openChat(User selected, User sender);
    BattlePromptController showBattlePrompt(User other, long battleId);
    boolean isChatShown();
    boolean isBattlePromptShown();
    void close();
}
