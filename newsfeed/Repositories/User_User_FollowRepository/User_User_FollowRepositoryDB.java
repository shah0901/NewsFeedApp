package com.example.newsfeed.Repositories.User_User_FollowRepository;

import com.example.newsfeed.Models.User;
import com.example.newsfeed.Repositories.User_User_FollowRepository.User_User_FollowRepository;

import java.util.HashMap;
import java.util.List;

public class User_User_FollowRepositoryDB implements User_User_FollowRepository {
    private final HashMap<String, List<User>> followers;
    private final HashMap<String, List<User>> following;

    public User_User_FollowRepositoryDB() {
        this.followers = new HashMap<>();
        this.following = new HashMap<>();
    }

    public void save(User user) {
        followers.putIfAbsent(user.getUsername(), user.getFollowers());
        following.putIfAbsent(user.getUsername(), user.getFollowing());
    }

    //user1 follows user2
    public void followUser(User user1, User user2) {
        followers.get(user2.getUsername()).add(user1);
        following.get(user1.getUsername()).add(user2);
    }

    //user1 unfollows user2
    public void unfollowUser(User user1, User user2) {
        for(User u: followers.get(user2.getUsername())){
            if(u.getUsername().equals(user1.getUsername())){
                followers.get(user2.getUsername()).remove(u);
                break;
            }
        }
        for(User u: following.get(user1.getUsername())){
            if(u.getUsername().equals(user2.getUsername())){
                following.get(user1.getUsername()).remove(u);
                break;
            }
        }
    }

    //returns a list of users that follow user
    public List<User> getFollowers(User user) {
        return followers.get(user.getUsername());
    }

    //returns a list of users that user follows
    public List<User> getFollowing(User user) {
        return following.get(user.getUsername());
    }

}
