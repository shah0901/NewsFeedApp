package com.example.newsfeed.Repositories.PostRepository;

import com.example.newsfeed.Exception.PostNotfoundException;
import com.example.newsfeed.Models.Post;
import com.example.newsfeed.Models.User;
import com.example.newsfeed.Repositories.PostRepository.PostRepository;

import java.util.ArrayList;
import java.util.List;

public class PostRepositoryDB implements PostRepository {

    private final List<Post> posts;
    private long id = 0;

    public PostRepositoryDB() {
        this.posts = new ArrayList<>();
    }

    public Post save(Post post) {
        post.setId(id++);
        posts.add(post);
        return post;
    }

    public List<Post> findAll() {
        return posts;
    }

    public Post findById(long id) {
        for (Post post : posts) {
            if (post.getId() == id) {
                return post;
            }
        }
        throw new PostNotfoundException("No post found with id: " + id);
    }

    public List<Post> findByUser(User user){
        List<Post> posts = new ArrayList<>();
        for (Post post : this.posts) {
            if (post.getUser().getUsername().equals(user.getUsername())) {
                posts.add(post);
            }
        }
        return posts;
    }



}
