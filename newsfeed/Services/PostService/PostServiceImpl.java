package com.example.newsfeed.Services.PostService;

import com.example.newsfeed.Exception.PostNotfoundException;
import com.example.newsfeed.Models.Comment;
import com.example.newsfeed.Models.Post;
import com.example.newsfeed.Models.User;
import com.example.newsfeed.Repositories.PostRepository.PostRepository;
import com.example.newsfeed.Repositories.Upvote_DownVoteRepository.Upvote_DownVoteRepository;
import com.example.newsfeed.Services.CommentService.CommentService;
import com.example.newsfeed.Services.CommentService.CommentServiceImpl;
import com.example.newsfeed.Services.DateConverterService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final Upvote_DownVoteRepository upvote_downVoteRepository;
    private final CommentService commentService;

    public PostServiceImpl(PostRepository postRepository, Upvote_DownVoteRepository upvoteDownVoteRepository, CommentService commentService) {
        this.postRepository = postRepository;
        this.upvote_downVoteRepository = upvoteDownVoteRepository;
        this.commentService = commentService;
    }

    public Post save(Post post) {
        Post p = postRepository.save(post);
        upvote_downVoteRepository.save(p.getId());
        return p;
    }

    public Post findById(long id) {
        Post post;
        try {
            post = postRepository.findById(id);
            post.setUpVotes(upvote_downVoteRepository.getUpVotes(id));
            post.setDownVotes(upvote_downVoteRepository.getDownVotes(id));
            post.setComments(commentService.getCommentsByPostId(id));
            post.setPostTimeStr(post.getPostTime().toString());
            return post;
        } catch (PostNotfoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void upVotePost(User user, long postId) {
        try {
            for (User u : upvote_downVoteRepository.getUpVotes(postId)) {
                if (u.getUsername().equals(user.getUsername())) {
                    upvote_downVoteRepository.removeUpVote(postId, user);
                    return;
                }
            }
            upvote_downVoteRepository.upVote(postId, user);
        } catch (PostNotfoundException e) {
            System.out.println(e.getMessage());
        }

    }
    public void downVotePost(User user, long postId) {
        try {
            for (User u : upvote_downVoteRepository.getDownVotes(postId)) {
                if (u.getUsername().equals(user.getUsername())) {
                    upvote_downVoteRepository.removeDownVote(postId, user);
                    return;
                }
            }
            upvote_downVoteRepository.downVote(postId, user);
        }
        catch (PostNotfoundException e){
            System.out.println(e.getMessage());
        }
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        for (Post post : postRepository.findAll()) {
            post.setDownVotes(upvote_downVoteRepository.getDownVotes(post.getId()));
            post.setUpVotes(upvote_downVoteRepository.getUpVotes(post.getId()));
            post.setPostTimeStr(changeDate(post.getPostTime()));
            List<Comment> comments = commentService.getCommentsByPostId(post.getId());
            for(Comment comment:comments){
                comment.setUpVotes(upvote_downVoteRepository.getUpVotes(comment.getId()));
                comment.setDownVotes(upvote_downVoteRepository.getDownVotes(comment.getId()));
            }
            post.setComments(comments);
            posts.add(post);
        }
        return posts;
    }

    public List<Post> findByUser(User user) {
        List<Post> posts = postRepository.findByUser(user);
        for (Post post : posts) {
            post.setPostTimeStr(changeDate(post.getPostTime()));
            post.setComments(commentService.getCommentsByPostId(post.getId()));
            post.setDownVotes(upvote_downVoteRepository.getDownVotes(post.getId()));
            post.setUpVotes(upvote_downVoteRepository.getUpVotes(post.getId()));
        }
        return posts;
    }

    private String changeDate(Date postTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return DateConverterService.convertDate(dateFormat.format(postTime));
    }
}
