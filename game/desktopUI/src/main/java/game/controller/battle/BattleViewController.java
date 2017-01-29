package game.controller.battle;

import com.google.common.collect.ImmutableList;
import game.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleViewController extends BaseController {
    private static ImmutableList<String> backgrounds = ImmutableList.of(
            "/backgrounds/arena.jpeg",
            "/backgrounds/cave.jpeg",
            "/backgrounds/desert.jpeg"
    );
    private static ImmutableList<String> icons = ImmutableList.of(
            "/icons/sword.png",
            "/icons/spellbook.png",
            "/icons/shield.png"
    );
    @FXML
    private ImageView background;
    @FXML
    private StackPane battlePane;
    @FXML
    private HBox menuBox;

    @FXML
    public void initialize() {
        battlePane.setOpacity(1);

        background.setImage(new Image(backgrounds.get(new Random().nextInt(backgrounds.size()))));
        background.fitWidthProperty().bind(battlePane.widthProperty());
        background.fitHeightProperty().bind(battlePane.heightProperty());

        List<Button> menuButtons = prepareMenuButtons();

        menuBox.prefHeightProperty().bind(battlePane.heightProperty().multiply(0.1));
        menuBox.translateYProperty().bind(battlePane.heightProperty().multiply(0.9).subtract(5));
        menuBox.setTranslateX(5);
        menuBox.prefWidthProperty().bind(battlePane.widthProperty().multiply(0.7).subtract(10));
        for(Button b : menuButtons){
            b.maxWidthProperty().bind(menuBox.widthProperty().divide(menuButtons.size()));
            b.prefHeightProperty().bind(menuBox.heightProperty());
            b.prefWidthProperty().bind(menuBox.heightProperty());
            b.getStyleClass().setAll("menuButton");
            menuBox.getChildren().add(b);
        }


    }

    private List<Button> prepareMenuButtons() {
        List<Button> menuButtons = new ArrayList<>();
        menuButtons.add(prepareIconButton(icons.get(0)));
        menuButtons.add(prepareIconButton(icons.get(1)));
        menuButtons.add(prepareIconButton(icons.get(2)));
        return menuButtons;
    }

    private Button prepareIconButton(String iconURL) {
        Button button = new Button();
        ImageView image = new ImageView(new Image(iconURL));
        image.getStyleClass().setAll("buttonIcon");
        image.fitHeightProperty().bind(battlePane.heightProperty().multiply(0.1).subtract(5));
        image.fitWidthProperty().bind(battlePane.heightProperty().multiply(0.1).subtract(5));
        button.setGraphic(image);
        return button;
    }

}
