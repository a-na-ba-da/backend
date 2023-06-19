package kr.anabada.anabadaserver.domain.message.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMessageOrigin is a Querydsl query type for MessageOrigin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessageOrigin extends EntityPathBase<MessageOrigin> {

    private static final long serialVersionUID = 1486373843L;

    public static final QMessageOrigin messageOrigin = new QMessageOrigin("messageOrigin");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> messageDetailId = createNumber("messageDetailId", Long.class);

    public final StringPath messageType = createString("messageType");

    public QMessageOrigin(String variable) {
        super(MessageOrigin.class, forVariable(variable));
    }

    public QMessageOrigin(Path<? extends MessageOrigin> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMessageOrigin(PathMetadata metadata) {
        super(MessageOrigin.class, metadata);
    }

}

