/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.front.base.dto.AbstractConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CalculatePriceUtility;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 商品グループDao用検索条件Dtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class GoodsGroupSearchForDaoConditionDto extends AbstractConditionDto {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * キーワード条件1
     */
    private String keywordLikeCondition1;

    /**
     * キーワード条件2
     */
    private String keywordLikeCondition2;

    /**
     * キーワード条件3
     */
    private String keywordLikeCondition3;

    /**
     * キーワード条件4
     */
    private String keywordLikeCondition4;

    /**
     * キーワード条件5
     */
    private String keywordLikeCondition5;

    /**
     * キーワード条件6
     */
    private String keywordLikeCondition6;

    /**
     * キーワード条件7
     */
    private String keywordLikeCondition7;

    /**
     * キーワード条件8
     */
    private String keywordLikeCondition8;

    /**
     * キーワード条件9
     */
    private String keywordLikeCondition9;

    /**
     * キーワード条件10
     */
    private String keywordLikeCondition10;

    /**
     * カテゴリID
     */
    private String categoryId;

    /**
     * 商品グループコード
     */
    private String goodsGroupCode;

    /**
     * 下限金額
     */
    private BigDecimal minPrice;

    /**
     * 上限金額
     */
    private BigDecimal maxPrice;

    /**
     * 税金算出時のROUNDINGモード（切り上げ、切り捨て）
     */
    private RoundingMode taxRoundingMode = CalculatePriceUtility.DEFAULT_TAX_ROUNDING_MODE;

    /**
     * サイト区分
     */
    private HTypeSiteType siteType;

    /**
     * 公開状態
     */
    private HTypeOpenDeleteStatus openStatus;

    /**
     * 対象外公開状態
     */
    private HTypeOpenDeleteStatus outOpenStatus;

    /**
     * カテゴリーパス
     */
    private String categoryPath;

    /**
     * 在庫ありステータス
     */
    private String[] stcockExistStatus;

    /**
     * セールID
     */
    private String saleId;

    /**
     * 認証済みシークレットセールID
     */
    private List<String> authenticatedSaleIdList;

    /**
     * セール商品一覧検索フラグ
     */
    private boolean saleGoodsGroupFlag = false;

    /**
     * 会員ランク
     */
    private HTypeMemberRank memberRank;

    /**
     * 商品グループSEQリスト
     */
    private List<Integer> goodsGroupSeqList;

    // PDR Migrate Customization from here
    /** 販売期間検索条件フラグ */
    private boolean isSearchSaleTimeFlag = false;

    /** 販売状態検索条件フラグ */
    private boolean isSearchSaleStatusFlag = false;

    /** 商品番号 */
    private String goodsCode;

    /** 販売可能商品区分リスト */
    private List<String> salesGoodsTypeList;

    /** 歯科専売フラグリスト */
    private List<String> dentalMonopolySalesFlgList;

    /** 販売可否判定SEQリスト */
    private List<Integer> salesAdvisabilitySeqList;
    // PDR Migrate Customization to here

    /**
     * 税金算出時のROUNDINGモードをSQL文で直接参照できるように、
     * ENUM形式から文字列形式に変換する
     *
     * @return ROUNDINGモードの文字列形式
     */
    public String getTaxRoundingModeStr() {
        if (this.taxRoundingMode == RoundingMode.UP) {
            return "UP";
        } else {
            return "NOT-UP";
        }
    }
}
