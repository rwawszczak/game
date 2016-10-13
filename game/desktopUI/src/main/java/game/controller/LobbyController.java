package game.controller;

import client.listeners.PlayerListListener;
import client.model.domain.Player;
import javafx.application.Platform;
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

import java.util.List;

public class LobbyController extends BaseController {
    @FXML
    private Button maximizeButton;

    @FXML
    private GridPane headerPanel;

    @FXML
    private Label headerLabel;

    @FXML
    private ListView<Player> connectedList;

    private ObservableList<Player> connectedPlayers = FXCollections.observableArrayList();
    private Player loggedAs;
    private PlayerListListener playerListListener = new LobbyPlayerListListener();

    @FXML
    public void initialize() {
        setupWindowDragging(headerPanel);
        setupConnectedList();
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
        client.promptForConnectedPlayers();
    }

    public void setLoggedPlayer(Player loggedPlayer) {
        loggedAs = loggedPlayer;
        headerLabel.setText("Logged as " + loggedPlayer.getName());
    }

    private void setupConnectedList() {
        connectedList.setCellFactory(new Callback<ListView<Player>, ListCell<Player>>() {
            @Override
            public ListCell<Player> call(ListView<Player> param) {

                return new ListCell<Player>() {

                    @Override
                    protected void updateItem(Player p, boolean bln) {
                        super.updateItem(p, bln);
                        if (p != null) {
                            setText(p.getName());
                        }
                    }

                };
            }
        });
        connectedList.setItems(connectedPlayers);
    }

    public void initWithClient(){
        registerClientListeners();
        refreshConnected();
    }

    private void unregisterClientListeners() {
        client.unregisterListener(playerListListener);
    }

    private void registerClientListeners() {
        client.registerListener(playerListListener);
    }

    private void reloadPlayers(List<Player> players) {
        connectedPlayers.clear();
        for (Player player : players) {
            if (player.getId() != loggedAs.getId()) {
                connectedPlayers.add(player);
            }
        }
    }
    private class LobbyPlayerListListener extends PlayerListListener {

        @Override
        public void handlePlayers(final List<Player> players) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    reloadPlayers(players);
                }
            });
        }

        @Override
        public boolean oneTimeOnly() {
            return false;
        }

    }
}
