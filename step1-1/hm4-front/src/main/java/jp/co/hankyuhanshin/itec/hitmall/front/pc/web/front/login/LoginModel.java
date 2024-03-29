/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.login;

import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;

/**
 * 会員ログインModel<br/>
 *
 * @author kaneda
 */
@Data
public class LoginModel extends AbstractModel {

    /**
     * 初期値の設定
     */
    public LoginModel() {
        // PDR Migrate Customization from here
        autoLoginFlg = false;
        // PDR Migrate Customization to here
    }

    /**
     * 会員ID
     */
    private String memberInfoId;

    /**
     * 管理者ID
     */
    private String administratorId;

    /**
     * 会員SEQ
     */
    private String proxyMemberInfoSeq;

    /**
     * パスワード
     */
    private String memberInfoPassWord;

    /**
     * パスワードAdmin
     */
    private String administratorPassWord;

    /**
     * 自動ログインフラグ
     */
    private boolean autoLoginFlg;

    // PDR Migrate Customization from here
    /** 会員ID 又は 顧客番号 */
    public String memberInfoIdOrCustomerNo;
    // PDR Migrate Customization to here

    /**
     * アカウントロック機能が利用可能かどうか<br/>
     *
     * @return true..利用可能
     */
    public boolean isAvailableAccountLock() {
        MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);
        return memberInfoUtility.isAvailableAccountLock();
    }

    /**
     * アカウントロック数を返却<br/>
     * 画面の文言表示に利用
     *
     * @return 設定ファイルのアカウントロック数
     */
    public String getAccountLockCount() {
        MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);
        return Integer.toString(memberInfoUtility.getAccountLockCount());
    }
}
