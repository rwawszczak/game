package game.server.commands;

import dto.MessageDTO;
import game.server.session.SessionObject;

import java.io.IOException;
import java.io.ObjectOutputStream;

import static dto.MessageDTO.Command.HEARTBEAT;
import static dto.MessageDTO.Command.LOGOUT;

public class MessageCommand implements BaseCommand<MessageDTO> {
    @Override
    public void execute(MessageDTO message, ObjectOutputStream outputStream, SessionObject sessionObject) {
        if (message.getCommand() == LOGOUT) {
            sessionObject.setOpened(false);
        } else if(message.getCommand() == HEARTBEAT) {
            try {
                outputStream.writeObject(new MessageDTO(MessageDTO.Command.SUCCESS));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
