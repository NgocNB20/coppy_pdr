/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitImageFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeUnitManagementFlag;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 商品クラス
 * <pre>
 * 画面入力を行う必須項目を追加した場合はGoodsRegistLogicImpl#getGoodsEntityByOverwritedRequiredItem
 * </pre>
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class GoodsEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品SEQ（必須）
     */
    private Integer goodsSeq;

    /**
     * 商品グループSEQ (FK)（必須）
     */
    private Integer goodsGroupSeq;

    /**
     * 商品コード（必須）
     */
    private String goodsCode;

    /**
     * JANコード
     */
    private String janCode;

    /**
     * カタログコード
     */
    private String catalogCode;

    /**
     * 販売状態PC（必須）
     */
    private HTypeGoodsSaleStatus saleStatusPC = HTypeGoodsSaleStatus.NO_SALE;

    /**
     * 販売開始日時PC
     */
    private Timestamp saleStartTimePC;

    /**
     * 販売終了日時PC
     */
    private Timestamp saleEndTimePC;

    /**
     * 商品単価（必須）
     */
    private BigDecimal goodsPrice = BigDecimal.ZERO;

    /**
     * 値引前単価
     */
    private BigDecimal preDiscountPrice;

    /**
     * 商品個別配送種別（必須）
     */
    private HTypeIndividualDeliveryType individualDeliveryType = HTypeIndividualDeliveryType.OFF;

    /**
     * 無料配送フラグ（必須）
     */
    private HTypeFreeDeliveryFlag freeDeliveryFlag = HTypeFreeDeliveryFlag.OFF;

    /**
     * 規格管理フラグ（必須）
     */
    private HTypeUnitManagementFlag unitManagementFlag = HTypeUnitManagementFlag.OFF;

    /**
     * 規格値１
     */
    private String unitValue1;

    /**
     * 規格値２
     */
    private String unitValue2;

    /**
     * 在庫管理フラグ（必須）
     */
    private HTypeStockManagementFlag stockManagementFlag = HTypeStockManagementFlag.OFF;

    /**
     * 商品最大購入可能数（必須）
     */
    private BigDecimal purchasedMax = BigDecimal.ZERO;

    /**
     * 表示順
     */
    private Integer orderDisplay;

    /**
     * ショップSEQ（必須）
     */
    private Integer shopSeq;

    /**
     * 更新カウンタ（必須）
     */
    // 商品CSV用に排他チェックしない @Version
    private Integer versionNo = 0;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;

    // PDR Migrate Customization from here
    //    /**
    //     * PDR#10 07_データ連携（商品情報）新規情報を商品情報に追加<br/>
    //     * <pre>
    //     * ・保留フラグを追加
    //     * ・単位を追加
    //     * ・価格記号表示フラグを追加
    //     * ・セール価格記号表示フラグを追加
    //     * ・管理商品コードを追加
    //     * ・商品分類コードを追加
    //     * ・カテゴリー1を追加
    //     * ・カテゴリー2を追加
    //     * ・カテゴリー3を追加
    //     * ・陸送商品フラグを追加
    //     * ・クール便フラグを追加
    //     * ・クール便適用期間Fromを追加
    //     * ・クール便適用期間Toを追加
    //     * ・セール価格整合性フラグを追加
    //     * </pre>
    //     *
    //     */
    /**
     * 保留フラグ<br/>
     */
    private HTypeReserveFlag reserveFlag;

    /**
     * 単位<br/>
     */
    private String unit;

    /**
     * 価格記号表示フラグ<br/>
     */
    private HTypePriceMarkDispFlag priceMarkDispFlag;

    /**
     * セール価格記号表示フラグ<br/>
     */
    private HTypeSalePriceMarkDispFlag salePriceMarkDispFlag;

    /**
     * 管理商品コード<br/>
     */
    private String goodsManagementCode;

    /**
     * 商品分類コード<br/>
     */
    private Integer goodsDivisionCode;

    /**
     * カテゴリー1<br/>
     */
    private Integer goodsCategory1;

    /**
     * カテゴリー2<br/>
     */
    private Integer goodsCategory2;

    /**
     * カテゴリー3<br/>
     */
    private Integer goodsCategory3;

    /**
     * 陸送商品フラグ<br/>
     */
    private HTypeLandSendFlag landSendFlag;

    /**
     * クール便フラグ<br/>
     */
    private HTypeCoolSendFlag coolSendFlag;

    /**
     * クール便適用期間From<br/>
     */
    private String coolSendFrom;

    /**
     * クール便適用期間To<br/>
     */
    private String coolSendTo;

    /**
     * セール価格整合性フラグ<br/>
     */
    private HTypeSalePriceIntegrityFlag salePriceIntegrityFlag;

    /**
     * 心意気価格保持区分<br/>
     */
    private HTypeEmotionPriceType emotionPriceType;
    // PDR Migrate Customization to here

    // 2023-renew No76 from here
    /**
     * 規格画像有無
     */
    private HTypeUnitImageFlag unitImageFlag;
    // 2023-renew No76 to here
}
