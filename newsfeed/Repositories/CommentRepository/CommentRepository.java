package com.example.newsfeed.Repositories.CommentRepository;

import com.example.newsfeed.Models.Comment;
import com.example.newsfeed.Models.User;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(long commentId);

    List<Comment> findByPostId(long postId);

    void downVote(User user, long id);

    void upVote(User user, long id);

    List<User> getUpVotes(long id);

    List<User> getDownVotes(long id);

    void removeUpVote(User user, long id);

    void removeDownVote(User user, long id);
}
