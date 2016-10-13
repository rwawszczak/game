package client.listeners;

import client.model.assemblers.UserAssembler;
import client.model.domain.User;
import dto.UserDTO;

public abstract class LoginListener extends Listener<UserDTO> {
    public abstract void handleUser(User user);


    @Override
    public final Class<UserDTO> getHandledType() {
        return UserDTO.class;
    }

    @Override
    public final boolean handle(UserDTO dto) {
        User user = UserAssembler.toDomainObject(dto);
        handleUser(user);
        return true;
    }

    @Override
    public final boolean oneTimeOnly() {
        return true;
    }
}
