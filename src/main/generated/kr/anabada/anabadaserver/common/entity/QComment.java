package kr.anabada.anabadaserver.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = 2070625751L;

    public static final QComment comment = new QComment("comment");

    public final StringPath borderType = createString("borderType");

    public final BooleanPath classField = createBoolean("classField");

    public final StringPath content = createString("content");

    public final NumberPath<Short> groupNumber = createNumber("groupNumber", Short.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Short> order = createNumber("order", Short.class);

    public final NumberPath<Long> writer = createNumber("writer", Long.class);

    public QComment(String variable) {
        super(Comment.class, forVariable(variable));
    }

    public QComment(Path<? extends Comment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComment(PathMetadata metadata) {
        super(Comment.class, metadata);
    }

}

