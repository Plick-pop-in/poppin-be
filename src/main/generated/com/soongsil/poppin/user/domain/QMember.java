package com.soongsil.poppin.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -26226636L;

    public static final QMember member = new QMember("member1");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final ListPath<com.soongsil.poppin.heart.domain.Heart, com.soongsil.poppin.heart.domain.QHeart> heart = this.<com.soongsil.poppin.heart.domain.Heart, com.soongsil.poppin.heart.domain.QHeart>createList("heart", com.soongsil.poppin.heart.domain.Heart.class, com.soongsil.poppin.heart.domain.QHeart.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final NumberPath<Long> point = createNumber("point", Long.class);

    public final ListPath<com.soongsil.poppin.userchat.domain.UserChat, com.soongsil.poppin.userchat.domain.QUserChat> userChat = this.<com.soongsil.poppin.userchat.domain.UserChat, com.soongsil.poppin.userchat.domain.QUserChat>createList("userChat", com.soongsil.poppin.userchat.domain.UserChat.class, com.soongsil.poppin.userchat.domain.QUserChat.class, PathInits.DIRECT2);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

