package com.soongsil.poppin.heart.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHeart is a Querydsl query type for Heart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHeart extends EntityPathBase<Heart> {

    private static final long serialVersionUID = 680722711L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHeart heart = new QHeart("heart");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> heartId = createNumber("heartId", Long.class);

    public final com.soongsil.poppin.user.domain.QMember member;

    public final com.soongsil.poppin.popup.domain.QPopup popup;

    public QHeart(String variable) {
        this(Heart.class, forVariable(variable), INITS);
    }

    public QHeart(Path<? extends Heart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHeart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHeart(PathMetadata metadata, PathInits inits) {
        this(Heart.class, metadata, inits);
    }

    public QHeart(Class<? extends Heart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.soongsil.poppin.user.domain.QMember(forProperty("member")) : null;
        this.popup = inits.isInitialized("popup") ? new com.soongsil.poppin.popup.domain.QPopup(forProperty("popup"), inits.get("popup")) : null;
    }

}

