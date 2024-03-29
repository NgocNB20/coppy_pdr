/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.footprint.impl;

import jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint.GoodsFootPrintClearLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.footprint.GoodsFootClearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * あしあと商品クリアクラス<br/>
 * あしあと商品情報を削除します。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
@Service
public class GoodsFootClearServiceImpl extends AbstractShopService implements GoodsFootClearService {

    /**
     * あしあと情報クリアロジッククラス
     */
    private final GoodsFootPrintClearLogic goodsFootPrintClearLogic;

    @Autowired
    public GoodsFootClearServiceImpl(GoodsFootPrintClearLogic goodsFootPrintClearLogic) {
        this.goodsFootPrintClearLogic = goodsFootPrintClearLogic;
    }

    /**
     * あしあと商品クリア<br/>
     * あしあと商品情報を削除します。<br/>
     */
    @Override
    public void execute(String accessUid) {

        // ・端末識別情報 ： null（or空文字）の場合 処理を終了する
        // (2)の処理には会員SEQと端末識別情報をパラメータに設定する
        // null（or空文字）の場合
        // (2)の処理には端末識別情報をパラメータに設定する

        if (accessUid == null || accessUid.equals("")) {
            return;
        }

        // (2) あしあとクリア処理実行
        goodsFootPrintClearLogic.execute(1001, accessUid);

    }

}
