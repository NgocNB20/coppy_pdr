/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.multipayment;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 与信枠解放取得結果クラス
 *
 * @author DtoGenerator
 */

@Data
@Component
@Scope("prototype")
public class MulPayBillResultDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * オーダーID
     */
    private String orderId;

    /**
     * 受注Seq
     */
    private Integer orderSeq;

    /**
     * 受注履歴連番
     */
    private Integer orderVersionNo;

    /**
     * 処理区分
     */
    private String jobCd;

    /**
     * ACS 呼出判定
     */
    private String acs;

    /**
     * トランザクション種別
     */
    private String tranExec;
}
