package kr.anabada.anabadaserver.domain.message.service;

import kr.anabada.anabadaserver.common.dto.DomainType;
import kr.anabada.anabadaserver.domain.message.dto.MessageSummaryResponse;
import kr.anabada.anabadaserver.domain.message.dto.MessageType;
import kr.anabada.anabadaserver.domain.message.entity.Message;
import kr.anabada.anabadaserver.domain.message.entity.MessageOrigin;
import kr.anabada.anabadaserver.domain.message.repository.MessageOriginRepository;
import kr.anabada.anabadaserver.domain.message.repository.MessageRepository;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.save.repository.SaveRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static kr.anabada.anabadaserver.domain.message.entity.Message.createWelcomeMessage;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageOriginRepository messageOriginRepository;
    private final SaveRepository saveRepository;

    private static MessageType getMessageType(User me, MessageOrigin messageRoom) {
        return messageRoom.getSender() == me ? MessageType.SENDER_SEND : MessageType.RECEIVER_SEND;
    }

    public List<MessageSummaryResponse> getMyAllMessageSummarized(User requester) {
        return messageRepository.getMyAllMessageSummarized(requester);
    }

    @Transactional
    public Message sendMessage(User user, DomainType postType, Long postId, String message) {
        // 처음 보내는 메세지 인지 확인, 처음이면 메세지 방 생성
        final User receiver = checkValidPostType(user, postType, postId);

        MessageOrigin messageRoom = messageOriginRepository.findByReceiverOrSender(receiver, user)
                .orElseGet(() -> {
                    // 메세지 방 생성
                    MessageOrigin messageOrigin = MessageOrigin.builder()
                            .messageType(postType)
                            .messagePostId(postId)
                            .receiver(receiver)
                            .sender(user)
                            .build();
                    messageOriginRepository.save(messageOrigin);

                    // 초기 메세지 생성
                    messageOrigin.addMessage(
                            messageRepository.save(
                                    createWelcomeMessage(messageOrigin, LocalDateTime.now()))
                    );

                    return messageOrigin;
                });

        // 메세지 방에 메세지 저장
        Message messageEntity = Message.builder()
                .content(message)
                .messageType(getMessageType(user, messageRoom))
                .messageOrigin(messageRoom)
                .build();

        return messageRepository.save(messageEntity);
    }

    private User checkValidPostType(User user, DomainType postType, Long postId) {
        switch (postType) {
            case BUY_TOGETHER -> {
                return checkValidBuyTogether(user, postId);
            }
            case KNOW_TOGETHER -> {
                return checkValidKnowTogether(user, postId);
            }
            default -> throw new IllegalArgumentException("수신자를 확인 할 수 없어 메세지를 보낼 수 없습니다.");
        }
    }

    private User checkValidKnowTogether(User user, Long postId) {
        Save post = saveRepository.findKnowTogetherById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        if (Objects.equals(post.getWriter().getId(), user.getId())) {
            throw new IllegalArgumentException("자신의 게시글에는 메세지를 보낼 수 없습니다.");
        }
        return post.getWriter();
    }

    private User checkValidBuyTogether(User user, Long postId) {
        Save post = saveRepository.findBuyTogetherById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        if (Objects.equals(post.getWriter().getId(), user.getId())) {
            throw new IllegalArgumentException("자신의 게시글에는 메세지를 보낼 수 없습니다.");
        }
        return post.getWriter();
    }
}
