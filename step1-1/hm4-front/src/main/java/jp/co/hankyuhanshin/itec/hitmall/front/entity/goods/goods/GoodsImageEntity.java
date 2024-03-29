/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDisplayStatus;
import lombok.Data;
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
    private Integer goodsGroupSeq;

    /**
     * 商品SEQ (FK)（必須）
     */
    private Integer goodsSeq;

    /**
     * 画像ファイル名
     */
    private String imageFileName;

    /**
     * 表示状態
     */
    private HTypeDisplayStatus displayFlag;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;

    /**
     * テンポラリーファイルパス
     */
    private String tmpFilePath;
}
