/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea.FreeAreaGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * フリーエリア情報取得サービス<br/>
 *
 * @author natume
 * @version $Revision: 1.1 $
 */
@Service
public class FreeAreaGetServiceImpl extends AbstractShopService implements FreeAreaGetService {
    /**
     * フリーエリア情報取得ロジック<br/>
     */
    private final FreeAreaGetLogic freeAreaGetLogic;

    @Autowired
    public FreeAreaGetServiceImpl(FreeAreaGetLogic freeAreaGetLogic) {
        this.freeAreaGetLogic = freeAreaGetLogic;
    }

    /**
     * フリーエリア情報取得処理<br/>
     * 注意：会員別制限が有効する時、このメソッドの利用を避けてください。キャッシュのリフレッシュに問題あるから。
     * 代わりに、会員SEQが引数にあるメソッドを使ってください。
     *
     * @param freeAreaKey フリーエリアキー
     * @return フリーエリアエンティティ
     */
    @Override
    public FreeAreaEntity execute(String freeAreaKey) {
        return execute(freeAreaKey, null);
    }

    /**
     * フリーエリア情報取得処理<br/>
     *
     * @param freeAreaKey   フリーエリアキー
     * @param memberInfoSeq 会員SEQ
     * @return フリーエリアエンティティ
     */
    @Override
    public FreeAreaEntity execute(String freeAreaKey, Integer memberInfoSeq) {

        // パラメータチェック
        Integer shopSeq = 1001;
        ArgumentCheckUtil.assertNotEmpty("freeAreaKey", freeAreaKey);

        // フリーエリア情報取得 取得エラーなし
        return freeAreaGetLogic.execute(shopSeq, freeAreaKey, memberInfoSeq);
    }

    /**
     * プレビュー時フリーエリア情報取得処理<br/>
     *
     * @param freeAreaSeq フリーエリアSEQ
     * @return フリーエリアエンティティ
     */
    @Override
    public FreeAreaEntity execute(Integer freeAreaSeq) {

        // パラメータチェック
        Integer shopSeq = 1001;

        // フリーエリア情報取得 取得エラーなし
        return freeAreaGetLogic.execute(shopSeq, freeAreaSeq);
    }
}
