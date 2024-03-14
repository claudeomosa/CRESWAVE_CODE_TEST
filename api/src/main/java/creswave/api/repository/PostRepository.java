package creswave.api.repository;

import creswave.api.model.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<BlogPost, Long> {
    Page<BlogPost> findByUserId(Long userId, Pageable pageable);
    @Query("SELECT p FROM BlogPost p WHERE p.title LIKE %?1%"
            + " OR p.content LIKE %?1%")
    Page<BlogPost> searchByTitleContainingOrContentContaining(String keyword, Pageable pageable);
}
