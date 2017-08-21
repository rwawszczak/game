package game.model.assemblers;

import dto.user.UserDetailsDTO;
import game.model.domain.UserDetails;

public class UserDetailsAssembler {
    private UserDetailsAssembler() {
    }

    public static UserDetails toDomainObject(UserDetailsDTO details) {
        return details != null ? new UserDetails(details.getCharacterIds()) : null;
    }

    public static UserDetailsDTO toDTO(UserDetails details) {
        return details != null ?
                new UserDetailsDTO.Builder()
                .withCharacterIds(details.getCharacterIds())
                .build()
                : null;
    }
}
