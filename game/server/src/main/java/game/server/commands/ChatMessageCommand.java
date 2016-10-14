package game.server.commands;

import dto.ChatMessageDTO;
import game.model.assemblers.UserAssembler;
import game.server.ServerData;
import game.server.ServerThread;
import game.server.session.SessionObject;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ChatMessageCommand implements BaseCommand<ChatMessageDTO> {

    @Override
    public void execute(ChatMessageDTO message, ObjectOutputStream outputStream, SessionObject sessionObject) {
        for(ServerThread serverThread : ServerData.getThreads()){
            if(message.getRecipientId() == serverThread.getUserId()){
                try {
                    serverThread.send(new ChatMessageDTO(UserAssembler.toLightDTO(sessionObject.getUser()), message.getMessage()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

}
