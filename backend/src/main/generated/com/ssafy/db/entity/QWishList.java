package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWishList is a Querydsl query type for WishList
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWishList extends EntityPathBase<WishList> {

    private static final long serialVersionUID = -957594713L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWishList wishList = new QWishList("wishList");

    public final QLive live;

    public final QUser user;

    public final NumberPath<Integer> wishId = createNumber("wishId", Integer.class);

    public QWishList(String variable) {
        this(WishList.class, forVariable(variable), INITS);
    }

    public QWishList(Path<? extends WishList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWishList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWishList(PathMetadata metadata, PathInits inits) {
        this(WishList.class, metadata, inits);
    }

    public QWishList(Class<? extends WishList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.live = inits.isInitialized("live") ? new QLive(forProperty("live"), inits.get("live")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

