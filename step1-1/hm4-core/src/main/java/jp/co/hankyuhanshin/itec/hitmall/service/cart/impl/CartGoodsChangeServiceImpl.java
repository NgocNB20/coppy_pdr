/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsCountUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.cart.CartGoodsChangeService;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * カート情報変更クラス<br/>
 * カート情報を更新する<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
@Service
public class CartGoodsChangeServiceImpl extends AbstractShopService implements CartGoodsChangeService {

    /**
     * カート商品数量変更ロジッククラス
     */
    private final CartGoodsCountUpdateLogic cartGoodsCountUpdateLogic;

    @Autowired
    public CartGoodsChangeServiceImpl(CartGoodsCountUpdateLogic cartGoodsCountUpdateLogic) {
        this.cartGoodsCountUpdateLogic = cartGoodsCountUpdateLogic;
    }

    /**
     * メソッド概要<br/>
     * メソッドの説明・概要<br/>
     *
     * @param cartGoodsDtoList 変更するカート商品情報リスト
     * @param accessUid        端末識別番号
     * @param memberInfoSeq    会員SEQ
     */
    @Override
    public void execute(List<CartGoodsDto> cartGoodsDtoList, String accessUid, Integer memberInfoSeq) {

        // (1) パラメータチェック
        // ・リスト件数チェック ： リスト件数が0件又はリスト情報がnullの場合
        // 処理を終了する
        if (cartGoodsDtoList == null || cartGoodsDtoList.size() == 0) {
            return;
        }

        // (2) 共通情報チェック
        // ・端末識別番号：null（or空文字）の場合 エラーとして処理を終了する
        // ・会員SEQ ： null（or空文字）以外の場合 (3)の処理には会員SEQをパラメータに設定する
        // null（or空文字）の場合 (3)の処理には端末識別番号をパラメータに設定する
        AssertionUtil.assertNotEmpty("accessUid", accessUid);
        if (memberInfoSeq != null && memberInfoSeq.intValue() != 0) {
            accessUid = null;
        } else {
            memberInfoSeq = 0;
        }

        // (3) カート商品数量更新処理
        // ※『logic基本設計書（カート商品数量変更）.xls』参照
        // Logic CartGoodsCountUpdateLogic
        // パラメータ 端末識別番号
        // 会員SEQ
        // カート商品DTOリスト
        // 戻り値 処理件数
        cartGoodsCountUpdateLogic.execute(cartGoodsDtoList, memberInfoSeq, accessUid);
    }

}
