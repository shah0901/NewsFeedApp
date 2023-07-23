package com.example.newsfeed.Repositories.UserRepository;

import com.example.newsfeed.Models.User;

import java.util.List;

public interface UserRepository {
    User save(User user);

    List<User> findAll();

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    User findByEmail(String email);
}
