package dto;

public class MessageDTO extends DTO {
    public enum Command{
        SUCCESS, ERROR, LOGOUT, HEARTBEAT, PLAYERLIST
    }

    private Command command;
    private String text;

    public MessageDTO(Command command) {
        this.command = command;
    }

    public MessageDTO(Command command, String text) {
        this.command = command;
        this.text = text;
    }

    public Command getCommand() {
        return command;
    }

    public String getText() {
        return text;
    }

}
