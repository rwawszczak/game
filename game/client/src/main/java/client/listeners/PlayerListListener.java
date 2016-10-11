package client.listeners;

import client.model.assemblers.PlayerAssembler;
import client.model.domain.Player;
import dto.PlayersDTO;

import java.util.List;

public abstract class PlayerListListener extends Listener<PlayersDTO> {
    public abstract void handlePlayers(List<Player> players);


    @Override
    public final Class<PlayersDTO> getHandledType() {
        return PlayersDTO.class;
    }

    @Override
    public final boolean handle(PlayersDTO dto) {
        List<Player> players = PlayerAssembler.toDomainObjects(dto);
        handlePlayers(players);
        return true;
    }
}
