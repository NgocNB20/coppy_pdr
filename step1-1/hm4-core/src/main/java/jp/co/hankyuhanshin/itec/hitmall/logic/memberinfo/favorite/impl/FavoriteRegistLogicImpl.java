/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.favorite.FavoriteDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * お気に入り情報登録ロジック<br/>
 *
 * @author ueshima
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class FavoriteRegistLogicImpl extends AbstractShopLogic implements FavoriteRegistLogic {

    /**
     * お気に入りDao
     */
    private final FavoriteDao favoriteDao;

    @Autowired
    public FavoriteRegistLogicImpl(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param favoriteEntity お気に入りエンティティ
     * @return 登録件数
     */
    @Override
    public int execute(FavoriteEntity favoriteEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("favoriteEntity", favoriteEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録日時・更新日時の設定
        Timestamp currentTime = dateUtility.getCurrentTime();
        favoriteEntity.setRegistTime(currentTime);
        favoriteEntity.setUpdateTime(currentTime);

        // お気に入り登録の実行
        return favoriteDao.insert(favoriteEntity);
    }

}
