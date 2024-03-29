/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign;

/**
 * リダイレクトURLチェック<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
public interface CampaignRedirectUrlCheckService {

    /**
     * リダイレクトURLチェック<br/>
     *　画面で入力されたリダイレクトURLのチェックを行う<br/>
     *
     * @param redirectUrl リダイレクトURL
     * @return true:リダイレクトURLチェックOK false:リダイレクトURLチェックNG
     */
    boolean execute(String redirectUrl);

}
