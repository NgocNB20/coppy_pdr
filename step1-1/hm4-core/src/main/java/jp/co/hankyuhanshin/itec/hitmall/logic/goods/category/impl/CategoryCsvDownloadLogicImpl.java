/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryCsvDownloadLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * カテゴリ情報DTO取得<br/>
 *
 * @author kamei
 */
@Component
// 2023-renew categoryCSV from here
public class CategoryCsvDownloadLogicImpl extends AbstractShopLogic implements CategoryCsvDownloadLogic {

    /**
     * カテゴリDao<br/>
     */
    protected CategoryDao categoryDao;

    /**
     * コンストラクタ
     *
     * @param categoryDao
     */
    @Autowired
    public CategoryCsvDownloadLogicImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * カテゴリデータ出力<br/>
     * @param categorySearchForDaoConditionDto
     * @return CSV出力件数
     */
    @Override
    public Stream<CategoryCsvDto> execute(CategorySearchForDaoConditionDto categorySearchForDaoConditionDto) {

        return categoryDao.exportCsvByCategorySearchForDaoConditionDto(categorySearchForDaoConditionDto);
    }
}
// 2023-renew categoryCSV to here
