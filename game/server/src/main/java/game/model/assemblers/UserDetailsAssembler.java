package game.model.assemblers;

import dto.UserDetailsDTO;
import game.model.domain.UserDetails;

public class UserDetailsAssembler {
    private UserDetailsAssembler() {
    }

    public static UserDetails toDomainObject(UserDetailsDTO details) {
        return details != null ? new UserDetails() : null;
    }

    public static UserDetailsDTO toDTO(UserDetails details) {
        return details != null ? new UserDetailsDTO.Builder().build() : null;
    }
}
