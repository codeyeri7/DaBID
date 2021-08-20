package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResult is a Querydsl query type for Result
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QResult extends EntityPathBase<Result> {

    private static final long serialVersionUID = 1680108127L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResult result = new QResult("result");

    public final QUser buyer;

    public final ListPath<Chat, QChat> chatlist = this.<Chat, QChat>createList("chatlist", Chat.class, QChat.class, PathInits.DIRECT2);

    public final QLive live;

    public final NumberPath<Integer> prdId = createNumber("prdId", Integer.class);

    public final NumberPath<Integer> resPriceEnd = createNumber("resPriceEnd", Integer.class);

    public final QUser seller;

    public QResult(String variable) {
        this(Result.class, forVariable(variable), INITS);
    }

    public QResult(Path<? extends Result> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResult(PathMetadata metadata, PathInits inits) {
        this(Result.class, metadata, inits);
    }

    public QResult(Class<? extends Result> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new QUser(forProperty("buyer"), inits.get("buyer")) : null;
        this.live = inits.isInitialized("live") ? new QLive(forProperty("live"), inits.get("live")) : null;
        this.seller = inits.isInitialized("seller") ? new QUser(forProperty("seller"), inits.get("seller")) : null;
    }

}

