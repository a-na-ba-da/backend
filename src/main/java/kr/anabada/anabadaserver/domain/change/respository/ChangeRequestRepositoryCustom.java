package kr.anabada.anabadaserver.domain.change.respository;

import kr.anabada.anabadaserver.domain.change.dto.ChangeRequestResponse;
import kr.anabada.anabadaserver.domain.user.entity.User;

import java.util.List;

public interface ChangeRequestRepositoryCustom {
    /**
     * 내가 요청한 교환 요청 목록 조회 쿼리
     *
     * @param user : 나의 userId
     * @return : 내가 요청한 교환 요청 목록
     */
    List<ChangeRequestResponse.ReqByMe> getAllRequestedByMe(User user);

    /**
     * 나에게 요청된 교환 요청 목록 조회 쿼리
     *
     * @param user : 나의 userId
     * @return : 나에게 요청된 교환 요청 목록
     */
    List<ChangeRequestResponse.ReqForMe> getAllRequestingForMe(User user);
}
