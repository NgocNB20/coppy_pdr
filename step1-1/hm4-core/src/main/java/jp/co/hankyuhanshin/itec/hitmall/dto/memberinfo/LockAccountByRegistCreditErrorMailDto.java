/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2023 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * アカウントロック通知（クレジット登録エラー）メールDto
 *
 * @author DtoGenerator
 */
@Data
@Component
@Scope("prototype")
// 2023-renew No60 from here
public class LockAccountByRegistCreditErrorMailDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 顧客番号
     */
    private Integer customerNo;

    /**
     * 事業所名
     */
    private String officeName;

}
// 2023-renew No60 to here
