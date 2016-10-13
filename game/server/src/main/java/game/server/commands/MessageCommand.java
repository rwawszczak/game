package game.server.commands;

import dto.MessageDTO;
import dto.UsersDTO;
import game.model.assemblers.UserAssembler;
import game.server.ServerData;
import game.server.session.SessionObject;

import java.io.IOException;
import java.io.ObjectOutputStream;

import static dto.MessageDTO.Command.*;

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
        } else if(message.getCommand() == DISCONNECTED) {
            sessionObject.setOpened(false);
        } else if(message.getCommand() == USERLIST) {
            try {
                UsersDTO users = new UsersDTO.Builder(
                        UserAssembler.toDTOs(ServerData.getUsers().values())
                ).build();
                outputStream.writeObject(users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
