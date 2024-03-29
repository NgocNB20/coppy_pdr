// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.restockannounce.RestockAnnounceDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce.RestockAnnounceListDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 入荷お知らせ情報リスト削除ロジック<br/>
 *
 * @author hung.leviet
 * @version $Revision: 1.4 $
 */
@Component
public class RestockAnnounceListDeleteLogicImpl extends AbstractShopLogic implements RestockAnnounceListDeleteLogic {

    /**
     * 入荷お知らせDao
     */
    private final RestockAnnounceDao restockAnnounceDao;

    @Autowired
    public RestockAnnounceListDeleteLogicImpl(RestockAnnounceDao restockAnnounceDao) {
        this.restockAnnounceDao = restockAnnounceDao;
    }

    /**
     * 入荷お知らせ削除処理<br/>
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
        return restockAnnounceDao.deleteList(memberInfoSeq, goodsSeqs);
    }

    /**
     * 入荷お知らせ削除処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 削除件数
     */
    @Override
    public int execute(Integer memberInfoSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);

        // お気に入り削除の実行
        return restockAnnounceDao.deleteListByMemberInfoSeq(memberInfoSeq);
    }

    /**
     * 入荷お知らせ削除処理<br/>
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

        // 入荷お知らせ削除の実行
        return restockAnnounceDao.deleteListByGoodsCode(memberInfoSeq, goodsCodes);
    }

}
// 2023-renew No65 to here
