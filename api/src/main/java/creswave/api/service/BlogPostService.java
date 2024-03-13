package creswave.api.service;

import creswave.api.dto.BlogPostDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogPostService {
    BlogPostDTO createPost(BlogPostDTO blogPostDto);
    BlogPostDTO getPostById(Long id);
    List<BlogPostDTO> getAllPosts();
    BlogPostDTO updatePost(Long id, BlogPostDTO blogPostDto);
    void deletePost(Long id);
}
