/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRegisterMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 商品検索結果Dtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Data
@Component
@Scope("prototype")
public class GoodsSearchResultDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;

    /**
     * 商品SEQ
     */
    private Integer goodsSeq;

    /**
     * 商品グループコード
     */
    private String goodsGroupCode;

    /**
     * 商品公開状態PC
     */
    private HTypeOpenDeleteStatus goodsOpenStatusPC;

    /**
     * 商品公開開始日時PC
     */
    private Timestamp openStartTimePC;

    /**
     * 商品公開終了日時PC
     */
    private Timestamp openEndTimePC;

    // 2023-renew No64 from here
    /**
     * 商品グループ名（管理用）
     */
    private String goodsGroupNameAdmin;
    // 2023-renew No64 to here

    /**
     * 商品コード
     */
    private String goodsCode;

    /**
     * 販売状態PC
     */
    private HTypeGoodsSaleStatus saleStatusPC;

    /**
     * 販売開始日時PC
     */
    private Timestamp saleStartTimePC;

    /**
     * 販売終了日時PC
     */
    private Timestamp saleEndTimePC;

    /**
     * 規格管理フラグ
     */
    private HTypeUnitManagementFlag unitManagementFlag;

    /**
     * 規格値１
     */
    private String unitValue1;

    /**
     * 規格値２
     */
    private String unitValue2;

    /**
     * 商品単価（税抜）
     */
    private BigDecimal goodsPrice;

    /**
     * 在庫管理フラグ
     */
    private HTypeStockManagementFlag stockmanagementflag;

    /**
     * 販売可能在庫数
     */
    private BigDecimal salesPossibleStock;

    /**
     * 実在庫数
     */
    private BigDecimal realStock;

    /**
     * 商品個別配送種別
     */
    private HTypeIndividualDeliveryType individualDeliveryType;

    // 2023-renew No21 from here
    /**
     * 登録方法
     */
    private HTypeRegisterMethodType registMethod;
    // 2023-renew No21 to here
}
