package kr.anabada.anabadaserver.domain.message.service;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.common.dto.DomainType;
import kr.anabada.anabadaserver.domain.ServiceTestWithoutImageUpload;
import kr.anabada.anabadaserver.domain.message.dto.MessageType;
import kr.anabada.anabadaserver.domain.message.entity.Message;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.save.service.KnowTogetherService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static kr.anabada.anabadaserver.fixture.dto.KnowTogetherFixture.createKnowTogetherOnline;
import static kr.anabada.anabadaserver.fixture.entity.UserFixture.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
class MessageServiceTest extends ServiceTestWithoutImageUpload {

    @Autowired
    private EntityManager em;

    @Autowired
    private MessageService messageService;

    @Autowired
    private KnowTogetherService knowTogetherService;

    @Test
    @DisplayName("모든 인자값이 정상이라면, 메세지를 보낼 수 있다.")
    void can_send_message_if_valid() {
        // given
        User sender = createUser("sender@naver.com", "sender");
        User receiver = createUser("receiver@naver.com", "receiver");
        em.persist(sender);
        em.persist(receiver);

        Save post = knowTogetherService.createNewKnowTogetherPost(receiver, createKnowTogetherOnline());

        // when & then
        assertThat(messageService.sendMessage(sender, DomainType.KNOW_TOGETHER, post.getId(), "message"))
                .isNotNull()
                .extracting("content")
                .isEqualTo("message");
                .extracting("content", "messageType")
                .containsExactly("message", MessageType.SENDER_SEND);
    }

    @Test
    @DisplayName("A가 B에게 메세지를 보내고, B가 메세지를 조회하면 A가 보낸 메세지는 읽음 상태로 변경된다.")
    void check_message_read_status() {
        // given
        User A = createUser("aaaa@naver.com", "aaaa");
        User B = createUser("bbbb@naver.com", "bbbb");
        em.persist(A);
        em.persist(B);

        Save post = knowTogetherService.createNewKnowTogetherPost(B, createKnowTogetherOnline());
        Message msg = messageService.sendMessage(A, DomainType.KNOW_TOGETHER, post.getId(), "message");
        long chatRoomId = msg.getMessageOrigin().getId();
        em.clear();
        em.flush();

        // when
        messageService.getMyMessageDetail(B, LocalDateTime.now(), chatRoomId);

        // then
        em.clear();
        assertThat(em.find(Message.class, msg.getId()).isRead()).isTrue();
    }

    @Test
    @DisplayName("본인이 작성한 게시물에는 메세지를 보낼 수 없다.")
    void cant_send_message_to_myPost() {
        // given
        User sender = createUser("sender@naver.com", "sender");
        em.persist(sender);

        Save post = knowTogetherService.createNewKnowTogetherPost(sender, createKnowTogetherOnline());

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> messageService.sendMessage(sender, DomainType.KNOW_TOGETHER, post.getId(), "message"),
                "자신의 게시글에는 메세지를 보낼 수 없습니다.");
    }
}