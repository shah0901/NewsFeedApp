package com.example.newsfeed.Repositories.Upvote_DownVoteRepository;

import com.example.newsfeed.Exception.PostNotfoundException;
import com.example.newsfeed.Models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Upvote_DownVoteRepositoryDB implements Upvote_DownVoteRepository {

    private final HashMap<Long, List<User>> upVotes;
    private final HashMap<Long, List<User>> downVotes;

    public Upvote_DownVoteRepositoryDB() {
        this.upVotes = new HashMap<>();
        this.downVotes = new HashMap<>();
    }


    public void upVote(long postId, User user) {
        upVotes.get(postId).add(user);
    }

    public void downVote(long postId, User user) {
        downVotes.get(postId).add(user);
    }

    public void save(long postId) {
        upVotes.putIfAbsent(postId, new ArrayList<>());
        downVotes.putIfAbsent(postId, new ArrayList<>());
    }

    public List<User> getUpVotes(long postId) {
        if(upVotes.get(postId) == null){
            throw new PostNotfoundException("No post found with id: " + postId);
        }
        return upVotes.get(postId);
    }

//    public Map<Long, List<User>> getUpVotess() {
//        return upVotes;
//    }
//    public HashMap<Long, List<User>> getDownVotess() {
//        return downVotes;
//    }
    public List<User> getDownVotes(long postId) {
        if (downVotes.get(postId) == null) {
            throw new PostNotfoundException("No post found with id: " + postId);
        }
        return downVotes.get(postId);
    }

    public void removeUpVote(long postId, User user) {
        if(upVotes.get(postId) == null){
            throw new PostNotfoundException("No post found with id: " + postId);
        }
        for(User u: upVotes.get(postId)){
            if(u.getUsername().equals(user.getUsername())){
                upVotes.get(postId).remove(u);
                return;
            }
        }
    }

    public void removeDownVote(long postId, User user) {
        if(downVotes.get(postId) == null){
            throw new PostNotfoundException("No post found with id: " + postId);
        }
        for(User u: downVotes.get(postId)){
            if(u.getUsername().equals(user.getUsername())){
                downVotes.get(postId).remove(u);
                return;
            }
        }
    }
}
