package com.soongsil.poppin.popup.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPopupImage is a Querydsl query type for PopupImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPopupImage extends EntityPathBase<PopupImage> {

    private static final long serialVersionUID = 111022276L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPopupImage popupImage = new QPopupImage("popupImage");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final QPopup popup;

    public final NumberPath<Long> popupImageId = createNumber("popupImageId", Long.class);

    public final StringPath popupImageUrl = createString("popupImageUrl");

    public QPopupImage(String variable) {
        this(PopupImage.class, forVariable(variable), INITS);
    }

    public QPopupImage(Path<? extends PopupImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPopupImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPopupImage(PathMetadata metadata, PathInits inits) {
        this(PopupImage.class, metadata, inits);
    }

    public QPopupImage(Class<? extends PopupImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.popup = inits.isInitialized("popup") ? new QPopup(forProperty("popup"), inits.get("popup")) : null;
    }

}

