package client.listeners;

import dto.DTO;

public abstract class Listener<T extends DTO> {
    protected long conversationId = Long.MAX_VALUE;
    public abstract Class<T> getHandledType();
    public abstract boolean handle(T dto);
    public abstract boolean oneTimeOnly();
    public void setConversationId(long conversationId){
        this.conversationId = conversationId;
    }
}
