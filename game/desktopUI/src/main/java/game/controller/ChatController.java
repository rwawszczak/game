package game.controller;

import client.model.domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addConversation(User user) {
        final Tab tab = new Tab(user.getName());
        tabPane.getTabs().add(tab);
        tab.setOnClosed(event -> {
            if(tabPane.getTabs().isEmpty()){
                stage.close();
            }
        });
    }
}
