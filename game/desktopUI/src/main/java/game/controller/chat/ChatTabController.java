package game.controller.chat;

import client.model.domain.User;
import game.controller.BaseController;
import game.model.ChatMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class ChatTabController extends BaseController {
    @FXML
    private ListView<ChatMessage> chatText;
    @FXML
    private TextField inputField;

    private ObservableList<ChatMessage> messages = FXCollections.observableArrayList();
    private User sender;
    private User recipient;

    @FXML
    public void initialize() {
        setupEnterListener();
        setupChatText();
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    private void setupChatText() {
        chatText.setSelectionModel(new DisabledSelectionModel<>());
        chatText.setCellFactory(new Callback<ListView<ChatMessage>, ListCell<ChatMessage>>() {
            @Override
            public ListCell<ChatMessage> call(ListView<ChatMessage> param) {

                return new ListCell<ChatMessage>() {

                    @Override
                    protected void updateItem(ChatMessage message, boolean empty) {
                        super.updateItem(message, empty);
                        if (empty || message == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(message.getSender().toUpperCase() + ": " + message.getMessage());
                        }
                    }

                };
            }
        });
        chatText.setItems(messages);
    }

    public void scrollToBottom() {
        Platform.runLater(() -> chatText.scrollTo(messages.size() - 1));
    }

    private void setupEnterListener() {
        EventHandler<KeyEvent> enterHandler = event -> {
            if (event.getCode() == KeyCode.ENTER) {
                send();
            }
        };
        inputField.setOnKeyPressed(enterHandler);
    }

    @FXML
    private void send() {
        client.sendChatMessage(recipient.getId(), inputField.getText());
        ChatMessage message = new ChatMessage(sender.getName(), inputField.getText());
        messages.add(message);
        inputField.clear();
        scrollToBottom();
    }

    public void newMessage(User user, String message) {
        ChatMessage chatMessage = new ChatMessage(user.getName(), message);
        messages.add(chatMessage);
        scrollToBottom();

    }

    private class DisabledSelectionModel<T> extends MultipleSelectionModel<T> {
        DisabledSelectionModel() {
            super.setSelectedIndex(-1);
            super.setSelectedItem(null);
        }

        @Override
        public ObservableList<Integer> getSelectedIndices() {
            return FXCollections.<Integer>emptyObservableList();
        }

        @Override
        public ObservableList<T> getSelectedItems() {
            return FXCollections.<T>emptyObservableList();
        }

        @Override
        public void selectAll() {
        }

        @Override
        public void selectFirst() {
        }

        @Override
        public void selectIndices(int index, int... indicies) {
        }

        @Override
        public void selectLast() {
        }

        @Override
        public void clearAndSelect(int index) {
        }

        @Override
        public void clearSelection() {
        }

        @Override
        public void clearSelection(int index) {
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean isSelected(int index) {
            return false;
        }

        @Override
        public void select(int index) {
        }

        @Override
        public void select(T item) {
        }

        @Override
        public void selectNext() {
        }

        @Override
        public void selectPrevious() {
        }
    }
}
