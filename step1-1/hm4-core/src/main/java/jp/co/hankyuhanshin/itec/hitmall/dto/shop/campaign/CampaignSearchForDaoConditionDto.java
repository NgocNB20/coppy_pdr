/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign;

import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * キャンペーンDao用検索条件Dtoクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Data
@Component
@Scope("prototype")
public class CampaignSearchForDaoConditionDto extends AbstractConditionDto {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** ショップSEQ */
    private Integer shopSeq;

    /** 広告コード */
    private String campaignCode;

    /** 広告名 */
    private String campaignName;

    /** メモ */
    private String note;

    /** キャンペーンコードリスト */
    private List<String> campaignCodeList;

}
