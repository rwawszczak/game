package client.listeners;

import dto.MessageDTO;

public abstract class SuccessListener extends Listener<MessageDTO> {

    public abstract void onSuccess();
    public abstract void onError();

    @Override
    public final Class<MessageDTO> getHandledType() {
        return MessageDTO.class;
    }

    @Override
    public final boolean handle(MessageDTO dto) {
        if(conversationId == dto.getConversationId()) {
            if (dto.getCommand() == MessageDTO.Command.SUCCESS) {
                onSuccess();
                return true;
            } else if (dto.getCommand() == MessageDTO.Command.ERROR) {
                onError();
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean oneTimeOnly() {
        return true;
    }
}
