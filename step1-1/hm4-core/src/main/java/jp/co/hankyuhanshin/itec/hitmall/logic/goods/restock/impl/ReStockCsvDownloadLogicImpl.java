/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock.ReStockDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockDownloadCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.ReStockCsvDownloadLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

/**
 * 入荷お知らせ商品CSV一括ダウンロードロジック<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class ReStockCsvDownloadLogicImpl extends AbstractShopLogic implements ReStockCsvDownloadLogic {

    /**
     * 商品DAO
     */
    private final ReStockDao reStockDao;

    @Autowired
    public ReStockCsvDownloadLogicImpl(ReStockDao reStockDao) {
        this.reStockDao = reStockDao;
    }

    /**
     *
     * @param keyList キー（商品SEQ+入荷状態+配信ID+配信状況) or （商品SEQ+入荷状態+配信ID+配信状況+会員SEQ)
     * @param memberFlg 会員SEQがキーにあるかを判定する true:あり、false:なし
     * @return 入荷お知らせ商品CSVダウンロードDto
     */
    @Override
    public Stream<ReStockDownloadCsvDto> execute(List<String> keyList, boolean memberFlg) {
        Stream<ReStockDownloadCsvDto> reStockDownloadCsvDto;
        if(memberFlg) {
            // キーに会員SEQ有り
            reStockDownloadCsvDto = this.reStockDao.exportCsvByReStockMembersList(keyList);
        }else{
            reStockDownloadCsvDto = this.reStockDao.exportCsvByReStockList(keyList);
        }
        return reStockDownloadCsvDto;
    }

}
