package com.example.newsfeed.Controller;

import com.example.newsfeed.Exception.CommentNotFoundException;
import com.example.newsfeed.Models.Comment;
import com.example.newsfeed.Models.CommentReply;
import com.example.newsfeed.Models.User;
import com.example.newsfeed.Services.CommentService.CommentService;
import com.example.newsfeed.Services.CommentService.CommentServiceImpl;

public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    public Comment addComment(Comment comment){
        return commentService.addComment(comment);
    }

    public Comment getCommentById(long commentId){
        try {
            return commentService.getCommentById(commentId);
        }
        catch (CommentNotFoundException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void downVoteComment(User user, long id) {
        commentService.downVoteComment(user, id);
    }

    public void upVoteComment(User user, long id) {
        commentService.upVoteComment(user, id);
    }

    public void addCommentReply(long commentId, CommentReply commentReply) {
        commentService.addCommentReply(commentId,commentReply);
    }
}
