package com.example.newsfeed.Models;

import java.util.List;

public class Comment {
    private long id;
    private String content;
    private User user;
    private Post post;
    private List<User> upVotes;
    private Comment comment;
    private List<User> downVotes;
    private List<CommentReply> comments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<CommentReply> getCommentReplies() {
        return comments;
    }

    public void setCommentReplies(List<CommentReply> commentReplies) {
        this.comments = commentReplies;
    }

    public List<User> getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(List<User> upVotes) {
        this.upVotes = upVotes;
    }

    public List<User> getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(List<User> downVotes) {
        this.downVotes = downVotes;
    }

    public List<CommentReply> getComments() {
        return comments;
    }

    public void setComments(List<CommentReply> comments) {
        this.comments = comments;
    }

    public Comment getComment() {
        return comment;
    }

    public String toString(){
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", commentedBy=" + user +
                ", upVotes=" + upVotes.size() +
                ", downVotes=" + downVotes.size() +
                ", comments=" + comments.size() +
                '}';
    }
}
