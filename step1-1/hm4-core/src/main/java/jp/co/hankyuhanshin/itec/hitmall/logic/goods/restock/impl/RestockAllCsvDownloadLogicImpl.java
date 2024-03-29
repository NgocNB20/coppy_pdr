/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock.ReStockDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockDownloadCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock.ReStockAllCsvDownloadLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * 入荷お知らせ商品CSV一括ダウンロードロジック<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class RestockAllCsvDownloadLogicImpl extends AbstractShopLogic implements ReStockAllCsvDownloadLogic {

    /**
     * 商品DAO
     */
    private final ReStockDao reStockDao;

    @Autowired
    public RestockAllCsvDownloadLogicImpl(ReStockDao reStockDao) {
        this.reStockDao = reStockDao;
    }

    @Override
    public Stream<ReStockDownloadCsvDto> execute(ReStockSearchForBackDaoConditionDto conditionDto) {
        return this.reStockDao.exportCsvByReStockSearchForBackDaoConditionDto(conditionDto);
    }

}
