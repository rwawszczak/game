package game.services;


import game.model.domain.User;

public interface UserServiceInterface {
    User login(String login, String password) throws UserDontExistsException, WrongPasswordException;
    User register(String login, String password) throws UserAlreadyExistsException;

    class UserAlreadyExistsException extends Exception{}
    class UserDontExistsException extends Exception{}
    class WrongPasswordException extends Exception{}
}
