/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.footprint.FootprintDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint.GoodsFootPrintClearLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * あしあとクリア<br/>
 * あしあと商品リストを削除する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.3 $
 */
@Component
public class GoodsFootPrintClearLogicImpl extends AbstractShopLogic implements GoodsFootPrintClearLogic {

    /**
     * あしあと情報DAO
     */
    private final FootprintDao footprintDao;

    @Autowired
    public GoodsFootPrintClearLogicImpl(FootprintDao footprintDao) {
        this.footprintDao = footprintDao;
    }

    /**
     * あしあとクリア<br/>
     * あしあと商品リストを削除する。<br/>
     *
     * @param shopSeq   ショップSEQ
     * @param accessUid 端末識別ID
     * @return 削除件数
     */
    @Override
    public int execute(Integer shopSeq, String accessUid) {

        // (1) パラメータチェック
        // 端末識別情報が null でないかをチェック
        AssertionUtil.assertNotNull("accessUid", accessUid);

        // (3) あしあとクリア
        // あしあと情報Daoのあしあとクリア処理を実行する。
        int deleteCount = footprintDao.deleteFootprint(shopSeq, accessUid);

        // (4) 戻り値
        // 削除した件数を返す。
        return deleteCount;
    }
}
