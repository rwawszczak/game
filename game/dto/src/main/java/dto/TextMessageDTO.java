package dto;

import java.io.Serializable;

public class TextMessageDTO extends DTO implements Serializable {
    private static final long serialVersionUID = -2559098889906185777L;
    private String message;

    public TextMessageDTO() {
    }

    public TextMessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
