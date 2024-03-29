/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.cart.CartGoodsDeleteService;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * カート商品削除クラス<br/>
 * カートより指定した商品情報を削除する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
@Service
public class CartGoodsDeleteServiceImpl extends AbstractShopService implements CartGoodsDeleteService {

    /**
     * カート商品リスト削除ロジッククラス
     */
    private final CartGoodsListDeleteLogic cartGoodsListDeleteLogic;

    @Autowired
    public CartGoodsDeleteServiceImpl(CartGoodsListDeleteLogic cartGoodsListDeleteLogic) {
        this.cartGoodsListDeleteLogic = cartGoodsListDeleteLogic;
    }

    /**
     * カート商品削除<br/>
     * カートより指定した商品情報を削除する。<br/>
     *
     * @param cartGoodsSeqList カート商品情報SEQリスト
     * @param memberInfoSeq 会員SEQ
     * @param accessUid 端末識別情報
     */
    @Override
    public void execute(List<Integer> cartGoodsSeqList, Integer memberInfoSeq, String accessUid) {

        // (1) パラメータチェック
        // ・リスト件数チェック ： リスト件数が0件又はリスト情報がnullの場合
        // 処理を終了する
        if (cartGoodsSeqList == null || cartGoodsSeqList.size() == 0) {
            return;
        }

        // (2) 共通情報チェック
        // ・端末識別番号 ： null（or空文字）の場合 処理を終了する
        // ・会員SEQ ： null（or空文字）以外の場合 (3)の処理には会員SEQをパラメータに設定する
        // null（or空文字）の場合 (3)の処理には端末識別番号をパラメータに設定する
        AssertionUtil.assertNotEmpty("accessUid", accessUid);
        if (memberInfoSeq != null && memberInfoSeq.intValue() != 0) {
            accessUid = null;
        } else {
            memberInfoSeq = 0;
        }

        // (3) カート商品リスト削除処理実行
        // カート商品DBから指定のカート商品情報を削除する
        // ※『logic基本設計書（カート商品リスト削除）.xls』参照
        cartGoodsListDeleteLogic.execute(cartGoodsSeqList, accessUid, memberInfoSeq);

    }

}
