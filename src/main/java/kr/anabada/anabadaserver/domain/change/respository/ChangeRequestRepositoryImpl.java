package kr.anabada.anabadaserver.domain.change.respository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.anabada.anabadaserver.domain.change.dto.ChangeRequestResponse;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.anabada.anabadaserver.domain.change.entity.QChangeRequest.changeRequest;
import static kr.anabada.anabadaserver.domain.change.entity.QMyProduct.myProduct;

@RequiredArgsConstructor
public class ChangeRequestRepositoryImpl implements ChangeRequestRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChangeRequestResponse.ReqByMe> getAllRequestedByMe(User user) {
        return queryFactory.selectFrom(changeRequest)
                .leftJoin(changeRequest.targetProduct, myProduct).fetchJoin()
                .leftJoin(changeRequest.toChangeProducts).fetchJoin()
                .where(changeRequest.requester.eq(user))
                .fetch()
                .stream()
                .map(ChangeRequestResponse.ReqByMe::of)
                .toList();
    }

    @Override
    public List<ChangeRequestResponse.ReqForMe> getAllRequestingForMe(User owner) {
        return queryFactory.select(changeRequest)
                .from(changeRequest)
                .leftJoin(changeRequest.targetProduct, myProduct).fetchJoin()
                .leftJoin(changeRequest.toChangeProducts).fetchJoin()
                .leftJoin(myProduct.owner).fetchJoin()
                .where(changeRequest.targetProduct.owner.eq(owner))
                .fetch()
                .stream()
                .map(ChangeRequestResponse.ReqForMe::of)
                .toList();
    }
}
