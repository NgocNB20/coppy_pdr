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
public class ReStockDetailsResultDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * CSV出力用キー(商品SEQ+入荷状態+配信ID+配信状況+会員seq)
     */
    private String detailsKey;

    /*************************
     * 商品情報
     *************************/

    /**
     * 商品番号
     */
    private Integer goodsSeq;

    /**
     * 商品番号
     */
    private String goodsCode;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 入荷状態
     */
    private HTypeReStockStatus reStockStatus;

    /**
     * 配信ID
     */
    private String deliveryId;

    /**
     * 入荷日時
     */
    private Timestamp reStockTime;

    /*************************
     * 顧客情報
     *************************/

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * 顧客番号
     */
    private Integer customerNo;

    /**
     * 顧客名
     */
    private String memberInfoLastName;

    /**
     * 会員ID
     */
    private String memberInfoId;

    /**
     * 入荷お知らせ登録日時
     */
    private Timestamp registTime;

    /**
     * 入荷メール送信状態
     */
    private HTypeMailDeliveryStatus deliveryStatus;

    /**
     * 更新カウンタ
     */
    private Integer versionNo;

}
