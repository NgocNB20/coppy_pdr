/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.saleannounce.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock.ReStockDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.saleannounce.SaleAnnounceDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockAddImportListDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.saleannounce.SaleAnnounceAddImportListDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.saleannounce.SaleAnnounceMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.ReStockAddImportListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.saleannounce.SaleAddImportListGetLogic;
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
public class SaleAddImportListGetLogicImpl extends AbstractShopLogic implements SaleAddImportListGetLogic {

    /**
     * セールお知らせ商品DAO
     */
    private final SaleAnnounceDao saleAnnounceDao;

    @Autowired
    public SaleAddImportListGetLogicImpl(SaleAnnounceDao saleAnnounceDao) {
        this.saleAnnounceDao = saleAnnounceDao;
    }

    /**
     * 実行メソッド<br/>
     * @param keyList 商品SEQと会員SEQをまとめたキー
     * @return セールお知らせメールアドレス帳登録Dtoリスト
     */
    @Override
    public List<SaleAnnounceAddImportListDto> execute(List<String> keyList) {

        // (1)パラメータチェック
        ArgumentCheckUtil.assertNotNull("keyList", keyList);

        // (2)商品検索結果リスト取得処理
        List<SaleAnnounceAddImportListDto> saleAnnounceAddImportListDtos;

        return saleAnnounceAddImportListDtos = saleAnnounceDao.getSaleAddImportList(keyList);
    }
}
