package game.server.commands;

import dto.MessageDTO;
import dto.PlayersDTO;
import game.model.assemblers.PlayerAssembler;
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
        } else if(message.getCommand() == PLAYERLIST) {
            try {
                PlayersDTO players = new PlayersDTO.Builder(
                        PlayerAssembler.toDTOs(ServerData.getPlayers().values())
                ).build();
                outputStream.writeObject(players);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
