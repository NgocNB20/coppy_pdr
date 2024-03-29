/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo;

/**
 * 会員退会更新サービス<br/>
 *
 * @author natume
 * @version $Revision: 1.3 $
 */
public interface MemberInfoRemoveService {

    /**
     * 会員情報取得失敗<br/>
     * <code>SMM000401</code>
     */
    String MSGCD_NOT_EXSIT = "SMM000401";

    /**
     * ID不一致<br/>
     * <code>SMM000403</code>
     */
    String MSGCD_ID_FAIL = "SMM000402";

    /**
     * パスワード不一致<br/>
     * <code>SMM000404</code>
     */
    String MSGCD_PASSWORD_FAIL = "SMM000403";

    /**
     * 会員情報更新失敗<br/>
     * <code>SMM000405</code>
     */
    String MSGCD_UPDATE_FAIL = "SMM000404";

    /**
     * 会員退会処理<br/>
     *
     * @param memberInfoId       会員ID
     * @param memberInfoPassWord 会員パスワード
     */
    void execute(String memberInfoId, String memberInfoPassWord, Integer memberInfoSeq, String accessUid);

}
