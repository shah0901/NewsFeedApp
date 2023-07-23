package com.example.newsfeed.Services.CommentService;

import com.example.newsfeed.Exception.CommentNotFoundException;
import com.example.newsfeed.Models.Comment;
import com.example.newsfeed.Models.CommentReply;
import com.example.newsfeed.Models.User;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentsByPostId(long id);

    Comment addComment(Comment comment);

    Comment getCommentById(long commentId) throws CommentNotFoundException;

    void downVoteComment(User user, long id);

    void upVoteComment(User user, long id);

    void addCommentReply(long commentId, CommentReply commentReply);
}
