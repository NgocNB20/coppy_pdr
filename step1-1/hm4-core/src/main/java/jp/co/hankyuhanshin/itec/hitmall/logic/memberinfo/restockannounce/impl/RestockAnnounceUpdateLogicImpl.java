// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.restockannounce.RestockAnnounceDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.restockannounce.RestockAnnounceEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce.RestockAnnounceUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 入荷お知らせ更新ロジック<br/>
 *
 * @author Thang Doan (VJP)
 */
@Component
public class RestockAnnounceUpdateLogicImpl extends AbstractShopLogic implements RestockAnnounceUpdateLogic {

    /**
     * 入荷お知らDao<br/>
     */
    private final RestockAnnounceDao restockAnnounceDao;

    @Autowired
    public RestockAnnounceUpdateLogicImpl(RestockAnnounceDao restockAnnounceDao) {
        this.restockAnnounceDao = restockAnnounceDao;
    }

    /**
     * 入荷お知ら更新処理<br/>
     *
     * @param restockAnnounceEntity 入荷お知らエンティティ
     * @return 更新処理
     */
    @Override
    public int execute(RestockAnnounceEntity restockAnnounceEntity) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("restockAnnounceEntity", restockAnnounceEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 更新日時設定
        restockAnnounceEntity.setUpdateTime(dateUtility.getCurrentTime());

        // 入荷お知ら商品更新
        return restockAnnounceDao.update(restockAnnounceEntity);
    }

}
// 2023-renew No65 from here
