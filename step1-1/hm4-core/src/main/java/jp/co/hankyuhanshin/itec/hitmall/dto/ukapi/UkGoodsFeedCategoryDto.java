/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.dto.ukapi;

import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 商品フィードTsv用のCategoryDto
 */
@Entity
@Data
@Component
@Scope("prototype")
public class UkGoodsFeedCategoryDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * カテゴリSEQ
     */
    private Integer categorySeq;

    /**
     * カテゴリIDリスト
     */
    private String categoryIdList;

    /**
     * カテゴリ名リスト
     */
    private String categoryNameList;

    /**
     * カテゴリ順リスト
     */
    private String categoryOrderList;

}
