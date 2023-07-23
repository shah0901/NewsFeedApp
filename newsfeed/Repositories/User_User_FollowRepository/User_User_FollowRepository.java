package com.example.newsfeed.Repositories.User_User_FollowRepository;

import com.example.newsfeed.Models.User;

import java.util.List;

public interface User_User_FollowRepository {
    List<User> getFollowers(User user);

    List<User> getFollowing(User user);

    void followUser(User user, User userToFollow);

    void unfollowUser(User user, User userToUnfollow);

    void save(User u);
}
