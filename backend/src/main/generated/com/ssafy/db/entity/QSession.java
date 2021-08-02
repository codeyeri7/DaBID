package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSession is a Querydsl query type for Session
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSession extends EntityPathBase<Session> {

    private static final long serialVersionUID = 1431185556L;

    public static final QSession session = new QSession("session");

    public final DateTimePath<java.util.Date> sessionDate = createDateTime("sessionDate", java.util.Date.class);

    public final NumberPath<Integer> sessionId = createNumber("sessionId", Integer.class);

    public final StringPath sessionStatus = createString("sessionStatus");

    public final StringPath sessionToken = createString("sessionToken");

    public final ComparablePath<Character> userId = createComparable("userId", Character.class);

    public QSession(String variable) {
        super(Session.class, forVariable(variable));
    }

    public QSession(Path<? extends Session> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSession(PathMetadata metadata) {
        super(Session.class, metadata);
    }

}

