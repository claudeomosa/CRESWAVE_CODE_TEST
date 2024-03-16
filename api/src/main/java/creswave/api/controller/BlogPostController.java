package creswave.api.controller;
/*
* The BlogPostController class is used to handle the blog posts.
* It has endpoints for creating, updating, deleting and getting blog posts.
*/
import creswave.api.service.BlogPostService;
import creswave.api.dto.BlogPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {
    @Autowired
    private BlogPostService blogPostService;

    @PostMapping
    public ResponseEntity<BlogPostDTO> createPost(@RequestBody BlogPostDTO blogPostDto) {
        if (blogPostDto.getTitle() == null || blogPostDto.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (blogPostDto.getContent() == null || blogPostDto.getContent().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(blogPostService.createPost(blogPostDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(blogPostService.getPostById(id));
    }

    @GetMapping
    public ResponseEntity<List<BlogPostDTO>> getAllPosts() {
        return ResponseEntity.ok(blogPostService.getAllPosts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPostDTO> updatePost(@PathVariable Long id, @RequestBody BlogPostDTO blogPostDto) {
        return ResponseEntity.ok(blogPostService.updatePost(id, blogPostDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        blogPostService.deletePost(id);
        return ResponseEntity.ok().build();
    }

}
