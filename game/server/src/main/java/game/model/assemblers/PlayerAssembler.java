package game.model.assemblers;

import dto.PlayerDTO;
import game.model.domain.Player;

public class PlayerAssembler {
    private PlayerAssembler() {
    }

    public static Player toDomainObject(PlayerDTO dto) {
        return new Player(dto.getId(), dto.getName(), PlayerDetailsAssembler.toDomainObject(dto.getDetails()));
    }

    public static PlayerDTO toDTO(Player player) {
        PlayerDTO.Builder builder = new PlayerDTO.Builder(player.getId(), player.getName());
        if(player.getDetails() != null){
            builder.withDetails(PlayerDetailsAssembler.toDTO(player.getDetails()));
        }
        return builder.build();
    }
}
