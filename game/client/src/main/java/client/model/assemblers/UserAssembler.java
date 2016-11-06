package client.model.assemblers;

import client.model.domain.User;
import com.google.common.collect.Lists;
import dto.UserDTO;
import dto.UsersDTO;

import java.util.List;

public class UserAssembler {
    private UserAssembler() {
    }

    public static User toDomainObject(UserDTO dto) {
        return new User(dto.getId(), dto.getName(), UserDetailsAssembler.toDomainObject(dto.getDetails()));
    }

    public static List<User> toDomainObjects(List<UserDTO> dtos) {
        return Lists.transform(dtos, UserAssembler::toDomainObject);
    }

    public static List<User> toDomainObjects(UsersDTO dto) {
        return Lists.transform(dto.getUsers(), UserAssembler::toDomainObject);
    }

}
