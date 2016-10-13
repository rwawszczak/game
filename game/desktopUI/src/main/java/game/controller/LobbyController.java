package game.controller;

import client.listeners.UserListListener;
import client.model.domain.User;
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
    private ListView<User> connectedList;

    private ObservableList<User> connectedUsers = FXCollections.observableArrayList();
    private User loggedAs;
    private UserListListener userListListener = new LobbyUserListListener();

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
        connectedUsers.clear();
        client.promptForConnectedUsers();
    }

    public void setLoggedUser(User loggedUser) {
        loggedAs = loggedUser;
        headerLabel.setText("Logged as " + loggedUser.getName());
    }

    private void setupConnectedList() {
        connectedList.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {

                return new ListCell<User>() {

                    @Override
                    protected void updateItem(User p, boolean bln) {
                        super.updateItem(p, bln);
                        if (p != null) {
                            setText(p.getName());
                        }
                    }

                };
            }
        });
        connectedList.setItems(connectedUsers);
    }

    public void initWithClient(){
        registerClientListeners();
        refreshConnected();
    }

    private void unregisterClientListeners() {
        client.unregisterListener(userListListener);
    }

    private void registerClientListeners() {
        client.registerListener(userListListener);
    }

    private void reloadUsers(List<User> users) {
        connectedUsers.clear();
        for (User user : users) {
            if (user.getId() != loggedAs.getId()) {
                connectedUsers.add(user);
            }
        }
    }
    private class LobbyUserListListener extends UserListListener {

        @Override
        public void handleUsers(final List<User> users) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    reloadUsers(users);
                }
            });
        }

        @Override
        public boolean oneTimeOnly() {
            return false;
        }

    }
}
