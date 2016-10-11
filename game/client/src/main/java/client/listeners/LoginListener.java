package client.listeners;

import client.model.assemblers.PlayerAssembler;
import client.model.domain.Player;
import dto.PlayerDTO;

public abstract class LoginListener extends Listener<PlayerDTO> {
    public abstract void handlePlayer(Player player);


    @Override
    public final Class<PlayerDTO> getHandledType() {
        return PlayerDTO.class;
    }

    @Override
    public final boolean handle(PlayerDTO dto) {
        Player player = PlayerAssembler.toDomainObject(dto);
        handlePlayer(player);
        return true;
    }

    @Override
    public final boolean oneTimeOnly() {
        return true;
    }
}
