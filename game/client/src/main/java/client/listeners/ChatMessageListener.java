package client.listeners;

import client.model.assemblers.UserAssembler;
import client.model.domain.User;
import dto.ChatMessageDTO;

public abstract class ChatMessageListener extends Listener<ChatMessageDTO> {
    public abstract void handleMessage(User user, String message);


    @Override
    public final Class<ChatMessageDTO> getHandledType() {
        return ChatMessageDTO.class;
    }

    @Override
    public final boolean handle(ChatMessageDTO dto) {
        User user = UserAssembler.toDomainObject(dto.getSender());
        handleMessage(user, dto.getMessage());
        return true;
    }
}
