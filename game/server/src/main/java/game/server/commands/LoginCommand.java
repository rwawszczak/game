package game.server.commands;

import dto.CredentialsDTO;
import dto.TextMessageDTO;
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
                outputStream.writeObject(new TextMessageDTO("Successfully logged as " + credentials.getLogin()));
            } else {
                sessionObject.setOpened(false);
                outputStream.writeObject(new TextMessageDTO("Wrong credentials for user " + credentials.getLogin()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
