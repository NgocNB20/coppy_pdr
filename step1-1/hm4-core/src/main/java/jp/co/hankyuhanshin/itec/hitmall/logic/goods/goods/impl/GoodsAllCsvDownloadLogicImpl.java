/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsAllCsvDownloadLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * 商品検索CSV一括ダウンロードロジック<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class GoodsAllCsvDownloadLogicImpl extends AbstractShopLogic implements GoodsAllCsvDownloadLogic {

    /**
     * 商品DAO
     */
    private final GoodsDao goodsDao;

    @Autowired
    public GoodsAllCsvDownloadLogicImpl(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    @Override
    public Stream<GoodsCsvDto> execute(GoodsSearchForBackDaoConditionDto conditionDto) {
        return this.goodsDao.exportCsvByGoodsSearchForBackDaoConditionDto(conditionDto);
    }

}
