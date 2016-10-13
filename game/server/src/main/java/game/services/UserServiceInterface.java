package game.services;


import game.model.domain.User;

public interface UserServiceInterface {
    User login(String login, String password);
}
