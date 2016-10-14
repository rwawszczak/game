package game.controller.chat;

import client.model.domain.User;
import game.controller.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChatController extends BaseController {
    @FXML
    private TabPane tabPane;
    @FXML
    private AnchorPane anchorPane;

    private Stage stage;

    @FXML
    public void initialize() {
        setupWindowDragging(tabPane);
    }

    private Map<User, Tab> conversations = new HashMap<>();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void openConversation(User user) {
        if(conversations.containsKey(user)){
            tabPane.getSelectionModel().select(conversations.get(user));
        } else {
            Tab tab;
            try {
                tab = FXMLLoader.load(this.getClass().getResource("/chatTab.fxml"));
            } catch (IOException e) {
                tab = new Tab();
                e.printStackTrace();
            }
            conversations.put(user, tab);
            tab.setText(user.getName());
            tabPane.getTabs().add(tab);
            tab.setOnClosed(event -> {
                conversations.remove(user);
                if (tabPane.getTabs().isEmpty()) {
                    stage.close();
                }
            });
        }
    }
}
