package game.server.commands;

import dto.CredentialsDTO;
import dto.MessageDTO;
import game.model.assemblers.UserAssembler;
import game.model.domain.User;
import game.server.ServerData;
import game.server.session.ServerBroadcasting;
import game.server.session.SessionObject;
import game.services.UserServiceInterface;
import game.services.ServiceProvider;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class LoginCommand implements BaseCommand<CredentialsDTO> {

    private UserServiceInterface userService = ServiceProvider.getUserService();

    @Override
    public void execute(CredentialsDTO credentials, ObjectOutputStream outputStream, SessionObject sessionObject) {
        try {
            final User user = userService.login(credentials.getLogin(), credentials.getPassword());
            if (user != null) {
                if (sessionObject.isAuthenticated()) {
                    handleAlreadyAuthenticated(outputStream);
                } else {
                    handleSuccessfulLogin(outputStream, sessionObject, user);
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

    private void handleSuccessfulLogin(ObjectOutputStream outputStream, SessionObject sessionObject, User user) throws IOException {
        sessionObject.setAuthenticated(true);
        sessionObject.setUser(user);
        ServerData.getUsers().put(user.getId(), user);
        outputStream.writeObject(new MessageDTO.Builder(MessageDTO.Command.SUCCESS)
                .withText("Successfully logged as" + user.getName())
                .build());
        outputStream.writeObject(UserAssembler.toDTO(user));

        ServerBroadcasting.broadcastConnectedUsers();
    }

    private void handleWrongCredentials(ObjectOutputStream outputStream, SessionObject sessionObject) throws IOException {
        sessionObject.setFailedLogins(sessionObject.getFailedLogins() + 1);
        if (sessionObject.getFailedLogins() > 2) {
            sessionObject.setOpened(false);
            outputStream.writeObject(new MessageDTO.Builder(MessageDTO.Command.DISCONNECTED)
                    .withText("Wrong credentials for user")
                    .build());
        } else {
            outputStream.writeObject(new MessageDTO.Builder(MessageDTO.Command.ERROR)
                    .withText("Wrong credentials for user")
                    .build());
        }
    }

}
