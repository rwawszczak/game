package game.server;

import dto.DTO;
import dto.user.UserDTO;
import dto.user.UsersDTO;
import dto.battle.BattleInvitationDTO;
import dto.battle.BattleStateDTO;
import game.model.assemblers.UserAssembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public static void broadcastBattleState(Map<Long, BattleStateDTO> battleStateDTOs) {
        Set<Long> userIds = battleStateDTOs.keySet();
        for (ServerThread serverThread : ServerData.getThreads()) {
            if(userIds.contains(serverThread.getUserId()) ) {
                try {
                    serverThread.send(battleStateDTOs.get(serverThread.getUserId()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
