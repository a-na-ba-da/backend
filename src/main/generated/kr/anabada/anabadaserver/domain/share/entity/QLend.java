package kr.anabada.anabadaserver.domain.share.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLend is a Querydsl query type for Lend
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLend extends EntityPathBase<Lend> {

    private static final long serialVersionUID = 1655019969L;

    public static final QLend lend = new QLend("lend");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> end = createDateTime("end", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRemoved = createBoolean("isRemoved");

    public final NumberPath<Double> lat = createNumber("lat", Double.class);

    public final NumberPath<Double> lng = createNumber("lng", Double.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> pricePerDay = createNumber("pricePerDay", Long.class);

    public final DateTimePath<java.time.LocalDateTime> start = createDateTime("start", java.time.LocalDateTime.class);

    public final BooleanPath status = createBoolean("status");

    public final StringPath title = createString("title");

    public final NumberPath<Long> writer = createNumber("writer", Long.class);

    public QLend(String variable) {
        super(Lend.class, forVariable(variable));
    }

    public QLend(Path<? extends Lend> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLend(PathMetadata metadata) {
        super(Lend.class, metadata);
    }

}

