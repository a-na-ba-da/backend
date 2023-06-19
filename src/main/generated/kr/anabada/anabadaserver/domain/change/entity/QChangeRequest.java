package kr.anabada.anabadaserver.domain.change.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChangeRequest is a Querydsl query type for ChangeRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChangeRequest extends EntityPathBase<ChangeRequest> {

    private static final long serialVersionUID = 1707119336L;

    public static final QChangeRequest changeRequest = new QChangeRequest("changeRequest");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRemoved = createBoolean("isRemoved");

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final StringPath rejectMessage = createString("rejectMessage");

    public final NumberPath<Long> requesterId = createNumber("requesterId", Long.class);

    public final NumberPath<Long> requesterProduct = createNumber("requesterProduct", Long.class);

    public final BooleanPath status = createBoolean("status");

    public QChangeRequest(String variable) {
        super(ChangeRequest.class, forVariable(variable));
    }

    public QChangeRequest(Path<? extends ChangeRequest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChangeRequest(PathMetadata metadata) {
        super(ChangeRequest.class, metadata);
    }

}

