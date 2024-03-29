/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.ajax.MessageUtils;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.ajax.ValidatorMessage;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.validation.goodssearch.SelectRegitOrUpdateValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.validation.goodssearch.group.GoodsSearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultForOrderRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.division.DivisionMapGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.GoodsSearchResultListForOrderRegistGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * 「新規受注：商品検索」画面のアクション<br/>
 *
 * @author nakamura
 */
@RequestMapping("/order/details/goodssearch")
@Controller
@SessionAttributes(value = "goodsSearchModel")
@PreAuthorize("hasAnyAuthority('ORDER:8')")
public class GoodsSearchController extends AbstractController {

    /**
     * セール基準日が未来日：PKG-3553-031-A-
     */
    public static final String MSGCD_SALE_TIME_FUTURE_DATE = "PKG-3553-031-A-";

    /**
     * 検索条件
     */
    public static final String FLASH_CONDITIONDTO = "conditionDto";

    /**
     * 受注お届け先Index
     */
    public static final String FLASH_RECEIVERINDEX = "orderReceiverIndex";

    /**
     * 商品検索：デフォルトページ番号
     */
    private static final String DEFAULT_GOODSSEARCH_PNUM = "1";

    /**
     * 商品検索：デフォルト：ソート項目
     */
    private static final String DEFAULT_GOODSSEARCH_ORDER_FIELD = "goodsGroupCode";

    /**
     * 商品検索：デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_GOODSSEARCH_ORDER_ASC = true;

    /**
     * helper
     */
    private final GoodsSearchHelper goodsSearchHelper;

    /**
     * 商品検索結果リスト取得サービス
     */
    private final GoodsSearchResultListForOrderRegistGetService goodsSearchResultListForOrderRegistGetService;

    /**
     * カテゴリリスト取得
     */
    private final CategoryListGetService categoryListGetService;

    /**
     * 分類リスト取得サービス
     */
    private final DivisionMapGetService divisionMapGetService;

    /**
     * 登録日／更新日選択フラグ用動的バリデータ
     */
    private final SelectRegitOrUpdateValidator selectRegitOrUpdateValidator;

    @Autowired
    public GoodsSearchController(GoodsSearchHelper goodsSearchHelper,
                                 GoodsSearchResultListForOrderRegistGetService goodsSearchResultListForOrderRegistGetService,
                                 CategoryListGetService categoryListGetService,
                                 DivisionMapGetService divisionMapGetService,
                                 SelectRegitOrUpdateValidator selectRegitOrUpdateValidator) {
        this.goodsSearchHelper = goodsSearchHelper;
        this.goodsSearchResultListForOrderRegistGetService = goodsSearchResultListForOrderRegistGetService;
        this.categoryListGetService = categoryListGetService;
        this.divisionMapGetService = divisionMapGetService;
        this.selectRegitOrUpdateValidator = selectRegitOrUpdateValidator;
    }

    @InitBinder(value = "goodsSearchModel")
    public void initBinder(WebDataBinder error) {
        // 商品検索画面の動的バリデータをセット
        error.addValidators(selectRegitOrUpdateValidator);
    }

    /**
     * 初期処理<br/>
     *
     * @return 自画面
     */
    @GetMapping("")
    public String doLoad(GoodsSearchModel goodsSearchModel, Model model) {

        if (model.containsAttribute(DetailsUpdateModel.FLASH_UPDATE_MODEL)) {

            DetailsUpdateModel detailsUpdateModel =
                            (DetailsUpdateModel) model.getAttribute(DetailsUpdateModel.FLASH_UPDATE_MODEL);

            goodsSearchModel.setOriginalReceiveOrder(detailsUpdateModel.getOriginalReceiveOrder());
            goodsSearchModel.setModifiedReceiveOrder(detailsUpdateModel.getModifiedReceiveOrder());
            goodsSearchModel.setOrderCode(detailsUpdateModel.getOrderCode());
        }

        // カテゴリパスを保持するマップを作成
        this.setCategoryPathMap(goodsSearchModel, model);
        model.addAttribute("goodsSearchModel", goodsSearchModel);
        initComponentValue(goodsSearchModel);

        return "order/details/goodssearch";
    }

    /**
     * 初期処理<br/>
     *
     * @return 自画面
     */
    @PostMapping("/ajax")
    public ResponseEntity<?> doLoad(GoodsSearchModel goodsSearchModel,
                                    Model model,
                                    @RequestBody DetailsUpdateModel detailsUpdateModel) {
        if (!ObjectUtils.isEmpty(detailsUpdateModel)) {
            goodsSearchModel.setOriginalReceiveOrder(detailsUpdateModel.getOriginalReceiveOrder());
            goodsSearchModel.setModifiedReceiveOrder(detailsUpdateModel.getModifiedReceiveOrder());
            goodsSearchModel.setOrderCode(detailsUpdateModel.getOrderCode());
        }
        // カテゴリパスを保持するマップを作成
        this.setCategoryPathMap(goodsSearchModel, model);
        model.addAttribute("goodsSearchModel", goodsSearchModel);
        initComponentValue(goodsSearchModel);

        return ResponseEntity.ok("success");
    }

    /**
     * 「追加」ボタン押下時の処理<br/>
     *
     * @return 商品選択画面
     */
    @PostMapping(value = "/", params = "doOrderGoodsAdd")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/goodssearch")
    public String doOrderGoodsAdd(GoodsSearchModel goodsSearchModel,
                                  RedirectAttributes redirectAttrs,
                                  SessionStatus sessionStatus,
                                  Model model) {

        ReceiveOrderDto modified = goodsSearchModel.getModifiedReceiveOrder();

        if (modified == null) {
            this.throwMessage("AOX001007");
            return "redirect:/error";
        }

        // チェックされた商品の商品詳細DTOリストを取得
        List<GoodsDetailsDto> selectedGoodsDetailsDtoList =
                        goodsSearchHelper.toGoodsDetailsDtoList(goodsSearchModel.getResultItems());

        // リストが空の場合、エラー
        if (selectedGoodsDetailsDtoList.isEmpty()) {
            throwMessage("AOX001012");
        }

        // 注商品エンティティリストに変換
        List<OrderGoodsEntity> orderGoodsEntityList =
                        goodsSearchHelper.toOrderGoodsEntityListForOrderGoodsAdd(selectedGoodsDetailsDtoList,
                                                                                 goodsSearchModel
                                                                                );

        // 作業用受注DTOへ選択した商品をセット
        goodsSearchHelper.addOrderGoodsToWorkReceiveOrderDto(goodsSearchModel, orderGoodsEntityList);

        // Modelをセッションより破棄
        sessionStatus.setComplete();
        redirectAttrs.addFlashAttribute(DetailsUpdateModel.FLASH_GOODS_SEARCH_MODEL, goodsSearchModel);

        // 商品選択画面へ遷移
        return "redirect:/order/detailsupdate/";
    }

    /**
     * 「追加」ボタン押下時の処理<br/>
     *
     * @return 商品選択画面
     */
    @PostMapping(value = "doOrderGoodsAddAjax")
    @ResponseBody
    public ResponseEntity<?> doOrderGoodsAddAjax(GoodsSearchModel goodsSearchModel,
                                                 RedirectAttributes redirectAttrs,
                                                 SessionStatus sessionStatus,
                                                 Model model) {
        List<ValidatorMessage> messageList = new ArrayList<>();
        ReceiveOrderDto modified = goodsSearchModel.getModifiedReceiveOrder();

        if (modified == null) {
            MessageUtils.getAllMessage(messageList, "AOX001007", null);
            return ResponseEntity.badRequest().body(messageList);
        }

        // チェックされた商品の商品詳細DTOリストを取得
        List<GoodsDetailsDto> selectedGoodsDetailsDtoList =
                        goodsSearchHelper.toGoodsDetailsDtoList(goodsSearchModel.getResultItems());

        // リストが空の場合、エラー
        if (selectedGoodsDetailsDtoList.isEmpty()) {
            MessageUtils.getAllMessage(messageList, "AOX001012", null);
            return ResponseEntity.badRequest().body(messageList);
        }

        // 注商品エンティティリストに変換
        List<OrderGoodsEntity> orderGoodsEntityList =
                        goodsSearchHelper.toOrderGoodsEntityListForOrderGoodsAdd(selectedGoodsDetailsDtoList,
                                                                                 goodsSearchModel
                                                                                );

        // 作業用受注DTOへ選択した商品をセット
        goodsSearchHelper.addOrderGoodsToWorkReceiveOrderDto(goodsSearchModel, orderGoodsEntityList);

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        // 商品選択画面へ遷移
        return ResponseEntity.ok(goodsSearchModel);
    }

    /**
     * 「検索」ボタン押下時の処理<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doGoodsSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/goodssearch")
    public String doGoodsSearch(@Validated(GoodsSearchGroup.class) GoodsSearchModel goodsSearchModel,
                                BindingResult error,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // エラーチェック
        if (error.hasErrors()) {
            error.getFieldErrors();
            return "order/details/goodssearch";
        }

        if (hasErrorMessage()) {
            throwMessage();
        }

        // 初期ソートと1ページをセット
        goodsSearchModel.setOrderField(DEFAULT_GOODSSEARCH_ORDER_FIELD);
        goodsSearchModel.setOrderAsc(DEFAULT_GOODSSEARCH_ORDER_ASC);
        goodsSearchModel.setPageNumber(DEFAULT_GOODSSEARCH_PNUM);

        // 検索
        search(goodsSearchModel, model);

        return "order/details/goodssearch";
    }

    /**
     * 「検索」ボタン押下時の処理<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/doGoodsSearchAjax")
    public ResponseEntity<?> doGoodsSearchAjax(@Validated(GoodsSearchGroup.class) GoodsSearchModel goodsSearchModel,
                                               BindingResult error,
                                               RedirectAttributes redirectAttributes,
                                               Model model) {

        // エラーチェック
        if (error.hasErrors()) {
            List<ValidatorMessage> mapError = MessageUtils.getMessageErrorFromBindingResult(error);
            return ResponseEntity.badRequest().body(mapError);
        }

        // 初期ソートと1ページをセット
        goodsSearchModel.setOrderField(DEFAULT_GOODSSEARCH_ORDER_FIELD);
        goodsSearchModel.setOrderAsc(DEFAULT_GOODSSEARCH_ORDER_ASC);
        goodsSearchModel.setPageNumber(DEFAULT_GOODSSEARCH_PNUM);

        // 検索
        List<GoodsSearchResultForOrderRegistDto> lstResultGoods = searchAjax(goodsSearchModel, model);

        return ResponseEntity.ok(lstResultGoods);
    }

    /**
     * 検索結果の表示切替<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/goodssearch")
    public String doDisplayChange(@Validated(DisplayChangeGroup.class) GoodsSearchModel goodsSearchModel,
                                  BindingResult error,
                                  Model model) {

        // エラーチェック
        if (error.hasErrors()) {
            return "order/details/goodssearch";
        }

        // 検検索処理
        search(goodsSearchModel, model);
        return "order/details/goodssearch";
    }

    /**
     * 「キャンセル」ボタン押下時の処理<br/>
     *
     * @return 受注詳細修正ページ
     */
    @PostMapping(value = "/", params = "doCancel")
    public String doCancel(GoodsSearchModel goodsSearchModel,
                           RedirectAttributes redirectAttrs,
                           SessionStatus sessionStatus,
                           Model model) {
        // 受注詳細修正ページへ遷移
        sessionStatus.setComplete();
        redirectAttrs.addFlashAttribute(DetailsUpdateModel.FLASH_GOODS_SEARCH_MODEL, goodsSearchModel);
        return "redirect:/order/detailsupdate/";
    }

    /**
     * 検索処理<br/>
     */
    protected void search(GoodsSearchModel goodsSearchModel, Model model) {

        // 検索条件作成
        GoodsSearchForBackDaoConditionDto goodsSearchForBackDaoConditionDto =
                        goodsSearchHelper.toGoodsSearchForBackDaoConditionDtoForGoodsSearch(goodsSearchModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(goodsSearchForBackDaoConditionDto, goodsSearchModel.getPageNumber(),
                                     goodsSearchModel.getLimit(), goodsSearchModel.getOrderField(),
                                     goodsSearchModel.isOrderAsc()
                                    );

        // 取得
        List<GoodsSearchResultForOrderRegistDto> goodsSearchResultDtoList =
                        goodsSearchResultListForOrderRegistGetService.execute(goodsSearchForBackDaoConditionDto,
                                                                              HTypeSiteType.BACK
                                                                             );

        // ページにセット
        goodsSearchHelper.toPageForSearch(
                        goodsSearchResultDtoList, goodsSearchModel, goodsSearchForBackDaoConditionDto);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(goodsSearchForBackDaoConditionDto, goodsSearchModel);

        // 件数セット
        goodsSearchModel.setTotalCount(goodsSearchForBackDaoConditionDto.getTotalCount());

        // ページに格納
        goodsSearchModel.setGoodsSearchForBackDaoConditionDto(goodsSearchForBackDaoConditionDto);
    }

    /**
     * 検索処理<br/>
     */
    protected List<GoodsSearchResultForOrderRegistDto> searchAjax(GoodsSearchModel goodsSearchModel, Model model) {

        // 検索条件作成
        GoodsSearchForBackDaoConditionDto goodsSearchForBackDaoConditionDto =
                        goodsSearchHelper.toGoodsSearchForBackDaoConditionDtoForGoodsSearchAjax(goodsSearchModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(goodsSearchForBackDaoConditionDto, goodsSearchModel.getPageNumber(), 100,
                                     "goodsGroupName", true
                                    );

        // 取得
        List<GoodsSearchResultForOrderRegistDto> goodsSearchResultDtoList =
                        goodsSearchResultListForOrderRegistGetService.execute(goodsSearchForBackDaoConditionDto,
                                                                              HTypeSiteType.BACK
                                                                             );

        // ページにセット
        goodsSearchHelper.toPageForSearch(
                        goodsSearchResultDtoList, goodsSearchModel, goodsSearchForBackDaoConditionDto);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(goodsSearchForBackDaoConditionDto, goodsSearchModel);

        // 件数セット
        goodsSearchModel.setTotalCount(goodsSearchForBackDaoConditionDto.getTotalCount());

        // ページに格納
        goodsSearchModel.setGoodsSearchForBackDaoConditionDto(goodsSearchForBackDaoConditionDto);
        return goodsSearchResultDtoList;
    }

    /**
     * カテゴリパスを保持するマップを作成<br/>
     */
    protected void setCategoryPathMap(GoodsSearchModel goodsSearchModel, Model model) {

        // 検索条件
        CategorySearchForDaoConditionDto condition =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);

        // ※カテゴリの検索ではページング制御不要のため、PageInfoは使わない（AbstractConditionDtoの継承はしない）
        condition.setOrderField("categorypath");
        condition.setOrderAsc(true);

        // 検索
        List<CategoryEntity> list = categoryListGetService.execute(condition, HTypeSiteType.BACK);

        // 結果設定
        goodsSearchHelper.setCategoryPathMap(goodsSearchModel, list);
    }

    /**
     * コンポーネント値初期化
     *
     * @param goodsSearchModel
     */
    private void initComponentValue(GoodsSearchModel goodsSearchModel) {
        goodsSearchModel.setGoodsOpenStatusItems(EnumTypeUtil.getEnumMap(HTypeOpenStatus.class));
        goodsSearchModel.setSearchCategoryIdItems(divisionMapGetService.getCategoryMapList());
        goodsSearchModel.setGoodsSaleStatusItems(EnumTypeUtil.getEnumMap(HTypeGoodsSaleStatus.class));
        goodsSearchModel.setSearchSettlementMethodItems(divisionMapGetService.getSettlementMapList());
        goodsSearchModel.setSearchDeliveryMethodItems(divisionMapGetService.getDeliveryMapList());

    }
}
