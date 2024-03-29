/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * アンケートDao用検索条件Dto(管理機能用）クラス<br />
 * <pre>
 * 検索条件を保持するためのDTOクラス。
 * </pre>
 *
 * @author matsuda
 */
@Data
@Entity
@Component
@Scope("prototype")
public class QuestionnaireSearchForBackDto extends AbstractConditionDto {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * サイト区分
     */
    private String site;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * アンケートSEQ
     */
    private Integer questionnaireSeq;

    /**
     * アンケートキー
     */
    private String questionnaireKey;

    /**
     * アンケート名称
     */
    private String name;

    /**
     * 回答数（From）
     */
    private Integer replyCountFrom;

    /**
     * 回答数（To）
     */
    private Integer replyCountTo;

    /**
     * サイトマップ出力
     */
    private HTypeSiteMapFlag siteMapFlag;

    /**
     * 管理メモ
     */
    private String memo;

    /**
     * アンケート公開状態
     */
    private HTypeOpenDeleteStatus openStatus;

    /**
     * アンケート公開開始日時(From)
     */
    private Timestamp openStartTimeFrom;

    /**
     * アンケート公開開始日時(To)
     */
    private Timestamp openStartTimeTo;

    /**
     * アンケート公開終了日時(From)
     */
    private Timestamp openEndTimeFrom;

    /**
     * アンケート公開終了日時(To)
     */
    private Timestamp openEndTimeTo;
}
