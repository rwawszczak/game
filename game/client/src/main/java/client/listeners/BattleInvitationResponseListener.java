package client.listeners;

import client.model.assemblers.UserAssembler;
import client.model.domain.User;
import dto.battle.BattleInvitationResponseDTO;

public abstract class BattleInvitationResponseListener extends Listener<BattleInvitationResponseDTO> {
    public abstract void handleResponse(long battleId, User user, boolean hasAccepted);


    @Override
    public final Class<BattleInvitationResponseDTO> getHandledType() {
        return BattleInvitationResponseDTO.class;
    }

    @Override
    public final boolean handle(BattleInvitationResponseDTO dto) {
        User user = UserAssembler.toDomainObject(dto.getUser());
        handleResponse(dto.getBattleId(), user, dto.getResponse() == BattleInvitationResponseDTO.Response.ACCEPTED);
        return true;
    }

    @Override
    public boolean oneTimeOnly() {
        return false;
    }
}
