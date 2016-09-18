package dto;

import java.io.Serializable;

public class CredentialsDTO extends DTO implements Serializable {
    private static final long serialVersionUID = -6082744288733541097L;
    private String login;
    private String password;

    public CredentialsDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
