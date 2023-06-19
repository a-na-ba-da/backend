package kr.anabada.anabadaserver.domain.change.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRequesterProduct is a Querydsl query type for RequesterProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRequesterProduct extends EntityPathBase<RequesterProduct> {

    private static final long serialVersionUID = 836659978L;

    public static final QRequesterProduct requesterProduct = new QRequesterProduct("requesterProduct");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public QRequesterProduct(String variable) {
        super(RequesterProduct.class, forVariable(variable));
    }

    public QRequesterProduct(Path<? extends RequesterProduct> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRequesterProduct(PathMetadata metadata) {
        super(RequesterProduct.class, metadata);
    }

}

