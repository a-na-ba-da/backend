package kr.anabada.anabadaserver.domain.message.repository;

import kr.anabada.anabadaserver.domain.message.dto.MessageSummaryResponse;
import kr.anabada.anabadaserver.domain.user.entity.User;

import java.util.List;

public interface MessageRepositoryCustom {

    List<MessageSummaryResponse> getMyAllMessageSummarized(User requester);
}
