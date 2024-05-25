package com.soongsil.poppin.popup.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPopup is a Querydsl query type for Popup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPopup extends EntityPathBase<Popup> {

    private static final long serialVersionUID = 836851415L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPopup popup = new QPopup("popup");

    public final com.soongsil.poppin.category.domain.QCategory category;

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final ListPath<com.soongsil.poppin.heart.domain.Heart, com.soongsil.poppin.heart.domain.QHeart> hearts = this.<com.soongsil.poppin.heart.domain.Heart, com.soongsil.poppin.heart.domain.QHeart>createList("hearts", com.soongsil.poppin.heart.domain.Heart.class, com.soongsil.poppin.heart.domain.QHeart.class, PathInits.DIRECT2);

    public final StringPath popupCity = createString("popupCity");

    public final DateTimePath<java.time.LocalDateTime> popupEndDate = createDateTime("popupEndDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> popupId = createNumber("popupId", Long.class);

    public final ListPath<PopupImage, QPopupImage> popupImages = this.<PopupImage, QPopupImage>createList("popupImages", PopupImage.class, QPopupImage.class, PathInits.DIRECT2);

    public final StringPath popupIntro = createString("popupIntro");

    public final StringPath popupLocal = createString("popupLocal");

    public final StringPath popupLocation = createString("popupLocation");

    public final StringPath popupName = createString("popupName");

    public final StringPath popupPageLink = createString("popupPageLink");

    public final DateTimePath<java.time.LocalDateTime> popupStartDate = createDateTime("popupStartDate", java.time.LocalDateTime.class);

    public final StringPath popupTime = createString("popupTime");

    public QPopup(String variable) {
        this(Popup.class, forVariable(variable), INITS);
    }

    public QPopup(Path<? extends Popup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPopup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPopup(PathMetadata metadata, PathInits inits) {
        this(Popup.class, metadata, inits);
    }

    public QPopup(Class<? extends Popup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.soongsil.poppin.category.domain.QCategory(forProperty("category"), inits.get("category")) : null;
    }

}

