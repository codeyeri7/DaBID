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

    public static final QLiveStatus liveStatus1 = new QLiveStatus("liveStatus1");

    public final ListPath<Live, QLive> livelist = this.<Live, QLive>createList("livelist", Live.class, QLive.class, PathInits.DIRECT2);

    public final NumberPath<Integer> liveStatus = createNumber("liveStatus", Integer.class);

    public final StringPath liveStatusName = createString("liveStatusName");

    public QLiveStatus(String variable) {
        super(LiveStatus.class, forVariable(variable));
    }

    public QLiveStatus(Path<? extends LiveStatus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLiveStatus(PathMetadata metadata) {
        super(LiveStatus.class, metadata);
    }

}

