/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteType;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * アンケート回答エンティティクラス。<br />
 *
 * @author matsuda
 */
@Data
@Component
@Scope("prototype")
public class QuestionnaireReplyEntity implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** アンケート回答SEQ（PK） */
    private Integer questionnaireReplySeq;

    /** アンケートSEQ (FK) */
    private Integer questionnaireSeq;

    /** サイト種別 */
    private HTypeSiteType siteType;

    /** デバイス種別 */
    private HTypeDeviceType deviceType;

    /** 会員SEQ */
    private Integer memberInfoSeq;

    /** 受注コード */
    private String orderCode;

    /** 登録日時 */
    private Timestamp registTime;

    /** アンケートキー */
    private String questionnaireKey;

}
