package kr.anabada.anabadaserver.common.repository;

import kr.anabada.anabadaserver.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostTypeAndPostIdAndIsRemovedFalse(String postType, Long postId);
}
