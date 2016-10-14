package game.controller;

import client.listeners.ChatMessageListener;
import client.listeners.UserListListener;
import client.model.domain.User;
import game.controller.chat.ChatController;
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
    private ChatMessageListener chatMessageListener = new ReceivedMessageListener();
    private ChatController chatController;

    @FXML
    public void initialize() {
        setupWindowDragging(headerPanel);
        setupListClick();
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
                    protected void updateItem(User user, boolean empty) {
                        super.updateItem(user, empty);
                        if (empty || user == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(user.getName());
                        }
                    }

                };
            }
        });
        connectedList.setItems(connectedUsers);
    }

    public void initWithClient() {
        registerClientListeners();
        refreshConnected();
    }

    private void unregisterClientListeners() {
        client.unregisterListener(userListListener);
        client.unregisterListener(chatMessageListener);
    }

    private void registerClientListeners() {
        client.registerListener(userListListener);
        client.registerListener(chatMessageListener);
    }

    private void reloadUsers(List<User> users) {
        connectedUsers.clear();
        for (User user : users) {
            if (user.getId() != loggedAs.getId()) {
                connectedUsers.add(user);
            }
        }
    }

    private void setupListClick() {
        connectedList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                final User selected = connectedList.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    openChat(selected);
                }
            }
        });
    }

    private void openChat(User user) {
        if (navigation.isChatShown()) {
            chatController.openConversation(user);
        } else {
            chatController = navigation.openChat(user, loggedAs);
        }
    }

    private class LobbyUserListListener extends UserListListener {

        @Override
        public void handleUsers(final List<User> users) {
            Platform.runLater(() -> reloadUsers(users));
        }

        @Override
        public boolean oneTimeOnly() {
            return false;
        }

    }

    private class ReceivedMessageListener extends ChatMessageListener {
        @Override
        public void handleMessage(User user, String message) {
            Platform.runLater(() ->{
                openChat(user);
                chatController.newMessage(user, message);
            });
        }

        @Override
        public boolean oneTimeOnly() {
            return false;
        }
    }
}
