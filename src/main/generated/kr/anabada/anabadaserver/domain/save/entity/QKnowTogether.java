package kr.anabada.anabadaserver.domain.save.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKnowTogether is a Querydsl query type for KnowTogether
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKnowTogether extends EntityPathBase<KnowTogether> {

    private static final long serialVersionUID = 121919081L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKnowTogether knowTogether = new QKnowTogether("knowTogether");

    public final QSave _super;

    //inherited
    public final NumberPath<Double> buyPlaceLat;

    //inherited
    public final NumberPath<Double> buyPlaceLng;

    //inherited
    public final StringPath content;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    public final ListPath<kr.anabada.anabadaserver.common.entity.Image, kr.anabada.anabadaserver.common.entity.QImage> images = this.<kr.anabada.anabadaserver.common.entity.Image, kr.anabada.anabadaserver.common.entity.QImage>createList("images", kr.anabada.anabadaserver.common.entity.Image.class, kr.anabada.anabadaserver.common.entity.QImage.class, PathInits.DIRECT2);

    public final BooleanPath isOnline = createBoolean("isOnline");

    //inherited
    public final BooleanPath isRemoved;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    //inherited
    public final StringPath productUrl;

    //inherited
    public final StringPath title;

    // inherited
    public final kr.anabada.anabadaserver.domain.user.entity.QUser writer;

    public QKnowTogether(String variable) {
        this(KnowTogether.class, forVariable(variable), INITS);
    }

    public QKnowTogether(Path<? extends KnowTogether> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKnowTogether(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKnowTogether(PathMetadata metadata, PathInits inits) {
        this(KnowTogether.class, metadata, inits);
    }

    public QKnowTogether(Class<? extends KnowTogether> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QSave(type, metadata, inits);
        this.buyPlaceLat = _super.buyPlaceLat;
        this.buyPlaceLng = _super.buyPlaceLng;
        this.content = _super.content;
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.isRemoved = _super.isRemoved;
        this.modifiedAt = _super.modifiedAt;
        this.productUrl = _super.productUrl;
        this.title = _super.title;
        this.writer = _super.writer;
    }

}

