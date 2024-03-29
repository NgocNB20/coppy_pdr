/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDisplayRemoveLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetSortOrderdisplayCategoryIdLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGoodsRemoveLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryModifyLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryRemoveLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryRemoveService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryTreeNodeGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CategoryUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.FileOperationUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * カテゴリ削除<br/>
 *
 * @author kimura
 * @author Kaneko (itec) 2012/08/16 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class CategoryRemoveServiceImpl extends AbstractShopService implements CategoryRemoveService {

    /**
     * カテゴリツリー構造取得
     */
    private final CategoryTreeNodeGetService categoryTreeNodeGetService;

    /**
     * カテゴリ削除
     */
    private final CategoryRemoveLogic categoryRemoveLogic;

    /**
     * カテゴリ表示削除
     */
    private final CategoryDisplayRemoveLogic categoryDisplayRemoveLogic;

    /**
     * カテゴリ登録商品削除
     */
    private final CategoryGoodsRemoveLogic categoryGoodsRemoveLogic;

    /**
     * カテゴリ並び順整備対象カテゴリID取得
     */
    private final CategoryGetSortOrderdisplayCategoryIdLogic categoryGetSortOrderdisplayCategoryIdLogic;

    /**
     * カテゴリ修正
     */
    private final CategoryModifyLogic categoryModifyLogic;

    /**
     * 削除したカテゴリーIDリスト
     */
    private final List<CategoryDisplayEntity> removeCategoryDisplayList = new ArrayList<>();

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryRemoveServiceImpl.class);

    @Autowired
    public CategoryRemoveServiceImpl(CategoryTreeNodeGetService categoryTreeNodeGetService,
                                     CategoryRemoveLogic categoryRemoveLogic,
                                     CategoryDisplayRemoveLogic categoryDisplayRemoveLogic,
                                     CategoryGoodsRemoveLogic categoryGoodsRemoveLogic,
                                     CategoryGetSortOrderdisplayCategoryIdLogic categoryGetSortOrderdisplayCategoryIdLogic,
                                     CategoryModifyLogic categoryModifyLogic) {

        this.categoryTreeNodeGetService = categoryTreeNodeGetService;
        this.categoryRemoveLogic = categoryRemoveLogic;
        this.categoryDisplayRemoveLogic = categoryDisplayRemoveLogic;
        this.categoryGoodsRemoveLogic = categoryGoodsRemoveLogic;
        this.categoryGetSortOrderdisplayCategoryIdLogic = categoryGetSortOrderdisplayCategoryIdLogic;
        this.categoryModifyLogic = categoryModifyLogic;
    }

    /**
     * カテゴリの削除<br/>
     *
     * @param categoryId カテゴリID
     * @return 件数
     */
    @Override
    public int execute(String categoryId, HTypeSiteType siteType) {

        Integer shopSeq = 1001;
        AssertionUtil.assertNotNull("categoryId", categoryId);
        AssertionUtil.assertNotNull("shopSeq", shopSeq);
        CategorySearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
        conditionDto.setOrderField("categorypath");
        conditionDto.setOrderAsc(true);

        conditionDto.setCategoryId(categoryId);
        CategoryDto categoryDto = categoryTreeNodeGetService.execute(conditionDto, null, siteType);
        if (categoryDto == null) {
            // 既に削除されている場合
            return removeCategoryDisplayList.size();
        }

        // カレントを含む子ツリーを削除
        nodeRemove(categoryDto);

        // 以下より、カテゴリ並び順整備対象カテゴリID取得
        String categorySeqPath = categoryDto.getCategoryEntity().getCategorySeqPath();
        String parentCategorySeqPath = categorySeqPath.substring(0, categorySeqPath.length() - 8);
        Integer ccategoryLevel = categoryDto.getCategoryEntity().getCategoryLevel();
        Integer orderdisplay = categoryDto.getCategoryEntity().getOrderDisplay();
        // カテゴリ並び順整備対象カテゴリID取得
        List<String> list = categoryGetSortOrderdisplayCategoryIdLogic.execute(shopSeq, parentCategorySeqPath,
                                                                               ccategoryLevel, orderdisplay
                                                                              );
        if (list == null || list.size() == 0) {
            // 物理ファイルの削除
            removeFile();
            return removeCategoryDisplayList.size();
        }
        Iterator<String> ite = list.iterator();
        while (ite.hasNext()) {
            String targetCategoryId = ite.next();
            conditionDto = ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
            conditionDto.setOrderField("categorypath");
            conditionDto.setOrderAsc(true);

            conditionDto.setCategoryId(targetCategoryId);
            categoryDto = categoryTreeNodeGetService.execute(conditionDto, null, siteType);
            // カレントカテゴリの並び順を1つ上へ上げる
            categoryDto.getCategoryEntity().setOrderDisplay(categoryDto.getCategoryEntity().getOrderDisplay() - 1);
            nodeModify(categoryDto, categoryDto.getCategoryEntity().getCategoryLevel(),
                       categoryDto.getCategoryEntity().getOrderDisplay()
                      );
        }

        // 物理ファイルの削除
        removeFile();

        return removeCategoryDisplayList.size();
    }

    /**
     * カテゴリを削除
     *
     * @param categoryDto カテゴリDTO
     */
    protected void nodeRemove(CategoryDto categoryDto) {
        if (categoryRemoveLogic.execute(categoryDto.getCategoryEntity()) != 1) {
            throwMessage("SGC000101");
        }
        if (categoryDisplayRemoveLogic.execute(categoryDto.getCategoryDisplayEntity()) != 1) {
            throwMessage("SGC000101");
        }
        categoryGoodsRemoveLogic.execute(categoryDto.getCategoryEntity().getCategorySeq());
        removeCategoryDisplayList.add(categoryDto.getCategoryDisplayEntity());
        for (CategoryDto subNode : categoryDto.getCategoryDtoList()) {
            nodeRemove(subNode);
        }
    }

    /**
     * カテゴリの並び順更新
     *
     * @param categoryDto   カテゴリDTO
     * @param categoryLevel 設定するレベル
     * @param orderDisplay  設定する並び順
     */
    protected void nodeModify(CategoryDto categoryDto, int categoryLevel, int orderDisplay) {
        CategoryEntity categoryEntity = categoryDto.getCategoryEntity();
        categoryModifyLogic.execute(categoryEntity);
        StringBuilder buff = new StringBuilder();
        String[] str = categoryEntity.getCategoryPath().split("/");
        for (int j = 1; j < str.length; j++) {
            if (categoryLevel == j) {
                buff.append("/" + String.format("%0" + 3 + "d", orderDisplay));
            } else {
                buff.append("/" + str[j]);
            }
        }
        // 生成したカテゴリパスを設定
        categoryEntity.setCategoryPath(buff.toString());
        if (categoryModifyLogic.execute(categoryEntity) != 1) {
            throwMessage("SGC000101");
        }
        for (CategoryDto subNode : categoryDto.getCategoryDtoList()) {
            nodeModify(subNode, categoryLevel, orderDisplay);
        }
    }

    /**
     * カテゴリの画像を物理削除
     */
    protected void removeFile() {
        // カテゴリUtility取得
        CategoryUtility categoryUtility = ApplicationContextUtility.getBean(CategoryUtility.class);

        String realPath = categoryUtility.getCategoryImageRealPath();
        Iterator<CategoryDisplayEntity> removeCategoryDisplayIterator = removeCategoryDisplayList.iterator();

        // ファイル操作Helper取得
        FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);

        String targetPath = "";
        try {
            while (removeCategoryDisplayIterator.hasNext()) {
                CategoryDisplayEntity categoryDisplayEntity = removeCategoryDisplayIterator.next();
                // PCの画像ファイルがある場合は削除
                if (categoryDisplayEntity.getCategoryImagePC() != null) {
                    targetPath = realPath + "/" + categoryDisplayEntity.getCategoryImagePC();
                    fileOperationUtility.remove(targetPath);
                }
                // SPの画像ファイルがある場合は削除
                if (categoryDisplayEntity.getCategoryImageSP() != null) {
                    targetPath = realPath + "/" + categoryDisplayEntity.getCategoryImageSP();
                    fileOperationUtility.remove(targetPath);
                }
                // モバイルの画像ファイルがある場合は削除
                if (categoryDisplayEntity.getCategoryImageMB() != null) {
                    targetPath = realPath + "/" + categoryDisplayEntity.getCategoryImageMB();
                    fileOperationUtility.remove(targetPath);
                }
            }
        } catch (IOException e) {
            LOGGER.warn("商品規格画像の削除に失敗しました。(" + targetPath + ")", e);
        }
    }

}
