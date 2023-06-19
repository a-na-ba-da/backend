package kr.anabada.anabadaserver.domain.recycle.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecycleLike is a Querydsl query type for RecycleLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecycleLike extends EntityPathBase<RecycleLike> {

    private static final long serialVersionUID = -1525390844L;

    public static final QRecycleLike recycleLike = new QRecycleLike("recycleLike");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> recycleId = createNumber("recycleId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QRecycleLike(String variable) {
        super(RecycleLike.class, forVariable(variable));
    }

    public QRecycleLike(Path<? extends RecycleLike> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecycleLike(PathMetadata metadata) {
        super(RecycleLike.class, metadata);
    }

}

