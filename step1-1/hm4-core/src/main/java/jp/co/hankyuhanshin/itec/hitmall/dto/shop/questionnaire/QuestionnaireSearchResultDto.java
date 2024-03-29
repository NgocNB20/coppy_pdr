/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * アンケートDao用検索結果Dto(管理機能用）クラス<br />
 * <pre>
 * 検索結果を保持するためのDTOクラス。
 * </pre>
 *
 * @author matsuda
 */
@Data
@Entity
@Component
@Scope("prototype")
public class QuestionnaireSearchResultDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

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
     * アンケート公開状態PC
     */
    private HTypeOpenDeleteStatus openStatusPc;

    /**
     * アンケート公開開始時間
     */
    private Timestamp openStartTime;

    /**
     * アンケート公開終了時間
     */
    private Timestamp openEndTime;

    /**
     * 回答数
     */
    private Integer replyCount;

    /**
     * サイトマップ出力
     */
    private HTypeSiteMapFlag siteMapFlag;

}
