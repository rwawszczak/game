package dto;

import java.io.Serializable;

public class CredentialsDTO extends DTO implements Serializable {
    public enum Operation {
        LOGIN, REGISTER
    }
    private static final long serialVersionUID = -6082744288733541097L;
    private String login;
    private String password;
    private Operation operation;

    private CredentialsDTO(String login, String password, Operation operation) {
        this.login = login;
        this.password = password;
        this.operation = operation;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Operation getOperation() {
        return operation;
    }

    public static class Builder {
        private CredentialsDTO dto;

        public Builder(String login, String password, Operation operation) {
            dto = new CredentialsDTO(login, password, operation);
        }

        public CredentialsDTO build() {
            return dto;
        }
    }
}
