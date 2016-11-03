package game.server.session;

import dto.UserDTO;
import dto.UsersDTO;
import dto.battle.BattleInvitationDTO;
import game.model.assemblers.UserAssembler;
import game.server.ServerData;
import game.server.ServerThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerBroadcasting {
    private static long nextBattleId = 1;
    private ServerBroadcasting() {
    }

    public static void broadcastConnectedUsers() {
        UsersDTO users = new UsersDTO.Builder(
                UserAssembler.toDTOs(ServerData.getUsers().values())
        ).build();
        for (ServerThread serverThread : ServerData.getThreads()) {
            try {
                serverThread.send(users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastBattleInvitation(List<UserDTO> users) {
        final BattleInvitationDTO invitation = new BattleInvitationDTO.Builder(users).withBattleId(getNextBattleId()).build();
        List<Long> ids = new ArrayList<>();
        for (UserDTO user : invitation.getUsers()) {
            ids.add(user.getId());
        }
        for (ServerThread serverThread : ServerData.getThreads()) {
            if (ids.contains(serverThread.getUserId())) {
                try {
                    serverThread.send(invitation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private synchronized static long getNextBattleId(){
        return nextBattleId++;
    }
}
