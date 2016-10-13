package dto;

public class UserDetailsDTO extends DTO {
    private UserDetailsDTO() {
    }

    public static class Builder {
        private UserDetailsDTO dto;

        public Builder() {
            dto = new UserDetailsDTO();
        }

        public UserDetailsDTO build() {
            return dto;
        }
    }

}
