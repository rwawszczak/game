package game.server.commands;

import dto.UserDTO;
import dto.battle.BattleInvitationDTO;
import game.model.assemblers.UserAssembler;
import game.model.domain.Battle;
import game.server.ServerBroadcasting;
import game.server.ServerData;
import game.server.session.SessionObject;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BattleInvitationCommand implements BaseCommand<BattleInvitationDTO> {

    @Override
    public void execute(BattleInvitationDTO prompt, ObjectOutputStream outputStream, SessionObject sessionObject) {
        List<UserDTO> users = new ArrayList<>(prompt.getUsers());
        users.add(UserAssembler.toLightDTO(sessionObject.getUser()));
        long battleId = ServerData.getNextBattleId();
        storeBattle(battleId, users);
        ServerBroadcasting.broadcastBattleInvitation(battleId, users);
    }

    private void storeBattle(long battleId, List<UserDTO> users) {
        Battle battle = new Battle(battleId);
        for (UserDTO user : users) {
            battle.getUsers().put(UserAssembler.toDomainObject(user), Battle.Status.PENDING);
        }
        ServerData.getBattles().put(battleId, battle);
    }

}
