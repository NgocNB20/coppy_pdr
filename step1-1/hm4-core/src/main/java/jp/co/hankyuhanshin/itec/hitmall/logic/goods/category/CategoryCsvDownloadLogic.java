/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;

import java.util.stream.Stream;

/**
 * カテゴリ情報DTO取得<br/>
 *
 * @author kamei
 */
// 2023-renew categoryCSV from here
public interface CategoryCsvDownloadLogic {
    public Stream<CategoryCsvDto> execute(CategorySearchForDaoConditionDto categorySearchForDaoConditionDto);
}
// 2023-renew categoryCSV to here
