/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdministratorStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.List;

/**
 * 運営者検索ページ
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AdministratorModel extends AbstractModel {

    /**
     * 運営者情報取得失敗
     */
    public static final String MSGCD_ADMINISTRATOR_NO_DATA = "AYO000201";

    /**
     * ソート項目名
     */
    private String orderField;
    /**
     * ソート昇順フラグ
     */
    private boolean orderAsc;

    /**
     * 結果一覧<br/>
     */
    private List<AdministratorPageItem> resultItems;

    /**
     * 会員ID<br/>
     */
    private String administratorId;

    /**
     * 氏名<br/>
     */
    private String administratorName;

    /**
     * フリガナ<br/>
     */
    private String administratorKana;

    /**
     * メールアドレス<br/>
     */
    private String administratorMail;

    /**
     * 管理者状態<br/>
     */
    private HTypeAdministratorStatus administratorStatus;

    /**
     * 利用開始日<br/>
     */
    private Timestamp useStartDate;

    /**
     * 利用終了日<br/>
     */
    private Timestamp useEndDate;

    /**
     * 管理者グループ名<br/>
     */
    private String administratorGroupName;

    /**
     * Password change time
     */
    private Timestamp passwordChangeTime;

    /**
     * Password expiry date
     */
    private String passwordExpiryDate;

    /**
     * アカウントロック状態<br/>
     * true..ロックされている
     */
    private boolean loginFailureAccountLock;

    /**
     * アカウントロック状態<br/>
     * true..ロックされている
     */
    private boolean pwdExpiredAccountLock;

    /**
     * アカウントロック日時
     */
    public Timestamp accountLockTime;

    /**
     * Method to decide whether the account is locked or not
     *
     * @return true when account is locked, false when account is not locked
     */
    public boolean isAccountLock() {
        return loginFailureAccountLock || pwdExpiredAccountLock;
    }

    /**
     * ログイン失敗回数
     */

    private Integer loginFailureCount;

    /************************************
     ** パスワード
     ************************************/
    /**
     * パスワード変更要求フラグ<br/>
     */
    private HTypePasswordNeedChangeFlag passwordNeedChangeFlag;

    /**
     * 運営者情報エンティティ
     */
    private AdministratorEntity administratorEntity;

    /**
     * 運営者SEQ<br/>
     */
    private Integer administratorSeq;

    /**
     * パスワード<br/>
     */
    private String administratorPassword;

    /**
     * 氏名（姓）<br/>
     */
    private String administratorLastName;

    /**
     * 氏名（名）<br/>
     */
    private String administratorFirstName;

    /**
     * フリガナ(姓)<br/>
     */
    private String administratorLastKana;

    /**
     * フリガナ(名)<br/>
     */
    private String administratorFirstKana;

    /**
     * 管理者グループSEQ<br/>
     */
    private String administratorGroupSeq;
}
