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
                sessionObject.setAuthenticated(true);
                sessionObject.setPlayer(player);
                outputStream.writeObject(new MessageDTO(MessageDTO.Command.SUCCESS ,"Successfully logged as " + credentials.getLogin()));
            } else {
                sessionObject.setFailedLogins(sessionObject.getFailedLogins()+1);
                if(sessionObject.getFailedLogins()>2) {
                    sessionObject.setOpened(false);
                }
                outputStream.writeObject(new MessageDTO(MessageDTO.Command.ERROR ,"Wrong credentials for user " + credentials.getLogin()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
