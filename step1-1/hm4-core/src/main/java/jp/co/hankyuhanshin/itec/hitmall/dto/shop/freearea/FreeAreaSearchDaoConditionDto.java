/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUkFeedInfoSendFlag;
import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * フリーエリアDao用検索条件Dtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class FreeAreaSearchDaoConditionDto extends AbstractConditionDto {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * フリーエリアキー
     */
    private String freeAreaKey;

    /**
     * フリーエリアタイトル
     */
    private String freeAreaTitle;

    /**
     * 公開開始日時(From)
     */
    private Timestamp openStartTimeFrom;

    /**
     * 公開開始日時(To)
     */
    private Timestamp openStartTimeTo;

    /**
     * 基準日
     */
    private Timestamp baseDate;

    /**
     * 公開状態区分
     */
    private List<String> openStatusList;

    /**
     * 日時タイプ  ※検索条件保持の為、追加
     */
    private String dateType;

    /**
     * 指定日時(日) ※検索条件保持の為、追加
     */
    private String targetDate;

    /**
     * 指定日時(時間) ※検索条件保持の為、追加
     */
    private String targetTime;

    /**
     * サイトマップ出力
     */
    private HTypeSiteMapFlag siteMapFlag;

    // 2023-renew No36-1, No61,67,95 from here
    /**
     * フィード情報送信
     */
    private HTypeUkFeedInfoSendFlag ukFeedInfoSendFlag;
    // 2023-renew No36-1, No61,67,95 to here
}
