/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.access.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.access.AccessSearchKeywordDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.access.AccessSearchKeywordEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.access.AccessSearchKeywordRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * アクセス検索キーワード情報登録ロジック<br/>
 *
 * @author ozaki
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class AccessSearchKeywordRegistLogicImpl extends AbstractShopLogic implements AccessSearchKeywordRegistLogic {

    /**
     * アクセス情報DAO
     */
    private final AccessSearchKeywordDao accessSearchKeywordDao;

    /**
     * 日付関連ユーティリティ
     */
    private final DateUtility dateUtility;

    @Autowired
    public AccessSearchKeywordRegistLogicImpl(AccessSearchKeywordDao accessSearchKeywordDao, DateUtility dateUtility) {
        this.accessSearchKeywordDao = accessSearchKeywordDao;
        this.dateUtility = dateUtility;
    }

    /**
     * アクセス検索キーワード情報登録メソッド<br/>
     *
     * @param accessSearchKeywordEntity アクセス検索キーワード情報エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(AccessSearchKeywordEntity accessSearchKeywordEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("accessSearchKeywordEntity", accessSearchKeywordEntity);

        Timestamp currentTime = dateUtility.getCurrentTime();

        // アクセス情報登録
        accessSearchKeywordEntity.setAccessTime(currentTime);
        accessSearchKeywordEntity.setRegistTime(currentTime);
        accessSearchKeywordEntity.setUpdateTime(currentTime);
        return accessSearchKeywordDao.insert(accessSearchKeywordEntity);
    }

}
