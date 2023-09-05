package kr.anabada.anabadaserver.domain.share.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.anabada.anabadaserver.domain.share.entity.Lend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.querydsl.core.types.Order.DESC;
import static kr.anabada.anabadaserver.domain.share.entity.QLend.lend;

@Slf4j
@RequiredArgsConstructor
@Component
public class LendRepositoryImpl implements LendRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Lend> findByLendList(Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(DESC, lend.id);

        return queryFactory
                .selectFrom(lend)
                .where(booleanBuilder)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
