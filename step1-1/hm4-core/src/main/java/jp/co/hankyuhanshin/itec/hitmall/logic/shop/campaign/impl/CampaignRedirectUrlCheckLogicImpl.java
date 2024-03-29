/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignRedirectUrlCheckLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * リダイレクトURLチェックロジック<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Component
public class CampaignRedirectUrlCheckLogicImpl extends AbstractShopLogic implements CampaignRedirectUrlCheckLogic {
    /**
     * リダイレクトURLチェック<br/>
     * 画面で入力されたリダイレクトURLのチェックを行う<br/>
     * 広告URLと同一の値が入力された場合、無限ループとなる為、リダイレクトURLをチェックする必要あり<br/>
     * 確認画面遷移時、shophm36-hm36-front-pc/view/front/から/shophm36/へ変換される為、両方チェックする<br/>
     * 確認画面遷移時、shophm36-hm36-front-mobile/view/mobile/から/shophm36/m/へ変換される為、両方チェックする<br/>
     * http, https共にチェックを行う<br/>
     *
     * @param redirectUrl リダイレクトURL
     * @return true:リダイレクトURLチェックOK false:リダイレクトURLチェックNG
     */
    @Override
    public boolean execute(String redirectUrl) {

        // #チケット 1509 広告URL無限ループ対応 リダイレクトのURLをチェックすることで対応する
        String targetUrlPc = PropertiesUtil.getSystemPropertiesValue("campaign.default.redirect.url.pc")
                                           .replace("index.html", "");
        String replaceUrlPc = PropertiesUtil.getSystemPropertiesValue("campaign.url.check.replace.pc");

        String[] checkCampainrUrl = new String[2];

        // shophm36-hm36-front-pc/view/front/はshophm36/へ変換される為、どちらもチェックを行う
        checkCampainrUrl[0] =
                        PropertiesUtil.getSystemPropertiesValue("campaign.url.pc").replace(targetUrlPc, replaceUrlPc);
        // 上記のＰＣと同様にモバイルを変換前・返還後両方のチェックが必要
        checkCampainrUrl[1] = PropertiesUtil.getSystemPropertiesValue("campaign.url.pc");

        // http,https どちらで入力されても対応する為、正規表現でカットする
        Matcher matcherPcReplace = Pattern.compile("http://").matcher(checkCampainrUrl[0]);
        Matcher matcherPc = Pattern.compile("http://").matcher(checkCampainrUrl[1]);

        if (redirectUrl.startsWith(checkCampainrUrl[0]) || redirectUrl.startsWith(
                        matcherPcReplace.replaceFirst("https://")) || redirectUrl.startsWith(checkCampainrUrl[1])
            || redirectUrl.startsWith(matcherPc.replaceFirst("https://"))) {

            return false;
        }

        return true;
    }
}
