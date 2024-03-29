/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.administrator.confirmmail;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdminConfirmMailType;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * AdminConfirmMail entity
 *
 * @author EntityGenerator
 */
@Entity
@Table(name = "AdminConfirmMail")
@Data
@Component
@Scope("prototype")
public class AdminConfirmMailEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 運営者確認メールSEQ
     */
    @Column(name = "adminConfirmMailSeq")
    @Id
    private Integer adminConfirmMailSeq;

    /**
     * ショップSEQ
     */
    @Column(name = "shopSeq")
    private Integer shopSeq = 0;

    /**
     * 運営者SEQ
     */
    @Column(name = "administratorSeq")
    private Integer administratorSeq = 0;

    /**
     * 運営者確認メールアドレス
     */
    @Column(name = "adminConfirmMail")
    private String adminConfirmMail;

    /**
     * 運営者確認メール種別
     */
    @Column(name = "adminConfirmMailType")
    private HTypeAdminConfirmMailType adminConfirmMailType;

    /**
     * 運営者確認メール値
     */
    @Column(name = "adminConfirmMailPassword")
    private String adminConfirmMailPassword;

    /**
     * 有効期限（必須）
     */
    @Column(name = "effectiveTime")
    private Timestamp effectiveTime;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registTime")
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updateTime")
    private Timestamp updateTime;

}
