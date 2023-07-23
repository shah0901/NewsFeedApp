package com.example.newsfeed.Repositories.CommentRepliesRepository;

import com.example.newsfeed.Models.CommentReply;
import com.example.newsfeed.Repositories.CommentRepliesRepository.CommentRepliesRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentRepliesRepositoryDB implements CommentRepliesRepository {

    private final HashMap<Long, List<CommentReply>> commentReplies;

    public CommentRepliesRepositoryDB() {
        this.commentReplies = new HashMap<>();
    }

    public void save(long commentId) {
        commentReplies.putIfAbsent(commentId, new ArrayList<>());
    }
    public void addCommentReply(long commentId, CommentReply commentReply) {
        commentReplies.get(commentId).add(commentReply);
    }
    public List<CommentReply> getCommentReplies(long commentId) {
        return commentReplies.get(commentId);
    }

    public void removeCommentReply(long commentId, CommentReply commentReply) {
        commentReplies.get(commentId).removeIf(c -> c.getId() == commentReply.getId());
    }


}
