package game.server.commands;

import com.google.common.collect.ImmutableMap;
import dto.*;
import dto.battle.BattleInvitationDTO;
import game.server.session.SessionObject;

import java.io.ObjectOutputStream;

public class CommandExecutor {
    private ImmutableMap<Class<? extends DTO>, BaseCommand> commandMap = ImmutableMap.<Class<? extends DTO>, BaseCommand>of(
            CredentialsDTO.class, new LoginCommand(),
            MessageDTO.class, new MessageCommand(),
            TextMessageDTO.class, new TextMessageCommand(),
            ChatMessageDTO.class, new ChatMessageCommand(),
            BattleInvitationDTO.class, new BattleInvitationCommand()
    );

    public void execute(DTO data, ObjectOutputStream outputStream, SessionObject session) {
        BaseCommand command = commandMap.get(data.getClass());
        if (command != null) {
            command.execute(data, outputStream, session);
        } else {
            System.out.println(data.getClass() + " is not supported data type");
        }
    }
}
