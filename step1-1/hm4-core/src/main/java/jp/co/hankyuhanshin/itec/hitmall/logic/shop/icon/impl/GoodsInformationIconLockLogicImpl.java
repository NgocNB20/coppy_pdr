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
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon.GoodsInformationIconLockLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品インフォメーションアイコン 行ロック
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Component
public class GoodsInformationIconLockLogicImpl extends AbstractShopLogic implements GoodsInformationIconLockLogic {

    /**
     * 商品インフォメーションアイコンDao
     */
    private final GoodsInformationIconDao goodsInformationIconDao;

    @Autowired
    public GoodsInformationIconLockLogicImpl(GoodsInformationIconDao goodsInformationIconDao) {
        this.goodsInformationIconDao = goodsInformationIconDao;
    }

    /**
     * 行ロック実行
     *
     * @param iconSeq アイコンSEQ
     */
    @Override
    public void execute(Integer iconSeq) {
        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("iconSeq", iconSeq);

        GoodsInformationIconEntity entity = goodsInformationIconDao.getGoodsInformationIconBySeqForUpdate(iconSeq);

        // ロック失敗
        if (entity == null) {
            throwMessage(MSGCD_GOODSINFORMATIONICON_LOCK_FAIL);
        }

        // ロック成功時はなにもしない
        return;
    }

}
