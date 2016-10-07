package game.server.commands;

import dto.LightPlayerDTO;
import dto.MessageDTO;
import dto.PlayersDTO;
import game.model.Player;
import game.server.ServerData;
import game.server.session.SessionObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

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
        } else if(message.getCommand() == PLAYERLIST) {
            try {
                List<LightPlayerDTO> playerList = new ArrayList<LightPlayerDTO>();
                for(Player player : ServerData.getPlayers().values()){
                    if(player.getId()!=sessionObject.getPlayer().getId()) {
                        playerList.add(new LightPlayerDTO(player.getId(), player.getName()));
                    }
                }
                PlayersDTO players = new PlayersDTO(playerList);
                outputStream.writeObject(players);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
