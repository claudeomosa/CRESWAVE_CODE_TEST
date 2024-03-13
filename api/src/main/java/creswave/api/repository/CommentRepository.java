package creswave.api.repository;

import creswave.api.model.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<BlogComment, Long> {
    List<BlogComment> findByPostId(Long postId);
}
