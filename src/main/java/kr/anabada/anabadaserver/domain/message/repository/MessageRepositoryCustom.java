package kr.anabada.anabadaserver.domain.message.repository;

import kr.anabada.anabadaserver.domain.message.dto.MessageSummaryResponse;
import kr.anabada.anabadaserver.domain.message.entity.MessageOrigin;
import kr.anabada.anabadaserver.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepositoryCustom {

    List<MessageSummaryResponse> getMyAllMessageSummarized(User requester);

    MessageOrigin getMyMessageDetail(User user, long messageRoomId, LocalDateTime timestamp);
}
