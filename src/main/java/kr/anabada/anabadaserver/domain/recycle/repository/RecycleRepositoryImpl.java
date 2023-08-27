package kr.anabada.anabadaserver.domain.recycle.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static com.querydsl.core.types.Order.DESC;

import static kr.anabada.anabadaserver.domain.recycle.entity.QRecycle.recycle;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class RecycleRepositoryImpl implements RecycleRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Recycle> findByRecycleList(Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(DESC, recycle.id);

        return queryFactory
                .selectFrom(recycle)
                .where(booleanBuilder)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
