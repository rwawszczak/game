package dto;

import java.util.List;

public class PlayersDTO extends DTO {
    private List<PlayerDTO> players;

    private PlayersDTO(List<PlayerDTO> players) {
        this.players = players;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public static class Builder {
        private PlayersDTO dto;

        public Builder(List<PlayerDTO> players) {
            dto = new PlayersDTO(players);
        }

        public PlayersDTO build() {
            return dto;
        }
    }
}
