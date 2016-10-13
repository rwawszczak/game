package game.model.assemblers;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import dto.UserDTO;
import game.model.domain.User;

import java.util.Collection;
import java.util.List;

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

    public static List<UserDTO> toDTOs(Collection<User> users) {
        return FluentIterable.from(users).transform(userToDTO).toList();
    }

    private static Function<User, UserDTO> getUserToDTOFunction() {
        return new Function<User, UserDTO>() {
            @Override
            public UserDTO apply(User user) {
                return toDTO(user);
            }
        };
    }
}
