package kr.anabada.anabadaserver.domain.change.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMyProduct is a Querydsl query type for MyProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyProduct extends EntityPathBase<MyProduct> {

    private static final long serialVersionUID = 1226177612L;

    public static final QMyProduct myProduct = new QMyProduct("myProduct");

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRemoved = createBoolean("isRemoved");

    public final NumberPath<Double> lat = createNumber("lat", Double.class);

    public final NumberPath<Double> lng = createNumber("lng", Double.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> owner = createNumber("owner", Long.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final BooleanPath status = createBoolean("status");

    public QMyProduct(String variable) {
        super(MyProduct.class, forVariable(variable));
    }

    public QMyProduct(Path<? extends MyProduct> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMyProduct(PathMetadata metadata) {
        super(MyProduct.class, metadata);
    }

}

