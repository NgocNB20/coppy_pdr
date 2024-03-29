/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDisplayDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDisplayGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * カテゴリ表示情報取得ロジック
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
@Component
public class CategoryDisplayGetLogicImpl extends AbstractShopLogic implements CategoryDisplayGetLogic {

    /**
     * カテゴリ表示情報DAO
     */
    private final CategoryDisplayDao categoryDisplayDao;

    @Autowired
    public CategoryDisplayGetLogicImpl(CategoryDisplayDao categoryDisplayDao) {
        this.categoryDisplayDao = categoryDisplayDao;
    }

    /**
     * カテゴリ表示情報を取得する。<br/>
     *
     * @param categorySeq カテゴリSEQ
     * @return カテゴリ表示情報エンティティ
     */
    @Override
    public CategoryDisplayEntity execute(Integer categorySeq) {

        // (1) パラメータチェック
        // カテゴリSEQが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("categorySeq", categorySeq);

        // (2) カテゴリ表示情報取得
        // カテゴリ表示Daoのカテゴリ表示エンティティ取得処理を実行する。
        // DAO CategoryDisplayDao
        // メソッド カテゴリ表示エンティティ getEntity( （パラメータ）カテゴリSEQ)
        CategoryDisplayEntity categoryDisplayEntity = categoryDisplayDao.getEntity(categorySeq);

        // (3) 戻り値
        // 取得したカテゴリ表示エンティティを返す。
        // （該当するカテゴリ表示情報がない場合は null を返す）
        return categoryDisplayEntity;
    }
}
