/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign;

/**
 * リダイレクトURLチェックロジック<br/>
 *
 * @author @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
public interface CampaignRedirectUrlCheckLogic {

    /**
     * リダイレクトURLチェック<br/>
     * 画面で入力されたリダイレクトURLのチェックを行う<br/>
     *
     * @param redirectUrl リダイレクトURL
     * @return true:リダイレクトURLチェックOK false:リダイレクトURLチェックNG
     */
    public boolean execute(String redirectUrl);

}
