package jp.co.hankyuhanshin.itec.hitmall.logic.common.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.access.AccessInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.common.EffectiveCampaignCodeGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class EffectiveCampaignCodeGetLogicImpl extends AbstractShopLogic implements EffectiveCampaignCodeGetLogic {

    /**
     *  日付関連Helper取得
     */
    private final DateUtility dateUtility;

    /**
     * アクセス情報Daoクラス
     */
    private final AccessInfoDao accessInfoDao;

    public EffectiveCampaignCodeGetLogicImpl(DateUtility dateUtility, AccessInfoDao accessInfoDao) {
        this.dateUtility = dateUtility;
        this.accessInfoDao = accessInfoDao;
    }

    /**
     * 有効なキャンペーンコード取得処理<br/>
     *
     * @param shopSeq ショップSEQ
     * @param accessUid 端末識別番号
     * @return キャンペーンコード
     */
    @Override
    public String execute(Integer shopSeq, String accessUid) {

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
        String campaignCode = accessInfoDao.getEffectiveCampaignCode(shopSeq, accessUid, startDate, endDate);
        if (campaignCode == null) {
            campaignCode = "";
        }
        return campaignCode;
    }

}
