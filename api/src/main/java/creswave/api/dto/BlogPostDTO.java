package creswave.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BlogPostDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime creationDate;
    private UserDTO user;
}
