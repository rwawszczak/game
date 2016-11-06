package game.server;

import dto.DTO;
import dto.UserDTO;
import dto.UsersDTO;
import dto.battle.BattleInvitationDTO;
import game.model.assemblers.UserAssembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerBroadcasting {
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

    public static void broadcast(List<Long> userIds, DTO dto) {
        for (ServerThread serverThread : ServerData.getThreads()) {
            if(userIds.contains(serverThread.getUserId()) ) {
                try {
                    serverThread.send(dto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void broadcastBattleInvitation(long battleId, List<UserDTO> users) {
        final BattleInvitationDTO invitation = new BattleInvitationDTO.Builder(users).withBattleId(battleId).build();
        List<Long> ids = new ArrayList<>();
        for (UserDTO user : invitation.getUsers()) {
            ids.add(user.getId());
        }
        broadcast(ids, invitation);
    }

}
