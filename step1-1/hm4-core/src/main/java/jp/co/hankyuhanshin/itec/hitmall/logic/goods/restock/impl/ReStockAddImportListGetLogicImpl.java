/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock.ReStockDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockAddImportListDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.ReStockAddImportListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 入荷お知らせメールアドレス帳登録リスト取得ロジック
 *
 * @author st75001
 * @version $Revision: 1.3 $
 */
@Component
public class ReStockAddImportListGetLogicImpl extends AbstractShopLogic implements ReStockAddImportListGetLogic {

    /**
     * 入荷お知らせ商品DAO
     */
    private final ReStockDao reStockDao;

    @Autowired
    public ReStockAddImportListGetLogicImpl(ReStockDao reStockDao) {
        this.reStockDao = reStockDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param memberFlg 会員SEQがキーにあるかを判定する true:あり、false:なし
     * @param keyList 検索結果選択キー
     * @return 入荷お知らせメールアドレス帳登録Dtoリスト
     */
    @Override
    public List<ReStockAddImportListDto> execute(boolean memberFlg,List<String> keyList) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("keyList", keyList);

        // (2)商品検索結果リスト取得処理
        List<ReStockAddImportListDto> reStockAddImportListDto;

        if(memberFlg) {
            // キーに会員SEQ有り
            reStockAddImportListDto = reStockDao.getReStockAddImportMembersList(keyList);
        }else{
            reStockAddImportListDto = reStockDao.getReStockAddImportList(keyList);
        }

        return reStockAddImportListDto;
    }
}
