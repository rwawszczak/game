package game.model.assemblers;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import dto.PlayerDTO;
import game.model.domain.Player;

import java.util.Collection;
import java.util.List;

public class PlayerAssembler {
    private static Function<Player, PlayerDTO> playerToDTO = getPlayerToDTOFunction();

    private PlayerAssembler() {
    }

    public static Player toDomainObject(PlayerDTO dto) {
        return new Player(dto.getId(), dto.getName(), PlayerDetailsAssembler.toDomainObject(dto.getDetails()));
    }

    public static PlayerDTO toDTO(Player player) {
        PlayerDTO.Builder builder = new PlayerDTO.Builder(player.getId(), player.getName());
        if (player.getDetails() != null) {
            builder.withDetails(PlayerDetailsAssembler.toDTO(player.getDetails()));
        }
        return builder.build();
    }

    public static List<PlayerDTO> toDTOs(Collection<Player> players) {
        return FluentIterable.from(players).transform(playerToDTO).toList();
    }

    private static Function<Player, PlayerDTO> getPlayerToDTOFunction() {
        return new Function<Player, PlayerDTO>() {
            @Override
            public PlayerDTO apply(Player player) {
                return toDTO(player);
            }
        };
    }
}
