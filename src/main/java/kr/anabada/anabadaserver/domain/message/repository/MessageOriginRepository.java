package kr.anabada.anabadaserver.domain.message.repository;

import kr.anabada.anabadaserver.domain.message.entity.MessageOrigin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageOriginRepository extends JpaRepository<MessageOrigin, Long> {
}
