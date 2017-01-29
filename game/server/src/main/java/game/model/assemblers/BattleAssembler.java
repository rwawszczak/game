package game.model.assemblers;

import dto.UserDTO;
import dto.battle.BattleStateDTO;
import game.model.domain.Battle;
import game.model.domain.User;

import java.util.*;

public class BattleAssembler {
    public static Map<Long, BattleStateDTO> toBattleStateDTOs(Battle battle) {
        Map<Long, BattleStateDTO> dtos = new HashMap<>();
        Set<User> users = battle.getUsers().keySet();
        List<UserDTO> userDTOs = UserAssembler.toDTOs(users);
        for(User user : users){
            dtos.put(user.getId(),
                    new BattleStateDTO.Builder(userDTOs)
                    .withBattleId(battle.getId())
                    .build()
            );
        }
        return dtos;
    }
}
