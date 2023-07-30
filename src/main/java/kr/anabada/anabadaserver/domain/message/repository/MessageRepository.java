package kr.anabada.anabadaserver.domain.message.repository;

import kr.anabada.anabadaserver.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
