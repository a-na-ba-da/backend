package kr.anabada.anabadaserver.domain.change.respository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.anabada.anabadaserver.common.dto.DomainType;
import kr.anabada.anabadaserver.domain.change.dto.MyProductResponse;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

import static kr.anabada.anabadaserver.common.entity.QImage.image;
import static kr.anabada.anabadaserver.domain.change.dto.ProductStatus.AVAILABLE;
import static kr.anabada.anabadaserver.domain.change.entity.QMyProduct.myProduct;

@Slf4j
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public void createChangeRequest(Long targetId, List<Long> myProductIds) {

    }

    @Override
    public void updateStatus(List<Long> productIds, ProductStatus status) {
        List<MyProduct> products = queryFactory
                .selectFrom(myProduct)
                .where(myProduct.id.in(productIds))
                .fetch();

        products.forEach(product -> product.updateStatus(status));
    }

 /*   @Override
    public Page<MyProductResponse> findUserProductList(SearchProductRecord searchProduct) {
        List<MyProductResponse> result = queryFactory
                .select(Projections.fields(MyProductResponse.class,
                        myProduct.id,
                        myProduct.name,
                        myProduct.content,
                        myProduct.originalPrice,
                        myProduct.status,
                        getOwner().as("owner"),
                        GroupBy.list(image.id).as("images")))
                .from(myProduct)
                .leftJoin(myProduct.images)
                .where(myProduct.status.eq(AVAILABLE)
                        .and(onlyActivatedUser())
                        .and(onlyUserProduct(searchProduct.user))
                        .and(keywordSearch(searchProduct.keyword)))
                .orderBy(myProduct.id.desc())
                .offset(searchProduct.pageable.getOffset())
                .limit(searchProduct.pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, searchProduct.pageable, result.size());
    }*/

    @Override
    public Page<MyProductResponse> findUserProductList(SearchProductRecord searchProduct) {
        List<MyProduct> result = queryFactory
                .selectFrom(myProduct)
                .leftJoin(myProduct.images)
                .where(myProduct.status.eq(AVAILABLE)
                        .and(onlyActivatedUser())
                        .and(onlyUserProduct(searchProduct.user))
                        .and(keywordSearch(searchProduct.keyword)))
                .orderBy(myProduct.id.desc())
                .offset(searchProduct.pageable.getOffset())
                .limit(searchProduct.pageable.getPageSize())
                .fetch();

        return new PageImpl<>(
                result.stream().map(MyProduct::toResponse).toList(),
                searchProduct.pageable,
                result.size());
    }

    private Predicate onlyUserProduct(User user) {
        return user != null ? myProduct.owner.eq(user) : null;
    }

    private BooleanExpression keywordSearch(String keyword) {
        return StringUtils.hasText(keyword) ? myProduct.name.containsIgnoreCase(keyword)
                .or(myProduct.content.containsIgnoreCase(keyword)) : null;
    }

    private BooleanExpression onlyActivatedUser() {
        return myProduct.owner.activated.eq(true);
    }

    private QBean<UserDto> getOwner() {
        return Projections.fields(UserDto.class,
                myProduct.owner.id,
                myProduct.owner.nickname,
                myProduct.owner.activated
        );
    }

    private JPQLQuery<UUID> getMainImage() {
        return JPAExpressions
                .select(image.id)
                .from(image)
                .where(image.postId.eq(myProduct.id)
                        .and(image.imageType.eq(DomainType.MY_PRODUCT.name())))
                .orderBy(image.createdAt.asc())
                .limit(1);
    }

    public record SearchProductRecord(
            User user,
            String keyword,
            Pageable pageable,
            boolean isMyProduct
    ) {
    }
}
