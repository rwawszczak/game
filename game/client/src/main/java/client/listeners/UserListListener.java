package client.listeners;

import client.model.assemblers.UserAssembler;
import client.model.domain.User;
import dto.user.UsersDTO;

import java.util.List;

public abstract class UserListListener extends Listener<UsersDTO> {
    public abstract void handleUsers(List<User> users);


    @Override
    public final Class<UsersDTO> getHandledType() {
        return UsersDTO.class;
    }

    @Override
    public final boolean handle(UsersDTO dto) {
        List<User> users = UserAssembler.toDomainObjects(dto);
        handleUsers(users);
        return true;
    }
}
