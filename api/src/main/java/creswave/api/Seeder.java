package creswave.api;

import com.github.javafaker.Faker;
import creswave.api.model.BlogComment;
import creswave.api.model.BlogPost;
import creswave.api.model.Role;
import creswave.api.model.User;
import creswave.api.repository.CommentRepository;
import creswave.api.repository.PostRepository;
import creswave.api.repository.UserRepository;
import creswave.api.service.BlogCommentService;
import creswave.api.service.BlogPostService;
import creswave.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Seeder implements CommandLineRunner {
    private final UserService userService;
    private final BlogPostService blogPostService;
    private final BlogCommentService blogCommentService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public Seeder(UserService userService, BlogPostService blogPostService, BlogCommentService blogCommentService, UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userService = userService;
        this.blogPostService = blogPostService;
        this.blogCommentService = blogCommentService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        //users
        for(int i = 0; i < 30; i++) {
            User user = new User();
            user.setFullname(faker.name().fullName());
            user.setUsername(faker.name().username());
            user.setPassword(faker.internet().password());
            user.setEmail(faker.internet().emailAddress());
            user.setRole(Role.USER);

            userRepository.save(user);
        }
        //blogposts
        for(int i = 0; i < 50; i++) {
            LocalDateTime creationDate = LocalDateTime.now();
            BlogPost blogPost = new BlogPost();
            blogPost.setTitle(faker.lorem().sentence());
            blogPost.setContent(faker.lorem().paragraph());
            blogPost.setUser(userRepository.findById((long) faker.number().numberBetween(1, 10)).get());
            blogPost.setCreationDate(creationDate);
            blogPost.setSlug(blogPost.getTitle().toLowerCase().replace(" ", "-"));
            postRepository.save(blogPost);
        }
        // blogcomments
        for(int i = 0; i < 40; i++) {
            BlogComment blogComment = new BlogComment();
            blogComment.setText(faker.lorem().paragraph());
            blogComment.setUser(userRepository.findById((long) faker.number().numberBetween(1, 10)).get());
            blogComment.setPost(postRepository.findById((long) faker.number().numberBetween(1, 10)).get());
            commentRepository.save(blogComment);
        }
    }
}