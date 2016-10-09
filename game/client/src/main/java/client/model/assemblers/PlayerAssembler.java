package client.model.assemblers;

import client.model.domain.Player;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import dto.PlayerDTO;
import dto.PlayersDTO;

import java.util.List;

public class PlayerAssembler {
    private PlayerAssembler() {
    }

    public static Player toDomainObject(PlayerDTO dto){
        return new Player(dto.getId(), dto.getName(), PlayerDetailsAssembler.toDomainObject(dto.getDetails()));
    }

    public static List<Player> toDomainObjects(List<PlayerDTO> dtos){
        return Lists.transform(dtos, getDtoToDomain());
    }

    public static List<Player> toDomainObjects(PlayersDTO dto){
        return Lists.transform(dto.getPlayers(), getDtoToDomain());
    }

    private static Function<PlayerDTO, Player> getDtoToDomain() {
        return new Function<PlayerDTO, Player>() {
            @Override
            public Player apply(PlayerDTO playerDTO) {
                return toDomainObject(playerDTO);
            }
        };
    }
}
