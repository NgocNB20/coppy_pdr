/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.access.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.access.AccessInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.access.AccessInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.access.AccessSessionCountGetForUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.logic.AbstractLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AccessSessionCountGetForUpdateLogicImpl 実装
 *
 * @author tm27400
 * @version $Revision: 1.2 $
 *
 */
@Component
public class AccessSessionCountGetForUpdateLogicImpl extends AbstractLogic
                implements AccessSessionCountGetForUpdateLogic {

    /** アクセス情報 DAO */
    private final AccessInfoDao accessInfoDao;

    @Autowired
    public AccessSessionCountGetForUpdateLogicImpl(AccessInfoDao accessInfoDao) {
        this.accessInfoDao = accessInfoDao;
    }

    /**
     * 実行する
     *
     * @param condition 検索条件として使用するエンティティ
     * @return 検索条件に合致する AccessInfoEntity
     */
    @Override
    public AccessInfoEntity getEntity(AccessInfoEntity condition) {

        // 該当するエンティティがあれば取得する
        return accessInfoDao.getEntityForUpdate(condition.getShopSeq(), condition.getAccessType().getValue(),
                                                condition.getAccessDate(), condition.getSiteType().getValue(),
                                                condition.getGoodsGroupSeq(), condition.getAccessUid(),
                                                condition.getCampaignCode()
                                               );
    }

}
