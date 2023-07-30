package kr.anabada.anabadaserver.common.repository;

import kr.anabada.anabadaserver.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
