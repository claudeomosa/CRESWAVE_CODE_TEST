package creswave.api.service.impl;

import creswave.api.dto.BlogCommentDTO;
import creswave.api.dto.UserDTO;
import creswave.api.model.BlogComment;
import creswave.api.model.BlogPost;
import creswave.api.model.User;
import creswave.api.repository.CommentRepository;
import creswave.api.repository.UserRepository;
import creswave.api.service.BlogCommentService;
import creswave.api.service.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogCommentServiceImpl implements BlogCommentService {

    private final CommentRepository blogCommentRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Autowired
    public BlogCommentServiceImpl(CommentRepository blogCommentRepository, JwtService jwtService, UserRepository userRepository) {
        this.blogCommentRepository = blogCommentRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public BlogCommentDTO createComment(Long postId, BlogCommentDTO blogCommentDto) {
        BlogComment blogComment = new BlogComment();
        blogComment.setText(blogCommentDto.getText());
        BlogPost blogPost = new BlogPost();
        blogPost.setId(postId);
        blogComment.setPost(blogPost);
        blogComment.setUser(getCurrentUser());
        return toDto(blogCommentRepository.save(blogComment));
    }

    private User getCurrentUser() {
        UserDetails loggedInUserDetails =  jwtService.getLoggedInUser();
        Optional<User> user = userRepository.findByUsername(loggedInUserDetails.getUsername());

        return user.orElseThrow();
    }

    @Override
    @Transactional
    public BlogCommentDTO getCommentById(Long id) {
        BlogComment blogComment = blogCommentRepository.findById(id).orElseThrow();
        return toDto(blogComment);
    }

    @Override
    @Transactional
    public List<BlogCommentDTO> getCommentsByPostId(Long blogPostId) {
        List<BlogComment> blogComments = blogCommentRepository.findByPostId(blogPostId);
        return blogComments.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BlogCommentDTO updateComment(Long id, BlogCommentDTO blogCommentDto) {
        BlogComment blogComment = blogCommentRepository.findById(id).orElseThrow();
        blogComment.setText(blogCommentDto.getText());
        return toDto(blogCommentRepository.save(blogComment));
    }

    private BlogCommentDTO toDto(BlogComment blogComment) {
        BlogCommentDTO dto = new BlogCommentDTO();
        dto.setId(blogComment.getId());
        dto.setText(blogComment.getText());
        dto.setUser(toDtoUser(blogComment.getUser()));
        return dto;
    }

    private UserDTO toDtoUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }

    @Override
    public void deleteComment(Long id) {
        blogCommentRepository.deleteById(id);
    }
}
