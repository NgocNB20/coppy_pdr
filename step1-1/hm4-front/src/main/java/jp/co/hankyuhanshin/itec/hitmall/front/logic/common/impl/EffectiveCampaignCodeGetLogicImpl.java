package jp.co.hankyuhanshin.itec.hitmall.front.logic.common.impl;

import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.EffectiveCampaignCodeGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.EffectiveCampaignCodeResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.logic.common.EffectiveCampaignCodeGetLogic;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Timestamp;

@Component
public class EffectiveCampaignCodeGetLogicImpl implements EffectiveCampaignCodeGetLogic {

    /**
     * 日付関連Helper取得
     */
    private final DateUtility dateUtility;

    private final ShopApi shopApi;

    public EffectiveCampaignCodeGetLogicImpl(DateUtility dateUtility, ShopApi shopApi) {
        this.dateUtility = dateUtility;
        this.shopApi = shopApi;
    }

    /**
     * 有効なキャンペーンコード取得処理<br/>
     *
     * @param shopSeq   ショップSEQ
     * @param accessUid 端末識別番号
     * @return キャンペーンコード
     */
    @Override
    public String execute(Integer shopSeq, String accessUid) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("accessUid", accessUid);

        // システムプロパティよりキャンペーン有効日数を取得
        String effectiveDay = PropertiesUtil.getSystemPropertiesValue("campaign.effective.day");
        if (StringUtil.isEmpty(effectiveDay)) {
            throw new RuntimeException("設定不備 system.properties campaign.effective.dayの値が不正");
        }

        // 現在日
        Timestamp currentDay = dateUtility.getCurrentDate();

        // 開始日時 有効期間マイナスの日時
        Timestamp startDate = dateUtility.getAmountDayTimestamp(Integer.parseInt(effectiveDay), false, currentDay);

        // 終了日時 現在日時
        Timestamp endDate = currentDay;

        // キャンペーンコード取得 取得できない場合は、空文字
        EffectiveCampaignCodeGetRequest effectiveCampaignCodeGetRequest = new EffectiveCampaignCodeGetRequest();
        effectiveCampaignCodeGetRequest.setAccessUid(accessUid);
        effectiveCampaignCodeGetRequest.setStartDate(startDate);
        effectiveCampaignCodeGetRequest.setEndDate(endDate);
        EffectiveCampaignCodeResponse response = null;
        try {
            response = shopApi.getEffectiveCampaignCode(effectiveCampaignCodeGetRequest);

        } catch (HttpClientErrorException e) {
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
        }
        String campaignCode = response.getCampaignCode();
        if (campaignCode == null) {
            campaignCode = "";
        }
        return campaignCode;
    }

}
