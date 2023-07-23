package com.example.newsfeed.Services.CommentService;

import com.example.newsfeed.Exception.CommentNotFoundException;
import com.example.newsfeed.Models.Comment;
import com.example.newsfeed.Models.CommentReply;
import com.example.newsfeed.Models.User;
import com.example.newsfeed.Repositories.CommentRepliesRepository.CommentRepliesRepository;
import com.example.newsfeed.Repositories.CommentRepository.CommentRepository;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentRepliesRepository commentRepliesRepository;

    public CommentServiceImpl(CommentRepository commentRepository, CommentRepliesRepository commentRepliesRepository) {
        this.commentRepository = commentRepository;
        this.commentRepliesRepository = commentRepliesRepository;
    }

    public Comment addComment(Comment comment) {
        Comment parentComment = commentRepository.save(comment);
        commentRepliesRepository.save(parentComment.getId());
        return parentComment;
    }

    public void addCommentReply(long id, CommentReply comment) {
        commentRepliesRepository.addCommentReply(id, comment);
    }
    public Comment getCommentById(long commentId) throws CommentNotFoundException {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        comment.setComments(commentRepliesRepository.getCommentReplies(commentId));
        return comment;
    }


    public List<Comment> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        for(Comment comment: comments){
            comment.setUpVotes(commentRepository.getUpVotes(comment.getId()));
            comment.setDownVotes(commentRepository.getDownVotes(comment.getId()));
            comment.setComments(commentRepliesRepository.getCommentReplies(comment.getId()));
        }
        return comments;
    }

    public void downVoteComment(User user, long id) {
        for (User user1 : commentRepository.getDownVotes(id)) {
            if (user1.getUsername().equals(user.getUsername())) {
                commentRepository.removeDownVote(user, id);
                return;
            }
        }
        commentRepository.downVote(user, id);
    }

    public void upVoteComment(User user, long id) {
        for (User user1 : commentRepository.getUpVotes(id)) {
            if (user1.getUsername().equals(user.getUsername())) {
                commentRepository.removeUpVote(user, id);
                return;
            }
        }
        commentRepository.upVote(user, id);
    }
}
