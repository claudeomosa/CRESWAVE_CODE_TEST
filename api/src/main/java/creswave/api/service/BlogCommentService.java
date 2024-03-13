package creswave.api.service;

import creswave.api.dto.BlogCommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BlogCommentService {
    BlogCommentDTO createComment(Long postId, BlogCommentDTO blogCommentDto);
    BlogCommentDTO getCommentById(Long id);
    List<BlogCommentDTO> getCommentsByPostId(Long blogPostId);
    BlogCommentDTO updateComment(Long id, BlogCommentDTO blogCommentDto);
    void deleteComment(Long id);
}
