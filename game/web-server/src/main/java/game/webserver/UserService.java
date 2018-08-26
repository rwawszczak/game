package game.webserver;

import java.util.List;

public interface UserService {
    User create(User user);
    User delete(String uid);
    List findAll();
    User findById(String uid);
    User update(User user);
}
