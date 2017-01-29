package game.controller;

import client.listeners.DisconnectedListener;
import client.listeners.LoginListener;
import client.listeners.SuccessListener;
import client.model.domain.User;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class LoginController extends BaseController {
    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button connectButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField serverField;

    @FXML
    private TextField portField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label infoLabel;

    @FXML
    private GridPane loginLayout;
    private DisconnectedListener disconnectedListener = new LoginDisconnectedListener();


    @FXML
    public void initialize() {
        setupWindowDragging(loginLayout);
        setupPortField();
        setupKeyListeners();
    }

    @FXML
    public void connect() {
        setInfo("Connecting...");
        client.connect(serverField.getText(), Integer.parseInt(portField.getText()));
        client.isConnected(new SuccessListener() {
            @Override
            public void onSuccess(String message) {
                Platform.runLater(() -> {
                    disableLogin(false);
                    serverField.setDisable(true);
                    portField.setDisable(true);
                    connectButton.setDisable(true);
                    setInfo("Connected to server.");
                    client.registerListener(disconnectedListener);
                });
            }

            @Override
            public void onError(String message) {
                Platform.runLater(() -> {
                    disableLogin(true);
                    setInfo("Server is not responding.");
                });
            }
        });
    }

    @FXML
    public void login() {
        client.login(loginField.getText(), passwordField.getText(), new LoginListener() {
            @Override
            public void handleUser(final User user) {
                Platform.runLater(() -> {
                    if (user != null) {
                        setInfo("Successful login.");
                        client.unregisterListener(disconnectedListener);
                        navigation.gotoLobby(user);
                    } else {
                        setInfo("Login failed.");
                    }
                });
            }
        });
    }

    @FXML
    public void register() {
        client.register(loginField.getText(), passwordField.getText(), new SuccessListener() {
            @Override
            public void onSuccess(String message) {
                Platform.runLater(() ->
                        setInfo(message));
            }

            @Override
            public void onError(String message) {
                Platform.runLater(() ->
                        setInfo(message));
            }
        });
    }

    private void disableLogin(boolean disable) {
        loginField.setDisable(disable);
        passwordField.setDisable(disable);
        loginButton.setDisable(disable);
        registerButton.setDisable(disable);
    }

    public void setInfo(String info) {
        infoLabel.setText(info);
    }

    private void setupKeyListeners() {
        loginLayout.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                close();
            }
        });
        EventHandler<KeyEvent> enterLoginHandler = event -> {
            if (event.getCode() == KeyCode.ENTER) {
                login();
            }
        };
        loginField.setOnKeyPressed(enterLoginHandler);
        passwordField.setOnKeyPressed(enterLoginHandler);
    }


    private void setupPortField() {
        portField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 4) {
                portField.setText(oldValue);
            } else if (!newValue.matches("\\d*")) {
                portField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private class LoginDisconnectedListener extends DisconnectedListener {
        @Override
        public void onDisconnected() {
            System.out.println("handling disconnect.");
            Platform.runLater(() -> {
                serverField.setDisable(false);
                portField.setDisable(false);
                connectButton.setDisable(false);
                disableLogin(true);
                setInfo("Disconnected by server.");
            });
        }
    }
}
