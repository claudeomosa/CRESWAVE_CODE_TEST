package creswave.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BlogCommentDTO {
    private Long id;
    private String text;
    private BlogPostDTO post;
    private UserDTO user;
}
