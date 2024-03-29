/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 配送方法設定画面表示アイテム
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class DeliveryResultItem implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 配送方法エンティティ
     */
    private DeliveryMethodEntity deliveryMethodEntity;

    /**
     * ラジオボタン
     */
    private Integer deliveryMethodRadioValue;

    /**
     * 表示順
     */
    private Integer orderDisplay;

    /**
     * 配送方法SEQ
     */
    private Integer deliveryMethodSeq;

    /**
     * 配送方法名
     */
    private String deliveryMethodName;

    /**
     * 公開状態PC
     */
    private HTypeOpenDeleteStatus openStatusPC;

    /**
     * 公開状態携帯
     */
    private HTypeOpenDeleteStatus openStatusMB;

    /**
     * 配送方法種別
     */
    private HTypeDeliveryMethodType deliveryMethodType;
}
