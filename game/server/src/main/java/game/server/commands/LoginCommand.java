package game.server.commands;

import dto.CredentialsDTO;
import dto.MessageDTO;
import dto.PlayerDTO;
import game.model.assemblers.PlayerAssembler;
import game.model.domain.Player;
import game.server.ServerData;
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
                if (sessionObject.isAuthenticated()) {
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
        outputStream.writeObject(new MessageDTO.Builder(MessageDTO.Command.ERROR)
                .withText("User is already authenticated")
                .build());
    }

    private void handleSuccessfulLogin(ObjectOutputStream outputStream, SessionObject sessionObject, Player player) throws IOException {
        sessionObject.setAuthenticated(true);
        sessionObject.setPlayer(player);
        ServerData.getPlayers().put(player.getId(), player);
        outputStream.writeObject(new MessageDTO.Builder(MessageDTO.Command.SUCCESS)
                .withText("Successfully logged as")
                .build());
        outputStream.writeObject(PlayerAssembler.toDTO(player));
    }

    private void handleWrongCredentials(ObjectOutputStream outputStream, SessionObject sessionObject) throws IOException {
        sessionObject.setFailedLogins(sessionObject.getFailedLogins() + 1);
        if (sessionObject.getFailedLogins() > 2) {
            sessionObject.setOpened(false);
        }
        outputStream.writeObject(new MessageDTO.Builder(MessageDTO.Command.ERROR)
                .withText("Wrong credentials for user")
                .build());
    }

}
