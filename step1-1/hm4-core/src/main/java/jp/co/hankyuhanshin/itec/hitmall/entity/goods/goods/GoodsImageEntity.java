/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDisplayStatus;
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
 * 商品画像クラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Table(name = "GoodsImage")
@Data
@Component
@Scope("prototype")
public class GoodsImageEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品グループSEQ (FK)（必須）
     */
    @Column(name = "goodsGroupSeq")
    @Id
    private Integer goodsGroupSeq;

    /**
     * 商品SEQ (FK)（必須）
     */
    @Column(name = "goodsSeq")
    @Id
    private Integer goodsSeq;

    /**
     * 画像ファイル名
     */
    @Column(name = "imageFileName")
    private String imageFileName;

    /**
     * 表示状態
     */
    @Column(name = "displayFlag")
    private HTypeDisplayStatus displayFlag;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registTime", updatable = false)
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updateTime")
    private Timestamp updateTime;

    /**
     * テンポラリーファイルパス
     */
    @Column(insertable = false, updatable = false)
    private String tmpFilePath;
}
