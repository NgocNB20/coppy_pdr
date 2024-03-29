/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.access;

import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 広告効果Dtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.4 $
 */
@Data
@Component
@Entity
@Scope("prototype")
public class CampaignEffectDto implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** キャンペーンSEQ */
    private Integer campaignSeq;

    /** ショップSEQ */
    private Integer shopSeq;

    /** キャンペーンコード */
    private String campaignCode;

    /** キャンペーン名 */
    private String campaignName;

    /** 備考 */
    private String note;

    /** クリック数 */
    private Integer clickCount;

    /** 注文完了数 */
    private Integer orderCount;

    /** 注文完了比率 */
    private String orderCountRatio;

    /** 会員登録数 */
    private Integer memberRegistCount;

    /** 会員登録比率 */
    private String memberRegistCountRatio;

    /** 会員退会数 */
    private Integer memberDeleteCount;

    /** 会員退会比率 */
    private String memberDeleteCountRatio;

    /** 広告コスト */
    private String advertiseCost;

    /** 受注金額 */
    private String orderPrice;

    /** コスト比率 */
    private String advertiseCostRatio;

}
