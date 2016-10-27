package dto;

import java.io.Serializable;

public class ChatMessageDTO extends DTO implements Serializable {
    private String message;
    private UserDTO sender;
    private long recipientId;

    private ChatMessageDTO(long recipientId, String message) {
        this.recipientId = recipientId;
        this.message = message;
    }

    private ChatMessageDTO(UserDTO sender, String message) {
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

    public static class Builder{
        private ChatMessageDTO dto;

        public Builder(long recipientId, String message) {
            dto = new ChatMessageDTO(recipientId, message);        }

        public Builder(UserDTO sender, String message) {
            dto = new ChatMessageDTO(sender, message);
        }

        public Builder withConversationId(long conversationId){
            dto.conversationId = conversationId;
            return this;
        }

        public ChatMessageDTO build(){
            return dto;
        }
    }
}
