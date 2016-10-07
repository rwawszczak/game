package dto;

import java.util.List;

public class PlayersDTO extends DTO {
    private List<LightPlayerDTO> players;

    public PlayersDTO(List<LightPlayerDTO> players) {
        this.players = players;
    }

    public List<LightPlayerDTO> getPlayers() {
        return players;
    }
}
