package com.example.newsfeed.Services.UserService;

import com.example.newsfeed.Exception.UserAlreadyExistsException;
import com.example.newsfeed.Exception.UserNotFoundException;
import com.example.newsfeed.Models.User;
import com.example.newsfeed.Repositories.PostRepository.PostRepository;
import com.example.newsfeed.Repositories.UserRepository.UserRepository;
import com.example.newsfeed.Repositories.User_User_FollowRepository.User_User_FollowRepository;

import java.util.List;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final User_User_FollowRepository user_user_followRepository;
    private final PostRepository postRepository;

    public UserServiceImpl(UserRepository userRepository, User_User_FollowRepository userUserFollowRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        user_user_followRepository = userUserFollowRepository;
        this.postRepository = postRepository;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        for(User user : users) {
            user.setFollowers(user_user_followRepository.getFollowers(user));
            user.setFollowing(user_user_followRepository.getFollowing(user));
            user.setPosts(postRepository.findByUser(user));
        }
        return users;
    }
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user!=null){
        user.setPosts(postRepository.findByUser(user));
        user.setFollowers(user_user_followRepository.getFollowers(user));
        user.setFollowing(user_user_followRepository.getFollowing(user));
        return user;}
        else{
            throw new UserNotFoundException("User not found");
        }
    }
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        user.setPosts(postRepository.findByUser(user));
        user.setFollowers(user_user_followRepository.getFollowers(user));
        user.setFollowing(user_user_followRepository.getFollowing(user));
        return user;
    }
    public User getUserByUsernameAndPassword(String username, String password) {
        if(userRepository.findByUsernameAndPassword(username, password) == null)
            throw new RuntimeException("User not found");
        User user = userRepository.findByUsernameAndPassword(username, password);
        user.setPosts(postRepository.findByUser(user));
        user.setFollowers(user_user_followRepository.getFollowers(user));
        user.setFollowing(user_user_followRepository.getFollowing(user));
        return user;
    }
    public boolean signUp(User user) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("User already exists with username: " + user.getUsername());
        }
//        if (userRepository.findByEmail(user.getEmail()) != null) {
//            throw new UserAlreadyExistsException("User already exists with email: " + user.getEmail());
//        }
        User u =  userRepository.save(user);
        user_user_followRepository.save(u);
        return true;
    }

    public void followUser(User user, User userToFollow) {
        if(user.getUsername().equals(userToFollow.getUsername()))
            throw new RuntimeException("You cannot follow yourself");
        user_user_followRepository.followUser(user, userToFollow);
    }

    public void unfollowUser(User user, User userToUnfollow) {
        user_user_followRepository.unfollowUser(user, userToUnfollow);
    }

    public List<User> getFollowers(User user) {
        return user_user_followRepository.getFollowers(user);
    }

    public List<User> getFollowing(User user) {
        return user_user_followRepository.getFollowing(user);
    }
}
