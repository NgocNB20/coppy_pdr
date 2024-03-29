/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSend;

/**
 * 督促・決済期限切れメール送信フラグ変更ロジック<br/>
 * 「督促メール送信済みフラグ」、「決済期限切れメール送信済みフラグ」を変更する<br/>
 *
 * @author MN7017
 * @version $Revision:$
 */
public interface OrderMarkMailSendingLogic {

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq         編集対象のオーダーSEQ
     * @param reminderSentFlag 督促メール送信状態（変更しない場合はnull）
     * @param expiredSentFlag  決済期限切れメール送信状態（変更しない場合はnull）
     * @param orderVersionNo   登録時に指定するorderVersionNo。（incrementする場合はnull）
     * @return orderVersionNo(引数で渡されたもの ）
     */
    Integer markMailSending(Integer orderSeq,
                            HTypeSend reminderSentFlag,
                            HTypeSend expiredSentFlag,
                            Integer orderVersionNo,
                            String administratorName);

}
