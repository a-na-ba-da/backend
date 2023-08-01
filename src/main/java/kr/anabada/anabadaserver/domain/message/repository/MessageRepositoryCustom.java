package kr.anabada.anabadaserver.domain.message.repository;

import kr.anabada.anabadaserver.domain.message.dto.MessageDetailResponse;
import kr.anabada.anabadaserver.domain.message.dto.MessageSummaryResponse;
import kr.anabada.anabadaserver.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepositoryCustom {

    List<MessageSummaryResponse> getMyAllMessageSummarized(User requester);

    MessageDetailResponse getMyMessageDetail(User user, long messageRoomId, LocalDateTime timestamp);
}
