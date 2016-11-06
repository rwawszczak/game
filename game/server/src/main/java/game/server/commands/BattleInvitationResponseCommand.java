package game.server.commands;

import dto.battle.BattleInvitationResponseDTO;
import game.model.assemblers.UserAssembler;
import game.model.domain.Battle;
import game.model.domain.User;
import game.server.ServerBroadcasting;
import game.server.ServerData;
import game.server.session.SessionObject;

import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BattleInvitationResponseCommand implements BaseCommand<BattleInvitationResponseDTO> {

    @Override
    public void execute(BattleInvitationResponseDTO response, ObjectOutputStream outputStream, SessionObject sessionObject) {
        Battle battle = ServerData.getBattles().get(response.getBattleId());
        if (battle != null) {
            Map<User, Battle.Status> userMap = battle.getUsers();
            if (userMap.get(sessionObject.getUser()) != null) {
                Battle.Status status = response.getResponse() == BattleInvitationResponseDTO.Response.ACCEPTED ? Battle.Status.ACCEPTED : Battle.Status.DECLINED;
                userMap.put(sessionObject.getUser(), status);
                Set<User> users = userMap.keySet();
                List<Long> ids = users.stream().map(User::getId).collect(Collectors.toList());
                ServerBroadcasting.broadcast(ids,
                        new BattleInvitationResponseDTO.Builder(response.getBattleId())
                                .withUser(UserAssembler.toLightDTO(sessionObject.getUser()))
                                .withResponse(response.getResponse())
                                .build()
                );
                if(battle.allAccepted()){
                    //TODO: Battle is matched, create and send Battle dto to matched users
                }
            } else {
                System.out.println("User " + sessionObject.getUser().getName() + " tried to respond to battle he is not associated to nr: " + response.getBattleId());
            }
        } else {
            System.out.println("User " + sessionObject.getUser().getName() + " tried to respond to not existing battle nr: " + response.getBattleId());
        }
    }
}
