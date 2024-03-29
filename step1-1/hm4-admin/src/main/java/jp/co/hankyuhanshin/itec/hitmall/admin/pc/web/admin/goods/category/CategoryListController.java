/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.validation.group.SelectionOutput1Group;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryExclusiveDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryGetExclusiveLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryModifyCategoryPathLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryCsvDownLoadService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryGetMaxCategorySeqService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryOpenGoodsCountService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryRemoveService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategorySimpleModifyService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryTableLockService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryTreeNodeGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CategoryUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.FileDownloadException;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * カテゴリ管理
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */

@RequestMapping("/goods/category")
@Controller
@SessionAttributes(value = "categoryListModel")
@PreAuthorize("hasAnyAuthority('GOODS:4')")
public class CategoryListController extends AbstractController {

    // 2023-renew categoryCSV from here
    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryListController.class);
    // 2023-renew categoryCSV to here

    /**
     * カテゴリ一覧Helper
     */
    public CategoryListHelper categoryHelper;

    /**
     * 変換ユーティリティ
     */
    public ConversionUtility conversionUtility;

    /**
     * CategoryUtility
     */
    public CategoryUtility categoryUtility;

    /**
     * カテゴリ木構造取得サービス
     */
    public CategoryTreeNodeGetService categoryTreeNodeGetService;

    /**
     * カテゴリ取得サービス
     */
    public CategoryGetService categoryGetService;

    /**
     * カテゴリ削除サービス
     */
    public CategoryRemoveService categoryRemoveService;

    /**
     * カテゴリ修正サービス
     */
    public CategorySimpleModifyService categorySimpleModifyService;

    /**
     * カテゴリIDに紐づく公開商品数取得サービス
     */
    public CategoryOpenGoodsCountService categoryOpenGoodsCountService;

    /**
     * カテゴリロックサービス
     */
    public CategoryTableLockService categoryTableLockService;

    /**
     * 現在のMAXSEQ取得サービス
     */
    public CategoryGetMaxCategorySeqService categoryGetMaxCategorySeqService;

    /**
     * 子階層のカテゴリーパスを更新
     */
    public CategoryModifyCategoryPathLogic categoryModifyCategoryPathLogic;

    /**
     * カテゴリ排他を取得
     */
    public CategoryGetExclusiveLogic categoryExclusive;

    // 2023-renew categoryCSV from here
    /**
     * カテゴリCSVダウンロードサービス
     */
    public CategoryCsvDownLoadService categoryCsvDownLoadService;
    // 2023-renew categoryCSV to here

    /**
     * コンストラクター
     *
     * @param categoryHelper
     * @param conversionUtility
     * @param categoryUtility
     * @param categoryTreeNodeGetService
     * @param categoryGetService
     * @param categoryRemoveService
     * @param categorySimpleModifyService
     * @param categoryOpenGoodsCountService
     * @param categoryTableLockService
     * @param categoryGetMaxCategorySeqService
     * @param categoryModifyCategoryPathLogic
     * @param categoryExclusive
     */
    @Autowired
    public CategoryListController(CategoryListHelper categoryHelper,
                                  ConversionUtility conversionUtility,
                                  CategoryUtility categoryUtility,
                                  CategoryTreeNodeGetService categoryTreeNodeGetService,
                                  CategoryGetService categoryGetService,
                                  CategoryRemoveService categoryRemoveService,
                                  CategorySimpleModifyService categorySimpleModifyService,
                                  CategoryOpenGoodsCountService categoryOpenGoodsCountService,
                                  CategoryTableLockService categoryTableLockService,
                                  CategoryGetMaxCategorySeqService categoryGetMaxCategorySeqService,
                                  CategoryModifyCategoryPathLogic categoryModifyCategoryPathLogic,
                                  CategoryGetExclusiveLogic categoryExclusive,
                                  // 2023-renew categoryCSV from here
                                  CategoryCsvDownLoadService categoryCsvDownLoadService) {
        // 2023-renew categoryCSV to here
        this.categoryHelper = categoryHelper;
        this.conversionUtility = conversionUtility;
        this.categoryUtility = categoryUtility;
        this.categoryTreeNodeGetService = categoryTreeNodeGetService;
        this.categoryGetService = categoryGetService;
        this.categoryRemoveService = categoryRemoveService;
        this.categorySimpleModifyService = categorySimpleModifyService;
        this.categoryOpenGoodsCountService = categoryOpenGoodsCountService;
        this.categoryTableLockService = categoryTableLockService;
        this.categoryGetMaxCategorySeqService = categoryGetMaxCategorySeqService;
        this.categoryModifyCategoryPathLogic = categoryModifyCategoryPathLogic;
        this.categoryExclusive = categoryExclusive;
        // 2023-renew categoryCSV from here
        this.categoryCsvDownLoadService = categoryCsvDownLoadService;
        // 2023-renew categoryCSV to here
    }

    /**
     * 初期表示
     *
     * @param categoryListModel
     * @param model
     * @return
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/category/list")
    public String doLoadList(CategoryListModel categoryListModel, RedirectAttributes redirectAttributes, Model model) {

        clearModel(CategoryListModel.class, categoryListModel, model);

        // 抽出カテゴリー用の値をページにセット
        categoryHelper.setExtractCategory(categoryListModel);

        // 抽出カテゴリーの表示 かつ 初期表示がされたことがない場合、抽象カテゴリーのプルダウン値を初期化
        if (categoryListModel.isExtractCategory() && !categoryListModel.isInitialDisplayList()) {
            categoryListModel.setExtractCategoryName(null);
        }

        // 抽出カテゴリーのプルダウンを作成
        createPulldownForExtractCategory(categoryListModel);

        // カテゴリー一覧を表示
        displayCategoryList(categoryListModel, redirectAttributes, model);

        // カテゴリSEQパス(画面受渡し用)を削除
        categoryListModel.setCategorySeqPathTarget(null);

        // 初期表示フラグ
        categoryListModel.setInitialDisplayList(false);
        // カテゴリー削除フラグ
        categoryListModel.setDeleteCategory(false);

        return "goods/category/list";
    }

    /**
     * 並べ替え
     *
     * @param categoryListModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doOrder")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/category/list")
    public String doOrder(CategoryListModel categoryListModel, RedirectAttributes redirectAttributes, Model model) {

        // カテゴリSEQパス(画面受渡し用)を削除
        categoryListModel.setCategorySeqPathTarget(null);

        // 変更カテゴリ一覧を取得します。
        String lstCategoryChange = categoryListModel.getDtCategory();

        // 情報のチェック
        if (categoryListModel.getCategoryMap() == null) {
            return "redirect:/goods/category/";
        }

        Iterator<CategoryModelItem> ite = categoryListModel.getResultItems().iterator();

        // セッション情報のチェック
        if (lstCategoryChange == null) {
            throwMessage("AGC000016");
        }

        // 更新対象があるかどうか
        if (categoryHelper.splitCategory(lstCategoryChange).length == 0) {
            throwMessage("AGC000016");
        }

        // カテゴリーアップデート
        categoryHelper.updateListCategory(lstCategoryChange, categoryListModel);

        // カテゴリテーブルロック
        categoryTableLockService.execute();

        // カテゴリ排他を取得
        CategoryExclusiveDto exclusiveTemp = categoryExclusive.execute();
        // 排他チェック
        if (exclusiveTemp.getSumCategory() != categoryListModel.getCategoryExclusive().getSumCategory()
            || !exclusiveTemp.getMaxUpdateTime().equals(categoryListModel.getCategoryExclusive().getMaxUpdateTime())) {
            addMessage("AGC000015", redirectAttributes, model);
            return "redirect:/goods/category/";
        }

        while (ite.hasNext()) {
            CategoryModelItem item = ite.next();
            CategoryEntity categoryEntity = categoryListModel.getCategoryMap().get(item.getCategoryId());
            if (categoryEntity == null) {
                addMessage("AGC000015", redirectAttributes, model);
                return "redirect:/goods/category/";
            }

            // 前回と移動時で、順位に変動なければカテゴリーを更新しない
            if (categoryEntity.getOrderDisplay().compareTo(item.getOrderDisplay()) == 0 && StringUtil.equals(
                            categoryEntity.getCategoryPath(), item.getCategoryPath()) && StringUtil.equals(
                            categoryEntity.getCategorySeqPath(), item.getCategorySeqPath())) {
                // カテゴリーを更新しない
                continue;
            }

            // // 抽出カテゴリーの表示 かつ プルダウン「指定あり」 かつ 指定したlimit値よりカテゴリーレベルが低い場合
            if (categoryListModel.isExtractCategory() && StringUtil.isNotEmpty(
                            categoryListModel.getExtractCategoryName())
                && item.getCategoryLevel() <= categoryListModel.getExtractCategoryLimit()) {
                // カテゴリーを更新しない
                continue;
            }

            categoryEntity.setOrderDisplay(item.getOrderDisplay());
            categoryEntity.setCategoryPath(item.getCategoryPath());
            // categorySeqPathを再更新する
            categoryEntity.setCategorySeqPath(item.getCategorySeqPath());
            // categoryLevelを再更新する
            categoryEntity.setCategoryLevel(item.getCategoryLevel());

            // 子階層のカテゴリパスを更新
            modifyCategoryPath(categoryListModel, categoryEntity);

            // カテゴリー更新
            if (categorySimpleModifyService.execute(categoryEntity) != 1) {
                addMessage("AGC000015", redirectAttributes, model);
                return "redirect:/goods/category/";
            }
        }

        // 初期表示フラグ
        categoryListModel.setInitialDisplayList(true);
        categoryListModel.setDtCategory(null);

        addInfoMessage("AGC000011", null, redirectAttributes, model);
        return "redirect:/goods/category/";
    }

    /**
     * 削除
     *
     * @param categoryListModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doRemove")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/category/list")
    public String doRemove(CategoryListModel categoryListModel, RedirectAttributes redirectAttributes, Model model) {

        // 情報のチェック
        if (categoryListModel.getCategoryMap() == null) {
            return "redirect:/goods/category/";
        }

        // カテゴリテーブルロック
        categoryTableLockService.execute();

        // 作業をしている間にデータが登録されていないかチェック
        if (!categoryListModel.getCategoryMaxSeq().equals(categoryGetMaxCategorySeqService.execute())) {
            addMessage("AGC000015", redirectAttributes, model);
            return "redirect:/goods/category/";
        }

        // カテゴリSEQパス(画面受渡し用)に追加
        CategoryModelItem deleteTarget =
                        categoryListModel.getResultItems().get(Integer.valueOf(categoryListModel.getResultIndex()));
        CategoryEntity categoryEntity = categoryGetService.execute(deleteTarget.getCategoryId());
        categoryListModel.setCategorySeqPathTarget(categoryEntity.getCategorySeqPath());

        // カテゴリID
        String categoryId = deleteTarget.getCategoryId();
        // 更新カウンタ（必須）
        int versionNo = deleteTarget.getVersionNo();
        // カテゴリ排他を取得
        int versionNoCurent = categoryEntity.getVersionNo();
        // 排他チェック
        if (versionNo != versionNoCurent) {
            addMessage("AGC000015", redirectAttributes, model);
            return "redirect:/goods/category/";
        }

        // 削除処理
        if (categoryRemoveService.execute(categoryId, HTypeSiteType.BACK) < 1) {
            addMessage("AGC000007", redirectAttributes, model);
            return "redirect:/goods/category/";
        }

        // カテゴリSEQパス(画面受渡し用)を親カテゴリパスに変更
        categoryListModel.setCategorySeqPathTarget(
                        categoryHelper.getParentSeqPath(categoryListModel.getCategorySeqPathTarget()));
        // 初期表示フラグ
        categoryListModel.setInitialDisplayList(true);
        // カテゴリー削除フラグ
        categoryListModel.setDeleteCategory(true);

        addInfoMessage("AGC000010", null, redirectAttributes, model);
        return "redirect:/goods/category/";
    }

    /**
     * 新規登録
     *
     * @param categoryListModel
     * @return
     */
    @PostMapping(value = "/", params = "jumpInput")
    public String jumpInput(CategoryListModel categoryListModel) {
        return "redirect:/goods/category/input/";
    }

    /**
     * カテゴリCSV出力<br/>
     */
    protected void hmCategoryDownload() {
        // 検索条件作成
        CategorySearchForDaoConditionDto categorySearchForDaoConditionDto =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);

        // 除外カテゴリID設定
        // TOPカテゴリは出力不要
        List<String> notInCategoryIdList = new ArrayList<>();
        notInCategoryIdList.add(categoryUtility.getCategoryIdTop());
        categorySearchForDaoConditionDto.setNotInCategoryIdList(notInCategoryIdList);
    }

    /**
     * 抽出カテゴリー表示(一覧表示ボタン押下)<br/>
     *
     * @param categoryListModel
     * @return 自ページ
     */
    @PostMapping(value = "/", params = "doDisplayExtractCategory")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/category/list")
    public String doDisplayExtractCategory(CategoryListModel categoryListModel,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {

        // 指定されたカテゴリーidからカテゴリー一覧を表示
        displayCategoryList(categoryListModel, redirectAttributes, model);
        return "goods/category/list";
    }

    /**
     * 抽出カテゴリーのプルダウンを作成<br/>
     *
     * @param categoryListModel
     */
    protected void createPulldownForExtractCategory(CategoryListModel categoryListModel) {

        // 抽出カテゴリーを表示しない場合、プルダウンは作成しない。
        if (!categoryListModel.isExtractCategory()) {
            return;
        }

        // html上のextractCategoryNameItemsのlimit値を取得
        Integer limit = categoryListModel.getExtractCategoryLimit();

        // カテゴリプルダウン用に最大表示階層数が指定されたカテゴリー情報を取得
        CategoryDto dto = categoryTreeNodeGetService.executeSpecificHierarchical(limit, HTypeSiteType.BACK);

        // カテゴリプルダウン作成
        categoryHelper.createPulldown(dto, categoryListModel, limit);

    }

    /**
     * カテゴリー一覧を表示<br/>
     *
     * @param categoryListModel
     */
    protected void displayCategoryList(CategoryListModel categoryListModel,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {

        // 移動実行時に利用するカテゴリ格納用
        categoryListModel.setCategoryMap(new HashMap<>());

        // カテゴリー排他を取得する。
        categoryListModel.setCategoryExclusive(categoryExclusive.execute());

        // カテゴリー情報を取得
        CategoryDto dto = getCategoryDto(categoryListModel, redirectAttributes, model);

        // 現在のカテゴリーMAXシーケンスを取得する。
        categoryListModel.setCategoryMaxSeq(categoryGetMaxCategorySeqService.execute());

        // カテゴリー情報より、カテゴリSEQのリストを作成する。
        List<Integer> categorySeqList = new ArrayList<>();
        getCategorySeqList(dto, categorySeqList);

        // カテゴリーに紐づく公開商品数の取得
        List<CategoryEntity> categoryEntityList = categoryOpenGoodsCountService.execute(categorySeqList);
        Map<Integer, CategoryEntity> categoryEntityMap = new HashMap<>();

        for (CategoryEntity categoryEntity : categoryEntityList) {
            categoryEntityMap.put(categoryEntity.getCategorySeq(), categoryEntity);
        }

        // カテゴリツリーの生成
        categoryHelper.createNode(dto, categoryListModel, categoryEntityMap);

    }

    /**
     * カテゴリー情報を取得<br/>
     *
     * @param categoryListModel
     * @return CategoryDto カテゴリー情報
     */
    protected CategoryDto getCategoryDto(CategoryListModel categoryListModel,
                                         RedirectAttributes redirectAttributes,
                                         Model model) {

        // 抽出カテゴリーが表示されていない場合、全カテゴリー一覧を取得
        if (!categoryListModel.isExtractCategory()) {

            // TOPから全てのカテゴリー情報を取得
            CategoryDto dto = categoryTreeNodeGetService.executeForAllCategory(HTypeSiteType.BACK);
            categoryListModel.setExtractCategorySpecified(false);
            return dto;
        }

        // 抽出カテゴリーが表示されている場合
        // 抽出カテゴリーのプルダウン「指定なし」の場合、最大表示階層数が指定されたカテゴリ情報を取得
        if (StringUtil.isEmpty(categoryListModel.getExtractCategoryName())) {

            // カテゴリ一覧用に最大表示階層(limit値)が指定されたカテゴリー情報を取得
            CategoryDto dto = categoryTreeNodeGetService.executeSpecificHierarchical(
                            categoryListModel.getExtractCategoryLimit(), HTypeSiteType.BACK);
            categoryListModel.setExtractCategorySpecified(false);
            return dto;
        }

        // 抽出カテゴリー用のカテゴリー情報をチェック
        checkExtractCategory(categoryListModel, redirectAttributes, model);

        // 抽出カテゴリーが表示されている場合
        // 抽出カテゴリーのプルダウン「指定あり」の場合、指定したカテゴリーidから親と子のカテゴリ情報を取得
        CategoryDto dto = categoryTreeNodeGetService.executeForAllNode(categoryListModel.getExtractCategoryName());
        categoryListModel.setExtractCategorySpecified(true);
        return dto;
    }

    /**
     * 抽出カテゴリー用のカテゴリー情報をチェック<br/>
     *
     * @param categoryListModel
     */
    protected String checkExtractCategory(CategoryListModel categoryListModel,
                                          RedirectAttributes redirectAttributes,
                                          Model model) {

        CategoryEntity categoryEntity = categoryGetService.execute(categoryListModel.getExtractCategoryName());

        // カテゴリーが削除されていた場合、エラー表示
        if (categoryEntity == null) {
            categoryListModel.setExtractCategoryName(null);
            addMessage("AGC000015", redirectAttributes, model);
            return "redirect:/goods/category/";
        }

        // 今現在と階層が違う場合、エラー表示
        if (categoryEntity.getCategoryLevel() != categoryListModel.getExtractCategoryLimit()) {
            categoryListModel.setExtractCategoryName(null);
            addMessage("AGC000015", redirectAttributes, model);
            return "redirect:/goods/category/";
        }
        return "";
    }

    /**
     * 子階層のカテゴリパスを更新<br/>
     *
     * @param categoryEntity    カテゴリエンティティ
     * @param categoryListModel
     */
    protected void modifyCategoryPath(CategoryListModel categoryListModel, CategoryEntity categoryEntity) {

        // 抽出カテゴリーが「非表示」の場合、子階層のカテゴリパスを更新しない
        if (!categoryListModel.isExtractCategory()) {
            return;
        }

        // 抽出カテゴリーがプルダウン「指定あり」の場合、子階層のカテゴリパスを更新しない
        if (StringUtil.isNotEmpty(categoryListModel.getExtractCategoryName())) {
            return;
        }

        // カテゴリーレベルが抽出カテゴリー(limit値)同じでない場合、子階層のカテゴリパスを更新しない
        if (categoryEntity.getCategoryLevel() != categoryListModel.getExtractCategoryLimit()) {
            return;
        }

        // カテゴリーレベルが抽出カテゴリー(limit値)と同じ場合のみ、子階層のカテゴリパスを更新
        categoryModifyCategoryPathLogic.execute(categoryEntity);

    }

    /**
     * カテゴリDTOよりカテゴリSEQのリストを作成<br/>
     *
     * @param categoryDto     カテゴリ情報
     * @param categorySeqList 作成するカテゴリSEQのリスト
     */
    protected void getCategorySeqList(CategoryDto categoryDto, List<Integer> categorySeqList) {
        // 自カテゴリのSEQをリストに追加
        categorySeqList.add(categoryDto.getCategoryEntity().getCategorySeq());

        if (categoryDto.getCategoryDtoList() != null) {
            for (CategoryDto childCategoryDto : categoryDto.getCategoryDtoList()) {
                getCategorySeqList(childCategoryDto, categorySeqList);
            }
        }
    }

    // 2023-renew categoryCSV from here

    /**
     * カテゴリーCSVダウンロード（一覧上部ボタン）<br/>
     *
     * @param
     * @return
     */
    @PostMapping(value = "/", params = "doCsvDownload")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/category/list")
    @HEHandler(exception = FileDownloadException.class, returnView = "goods/category/list")
    public void doCsvDownload(@Validated(SelectionOutput1Group.class) CategoryListModel categoryListModel,
                              BindingResult error,
                              HttpServletResponse response,
                              Model model) {
        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        // 検索条件作成
        CategorySearchForDaoConditionDto categorySearchForDaoConditionDto =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);

        // 除外カテゴリID設定
        // TOPカテゴリは出力不要
        List<String> notInCategoryIdList = new ArrayList<>();
        notInCategoryIdList.add(categoryUtility.getCategoryIdTop());
        categorySearchForDaoConditionDto.setNotInCategoryIdList(notInCategoryIdList);
        categorySearchForDaoConditionDto.setShopSeq(1001);
        try {
            this.categoryCsvDownLoadService.execute(categorySearchForDaoConditionDto, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }
    }

    /**
     * 新規登録
     *
     * @param categoryListModel
     * @return
     */
    @PostMapping(value = "/", params = "jumpBundledUpload")
    public String jumpBundledupload(CategoryListModel categoryListModel) {
        return "redirect:/goods/category/bundledupload/csv/";
    }

    // 2023-renew categoryCSV to here
}
