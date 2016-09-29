package game.server.commands;

import dto.CredentialsDTO;
import dto.MessageDTO;
import game.model.Player;
import game.server.session.SessionObject;
import game.services.PlayerServiceInterface;
import game.services.ServiceProvider;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class LoginCommand implements BaseCommand<CredentialsDTO> {

    private PlayerServiceInterface playerService = ServiceProvider.getPlayerService();

    @Override
    public void execute(CredentialsDTO credentials, ObjectOutputStream outputStream, SessionObject sessionObject) {
        try {
            final Player player = playerService.login(credentials.getLogin(), credentials.getPassword());
            if (player != null) {
                if(sessionObject.isAuthenticated()){
                    handleAlreadyAuthenticated(outputStream);
                } else {
                    handleSuccessfulLogin(outputStream, sessionObject, player);
                }
            } else {
                handleWrongCredentials(outputStream, sessionObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleAlreadyAuthenticated(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(new MessageDTO(MessageDTO.Command.ERROR ,"User is already authenticated"));
    }

    private void handleSuccessfulLogin(ObjectOutputStream outputStream, SessionObject sessionObject, Player player) throws IOException {
        sessionObject.setAuthenticated(true);
        sessionObject.setPlayer(player);
        outputStream.writeObject(new MessageDTO(MessageDTO.Command.SUCCESS, "Successfully logged as"));
    }

    private void handleWrongCredentials(ObjectOutputStream outputStream, SessionObject sessionObject) throws IOException {
        sessionObject.setFailedLogins(sessionObject.getFailedLogins()+1);
        if(sessionObject.getFailedLogins()>2) {
            sessionObject.setOpened(false);
        }
        outputStream.writeObject(new MessageDTO(MessageDTO.Command.ERROR ,"Wrong credentials for user"));
    }

}
