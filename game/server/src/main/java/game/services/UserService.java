package game.services;

import game.model.domain.User;

public class UserService implements UserServiceInterface { //TODO: this is mock implementation
    private long currentId = 0;
    @Override
    public User login(String login, String password) {
        if (login.equals(password)) {
            return new User(++currentId, login);
        }
        return null;
    }
}
