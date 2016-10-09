package game.controller;

import game.Navigation;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public abstract class BaseController {
    protected Navigation navigation;
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean dragable = true;

    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

    public void setDragable(boolean dragable) {
        this.dragable = dragable;
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
}
