package kr.anabada.anabadaserver.domain.save.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.anabada.anabadaserver.domain.save.dto.SaveSearchRequestDto;
import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.querydsl.core.types.Order.DESC;
import static kr.anabada.anabadaserver.domain.save.entity.QBuyTogether.buyTogether;

@Slf4j
@RequiredArgsConstructor
public class SaveRepositoryImpl implements SaveRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BuyTogether> findSaveList(SaveSearchRequestDto searchRequest, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(DESC, buyTogether.id);

        if (Boolean.TRUE.equals(searchRequest.onlyOnlineBought())) {
            log.info("only onlineBought enable");
            builder.and(buyTogether.productUrl.isNotNull());
        }

        if (searchRequest.fullySetLocationInfo()) {
            // add order by
            orderSpecifier = new OrderSpecifier<>(DESC,
                    Expressions.stringTemplate("ST_Distance_Sphere({0}, {1})",
                            Expressions.stringTemplate("POINT({0}, {1})",
                                    searchRequest.getLng(),
                                    searchRequest.getLat()
                            ),
                            Expressions.stringTemplate("POINT({0}, {1})",
                                    buyTogether.buyPlaceLng,
                                    buyTogether.buyPlaceLat
                            )
                    ));
        }

        return queryFactory.selectFrom(buyTogether)
                .where(builder)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
