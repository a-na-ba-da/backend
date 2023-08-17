package kr.anabada.anabadaserver.domain.message.repository;

import kr.anabada.anabadaserver.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {
    /**
     * 메세지를 일괄(Bulk)로 읽음 처리한다.
     *
     * @param messagesToMarkRead 읽음 처리할 메세지들
     */
    @Modifying
    @Query("UPDATE Message m " +
            "SET m.isRead = true " +
            "WHERE m IN :messagesToMarkRead")
    void markMessagesAsRead(@Param("messagesToMarkRead") List<Message> messagesToMarkRead);
}
