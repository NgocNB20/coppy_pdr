/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.questionnaire.QuestionnaireReplyDisplayDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * アンケートModel
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class QuestionnaireModel extends AbstractModel {

    /** アンケート回答キー */
    private String qkey;

    /** 確認画面からの遷移フラグ */
    private boolean isEdit;

    /***アンケート表示名PC */
    private String namePc;

    /**アンケート説明文PC */
    private String freeTextPc;

    /** アンケート回答完了文PC */
    private String completeTextPc;

    /** アンケートSEQ */
    private Integer questionnaireSeq;

    /** アンケートエンティティ */
    private QuestionnaireEntity questionnaireEntity;

    /**
     * アンケート回答画面表示用DTOリスト
     * この下の行に記載のプロパティの説明、設定値内容はDTO内のコメントを参照。
     * 確認画面へ引き継ぐ。
     * 画面表示に使用するため、Dto名＋"Items"
     */
    private List<QuestionnaireReplyDisplayDto> questionnaireReplyDisplayDtoItems;
}
