package kr.anabada.anabadaserver.domain.message.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.anabada.anabadaserver.domain.message.dto.MessageSummaryResponse;
import kr.anabada.anabadaserver.domain.message.dto.MessageType;
import kr.anabada.anabadaserver.domain.message.dto.MessageTypeResponse;
import kr.anabada.anabadaserver.domain.message.entity.Message;
import kr.anabada.anabadaserver.domain.message.entity.MessageOrigin;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static kr.anabada.anabadaserver.domain.message.entity.QMessageOrigin.messageOrigin;

@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MessageSummaryResponse> getMyAllMessageSummarized(User requester) {
        List<MessageOrigin> lists = queryFactory.selectFrom(messageOrigin)
                .join(messageOrigin.messages).fetchJoin()
                .join(messageOrigin.receiver).fetchJoin()
                .join(messageOrigin.sender).fetchJoin()
                .where(messageOrigin.sender.eq(requester).or(messageOrigin.receiver.eq(requester)))
                .fetch();

        List<MessageSummaryResponse> result = new ArrayList<>();

        if (!lists.isEmpty()) {
            for (var list : lists) {
                result.add(
                        new MessageSummaryResponse(
                                list.getId(),
                                list.getMessagePostType(),
                                list.getMessagePostId(),
                                list.getInterlocutor(requester).toDto(),
                                parseLastMessage(list).getContent(),
                                parseLastMessage(list).getCreatedAt(),
                                countUnreadMessages(requester, list)
                        )
                );
            }
        }

        // orderBy result.lastMessagedAt desc
        result.sort((o1, o2) -> o2.getLastMessagedAt().compareTo(o1.getLastMessagedAt()));

        return result;
    }

    @Override
    public MessageOrigin getMyMessageDetail(User user, long messageRoomId, LocalDateTime timestamp) {
        return queryFactory.selectFrom(messageOrigin)
                .join(messageOrigin.messages).fetchJoin()
                .join(messageOrigin.receiver).fetchJoin()
                .join(messageOrigin.sender).fetchJoin()
                .where(messageOrigin.id.eq(messageRoomId)
                        .and(timestamp == null ?
                                null : messageOrigin.createdAt.before(timestamp))
                        .and(messageOrigin.sender.eq(user).or(messageOrigin.receiver.eq(user))))
                .orderBy(messageOrigin.id.desc())
                .limit(20)
                .fetchOne();
    }

    private MessageTypeResponse parseSendBy(User me, MessageOrigin messageRoom, MessageType messageType) {
        if (messageType.equals(MessageType.NOTIFICATION))
            return MessageTypeResponse.NOTIFICATION;

        if (messageRoom.getSender().equals(me) && messageType.equals(MessageType.SENDER_SEND))
            return MessageTypeResponse.ME;

        if (messageRoom.getReceiver().equals(me) && messageType.equals(MessageType.RECEIVER_SEND))
            return MessageTypeResponse.ME;

        return MessageTypeResponse.INTERLOCUTOR;
    }

    private Message parseLastMessage(MessageOrigin messageOrigin) {
        // todo: 로직 보강
        List<Message> messages = messageOrigin.getMessages();
        return messages.get(messages.size() - 1);
    }

    private int countUnreadMessages(User requester, MessageOrigin messageOrigin) {
        List<Message> messages = messageOrigin.getMessages();
        int unreadCount = 0;
        boolean senderIsRequester = messageOrigin.getSender().equals(requester);

        for (Message message : messages) {
            if (!message.isRead()) {
                if (senderIsRequester && message.getMessageType().equals(MessageType.RECEIVER_SEND)
                        || !senderIsRequester && message.getMessageType().equals(MessageType.SENDER_SEND)) {
                    unreadCount++;
                }
            }
        }

        return unreadCount;
    }

}
