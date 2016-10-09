package dto;

public class PlayerDetailsDTO extends DTO {
    private PlayerDetailsDTO() {
    }

    public static class Builder {
        private PlayerDetailsDTO dto;

        public Builder() {
            dto = new PlayerDetailsDTO();
        }

        public PlayerDetailsDTO build() {
            return dto;
        }
    }

}
