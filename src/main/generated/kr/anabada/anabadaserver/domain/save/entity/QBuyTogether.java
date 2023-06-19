package kr.anabada.anabadaserver.domain.save.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBuyTogether is a Querydsl query type for BuyTogether
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBuyTogether extends EntityPathBase<BuyTogether> {

    private static final long serialVersionUID = 1439990236L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBuyTogether buyTogether = new QBuyTogether("buyTogether");

    public final QSave _super;

    public final DatePath<java.time.LocalDate> buyDate = createDate("buyDate", java.time.LocalDate.class);

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

    public final BooleanPath isOnlineDelivery = createBoolean("isOnlineDelivery");

    //inherited
    public final BooleanPath isRemoved;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    public final NumberPath<Integer> pay = createNumber("pay", Integer.class);

    //inherited
    public final StringPath productUrl;

    //inherited
    public final StringPath title;

    // inherited
    public final kr.anabada.anabadaserver.domain.user.entity.QUser writer;

    public QBuyTogether(String variable) {
        this(BuyTogether.class, forVariable(variable), INITS);
    }

    public QBuyTogether(Path<? extends BuyTogether> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBuyTogether(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBuyTogether(PathMetadata metadata, PathInits inits) {
        this(BuyTogether.class, metadata, inits);
    }

    public QBuyTogether(Class<? extends BuyTogether> type, PathMetadata metadata, PathInits inits) {
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

