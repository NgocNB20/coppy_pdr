/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.loginadvisability;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@Scope("prototype")
public class LoginAdvisabilityEntity implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /**
     * ログイン可否判定SEQ<br/>
     */
    private Integer loginAdvisabilityseq;

    /**
     * 会員状態<br/>
     */
    private String memberInfoStatus;

    /**
     * 承認状態<br/>
     */
    private String approveStatus;

    /**
     * オンラインログイン可否<br/>
     */
    private String onlineloginAdvisability;

    /**
     * 名簿区分<br/>
     */
    private String memberListType;

    /**
     * 経理区分<br/>
     */
    private String accountingType;

    /**
     * ログイン可否区分<br/>
     */
    private String loginAdvisabilitytype;
    // PDR Migrate Customization to here
}
