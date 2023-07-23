package com.example.newsfeed.Controller;

import com.example.newsfeed.Models.Post;
import com.example.newsfeed.Models.User;
import com.example.newsfeed.Services.PostService.PostService;
import com.example.newsfeed.Services.PostService.PostServiceImpl;

import java.util.List;

public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public Post createPost(Post post) {
        return postService.save(post);
    }


    public Post getPostById(long postId) {
        return postService.findById(postId);
    }

    public void upVotePost(User user, long postId) {
        postService.upVotePost(user, postId);
    }
    public void downVotePost(User user, long postId) {
        postService.downVotePost(user, postId);
    }

    public List<Post> getPostByUserId(User user) {
        return postService.findByUser(user);
    }
}
