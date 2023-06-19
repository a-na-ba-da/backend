package kr.anabada.anabadaserver.domain.save.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSave is a Querydsl query type for Save
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSave extends EntityPathBase<Save> {

    private static final long serialVersionUID = -2061164031L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSave save = new QSave("save");

    public final kr.anabada.anabadaserver.common.entity.QBaseTimeEntity _super = new kr.anabada.anabadaserver.common.entity.QBaseTimeEntity(this);

    public final NumberPath<Double> buyPlaceLat = createNumber("buyPlaceLat", Double.class);

    public final NumberPath<Double> buyPlaceLng = createNumber("buyPlaceLng", Double.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRemoved = createBoolean("isRemoved");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath productUrl = createString("productUrl");

    public final StringPath title = createString("title");

    public final kr.anabada.anabadaserver.domain.user.entity.QUser writer;

    public QSave(String variable) {
        this(Save.class, forVariable(variable), INITS);
    }

    public QSave(Path<? extends Save> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSave(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSave(PathMetadata metadata, PathInits inits) {
        this(Save.class, metadata, inits);
    }

    public QSave(Class<? extends Save> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.writer = inits.isInitialized("writer") ? new kr.anabada.anabadaserver.domain.user.entity.QUser(forProperty("writer")) : null;
    }

}

