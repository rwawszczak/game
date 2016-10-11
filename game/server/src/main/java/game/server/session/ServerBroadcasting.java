package game.server.session;

import dto.PlayersDTO;
import game.model.assemblers.PlayerAssembler;
import game.server.ServerData;
import game.server.ServerThread;

import java.io.IOException;

public class ServerBroadcasting {
    private ServerBroadcasting() {
    }

    public static void broadcastConnectedUsers(){
        PlayersDTO players = new PlayersDTO.Builder(
                PlayerAssembler.toDTOs(ServerData.getPlayers().values())
        ).build();
        for(ServerThread serverThread : ServerData.getThreads()){
            try {
                serverThread.send(players);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
