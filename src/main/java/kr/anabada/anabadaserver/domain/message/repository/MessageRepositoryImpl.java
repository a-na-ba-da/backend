package kr.anabada.anabadaserver.domain.message.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.anabada.anabadaserver.domain.message.dto.MessageSummaryResponse;
import kr.anabada.anabadaserver.domain.message.entity.Message;
import kr.anabada.anabadaserver.domain.message.entity.MessageOrigin;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

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
            if (senderIsRequester && !message.isReadBySender()
                    || !senderIsRequester && !message.isReadByReceiver()) {
                unreadCount++;
            }
        }

        return unreadCount;
    }

}
