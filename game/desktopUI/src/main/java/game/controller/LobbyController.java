package game.controller;

import client.ClientAPI;
import client.model.domain.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private ListView<Player> connectedList;

    private ClientAPI client;
    private ObservableList<Player> connectedPlayers = FXCollections.observableArrayList();
    private Player loggedAs;

    @FXML
    public void initialize() {
        setupWindowDragging(headerPanel);
        setupConnectedList();
    }

    private void setupConnectedList() {
        connectedList.setCellFactory(new Callback<ListView<Player>, ListCell<Player>>() {
            @Override
            public ListCell<Player> call(ListView<Player> param) {
                ListCell<Player> cell = new ListCell<Player>(){

                    @Override
                    protected void updateItem(Player p, boolean bln) {
                        super.updateItem(p, bln);
                        if (p != null) {
                            setText(p.getName());
                        }
                    }

                };

                return cell;
            }
        });
        connectedList.setItems(connectedPlayers);
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
            maximizeButton.setText("↑");
        } else {
            stage.setMaximized(true);
            setDragable(false);
            maximizeButton.setText("↓");
        }
    }

    @FXML
    public void refreshConnected() {
        connectedPlayers.clear();
        connectedPlayers.addAll(client.getConnectedPlayers());
    }

    public void setLoggedPlayer(Player loggedPlayer) {
        loggedAs = loggedPlayer;
        headerLabel.setText("Logged as " + loggedPlayer.getName());
    }

    public void setClient(ClientAPI client) {
        this.client = client;
        refreshConnected();
    }
}
