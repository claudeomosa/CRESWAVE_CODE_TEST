package creswave.api.service;
/*
* Service for BlogComment
* This interface is used to define the methods for BlogComment, such as createComment, getCommentById, getCommentsByPostId, updateComment, and deleteComment.
* The methods are implemented in the BlogCommentServiceImpl class.
*/
import creswave.api.dto.BlogCommentDTO;

import java.util.List;

public interface BlogCommentService {
    BlogCommentDTO createComment(Long postId, BlogCommentDTO blogCommentDto);
    BlogCommentDTO getCommentById(Long id);
    List<BlogCommentDTO> getCommentsByPostId(Long blogPostId);
    BlogCommentDTO updateComment(Long id, BlogCommentDTO blogCommentDto);
    void deleteComment(Long id);
}
