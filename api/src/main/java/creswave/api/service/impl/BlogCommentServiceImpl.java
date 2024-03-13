package creswave.api.service.impl;

import creswave.api.dto.BlogCommentDTO;
import creswave.api.model.BlogComment;
import creswave.api.repository.CommentRepository;
import creswave.api.service.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogCommentServiceImpl implements BlogCommentService {

    private final CommentRepository blogCommentRepository;

    @Autowired
    public BlogCommentServiceImpl(CommentRepository blogCommentRepository) {
        this.blogCommentRepository = blogCommentRepository;
    }

    @Override
    public BlogCommentDTO createComment(Long postId, BlogCommentDTO blogCommentDto) {
        BlogComment blogComment = new BlogComment();
        blogComment.setText(blogCommentDto.getText());
        return toDto(blogCommentRepository.save(blogComment));
    }

    @Override
    public BlogCommentDTO getCommentById(Long id) {
        BlogComment blogComment = blogCommentRepository.findById(id).orElseThrow();
        return toDto(blogComment);
    }

    @Override
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
        return dto;
    }

    @Override
    public void deleteComment(Long id) {
        blogCommentRepository.deleteById(id);
    }
}
