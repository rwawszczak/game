package client.listeners;

import client.model.assemblers.UserAssembler;
import client.model.domain.User;
import dto.battle.BattleInvitationDTO;

import java.util.List;

public abstract class BattleInvitationListener extends Listener<BattleInvitationDTO> {
    public abstract void handleInvitation(long battleId, List<User> users);


    @Override
    public final Class<BattleInvitationDTO> getHandledType() {
        return BattleInvitationDTO.class;
    }

    @Override
    public final boolean handle(BattleInvitationDTO dto) {
        List<User> users = UserAssembler.toDomainObjects(dto.getUsers());
        handleInvitation(dto.getBattleId(), users);
        return true;
    }

    @Override
    public boolean oneTimeOnly() {
        return false;
    }
}
