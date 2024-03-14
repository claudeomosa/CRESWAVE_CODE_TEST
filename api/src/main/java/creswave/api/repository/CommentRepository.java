package creswave.api.repository;

import creswave.api.model.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<BlogComment, Long> {
    List<BlogComment> findByPostId(Long postId);
}
