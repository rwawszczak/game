package game;

import client.ClientAPI;
import client.listeners.SuccessListener;
import client.model.domain.User;
import game.controller.BattlePromptController;
import game.controller.LobbyController;
import game.controller.LoginController;
import game.controller.chat.ChatController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UI extends Application implements Navigation {

    private static final String WINDOW_NAME = "Game [Desktop]";
    private static final String CHAT_NAME = "Chat";
    private static final String PROMPT_NAME = "Prompt";
    private static final String LOGIN_FXML = "/login.fxml";
    private static final String LOBBY_FXML = "/lobby.fxml";
    private static final String CHAT_FXML = "/chat.fxml";
    private static final String BATTLE_PROMPT_FXML = "/battlePrompt.fxml";
    private static final int LOGIN_WIDTH = 350;
    private static final int LOGIN_HEIGHT = 210;
    private static final int LOBBY_WIDTH = 1024;
    private static final int LOBBY_HEIGHT = 768;
    private static final int CHAT_WIDTH = 300;
    private static final int CHAT_HEIGHT = 400;
    private static final int PROMPT_WIDTH = 400;
    private static final int PROMPT_HEIGHT = 200;

    private Stage stage;
    private Stage chatStage;
    private Stage promptStage;
    private ClientAPI client = new ClientAPI();

    @Override
    public void start(Stage primaryStage) throws Exception {
        chatStage = new Stage();
        initDefaultStage(chatStage, CHAT_NAME);
        stage = primaryStage;
        preparePromptStage();
        stage.setOpacity(0.9);
        stage.setTitle(WINDOW_NAME);
        stage.initStyle(StageStyle.UNDECORATED);
        client.setPerformOnDisconnectAction(new OnDisconnectAction());
        gotoLogin();
        stage.show();
        setupOnCloseRequest();
    }

    private void preparePromptStage() {
        promptStage = new Stage();
        promptStage.initModality(Modality.WINDOW_MODAL);
        promptStage.initOwner(stage);
        initDefaultStage(promptStage, PROMPT_NAME);
    }

    private void initDefaultStage(Stage stage, String name) {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setOpacity(0.9);
        stage.setTitle(name);
        stage.initStyle(StageStyle.UNDECORATED);
    }

    public LoginController gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent(LOGIN_FXML, LOGIN_WIDTH, LOGIN_HEIGHT, stage);
            login.setClient(client);
            login.setNavigation(this);
            stage.setResizable(false);
            promptStage.close();
            chatStage.close();
            return login;
        } catch (Exception ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public LobbyController gotoLobby(User logged) {
        try {
            LobbyController lobby = (LobbyController) replaceSceneContent(LOBBY_FXML, LOBBY_WIDTH, LOBBY_HEIGHT, stage);
            lobby.setClient(client);
            lobby.initWithClient();
            lobby.setNavigation(this);
            lobby.setLoggedUser(logged);
            stage.setResizable(true);
            stage.centerOnScreen();
            return lobby;
        } catch (Exception ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ChatController openChat(User selected, User sender) {
        try {
            ChatController chat = (ChatController) replaceSceneContent(CHAT_FXML, CHAT_WIDTH, CHAT_HEIGHT, chatStage);
            chat.setClient(client);
            chat.setSender(sender);
            chat.setNavigation(this);
            chat.setStage(chatStage);
            chat.openConversation(selected);
            chatStage.setResizable(true);
            chatStage.show();
            return chat;
        } catch (Exception ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BattlePromptController showBattlePrompt(User other, long battleId) {
        try {
            BattlePromptController battlePrompt = (BattlePromptController) replaceSceneContent(BATTLE_PROMPT_FXML, PROMPT_WIDTH, PROMPT_HEIGHT, promptStage);
            battlePrompt.setClient(client);
            battlePrompt.setOtherUser(other);
            battlePrompt.setNavigation(this);
            battlePrompt.setStage(promptStage);
            battlePrompt.setBattleId(battleId);
            battlePrompt.postCreateInitialize();
            promptStage.setResizable(false);
            promptStage.show();
            return battlePrompt;
        } catch (Exception ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean isChatShown() {
        return chatStage != null && chatStage.isShowing();
    }

    @Override
    public boolean isBattlePromptShown() {
        return promptStage != null && promptStage.isShowing();
    }

    @Override
    public void close() {
        client.isConnected(getSuccessCloseListener());
    }

    private Object replaceSceneContent(String fxml, int width, int height, Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(UI.class.getResource(fxml));
        Pane page;
        try (InputStream in = UI.class.getResourceAsStream(fxml)) {
            page = loader.load(in);
        }
        Scene scene = new Scene(page, width, height);
        stage.setScene(scene);
        stage.sizeToScene();
        return loader.getController();
    }

    private void setupOnCloseRequest() {
        stage.setOnCloseRequest(event -> client.isConnected(getSuccessCloseListener()));
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void closeStage(){
        Platform.runLater(() -> {
            stage.close();
            chatStage.close();
        });
    }


    private SuccessListener getSuccessCloseListener() {
        return new SuccessListener() {
            @Override
            public void onSuccess() {
                client.disconnect();
                closeStage();
            }

            @Override
            public void onError() {
                closeStage();
            }
        };
    }

    private class OnDisconnectAction implements Runnable {
            @Override
            public void run() {
                Platform.runLater(() -> gotoLogin().setInfo("Lost server connection."));
            }
    }
}
