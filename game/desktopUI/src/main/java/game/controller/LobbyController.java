package game.controller;

import client.ClientAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LobbyController extends BaseController {
    @FXML
    private Button closeButton;

    @FXML
    private GridPane headerPanel;

    @FXML
    private Label headerLabel;

    private ClientAPI client;

    @FXML
    public void initialize() {
        setupWindowDragging(headerPanel);
    }

    @FXML
    public void close() {
        if (client.isConnected()) {
            client.disconnect();
        }
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void setUserName(String userName) {
        headerLabel.setText("Logged as "+userName);
    }

    public void setClient(ClientAPI client) {
        this.client = client;
    }
}
