package dto;

import java.io.Serializable;

public abstract class DTO implements Serializable {
    private static final long serialVersionUID = 3658264928019852286L;
    protected long conversationId = Long.MAX_VALUE;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getConversationId() {
        return conversationId;
    }
}
