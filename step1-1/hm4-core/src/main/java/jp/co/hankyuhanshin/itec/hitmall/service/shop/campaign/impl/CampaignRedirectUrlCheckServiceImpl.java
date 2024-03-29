/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignRedirectUrlCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignRedirectUrlCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * リダイレクトURLチェック<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Component
public class CampaignRedirectUrlCheckServiceImpl extends AbstractShopService
                implements CampaignRedirectUrlCheckService {

    /** リダイレクトURLチェックロジック */
    private final CampaignRedirectUrlCheckLogic campaignRedirectUrlCheckLogic;

    @Autowired
    public CampaignRedirectUrlCheckServiceImpl(CampaignRedirectUrlCheckLogic campaignRedirectUrlCheckLogic) {
        this.campaignRedirectUrlCheckLogic = campaignRedirectUrlCheckLogic;
    }

    /**
     * リダイレクトURLチェック<br/>
     *　画面で入力されたリダイレクトURLのチェックを行う<br/>
     *
     * @param redirectUrl リダイレクトURL
     * @return true:リダイレクトURLチェックOK false:リダイレクトURLチェックNG
     */
    @Override
    public boolean execute(String redirectUrl) {
        // #チケット　1509　広告URL無限ループ対応 リダイレクトのURLをチェックすることで対応する
        return campaignRedirectUrlCheckLogic.execute(redirectUrl);
    }
}
