package game.webserver.repository;


import game.webserver.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    void delete(User user);

    List<User> findAll();

    Optional<User> findByUid(String uid);

    User save(User user);

    Optional<User> findByUsernameOrEmail(String usernameOrEmail, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
