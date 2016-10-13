package game;

import client.ClientAPI;
import client.listeners.SuccessListener;
import client.model.domain.User;
import game.controller.LobbyController;
import game.controller.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UI extends Application implements Navigation {

    private static final String WINDOW_NAME = "Game [Desktop]";
    private static final String LOGIN_FXML = "/login.fxml";
    private static final String LOBBY_FXML = "/lobby.fxml";
    private static final int LOGIN_WIDTH = 350;
    private static final int LOGIN_HEIGHT = 210;
    private static final int LOBBY_WIDTH = 1024;
    private static final int LOBBY_HEIGHT = 768;

    private Stage stage;
    private ClientAPI client = new ClientAPI();

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setOpacity(0.9);
        stage.setTitle(WINDOW_NAME);
        stage.initStyle(StageStyle.UNDECORATED);
        client.setPerformOnDisconnectAction(new OnDisconnectAction());
        gotoLogin();
        stage.show();
        setupOnCloseRequest();
    }

    public LoginController gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent(LOGIN_FXML, LOGIN_WIDTH, LOGIN_HEIGHT);
            login.setClient(client);
            login.setNavigation(this);
            stage.setResizable(false);
            return login;
        } catch (Exception ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public LobbyController gotoLobby(User logged) {
        try {
            LobbyController lobby = (LobbyController) replaceSceneContent(LOBBY_FXML, LOBBY_WIDTH, LOBBY_HEIGHT);
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
    public void close() {
        client.isConnected(getSuccessCloseListener());
    }

    private Object replaceSceneContent(String fxml, int width, int height) throws Exception {
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
        Platform.runLater(() -> stage.close());
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
