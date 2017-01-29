package game.server;

import dto.*;
import dto.battle.BattleInvitationDTO;
import dto.battle.BattleInvitationResponseDTO;
import game.server.commands.*;
import game.server.session.SessionObject;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private Map<Class<? extends DTO>, BaseCommand> commandMap = new HashMap<>();

    public CommandExecutor() {
        commandMap.put(CredentialsDTO.class, new CredentialsCommand());
        commandMap.put(MessageDTO.class, new MessageCommand());
        commandMap.put(TextMessageDTO.class, new TextMessageCommand());
        commandMap.put(ChatMessageDTO.class, new ChatMessageCommand());
        commandMap.put(BattleInvitationDTO.class, new BattleInvitationCommand());
        commandMap.put(BattleInvitationResponseDTO.class, new BattleInvitationResponseCommand());
    }

    public void execute(DTO data, ObjectOutputStream outputStream, SessionObject session) {
        BaseCommand command = commandMap.get(data.getClass());
        if (command != null) {
            command.execute(data, outputStream, session);
        } else {
            System.out.println(data.getClass() + " is not supported data type");
        }
    }
}
