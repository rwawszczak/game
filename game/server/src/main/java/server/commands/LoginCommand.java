package server.commands;

import dto.CredentialsDTO;
import dto.TextMessageDTO;
import server.session.SessionObject;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class LoginCommand implements BaseCommand<CredentialsDTO> {

    @Override
    public void execute(CredentialsDTO credentials, ObjectOutputStream outputStream, SessionObject sessionObject) {
        if (credentials.getLogin() == credentials.getPassword()) {
            sessionObject.setAuthenticated(true);
            try {
                outputStream.writeObject(new TextMessageDTO("Successfully logged as " + credentials.getLogin()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            sessionObject.setOpened(false);
            try {
                outputStream.writeObject(new TextMessageDTO("Wrong credentials for user " + credentials.getLogin()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
