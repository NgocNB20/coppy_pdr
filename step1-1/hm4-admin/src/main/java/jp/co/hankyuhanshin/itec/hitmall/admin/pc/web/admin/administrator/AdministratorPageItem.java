/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 運営者検索結果画面情報<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
@Data
@Component
@Scope("prototype")
public class AdministratorPageItem implements Serializable {

    /**
     * シリアルバージョンID<br/>
     */
    private static final long serialVersionUID = 1L;

    /**
     * seq<br/>
     */
    private Integer administratorSeq;

    /**
     * No<br/>
     */
    private Integer resultNo;
    /**
     * ID<br/>
     */
    private String resultAdministratorId;
    /**
     * 姓<br/>
     */
    private String resultAdministratorLastName;
    /**
     * 名<br/>
     */
    private String resultAdministratorFirstName;
    /**
     * 姓カナ<br/>
     */
    private String resultAdministratorLastKana;
    /**
     * 名カナ<br/>
     */
    private String resultAdministratorFirstKana;
    /**
     * メールアドレス<br/>
     */
    private String resultMail;
    /**
     * 状態<br/>
     */
    private String resultAdministratorStatus;
    /**
     * 利用開始日<br/>
     */
    private Timestamp resultUseStartDate;
    /**
     * 利用終了日<br/>
     */
    private Timestamp resultUseEndDate;
    /**
     * 管理者グループ名<br/>
     */
    private String resultAdministratorGroupName;

}
