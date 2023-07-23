package com.example.newsfeed.Services.PostService;

import com.example.newsfeed.Models.Post;
import com.example.newsfeed.Models.User;

import java.util.List;

public interface PostService {
    List<Post> findAll();

    Post save(Post post);

    Post findById(long postId);

    void upVotePost(User user, long postId);

    void downVotePost(User user, long postId);

    List<Post> findByUser(User user);
}
