/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.administrator.confirmmail;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdminConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;

/**
 * This class is used to send mail to operator and update the DB
 *
 * @author sk53402
 */
public interface AdminConfirmMailLogic {
    /**
     * メールパスワードの作成に失敗エラー<br/>
     * <code>MSGCD_MEMBERINFOENTITYDTO_NULL</code>
     */
    String MSGCD_MAKE_CONFIRMMAILPASSWORD_FAIL = "LIM000501";
    /**
     * 会員情報取得失敗エラー<br/>
     * ユニークIdでの取得時エラー<br/>
     * <code>MSGCD_MEMBERINFOENTITYDTO_NULL</code>
     */
    String MSGCD_ADMINISTRATORIDMAIL_FAIL = "HM34-3737-005-L-";

    /**
     * 確認メール情報登録失敗エラー<br/>
     * <code>MSGCD_CONFIRMMAIL_REGIST_FAIL</code>
     */
    String MSGCD_CONFIRMMAIL_REGIST_FAIL = "SMP000202";

    /**
     * This method will check the operator details and send mail to the operator
     *
     * @param mail            mail address entered on screen
     * @param administratorId administratorId entered on screen
     * @return true: mail send success, false: mail send failure
     */
    boolean passwordResetSendMail(String mail, String administratorId);

    /**
     * This method will return AdminConfirmMailEntity depending upon the confirm mail type
     *
     * @param aid                  random id present in mail url.
     * @param adminConfirmMailType adminConfirmMailType
     * @return AdministratorEntity instance
     */
    AdministratorEntity getAdministratorEntityByMailType(String aid, HTypeAdminConfirmMailType adminConfirmMailType);
}
