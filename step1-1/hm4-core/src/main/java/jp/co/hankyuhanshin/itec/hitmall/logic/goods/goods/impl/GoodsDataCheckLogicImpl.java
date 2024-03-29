/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDataCheckLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品データチェック<br/>
 *
 * @author hirata
 */
@Component
public class GoodsDataCheckLogicImpl extends AbstractShopLogic implements GoodsDataCheckLogic {

    /**
     * 商品DAO
     */
    private final GoodsDao goodsDao;

    @Autowired
    public GoodsDataCheckLogicImpl(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    /**
     * 商品リスト取得<br/>
     * 商品情報リストを取得する。<br/>
     * CSV用にエラーパラメータの末尾に商品グループコード・商品コードを付加する。<br/>
     *
     * @param goodsEntityList 商品エンティティリスト
     * @param shopSeq         ショップSEQ
     * @param goodsGroupCode  商品グループコード
     */
    @Override
    public void execute(List<GoodsEntity> goodsEntityList, Integer shopSeq, String goodsGroupCode) {

        // (1) パラメータチェック
        // 商品エンティティリストが null または 0件 でないかをチェック
        ArgumentCheckUtil.assertNotEmpty("goodsEntityList", goodsEntityList);

        // (2) 商品チェック
        checkGoods(goodsEntityList, shopSeq, goodsGroupCode);

        // (3) エラーメッセージがあれば投げる
        if (hasErrorList()) {
            throwMessage();
        }
    }

    /**
     * 商品をチェック<br/>
     * <pre>
     * ・商品コード重複チェック
     * ・入荷お知らせメール設定変更チェック
     * </pre>
     *
     * @param goodsEntityList 商品エンティティリスト
     * @param shopSeq         ショップSEQ
     * @param goodsGroupCode  商品グループコード
     * @param customParams    案件用引数
     */
    protected void checkGoods(List<GoodsEntity> goodsEntityList,
                              Integer shopSeq,
                              String goodsGroupCode,
                              Object... customParams) {

        // 商品エンティティリストの件数分、以下の処理を繰り返す。
        for (GoodsEntity goodsEntity : goodsEntityList) {
            GoodsEntity tmpGoodsEntity = goodsDao.getGoodsByCode(shopSeq, goodsEntity.getGoodsCode());

            // (2)-1 商品コード重複チェック
            checkGoodscodeRepetition(goodsGroupCode, goodsEntity, tmpGoodsEntity);
        }
    }

    /**
     * 商品コード重複チェック<br/>
     * <pre>
     * </pre>
     *
     * @param goodsGroupCode 商品グループコード
     * @param goodsEntity    商品エンティティ
     * @param tmpGoodsEntity 変更前商品エンティティ
     * @param customParams   案件用引数
     */
    protected void checkGoodscodeRepetition(String goodsGroupCode,
                                            GoodsEntity goodsEntity,
                                            GoodsEntity tmpGoodsEntity,
                                            Object... customParams) {

        if (tmpGoodsEntity == null || tmpGoodsEntity.getGoodsSeq().equals(goodsEntity.getGoodsSeq())) {
            return;
        }

        // 商品コード重複エラー（エラーは投げない。）
        addErrorMessage(
                        MSGCD_GOODSCODE_REPETITION_FAIL,
                        new Object[] {goodsEntity.getGoodsCode(), goodsGroupCode, goodsEntity.getGoodsCode()}
                       );
    }
}
