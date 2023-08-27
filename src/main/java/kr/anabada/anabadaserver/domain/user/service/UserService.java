package kr.anabada.anabadaserver.domain.user.service;

import io.micrometer.common.util.StringUtils;
import kr.anabada.anabadaserver.domain.change.respository.ChangeRequestRepository;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleRepository;
import kr.anabada.anabadaserver.domain.save.repository.SaveRepository;
import kr.anabada.anabadaserver.domain.user.dto.MyActivityDto;
import kr.anabada.anabadaserver.domain.user.dto.UserInfoChangeRequest;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.anabada.anabadaserver.domain.user.dto.MyActivityDto.CreateMyActivityDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final SaveRepository saveRepository;
    private final RecycleRepository recycleRepository;
    private final ChangeRequestRepository changeRequestRepository;
//    private final CommentRepository commentRepository;

    @Transactional
    public void changeMyInformation(User user, UserInfoChangeRequest request) {
        // 변경할 정보가 아직 닉네임 밖에 없음
        if (StringUtils.isNotEmpty(request.nickname())) {
            user.changeNickname(request.nickname());
        }
    }

    public MyActivityDto getMyActivity(User user) {
        return CreateMyActivityDto(
                saveRepository.findAllByWriter(user),
                recycleRepository.findAllByWriter(user),
                changeRequestRepository.findAllByTargetUser(user));
    }
}
