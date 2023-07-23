package com.example.newsfeed.Services.NewsFeedService;

import com.example.newsfeed.Models.Post;
import com.example.newsfeed.Services.PostService.PostService;
import com.example.newsfeed.Services.PostService.PostServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsFeedServiceImpl implements NewsFeedService{
    private final PostService postService;

    public NewsFeedServiceImpl(PostService postService) {
        this.postService = postService;
    }


    public List<Post> getPosts(){
        List<Post> posts = postService.findAll();
        posts.sort((a, b) -> Integer.compare(b.getUpVotes().size(), a.getUpVotes().size()));
        return posts;
    }
}
