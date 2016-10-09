package dto;

import java.io.Serializable;
import java.util.List;

public class CredentialsDTO extends DTO implements Serializable {
    private static final long serialVersionUID = -6082744288733541097L;
    private String login;
    private String password;

    private CredentialsDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }


    public static class Builder {
        private CredentialsDTO dto;

        public Builder(String login, String password) {
            dto = new CredentialsDTO(login, password);
        }

        public CredentialsDTO build() {
            return dto;
        }
    }
}
