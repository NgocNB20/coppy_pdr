/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCartGoodsMergeFlag;
import jp.co.hankyuhanshin.itec.hitmall.dao.cart.CartGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cart.CartGoodsMergeLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カート商品マージ<br/>
 * ゲスト時にカート投入した商品を、会員カートへ移行させる処理。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.4 $
 */
@Component
public class CartGoodsMergeLogicImpl extends AbstractShopLogic implements CartGoodsMergeLogic {

    /**
     * カート商品Dao<br/>
     */
    private final CartGoodsDao cartGoodsDao;

    @Autowired
    public CartGoodsMergeLogicImpl(CartGoodsDao cartGoodsDao) {
        this.cartGoodsDao = cartGoodsDao;
    }

    /**
     * カート商品マージ<br/>
     * ゲスト時にカート投入した商品を、会員カートへ移行させる処理。<br/>
     *
     * @param shopSeq             ショップSEQ(null可)
     * @param memberInfoSeq       会員SEQ(0許可)
     * @param accessUid           端末識別番号
     * @param changeMemberInfoSeq 変更後会員SEQ
     * @return 移行したカート商品件数
     */
    @Override
    public int execute(Integer shopSeq, Integer memberInfoSeq, String accessUid, Integer changeMemberInfoSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotNull("accessUid", accessUid);
        ArgumentCheckUtil.assertNotNull("changeMemberInfoSeq", changeMemberInfoSeq);

        int cnt = 0;
        // ①カート登録処理更新
        // 会員の既存カート情報に、ゲストのカート情報を追加する
        cnt = cartGoodsDao.updateMemberInfo(shopSeq, accessUid, memberInfoSeq, changeMemberInfoSeq);

        // カート内同一商品マージを実施する・しないの設定値を取得
        String cartGoodsMergeFlag = PropertiesUtil.getSystemPropertiesValue(CART_GOODS_MERGE);

        // ②カート内の同一商品を削除
        // ※退会処理の場合はカートマージ処理は不要(退会時：changeMemberInfoSeq=0)
        //
        // 会員で保持しているカート情報と、ゲストで追加したカート情報で
        // 同一の商品が含まれる場合、この時点でDBに商品情報が重複して存在することとなる
        // その場合、ゲストで追加した商品を正とし、会員で保持していた商品を削除する
        if (HTypeCartGoodsMergeFlag.CART_MERGE.getValue().equals(cartGoodsMergeFlag)) {
            if (changeMemberInfoSeq != 0) {
                cartGoodsDao.deleteSameOldCartGoods(shopSeq, changeMemberInfoSeq);
            }
        }

        // 更新結果を返す
        return cnt;
    }

}
