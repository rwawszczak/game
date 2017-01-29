package game.controller;

import client.listeners.BattleInvitationListener;
import client.listeners.ChatMessageListener;
import client.listeners.UserListListener;
import client.model.domain.User;
import game.controller.chat.ChatController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyController extends BaseController {
    private static final String BATTLE_VIEW_FXML = "/battleView.fxml";

    @FXML
    private Button maximizeButton;
    @FXML
    private GridPane headerPanel;
    @FXML
    private Label headerLabel;
    @FXML
    private ListView<User> connectedList;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Pane centerPane;

    private ObservableList<User> connectedUsers = FXCollections.observableArrayList();
    private User loggedAs;
    private UserListListener userListListener = new LobbyUserListListener();
    private ChatMessageListener chatMessageListener = new ReceivedMessageListener();
    private BattleInvListener battleInvListener = new BattleInvListener();
    private ChatController chatController;

    @FXML
    public void initialize() {
        setupWindowDragging(headerPanel);
        setupListClick();
        setupConnectedList();
        setupBattleView();
    }

    private void setupBattleView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(this.getClass().getResource(BATTLE_VIEW_FXML));
        try {
            mainPane.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @FXML
    public void sendBattleRequest() {
        final User selectedUser = connectedList.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            List<User> users = Collections.singletonList(selectedUser);
            client.promptForBattle(users);
        }
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
        client.unregisterListener(battleInvListener);
    }

    private void registerClientListeners() {
        client.registerListener(userListListener);
        client.registerListener(chatMessageListener);
        client.registerListener(battleInvListener);
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
            Platform.runLater(() -> {
                openChat(user);
                chatController.newMessage(user, message);
            });
        }

        @Override
        public boolean oneTimeOnly() {
            return false;
        }
    }


    private class BattleInvListener extends BattleInvitationListener {
        @Override
        public void handleInvitation(long battleId, List<User> users) {
            Platform.runLater(() -> {
                List<User> us = new ArrayList<>(users);
                us.remove(loggedAs);
                if (!navigation.isBattlePromptShown()) {
                    navigation.showBattlePrompt(us.get(0), battleId);
                }
            });
        }
    }
}
