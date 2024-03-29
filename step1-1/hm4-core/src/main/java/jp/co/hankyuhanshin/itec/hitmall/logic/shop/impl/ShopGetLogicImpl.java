/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.ShopDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.ShopGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ショップ情報取得<br/>
 *
 * @author ozaki
 * @author sakai
 * @version $Revision: 1.4 $
 */
@Component
public class ShopGetLogicImpl extends AbstractShopLogic implements ShopGetLogic {

    /**
     * shopDao
     */
    private final ShopDao shopDao;

    @Autowired
    public ShopGetLogicImpl(ShopDao shopDao) {
        this.shopDao = shopDao;
    }

    /**
     * ショップ情報を取得する<br/>
     *
     * @param shopSeq    ショップSEQ
     * @param siteType   サイト種別
     * @param openStatus 公開状態
     * @return ショップエンティティ
     */
    @Override
    public ShopEntity execute(Integer shopSeq, HTypeSiteType siteType, HTypeOpenStatus openStatus) {

        // (1) パラメータチェック
        // ショップSEQが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);

        // (2) ショップ情報取得
        // ショップDaoのショップ情報取得処理を実行する。
        // DAO ShopDao
        // メソッド ショップエンティティ getEntity( （パラメータ）ショップSEQ, （パラメータ）サイト区分,
        // （パラメータ）公開状態)
        ShopEntity shopEntity = shopDao.getEntityBySiteTypeStatus(shopSeq, siteType, openStatus);

        // (3) 戻り値
        // 取得したショップエンティティを返す。
        return shopEntity;
    }

    /**
     * ショップ情報を取得する<br/>
     * <pre>
     * ショップSEQが null でないかをチェック
     * ショップDaoのショップ情報取得処理を実行する。
     * 取得したショップエンティティを返す。
     * </pre>
     *
     * @param shopSeq ショップSEQ
     * @return ショップエンティティ
     */
    @Override
    public ShopEntity executeUseCache(Integer shopSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);

        // ショップ情報取得
        ShopEntity shopEntity = shopDao.getEntity(shopSeq);

        // 戻り値
        return shopEntity;
    }

}
