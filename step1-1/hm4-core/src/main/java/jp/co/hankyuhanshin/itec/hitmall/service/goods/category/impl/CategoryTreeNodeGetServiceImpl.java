/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.AllNodeCategorySearchLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.ChildNodeCategorySearchLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryTreeNodeGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CategoryUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * カテゴリ木構造取得<br/>
 *
 * @author ozaki
 */
@Service
public class CategoryTreeNodeGetServiceImpl extends AbstractShopService implements CategoryTreeNodeGetService {

    /**
     * 子ノードカテゴリ検索ロジッククラス
     */
    private final ChildNodeCategorySearchLogic childNodeCategorySearchLogic;

    /**
     * 親・子ノードカテゴリ検索ロジッククラス
     */
    private final AllNodeCategorySearchLogic allNodeCategorySearchLogic;

    /**
     * カテゴリーユーティリティクラス
     */
    private final CategoryUtility categoryUtility;

    @Autowired
    public CategoryTreeNodeGetServiceImpl(ChildNodeCategorySearchLogic childNodeCategorySearchLogic,
                                          AllNodeCategorySearchLogic allNodeCategorySearchLogic,
                                          CategoryUtility categoryUtility) {

        this.childNodeCategorySearchLogic = childNodeCategorySearchLogic;
        this.allNodeCategorySearchLogic = allNodeCategorySearchLogic;
        this.categoryUtility = categoryUtility;
    }

    /**
     * カテゴリ情報を階層の形で取得する。<br/>
     *
     * @param conditionDto      検索条件DTO
     * @param currentCategoryId 表示するカテゴリのカテゴリID（子カテゴリを取得したい場合のみ指定）
     * @return カテゴリDTO
     */
    @Override
    public CategoryDto execute(CategorySearchForDaoConditionDto conditionDto,
                               String currentCategoryId,
                               HTypeSiteType siteType) {

        // (1) パラメータチェック
        ArgumentCheckUtil.assertNotNull("conditionDto", conditionDto);

        // (2) 共通情報チェック

        // (3) カテゴリ情報検索処理実行
        // ‥ショップSEQ =共通情報.ショップSEQ
        // ‥サイト区分 =共通情報.サイト区分
        conditionDto.setShopSeq(1001);
        conditionDto.setSiteType(siteType);

        // (4) カテゴリリストの取得
        // ※『logic基本設計書（子ノードカテゴリ情報検索）.xls』参照
        // パラメータ ・カテゴリ情報Dao用検索条件Dto
        // ・表示しているカテゴリのカテゴリID
        // 戻り値 カテゴリ詳細情報DTOリスト
        List<CategoryDetailsDto> categoryDetailsDtoList =
                        childNodeCategorySearchLogic.execute(conditionDto, currentCategoryId);
        if (categoryDetailsDtoList == null || categoryDetailsDtoList.size() == 0) {
            // 取得できなかった場合はエラー
            this.addErrorMessage(MSGCD_CATEGORYDTO_NULL);
            return null;
        }

        // (5) カテゴリDTO作成
        // ・取得したカテゴリエンティティリストとカテゴリ表示情報リスト
        // を元にカテゴリDTOを作成する
        // ※但し、カテゴリ表示情報はパラメータ.カテゴリIDと同じカテゴリにのみ
        // 設定するものとする
        int[] currentIndex = {0};
        CategoryDto categoryDto = getCategoryDto(categoryDetailsDtoList, currentCategoryId, currentIndex);

        return categoryDto;
    }

    /**
     * TOPから全てのカテゴリー情報を階層の形で取得する。<br/>
     *
     * @return カテゴリ情報DTO
     */
    @Override
    public CategoryDto executeForAllCategory(HTypeSiteType siteType) {

        // TOPから全てのカテゴリを取得する検索条件を作成
        CategorySearchForDaoConditionDto conditionDto = createConditionDtoForAllCategory();

        // TOPから全カテゴリ情報の取得
        CategoryDto dto = execute(conditionDto, null, siteType);

        return dto;
    }

    /**
     * 最大表示階層が指定されたカテゴリー情報を階層の形で取得する。<br/>
     *
     * @param maxHierarchical 最大表示階層
     * @return カテゴリ情報DTO
     */
    @Override
    public CategoryDto executeSpecificHierarchical(Integer maxHierarchical, HTypeSiteType siteType) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("maxHierarchical", maxHierarchical);

        // 最大表示階層が指定された検索条件を作成
        CategorySearchForDaoConditionDto conditionDto = createConditionDtoSpecificHierarchical(maxHierarchical);

        // カテゴリ情報を階層の形で取得
        CategoryDto categoryDto = execute(conditionDto, null, siteType);

        return categoryDto;
    }

    /**
     * 指定したカテゴリーidから親・子カテゴリ情報を階層の形で取得する。<br/>
     *
     * @param categoryId カテゴリID
     * @return カテゴリDTO
     */
    @Override
    public CategoryDto executeForAllNode(String categoryId) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("categoryId", categoryId);

        // カテゴリリストの取得
        List<CategoryDetailsDto> categoryDetailsDtoList = allNodeCategorySearchLogic.execute(categoryId);

        if (CollectionUtil.isEmpty(categoryDetailsDtoList)) {
            // 取得できなかった場合はエラー
            this.addErrorMessage(MSGCD_CATEGORYDTO_NULL);
            return null;
        }

        // カテゴリDTO作成
        int[] currentIndex = {0};
        CategoryDto categoryDto = getCategoryDto(categoryDetailsDtoList, null, currentIndex);

        return categoryDto;
    }

    /**
     * 自分とその子供のカテゴリDTO階層の作成<br/>
     *
     * @param categoryDetailsDtoList カテゴリ詳細DTO一覧
     * @param categoryId             カテゴリID
     * @param currentIndex           現在参照しているリストインデックス
     * @return カテゴリDTO
     */
    protected CategoryDto getCategoryDto(List<CategoryDetailsDto> categoryDetailsDtoList,
                                         String categoryId,
                                         int[] currentIndex) {

        List<CategoryDto> categoryDtoList = new ArrayList<>();

        CategoryDetailsDto categoryDetailsDto = categoryDetailsDtoList.get(currentIndex[0]);
        CategoryDto categoryDto = ApplicationContextUtility.getBean(CategoryDto.class);
        categoryDto.setCategoryEntity(getCategoryEntity(categoryDetailsDto));
        categoryDto.setCategoryDisplayEntity(getCategoryDisplayEntity(categoryDetailsDto));

        currentIndex[0]++;
        for (; currentIndex[0] < categoryDetailsDtoList.size(); currentIndex[0]++) {

            CategoryDetailsDto newCategoryDetailsDto = categoryDetailsDtoList.get(currentIndex[0]);

            if (categoryDetailsDto.getCategoryLevel().compareTo(newCategoryDetailsDto.getCategoryLevel()) == -1) {
                if (newCategoryDetailsDto.getCategorySeqPath().startsWith(categoryDetailsDto.getCategorySeqPath())
                    && newCategoryDetailsDto.getCategoryLevel().intValue()
                       == categoryDetailsDto.getCategoryLevel().intValue() + 1) {
                    categoryDtoList.add(getCategoryDto(categoryDetailsDtoList, categoryId, currentIndex));
                    currentIndex[0]--;
                }
            } else {
                break;
            }
        }

        categoryDto.setCategoryDtoList(categoryDtoList);

        return categoryDto;
    }

    /**
     * カテゴリエンティティ作成<br/>
     * カテゴリ情報DTOよりカテゴリエンティティを作成する。<br/>
     *
     * @param categoryDetailsDto カテゴリ詳細情報DTO
     * @return カテゴリエンティティ
     */
    protected CategoryEntity getCategoryEntity(CategoryDetailsDto categoryDetailsDto) {

        CategoryEntity categoryEntity = ApplicationContextUtility.getBean(CategoryEntity.class);

        categoryEntity.setCategorySeq(categoryDetailsDto.getCategorySeq());
        categoryEntity.setShopSeq(categoryDetailsDto.getShopSeq());
        categoryEntity.setCategoryId(categoryDetailsDto.getCategoryId());
        categoryEntity.setCategoryLevel(categoryDetailsDto.getCategoryLevel());
        categoryEntity.setCategoryName(categoryDetailsDto.getCategoryName());
        categoryEntity.setCategoryOpenEndTimeMB(categoryDetailsDto.getCategoryOpenEndTimeMB());
        categoryEntity.setCategoryOpenEndTimePC(categoryDetailsDto.getCategoryOpenEndTimePC());
        categoryEntity.setCategoryOpenStartTimeMB(categoryDetailsDto.getCategoryOpenStartTimeMB());
        categoryEntity.setCategoryOpenStartTimePC(categoryDetailsDto.getCategoryOpenStartTimePC());
        categoryEntity.setCategoryOpenStatusMB(categoryDetailsDto.getCategoryOpenStatusMB());
        categoryEntity.setCategoryOpenStatusPC(categoryDetailsDto.getCategoryOpenStatusPC());
        categoryEntity.setCategoryPath(categoryDetailsDto.getCategoryPath());
        categoryEntity.setCategorySeqPath(categoryDetailsDto.getCategorySeqPath());
        categoryEntity.setCategoryType(categoryDetailsDto.getCategoryType());
        categoryEntity.setManualDisplayFlag(categoryDetailsDto.getManualDisplayFlag());
        categoryEntity.setOrderDisplay(categoryDetailsDto.getOrderDisplay());
        categoryEntity.setRootCategorySeq(categoryDetailsDto.getRootCategorySeq());
        categoryEntity.setVersionNo(categoryDetailsDto.getVersionNo());

        categoryEntity.setRegistTime(categoryDetailsDto.getRegistTime());
        categoryEntity.setUpdateTime(categoryDetailsDto.getUpdateTime());

        categoryEntity.setSiteMapFlag(categoryDetailsDto.getSiteMapFlag());

        return categoryEntity;
    }

    /**
     * カテゴリ表示エンティティ作成<br/>
     * カテゴリ情報DTOよりカテゴリ表示エンティティを作成する。<br/>
     *
     * @param categoryDetailsDto カテゴリ詳細情報DTO
     * @return カテゴリ表示エンティティ
     */
    protected CategoryDisplayEntity getCategoryDisplayEntity(CategoryDetailsDto categoryDetailsDto) {

        CategoryDisplayEntity categoryDisplayEntity = ApplicationContextUtility.getBean(CategoryDisplayEntity.class);

        categoryDisplayEntity.setCategoryImageMB(categoryDetailsDto.getCategoryImageMB());
        categoryDisplayEntity.setCategoryImageSP(categoryDetailsDto.getCategoryImageSP());
        categoryDisplayEntity.setCategoryImagePC(categoryDetailsDto.getCategoryImagePC());
        categoryDisplayEntity.setCategoryNameMB(categoryDetailsDto.getCategoryNameMB());
        categoryDisplayEntity.setCategoryNamePC(categoryDetailsDto.getCategoryNamePC());
        categoryDisplayEntity.setCategoryNoteMB(categoryDetailsDto.getCategoryNoteMB());
        categoryDisplayEntity.setCategoryNotePC(categoryDetailsDto.getCategoryNotePC());
        categoryDisplayEntity.setCategorySeq(categoryDetailsDto.getCategorySeq());
        categoryDisplayEntity.setFreeTextMB(categoryDetailsDto.getFreeTextMB());
        categoryDisplayEntity.setFreeTextSP(categoryDetailsDto.getFreeTextSP());
        categoryDisplayEntity.setFreeTextPC(categoryDetailsDto.getFreeTextPC());
        categoryDisplayEntity.setMetaDescription(categoryDetailsDto.getMetaDescription());
        categoryDisplayEntity.setMetaKeyword(categoryDetailsDto.getMetaKeyword());
        categoryDisplayEntity.setRegistTime(categoryDetailsDto.getDisplayRegistTime());
        categoryDisplayEntity.setUpdateTime(categoryDetailsDto.getDisplayUpdateTime());

        return categoryDisplayEntity;
    }

    /**
     * TOPから全てのカテゴリーを取得する検索条件を作成<br/>
     *
     * @return CategoryDto カテゴリ情報Dao用検索条件Dto
     */
    protected CategorySearchForDaoConditionDto createConditionDtoForAllCategory() {

        CategorySearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
        conditionDto.setCategoryId(categoryUtility.getCategoryIdTop());
        conditionDto.setOrderField("categorypath");
        conditionDto.setOrderAsc(true);

        return conditionDto;
    }

    /**
     * 最大表示階層が指定した検索条件を作成<br/>
     *
     * @param maxHierarchical 最大表示階層
     * @return ConditionDto
     */
    protected CategorySearchForDaoConditionDto createConditionDtoSpecificHierarchical(Integer maxHierarchical) {

        CategorySearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
        conditionDto.setCategoryId(categoryUtility.getCategoryIdTop());
        conditionDto.setMaxHierarchical(maxHierarchical);
        conditionDto.setOrderField("categorypath");
        conditionDto.setOrderAsc(true);

        return conditionDto;
    }

}
