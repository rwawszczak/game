package game.webserver;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    void delete(User user);

    List findAll();

    User findByUid(String uid);

    User save(User user);
}
