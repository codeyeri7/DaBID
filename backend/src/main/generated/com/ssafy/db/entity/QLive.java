package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLive is a Querydsl query type for Live
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLive extends EntityPathBase<Live> {

    private static final long serialVersionUID = 846265262L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLive live = new QLive("live");

    public final DateTimePath<java.sql.Timestamp> liveDate = createDateTime("liveDate", java.sql.Timestamp.class);

    public final StringPath liveDesc = createString("liveDesc");

    public final QLiveStatus liveStatus;

    public final StringPath liveTitle = createString("liveTitle");

    public final NumberPath<Integer> prdId = createNumber("prdId", Integer.class);

    public final StringPath prdName = createString("prdName");

    public final StringPath prdNo = createString("prdNo");

    public final StringPath prdPhoto = createString("prdPhoto");

    public final NumberPath<Integer> prdPriceStart = createNumber("prdPriceStart", Integer.class);

    public final QProductCategory productCategory;

    public final QResult result;

    public final QUser user;

    public final ListPath<WishList, QWishList> WishList = this.<WishList, QWishList>createList("WishList", WishList.class, QWishList.class, PathInits.DIRECT2);

    public QLive(String variable) {
        this(Live.class, forVariable(variable), INITS);
    }

    public QLive(Path<? extends Live> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLive(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLive(PathMetadata metadata, PathInits inits) {
        this(Live.class, metadata, inits);
    }

    public QLive(Class<? extends Live> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.liveStatus = inits.isInitialized("liveStatus") ? new QLiveStatus(forProperty("liveStatus")) : null;
        this.productCategory = inits.isInitialized("productCategory") ? new QProductCategory(forProperty("productCategory")) : null;
        this.result = inits.isInitialized("result") ? new QResult(forProperty("result"), inits.get("result")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

