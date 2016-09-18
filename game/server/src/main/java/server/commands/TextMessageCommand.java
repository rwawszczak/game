package server.commands;

import dto.TextMessageDTO;
import server.session.SessionObject;

import java.io.ObjectOutputStream;

public class TextMessageCommand implements BaseCommand<TextMessageDTO> {

    @Override
    public void execute(TextMessageDTO message, ObjectOutputStream outputStream, SessionObject sessionObject) {
        System.out.println(message.getMessage());
        if (message.getMessage().equals("logout") || message.getCode() == -1) {
            sessionObject.setOpened(false);
        }
    }

}
