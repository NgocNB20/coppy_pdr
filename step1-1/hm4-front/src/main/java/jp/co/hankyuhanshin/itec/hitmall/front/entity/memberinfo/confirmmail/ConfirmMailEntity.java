/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.confirmmail;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeConfirmMailType;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 確認メールクラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class ConfirmMailEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 確認メールSEQ（必須）
     */
    private Integer confirmMailSeq;

    /**
     * ショップSEQ（必須）
     */
    private Integer shopSeq;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq = 0;

    /**
     * 受注SEQ
     */
    private Integer orderSeq = 0;

    /**
     * 確認メールアドレス
     */
    private String confirmMail;

    /**
     * 確認メール種別（必須）
     */
    private HTypeConfirmMailType confirmMailType;

    /**
     * 確認メールパスワード（必須）
     */
    private String confirmMailPassword;

    /**
     * 有効期限（必須）
     */
    private Timestamp effectiveTime;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;
}
