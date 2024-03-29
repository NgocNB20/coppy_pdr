/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire;

import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * アンケート回答集計エンティティクラス。<br />
 *
 * @author matsuda
 */
@Entity
@Table(name = "QuestionnaireReplyTotal")
@Data
@Component
@Scope("prototype")
public class QuestionnaireReplyTotalEntity implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** アンケートSEQ (FK) */
    @Column(name = "questionnaireSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "questionnaireSeq")
    private Integer questionnaireSeq;

    /** 回答数 */
    @Column(name = "replyCount")
    private Integer replyCount;

    /** 登録日時 */
    @Column(name = "registTime")
    private Timestamp registTime;

}
