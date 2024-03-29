/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsInformationIconDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * アイコン更新Logic
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Component
public class GoodsInformationIconDeleteLogicImpl extends AbstractShopLogic implements GoodsInformationIconDeleteLogic {

    /**
     * 商品インフォメーションアイコンDao
     */
    private final GoodsInformationIconDao goodsInformationIconDao;

    @Autowired
    public GoodsInformationIconDeleteLogicImpl(GoodsInformationIconDao goodsInformationIconDao) {
        this.goodsInformationIconDao = goodsInformationIconDao;
    }

    /**
     * アイコン更新処理
     *
     * @param goodsInformationIconEntity 商品インフォメーションアイコンエンティティ
     * @return 処理件数
     */
    @Override
    public int execute(GoodsInformationIconEntity goodsInformationIconEntity) {
        // パラメータチェック
        this.checkParam(goodsInformationIconEntity);

        // 物理削除
        return goodsInformationIconDao.delete(goodsInformationIconEntity);
    }

    /**
     * パラメータチェック
     *
     * @param goodsInformationIconEntity 商品インフォメーションアイコンエンティティ
     */
    protected void checkParam(GoodsInformationIconEntity goodsInformationIconEntity) {

        ArgumentCheckUtil.assertNotNull("goodsInformationIconEntity", goodsInformationIconEntity);
        ArgumentCheckUtil.assertGreaterThanZero("iconSeq", goodsInformationIconEntity.getIconSeq());
        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", goodsInformationIconEntity.getShopSeq());
        ArgumentCheckUtil.assertNotEmpty("iconName", goodsInformationIconEntity.getIconName());
    }

}
