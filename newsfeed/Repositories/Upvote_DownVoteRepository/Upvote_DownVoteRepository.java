package com.example.newsfeed.Repositories.Upvote_DownVoteRepository;

import com.example.newsfeed.Models.User;

import java.util.List;

public interface Upvote_DownVoteRepository {
    void upVote(long id, User user);

    List<User> getUpVotes(long id);
    
    void removeUpVote(long id, User user);

    List<User> getDownVotes(long id);

    void removeDownVote(long id, User user);

    void downVote(long id, User user);

    void save(long id);

//    HashMap<Long, List<User>> getDownVotess();
}
