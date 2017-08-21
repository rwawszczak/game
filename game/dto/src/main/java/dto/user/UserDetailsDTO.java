package dto.user;

import dto.DTO;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsDTO extends DTO {
    private List<Long> characterIds;
    private UserDetailsDTO() {
    }

    public List<Long> getCharacterIds() {
        return characterIds;
    }

    public static class Builder {
        private UserDetailsDTO dto;

        public Builder() {
            dto = new UserDetailsDTO();
        }

        public Builder withCharacterIds(List<Long> ids){
            dto.characterIds = new ArrayList<>(ids);
            return this;
        }

        public UserDetailsDTO build() {
            return dto;
        }
    }

}
