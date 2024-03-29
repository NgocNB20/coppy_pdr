/*
 * Project Name : HIT-MALL4
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryGoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryGoodsSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryGetGoodsListService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryModifyGoodsOrderService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * カテゴリ管理：カテゴリ内商品一覧
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/goods/category/goodslist")
@Controller
@SessionAttributes(value = "categoryGoodsListModel")
@PreAuthorize("hasAnyAuthority('GOODS:4')")
public class CategoryGoodsListController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryGoodsListController.class);

    /**
     * カテゴリー内商品一覧：デフォルトページ番号
     */
    private static final String DEFAULT_PNUM = "1";
    /**
     * カテゴリー内商品一覧：１ページ当たりのデフォルト最大表示件数
     */
    private static final int DEFAULT_LIMIT = 100;

    /**
     * カテゴリ取得サービス
     */
    public CategoryGetService categoryGetService;

    /**
     * カテゴリIDに紐づく公開商品数取得サービス
     */
    public CategoryGetGoodsListService categoryGetGoodsListService;

    /**
     * カテゴリ内商品の並び順変更サービス
     */
    public CategoryModifyGoodsOrderService categoryModifyGoodsOrderService;

    /**
     * コンストラクター
     *
     * @param categoryGetService
     * @param categoryGetGoodsListService
     * @param categoryModifyGoodsOrderService
     */
    @Autowired
    public CategoryGoodsListController(CategoryGetService categoryGetService,
                                       CategoryGetGoodsListService categoryGetGoodsListService,
                                       CategoryModifyGoodsOrderService categoryModifyGoodsOrderService) {
        this.categoryGetService = categoryGetService;
        this.categoryGetGoodsListService = categoryGetGoodsListService;
        this.categoryModifyGoodsOrderService = categoryModifyGoodsOrderService;
    }

    /**
     * 初期表示<br/>
     *
     * @param categoryId
     * @param categoryGoodsListModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/category/goodslist")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> categoryId,
                              CategoryGoodsListModel categoryGoodsListModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // 初期表示(カテゴリー一覧ページ)
        categoryGoodsListModel.setInitialDisplayList(true);

        if (categoryId.isPresent()) {
            categoryGoodsListModel.setCategoryId(categoryId.get());
        }

        // 表示用のカテゴリ情報の取得
        if (categoryGoodsListModel.getCategoryId() != null) {
            categoryGoodsListModel.setTargetParentCategoryId(categoryGoodsListModel.getCategoryId());
            String returnView = setTargetCurrentCategory(categoryGoodsListModel, redirectAttributes, model);
            if (StringUtils.isNotEmpty(returnView)) {
                return returnView;
            }
        }

        // セッション情報のチェック
        if (categoryGoodsListModel.getCategoryEntity() == null) {
            return "redirect:/goods/category/";
        }

        // 読み込み処理
        String returnView = read(categoryGoodsListModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(returnView)) {
            return returnView;
        }

        return "goods/category/goodslist";
    }

    /**
     * 並び順の変更処理<br/>
     * ・並び順変更(ポップアップ)でOKボタンが押下された場合の処理<br/>
     *
     * @param categoryGoodsListModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doChangeOrderBy")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/category/goodslist")
    public String doChangeOrderBy(CategoryGoodsListModel categoryGoodsListModel,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        // 読み込み処理
        String returnView = read(categoryGoodsListModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(returnView)) {
            return returnView;
        }

        // 並び順を変更する
        if (categoryGoodsListModel.getTargetNo() != null) {
            if (!categoryGoodsListModel.getTargetNo().equals(categoryGoodsListModel.getNo())) {
                try {
                    // 数値チェック
                    Integer.valueOf(categoryGoodsListModel.getTargetNo());
                } catch (Exception e) {
                    LOGGER.error("例外処理が発生しました", e);
                    throwMessage("AGC000018");
                }

                // 入力値下限チェック
                if (Integer.valueOf(categoryGoodsListModel.getTargetNo()) < 1) {
                    throwMessage("AGC000022");
                }

                // 入力値上限チェック
                Integer maxOrder = categoryGoodsListModel.getTotalCount();
                if (maxOrder.compareTo(Integer.valueOf(categoryGoodsListModel.getTargetNo())) < 0) {
                    throwMessage("AGC000021");
                }

                // 並び順の変更
                returnView = orderModify(categoryGoodsListModel, redirectAttributes, model);
                if (StringUtils.isNotEmpty(returnView)) {
                    return returnView;
                }

                // 読み込み処理
                returnView = read(categoryGoodsListModel, redirectAttributes, model);
                if (StringUtils.isNotEmpty(returnView)) {
                    return returnView;
                }

            }
        }
        return "goods/category/goodslist";
    }

    /**
     * ページング処理<br/>
     *
     * @param categoryGoodsListModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/category/goodslist")
    public String doDisplayChange(CategoryGoodsListModel categoryGoodsListModel,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        // セッション情報のチェック
        if (categoryGoodsListModel.getCategoryEntity() == null) {
            return "redirect:/goods/category/";
        }

        // 読み込み処理
        String returnView = read(categoryGoodsListModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(returnView)) {
            return returnView;
        }

        return "goods/category/goodslist";
    }

    /**
     * 初期読み込み<br/>
     *
     * @param categoryGoodsListModel
     * @param redirectAttributes
     * @param model
     */
    protected String read(CategoryGoodsListModel categoryGoodsListModel,
                          RedirectAttributes redirectAttributes,
                          Model model) {

        // ページ番号と最大表示件数を初期化
        if (StringUtils.isEmpty(categoryGoodsListModel.getPageNumber())) {
            categoryGoodsListModel.setPageNumber(DEFAULT_PNUM);
        }
        if (categoryGoodsListModel.getLimit() == 0) {
            categoryGoodsListModel.setLimit(DEFAULT_LIMIT);
        }

        // ---------------------------------------------
        // 画面表示用のカテゴリー内商品一覧を取得
        // ---------------------------------------------
        CategoryGoodsSearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(CategoryGoodsSearchForDaoConditionDto.class);
        conditionDto.setCategorySeq(categoryGoodsListModel.getCategoryEntity().getCategorySeq());

        // PageInfoセットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(
                        conditionDto, categoryGoodsListModel.getPageNumber(), categoryGoodsListModel.getLimit());

        List<CategoryGoodsDetailsDto> list = categoryGetGoodsListService.execute(conditionDto);

        // Pagerセットアップ
        pageInfoHelper.setupViewPager(conditionDto, categoryGoodsListModel);

        // 件数セット
        categoryGoodsListModel.setTotalCount(conditionDto.getTotalCount());

        // ---------------------------------------------
        // 表示順切り替えのために、別途カテゴリー内商品一覧を取得
        // ※こちらは全件データを検索
        // ---------------------------------------------
        CategoryGoodsSearchForDaoConditionDto conditionDto2 =
                        ApplicationContextUtility.getBean(CategoryGoodsSearchForDaoConditionDto.class);
        conditionDto2.setCategorySeq(categoryGoodsListModel.getCategoryEntity().getCategorySeq());
        pageInfoHelper.setupPageInfoForSkipCount(conditionDto2, categoryGoodsListModel.getTotalCount());
        List<CategoryGoodsDetailsDto> list2 = categoryGetGoodsListService.execute(conditionDto2);
        // 商品情報のチェック
        if (list.size() == 0) {
            addMessage("AGC000019", redirectAttributes, model);
            return "redirect:/goods/category/";
        }
        init(list, categoryGoodsListModel, conditionDto);
        init2(list2, categoryGoodsListModel);
        return null;
    }

    /**
     * カレントカテゴリを設定<br/>
     *
     * @param categoryGoodsListModel
     * @param redirectAttributes
     * @param model
     */
    protected String setTargetCurrentCategory(CategoryGoodsListModel categoryGoodsListModel,
                                              RedirectAttributes redirectAttributes,
                                              Model model) {

        CategoryEntity dto = categoryGetService.execute(categoryGoodsListModel.getCategoryId());

        if (dto == null) {
            addMessage("AGC000017", redirectAttributes, model);
            return "redirect:/goods/category/";
        }
        categoryGoodsListModel.setCategoryEntity(dto);
        // カテゴリSEQパス(画面受渡し用)
        categoryGoodsListModel.setCategorySeqPathTarget(dto.getCategorySeqPath());

        return null;
    }

    /**
     * 並び順を変更<br/>
     *
     * @param categoryGoodsListModel
     * @param redirectAttributes
     * @param model
     */
    protected String orderModify(CategoryGoodsListModel categoryGoodsListModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        Integer orderDisplay = Integer.valueOf(categoryGoodsListModel.getTempItems()
                                                                     .get(Integer.valueOf(
                                                                                     categoryGoodsListModel.getNo())
                                                                          - 1)
                                                                     .getOrderDisplay());
        Integer resultOrderDisplay = Integer.valueOf(categoryGoodsListModel.getTempItems()
                                                                           .get(Integer.valueOf(
                                                                                           categoryGoodsListModel.getTargetNo())
                                                                                - 1)
                                                                           .getOrderDisplay());

        // 並び順の変更
        int updateCount = categoryModifyGoodsOrderService.execute(
                        categoryGoodsListModel.getCategoryEntity().getCategorySeq(), orderDisplay, resultOrderDisplay);

        if (updateCount < 1) {
            addMessage("AGC000023", redirectAttributes, model);
            return "redirect:/goods/category/";
        }
        addInfoMessage("AGC000020", null, redirectAttributes, model);
        return null;
    }

    /**
     * 初期処理(カテゴリ内商品一覧の生成)<br/>
     *
     * @param list                   カテゴリ内商品一覧
     * @param categoryGoodsListModel
     * @param conditionDto
     */
    protected void init(List<CategoryGoodsDetailsDto> list,
                        CategoryGoodsListModel categoryGoodsListModel,
                        CategoryGoodsSearchForDaoConditionDto conditionDto) {

        categoryGoodsListModel.setCategoryId(categoryGoodsListModel.getCategoryEntity().getCategoryId());
        categoryGoodsListModel.setCategoryName(categoryGoodsListModel.getCategoryEntity().getCategoryName());

        if (list.size() <= 0) {
            return;
        }

        // resultNo計上用
        int index = conditionDto.getOffset() + 1;

        List<CategoryGoodslistItem> goodsListPageItemList = new ArrayList<>();

        for (CategoryGoodsDetailsDto categoryGoodsDetailsDto : list) {
            CategoryGoodslistItem goodsListPageItem = ApplicationContextUtility.getBean(CategoryGoodslistItem.class);
            goodsListPageItem.setResultNo(index++);
            goodsListPageItem.setGoodsGroupCode(categoryGoodsDetailsDto.getGoodsGroupCode());
            // 2023-renew No64 from here
            goodsListPageItem.setGoodsGroupName(categoryGoodsDetailsDto.getGoodsGroupNameAdmin());
            // 2023-renew No64 to here

            goodsListPageItem.setGoodsOpenStatusPC(categoryGoodsDetailsDto.getGoodsOpenStatusPC().getValue());

            // 商品公開開始期間(PC)
            if (categoryGoodsDetailsDto.getOpenStartTimePC() != null) {
                goodsListPageItem.setGoodsOpenFromDateTimePC(categoryGoodsDetailsDto.getOpenStartTimePC());
            }
            // 商品公開終了期間(PC)
            if (categoryGoodsDetailsDto.getOpenEndTimePC() != null) {
                goodsListPageItem.setGoodsOpenToDateTimePC(categoryGoodsDetailsDto.getOpenEndTimePC());
            }

            goodsListPageItem.setOrderDisplay(categoryGoodsDetailsDto.getOrderDisplay().toString());
            goodsListPageItemList.add(goodsListPageItem);
        }
        categoryGoodsListModel.setResultItems(goodsListPageItemList);
    }

    /**
     * 初期処理(カテゴリ内商品一覧の生成)<br/>
     *
     * @param list カテゴリ内商品一覧
     */
    protected void init2(List<CategoryGoodsDetailsDto> list, CategoryGoodsListModel categoryGoodsListModel) {

        List<CategoryGoodslistItem> goodsListPageItemList = new ArrayList<>();

        for (CategoryGoodsDetailsDto categoryGoodsDetailsDto : list) {
            CategoryGoodslistItem goodsListPageItem = ApplicationContextUtility.getBean(CategoryGoodslistItem.class);
            goodsListPageItem.setOrderDisplay(categoryGoodsDetailsDto.getOrderDisplay().toString());
            goodsListPageItemList.add(goodsListPageItem);
        }
        categoryGoodsListModel.setTempItems(goodsListPageItemList);
    }

    /**
     * 並び順指定<br/>
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doOrder")
    public String doOrder() {
        return "redirect:/goods/category/goodslist/";
    }

    /**
     * 戻る<br/>
     *
     * @return class
     */
    @PostMapping(value = "/", params = "doReturn")
    public String doReturn() {
        return "redirect:/goods/category/";
    }

}
