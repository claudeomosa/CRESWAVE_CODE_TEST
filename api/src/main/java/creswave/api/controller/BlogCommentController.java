package creswave.api.controller;

import creswave.api.dto.BlogCommentDTO;
import creswave.api.service.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class BlogCommentController {
    @Autowired
    private BlogCommentService blogCommentService;

    @PostMapping
    public ResponseEntity<BlogCommentDTO> createComment(@PathVariable Long postId, @RequestBody BlogCommentDTO blogCommentDTO) {
        return ResponseEntity.ok(blogCommentService.createComment(postId, blogCommentDTO));
    }

    @GetMapping
    public ResponseEntity<List<BlogCommentDTO>> getAllCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(blogCommentService.getCommentsByPostId(postId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<BlogCommentDTO> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody BlogCommentDTO blogCommentDto) {
        return ResponseEntity.ok(blogCommentService.updateComment(commentId, blogCommentDto));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        blogCommentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
