package game.server.commands;

import com.google.common.collect.ImmutableMap;
import dto.CredentialsDTO;
import dto.DTO;
import dto.MessageDTO;
import dto.TextMessageDTO;
import game.server.session.SessionObject;

import java.io.ObjectOutputStream;

public class CommandExecutor {
    private ImmutableMap<Class<? extends DTO>, BaseCommand> commandMap = ImmutableMap.<Class<? extends DTO>, BaseCommand>of(
            CredentialsDTO.class, new LoginCommand(),
            MessageDTO.class, new MessageCommand(),
            TextMessageDTO.class, new TextMessageCommand()
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