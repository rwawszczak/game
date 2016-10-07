package game.controller;

import game.Navigation;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public abstract class BaseController {
    protected Navigation navigation;
    private double xOffset = 0;
    private double yOffset = 0;

    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

    protected void setupWindowDragging(final Pane layout) {
        layout.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = layout.getScene().getWindow().getX() - event.getScreenX();
                yOffset = layout.getScene().getWindow().getY() - event.getScreenY();
            }
        });
        layout.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                layout.getScene().getWindow().setX(event.getScreenX() + xOffset);
                layout.getScene().getWindow().setY(event.getScreenY() + yOffset);
            }
        });
    }
}
