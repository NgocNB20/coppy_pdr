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
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce.RestockAnnounceRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 入荷お知らせ情報登録ロジック<br/>
 *
 * @author Thang Doan (VJP)
 */
@Component
public class RestockAnnounceRegistLogicImpl extends AbstractShopLogic implements RestockAnnounceRegistLogic {

    /**
     * 入荷お知らせDao
     */
    private final RestockAnnounceDao restockAnnounceDao;

    @Autowired
    public RestockAnnounceRegistLogicImpl(RestockAnnounceDao restockAnnounceDao) {
        this.restockAnnounceDao = restockAnnounceDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param restockAnnounceEntity 入荷お知らエンティティ
     * @return 登録件数
     */
    @Override
    public int execute(RestockAnnounceEntity restockAnnounceEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("restockAnnounceEntity", restockAnnounceEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録日時設定
        restockAnnounceEntity.setRegistTime(dateUtility.getCurrentTime());
        // 更新日時設定
        restockAnnounceEntity.setUpdateTime(dateUtility.getCurrentTime());

        // 入荷お知らせ登録の実行
        return restockAnnounceDao.insertOrUpdate(restockAnnounceEntity);
    }

}
// 2023-renew No65 to here
