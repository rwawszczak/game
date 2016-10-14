package dto;

import java.io.Serializable;

public class ChatMessageDTO extends DTO implements Serializable {
    private String message;
    private UserDTO sender;
    private long recipientId;

    public ChatMessageDTO(long recipientId, String message) {
        this.recipientId = recipientId;
        this.message = message;
    }

    public ChatMessageDTO(UserDTO sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public UserDTO getSender() {
        return sender;
    }
}
