package game.controller.chat;

import client.model.domain.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class ChatTabController {
    @FXML
    private Tab tab;
    @FXML
    private ListView<String> chatText;
    @FXML
    private TextField inputField;
    @FXML
    private Button sendButton;

    private ObservableList<String> messages = FXCollections.observableArrayList();
    private User sender;
    private User recipient;

    @FXML
    public void initialize() {
        setupEnterListener();
        setupChatText();
    }

    private void setupChatText() {
        chatText.setSelectionModel(new DisabledSelectionModel<>());
        chatText.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {

                return new ListCell<String>() {

                    @Override
                    protected void updateItem(String text, boolean empty) {
                        super.updateItem(text, empty);
                        if (empty || text == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(text);
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
        messages.add("You: " + inputField.getText());
        inputField.clear();
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
