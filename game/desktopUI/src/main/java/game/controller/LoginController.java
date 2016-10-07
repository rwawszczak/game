package game.controller;

import client.ClientAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    private PasswordField passwordField;

    @FXML
    private Label infoLabel;

    @FXML
    private GridPane loginLayout;

    private ClientAPI client;

    @FXML
    public void initialize() {
        setupWindowDragging(loginLayout);
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
        client.connect("localhost", 4445);
        if (client.isConnected()) {
            disableLogin(false);
            connectButton.setDisable(true);
            setInfo("Connected to server.");
        } else {
            disableLogin(true);
            setInfo("Server is not responding.");
        }
    }

    @FXML
    public void login() {
        if (client.login(loginField.getText(), passwordField.getText())) {
            setInfo("Successful login.");
            navigation.gotoLobby(loginField.getText());
        } else {
            setInfo("Login failed.");
        }
        if (!client.isConnected()) {
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

}
