package kr.anabada.anabadaserver.domain.message.repository;

import kr.anabada.anabadaserver.domain.message.entity.MessageOrigin;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageOriginRepository extends JpaRepository<MessageOrigin, Long> {
    Optional<MessageOrigin> findByReceiverOrSender(User receiver, User sender);
}
