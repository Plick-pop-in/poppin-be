package com.soongsil.poppin.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 214749157L;

    public static final QUser user = new QUser("user");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final ListPath<com.soongsil.poppin.heart.domain.Heart, com.soongsil.poppin.heart.domain.QHeart> heart = this.<com.soongsil.poppin.heart.domain.Heart, com.soongsil.poppin.heart.domain.QHeart>createList("heart", com.soongsil.poppin.heart.domain.Heart.class, com.soongsil.poppin.heart.domain.QHeart.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final NumberPath<Long> point = createNumber("point", Long.class);

    public final ListPath<com.soongsil.poppin.userchat.domain.UserChat, com.soongsil.poppin.userchat.domain.QUserChat> userChat = this.<com.soongsil.poppin.userchat.domain.UserChat, com.soongsil.poppin.userchat.domain.QUserChat>createList("userChat", com.soongsil.poppin.userchat.domain.UserChat.class, com.soongsil.poppin.userchat.domain.QUserChat.class, PathInits.DIRECT2);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

