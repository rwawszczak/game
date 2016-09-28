package game.server.commands;

import dto.MessageDTO;
import game.server.session.SessionObject;

import java.io.ObjectOutputStream;

public class MessageCommand implements BaseCommand<MessageDTO> {
    @Override
    public void execute(MessageDTO message, ObjectOutputStream outputStream, SessionObject sessionObject) {
        if (message.getCommand().equals(MessageDTO.Command.LOGOUT)) {
            sessionObject.setOpened(false);
        }
    }
}
