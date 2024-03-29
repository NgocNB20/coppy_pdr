/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.loginadvisability;

import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Entity
@Table(name = "LoginAdvisability")
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
    @Column(name = "loginAdvisabilityseq")
    private Integer loginAdvisabilityseq;

    /**
     * 会員状態<br/>
     */
    @Column(name = "memberInfoStatus")
    private String memberInfoStatus;

    /**
     * 承認状態<br/>
     */
    @Column(name = "approveStatus")
    private String approveStatus;

    /**
     * オンラインログイン可否<br/>
     */
    @Column(name = "onlineloginAdvisability")
    private String onlineloginAdvisability;

    /**
     * 名簿区分<br/>
     */
    @Column(name = "memberListType")
    private String memberListType;

    /**
     * 経理区分<br/>
     */
    @Column(name = "accountingType")
    private String accountingType;

    /**
     * ログイン可否区分<br/>
     */
    @Column(name = "loginAdvisabilitytype")
    private String loginAdvisabilitytype;
    // PDR Migrate Customization to here
}
