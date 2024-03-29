/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CartGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsListDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.exception.seasar.EmptyRuntimeException;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * カート商品リスト削除<br/>
 * カート商品SEQリストを元に、カート商品情報を削除します。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
@Component
public class CartGoodsListDeleteLogicImpl extends AbstractShopLogic implements CartGoodsListDeleteLogic {

    /**
     * カート商品情報DAO
     */
    private final CartGoodsDao cartGoodsDao;

    @Autowired
    public CartGoodsListDeleteLogicImpl(CartGoodsDao cartGoodsDao) {
        this.cartGoodsDao = cartGoodsDao;
    }

    /**
     * カート商品リスト削除<br/>
     * カート商品SEQリストを元に、カート商品情報を削除します。<br/>
     *
     * @param cartGoodsSeqList カート商品SEQリスト
     * @param accessUid        端末識別情報
     * @param memberInfoSeq    会員SEQ
     * @return 削除件数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int execute(List<Integer> cartGoodsSeqList, String accessUid, Integer memberInfoSeq) {

        // (1) パラメータチェック
        // カート商品SEQリストが null でないかをチェック
        // 会員SEQと端末識別番号がともにnullでないことをチェック
        // ・会員SEQがnullでない場合
        // 端末識別番号 = null を設定する
        AssertionUtil.assertNotNull("cartGoodsSeqList", cartGoodsSeqList);
        if (cartGoodsSeqList.size() == 0) {
            return 0;
        }
        if (memberInfoSeq == null && accessUid == null) {
            throw new EmptyRuntimeException("accessUid");
        }
        // if (memberInfoSeq != null) {
        // accessUid = null;
        // }
        if (memberInfoSeq != null && memberInfoSeq.intValue() != 0) {
            accessUid = null;
        } else {
            memberInfoSeq = 0;
        }

        // (2)カート内の指定商品を削除
        // カート商品Daoの複数削除を実行する。
        // DAO CartGoodsDao
        // メソッド 削除した数 delete( カート商品SEQリスト, 端末識別番号, 会員SEQ)
        int deleteCount = cartGoodsDao.deleteList(cartGoodsSeqList, accessUid, memberInfoSeq);

        // (2) 戻り値
        // 削除した数を返す。
        return deleteCount;
    }

}
