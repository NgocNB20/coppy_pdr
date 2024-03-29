package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignListGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignListGetByCodeService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 広告リスト取得<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Service
public class CampaignListGetByCodeServiceImpl extends AbstractShopService implements CampaignListGetByCodeService {

    /** キャンペーンリスト取得ロジック */
    private final CampaignListGetByCodeLogic campaignListGetByCodeLogic;

    @Autowired
    public CampaignListGetByCodeServiceImpl(CampaignListGetByCodeLogic campaignListGetByCodeLogic) {
        this.campaignListGetByCodeLogic = campaignListGetByCodeLogic;
    }

    /**
     * 広告リスト取得<br/>
     *
     * @param searchCondition 広告検索条件DTO
     * @return List<CampaignEntity> 広告エンティティリスト
     */
    @Override
    public List<CampaignEntity> execute(CampaignSearchForDaoConditionDto searchCondition) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("searchCondition", searchCondition);

        // (2) 共通情報チェック
        // ショップSEQ ： null(or 0) の場合 エラーとして処理を終了する
        // サイト区分 ： null(or 空文字) の場合 エラーとして処理を終了する
        Integer shopSeq = 1001;

        searchCondition.setShopSeq(shopSeq);
        return campaignListGetByCodeLogic.execute(searchCondition);
    }
}
