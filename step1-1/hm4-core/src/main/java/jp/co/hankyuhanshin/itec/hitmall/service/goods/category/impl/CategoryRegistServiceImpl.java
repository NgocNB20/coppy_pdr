/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDisplayRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetMaxOrderdisplayLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.NewCategorySeqGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryRegistService;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * カテゴリ登録<br/>
 *
 * @author kimura
 * @version $Revision: 1.10 $
 */
@Service
public class CategoryRegistServiceImpl extends AbstractShopService implements CategoryRegistService {

    /**
     * カテゴリSEQ採番
     */
    private final NewCategorySeqGetLogic newCategorySeqGetLogic;

    /**
     * カテゴリ情報取得
     */
    private final CategoryGetLogic categoryGetLogic;

    /**
     * カテゴリ情報取得
     */
    private final CategoryGetMaxOrderdisplayLogic categoryGetMaxOrderdisplayLogic;

    /**
     * カテゴリ登録
     */
    private final CategoryRegistLogic categoryRegistLogic;

    /**
     * カテゴリ表示登録
     */
    private final CategoryDisplayRegistLogic categoryDisplayRegistLogic;

    @Autowired
    public CategoryRegistServiceImpl(NewCategorySeqGetLogic newCategorySeqGetLogic,
                                     CategoryGetLogic categoryGetLogic,
                                     CategoryGetMaxOrderdisplayLogic categoryGetMaxOrderdisplayLogic,
                                     CategoryRegistLogic categoryRegistLogic,
                                     CategoryDisplayRegistLogic categoryDisplayRegistLogic) {

        this.newCategorySeqGetLogic = newCategorySeqGetLogic;
        this.categoryGetLogic = categoryGetLogic;
        this.categoryGetMaxOrderdisplayLogic = categoryGetMaxOrderdisplayLogic;
        this.categoryRegistLogic = categoryRegistLogic;
        this.categoryDisplayRegistLogic = categoryDisplayRegistLogic;
    }

    /**
     * カテゴリの登録<br/>
     *
     * @param categoryDto      カテゴリ情報DTO
     * @param parentCategoryId カテゴリID
     * @return 件数
     */
    @Override
    public int execute(CategoryDto categoryDto, String parentCategoryId) {

        int size = 0;
        Integer categorySeq = newCategorySeqGetLogic.execute();
        Integer shopSeq = 1001;
        AssertionUtil.assertNotNull("categoryDto", categoryDto);
        AssertionUtil.assertNotNull("shopSeq", shopSeq);
        AssertionUtil.assertNotNull("parentCategoryId", parentCategoryId);

        // 親カテゴリの取得
        CategoryEntity categoryEntity = categoryGetLogic.execute(shopSeq, parentCategoryId);
        if (categoryEntity == null) {
            throwMessage("SGC001105");
        }
        // レベルの取得
        int currentLevel = categoryEntity.getCategoryLevel() + 1;

        // SEQパス取得
        String currentCategorySeqPath = categoryEntity.getCategorySeqPath() + categorySeq;

        // 並び順の取得
        int currentOrderDisplay = categoryGetMaxOrderdisplayLogic.execute(shopSeq, categoryEntity.getCategorySeqPath(),
                                                                          currentLevel
                                                                         );

        // 並び順からカテゴリパスを生成
        String currentCategoryPath =
                        categoryEntity.getCategoryPath() + "/" + String.format("%0" + 3 + "d", currentOrderDisplay);

        // カテゴリ情報設定
        categoryDto.getCategoryEntity().setShopSeq(shopSeq);
        categoryDto.getCategoryEntity().setCategorySeq(categorySeq);
        categoryDto.getCategoryEntity().setCategorySeqPath(currentCategorySeqPath);
        categoryDto.getCategoryEntity().setRootCategorySeq(categoryEntity.getRootCategorySeq());
        categoryDto.getCategoryEntity().setCategoryLevel(currentLevel);
        categoryDto.getCategoryEntity().setOrderDisplay(currentOrderDisplay);
        categoryDto.getCategoryEntity().setCategoryPath(currentCategoryPath);

        // カテゴリ表示情報設定
        categoryDto.getCategoryDisplayEntity().setCategorySeq(categorySeq);

        size = categoryRegistLogic.execute(categoryDto.getCategoryEntity());

        if (size != 1) {
            return size;
        }

        size = categoryDisplayRegistLogic.execute(categoryDto.getCategoryDisplayEntity());

        if (size != 1) {
            return size;
        }

        return size;
    }

}
