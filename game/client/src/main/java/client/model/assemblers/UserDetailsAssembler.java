package client.model.assemblers;

import client.model.domain.UserDetails;
import dto.user.UserDetailsDTO;

public class UserDetailsAssembler {
    private UserDetailsAssembler() {
    }

    public static UserDetails toDomainObject(UserDetailsDTO details) {
        return new UserDetails();
    }
}
