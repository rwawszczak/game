package game.controller;

import client.listeners.BattleInvitationResponseListener;
import client.model.domain.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BattlePromptController extends BaseController {
    @FXML
    private Label promptLabel;
    @FXML
    private Label youInfo;
    @FXML
    private VBox youBox;
    @FXML
    private Label otherLabel;
    @FXML
    private Label otherInfo;
    @FXML
    private VBox otherBox;
    @FXML
    private Button acceptButton;
    @FXML
    private Button declineButton;
    @FXML
    private Label countdownLabel;

    private ResponseListener listener;

    private Stage stage;
    private User otherUser;
    private Status youStatus;
    private Status otherStatus;
    private long battleId;

    private Integer countdown = 10;

    @FXML
    public void initialize() {
        setYouStatus(Status.PENDING);
        setOtherStatus(Status.PENDING);
        startCountdown();
    }

    public void postCreateInitialize() {
        registerListeners();
        stage.setOnCloseRequest((WindowEvent we) -> unregisterListeners());
    }

    public void setBattleId(long battleId) {
        this.battleId = battleId;
    }

    private void startCountdown() {
        new Thread(() -> {
            while (countdown >= 0) {
                Platform.runLater(() -> countdownLabel.setText(countdown.toString()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countdown--;
            }
            Platform.runLater(() -> {
                countdownLabel.setText("");
                disableButtons();
                if (youStatus == Status.ACCEPTED && youStatus == otherStatus) {
                    startBattle();
                } else {
                    backToLobby();
                }
            });
        }).start();
    }

    private void backToLobby() {
        stage.close();
    }

    private void startBattle() {

    }

    public void setOtherUser(User otherUser) {
        this.otherUser = otherUser;
        otherLabel.setText(otherUser.getName());
        promptLabel.setText("Do you want to battle with " + otherLabel.getText() + "?");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void setStatus(VBox box, Label label, Status status) {
        box.setStyle("prompt-player-color: " + status.getColor() + ";");
        label.setText(status.getMessage());
    }

    private void setYouStatus(Status status) {
        setStatus(youBox, youInfo, status);
        youStatus = status;
    }

    private void setOtherStatus(Status status) {
        setStatus(otherBox, otherInfo, status);
        otherStatus = status;
    }

    private void disableButtons() {
        acceptButton.setDisable(true);
        declineButton.setDisable(true);
    }

    public void accept() {
        setYouStatus(Status.ACCEPTED);
        disableButtons();
        client.acceptBattleInvitation(battleId);
    }

    public void decline() {
        setYouStatus(Status.DECLINED);
        disableButtons();
        client.declineBattleInvitation(battleId);
    }

    private void registerListeners(){
        if(listener == null){
            listener = new ResponseListener();
        }
        client.registerListener(listener);
    }

    private void unregisterListeners(){
        client.unregisterListener(listener);
    }

    private enum Status {
        PENDING("darkOrange", "Pending..."), ACCEPTED("green", "Accepted!"), DECLINED("red", "Declined");
        private final String color;
        private final String message;

        Status(String color, String message) {
            this.color = color;
            this.message = message;
        }

        public String getColor() {
            return color;
        }

        public String getMessage() {
            return message;
        }
    }

    private class ResponseListener extends BattleInvitationResponseListener {
        @Override
        public void handleResponse(long id, User user, boolean hasAccepted) {
            if (battleId == id && otherUser.equals(user)){
                Platform.runLater(()->{
                    if(hasAccepted){
                        setOtherStatus(Status.ACCEPTED);
                    } else {
                        setOtherStatus(Status.DECLINED);
                    }
                });
            }
        }
    }
}
