package game.server.commands;

import dto.battle.BattleInvitationDTO;
import dto.user.UserDTO;
import game.model.assemblers.UserAssembler;
import game.model.domain.Battle;
import game.model.domain.Character;
import game.model.domain.User;
import game.server.ServerBroadcasting;
import game.server.ServerData;
import game.server.session.SessionObject;
import game.services.CharacterService;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BattleInvitationCommand implements BaseCommand<BattleInvitationDTO> {
    private CharacterService characterService = new CharacterService();

    @Override
    public void execute(BattleInvitationDTO prompt, ObjectOutputStream outputStream, SessionObject sessionObject) {
        List<UserDTO> users = new ArrayList<>(prompt.getUsers());
        users.add(UserAssembler.toLightDTO(sessionObject.getUser()));
        long battleId = ServerData.getNextBattleId();
        storeBattle(battleId, users);
        ServerBroadcasting.broadcastBattleInvitation(battleId, users);
    }

    private void storeBattle(long battleId, List<UserDTO> userDTOs) {
        Battle battle = new Battle(battleId);
        List<User> users = new ArrayList<>();
        for (UserDTO dto : userDTOs) {
            users.add(ServerData.getUsers().get(dto.getId()));
        }
        for (User user : users) {
            battle.getUsers().put(user, Battle.Status.PENDING);
            Character character = characterService.load(user.getDetails().getCharacterIds().get(0));
            battle.getCharacters().add(character);
            //TODO: make check if player have characters
            //TODO: dont take hardcoded 0 character
        }
        ServerData.getBattles().put(battleId, battle);
    }

}
