package com.example.newsfeed.Repositories.PostRepository;

import com.example.newsfeed.Models.Post;
import com.example.newsfeed.Models.User;

import java.util.List;

public interface PostRepository {
    Post save(Post post);

    Post findById(long id);

    List<Post> findAll();

    List<Post> findByUser(User user);

}
