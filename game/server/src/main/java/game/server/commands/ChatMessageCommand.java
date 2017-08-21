package game.server.commands;

import dto.ChatMessageDTO;
import dto.user.UserDTO;
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
                    UserDTO sender = UserAssembler.toLightDTO(sessionObject.getUser());
                    String messageText = message.getMessage();
                    serverThread.send(
                            new ChatMessageDTO.Builder(sender, messageText)
                                    .withConversationId(message.getConversationId())
                                    .build()
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

}
