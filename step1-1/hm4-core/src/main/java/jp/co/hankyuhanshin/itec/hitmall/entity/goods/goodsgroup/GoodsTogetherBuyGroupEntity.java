// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRegisterMethodType;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * よく一緒に購入される商品クラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Table(name = "GoodsTogetherBuy")
@Data
@Component
@Scope("prototype")
public class GoodsTogetherBuyGroupEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品グループSEQ (FK)（必須）
     */
    @Column(name = "goodsgroupseq")
    @Id
    private Integer goodsGroupSeq;

    /**
     * 関連商品グループSEQ（必須）
     */
    @Column(name = "goodstogetherbuygroupseq")
    @Id
    private Integer goodsTogetherBuyGroupSeq;

    /**
     * 購入数
     */
    @Column(name = "boughtNumber")
    private Integer boughtNumber;

    /**
     * 表示順
     */
    @Column(name = "orderdisplay")
    private Integer orderDisplay;

    /**
     * 登録方法
     */
    @Column(name = "registmethod")
    @Id
    private HTypeRegisterMethodType registMethod;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registtime")
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updatetime")
    private Timestamp updateTime;

    // テーブル項目外追加フィールド

    /**
     * 商品グループコード
     */
    @Column(insertable = false, updatable = false)
    private String goodsGroupCode;

    /**
     * 商品グループ名
     */
    @Column(insertable = false, updatable = false)
    private String goodsGroupName;

    /**
     * 商品公開状態PC
     */
    @Column(insertable = false, updatable = false)
    private HTypeOpenDeleteStatus goodsOpenStatusPC;
}
// 2023-renew No21 to here