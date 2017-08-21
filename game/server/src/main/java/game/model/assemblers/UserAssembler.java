package game.model.assemblers;

import dto.user.UserDTO;
import game.model.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserAssembler {
    private static Function<User, UserDTO> userToDTO = getUserToDTOFunction();

    private UserAssembler() {
    }

    public static User toDomainObject(UserDTO dto) {
        return new User(dto.getId(), dto.getName(), UserDetailsAssembler.toDomainObject(dto.getDetails()));
    }

    public static UserDTO toDTO(User user) {
        UserDTO.Builder builder = new UserDTO.Builder(user.getId(), user.getName());
        if (user.getDetails() != null) {
            builder.withDetails(UserDetailsAssembler.toDTO(user.getDetails()));
        }
        return builder.build();
    }

    public static UserDTO toLightDTO(User user) {
        UserDTO.Builder builder = new UserDTO.Builder(user.getId(), user.getName());
        return builder.build();
    }

    public static List<UserDTO> toDTOs(Collection<User> users) {
        return users.stream().map(userToDTO).collect(Collectors.toList());
    }

    private static Function<User, UserDTO> getUserToDTOFunction() {
        return UserAssembler::toDTO;
    }
}
