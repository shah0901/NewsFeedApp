package com.example.newsfeed.Repositories.CommentRepliesRepository;

import com.example.newsfeed.Models.CommentReply;

import java.util.List;

public interface CommentRepliesRepository {
    void save(long id);

    List<CommentReply> getCommentReplies(long commentId);

    void addCommentReply(long id, CommentReply comment);
}
