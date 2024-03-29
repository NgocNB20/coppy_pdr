/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.access.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.access.AccessInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.AccessInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.CampaignEffectDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.access.AccessCampaignEffectListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * AccessInfo のエンティティを更新用に取得する
 *
 * @author tm27400
 * @version $Revision: 1.4 $
 *
 */
@Component
public class AccessCampaignEffectListGetLogicImpl extends AbstractShopLogic
                implements AccessCampaignEffectListGetLogic {

    /** アクセス情報DAO */
    private final AccessInfoDao accessInfoDao;

    @Autowired
    public AccessCampaignEffectListGetLogicImpl(AccessInfoDao accessInfoDao) {
        this.accessInfoDao = accessInfoDao;
    }

    /**
     *
     * 広告効果リスト取得<br/>
     *
     * @param accessInfoSearchForDaoConditionDto 広告効果検索条件DTO
     * @return List<AccessInfoSearchForDaoConditionDto> 広告効果リスト
     */
    @Override
    public List<CampaignEffectDto> execute(AccessInfoSearchForDaoConditionDto accessInfoSearchForDaoConditionDto) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("accessInfoSearchForDaoConditionDto", accessInfoSearchForDaoConditionDto);
        ArgumentCheckUtil.assertNotNull("shopSeq", accessInfoSearchForDaoConditionDto.shopSeq);

        return accessInfoDao.getSearchAccessInfoList(
                        accessInfoSearchForDaoConditionDto,
                        accessInfoSearchForDaoConditionDto.getPageInfo().getSelectOptions()
                                                    );
    }

}
