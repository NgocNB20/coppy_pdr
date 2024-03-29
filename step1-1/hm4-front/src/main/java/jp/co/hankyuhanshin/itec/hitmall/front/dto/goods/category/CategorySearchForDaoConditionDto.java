/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.front.base.dto.AbstractConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * カテゴリ情報Dao用検索条件Dtoクラス
 *
 * @author DtoGenerator
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@Scope("prototype")
public class CategorySearchForDaoConditionDto extends AbstractConditionDto implements Serializable {

    /**
     * カテゴリテーブル項目（カテゴリパス）
     */
    public static final String CATEGORY_FIELD_CATEGORY_PATH = "categorypath";
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * カテゴリID
     */
    private String categoryId;

    /**
     * カテゴリSEQリスト
     */
    private List<Integer> categorySeqList;

    /**
     * 最大表示階層数
     */
    private Integer maxHierarchical;

    /**
     * サイト区分
     */
    private HTypeSiteType siteType;

    /**
     * 公開状態
     */
    private HTypeOpenStatus openStatus;

    /**
     * 除外カテゴリIDリスト
     */
    private List<String> notInCategoryIdList;

    // ※カテゴリの検索ではページング制御不要のため、PageInfoは使わない（AbstractConditionDtoの継承はしない）
    /**
     * 並替項目
     */
    private String orderField;

    /**
     * 並替昇順フラグ
     */
    private boolean orderAsc;
}
