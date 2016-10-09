package game.model.assemblers;

import dto.PlayerDetailsDTO;
import game.model.domain.PlayerDetails;

public class PlayerDetailsAssembler {
    private PlayerDetailsAssembler() {
    }

    public static PlayerDetails toDomainObject(PlayerDetailsDTO details) {
        return details != null ? new PlayerDetails() : null;
    }

    public static PlayerDetailsDTO toDTO(PlayerDetails details) {
        return details != null ? new PlayerDetailsDTO.Builder().build() : null;
    }
}
