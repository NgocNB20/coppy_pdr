/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * お気に入り商品Dao用検索条件（管理機能）Dtoクラス
 *
 * @author takashima
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Component
@Scope("prototype")
public class FavoriteSearchForBackDaoConditionDto extends AbstractConditionDto {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * サイト区分
     */
    private String site;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * 商品グループコード
     */
    private String goodsGroupCode;

    /**
     * 商品コード
     */
    private String goodsCode;

    /**
     * 商品名
     */
    private String goodsGroupNameAdmin;

    /**
     * 顧客番号
     */
    private Integer customerNo;

    /**
     * サイト区分
     */
    private HTypeSiteType siteType;

    /**
     * お気に入りセール状態リスト
     */
    private List<String> favoriteSaleStatusList;

    /**
     * セールコード
     */
    private String saleCode;

    /**
     * セール開始日（from）
     */
    private Timestamp searchSaleStartTimeFrom;

    /**
     * セール開始日（to）
     */
    private Timestamp searchSaleStartTimeTo;

    /**
     * セール終了日（from）
     */
    private Timestamp searchSaleEndTimeFrom;

    /**
     * セール終了日（to）
     */
    private Timestamp searchSaleEndTimeTo;

    /**
     * 受注番号（複数番号検索用）
     */
    private List<String> goodsCodeList;

    /**
     * 会員状態
     */
    private HTypeMemberInfoStatus memberInfoStatus;
}
