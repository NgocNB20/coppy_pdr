/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankaku;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * PDR#022 ユーザー毎の支払方法表示制御<br/>
 * 登録済みカード情報Itemクラス
 *
 * @author satoh
 */
@Data
@Component
@Scope("prototype")
// PDR Migrate Customization from here
public class PaymentModelRegistedCardItem implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * カード番号（動的バリデータ）
     */
    @HCHankaku
    private String registedCardNumber;

    /**
     * 有効期限（月）
     */
    private String registedExpirationDateMonth;

    /**
     * 有効期限（年）
     */
    private String registedExpirationDateYear;

    /**
     * カード会社
     */
    private String registedCardBrand;

    /**
     * 顧客カードID
     */
    private String customerCardId;

    /**
     * 保持カード値
     */
    private String registCardSelectValue;
}
// PDR Migrate Customization to here