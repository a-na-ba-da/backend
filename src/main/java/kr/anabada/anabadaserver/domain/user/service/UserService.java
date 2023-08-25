package kr.anabada.anabadaserver.domain.user.service;

import io.micrometer.common.util.StringUtils;
import kr.anabada.anabadaserver.domain.user.dto.UserInfoChangeRequest;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Transactional
    public void changeMyInformation(User user, UserInfoChangeRequest request) {
        // 변경할 정보가 아직 닉네임 밖에 없음
        if (StringUtils.isNotEmpty(request.getNickname())) {
            user.changeNickname(request.getNickname());
        }
    }
}
