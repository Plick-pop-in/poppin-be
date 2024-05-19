package com.soongsil.poppin.category.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = -290308661L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategory category = new QCategory("category");

    public final BooleanPath beauty = createBoolean("beauty");

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    public final BooleanPath celeb = createBoolean("celeb");

    public final BooleanPath charactor = createBoolean("charactor");

    public final BooleanPath digital = createBoolean("digital");

    public final BooleanPath fashion = createBoolean("fashion");

    public final BooleanPath food = createBoolean("food");

    public final BooleanPath living = createBoolean("living");

    public final com.soongsil.poppin.popup.domain.QPopup popup;

    public QCategory(String variable) {
        this(Category.class, forVariable(variable), INITS);
    }

    public QCategory(Path<? extends Category> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategory(PathMetadata metadata, PathInits inits) {
        this(Category.class, metadata, inits);
    }

    public QCategory(Class<? extends Category> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.popup = inits.isInitialized("popup") ? new com.soongsil.poppin.popup.domain.QPopup(forProperty("popup"), inits.get("popup")) : null;
    }

}

