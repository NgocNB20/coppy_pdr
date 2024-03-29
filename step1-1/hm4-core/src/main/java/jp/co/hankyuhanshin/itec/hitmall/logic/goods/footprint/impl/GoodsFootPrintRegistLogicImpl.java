/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.footprint.FootprintDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.footprint.FootprintEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint.GoodsFootPrintRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * あしあと商品情報登録<br/>
 * あしあと商品情報を登録する。<br/>
 *
 * @author ozaki
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class GoodsFootPrintRegistLogicImpl extends AbstractShopLogic implements GoodsFootPrintRegistLogic {

    /**
     * あしあと商品DAO
     */
    private final FootprintDao footprintDao;

    @Autowired
    public GoodsFootPrintRegistLogicImpl(FootprintDao footprintDao) {
        this.footprintDao = footprintDao;
    }

    /**
     * あしあと商品情報登録<br/>
     * あしあと商品情報を登録する。<br/>
     *
     * @param footprintEntity あしあと情報
     * @return 登録・更新件数
     */
    @Override
    public int execute(FootprintEntity footprintEntity) {

        // (1) パラメータチェック
        // あしあと情報Dtoが null でないかをチェック
        // 端末識別情報が null でないかをチェック
        ArgumentCheckUtil.assertNotNull("footprintEntity", footprintEntity);

        // (2) あしあと情報取得
        // あしあと情報Daoのあしあと情報取得処理により、既に同一代表商品のあしあと情報があれば取得する。
        // DAO FootprintDao
        // メソッド あしあと情報 getEntity( ショップSEQ, 端末識別情報, 会員SEQ)
        FootprintEntity newFootprintEntity =
                        footprintDao.getEntity(footprintEntity.getAccessUid(), footprintEntity.getGoodsGroupSeq());

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // (4) あしあと情報の登録・更新
        int count = 0;
        if (newFootprintEntity != null) {
            // ・(3)であしあと商品エンティティを取得した場合
            // （(3)で取得した）あしあと商品エンティティ．更新日時 ＝ サーバ現在日時 をセット
            newFootprintEntity.setUpdateTime(dateUtility.getCurrentTime());

            // あしあとDaoのあしあと情報更新処理を実行する。
            // DAO FootprintDao
            // メソッド 更新した件数 update( （(3)で取得した）あしあと商品エンティティ)
            count = footprintDao.update(newFootprintEntity);
        } else {
            // ・(3)であしあと情報DTOを取得できなかった場合
            // （パラメータ）あしあと商品エンティティ．登録日時 ＝ サーバ現在日時 をセット
            // （パラメータ）あしあと商品エンティティ．更新日時 ＝ サーバ現在日時 をセット
            footprintEntity.setRegistTime(dateUtility.getCurrentTime());
            footprintEntity.setUpdateTime(dateUtility.getCurrentTime());

            // あしあと情報Daoのあしあと情報登録処理を実行する。
            // DAO FootprintDao
            // メソッド 登録した件数 insert( （パラメータ）あしあと商品エンティティ)
            count = footprintDao.insert(footprintEntity);
        }

        // (4) 戻り値
        // 更新した件数 または 登録した件数 を返す
        return count;
    }
}
