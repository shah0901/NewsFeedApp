package com.example.newsfeed.Controller;

import com.example.newsfeed.Exception.UserAlreadyExistsException;
import com.example.newsfeed.Models.User;
import com.example.newsfeed.Services.UserService.UserService;
import com.example.newsfeed.Services.UserService.UserServiceImpl;

import java.util.List;

public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    public User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }
    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }
    public User getUserByUsernameAndPassword(String username, String password) {
        try{
            return userService.getUserByUsernameAndPassword(username, password);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean signUp(User user){
        try{
            return userService.signUp(user);
        } catch (UserAlreadyExistsException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public void followUser(User user, User userToFollow) {
        try{
            userService.followUser(user, userToFollow);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void unfollowUser(User user, User userToUnfollow) {
        userService.unfollowUser(user, userToUnfollow);
    }

    public List<User> getFollowers(User user) {
        return userService.getFollowers(user);
    }

    public List<User> getFollowing(User user) {
        return userService.getFollowing(user);
    }
}
