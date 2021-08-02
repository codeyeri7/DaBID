package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLiveStatus is a Querydsl query type for LiveStatus
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLiveStatus extends EntityPathBase<LiveStatus> {

    private static final long serialVersionUID = -482886336L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLiveStatus liveStatus1 = new QLiveStatus("liveStatus1");

    public final QLive live;

    public final NumberPath<Integer> liveStatus = createNumber("liveStatus", Integer.class);

    public final StringPath liveStatusName = createString("liveStatusName");

    public QLiveStatus(String variable) {
        this(LiveStatus.class, forVariable(variable), INITS);
    }

    public QLiveStatus(Path<? extends LiveStatus> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLiveStatus(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLiveStatus(PathMetadata metadata, PathInits inits) {
        this(LiveStatus.class, metadata, inits);
    }

    public QLiveStatus(Class<? extends LiveStatus> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.live = inits.isInitialized("live") ? new QLive(forProperty("live"), inits.get("live")) : null;
    }

}

