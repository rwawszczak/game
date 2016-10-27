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
            long conversationId = credentials.getConversationId();
            if (user != null) {
                if (sessionObject.isAuthenticated()) {
                    handleAlreadyAuthenticated(outputStream, conversationId);
                } else {
                    handleSuccessfulLogin(outputStream, sessionObject, user, conversationId);
                }
            } else {
                handleWrongCredentials(outputStream, sessionObject, conversationId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleAlreadyAuthenticated(ObjectOutputStream outputStream, long conversationId) throws IOException {
        outputStream.writeObject(new MessageDTO.Builder(MessageDTO.Command.ERROR)
                .withConversationId(conversationId)
                .withText("User is already authenticated")
                .build());
    }

    private void handleSuccessfulLogin(ObjectOutputStream outputStream, SessionObject sessionObject, User user, long conversationId) throws IOException {
        sessionObject.setAuthenticated(true);
        sessionObject.setUser(user);
        ServerData.getUsers().put(user.getId(), user);
        outputStream.writeObject(new MessageDTO.Builder(MessageDTO.Command.SUCCESS)
                .withConversationId(conversationId)
                .withText("Successfully logged as " + user.getName())
                .build());
        outputStream.writeObject(UserAssembler.toDTO(user));

        ServerBroadcasting.broadcastConnectedUsers();
    }

    private void handleWrongCredentials(ObjectOutputStream outputStream, SessionObject sessionObject, long conversationId) throws IOException {
        sessionObject.setFailedLogins(sessionObject.getFailedLogins() + 1);
        if (sessionObject.getFailedLogins() > 2) {
            sessionObject.setOpened(false);
            outputStream.writeObject(new MessageDTO.Builder(MessageDTO.Command.DISCONNECTED)
                    .withConversationId(conversationId)
                    .withText("Wrong credentials for user")
                    .build());
        } else {
            outputStream.writeObject(new MessageDTO.Builder(MessageDTO.Command.ERROR)
                    .withConversationId(conversationId)
                    .withText("Wrong credentials for user")
                    .build());
        }
    }

}
