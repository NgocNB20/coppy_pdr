/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.questionnaire;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * アンケート設問回答集計エンティティクラス。<br />
 *
 * @author matsuda
 */
@Data
@Component
@Scope("prototype")
public class QuestionReplyTotalEntity implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** アンケート設問SEQ (FK) */
    private Integer questionSeq;

    /** 回答内容選択肢 */
    private String replyChoice;

    /** 選択肢別回答数 */
    private Integer replyChoiceCount;

    /** 登録日時 */
    private Timestamp registTime;

}
