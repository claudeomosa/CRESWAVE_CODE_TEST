package creswave.api.service.impl;

import creswave.api.dto.BlogPostDTO;
import creswave.api.model.BlogPost;
import creswave.api.model.Role;
import creswave.api.model.User;
import creswave.api.repository.PostRepository;
import creswave.api.repository.UserRepository;
import creswave.api.service.BlogPostService;
import creswave.api.service.JwtService;
import creswave.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogPostServiceImpl implements BlogPostService {
    private final PostRepository blogPostRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public BlogPostServiceImpl(PostRepository blogPostRepository, UserRepository userRepository, JwtService jwtService, UserService userService) {
        this.blogPostRepository = blogPostRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public BlogPostDTO createPost(BlogPostDTO blogPostDto) {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setContent(blogPostDto.getContent());
        blogPost.setSlug(blogPostDto.getTitle().toLowerCase().replace(" ", "-"));
        User currentUser = this.getCurrentUser();
        blogPost.setUser(currentUser);
        return toDto(blogPostRepository.save(blogPost));
    }

    private User getCurrentUser() {
       UserDetails loggedInUserDetails =  jwtService.getLoggedInUser();
       Optional<User> user = userRepository.findByUsername(loggedInUserDetails.getUsername());

       return user.orElseThrow();
    }

    @Override
    public BlogPostDTO getPostById(Long id) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow();
        return toDto(blogPost);
    }

    @Override
    public List<BlogPostDTO> getAllPosts() {
        return blogPostRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }



    @Override
    public List<BlogPostDTO> getAllPostsWithSorting(String sortBy) {
        return blogPostRepository.findAll(
                Sort.by(sortBy))
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BlogPostDTO> findPostsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return blogPostRepository.findAll(pageable)
                .map(this::toDto);
    }

    @Override
    public BlogPostDTO updatePost(Long id, BlogPostDTO blogPostDto) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow();
        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setContent(blogPostDto.getContent());
        User currentUser = getCurrentUser();
        if (blogPost.getUser().getId().equals(currentUser.getId()) || currentUser.getRole().equals(Role.ADMIN)) {
            return toDto(blogPostRepository.save(blogPost));
        } else {
            throw new IllegalArgumentException("You are not authorized to update this post");
        }
    }

    private BlogPostDTO toDto(BlogPost save) {
        BlogPostDTO dto = new BlogPostDTO();
        dto.setId(save.getId());
        dto.setTitle(save.getTitle());
        dto.setContent(save.getContent());
        dto.setSlug(save.getSlug());
        dto.setCreationDate(save.getCreationDate());

        User user = save.getUser();
        if (user != null) {
            dto.setUser(userService.toDto(user));
        }
        return dto;
    }

    @Override
    public void deletePost(Long id) {
        BlogPost existingPost = blogPostRepository.findById(id).orElseThrow();
        User currentUser = getCurrentUser();
        if (existingPost.getUser().getId().equals(currentUser.getId()) || currentUser.getRole().equals(Role.ADMIN)) {
            blogPostRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("You are not authorized to delete this post");
        }
    }
}
