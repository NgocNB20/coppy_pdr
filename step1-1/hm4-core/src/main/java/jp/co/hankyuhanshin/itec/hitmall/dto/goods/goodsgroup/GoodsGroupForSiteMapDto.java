/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * サイトマップXML用商品グループ情報
 */
@Entity
@Data
@Component
@Scope("prototype")
public class GoodsGroupForSiteMapDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;

    /**
     * 商品グループコード
     */
    private String goodsGroupCode;

    /**
     * 公開状態
     */
    private HTypeOpenDeleteStatus openStatus;

    /**
     * 最遅更新日時（カテゴリ、カテゴリ表示いずれかの最遅）
     */
    private Timestamp latestUpdateTime;

    /**
     * 画像種別内連番
     */
    private Integer imageTypeVersionNo;

    // PDR Migrate Customization from here
    /** 規格画像コード */
    private String unitImageCode;

    /** 規格画像グループNO */
    private Integer unitimagegroupno;
    // PDR Migrate Customization to here
}
