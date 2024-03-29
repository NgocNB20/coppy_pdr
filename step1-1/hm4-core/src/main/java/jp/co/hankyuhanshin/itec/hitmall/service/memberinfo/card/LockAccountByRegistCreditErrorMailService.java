/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2023 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.card;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.LockAccountByRegistCreditErrorMailDto;

/**
 * アカウントロック通知（クレジット登録エラー）メール送信サービス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
// 2023-renew No60 from here
public interface LockAccountByRegistCreditErrorMailService {

    /**
     * アカウントロック通知（クレジット登録エラー）メールを送信する
     *
     * @param lockAccountByRegistCreditErrorMailDto アカウントロック通知（クレジット登録エラー）メールDto
     */
    void execute(LockAccountByRegistCreditErrorMailDto lockAccountByRegistCreditErrorMailDto);

}
// 2023-renew No60 to here
