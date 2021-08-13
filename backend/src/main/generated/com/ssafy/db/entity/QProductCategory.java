package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductCategory is a Querydsl query type for ProductCategory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductCategory extends EntityPathBase<ProductCategory> {

    private static final long serialVersionUID = -718186741L;

    public static final QProductCategory productCategory = new QProductCategory("productCategory");

    public final ListPath<Live, QLive> livelist = this.<Live, QLive>createList("livelist", Live.class, QLive.class, PathInits.DIRECT2);

    public final NumberPath<Integer> prdCategory = createNumber("prdCategory", Integer.class);

    public final StringPath prdCategoryName = createString("prdCategoryName");

    public QProductCategory(String variable) {
        super(ProductCategory.class, forVariable(variable));
    }

    public QProductCategory(Path<? extends ProductCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductCategory(PathMetadata metadata) {
        super(ProductCategory.class, metadata);
    }

}

