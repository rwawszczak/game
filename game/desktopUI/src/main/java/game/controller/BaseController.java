package game.controller;

import client.ClientAPI;
import game.Navigation;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public abstract class BaseController {
    protected Navigation navigation;
    protected ClientAPI client;
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean dragable = true;

    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

    public void setDragable(boolean dragable) {
        this.dragable = dragable;
    }

    public void setClient(ClientAPI client) {
        this.client = client;
    }

    protected void setupWindowDragging(final Pane layout) {
        layout.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(dragable) {
                    xOffset = layout.getScene().getWindow().getX() - event.getScreenX();
                    yOffset = layout.getScene().getWindow().getY() - event.getScreenY();
                }
            }
        });
        layout.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(dragable) {
                    layout.getScene().getWindow().setX(event.getScreenX() + xOffset);
                    layout.getScene().getWindow().setY(event.getScreenY() + yOffset);
                }
            }
        });
    }

    @FXML
    public void close() {
        navigation.close();
    }

}
