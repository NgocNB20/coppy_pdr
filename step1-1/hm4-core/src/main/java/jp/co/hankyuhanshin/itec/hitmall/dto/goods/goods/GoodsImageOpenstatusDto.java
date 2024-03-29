/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDisplayStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 商品規格画像公開ステータスDtoクラス
 *
 * @author DtoGenerator
 */
@Entity
@Data
@Component
@Scope("prototype")
public class GoodsImageOpenstatusDto implements Serializable {

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
     * 画像ファイル名
     */
    private String imageFileName;

    /**
     * 商品管理番号
     */
    private String goodsGroupCode;

    /**
     * 商品番号
     */
    private String goodsCode;

    /**
     * 表示状態
     */
    private HTypeDisplayStatus displayFlag;

    /**
     * 公開状態PC
     */
    private HTypeOpenDeleteStatus goodsOpenStatusPc;

    /**
     * 販売状態PC
     */
    private HTypeGoodsSaleStatus saleStatusPc;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;
}
