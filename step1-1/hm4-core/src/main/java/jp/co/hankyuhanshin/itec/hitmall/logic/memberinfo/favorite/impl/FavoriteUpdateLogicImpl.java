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
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * お気に入り更新ロジック<br/>
 *
 * @author natume
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class FavoriteUpdateLogicImpl extends AbstractShopLogic implements FavoriteUpdateLogic {

    /**
     * お気に入りDao<br/>
     */
    private final FavoriteDao favoriteDao;

    @Autowired
    public FavoriteUpdateLogicImpl(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    /**
     * お気に入り更新処理<br/>
     *
     * @param favoriteEntity お気に入りエンティティ
     * @return 更新処理
     */
    @Override
    public int execute(FavoriteEntity favoriteEntity) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("favoriteEntity", favoriteEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 更新日時設定
        favoriteEntity.setUpdateTime(dateUtility.getCurrentTime());

        // お気に入り商品更新
        return favoriteDao.update(favoriteEntity);
    }

}
