/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
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
 * アンケート回答エンティティクラス。<br />
 *
 * @author matsuda
 */
@Entity
@Table(name = "QuestionnaireReply")
@Data
@Component
@Scope("prototype")
public class QuestionnaireReplyEntity implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** アンケート回答SEQ（PK） */
    @Column(name = "questionnaireReplySeq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "questionnaireReplySeq")
    private Integer questionnaireReplySeq;

    /** アンケートSEQ (FK) */
    @Column(name = "questionnaireSeq")
    private Integer questionnaireSeq;

    /** サイト種別 */
    @Column(name = "siteType")
    private HTypeSiteType siteType;

    /** デバイス種別 */
    @Column(name = "deviceType")
    private HTypeDeviceType deviceType;

    /** 会員SEQ */
    @Column(name = "memberInfoSeq")
    private Integer memberInfoSeq;

    /** 受注コード */
    @Column(name = "orderCode")
    private String orderCode;

    /** 登録日時 */
    @Column(name = "registTime", updatable = false)
    private Timestamp registTime;

    /** アンケートキー */
    @Column(name = "questionnairekey")
    private String questionnaireKey;

}
