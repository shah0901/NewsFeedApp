package com.example.newsfeed.Models;

public class CommentReply {
    private long id;
    private String content;
    private User user;
    private Comment comment;

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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
    public String toString(){
        return "CommentReply{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user +
                '}';
    }
}
