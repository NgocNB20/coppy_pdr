/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.include;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryDisplayEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryTreeNodeGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.param.CategoryTreeNodeResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeManualDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.category.CategoryDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CategoryUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 共通サイドメニューAjax Helper
 *
 * @author kaneda
 */
@Component
public class SidemenuHelper {

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    @Autowired
    public SidemenuHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * カテゴリ情報をサイドメニューModelアイテムクラスに変換<br />
     *
     * @param categoryDto        カテゴリDTO
     * @param sidemenuModelItems 共通サイドメニューAjax Modelアイテム
     * @param currentCid         現在の表示カテゴリID
     */
    public void toDataForLoad(CategoryDto categoryDto, List<SidemenuModelItem> sidemenuModelItems, String currentCid) {

        String currentCategorySeqPath = null;

        // カテゴリSEQパス
        if (!StringUtils.isEmpty(currentCid)) {
            currentCategorySeqPath = getCurrentCategorySeqPath(categoryDto, currentCid);
        }

        // サイドのカテゴリ一覧を設定
        if (categoryDto.getCategoryDtoList() != null) {
            for (int i = 0; i < categoryDto.getCategoryDtoList().size(); i++) {
                CategoryDto childCategoryDto = categoryDto.getCategoryDtoList().get(i);
                sidemenuModelItems = getCategoryList(childCategoryDto, sidemenuModelItems, currentCategorySeqPath);
            }
        }
    }

    /**
     * カテゴリ一覧情報の作成<br/>
     *
     * @param categoryDto            カテゴリ情報DTO
     * @param categoryPageItemList   カテゴリページ情報リスト
     * @param currentCategorySeqPath 現在カテゴリSEQパス
     * @return カテゴリ情報一覧
     */
    protected List<SidemenuModelItem> getCategoryList(CategoryDto categoryDto,
                                                      List<SidemenuModelItem> categoryPageItemList,
                                                      String currentCategorySeqPath) {

        SidemenuModelItem sideMenuModelItem = ApplicationContextUtility.getBean(SidemenuModelItem.class);
        if (categoryDto.getCategoryEntity() != null) {
            // 人気、おすすめ、新着カテゴリは省く
            if (!HTypeCategoryType.NORMAL.equals(categoryDto.getCategoryEntity().getCategoryType())) {
                return categoryPageItemList;
            }
            // サイドメニューページアイテムへカテゴリ情報を設定する
            setDataSidemenuPageItem(categoryDto, sideMenuModelItem, currentCategorySeqPath);
        }
        // カテゴリ情報一覧へサイドメニューページアイテムを設定する
        categoryPageItemList.add(sideMenuModelItem);

        // 子カテゴリ情報の設定
        if (categoryDto.getCategoryDtoList() != null) {
            for (CategoryDto childCategoryDto : categoryDto.getCategoryDtoList()) {
                categoryPageItemList = getCategoryList(childCategoryDto, categoryPageItemList, currentCategorySeqPath);
            }
        }
        return categoryPageItemList;
    }

    /**
     * サイドメニューページアイテムへカテゴリ情報を設定する<br/>
     *
     * @param categoryDto            カテゴリ情報DTO
     * @param sideMenuModelItem      サイドメニューページアイテム
     * @param currentCategorySeqPath 現在カテゴリSEQパス
     * @param customParams           案件用引数
     */
    protected void setDataSidemenuPageItem(CategoryDto categoryDto,
                                           SidemenuModelItem sideMenuModelItem,
                                           String currentCategorySeqPath,
                                           Object... customParams) {
        // カテゴリ情報設定
        sideMenuModelItem.setCid(categoryDto.getCategoryEntity().getCategoryId());
        sideMenuModelItem.setCategoryName(categoryDto.getCategoryDisplayEntity().getCategoryNamePC());
        sideMenuModelItem.setCategorySeqPath(categoryDto.getCategoryEntity().getCategorySeqPath());
        sideMenuModelItem.setCategoryLevel(categoryDto.getCategoryEntity().getCategoryLevel());

        // カテゴリファイル名設定
        setCategoryFileName(categoryDto.getCategoryEntity(), sideMenuModelItem, currentCategorySeqPath);
    }

    /**
     * カテゴリファイル名設定<br/>
     *
     * @param categoryEntity         カテゴリ情報
     * @param sideMenuPageItem       サイドメニューページアイテム
     * @param currentCategorySeqPath 現在カテゴリSEQパス
     * @param customParams           案件用引数
     */
    protected void setCategoryFileName(CategoryEntity categoryEntity,
                                       SidemenuModelItem sideMenuModelItem,
                                       String currentCategorySeqPath,
                                       Object... customParams) {

        if (categoryEntity.getCategoryLevel().intValue() == 1) {
            CategoryUtility categoryUtility = ApplicationContextUtility.getBean(CategoryUtility.class);
            String categoryId = categoryEntity.getCategoryId();
            // カテゴリ画像名を設定
            sideMenuModelItem.setCategoryFileName(categoryUtility.getLnavCategoryImageName(categoryId, false));
        }
    }

    /**
     * 現在カテゴリSEQパスを取得<br/>
     *
     * @param categoryDto       カテゴリ情報DTO
     * @param currentCategoryId 現在カテゴリID
     * @return 現在カテゴリSEQパス
     */
    protected String getCurrentCategorySeqPath(CategoryDto categoryDto, String currentCategoryId) {

        if (categoryDto.getCategoryEntity().getCategoryId().equals(currentCategoryId)) {
            return categoryDto.getCategoryEntity().getCategorySeqPath();
        }

        if (!CollectionUtils.isEmpty(categoryDto.getCategoryDtoList())) {
            String currentCategorySeqPath = null;
            for (int i = 0; i < categoryDto.getCategoryDtoList().size(); i++) {
                CategoryDto newCategoryDto = categoryDto.getCategoryDtoList().get(i);
                currentCategorySeqPath = getCurrentCategorySeqPath(newCategoryDto, currentCategoryId);
                if (currentCategorySeqPath != null) {
                    return currentCategorySeqPath;
                }
            }
        }

        return null;
    }

    /**
     * カテゴリ木構造取得リクエストに変換
     *
     * @param viewLevel カテゴリー階層数
     * @return カテゴリ木構造取得リクエスト
     */
    public CategoryTreeNodeGetRequest toCategoryTreeNodeGetRequest(String viewLevel) {
        CategoryTreeNodeGetRequest request = new CategoryTreeNodeGetRequest();
        String topCategoryId = PropertiesUtil.getSystemPropertiesValue("category.id.top");

        request.setCategoryId(topCategoryId);
        request.setMaxHierarchical(viewLevel == null ? null : Integer.parseInt(viewLevel));
        request.setSiteType(null);
        request.setOpenStatus(EnumTypeUtil.getValue(HTypeOpenStatus.OPEN));
        request.setOrderField(CategorySearchForDaoConditionDto.CATEGORY_FIELD_CATEGORY_PATH);
        request.setOrderAsc(true);

        return request;
    }

    /**
     * カテゴリクラスに変換
     *
     * @param categoryEntityResponse カテゴリレスポンス
     * @return カテゴリクラス
     */
    public CategoryEntity toCategoryEntity(CategoryEntityResponse categoryEntityResponse) {
        if (categoryEntityResponse == null) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategorySeq(categoryEntityResponse.getCategorySeq());
        categoryEntity.setShopSeq(1001);
        categoryEntity.setCategoryId(categoryEntityResponse.getCategoryId());
        categoryEntity.setCategoryName(categoryEntityResponse.getCategoryName());
        categoryEntity.setCategoryOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class,
                                                                             categoryEntityResponse.getCategoryOpenStatus()
                                                                            ));
        categoryEntity.setCategoryOpenStartTimePC(
                        conversionUtility.toTimeStamp(categoryEntityResponse.getCategoryOpenStartTime()));
        categoryEntity.setCategoryOpenEndTimePC(
                        conversionUtility.toTimeStamp(categoryEntityResponse.getCategoryOpenEndTime()));
        categoryEntity.setCategoryType(EnumTypeUtil.getEnumFromValue(HTypeCategoryType.class,
                                                                     categoryEntityResponse.getCategoryType()
                                                                    ));
        categoryEntity.setCategorySeqPath(categoryEntityResponse.getCategorySeqPath());
        categoryEntity.setOrderDisplay(categoryEntityResponse.getOrderDisplay());
        categoryEntity.setRootCategorySeq(categoryEntityResponse.getRootCategorySeq());
        categoryEntity.setCategoryLevel(categoryEntityResponse.getCategoryLevel());
        categoryEntity.setCategoryPath(categoryEntityResponse.getCategoryPath());
        categoryEntity.setManualDisplayFlag(EnumTypeUtil.getEnumFromValue(HTypeManualDisplayFlag.class,
                                                                          categoryEntityResponse.getManualDisplayFlag()
                                                                         ));
        categoryEntity.setVersionNo(categoryEntityResponse.getVersionNo());
        categoryEntity.setRegistTime(conversionUtility.toTimeStamp(categoryEntityResponse.getRegistTime()));
        categoryEntity.setUpdateTime(conversionUtility.toTimeStamp(categoryEntityResponse.getUpdateTime()));
        categoryEntity.setSiteMapFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class, categoryEntityResponse.getSiteMapFlag()));
        categoryEntity.setOpenGoodsCountPC(categoryEntityResponse.getOpenGoodsCount());

        return categoryEntity;
    }

    /**
     * カテゴリ表示に変換
     *
     * @param response カテゴリ表示レスポンス
     * @return カテゴリ表示
     */
    public CategoryDisplayEntity toCategoryDisplayEntity(CategoryDisplayEntityResponse response) {
        if (response == null) {
            return null;
        }

        CategoryDisplayEntity categoryDisplayEntity = new CategoryDisplayEntity();
        categoryDisplayEntity.setCategorySeq(response.getCategorySeq());
        categoryDisplayEntity.setCategoryNamePC(response.getCategoryName());
        categoryDisplayEntity.setCategoryNotePC(response.getCategoryNote());
        categoryDisplayEntity.setFreeTextPC(response.getFreeText());
        categoryDisplayEntity.setMetaDescription(response.getMetaDescription());
        categoryDisplayEntity.setMetaKeyword(response.getMetaKeyword());
        categoryDisplayEntity.setCategoryImagePC(response.getCategoryImage());
        categoryDisplayEntity.setRegistTime(conversionUtility.toTimeStamp(response.getRegistTime()));
        categoryDisplayEntity.setUpdateTime(conversionUtility.toTimeStamp(response.getUpdateTime()));

        return categoryDisplayEntity;
    }

    /**
     * カテゴリDTOリストに変換
     *
     * @param categoryTreeNodeResponseList カテゴリ木構造リストレスポンスリスト
     * @return カテゴリDTOリスト
     */
    public List<CategoryDto> toCategoryDtoList(List<CategoryTreeNodeResponse> categoryTreeNodeResponseList) {
        if (CollectionUtil.isEmpty(categoryTreeNodeResponseList)) {
            return new ArrayList<>();
        }

        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryTreeNodeResponseList.forEach(categoryTreeNodeResponse -> {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryEntity(toCategoryEntity(categoryTreeNodeResponse.getCategoryEntity()));
            categoryDto.setCategoryDisplayEntity(
                            toCategoryDisplayEntity(categoryTreeNodeResponse.getCategoryDisplayEntity()));
            categoryDto.setCategoryDtoList(toCategoryDtoList(categoryTreeNodeResponse.getCategoryDtoList()));

            categoryDtoList.add(categoryDto);
        });

        return categoryDtoList;
    }
}
