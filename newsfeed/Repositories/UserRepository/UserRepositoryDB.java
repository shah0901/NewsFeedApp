package com.example.newsfeed.Repositories.UserRepository;

import com.example.newsfeed.Models.User;
import com.example.newsfeed.Repositories.UserRepository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryDB implements UserRepository {

    private final List<User> users;

    public UserRepositoryDB() {
        users = new ArrayList<>();
    }
    public User save(User user) {
        users.add(user);
        return user;
    }
    public User findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public User findByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    public User findByUsernameAndPassword(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    public List<User> findAll() {
        return users;
    }

}
