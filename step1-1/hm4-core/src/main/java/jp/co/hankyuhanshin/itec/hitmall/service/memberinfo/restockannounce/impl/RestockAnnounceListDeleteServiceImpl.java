// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.restockannounce.impl;

import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce.RestockAnnounceListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.restockannounce.RestockAnnounceListDeleteService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 入荷お知ら入り情報リスト削除サービス<br/>
 *
 * @author hung.leviet
 * @version $Revision: 1.4 $
 */
@Service
public class RestockAnnounceListDeleteServiceImpl extends AbstractShopService
                implements RestockAnnounceListDeleteService {

    /**
     * 入荷お知ら入り情報リスト削除ロジック
     */
    private final RestockAnnounceListDeleteLogic restockAnnounceListDeleteLogic;

    @Autowired
    public RestockAnnounceListDeleteServiceImpl(RestockAnnounceListDeleteLogic restockAnnounceListDeleteLogic) {
        this.restockAnnounceListDeleteLogic = restockAnnounceListDeleteLogic;
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
        return restockAnnounceListDeleteLogic.execute(memberInfoSeq, goodsSeqs);
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
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int execute(Integer memberInfoSeq, String[] goodsCodes) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotEmpty("goodsCodes", goodsCodes);
        return restockAnnounceListDeleteLogic.execute(memberInfoSeq, goodsCodes);
    }
}
// 2023-renew No65 to here