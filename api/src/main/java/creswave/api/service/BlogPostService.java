package creswave.api.service;
/*
* Service for BlogPost
* This interface is used to define the methods for BlogPost, such as createPost, getPostById, getAllPosts, updatePost, and deletePost.
* The methods are implemented in the BlogPostServiceImpl class.
*/
import creswave.api.dto.BlogPostDTO;

import java.util.List;

public interface BlogPostService {
    BlogPostDTO createPost(BlogPostDTO blogPostDto);
    BlogPostDTO getPostById(Long id);
    List<BlogPostDTO> getAllPosts();
    BlogPostDTO updatePost(Long id, BlogPostDTO blogPostDto);
    void deletePost(Long id);
}
