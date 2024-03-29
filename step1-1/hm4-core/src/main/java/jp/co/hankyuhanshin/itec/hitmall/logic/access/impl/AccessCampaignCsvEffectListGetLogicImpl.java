/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.access.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.access.AccessInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.AccessInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.CampaignCsvEffectDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.access.AccessCampaignCsvEffectListGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * 広告効果CSV出力<br/>
 *
 * @author kimura
 * @author Kaneko (itec) 2012/08/10 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 *
 */
@Component
public class AccessCampaignCsvEffectListGetLogicImpl implements AccessCampaignCsvEffectListGetLogic {

    /** アクセス情報DAO */
    private final AccessInfoDao accessInfoDao;

    @Autowired
    public AccessCampaignCsvEffectListGetLogicImpl(AccessInfoDao accessInfoDao) {
        this.accessInfoDao = accessInfoDao;
    }

    /**
     * データを出力<br/>
     *
     * @param
     * @param
     * @return データ出力件数
     */
    @Override
    public Stream<CampaignCsvEffectDto> execute(AccessInfoSearchForDaoConditionDto accessInfoSearchForDaoConditionDto) {
        return accessInfoDao.exportCsvSearchAccessInfoList(accessInfoSearchForDaoConditionDto);
    }

}
