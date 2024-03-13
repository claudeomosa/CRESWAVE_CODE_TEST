package creswave.api.service;

import creswave.api.dto.BlogPostDTO;

import java.util.List;

public interface BlogPostService {
    BlogPostDTO createPost(BlogPostDTO blogPostDto);
    BlogPostDTO getPostById(Long id);
    List<BlogPostDTO> getAllPosts();
    BlogPostDTO updatePost(Long id, BlogPostDTO blogPostDto);
    void deletePost(Long id);
}
