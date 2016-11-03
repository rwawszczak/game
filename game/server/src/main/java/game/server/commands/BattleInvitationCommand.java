package game.server.commands;

import dto.UserDTO;
import dto.battle.BattleInvitationDTO;
import game.model.assemblers.UserAssembler;
import game.server.session.ServerBroadcasting;
import game.server.session.SessionObject;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BattleInvitationCommand implements BaseCommand<BattleInvitationDTO> {

    @Override
    public void execute(BattleInvitationDTO prompt, ObjectOutputStream outputStream, SessionObject sessionObject) {
        List<UserDTO> users = new ArrayList<>(prompt.getUsers());
        users.add(UserAssembler.toLightDTO(sessionObject.getUser()));
        ServerBroadcasting.broadcastBattleInvitation(users);
    }

}
