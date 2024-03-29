/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * カテゴリ内商品詳細Dtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Data
@Component
@Scope("prototype")
public class CategoryGoodsDetailsDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * カテゴリSEQ
     */
    private Integer categorySeq;

    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;

    /**
     * 表示順
     */
    private Integer orderDisplay;

    /**
     * 登録日時
     */
    private Timestamp registTime;

    /**
     * 更新日時
     */
    private Timestamp updateTime;

    /**
     * 商品グループコード
     */
    private String goodsGroupCode;

    // 2023-renew No64 from here
    /**
     * 商品グループ名（管理用）
     */
    private String goodsGroupNameAdmin;
    // 2023-renew No64 to here

    /**
     * 商品単価
     */
    private BigDecimal goodsPrice;

    /**
     * 新着日付
     */
    private Timestamp whatsnewDate;

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

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * 更新カウンタ
     */
    private Integer versionNo;
}
