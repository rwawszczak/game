package game.server.commands;

import dto.CredentialsDTO;
import dto.MessageDTO;
import game.model.assemblers.UserAssembler;
import game.model.domain.User;
import game.server.ServerData;
import game.server.ServerBroadcasting;
import game.server.session.SessionObject;
import game.services.UserServiceInterface;
import game.services.ServiceProvider;

import java.io.IOException;
import java.io.ObjectOutputStream;

import static dto.CredentialsDTO.Operation.LOGIN;
import static dto.CredentialsDTO.Operation.REGISTER;

public class CredentialsCommand implements BaseCommand<CredentialsDTO> {

    private UserServiceInterface userService = ServiceProvider.getUserService();

    @Override
    public void execute(CredentialsDTO credentials, ObjectOutputStream outputStream, SessionObject sessionObject) {
        if (credentials.getOperation() == LOGIN) {
            handleLogin(credentials, outputStream, sessionObject);
        }
        if (credentials.getOperation() == REGISTER) {
            handleRegistration(credentials, outputStream, sessionObject);
        }
    }

    private void handleLogin(CredentialsDTO credentials, ObjectOutputStream outputStream, SessionObject sessionObject) {
        try {
            long conversationId = credentials.getConversationId();
            try {
                User user = userService.login(credentials.getLogin(), credentials.getPassword());
                if (sessionObject.isAuthenticated()) {
                    handleError(outputStream, conversationId, "User is already authenticated");
                } else {
                    handleSuccessfulLogin(outputStream, sessionObject, user, conversationId);
                }
            } catch (UserServiceInterface.UserDontExistsException | UserServiceInterface.WrongPasswordException e) {
                handleWrongCredentials(outputStream, sessionObject, conversationId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRegistration(CredentialsDTO credentials, ObjectOutputStream outputStream, SessionObject sessionObject) {
        try {
            long conversationId = credentials.getConversationId();
            try {
                userService.register(credentials.getLogin(), credentials.getPassword());
                handleRegistered(outputStream, conversationId);
            } catch (UserServiceInterface.UserAlreadyExistsException e) {
                handleError(outputStream, conversationId, "User already exists");
            } catch (Exception e) {
                handleError(outputStream, conversationId, "Error occurred during registration");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRegistered(ObjectOutputStream outputStream, long conversationId) throws IOException {
        outputStream.writeObject(new MessageDTO.Builder(MessageDTO.Command.SUCCESS)
                .withConversationId(conversationId)
                .withText("User successfully registered")
                .build());
    }

    private void handleError(ObjectOutputStream outputStream, long conversationId, String errorMessage) throws IOException {
        outputStream.writeObject(new MessageDTO.Builder(MessageDTO.Command.ERROR)
                .withConversationId(conversationId)
                .withText(errorMessage)
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
