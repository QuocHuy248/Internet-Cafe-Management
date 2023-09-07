package org.example.Service;

import org.example.Utils.FileUtils;
import org.example.Utils.PasswordUtils;
import org.example.model.ERole;
import org.example.model.User;

import java.util.List;

public class UserService implements IUserService {
    private String fileUser = "./data/users.txt";

    @Override
    public List<User> getAllUsers() {
        return FileUtils.readData(fileUser, User.class
        );

    }

    @Override
    public User findUser(long id) {
        List<User> users = getAllUsers();
        User u = users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow(null);
        return u;
    }

    @Override
    public void updateUser(long id, User user) {
        List<User> users = getAllUsers();
        for (User u : users) {
            if (u.getId() == id) {
                u.setName(user.getName());
                u.setUsername(user.getUsername());
                u.setPassword(user.getPassword());
                u.setAge(user.getAge());
                u.setDob(user.getDob());
                u.setGender(user.getGender());
            }
        }
    }

    @Override
    public void deleteUser(long id) {
        List<User> users = getAllUsers();
        users.remove(users.stream().filter(user -> user.getId() == id).findFirst());
        FileUtils.writeData(fileUser, users);
    }

    @Override
    public void deleteUser(String username) {
        List<User> users = getAllUsers();
        users.remove(users.stream().filter(user -> user.getUsername().equals(username)).findFirst().get());
        FileUtils.writeData(fileUser, users);
    }

    @Override
    public void createUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        FileUtils.writeData(fileUser, users);
    }

    @Override
    public User findUser(String username) {
        List<User> users = getAllUsers();
        User u = users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElseThrow(null);
        return u;
    }

    @Override
    public boolean checkUserNamePassword(String username, String password) {
        List<User> users = getAllUsers();

        return users
                .stream()
                .anyMatch(user -> user.getUsername().equals(username) && PasswordUtils.isValid(password, user.getPassword()));
    }

    @Override
    public boolean checkGuestBalance(User user) {
        boolean check = false;

        if (user.getRole() == ERole.ADMIN || user.getRole() == ERole.EMPLOYEE) {
            check = true;
        }
        if (user.getRole() == ERole.GUEST && user.getBalance() < 0) {
            check = false;
        }
        if (user.getRole() == ERole.GUEST && user.getBalance() > 0) {
            check = true;
        }
        return check;

    }

    @Override
    public void updateBalance(long balance, User user) {
        List<User>users= getAllUsers();
        users.stream().filter(user1 -> user1.getUsername().equals(user.getUsername())).findFirst().get().setBalance(balance);
        FileUtils.writeData(fileUser,users);
    }

    @Override
    public void updatePassword(User user, String password) {
        List<User>users= getAllUsers();
        users.stream().filter(user1 -> user1.getUsername().equals(user.getUsername())).findFirst().get().setPassword(password);
        FileUtils.writeData(fileUser,users);
    }

    public static void main(String[] args) {
        UserService userService=new UserService();
        List<User> users= userService.getAllUsers();

    }
}

