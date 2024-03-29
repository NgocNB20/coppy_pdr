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
 * アンケート回答集計エンティティクラス。<br />
 *
 * @author matsuda
 */
@Data
@Component
@Scope("prototype")
public class QuestionnaireReplyTotalEntity implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** アンケートSEQ (FK) */
    private Integer questionnaireSeq;

    /** 回答数 */
    private Integer replyCount;

    /** 登録日時 */
    private Timestamp registTime;

}
