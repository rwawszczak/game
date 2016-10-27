package dto;

public class MessageDTO extends DTO {
    public enum Command {
        SUCCESS, ERROR, LOGOUT, HEARTBEAT, USERLIST, DISCONNECTED
    }

    private Command command;
    private String text;

    private MessageDTO(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public String getText() {
        return text;
    }

    public long getConversationId() {
        return conversationId;
    }

    public static class Builder {
        private MessageDTO dto;

        public Builder(Command command) {
            dto = new MessageDTO(command);
        }

        public Builder withText(String text) {
            dto.text = text;
            return this;
        }

        public Builder withConversationId(long id){
            dto.conversationId = id;
            return this;
        }

        public MessageDTO build() {
            return dto;
        }
    }

}
