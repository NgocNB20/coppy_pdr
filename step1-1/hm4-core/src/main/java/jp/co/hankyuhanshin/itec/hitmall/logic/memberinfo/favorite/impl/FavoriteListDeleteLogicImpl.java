/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.favorite.FavoriteDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteListDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * お気に入り情報リスト削除ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.4 $
 */
@Component
public class FavoriteListDeleteLogicImpl extends AbstractShopLogic implements FavoriteListDeleteLogic {

    /**
     * お気に入りDao
     */
    private final FavoriteDao favoriteDao;

    @Autowired
    public FavoriteListDeleteLogicImpl(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    /**
     * お気に入り削除処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsSeqs     商品SEQ配列
     * @return 削除件数
     */
    @Override
    public int execute(Integer memberInfoSeq, Integer[] goodsSeqs) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotEmpty("goodsSeqList", goodsSeqs);

        // お気に入り削除の実行
        return favoriteDao.deleteList(memberInfoSeq, goodsSeqs);
    }

    /**
     * お気に入り削除処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 削除件数
     */
    @Override
    public int execute(Integer memberInfoSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);

        // お気に入り削除の実行
        return favoriteDao.deleteListByMemberInfoSeq(memberInfoSeq);
    }

    /**
     * お気に入り削除処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsCodes    商品コード
     * @return 削除件数
     */
    @Override
    public int execute(Integer memberInfoSeq, String[] goodsCodes) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotEmpty("goodsCode", goodsCodes);

        // お気に入り削除の実行
        return favoriteDao.deleteListByGoodsCode(memberInfoSeq, goodsCodes);
    }

}
