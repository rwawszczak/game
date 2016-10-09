package game.controller;

import client.ClientAPI;
import client.model.domain.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginController extends BaseController {
    @FXML
    private Button closeButton;

    @FXML
    private Button loginButton;

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

    private ClientAPI client;

    @FXML
    public void initialize() {
        setupWindowDragging(loginLayout);
        setupPortField();
        setupKeyListeners();
    }

    @FXML
    public void close() {
        if (client.isConnected()) {
            client.disconnect();
        }
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void connect() {
        setInfo("Connecting...");
        client.connect(serverField.getText(), Integer.parseInt(portField.getText()));
        if (client.isConnected()) {
            disableLogin(false);
            serverField.setDisable(true);
            portField.setDisable(true);
            connectButton.setDisable(true);
            setInfo("Connected to server.");
        } else {
            disableLogin(true);
            setInfo("Server is not responding.");
        }
    }

    @FXML
    public void login() {
        Player logged = client.login(loginField.getText(), passwordField.getText());
        if (logged != null) {
            setInfo("Successful login.");
            navigation.gotoLobby(logged);
        } else {
            setInfo("Login failed.");
        }
        if (!client.isConnected()) {
            serverField.setDisable(false);
            portField.setDisable(false);
            connectButton.setDisable(false);
            disableLogin(true);
            setInfo("Disconnected by server.");
        }
    }

    public void setClient(ClientAPI client) {
        this.client = client;
    }

    private void disableLogin(boolean disable) {
        loginField.setDisable(disable);
        passwordField.setDisable(disable);
        loginButton.setDisable(disable);
    }

    private void setInfo(String info) {
        infoLabel.setText(info);
    }

    private void setupKeyListeners() {
        loginLayout.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ESCAPE){
                    close();
                }
            }
        });
        EventHandler<KeyEvent> enterLoginHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    login();
                }
            }
        };
        loginField.setOnKeyPressed(enterLoginHandler);
        passwordField.setOnKeyPressed(enterLoginHandler);
    }


    private void setupPortField() {
        portField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() > 4){
                    portField.setText(oldValue);
                } else if (!newValue.matches("\\d*")) {
                    portField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

}
