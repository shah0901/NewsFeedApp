package com.example.newsfeed.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post {

    private long id;
    private String content;
    private User user;
    private Date postTime;
    private String postTimeStr;
    private List<User> upVotes;
    private List<User> downVotes;
    private List<Comment> comments;

    public Post() {
        this.upVotes = new ArrayList<>();
        this.downVotes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

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

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    public String getPostTimeStr() {
        return postTimeStr;
    }

    public void setPostTimeStr(String postTimeStr) {
        this.postTimeStr = postTimeStr;
    }

    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", postedBy=" + user.getUsername() +
                ", posted=" + postTimeStr +
                ", upVotes=" + upVotes.size() +
                ", downVotes=" + downVotes.size() +
                ", comments=" + comments.size() +
                '}';
    }
}
