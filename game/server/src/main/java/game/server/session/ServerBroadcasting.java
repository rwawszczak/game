package game.server.session;

import dto.UsersDTO;
import game.model.assemblers.UserAssembler;
import game.server.ServerData;
import game.server.ServerThread;

import java.io.IOException;

public class ServerBroadcasting {
    private ServerBroadcasting() {
    }

    public static void broadcastConnectedUsers(){
        UsersDTO users = new UsersDTO.Builder(
                UserAssembler.toDTOs(ServerData.getUsers().values())
        ).build();
        for(ServerThread serverThread : ServerData.getThreads()){
            try {
                serverThread.send(users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
