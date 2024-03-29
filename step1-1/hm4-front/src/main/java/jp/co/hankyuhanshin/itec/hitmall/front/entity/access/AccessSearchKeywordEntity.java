/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.access;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * アクセス検索キーワードクラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.2 $
 */

@Data
@Component
@Scope("prototype")
public class AccessSearchKeywordEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ショップSEQ（必須）
     */
    private Integer shopSeq;

    /**
     * アクセス日時（必須）
     */
    private Timestamp accessTime;

    /**
     * 端末識別情報（必須）
     */
    private String accessUid;

    /**
     * サイト種別（必須）
     */
    private HTypeSiteType siteType = HTypeSiteType.FRONT_PC;

    /**
     * 検索キー（必須）
     */
    private String searchkeyword;

    /**
     * 検索結果件数（必須）
     */
    private Integer searchResultCount = 0;

    /**
     * 検索カテゴリSEQ
     */
    private Integer searchCategorySeq = 0;

    /**
     * 検索価格from
     */
    private BigDecimal searchPriceFrom;

    /**
     * 検索価格to
     */
    private BigDecimal searchPriceTo;

    /**
     * 画面ID（必須）
     */
    private String pageId;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;

}
