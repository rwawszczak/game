package game.controller.chat;

import client.model.domain.User;
import game.controller.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChatController extends BaseController {
    private static final String CHAT_TAB_FXML = "/chatTab.fxml";
    @FXML
    private TabPane tabPane;
    @FXML
    private AnchorPane anchorPane;

    private Stage stage;

    private User sender;

    private Map<User, ChatWindow> conversations = new HashMap<>();

    @FXML
    public void initialize() {
        setupWindowDragging(tabPane);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void openConversation(User user) {
        if(conversations.containsKey(user)){
            tabPane.getSelectionModel().select(conversations.get(user).tab);
        } else {
            try {
                prepareNewConversationTab(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void prepareNewConversationTab(User user) throws IOException {
        Tab tab;
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(this.getClass().getResource(CHAT_TAB_FXML));
        tab = loader.load();
        ChatTabController controller = loader.getController();
        controller.setClient(client);
        controller.setRecipient(user);
        controller.setSender(sender);
        conversations.put(user, new ChatWindow(tab, controller));
        tab.setText(user.getName());
        tabPane.getTabs().add(tab);
        tab.setOnClosed(event -> {
            conversations.remove(user);
            if (tabPane.getTabs().isEmpty()) {
                stage.close();
            }
        });
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void newMessage(User user, String message){
        openConversation(user);
        conversations.get(user).controller.newMessage(user, message);
    }

    private class ChatWindow{
        private Tab tab;
        private ChatTabController controller;

        public ChatWindow(Tab tab, ChatTabController controller) {
            this.tab = tab;
            this.controller = controller;
        }

    }
}
