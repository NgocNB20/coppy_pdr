/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.administrator;

import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;

/**
 * 管理者ログインサービス<br/>
 *
 * @author natume
 * @version $Revision: 1.4 $
 */
public interface AdministratorLoginService {

    /**
     * 運用者情報取得失敗<br/>
     * <code>MSGCD_ADMINISTRATORENTITYDTO_NULL</code><br/>
     */
    String MSGCD_ADMINISTRATORENTITYDTO_NULL = "SKA000101";

    /**
     * パスワード不一致<br/>
     * <code>MSGCD_INCORRECT_PASSWORD</code><br/>
     */
    String MSGCD_INCORRECT_PASSWORD = "SKA000102";

    /**
     * 管理者状態が停止中<br/>
     * <code>MSGCD_STATUS_STOP</code><br/>
     */
    String MSGCD_STATUS_STOP = "SKA000103";

    /**
     * 管理者パスワードがnullまたは復号できなかった場合<br/>
     * <code>MSGCD_PASSWORD_DECRYPT_FAIL</code><br/>
     */
    String MSGCD_PASSWORD_DECRYPT_FAIL = "SKA000104";

    /**
     * 管理者ログイン処理<br/>
     *
     * @param administratorId       管理者ID
     * @param administratorPassWord 管理者パスワード
     * @return 管理者エンティティ
     */
    AdministratorEntity execute(String administratorId, String administratorPassWord);

    /**
     * 運営者権限チェック処理<br/>
     *
     * @param entity AdminAuthGroupEntity
     * @return true=権限あり、false=権限なし
     */

    boolean adminAuthCheck(AdminAuthGroupEntity entity);
}
