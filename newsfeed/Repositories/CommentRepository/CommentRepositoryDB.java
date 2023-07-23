package com.example.newsfeed.Repositories.CommentRepository;

import com.example.newsfeed.Models.Comment;
import com.example.newsfeed.Models.User;
import com.example.newsfeed.Repositories.CommentRepository.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentRepositoryDB implements CommentRepository {
    private final List<Comment> comments;
    private long id = 0;

    public CommentRepositoryDB() {
        this.comments = new ArrayList<>();
    }

    public Comment save(Comment comment) {
        comment.setId(id++);
        comments.add(comment);
        return comment;
    }

    public Optional<Comment> findById(long commentId) {
        //        for (Comment comment : comments) {
//            if (comment.getId() == commentId) {
//                return comment;
//            }
//        }
//        throw new CommentNotFoundException("Comment not found");
        return comments.stream().filter(comment1 -> comment1.getId() == commentId).findFirst();
    }

    @Override
    public List<Comment> findByPostId(long postId) {
        List<Comment> commentsByPostId = new ArrayList<>();
        for(Comment comment : findAll()){
            if(comment.getPost().getId() == postId){
                commentsByPostId.add(comment);
            }
        }
        return commentsByPostId;
    }

    @Override
    public void downVote(User user, long id) {
        for (Comment comment : comments) {
            if (comment.getId() == id) {
                comment.getDownVotes().add(user);
            }
        }
    }

    @Override
    public void upVote(User user, long id) {
        for (Comment comment : comments) {
            if (comment.getId() == id) {
                comment.getUpVotes().add(user);
            }
        }
    }

    @Override
    public List<User> getUpVotes(long id) {
        List<User> upVotes = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getId() == id) {
                upVotes = comment.getUpVotes();
            }
        }
        return upVotes;
    }

    @Override
    public List<User> getDownVotes(long id) {
        List<User> downVotes = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getId() == id) {
                downVotes = comment.getDownVotes();
            }
        }
        return downVotes;
    }

    @Override
    public void removeUpVote(User user, long id) {
        for (Comment comment : comments) {
            if (comment.getId() == id) {
                comment.getUpVotes().remove(user);
            }
        }
    }

    @Override
    public void removeDownVote(User user, long id) {
        for (Comment comment : comments) {
            if (comment.getId() == id) {
                comment.getDownVotes().remove(user);
            }
        }
    }

    public List<Comment> findAll() {
        return comments;
    }

    public List<Comment> findByParentCommentId(long parentCommentId) {
        List<Comment> commentsByParentCommentId = new ArrayList<>();
        for(Comment comment : findAll()){
            if(comment.getComment().getId() == parentCommentId){
                commentsByParentCommentId.add(comment);
            }
        }
        return commentsByParentCommentId;
    }
}
