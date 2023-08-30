package kr.anabada.anabadaserver.domain.message.repository;

import kr.anabada.anabadaserver.common.dto.DomainType;
import kr.anabada.anabadaserver.domain.message.entity.MessageOrigin;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MessageOriginRepository extends JpaRepository<MessageOrigin, Long> {
    @Query("""
            select (count(m) > 0) from MessageOrigin m
            where m.messagePostId = :messagePostId and m.messagePostType = :messagePostType and (m.sender = :postWriter or m.receiver = :postWriter)""")
    boolean isAlreadyExistChatroom(@Param("messagePostId") Long messagePostId, @Param("messagePostType") DomainType messagePostType, @Param("postWriter") User postWriter);

    @Query("""
            select m from MessageOrigin m
            where m.messagePostId = :messagePostId and m.messagePostType = :messagePostType and (m.sender = :sender or m.receiver = :sender)""")
    Optional<MessageOrigin> findChatRoom(@Param("messagePostId") Long messagePostId, @Param("messagePostType") DomainType messagePostType, @Param("sender") User sender);
}
