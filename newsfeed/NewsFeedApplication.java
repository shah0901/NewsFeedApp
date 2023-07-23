package com.example.newsfeed;


import com.example.newsfeed.Controller.CommentController;
import com.example.newsfeed.Controller.PostController;
import com.example.newsfeed.Controller.UserController;
import com.example.newsfeed.Exception.PostNotfoundException;
import com.example.newsfeed.Exception.UserNotFoundException;
import com.example.newsfeed.Models.Comment;
import com.example.newsfeed.Models.CommentReply;
import com.example.newsfeed.Models.Post;
import com.example.newsfeed.Models.User;
import com.example.newsfeed.Repositories.CommentRepliesRepository.CommentRepliesRepository;
import com.example.newsfeed.Repositories.CommentRepliesRepository.CommentRepliesRepositoryDB;
import com.example.newsfeed.Repositories.CommentRepository.CommentRepository;
import com.example.newsfeed.Repositories.CommentRepository.CommentRepositoryDB;
import com.example.newsfeed.Repositories.PostRepository.PostRepository;
import com.example.newsfeed.Repositories.PostRepository.PostRepositoryDB;
import com.example.newsfeed.Repositories.Upvote_DownVoteRepository.Upvote_DownVoteRepository;
import com.example.newsfeed.Repositories.Upvote_DownVoteRepository.Upvote_DownVoteRepositoryDB;
import com.example.newsfeed.Repositories.UserRepository.UserRepository;
import com.example.newsfeed.Repositories.UserRepository.UserRepositoryDB;
import com.example.newsfeed.Repositories.User_User_FollowRepository.User_User_FollowRepository;
import com.example.newsfeed.Repositories.User_User_FollowRepository.User_User_FollowRepositoryDB;
import com.example.newsfeed.Services.CommentService.CommentService;
import com.example.newsfeed.Services.CommentService.CommentServiceImpl;
import com.example.newsfeed.Services.NewsFeedService.NewsFeedServiceImpl;
import com.example.newsfeed.Services.PostService.PostService;
import com.example.newsfeed.Services.PostService.PostServiceImpl;
import com.example.newsfeed.Services.UserService.UserServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Scanner;


/**
 * Frontend for the NewsFeed Console application
 * Description: This class is the entry point for the NewsFeed Console application.
 * Designed by: Shahnawaz Khan
 * Date created: 20/7/2021
 */

public class NewsFeedApplication {
    static CommentRepository commentRepository = new CommentRepositoryDB();
    static CommentRepliesRepository commentRepliesRepository = new CommentRepliesRepositoryDB();
    static CommentService commentService = new CommentServiceImpl(commentRepository, commentRepliesRepository);
    static CommentController commentController = new CommentController(commentService);
    private static final Upvote_DownVoteRepository upvoteDownVoteRepository = new Upvote_DownVoteRepositoryDB();
    static PostRepository postRepository = new PostRepositoryDB();
    static PostService postService = new PostServiceImpl(postRepository, upvoteDownVoteRepository, commentService);
    static PostController postController = new PostController(postService);
    static UserRepository userRepository = new UserRepositoryDB();
    static User_User_FollowRepository userUserFollowRepository = new User_User_FollowRepositoryDB();
    static UserServiceImpl userService = new UserServiceImpl(userRepository, userUserFollowRepository, postRepository);
    static UserController userController = new UserController(userService);

    static NewsFeedServiceImpl newsFeedService = new NewsFeedServiceImpl(postService);


    public static void main(String[] args) {
        //  Filling some data for testing
        fillData();

        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to NewsFeed Console Application");
        System.out.println("Please select an option");
        while (true) {
            System.out.println("------------------");
            System.out.println("1. Sign up");
            System.out.println("2. Sign in");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid choice");
                continue;
            }
            switch (choice) {
                case 1 -> {
                    System.out.println("------------------");
                    System.out.println("...Signing up...");
                    System.out.println("Enter username: ");
                    String username = scan.nextLine();
                    System.out.println("Enter password: ");
                    String password = scan.nextLine();
                    System.out.println("Enter email: ");
                    String email = scan.nextLine();
                    User user = new User(username, password, email);
                    if (userController.signUp(user)) {
                        System.out.println("User created successfully");
                        login(user);
                    }

                }
                case 2 -> {
                    System.out.println("------------------");
                    System.out.println("...Signing in...");
                    System.out.println("Enter username: ");
                    String username = scan.nextLine();
                    System.out.println("Enter password: ");
                    String password = scan.nextLine();
                    User user = userController.getUserByUsernameAndPassword(username, password);
                    if (user != null) {
                        System.out.println("Welcome " + user.getUsername());
                        login(user);
                    }

                }
                case 3 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
            }
        }


    }

    private static void fillData() {
        User user1 = new User("Shah", "123", "Shah");
        userController.signUp(user1);
        Post post = new Post();
        post.setUser(user1);
        post.setContent("This is my first post");
        Date date = new Date();
        date.setTime(date.getTime() - 1000 * 60 * 60 * 24);
        post.setPostTime(date);
        postController.createPost(post);
        User user2 = new User("Shah1", "123", "Shah1");
        userController.signUp(user2);
        Post post1 = new Post();
        post1.setUser(user2);
        post1.setContent("This is my second post");
        post1.setPostTime(new Date());
        postController.createPost(post1);
        userController.followUser(user1, user2);
    }

    private static void login(User user) {
        System.out.println("You are logged in as " + user.getUsername());
        boolean continueLoop = true;
        while (continueLoop) {
            Scanner scan = new Scanner(System.in);
            System.out.println("------------------");
            System.out.println("1. Show news feed");
            System.out.println("2. Create Post");
            System.out.println("3. View Profile");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid choice");
                continue;
            }
            switch (choice) {
                case 1 -> {
                    System.out.println("------------------");
                    if (newsFeedService.getPosts().size() == 0) {
                        System.out.println("No posts to show, Post something and that will appear here on the timeline, Thanks!!");
                    } else {
                        showNewsFeed(user);
                    }
                }
                case 2 -> {
                    System.out.println("------------------");
                    System.out.println("Creating post");
                    Post post = new Post();
                    System.out.println("Enter post Content: ");
                    post.setContent(scan.nextLine());
                    post.setUser(user);
                    post.setPostTime(new Date());
                    Post postCreated = postController.createPost(post);
                    System.out.println("------------------");
                    System.out.println("Post created successfully");
                    System.out.println(postCreated.toString());
                }
                case 3 -> {
                    System.out.println("------------------");
                    viewProfile(user);

                }
                case 4 -> {
                    System.out.println("------------------");
                    System.out.println("Logging out");
                    continueLoop = false;
                }
                default -> {
                    System.out.println("Invalid choice");
                }

            }
        }
    }

    private static void viewProfile(User user) {
        boolean continueLoop = true;
        while (continueLoop) {
            User user1 = userController.getUserByUsername(user.getUsername());
            Scanner scan = new Scanner(System.in);
            System.out.println("------------------");
            System.out.println(user1.toString());
            System.out.println("1. Follow user");
            System.out.println("2. Unfollow user");
            System.out.println("3. View followers");
            System.out.println("4. View following");
            System.out.println("5. View posts");
            System.out.println("6. Go back");
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid choice");
                continue;
            }
            switch (choice) {
                case 1 -> {
                    System.out.println("------------------");
                    System.out.println("Enter username to follow: ");
                    String username = scan.nextLine();
                    try {
                        User userToFollow = userController.getUserByUsername(username);
                        userController.followUser(user, userToFollow);
                        System.out.println("User followed successfully");
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("------------------");
                    System.out.println("Enter username to unfollow: ");
                    String username = scan.nextLine();
                    try {
                        User userToUnfollow = userController.getUserByUsername(username);
                        userController.unfollowUser(user, userToUnfollow);
                        System.out.println("User unfollowed successfully");
                    } catch (UserNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("------------------");
                    System.out.println("Followers");
                    List<User> followers = userController.getFollowers(user);
                    if (followers.isEmpty()) {
                        System.out.println("No followers found");
                    } else {
                        for (int i = 0; i < followers.size(); i++) {
                            System.out.println((i + 1) + ": " + followers.get(i).getUsername());
                        }
                    }
                }
                case 4 -> {
                    System.out.println("------------------");
                    System.out.println("Following");
                    List<User> userFollowing = userController.getFollowing(user);
                    if (userFollowing.isEmpty()) {
                        System.out.println("No following found");
                    } else {
                        for (int i = 0; i < userFollowing.size(); i++) {
                            System.out.println((i + 1) + ": " + userFollowing.get(i).getUsername());
                        }
                    }
                }
                case 5 -> {
                    System.out.println("------------------");
                    System.out.println("Posts");
                    List<Post> postsByUser = postController.getPostByUserId(user);
                    if (postsByUser.isEmpty()) {
                        System.out.println("No posts found");
                    } else {
                        postsActions(user, postsByUser);
                    }
                }
                case 6 -> {
                    continueLoop = false;
                }
                default -> {
                    System.out.println("Invalid choice");
                }
            }
        }
    }

    private static void showNewsFeed(User user) {
        boolean continueLoop = true;
        Scanner scan = new Scanner(System.in);
        while (continueLoop) {
            System.out.println("------------------");
            System.out.println(user.getUsername() + "'s News Feed");
            for (Post post : newsFeedService.getPosts()) {
                System.out.println(post.toString());
            }
            System.out.println("------------------");
            System.out.println("1. UpVote post");
            System.out.println("2. DownVote post");
            System.out.println("3. Comment on post");
            System.out.println("4. View UpVotes");
            System.out.println("5. View DownVotes");
            System.out.println("6. View comments");
            System.out.println("7. Go back");
            int choice;
            try {
                System.out.println("Enter your choice: ");
                choice = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid choice");
                continue;
            }
            switch (choice) {
                case 1 -> {
                    System.out.println("------------------");
                    System.out.println("Enter post id: ");
                    long postId = Long.parseLong(scan.nextLine());
                    postController.upVotePost(user, postId);
                }
                case 2 -> {
                    System.out.println("------------------");
                    System.out.println("Enter post id: ");
                    long postId = Long.parseLong(scan.nextLine());
                    postController.downVotePost(user, postId);
                }
                case 3 -> {
                    System.out.println("------------------");
                    System.out.println("Enter post id: ");
                    long postId = Long.parseLong(scan.nextLine());
                    Post post = postController.getPostById(postId);
                    if (post != null) {
                        System.out.println("Enter comment: ");
                        String com = scan.nextLine();
                        Comment comment = new Comment();
                        comment.setContent(com);
                        comment.setUser(user);
                        comment.setPost(post);
                        commentController.addComment(comment);
                    }
                }
                case 4 -> {
                    System.out.println("------------------");
                    System.out.println("Enter post id: ");
                    long postId = Long.parseLong(scan.nextLine());
                    Post post = postController.getPostById(postId);
                    if (post != null) {
                        for (User upVote : post.getUpVotes()) {
                            System.out.println(upVote.getUsername());
                        }
                    }
                }
                case 5 -> {
                    System.out.println("------------------");
                    System.out.println("Enter post id: ");
                    long postId = Long.parseLong(scan.nextLine());
                    Post post = postController.getPostById(postId);
                    if (post != null) {
                        for (User downVote : post.getDownVotes()) {
                            System.out.println(downVote.getUsername());
                        }
                    }
                }
                case 6 -> {
                    viewComments(user, scan);
                }
                case 7 -> {
                    continueLoop = false;
                }
                default -> {
                    System.out.println("Invalid choice");
                }
            }
        }
    }

    private static void postsActions(User user, List<Post> postsByUser) {
        boolean continueLoop = true;
        Scanner scan = new Scanner(System.in);
        while (continueLoop) {
            for (Post post : postsByUser) {
                System.out.println(post.toString());
            }
            System.out.println("------------------");
            System.out.println("1. View UpVotes");
            System.out.println("2. View DownVotes");
            System.out.println("3. View Comments");
            System.out.println("4. Go back");
            int choice;
            try {
                System.out.println("Enter your choice: ");
                choice = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid choice");
                continue;
            }
            switch (choice) {
                case 1 -> {
                    Post post1 = GetVotes(user, scan);
                    System.out.println("UpVotes");
                    try {
                        List<User> upVotes = post1.getUpVotes();
                        if (upVotes.isEmpty()) {
                            System.out.println("No upVotes for this post!!");
                        } else {
                            for (User upVote : upVotes) {
                                System.out.println(upVote.getUsername());
                            }
                        }
                    } catch (PostNotfoundException e) {
                        System.out.println(e.getMessage());
                    } catch (NullPointerException e) {
                        System.out.println("Post not found posted by " + user.getUsername());
                    }
                }
                case 2 -> {
                    Post post1 = GetVotes(user, scan);
                    System.out.println("DownVotes");
                    try {
                        List<User> downVotes = post1.getDownVotes();
                        if (downVotes.isEmpty()) {
                            System.out.println("No downVotes for this post!!");
                        } else {
                            for (User downVote : downVotes) {
                                System.out.println(downVote.getUsername());
                            }
                        }
                    } catch (PostNotfoundException e) {
                        System.out.println(e.getMessage());
                    } catch (NullPointerException e) {
                        System.out.println("Post not found posted by " + user.getUsername());
                    }

                }
                case 3 -> {
                    Post post = GetVotes(user, scan);
                    System.out.println("Comments");
                    try {
                        List<Comment> comments = post.getComments();
                        if (comments.isEmpty()) {
                            System.out.println("No comments for this post!!");
                        } else {
                            commentActions(user, comments);
                        }
                    } catch (PostNotfoundException e) {
                        System.out.println(e.getMessage());
                    } catch (NullPointerException e) {
                        System.out.println("Post not found posted by " + user.getUsername());
                    }

                }
                case 4 -> {
                    continueLoop = false;
                }
                default -> {
                    System.out.println("Invalid choice");
                }
            }
        }
    }

    private static Post GetVotes(User user, Scanner scan) {
        System.out.println("------------------");
        System.out.println("Enter post id: ");
        long postId;
        try {

            postId = Long.parseLong(scan.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid post id");
            return null;
        }
        List<Post> posts = postController.getPostByUserId(user);
        Post post1 = null;
        for (Post post : posts) {
            if (post.getId() == postId) {
                post1 = post;
            }
        }
        return post1;
    }

    private static void viewComments(User user, Scanner scan) {
        System.out.println("------------------");
        System.out.println("Enter post id: ");
        long postId = Long.parseLong(scan.nextLine());
        Post post = postController.getPostById(postId);
        System.out.println("Comments");
        List<Comment> comments = post.getComments();
        if (comments.isEmpty()) {
            System.out.println("No comments for this post!!");
        } else {
            commentActions(user, comments);
        }
    }

    private static void commentActions(User user, List<Comment> comments) {
        boolean continueLoop = true;
        Scanner scan = new Scanner(System.in);
        while (continueLoop) {
            for (Comment comment : comments) {
                System.out.println(comment.toString());
            }
            System.out.println("------------------");
            System.out.println("1. UpVote comment");
            System.out.println("2. DownVote comment");
            System.out.println("3. Reply on comment");
            System.out.println("4. View Comment Replies");
            System.out.println("4. Go back");
            int choice;
            try {
                choice = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid choice");
                continue;
            }
            switch (choice) {
                case 1 -> {
                    System.out.println("------------------");
                    System.out.println("Enter comment id: ");
                    long commentId;
                    try {
                        commentId = Long.parseLong(scan.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid choice");
                        continue;
                    }
                    Comment comment = commentController.getCommentById(commentId);
                    System.out.println("UpVotes");
                    commentController.upVoteComment(user, comment.getId());
                }
                case 2 -> {
                    System.out.println("------------------");
                    System.out.println("Enter comment id: ");
                    long commentId;
                    try {
                        commentId = Long.parseLong(scan.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid choice");
                        continue;
                    }
                    Comment comment = commentController.getCommentById(commentId);
                    System.out.println("DownVotes");
                    commentController.downVoteComment(user, comment.getId());
                }
                case 3 -> {
                    System.out.println("------------------");
                    System.out.println("Enter comment id: ");
                    long commentId;
                    try {
                        commentId = Long.parseLong(scan.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid choice");
                        continue;
                    }
                    Comment comment = commentController.getCommentById(commentId);
                    System.out.println("Enter comment: ");
                    String com = scan.nextLine();
                    CommentReply commentReply = new CommentReply();
                    commentReply.setContent(com);
                    commentReply.setUser(user);
                    commentReply.setComment(comment);
                    commentController.addCommentReply(commentId, commentReply);
                }
                case 4 -> {
                    System.out.println("------------------");
                    System.out.println("Enter comment id: ");
                    long commentId;
                    try {
                        commentId = Long.parseLong(scan.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid choice");
                        continue;
                    }
                    Comment comment = commentController.getCommentById(commentId);
                    List<CommentReply> commentReplies = comment.getCommentReplies();
                    if (commentReplies.isEmpty()) {
                        System.out.println("No replies for this comment!!");
                    } else {
                        for (CommentReply commentReply : commentReplies) {
                            System.out.println(commentReply.toString());
                        }
                    }
                }
                case 5 -> {
                    continueLoop = false;
                }
                default -> {
                    System.out.println("Invalid choice");
                }
            }
        }
    }

}
