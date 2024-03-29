/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;

import java.util.stream.Stream;

/**
 * 商品検索CSV一括ダウンロードロジックインターフェース<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public interface GoodsAllCsvDownloadLogic {
    Stream<GoodsCsvDto> execute(GoodsSearchForBackDaoConditionDto conditionDto);
}
