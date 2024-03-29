/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockStatus;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 入荷お知らせ商品検索結果Dtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Data
@Component
@Scope("prototype")
public class ReStockSearchResultDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * CSV出力用キー(商品SEQ+入荷状態+配信ID+配信状況)
     */
    private String key;

    /**
     * 商品SEQ
     */
    private Integer goodsSeq;

    /**
     * 商品管理番号
     */
    private String goodsGroupCode;

    /**
     * 商品番号
     */
    private String goodsCode;

    /**
     * 登録顧客件数
     */
    private Integer registMemberCount;

    /**
     * 入荷状態
     */
    private HTypeReStockStatus reStockStatus;

    /**
     * 配信ID
     */
    private String deliveryId;

    /**
     * 入荷メール送信状態
     */
    private HTypeMailDeliveryStatus deliveryStatus;

    /**
     * 入荷日時
     */
    private Timestamp reStockTime;
}
