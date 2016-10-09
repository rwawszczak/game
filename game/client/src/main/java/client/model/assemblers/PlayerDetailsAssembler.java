package client.model.assemblers;

import client.model.domain.PlayerDetails;
import dto.PlayerDetailsDTO;

public class PlayerDetailsAssembler {
    private PlayerDetailsAssembler() {
    }

    public static PlayerDetails toDomainObject(PlayerDetailsDTO details) {
        return new PlayerDetails();
    }
}
