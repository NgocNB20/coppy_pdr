/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionReplyTotalEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * アンケートDao用詳細画面表示Dtoクラス<br />
 * <pre>
 * 詳細画面のアンケート設問情報・アンケート設問回答集計情報を保持するためのDTOクラス。
 * </pre>
 * @author matsuda
 */
@Data
@Component
@Scope("prototype")
public class QuestionDetailsDto implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** アンケート設問 */
    private QuestionEntity questionEntity;

    /** アンケート設問回答集計リスト */
    private List<QuestionReplyTotalEntity> questionReplyTotalEntityList;

}
