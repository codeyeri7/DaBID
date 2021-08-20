package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLiveLog is a Querydsl query type for LiveLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLiveLog extends EntityPathBase<LiveLog> {

    private static final long serialVersionUID = -369530698L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLiveLog liveLog = new QLiveLog("liveLog");

    public final StringPath bidder = createString("bidder");

    public final NumberPath<Integer> bidPrice = createNumber("bidPrice", Integer.class);

    public final DateTimePath<java.sql.Timestamp> bidTime = createDateTime("bidTime", java.sql.Timestamp.class);

    public final QLive live;

    public final NumberPath<Integer> logId = createNumber("logId", Integer.class);

    public QLiveLog(String variable) {
        this(LiveLog.class, forVariable(variable), INITS);
    }

    public QLiveLog(Path<? extends LiveLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLiveLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLiveLog(PathMetadata metadata, PathInits inits) {
        this(LiveLog.class, metadata, inits);
    }

    public QLiveLog(Class<? extends LiveLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.live = inits.isInitialized("live") ? new QLive(forProperty("live"), inits.get("live")) : null;
    }

}

