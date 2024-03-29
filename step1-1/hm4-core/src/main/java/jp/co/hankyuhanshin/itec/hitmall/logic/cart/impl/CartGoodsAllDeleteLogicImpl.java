/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CartGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsAllDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.seasar.EmptyRuntimeException;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カート全商品削除ロジック（今すぐお届けのみ）
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
@Component
public class CartGoodsAllDeleteLogicImpl extends AbstractShopLogic implements CartGoodsAllDeleteLogic {

    /**
     * カート商品DAO
     */
    private final CartGoodsDao cartGoodsDao;

    @Autowired
    public CartGoodsAllDeleteLogicImpl(CartGoodsDao cartGoodsDao) {
        this.cartGoodsDao = cartGoodsDao;
    }

    /**
     * カート全商品削除<br/>
     * （自動認証時）会員SEQ、もしくは（未認証時）端末識別情報　を元に、カートをクリアします。<br/>
     *
     * ※reserveFlag（取りおきフラグ）≠1（取りおき不可）が対象
     *   → セールde予約商品は振分商品は発生しないので、カート画面表示時の再投入は行わないため。
     *
     * @param shopSeq       ショップSEQ
     * @param memberInfoSeq 会員SEQ
     * @param accessUid     端末識別番号
     * @return 削除件数
     */
    @Override
    public int execute(Integer shopSeq, Integer memberInfoSeq, String accessUid) {

        // (1) パラメータチェック
        // 会員SEQと端末識別番号がともにnullでないことをチェック
        if (memberInfoSeq == null && StringUtil.isEmpty(accessUid)) {
            throw new EmptyRuntimeException("accessUid");
        }

        // ・会員SEQがnullでない場合
        // 端末識別番号 = null を設定する
        // if (memberInfoSeq != null) {
        // accessUid = null;
        // }
        if (memberInfoSeq != null && memberInfoSeq.intValue() != 0) {
            accessUid = null;
        } else {
            memberInfoSeq = 0;
        }

        // (2) カート商品Daoのカート商品複数削除処理を実行する。
        // DAO CartGoodsDao
        // メソッド 削除した数 deleteShopMemberCart( ショップSEQ, 端末識別情報, 会員SEQ)
        int deleteCount = cartGoodsDao.deleteShopMemberCart(shopSeq, accessUid, memberInfoSeq);

        // (3) 戻り値
        // 削除した数を返す。
        return deleteCount;
    }

}
