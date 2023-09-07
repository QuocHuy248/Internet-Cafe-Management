package org.example.Service;

import org.example.model.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    User findUser(long id);

    User findUser(String username);

    void updateUser(long id, User user);

    void deleteUser(long id);

    void deleteUser(String username);

    void createUser(User user);

    boolean checkUserNamePassword(String username, String password);

    boolean checkGuestBalance(User user);

    void updateBalance(long balance, User user);

    void updatePassword(User user, String password);

    boolean checkUserPassword(User user, String password);

}
