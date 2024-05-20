package com.soongsil.poppin.userchat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserChat is a Querydsl query type for UserChat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserChat extends EntityPathBase<UserChat> {

    private static final long serialVersionUID = -1373925067L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserChat userChat = new QUserChat("userChat");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final com.soongsil.poppin.user.domain.QMember member;

    public final com.soongsil.poppin.popup.domain.QPopup popup;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Long> uchatId = createNumber("uchatId", Long.class);

    public QUserChat(String variable) {
        this(UserChat.class, forVariable(variable), INITS);
    }

    public QUserChat(Path<? extends UserChat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserChat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserChat(PathMetadata metadata, PathInits inits) {
        this(UserChat.class, metadata, inits);
    }

    public QUserChat(Class<? extends UserChat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.soongsil.poppin.user.domain.QMember(forProperty("member")) : null;
        this.popup = inits.isInitialized("popup") ? new com.soongsil.poppin.popup.domain.QPopup(forProperty("popup"), inits.get("popup")) : null;
    }

}

