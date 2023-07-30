package kr.anabada.anabadaserver.domain.message.service;

import kr.anabada.anabadaserver.common.dto.DomainType;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public void sendMessage(User user, DomainType postType, Long postId) {
        // TODO 기능 구현
    }
}
