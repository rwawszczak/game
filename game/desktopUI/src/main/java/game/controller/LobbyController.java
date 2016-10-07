package game.controller;

import client.ClientAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class LobbyController extends BaseController {
    @FXML
    private Button closeButton;

    @FXML
    private Button maximizeButton;

    @FXML
    private GridPane headerPanel;

    @FXML
    private Label headerLabel;

    @FXML
    private ListView connectedList;

    private ClientAPI client;
    private Map<Long, String> connectedPlayers = new HashMap<Long, String>();

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

    @FXML
    public void toggleMaximized() {
        Stage stage = (Stage) maximizeButton.getScene().getWindow();
        if (stage.isMaximized()) {
            stage.setMaximized(false);
            setDragable(true);
            maximizeButton.setText("/\\");
        } else {
            stage.setMaximized(true);
            setDragable(false);
            maximizeButton.setText("\\/");
        }
    }

    @FXML
    public void refreshConnected(){
        connectedPlayers.clear();
        connectedPlayers.putAll(client.getConnectedPlayers());
        connectedList.getItems().clear();
        connectedList.getItems().addAll(connectedPlayers.values());
    }

    public void setUserName(String userName) {
        headerLabel.setText("Logged as " + userName);
    }

    public void setClient(ClientAPI client) {
        this.client = client;
        refreshConnected();
    }
}
