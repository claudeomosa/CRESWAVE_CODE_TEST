package creswave.api.service.impl;

import creswave.api.dto.BlogPostDTO;
import creswave.api.model.BlogPost;
import creswave.api.repository.PostRepository;
import creswave.api.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogPostServiceImpl implements BlogPostService {
    private final PostRepository blogPostRepository;

    @Autowired
    public BlogPostServiceImpl(PostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public BlogPostDTO createPost(BlogPostDTO blogPostDto) {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setContent(blogPostDto.getContent());
        return toDto(blogPostRepository.save(blogPost));
    }

    @Override
    public BlogPostDTO getPostById(Long id) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow();
        return toDto(blogPost);
    }

    @Override
    public List<BlogPostDTO> getAllPosts() {
        List<BlogPost> blogPosts = blogPostRepository.findAll();
        return blogPosts.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BlogPostDTO updatePost(Long id, BlogPostDTO blogPostDto) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow();
        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setContent(blogPostDto.getContent());
        return toDto(blogPostRepository.save(blogPost));
    }

    private BlogPostDTO toDto(BlogPost save) {
        BlogPostDTO dto = new BlogPostDTO();
        dto.setId(save.getId());
        dto.setTitle(save.getTitle());
        dto.setContent(save.getContent());
        return dto;
    }

    @Override
    public void deletePost(Long id) {
        blogPostRepository.deleteById(id);
    }
}
