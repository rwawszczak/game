package dto;

public class MessageDTO extends DTO {
    public enum Command {
        SUCCESS, ERROR, LOGOUT, HEARTBEAT, PLAYERLIST
    }

    private Command command;
    private String text;

    public MessageDTO(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public String getText() {
        return text;
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

        public MessageDTO build() {
            return dto;
        }
    }

}
