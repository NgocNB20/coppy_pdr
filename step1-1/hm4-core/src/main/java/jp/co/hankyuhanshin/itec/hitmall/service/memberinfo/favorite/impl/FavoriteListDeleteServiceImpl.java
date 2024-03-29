/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite.impl;

import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite.FavoriteListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite.FavoriteListDeleteService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * お気に入り情報リスト削除サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.4 $
 */
@Service
public class FavoriteListDeleteServiceImpl extends AbstractShopService implements FavoriteListDeleteService {

    /**
     * お気に入り情報リスト削除ロジック
     */
    private final FavoriteListDeleteLogic favoriteListDeleteLogic;

    @Autowired
    public FavoriteListDeleteServiceImpl(FavoriteListDeleteLogic favoriteListDeleteLogic) {
        this.favoriteListDeleteLogic = favoriteListDeleteLogic;
    }

    /**
     * サービス実行<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsSeq      商品SEQ
     * @return 削除件数
     */
    @Override
    public int execute(Integer memberInfoSeq, Integer goodsSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("goodsSeq", goodsSeq);

        Integer[] goodsSeqs = new Integer[1];
        goodsSeqs[0] = goodsSeq;

        return this.execute(memberInfoSeq, goodsSeqs);
    }

    /**
     * サービス実行<br/>
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

        // ロジックの実行
        return favoriteListDeleteLogic.execute(memberInfoSeq, goodsSeqs);
    }

    /**
     * サービス実行<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsCode     商品コード
     * @return 削除件数
     */
    @Override
    public int execute(Integer memberInfoSeq, String goodsCode) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("goodsCode", goodsCode);

        String[] goodsCodes = new String[1];
        goodsCodes[0] = goodsCode;

        return this.execute(memberInfoSeq, goodsCodes);
    }

    /**
     * サービス実行<br/>
     *
     * @param goodsCodes 商品コード配列
     * @return 削除件数
     */
    @Override
    // PDR Migrate Customization from here
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    // PDR Migrate Customization to here
    public int execute(Integer memberInfoSeq, String[] goodsCodes) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotEmpty("goodsCodes", goodsCodes);
        return favoriteListDeleteLogic.execute(memberInfoSeq, goodsCodes);
    }
}
