package com.example.newsfeed.Services.UserService;

import com.example.newsfeed.Exception.UserAlreadyExistsException;
import com.example.newsfeed.Models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserByUsernameAndPassword(String username, String password);

    boolean signUp(User user) throws UserAlreadyExistsException;

    void followUser(User user, User userToFollow);

    void unfollowUser(User user, User userToUnfollow);

    List<User> getFollowers(User user);

    List<User> getFollowing(User user);
}
