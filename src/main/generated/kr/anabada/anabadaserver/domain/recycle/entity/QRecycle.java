package kr.anabada.anabadaserver.domain.recycle.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecycle is a Querydsl query type for Recycle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecycle extends EntityPathBase<Recycle> {

    private static final long serialVersionUID = 238906573L;

    public static final QRecycle recycle = new QRecycle("recycle");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRemoved = createBoolean("isRemoved");

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public final NumberPath<Long> writer = createNumber("writer", Long.class);

    public QRecycle(String variable) {
        super(Recycle.class, forVariable(variable));
    }

    public QRecycle(Path<? extends Recycle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecycle(PathMetadata metadata) {
        super(Recycle.class, metadata);
    }

}

