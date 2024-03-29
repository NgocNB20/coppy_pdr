package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.AbstractGoodsRegistUpdateController;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.AbstractGoodsRegistUpdateModel;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.UnitImageValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.AddGoodsGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.DeleteGoodsGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.DownGoodsGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.UpGoodsGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.UploadImageGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.UploadUnitImageGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeExcludeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatusWithNoDeleted;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitImageFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.division.DivisionMapGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupCorrelationDataCheckService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupRegistUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.relation.GoodsRelationListGetForBackService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.togetherbuy.GoodsTogetherBuyGroupListGetForBackService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.ShopGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconListGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.ImageUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.FileOperationUtility;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.ListUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 商品管理：商品登録更新（商品基本設定）コントロール
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Controller
@RequestMapping(value = "/goods/registupdate")
@SessionAttributes(value = "goodsRegistUpdateModel")
@PreAuthorize("hasAnyAuthority('GOODS:8')")
public class GoodsRegistUpdateController extends AbstractGoodsRegistUpdateController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRegistUpdateController.class);

    /**
     * 商品規格最大件数
     */
    protected static final int UNIT_COUNT_MAX = 9999;

    /**
     * 規格表示設定値最大値
     */
    protected static final int ORDERDISPLAY_MAX_VALUE = 9999;

    /**
     * 定期商品に在庫管理あり設定不可:PKG-3554-004-A-
     */
    public static final String MSGCD_PERIODIC_STOCK_MANEGIMENT_ON = "PKG-3554-025-A-";

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * 画面リロード後の自動スクロールのターゲットポジション : 基本情報
     */
    public static final String ID_SCROLL_GOODS_UNIT_ITEMS = "autoScrollGoodsUnitItems";

    /**
     * 画面リロード後の自動スクロールのターゲットポジション : 関連商品設定
     */
    public static final String ID_SCROLL_GOODS_RELATION_ITEMS = "autoScrollGoodsRelationItems";

    /**
     * 商品登録更新ページ/>
     */
    public static final String FLASH_GOODS_REGIST_UPDATES_MODEL = "goodsRegistUpdateModel";

    /**
     * 確認画面から
     */
    public static final String FLASH_FROM_CONFIRM = "fromConfirm";

    // 2023-renew No64 from here
    /* メッセージ PDR-2023RENEW-64-001-E */
    /**
     * 公開開始日時エラー<br/>
     * <code>MSGCD_DUPLICATE_DATETIME</code>
     */
    public static final String MSGCD_DUPLICATE_DATETIME = "PDR-2023RENEW-64-001-E";
    // 2023-renew No64 to here

    /**
     * 商品登録更新（商品基本設定）ページDxo<br/>
     */
    private final GoodsRegistUpdateHelper goodsRegistUpdateHelper;

    /**
     * ショップ情報取得サービス
     */
    private final ShopGetService shopInfoGetService;

    /**
     * 分類リスト取得サービス
     */
    private final DivisionMapGetService divisionMapGetService;

    /**
     * カテゴリリスト取得サービス<br/>
     */
    private final CategoryListGetService categoryListGetService;

    /**
     * アイコンリスト取得サービス<br/>
     */
    private final GoodsInformationIconListGetService goodsInformationIconListGetService;

    /**
     * 商品登録更新確認ページHelper
     */
    private final GoodsRegistUpdateConfirmHelper goodsRegistUpdateConfirmHelper;

    /**
     * 商品グループ登録更新サービス
     */
    private final GoodsGroupRegistUpdateService goodsGroupRegistUpdateService;

    /**
     * 商品グループDto相関チェックサービス
     */
    private final GoodsGroupCorrelationDataCheckService goodsGroupCorrelationService;

    /**
     * 商品グループ取得サービス<br/>
     */
    private final GoodsGroupGetService goodsGroupGetService;

    /**
     * 関連商品リスト取得サービス（バック用）<br/>
     */
    private final GoodsRelationListGetForBackService goodsRelationListGetForBackService;

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品リスト取得サービス（バック用）<br/>
     */
    private final GoodsTogetherBuyGroupListGetForBackService goodsTogetherBuyGroupListGetForBackService;
    // 2023-renew No21 to here

    /**
     * 商品Utility取得<br/>
     */
    private final GoodsUtility goodsUtility;

    // 2023-renew No64 from here
    /**
     * 変換ユーティリティ
     */
    private final ConversionUtility conversionUtility;
    // 2023-renew No64 to here

    /**
     * 規格画像の動的バリデータ
     */
    private final UnitImageValidator unitImageValidator;

    // 2023-renew No64 from here
    /**
     * コンストラクター
     *
     * @param goodsGroupGetService
     * @param goodsRelationListGetForBackService
     * @param goodsRegistUpdateHelper
     * @param shopInfoGetService
     * @param divisionMapGetService
     * @param categoryListGetService
     * @param goodsInformationIconListGetService
     * @param goodsRegistUpdateConfirmHelper
     * @param goodsGroupRegistUpdateService
     * @param goodsGroupCorrelationService
     * @param goodsUtility
     * @param unitImageValidator
     * @param goodsTogetherBuyGroupListGetForBackService
     */
    @Autowired
    public GoodsRegistUpdateController(GoodsGroupGetService goodsGroupGetService,
                                       GoodsRelationListGetForBackService goodsRelationListGetForBackService,
                                       GoodsRegistUpdateHelper goodsRegistUpdateHelper,
                                       ShopGetService shopInfoGetService,
                                       DivisionMapGetService divisionMapGetService,
                                       CategoryListGetService categoryListGetService,
                                       GoodsInformationIconListGetService goodsInformationIconListGetService,
                                       GoodsRegistUpdateConfirmHelper goodsRegistUpdateConfirmHelper,
                                       GoodsGroupRegistUpdateService goodsGroupRegistUpdateService,
                                       GoodsGroupCorrelationDataCheckService goodsGroupCorrelationService,
                                       GoodsUtility goodsUtility, ConversionUtility conversionUtility,
                                       UnitImageValidator unitImageValidator,
                                       GoodsTogetherBuyGroupListGetForBackService goodsTogetherBuyGroupListGetForBackService) {
        // 2023-renew No21 from here
        super(goodsGroupGetService, goodsRelationListGetForBackService, goodsTogetherBuyGroupListGetForBackService);
        // 2023-renew No21 to here
        this.goodsRegistUpdateHelper = goodsRegistUpdateHelper;
        this.shopInfoGetService = shopInfoGetService;
        this.divisionMapGetService = divisionMapGetService;
        this.categoryListGetService = categoryListGetService;
        this.goodsInformationIconListGetService = goodsInformationIconListGetService;
        this.goodsRegistUpdateConfirmHelper = goodsRegistUpdateConfirmHelper;
        this.goodsGroupRegistUpdateService = goodsGroupRegistUpdateService;
        this.goodsGroupCorrelationService = goodsGroupCorrelationService;
        this.goodsGroupGetService = goodsGroupGetService;
        this.goodsRelationListGetForBackService = goodsRelationListGetForBackService;
        this.goodsUtility = goodsUtility;
        this.conversionUtility = conversionUtility;
        this.unitImageValidator = unitImageValidator;
        // 2023-renew No21 from here
        this.goodsTogetherBuyGroupListGetForBackService = goodsTogetherBuyGroupListGetForBackService;
        // 2023-renew No21 to here
    }
    // 2023-renew No64 to here

    /**
     * 規格画像の動的バリデータ
     *
     * @param error
     */
    @InitBinder(value = "goodsRegistUpdateModel")
    public void initBinder(WebDataBinder error) {
        // 規格画像の動的バリデータをセット
        error.addValidators(unitImageValidator);
    }

    /**
     * 画像表示処理<br/>
     * 初期表示用メソッド
     *
     * @param from
     * @param scroll
     * @param goodGroupCode
     * @param md
     * @param recycle
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @GetMapping("")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/registupdate/index")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> from,
                              @RequestParam(required = false) Optional<String> scroll,
                              @RequestParam(required = false) Optional<String> goodGroupCode,
                              @RequestParam(required = false) Optional<String> md,
                              @RequestParam(required = false) Optional<String> recycle,
                              GoodsRegistUpdateModel goodsRegistUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (goodsRegistUpdateModel.isReloadFlag() || (from.isPresent() && from.get().equals("confirm")) || (
                        goodGroupCode.isPresent() && goodGroupCode.get()
                                                                  .equals(goodsRegistUpdateModel.getGoodsGroupCode())
                        && md.isEmpty())) {
            goodsRegistUpdateModel.setReloadFlag(false);
            return "goods/registupdate/index";
        }

        // コンポーネント値初期化
        initComponentValue(goodsRegistUpdateModel);

        // キープセッションを確認してください
        // 2023-renew No21 from here
        if (scroll.isPresent() && (scroll.get().equalsIgnoreCase("relation") || scroll.get()
                                                                                      .equalsIgnoreCase(
                                                                                                      "goodsTogetherBuyGroup"))) {
            model.addAttribute(FLASH_FROM_CONFIRM, true);
        }
        // 2023-renew No21 to here

        if (!model.containsAttribute(FLASH_FROM_CONFIRM)) {
            clearModel(GoodsRegistUpdateModel.class, goodsRegistUpdateModel, model);
            initComponentValue(goodsRegistUpdateModel);
        }

        if (from.isPresent() || (recycle.isPresent() && StringUtils.isNotEmpty(recycle.get()))) {
            goodsRegistUpdateModel.setRegistFlg(true);
        }
        // 2023-renew No21 from here
        boolean fromRelationOrTogetherBuy = false;
        if (scroll.isPresent() && ("relation".equalsIgnoreCase(scroll.get())
                                   || "goodsTogetherBuyGroup".equalsIgnoreCase(scroll.get()))) {
            fromRelationOrTogetherBuy = true;
        }
        // 2023-renew No21 to here
        if (goodGroupCode.isPresent() && md.isPresent()) {
            goodsRegistUpdateModel.setRedirectGoodsGroupCode(goodGroupCode.get());
            goodsRegistUpdateModel.setMd(md.get());
            goodsRegistUpdateModel.setRedirectRecycle(recycle.isPresent() ? recycle.get() : null);
            // 再利用の場合、不正操作チェック用項目値を初期化
            if ("1".equals(goodsRegistUpdateModel.getRedirectRecycle())) {
                goodsRegistUpdateModel.setScGoodsGroupSeq(null);
                if (goodsRegistUpdateModel.getGoodsGroupDto() != null
                    && goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity() != null) {
                    goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity().setGoodsGroupSeq(null);
                }
            }
            goodsRegistUpdateModel.setInputingFlg(false);
        }

        // 2023-renew No21 from here
        if (!fromRelationOrTogetherBuy && !model.containsAttribute(FLASH_FROM_CONFIRM)) {
            // 2023-renew No21 to here

            // 共通初期表示処理
            String errorClass =
                            super.loadPage(goodsRegistUpdateModel, goodsRegistUpdateHelper, redirectAttributes, model);

            // 共通初期表示エラー時
            if (errorClass != null) {
                return errorClass;
            }
        }

        // ショップ情報取得
        ShopEntity shopEntity = shopInfoGetService.execute();

        /***************************************************************************************************************************
         ** カテゴリー設定
         ***************************************************************************************************************************/
        // 登録カテゴリ情報の取得
        CategorySearchForDaoConditionDto categorySearchForDaoConditionDto =
                        (CategorySearchForDaoConditionDto) ApplicationContextUtility.getBean(
                                        CategorySearchForDaoConditionDto.class);
        categorySearchForDaoConditionDto.setCategoryId(null);

        // カテゴリ情報取得
        categorySearchForDaoConditionDto.setMaxHierarchical(null);
        categorySearchForDaoConditionDto.setOpenStatus(null);

        categorySearchForDaoConditionDto.setOrderField("categorypath");
        categorySearchForDaoConditionDto.setOrderAsc(true);

        // 全カテゴリリスト取得
        categorySearchForDaoConditionDto.setCategorySeqList(null);
        List<CategoryEntity> allCategoryList =
                        categoryListGetService.execute(categorySearchForDaoConditionDto, HTypeSiteType.BACK);

        /***************************************************************************************************************************
         ** 商品詳細設定
         ***************************************************************************************************************************/

        // 商品関連画面からページリロード不要
        // 2023-renew No21 from here
        if (!fromRelationOrTogetherBuy && !model.containsAttribute(FLASH_FROM_CONFIRM)) {
            goodsRegistUpdateHelper.toPageForLoad(shopEntity, goodsRegistUpdateModel);
        }
        if (!fromRelationOrTogetherBuy) {
            goodsRegistUpdateHelper.toPageForLoadCategory(allCategoryList, goodsRegistUpdateModel);
        }
        // 2023-renew No21 to here

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 自動スクロールのターゲットポジションをリセット
        if (scroll.isPresent()) {
            goodsRegistUpdateModel.setTargetAutoScrollTagId(ID_SCROLL_GOODS_RELATION_ITEMS);
        } else {
            goodsRegistUpdateModel.setTargetAutoScrollTagId("");
        }

        return "goods/registupdate/index";
    }

    /**
     * 初期表示用メソッド(Ajax用)
     *
     * @param from
     * @param goodsRegistUpdateModel
     * @param goodsRegistUpdateRelationsearchModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @PostMapping("loadAjax")
    @ResponseBody
    public ResponseEntity<?> doLoadIndexAjax(@RequestParam(required = false) Optional<String> from,
                                             GoodsRegistUpdateModel goodsRegistUpdateModel,
                                             @RequestBody
                                             GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel,
                                             RedirectAttributes redirectAttributes,
                                             Model model) {
        if (!ObjectUtils.isEmpty(goodsRegistUpdateRelationsearchModel)) {
            goodsRegistUpdateModel.setRedirectGoodsRelationEntityList(
                            goodsRegistUpdateRelationsearchModel.getRedirectGoodsRelationEntityList());
            goodsRegistUpdateModel.setTmpGoodsRelationEntityList(
                            goodsRegistUpdateRelationsearchModel.getTmpGoodsRelationEntityList());
            goodsRegistUpdateModel.setGoodsGroupDto(goodsRegistUpdateRelationsearchModel.getGoodsGroupDto());
            goodsRegistUpdateModel.setGoodsRelationEntityList(
                            goodsRegistUpdateRelationsearchModel.getGoodsRelationEntityList());
        }
        // 商品関連画面からページリロード不要
        if (ObjectUtils.isNotEmpty(goodsRegistUpdateRelationsearchModel)) {
            goodsRegistUpdateHelper.toPageForLoadRelation(goodsRegistUpdateModel);
        }
        return ResponseEntity.ok("Success");
    }

    // 2023-renew No21 from here
    /**
     * 初期表示用メソッド(Ajax用)
     *
     * @param from
     * @param goodsRegistUpdateModel
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @PostMapping("loadGoodsTogetherBuyGroupAjax")
    @ResponseBody
    public ResponseEntity<?> doLoadIndexGoodsTogetherBuyGroupAjax(@RequestParam(required = false) Optional<String> from,
                                                                  GoodsRegistUpdateModel goodsRegistUpdateModel,
                                                                  @RequestBody
                                                                  GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel,
                                                                  RedirectAttributes redirectAttributes,
                                                                  Model model) {
        if (!ObjectUtils.isEmpty(goodsRegistUpdateTogetherBuyGroupSearchModel)) {
            goodsRegistUpdateModel.setRedirectGoodsTogetherBuyGroupEntityList(
                            goodsRegistUpdateTogetherBuyGroupSearchModel.getRedirectGoodsTogetherBuyGroupEntityList());
            goodsRegistUpdateModel.setTmpGoodsTogetherBuyGroupEntityList(
                            goodsRegistUpdateTogetherBuyGroupSearchModel.getTmpGoodsTogetherBuyGroupEntityList());
            goodsRegistUpdateModel.setGoodsGroupDto(goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsGroupDto());
            goodsRegistUpdateModel.setGoodsTogetherBuyGroupEntityList(
                            goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsTogetherBuyGroupEntityList());
        }
        // よく一緒に購入される商品画面からページリロード不要
        if (ObjectUtils.isNotEmpty(goodsRegistUpdateTogetherBuyGroupSearchModel)) {
            goodsRegistUpdateHelper.toPageForLoadTogetherBuy(goodsRegistUpdateModel);
        }
        return ResponseEntity.ok("Success");
    }
    // 2023-renew No21 to here

    /**
     * 確認するイベント<br/>
     *
     * @param goodsRegistUpdateModel
     * @param error
     * @param redirectAttributes
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/registupdate/index")
    public String doConfirm(@Validated(ConfirmGroup.class) GoodsRegistUpdateModel goodsRegistUpdateModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        // 自動スクロールのターゲットポジションをリセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId("");

        // チェックボックスにデフォルト値を設定
        initValueFlag(goodsRegistUpdateModel);

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (error.hasErrors()) {
            return "goods/registupdate/index";
        }

        // 不整合処理チェック
        checkPageGoodsGroupEntity(goodsRegistUpdateModel, redirectAttributes, model);
        checkData(goodsRegistUpdateModel, redirectAttributes, model);
        checkDataUnit(goodsRegistUpdateModel, redirectAttributes, model);
        checkDataStock(goodsRegistUpdateModel, redirectAttributes, model);
        // 2023-renew No64 from here
        checkDateTimeDuplicate(goodsRegistUpdateModel);
        //2023-renew No64 to here

        goodsRegistUpdateHelper.toPageForNext(goodsRegistUpdateModel);
        // 共通商品情報から個別商品情報へのデータ反映
        goodsRegistUpdateHelper.toGoodsListFromCommonGoods(goodsRegistUpdateModel);
        return "redirect:/goods/registupdate/confirm";
    }

    /**
     * アクション実行前処理
     *
     * @param abstractGoodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     */
    public String preDoAction(AbstractGoodsRegistUpdateModel abstractGoodsRegistUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        // 不正操作チェック
        return checkIllegalOperation(abstractGoodsRegistUpdateModel, redirectAttributes, model);
    }

    /**
     * 入力情報チェック<br/>
     * Validatorで対応できないもの
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     */
    private void checkData(GoodsRegistUpdateModel goodsRegistUpdateModel,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        // 不整合処理チェック
        checkPageGoodsGroupEntity(goodsRegistUpdateModel, redirectAttributes, model);
        // 更新時の新着日時
        if (!goodsRegistUpdateModel.isRegist() && goodsRegistUpdateModel.getWhatsnewDate() == null) {
            addErrorMessage("AGG000207");
        }

        // 2023-renew No64 from here
        if (goodsRegistUpdateModel.getGoodsGroupName1() == null && goodsRegistUpdateModel.getGoodsGroupName2() == null) {
            addErrorMessage("SGP001005");
        }
        // 2023-renew No64 to here

        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            throwMessage();
        }
    }

    /***************************************************************************************************************************
     ** 商品規格設定
     ***************************************************************************************************************************/
    /**
     * 商品規格追加処理<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doAddGoods")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/registupdate/index")
    public String doAddGoods(@Validated(AddGoodsGroup.class) GoodsRegistUpdateModel goodsRegistUpdateModel,
                             BindingResult error,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId(ID_SCROLL_GOODS_UNIT_ITEMS);

        // goodsGroupDtoのバインディングエラーは一旦無視する（HTMLで在庫DTOのhidden項目を使っているため）
        // それ以外の項目は、自画面に遷移し入力エラーを表示する
        if (error.hasErrors()) {
            for (FieldError fieldError : error.getFieldErrors()) {
                if (!fieldError.getField().contains("goodsGroupDto")) {
                    return "goods/registupdate/index";
                }
            }
        }

        // 商品規格登録数チェック
        List<GoodsRegistUpdateUnitItem> unitItems = goodsRegistUpdateModel.getUnitItems();
        if (unitItems != null && unitItems.size() >= UNIT_COUNT_MAX) {
            // 商品規格登録件数が最大値（9999）以上の場合、規格追加不可エラー
            throwMessage("AGG000723", new Object[] {UNIT_COUNT_MAX});
        }

        // 規格表示順チェック
        // 商品規格リスト取得
        List<GoodsDto> goodsDtoList = goodsRegistUpdateModel.getGoodsGroupDto().getGoodsDtoList();
        if (goodsDtoList != null && !goodsDtoList.isEmpty()) {
            // 規格表示順設定値チェック
            // 商品規格最後尾のDto取得
            GoodsDto lastGoodsDto = goodsDtoList.get(goodsDtoList.size() - 1);
            if (lastGoodsDto.getGoodsEntity().getOrderDisplay().intValue() >= ORDERDISPLAY_MAX_VALUE) {
                // 規格表示順設定値が最大値（9999）以上の場合、規格追加不可エラー
                throwMessage("AGG000724", new Object[] {ORDERDISPLAY_MAX_VALUE + 1});
            }
        }

        // 変更情報の保存(同一画面内遷移)
        goodsRegistUpdateHelper.toPageForSame(goodsRegistUpdateModel);

        // 共通商品情報から個別商品情報へのデータ反映
        goodsRegistUpdateHelper.toGoodsListFromCommonGoods(goodsRegistUpdateModel);

        // 商品規格追加
        goodsRegistUpdateHelper.toPageForAddGoods(goodsRegistUpdateModel);

        // 画面再表示
        goodsRegistUpdateHelper.toPageForLoadUnit(goodsRegistUpdateModel);

        return "goods/registupdate/index";
    }

    /**
     * 商品規格削除処理<br/>
     *
     * @param goodsRegistUpdateModel
     * @param error
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doDeleteGoods")
    public String doDeleteGoods(@Validated(DeleteGoodsGroup.class) GoodsRegistUpdateModel goodsRegistUpdateModel,
                                BindingResult error,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId(ID_SCROLL_GOODS_UNIT_ITEMS);

        if (error.hasErrors()) {
            return "goods/registupdate/index";
        }

        // 変更情報の保存(同一画面内遷移)
        goodsRegistUpdateHelper.toPageForSame(goodsRegistUpdateModel);

        // 共通商品情報から個別商品情報へのデータ反映
        goodsRegistUpdateHelper.toGoodsListFromCommonGoods(goodsRegistUpdateModel);

        // 商品規格削除
        goodsRegistUpdateHelper.toPageForDeleteGoods(goodsRegistUpdateModel);

        // 画面再表示
        goodsRegistUpdateHelper.toPageForLoadUnit(goodsRegistUpdateModel);

        return "goods/registupdate/index";
    }

    /**
     * 商品表示順変更(上)<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doUpGoods")
    public String doUpGoods(@Validated(UpGoodsGroup.class) GoodsRegistUpdateModel goodsRegistUpdateModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId(ID_SCROLL_GOODS_UNIT_ITEMS);

        if (error.hasErrors()) {
            return "goods/registupdate/index";
        }

        // 変更情報の保存(同一画面内遷移)
        goodsRegistUpdateHelper.toPageForSame(goodsRegistUpdateModel);

        // 共通商品情報から個別商品情報へのデータ反映
        goodsRegistUpdateHelper.toGoodsListFromCommonGoods(goodsRegistUpdateModel);

        // 商品表示順変更(上)
        goodsRegistUpdateHelper.toPageForUpGoods(goodsRegistUpdateModel);

        // 画面再表示
        goodsRegistUpdateHelper.toPageForLoadUnit(goodsRegistUpdateModel);

        return "goods/registupdate/index";
    }

    /**
     * 商品表示順変更(下)<br/>
     *
     * @param goodsRegistUpdateModel
     * @param error
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doDownGoods")
    public String doDownGoods(@Validated(DownGoodsGroup.class) GoodsRegistUpdateModel goodsRegistUpdateModel,
                              BindingResult error,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId(ID_SCROLL_GOODS_UNIT_ITEMS);

        if (error.hasErrors()) {
            return "goods/registupdate/index";
        }

        // 変更情報の保存(同一画面内遷移)
        goodsRegistUpdateHelper.toPageForSame(goodsRegistUpdateModel);

        // 共通商品情報から個別商品情報へのデータ反映
        goodsRegistUpdateHelper.toGoodsListFromCommonGoods(goodsRegistUpdateModel);

        // 商品表示順変更(下)
        goodsRegistUpdateHelper.toPageForDownGoods(goodsRegistUpdateModel);

        // 画面再表示
        goodsRegistUpdateHelper.toPageForLoadUnit(goodsRegistUpdateModel);

        return "goods/registupdate/index";
    }

    /**
     * 入力情報チェック<br/>
     * Validatorで対応できないもの
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     */
    private void checkDataUnit(GoodsRegistUpdateModel goodsRegistUpdateModel,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        // 不整合処理チェック
        checkPageGoodsGroupEntity(goodsRegistUpdateModel, redirectAttributes, model);

        // 規格設定チェック
        checkGoodsUnit(goodsRegistUpdateModel);

        // 商品コード、カタログコードの重複チェック
        checkCodeDuplication(goodsRegistUpdateModel);

        // 規格管理ありの場合、規格名と規格画像のチェックを実施する
        if (HTypeUnitManagementFlag.ON.equals(EnumTypeUtil.getEnumFromValue(HTypeUnitManagementFlag.class,
                                                                            goodsRegistUpdateModel.getUnitManagementFlag()
                                                                           ))) {
            // 規格名重複チェック
            checkDuplicateUnitvalue(goodsRegistUpdateModel);
        }

        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            throwMessage();
        }
    }

    /**
     * 規格名重複チェック
     *
     * @param goodsRegistUpdateModel
     */
    private void checkDuplicateUnitvalue(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        Set<String> unitvalues = new HashSet<>();
        for (GoodsRegistUpdateUnitItem item : goodsRegistUpdateModel.getUnitItems()) {
            // 規格1未入力の場合はチェック不要
            // ※規格１未入力のエラーメッセージが出力されるため
            if (item.getUnitValue1() != null) {
                // 規格2は入力無しのケース有のため、nullの場合は空文字として扱う
                String unitvalue =
                                item.getUnitValue1() + (item.getUnitValue2() != null ? " " + item.getUnitValue2() : "");
                if (unitvalues.contains(unitvalue)) {
                    addErrorMessage("AGG000728", new Object[] {item.getUnitDspNo()});
                } else {
                    unitvalues.add(unitvalue);
                }
            }
        }
    }

    /**
     * 規格設定チェック<br/>
     *
     * @param goodsRegistUpdateModel
     */
    private void checkGoodsUnit(GoodsRegistUpdateModel goodsRegistUpdateModel) {

        // PDR Migrate Customization from here
        // PDR Migrate Customization to here

        if (HTypeUnitManagementFlag.ON.getValue().equals(goodsRegistUpdateModel.getUnitManagementFlag())) {
            // 規格１表示名
            if (goodsRegistUpdateModel.getUnitTitle1() == null) {
                addErrorMessage("AGG000709");
            }
            // 規格情報リスト
            if (goodsRegistUpdateModel.getUnitItems() == null) {
                return;
            }
            for (GoodsRegistUpdateUnitItem unitPageItem : goodsRegistUpdateModel.getUnitItems()) {
                if (unitPageItem.getUnitValue1() == null) {
                    addErrorMessage("AGG000710", new Object[] {unitPageItem.getUnitDspNo()});
                }
                if (goodsRegistUpdateModel.getUnitTitle2() != null && unitPageItem.getUnitValue2() == null) {
                    addErrorMessage("AGG000711", new Object[] {unitPageItem.getUnitDspNo()});
                }
                if (goodsRegistUpdateModel.getUnitTitle2() == null && unitPageItem.getUnitValue2() != null) {
                    addErrorMessage("AGG000712", new Object[] {unitPageItem.getUnitDspNo()});
                }
                // PDR Migrate Customization from here
                // PDR Migrate Customization to here
            }
        } else {
            // 規格１表示名
            if (goodsRegistUpdateModel.getUnitTitle1() != null) {
                addErrorMessage("AGG000713");
            }
            // 規格２表示名
            if (goodsRegistUpdateModel.getUnitTitle2() != null) {
                addErrorMessage("AGG000714");
            }
            // 規格情報リスト
            if (goodsRegistUpdateModel.getUnitItems() == null) {
                return;
            }
            boolean existUnit = false;
            boolean existUnitFail = false;
            // PDR Migrate Customization from here
            Integer i = 0;
            for (GoodsRegistUpdateUnitItem unitPageItem : goodsRegistUpdateModel.getUnitItems()) {
                if (!existUnitFail && !HTypeGoodsSaleStatus.DELETED.getValue()
                                                                   .equals(unitPageItem.getGoodsSaleStatusPC())) {
                    if (existUnit) {
                        if (!HTypeEmotionPriceType.EMOTIONPRICE.equals(goodsRegistUpdateModel.getGoodsGroupDto()
                                                                                             .getGoodsDtoList()
                                                                                             .get(i)
                                                                                             .getGoodsEntity()
                                                                                             .getEmotionPriceType())) {
                            addErrorMessage("AGG000721");
                            existUnitFail = true;
                            i += 1;
                        }
                    } else {
                        existUnit = true;
                        i += 1;
                    }
                    // PDR Migrate Customization to here
                }
                if (unitPageItem.getUnitValue1() != null) {
                    addErrorMessage("AGG000715", new Object[] {unitPageItem.getUnitDspNo()});
                }
                if (unitPageItem.getUnitValue2() != null) {
                    addErrorMessage("AGG000716", new Object[] {unitPageItem.getUnitDspNo()});
                }
                // PDR Migrate Customization from here
                // PDR Migrate Customization to here
            }
        }
    }

    /**
     * 商品コード、カタログコードの重複チェック<br/>
     *
     * @param goodsRegistUpdateModel
     */
    private void checkCodeDuplication(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        // 商品コード、カタログコードの重複チェック
        Set<String> goodsCodeSet = new HashSet<>();
        for (GoodsRegistUpdateUnitItem unitPageItem : goodsRegistUpdateModel.getUnitItems()) {
            if (goodsCodeSet.contains(unitPageItem.getGoodsCode())) {
                addErrorMessage("AGG000717", new Object[] {unitPageItem.getUnitDspNo()});
            } else {
                goodsCodeSet.add(unitPageItem.getGoodsCode());
            }
        }
    }

    /***************************************************************************************************************************
     ** 商品画像設定
     ***************************************************************************************************************************/
    // *****************************************************************************
    // 新HIT-MALL：複数画像アップロード
    // *****************************************************************************

    /**
     * 複数商品グループ詳細画像アップロードイベント<br/>
     *
     * @param goodsRegistUpdateModel
     * @param error
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doUploadMultipleDetailsImage")
    public String doUploadMultipleDetailsImage(
                    @Validated(UploadImageGroup.class) GoodsRegistUpdateModel goodsRegistUpdateModel,
                    BindingResult error,
                    Model model) {

        if (error.hasErrors()) {
            return "goods/registupdate/index";
        }

        MultipartFile[] uploadedGoodsImages = goodsRegistUpdateModel.getUploadedGoodsImages();

        // 画像操作Utility取得
        ImageUtility imageUtility = ApplicationContextUtility.getBean(ImageUtility.class);

        // 一時画像ファイルディレクトリ 物理パス
        String realPath = imageUtility.getRealTempPath() + "/";

        // 一時画像ファイルディレクトリ 一時urlパス
        String urlPath = imageUtility.getTempPath() + "/";

        // アップロードされた全ての画像を処理
        for (MultipartFile uploadedImageFile : uploadedGoodsImages) {
            Integer selectGn = goodsRegistUpdateHelper.getLastImageIndex(goodsRegistUpdateModel.getGoodsImageItems());

            // 対象の商品グループ詳細画像アイテムを取得
            GoodsRegistUpdateImageItem item = getSelectDetailsImageItem(selectGn, goodsRegistUpdateModel);
            if (item == null) {
                // 異常なので処理なし
                return "goods/registupdate/index";
            }

            // 対象の商品グループ詳細画像ファイル取得
            if (uploadedImageFile == null) {
                // 画像取得失敗エラー
                throwMessage("AGG000601");
            } else {
                item.setImageFile(uploadedImageFile);
            }

            // 保存するファイル名を作成
            String saveFileName = goodsUtility.createImageFileName(goodsRegistUpdateModel.getGoodsGroupCode(), selectGn,
                                                                   null
                                                                  );

            // 一時ファイル名と公開ファイル名を取得
            String tmpFileName = imageUtility.createTempImageFileNameExtension(uploadedImageFile.getOriginalFilename(),
                                                                               saveFileName
                                                                              );
            String realFileName = imageUtility.createImageFileNameExtension(uploadedImageFile.getOriginalFilename(),
                                                                            saveFileName
                                                                           );

            // ファイル取得
            MultipartFile selectedRealFile = getSelectedDetailsImageFile(item, realPath, tmpFileName);
            if (selectedRealFile == null) {
                // 画像取得失敗エラー
                throwMessage("AGG000601");
            }

            // ページにセット
            goodsRegistUpdateHelper.makeGoodsGroupImageRegistUpdateInfo(
                            goodsRegistUpdateModel, selectGn, realPath + tmpFileName, tmpFileName, realFileName, false);

            // 一時ファイルの情報を反映
            item.setImagePath(urlPath + tmpFileName);
        }

        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId("autoScrollGoodsImages");
        goodsRegistUpdateModel.setReloadFlag(true);
        return "redirect:/goods/registupdate/?goodGroupCode=" + goodsRegistUpdateModel.getGoodsGroupCode();
    }

    /**
     * 複数商品グループ詳細画像アップロードイベント<br/>
     *
     * @param goodsRegistUpdateModel
     * @param error
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doUploadUnitImage")
    public String doUploadUnitImage(
                    @Validated(UploadUnitImageGroup.class) GoodsRegistUpdateModel goodsRegistUpdateModel,
                    BindingResult error,
                    Model model) {

        if (error.hasErrors()) {
            return "goods/registupdate/index";
        }

        if (goodsRegistUpdateModel.getUnitImagesItemNo() == null) {
            return "goods/registupdate/index";
        }

        MultipartFile uploadedImageFile = goodsRegistUpdateModel.getUnitImagesItems()
                                                                .get(goodsRegistUpdateModel.getUnitImagesItemNo())
                                                                .getUnitImage();

        // 画像操作Utility取得
        ImageUtility imageUtility = ApplicationContextUtility.getBean(ImageUtility.class);

        // 一時画像ファイルディレクトリ 物理パス
        String realPath = imageUtility.getRealTempPath() + "/";

        // 一時画像ファイルディレクトリ 一時urlパス
        String urlPath = imageUtility.getTempPath() + "/";

        // 規格画像共通名
        String unitImageCommonName = "_u_";

        // アップロードされた全ての画像を処理

        Integer selectGn = goodsRegistUpdateHelper.getLastImageIndex(goodsRegistUpdateModel.getGoodsImageItems());

        // 対象の商品グループ詳細画像アイテムを取得
        GoodsRegistUpdateUnitImage item =
                        goodsRegistUpdateModel.getUnitImagesItems().get(goodsRegistUpdateModel.getUnitImagesItemNo());
        if (item == null) {
            // 異常なので処理なし
            return "goods/registupdate/index";
        }
        item.setGoodsCode(goodsRegistUpdateModel.getUnitItems()
                                                .get(goodsRegistUpdateModel.getUnitImagesItemNo())
                                                .getGoodsCode());

        // 対象の商品グループ詳細画像ファイル取得
        if (uploadedImageFile == null) {
            // 画像取得失敗エラー
            throwMessage("AGG000601");
        } else {
            item.setUnitImage(uploadedImageFile);
        }

        // 保存するファイル名を作成
        String saveFileName = goodsUtility.createUnitImageFileName(
                        goodsRegistUpdateModel.getGoodsGroupCode() + unitImageCommonName,
                        goodsRegistUpdateModel.getUnitItems()
                                              .get(goodsRegistUpdateModel.getUnitImagesItemNo())
                                              .getGoodsCode(), null
                                                                  );

        // 一時ファイル名と公開ファイル名を取得
        String tmpFileName = imageUtility.createTempImageFileNameExtension(uploadedImageFile.getOriginalFilename(),
                                                                           saveFileName
                                                                          );
        String realFileName = imageUtility.createImageFileNameExtension(uploadedImageFile.getOriginalFilename(),
                                                                        saveFileName
                                                                       );

        // ファイル取得
        MultipartFile selectedRealFile = getSelectedUnitImageFile(item, realPath, tmpFileName);
        if (selectedRealFile == null) {
            // 画像取得失敗エラー
            throwMessage("AGG000601");
        }

        // 一時ファイルの情報を反映
        item.setUrlImagePath(urlPath + tmpFileName);
        item.setRealImagePath(realPath + tmpFileName);

        // 2023-renew No76 from here
        // 規格画像有無を更新
        item.setUnitImageFlag(HTypeUnitImageFlag.ON);
        // 2023-renew No76 to here

        item.setUnitImageName(goodsRegistUpdateModel.getGoodsGroupCode() + "/" + realFileName);

        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId("autoScrollGoodsUnitImages");
        goodsRegistUpdateModel.setReloadFlag(true);
        return "redirect:/goods/registupdate/?goodGroupCode=" + goodsRegistUpdateModel.getGoodsGroupCode();
    }

    /**
     * 商品画像削除イベント<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDeleteDetailsImage")
    public String doDeleteDetailsImage(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        Integer selectImageNo = goodsRegistUpdateModel.getSelectImageNo();

        // 対象の商品グループ詳細画像アイテムを取得
        GoodsRegistUpdateImageItem item = getSelectDetailsImageItem(selectImageNo, goodsRegistUpdateModel);

        // ページにセット
        goodsRegistUpdateHelper.makeGoodsGroupImageRegistUpdateInfo(
                        goodsRegistUpdateModel, selectImageNo, null, null, null, true);

        // 一時ファイルの情報を反映
        item.setImagePath(null);

        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId("autoScrollGoodsImages");

        return "goods/registupdate/index";
    }

    /**
     * 規格画像削除イベント<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDeleteUnitImage")
    public String doDeleteUnitImage(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        Integer selectUnitImageNo = goodsRegistUpdateModel.getSelectUnitImageNo();

        // 対象の規格画像アイテムを取得
        GoodsRegistUpdateUnitImage item = goodsRegistUpdateModel.getUnitImagesItems().get(selectUnitImageNo);

        // ページにセット
        //        goodsRegistUpdateHelper.makeGoodsGroupImageRegistUpdateInfo(goodsRegistUpdateModel, selectUnitImageNo, null, null, null, true);

        // 一時ファイルの情報を反映
        item.setUrlImagePath(null);

        // 2023-renew No76 from here
        // 規格画像有無を更新
        item.setUnitImageFlag(HTypeUnitImageFlag.OFF);
        // 2023-renew No76 to here

        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId("autoScrollGoodsUnitImages");

        return "goods/registupdate/index";
    }

    /**
     * 処理対象の商品グループ詳細画像アイテム取得<br/>
     *
     * @param selectImageNo          対象の画像連番
     * @param goodsRegistUpdateModel
     * @return 商品グループ詳細画像アイテム
     */
    private GoodsRegistUpdateImageItem getSelectDetailsImageItem(Integer selectImageNo,
                                                                 GoodsRegistUpdateModel goodsRegistUpdateModel) {

        for (GoodsRegistUpdateImageItem item : goodsRegistUpdateModel.getGoodsImageItems()) {
            if (selectImageNo.equals(item.getImageNo())) {
                return item;
            }
        }
        return null;
    }

    /***************************************************************************************************************************
     ** 在庫設定
     ***************************************************************************************************************************/
    /**
     * 入力情報チェック<br/>
     * Validatorで対応できないもの
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     */
    private void checkDataStock(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        // 不整合処理チェック
        checkPageGoodsGroupEntity(goodsRegistUpdateModel, redirectAttributes, model);

        HTypeStockManagementFlag stockManagementFlag = EnumTypeUtil.getEnumFromValue(HTypeStockManagementFlag.class,
                                                                                     goodsRegistUpdateModel.getStockManagementFlag()
                                                                                    );

        // 在庫設定
        // ※管理フラグがONの場合、整合性チェックを実施
        // ※管理フラグがOFFの場合、在庫設定値を全て0に置き換えて登録する
        for (int i = 0;
             goodsRegistUpdateModel.getStockItems() != null && i < goodsRegistUpdateModel.getStockItems().size(); i++) {
            GoodsRegistUpdateStockItem stockPageItem = goodsRegistUpdateModel.getStockItems().get(i);
            if (HTypeStockManagementFlag.ON == stockManagementFlag) {
                // 残少表示在庫数
                if (stockPageItem.getRemainderFewStock() == null) {
                    addErrorMessage("AGG000801", new Object[] {stockPageItem.getStockDspNo()});
                }
                // 発注点在庫数
                if (stockPageItem.getOrderPointStock() == null) {
                    addErrorMessage("AGG000802", new Object[] {stockPageItem.getStockDspNo()});
                }
                // 安全在庫数
                if (stockPageItem.getSafetyStock() == null) {
                    addErrorMessage("AGG000803", new Object[] {stockPageItem.getStockDspNo()});
                }
                // 入庫数
                if (stockPageItem.getSupplementCount() == null) {
                    addErrorMessage("GOODS-STOCK-001-", new Object[] {stockPageItem.getStockDspNo()});
                }
            } else {

                // OFFのため値を0に置き換える

                // 残少表示在庫数
                stockPageItem.setRemainderFewStock("0");
                // 発注点在庫数
                stockPageItem.setOrderPointStock("0");
                // 安全在庫数
                stockPageItem.setSafetyStock("0");
                // 入庫数
                stockPageItem.setSupplementCount("0");
            }
        }

        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            throwMessage();
        }
    }

    //2023-renew No64 from here

    /**
     *  入力情報チェック<br/>
     *  Validatorで対応できないもの
     *
     * @param goodsRegistUpdateModel 商品管理：商品登録更新ページ
     */
    private void checkDateTimeDuplicate(GoodsRegistUpdateModel goodsRegistUpdateModel) {

        Timestamp goodsGroupName1OpenStart = conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsGroupName1OpenStartDate(),
                        goodsRegistUpdateModel.getGoodsGroupName1OpenStartTime(), ConversionUtility.DEFAULT_START_TIME);
        Timestamp goodsGroupName2OpenStart = conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsGroupName2OpenStartDate(),
                        goodsRegistUpdateModel.getGoodsGroupName2OpenStartTime(), ConversionUtility.DEFAULT_START_TIME);
        // 商品名
        if (goodsGroupName1OpenStart != null && goodsGroupName2OpenStart != null &&
            goodsGroupName1OpenStart.equals(goodsGroupName2OpenStart)) {
            // 商品グループ名重複エラーをメッセージに追加。（エラーは投げない。）
            addErrorMessage(MSGCD_DUPLICATE_DATETIME,
                            new Object[] {"商品名", goodsRegistUpdateModel.getGoodsGroupCode(), null});
        }

        Timestamp goodsNote1OpenStart = conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote1OpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote1OpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME);
        Timestamp goodsNote1SubOpenStart = conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote1SubOpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote1SubOpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME);
        // 商品概要
        if (goodsNote1OpenStart != null && goodsNote1SubOpenStart != null &&
            goodsNote1OpenStart.equals(goodsNote1SubOpenStart))  {
            // 商品説明重複エラーをメッセージに追加。（エラーは投げない。）
            addErrorMessage(MSGCD_DUPLICATE_DATETIME,
                            new Object[] {"商品概要", goodsRegistUpdateModel.getGoodsGroupCode(), null});
        }

        Timestamp goodsNote2OpenStart = conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote2OpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote2OpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME);
        Timestamp goodsNote2SubOpenStart = conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote2SubOpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote2SubOpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME);
        // 特徴
        if (goodsNote2OpenStart != null && goodsNote2SubOpenStart != null &&
            goodsNote2OpenStart.equals(goodsNote2SubOpenStart)) {
            // 商品説明重複エラーをメッセージに追加。（エラーは投げない。）
            addErrorMessage(MSGCD_DUPLICATE_DATETIME,
                            new Object[] {"特徴", goodsRegistUpdateModel.getGoodsGroupCode(), null});
        }

        Timestamp goodsNote4OpenStart = conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote4OpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote4OpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME);
        Timestamp goodsNote4SubOpenStart = conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote4SubOpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote4SubOpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME);
        // 注意事項
        if (goodsNote4OpenStart != null && goodsNote4SubOpenStart != null &&
            goodsNote4OpenStart.equals(goodsNote4SubOpenStart)) {
            // 商品説明重複エラーをメッセージに追加。（エラーは投げない。）
            addErrorMessage(MSGCD_DUPLICATE_DATETIME,
                            new Object[] {"注意事項", goodsRegistUpdateModel.getGoodsGroupCode(), null});
        }

        Timestamp goodsNote10OpenStart = conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote10OpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote10OpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME);
        Timestamp goodsNote10SubOpenStart = conversionUtility.toTimeStampWithDefaultHms(
                        goodsRegistUpdateModel.getGoodsNote10SubOpenStartTimeDate(),
                        goodsRegistUpdateModel.getGoodsNote10SubOpenStartTimeTime(), ConversionUtility.DEFAULT_START_TIME);
        // シリーズPRコメント
        if (goodsNote10OpenStart != null && goodsNote10SubOpenStart != null &&
            goodsNote10OpenStart.equals(goodsNote10SubOpenStart)) {
            // 商品説明重複エラーをメッセージに追加。（エラーは投げない。）
            addErrorMessage(MSGCD_DUPLICATE_DATETIME,
                            new Object[] {"シリーズPRコメント", goodsRegistUpdateModel.getGoodsGroupCode(), null});
        }

        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            throwMessage();
        }
    }
    //2023-renew No64 to here

    /***************************************************************************************************************************
     ** 関連商品設定
     ***************************************************************************************************************************/
    /**
     * 関連商品削除処理<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @PostMapping(value = "/", params = "doDeleteRelationGoods")
    public String doDeleteRelationGoods(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 関連商品削除
        goodsRegistUpdateHelper.doDeleteRelationGoods(goodsRegistUpdateModel);
        // 画面再表示
        goodsRegistUpdateHelper.toPageForLoadRelation(goodsRegistUpdateModel);
        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId(ID_SCROLL_GOODS_RELATION_ITEMS);

        return "goods/registupdate/index";
    }

    /**
     * 関連商品表示順変更(上)<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @PostMapping(value = "/", params = "doUpRelationGoods")
    public String doUpRelationGoods(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        // 関連商品表示順変更(上)
        goodsRegistUpdateHelper.toPageForUpRelationGoods(goodsRegistUpdateModel);
        // 画面再表示
        goodsRegistUpdateHelper.toPageForLoadRelation(goodsRegistUpdateModel);
        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId(ID_SCROLL_GOODS_RELATION_ITEMS);
        return "goods/registupdate/index";
    }

    /**
     * 関連商品表示順変更(下)<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @PostMapping(value = "/", params = "doDownRelationGoods")
    public String doDownRelationGoods(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        // 商品表示順変更(下)
        goodsRegistUpdateHelper.toPageForDownRelationGoods(goodsRegistUpdateModel);
        // 画面再表示
        goodsRegistUpdateHelper.toPageForLoadRelation(goodsRegistUpdateModel);
        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId(ID_SCROLL_GOODS_RELATION_ITEMS);

        return "goods/registupdate/index";
    }

    /**
     * 関連商品追加イベント<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return 関連商品検索画面
     */
    @PostMapping(value = "/", params = "doAddGoodsRelation")
    public String doAddGoodsRelation(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        goodsRegistUpdateModel.setRedirectGoodsRelationEntityList(
                        goodsRegistUpdateModel.getTmpGoodsRelationEntityList());

        redirectAttributes.addFlashAttribute(FLASH_GOODS_REGIST_UPDATES_MODEL, goodsRegistUpdateModel);
        return "redirect:/goods/registupdate/relationsearch/";
    }

    /**
     * 関連商品追加イベント（Ajax用）<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return 関連商品検索ダイアログ
     */
    @PostMapping(value = "/doAddGoodRelationAjax")
    @ResponseBody
    public ResponseEntity<?> doAddGoodsRelationAjax(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                                    RedirectAttributes redirectAttributes,
                                                    Model model) {
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return ResponseEntity.badRequest().body(check);
        }

        goodsRegistUpdateModel.setRedirectGoodsRelationEntityList(
                        goodsRegistUpdateModel.getTmpGoodsRelationEntityList());

        return ResponseEntity.ok(goodsRegistUpdateModel);
    }

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品削除処理<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @PostMapping(value = "/", params = "doDeleteTogetherBuyGoods")
    public String doDeleteGoodsTogetherBuyGroup(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                                RedirectAttributes redirectAttributes,
                                                Model model) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // よく一緒に購入される商品削除
        goodsRegistUpdateHelper.doDeleteGoodsTogetherGroup(goodsRegistUpdateModel);
        // 画面再表示
        goodsRegistUpdateHelper.toPageForLoadTogetherBuy(goodsRegistUpdateModel);
        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId(ID_SCROLL_GOODS_RELATION_ITEMS);

        return "goods/registupdate/index";
    }

    /**
     * よく一緒に購入される商品表示順変更(上)<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @PostMapping(value = "/", params = "doUpTogetherBuyGoods")
    public String doUpTogetherBuyGoods(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // よく一緒に購入される商品表示順変更(上)
        goodsRegistUpdateHelper.toPageForUpGoodsTogetherBuyGroup(goodsRegistUpdateModel);
        // 画面再表示
        goodsRegistUpdateHelper.toPageForLoadTogetherBuy(goodsRegistUpdateModel);

        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId(ID_SCROLL_GOODS_RELATION_ITEMS);
        return "goods/registupdate/index";
    }

    /**
     * よく一緒に購入される商品表示順変更(下)<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @PostMapping(value = "/", params = "doDownTogetherBuyGoods")
    public String doDownTogetherBuyGoods(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                         RedirectAttributes redirectAttributes,
                                         Model model) {
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // よく一緒に購入される商品表示順変更(上)
        goodsRegistUpdateHelper.toPageForDownGoodsTogetherBuyGroup(goodsRegistUpdateModel);
        // 画面再表示
        goodsRegistUpdateHelper.toPageForLoadTogetherBuy(goodsRegistUpdateModel);

        // 画面リロード後の自動スクロールのターゲットポジションをセット
        goodsRegistUpdateModel.setTargetAutoScrollTagId(ID_SCROLL_GOODS_RELATION_ITEMS);
        return "goods/registupdate/index";
    }

    /**
     * よく一緒に購入される商品追加イベント（Ajax用）<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return よく一緒に購入される商品検索ダイアログ
     */
    @PostMapping(value = "/doAddGoodsTogetherBuyGroupAjax")
    @ResponseBody
    public ResponseEntity<?> doAddGoodsTogetherBuyGroupAjax(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                                            RedirectAttributes redirectAttributes,
                                                            Model model) {
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return ResponseEntity.badRequest().body(check);
        }

        goodsRegistUpdateModel.setRedirectGoodsTogetherBuyGroupEntityList(
                        goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList());

        return ResponseEntity.ok(goodsRegistUpdateModel);
    }
    // 2023-renew No21 to here

    /**
     * コンポーネント値初期化
     *
     * @param goodsRegistUpdateModel
     */
    private void initComponentValue(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        goodsRegistUpdateModel.setTaxRateItems(divisionMapGetService.getTaxRateMapList());
        goodsRegistUpdateModel.setAlcoholFlagItems(EnumTypeUtil.getEnumMap(HTypeAlcoholFlag.class));
        goodsRegistUpdateModel.setSnsLinkFlagItems(EnumTypeUtil.getEnumMap(HTypeSnsLinkFlag.class));
        goodsRegistUpdateModel.setIndividualDeliveryTypeItems(
                        EnumTypeUtil.getEnumMap(HTypeIndividualDeliveryType.class));
        goodsRegistUpdateModel.setGoodsOpenStatusPCItems(EnumTypeUtil.getEnumMap(HTypeOpenDeleteStatus.class));
        goodsRegistUpdateModel.setFreeDeliveryFlagItems(EnumTypeUtil.getEnumMap(HTypeFreeDeliveryFlag.class));
        goodsRegistUpdateModel.setUnitManagementFlagItems(EnumTypeUtil.getEnumMap(HTypeUnitManagementFlag.class));
        goodsRegistUpdateModel.setGoodsSaleStatusPCItems(
                        EnumTypeUtil.getEnumMap(HTypeGoodsSaleStatusWithNoDeleted.class));
        goodsRegistUpdateModel.setStockManagementFlagItems(EnumTypeUtil.getEnumMap(HTypeStockManagementFlag.class));
    }

    /**
     * チェックボックスにデフォルト値を設定
     *
     * @param goodsRegistUpdateModel
     */
    private void initValueFlag(GoodsRegistUpdateModel goodsRegistUpdateModel) {
        if (StringUtils.isEmpty(goodsRegistUpdateModel.getAlcoholFlag())) {
            goodsRegistUpdateModel.setAlcoholFlag(HTypeAlcoholFlag.NOT_ALCOHOL.getValue());
        }
        if (StringUtils.isEmpty(goodsRegistUpdateModel.getSnsLinkFlag())) {
            goodsRegistUpdateModel.setSnsLinkFlag(HTypeSnsLinkFlag.OFF.getValue());
        }
        if (StringUtils.isEmpty(goodsRegistUpdateModel.getIndividualDeliveryType())) {
            goodsRegistUpdateModel.setIndividualDeliveryType(HTypeIndividualDeliveryType.OFF.getValue());
        }
        if (StringUtils.isEmpty(goodsRegistUpdateModel.getFreeDeliveryFlag())) {
            goodsRegistUpdateModel.setFreeDeliveryFlag(HTypeFreeDeliveryFlag.OFF.getValue());
        }
        if (StringUtils.isEmpty(goodsRegistUpdateModel.getUnitManagementFlag())) {
            goodsRegistUpdateModel.setUnitManagementFlag(HTypeUnitManagementFlag.OFF.getValue());
        }
        if (StringUtils.isEmpty(goodsRegistUpdateModel.getStockManagementFlag())) {
            goodsRegistUpdateModel.setStockManagementFlag(HTypeStockManagementFlag.OFF.getValue());
        }
        // 2023-renew No21 from here
        goodsRegistUpdateModel.setExcludingFlag(
                        goodsRegistUpdateModel.isExcluding() ? HTypeExcludeFlag.ON : HTypeExcludeFlag.OFF);
        // 2023-renew No21 to here
    }

    /***************************************************************************************************************************
     ** 商品登録更新確認
     ***************************************************************************************************************************/
    /**
     * 画像表示処理<br/>
     * 初期表示用メソッド<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @GetMapping("/confirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/goods/registupdate?from=confirm")
    public String doLoadConfirm(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // ブラウザバックの場合、処理しない
        if (goodsRegistUpdateModel.getGoodsGroupDto() == null) {
            return "redirect:/goods/";
        }

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        goodsRegistUpdateModel.setInputingFlg(true);

        // 共通初期表示処理
        String errorClass = super.loadPage(goodsRegistUpdateModel, goodsRegistUpdateConfirmHelper, redirectAttributes,
                                           model
                                          );
        // 共通初期表示エラー時
        if (errorClass != null) {
            return errorClass;
        }

        // 商品グループが存在しない場合エラー
        if (!goodsRegistUpdateModel.isInputingFlg()
            && goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq() == null) {
            // 商品グループコード未指定の場合
            addMessage("AGG000002", redirectAttributes, model);
            return "redirect:/error";
        }

        // 商品規格画像削除チェック
        List<String> deletedList = goodsRegistUpdateConfirmHelper.checkGoodsImageDeleted(goodsRegistUpdateModel);
        if (CollectionUtil.isNotEmpty(deletedList)) {
            for (String goodsCode : deletedList) {
                addMessage("AGG001003", new Object[] {goodsCode}, redirectAttributes, model);
            }
        }

        // 登録カテゴリ情報の取得
        CategorySearchForDaoConditionDto categorySearchForDaoConditionDto =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
        // 検索条件設定
        categorySearchForDaoConditionDto.setOrderField("categorypath");
        categorySearchForDaoConditionDto.setOrderAsc(true);
        categorySearchForDaoConditionDto.setCategoryId(null);

        // カテゴリ情報取得
        categorySearchForDaoConditionDto.setCategorySeqList(new ArrayList<>());
        List<CategoryGoodsEntity> categoryGoodsEntityList =
                        goodsRegistUpdateModel.getGoodsGroupDto().getCategoryGoodsEntityList();
        if (!ListUtils.isEmpty(categoryGoodsEntityList)) {
            List<Integer> categorySeqList = new ArrayList<>();
            for (CategoryGoodsEntity categoryGoodsEntity : categoryGoodsEntityList) {
                categorySeqList.add(categoryGoodsEntity.getCategorySeq());
            }
            categorySearchForDaoConditionDto.setCategorySeqList(categorySeqList);

            categorySearchForDaoConditionDto.setMaxHierarchical(null);
            categorySearchForDaoConditionDto.setOpenStatus(null);
            categorySearchForDaoConditionDto.setOrderField("categorypath");
            categorySearchForDaoConditionDto.setOrderAsc(true);
            // 登録カテゴリリスト取得
            List<CategoryEntity> registCategoryList = new ArrayList<>();
            if (categorySearchForDaoConditionDto.getCategorySeqList().size() > 0) {
                registCategoryList =
                                categoryListGetService.execute(categorySearchForDaoConditionDto, HTypeSiteType.BACK);
                goodsRegistUpdateModel.setRegistCategoryEntityList(registCategoryList);
            }

            // 全カテゴリリスト取得
            categorySearchForDaoConditionDto.setCategorySeqList(null);
            List<CategoryEntity> allCategoryList =
                            categoryListGetService.execute(categorySearchForDaoConditionDto, HTypeSiteType.BACK);
            goodsRegistUpdateModel.setCategoryEntityList(allCategoryList);
        } else {
            goodsRegistUpdateModel.setRegistCategoryEntityList(null);
        }

        // 商品アイコン情報リスト取得
        if (goodsRegistUpdateModel.getIconList() == null) {
            goodsRegistUpdateModel.setIconList(goodsInformationIconListGetService.execute());
        }

        Map<Integer, GoodsGroupImageEntity> masterGgImageMap = new HashMap<>();

        // 更新処理の場合は変更情報を表示する
        if (!goodsRegistUpdateModel.isRegist()
            && goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupCode() != null
            && goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq() != null) {
            // 商品グループDtoを取得
            GoodsGroupDto masterGoodsGroupDto = goodsGroupGetService.execute(
                            goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupCode(),
                            HTypeSiteType.BACK
                                                                            );
            if (masterGoodsGroupDto == null) {
                // 商品グループコードなしエラー
                addMessage("AGG000001", new Object[] {goodsRegistUpdateModel.getGoodsGroupDto()
                                                                            .getGoodsGroupEntity().getGoodsGroupCode()},
                           redirectAttributes, model
                          );
                return "redirect:/error";
            }
            // インフォメーションアイコンPC(変更前)をセット
            if (masterGoodsGroupDto.getGoodsGroupDisplayEntity().getInformationIconPC() != null) {
                goodsRegistUpdateModel.setMasterInformationIconPcList(
                                Arrays.asList(masterGoodsGroupDto.getGoodsGroupDisplayEntity()
                                                                 .getInformationIconPC()
                                                                 .split("/")));
            }

            // 登録カテゴリIDリスト(変更前)をセット
            goodsRegistUpdateModel.setMasterCategoryGoodsEntityList(masterGoodsGroupDto.getCategoryGoodsEntityList());

            // 商品規格リスト(変更前)をセット
            goodsRegistUpdateModel.setMasterGoodsDtoList(masterGoodsGroupDto.getGoodsDtoList());

            // 関連商品エンティティリスト(変更前)を取得する
            List<GoodsRelationEntity> masterGoodsRelationEntityList = goodsRelationListGetForBackService.execute(
                            goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq());
            goodsRegistUpdateModel.setMasterGoodsRelationEntityList(masterGoodsRelationEntityList);

            // 2023-renew No21 from here
            // よく一緒に購入される商品エンティティリスト(変更前)を取得する
            List<GoodsTogetherBuyGroupEntity> masterGoodsTogetherBuyGroupEntityList =
                            goodsTogetherBuyGroupListGetForBackService.execute(goodsRegistUpdateModel.getGoodsGroupDto()
                                                                                                     .getGoodsGroupEntity()
                                                                                                     .getGoodsGroupSeq());
            goodsRegistUpdateModel.setMasterGoodsTogetherBuyGroupEntityList(masterGoodsTogetherBuyGroupEntityList);
            // 2023-renew No21 to here

            // 商品グループDtoの比較(修正箇所マップを取得)
            List<String> modifiedGoodsGroupList =
                            DiffUtil.diff(masterGoodsGroupDto, goodsRegistUpdateModel.getGoodsGroupDto());
            goodsRegistUpdateModel.setModifiedGoodeGroupList(modifiedGoodsGroupList);
            // 商品グループDtoの各項目における差分を作成
            goodsRegistUpdateConfirmHelper.setDiff(
                            goodsRegistUpdateModel, masterGoodsGroupDto, goodsRegistUpdateModel.getGoodsGroupDto());

            // 共通商品項目の比較(修正箇所マップを取得)
            List<String> modifiedCommonGoodsList = new ArrayList<>();
            GoodsEntity masterCommonGoodsEntity = null;
            for (int i = 0; masterGoodsGroupDto.getGoodsDtoList() != null && i < masterGoodsGroupDto.getGoodsDtoList()
                                                                                                    .size(); i++) {
                if (HTypeGoodsSaleStatus.DELETED != masterGoodsGroupDto.getGoodsDtoList()
                                                                       .get(i)
                                                                       .getGoodsEntity()
                                                                       .getSaleStatusPC()) {
                    masterCommonGoodsEntity = masterGoodsGroupDto.getGoodsDtoList().get(i).getGoodsEntity();
                    break;
                }
            }
            if (goodsRegistUpdateModel.getNotDeletedGoodsFirstIndex(goodsRegistUpdateModel.getGoodsGroupDto()) != -1) {
                modifiedCommonGoodsList = DiffUtil.diff(masterCommonGoodsEntity,
                                                        goodsRegistUpdateModel.getGoodsGroupDto()
                                                                              .getGoodsDtoList()
                                                                              .get(goodsRegistUpdateModel.getNotDeletedGoodsFirstIndex(
                                                                                              goodsRegistUpdateModel.getGoodsGroupDto()))
                                                                              .getGoodsEntity()
                                                       );
            }
            goodsRegistUpdateModel.setModifiedCommonGoodsList(modifiedCommonGoodsList);

            // 各画像情報のマスタを保持(比較用)
            // グループ画像
            List<GoodsGroupImageEntity> ggImageDtoList = masterGoodsGroupDto.getGoodsGroupImageEntityList();
            if (ggImageDtoList != null) {
                for (GoodsGroupImageEntity ggImage : ggImageDtoList) {
                    GoodsGroupImageEntity entity = masterGgImageMap.get(ggImage.getImageTypeVersionNo());
                    masterGgImageMap.put(ggImage.getImageTypeVersionNo(), entity);
                }
            }
            goodsRegistUpdateModel.setMasterGoodsGroupImageEntityMap(masterGgImageMap);
        }

        // ショップ情報取得
        ShopEntity shopEntity = shopInfoGetService.execute();

        // ページ表示情報の編集
        goodsRegistUpdateConfirmHelper.toPageForLoad(shopEntity, goodsRegistUpdateModel);

        if (goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq() != null) {
            // 親商品グループがセール対象になっている警告
            List<String> goodsCodeList = new ArrayList<>();
            for (GoodsDto goodsDto : goodsRegistUpdateModel.getGoodsGroupDto().getGoodsDtoList()) {
                GoodsEntity goodsEntity = goodsDto.getGoodsEntity();
                if (HTypeGoodsSaleStatus.DELETED == goodsDto.getGoodsEntity().getSaleStatusPC()) {
                    continue;
                }
                goodsCodeList.add(goodsEntity.getGoodsCode());
            }
        }

        // 商品グループDto相関チェック
        if (goodsRegistUpdateModel.getGoodsGroupDto().getGoodsDtoList() != null) {
            goodsGroupCorrelationService.execute(goodsRegistUpdateModel.getGoodsGroupDto(),
                                                 goodsRegistUpdateModel.getGoodsRelationEntityList(),
                                                 // 2023-renew No21 from here
                                                 goodsRegistUpdateModel.getGoodsTogetherBuyGroupEntityList(),
                                                 // 2023-renew No21 to here
                                                 GoodsGroupCorrelationDataCheckService.PROCESS_TYPE_FROM_SCREEN, null
                                                );
        }

        return "goods/registupdate/confirm";
    }

    /**
     * 登録更新イベント<br/>
     *
     * @param goodsRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @param sessionStatus
     * @return 検索画面
     */
    @PostMapping(value = "/confirm/", params = "doOnceRegistUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/registupdate/confirm")
    public String doOnceRegistUpdate(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                     RedirectAttributes redirectAttributes,
                                     Model model,
                                     SessionStatus sessionStatus) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 商品グループDto相関チェック
        goodsGroupCorrelationService.execute(goodsRegistUpdateModel.getGoodsGroupDto(),
                                             goodsRegistUpdateModel.getGoodsRelationEntityList(),
                                             // 2023-renew No21 from here

                                             goodsRegistUpdateModel.getGoodsTogetherBuyGroupEntityList(),
                                             // 2023-renew No21 to here
                                             GoodsGroupCorrelationDataCheckService.PROCESS_TYPE_FROM_SCREEN, null
                                            );

        // 画像に関する登録更新情報をセット
        goodsRegistUpdateConfirmHelper.toSetImageInfo(goodsRegistUpdateModel);

        // 規格画像に関する登録更新情報をセット
        goodsRegistUpdateConfirmHelper.toSetUnitImagesInfo(goodsRegistUpdateModel);

        // 商品グループ登録更新
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        Map<String, Object> retMap = goodsGroupRegistUpdateService.execute(goodsRegistUpdateModel.getGoodsGroupDto(),
                                                                           Arrays.asList(goodsRegistUpdateModel.getGoodsRelationEntityList()
                                                                                                               .toArray(new GoodsRelationEntity[] {})),
                                                                           // 2023-renew No21 from here
                                                                           Arrays.asList(goodsRegistUpdateModel.getGoodsTogetherBuyGroupEntityList()
                                                                                                               .toArray(new GoodsTogetherBuyGroupEntity[] {})),
                                                                           // 2023-renew No21 to here
                                                                           goodsRegistUpdateModel.getGoodsGroupImageRegistUpdateDtoList(),
                                                                           GoodsGroupRegistUpdateService.PROCESS_TYPE_FROM_SCREEN,
                                                                           commonInfoUtility.getAdministratorName(
                                                                                           getCommonInfo())
                                                                          );

        goodsRegistUpdateHelper.updateImagesPosition(goodsRegistUpdateModel);

        if (goodsRegistUpdateModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq() != null) {
            // 再検索フラグをセット
            redirectAttributes.addFlashAttribute(FLASH_MD, MODE_LIST);
        }

        // ワーニングメッセージの設定
        if (retMap.get(GoodsGroupRegistUpdateService.WARNING_MESSAGE) != null && !"".equals(
                        retMap.get(GoodsGroupRegistUpdateService.WARNING_MESSAGE))) {
            String[] warningMessages = ((String) retMap.get(GoodsGroupRegistUpdateService.WARNING_MESSAGE)).split(",");
            for (String messageId : warningMessages) {
                addMessage(messageId, redirectAttributes, model);
            }
        }

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "redirect:/goods/";
    }

    /**
     * 商品登録更新（関連商品設定）遷移<br/>
     *
     * @param goodsRegistUpdateModel
     * @param model
     * @return 商品登録更新（関連商品設定）画面
     */
    @PostMapping(value = "/confirm/", params = "doCancelConfirm")
    public String doCancelConfirm(GoodsRegistUpdateModel goodsRegistUpdateModel,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        goodsRegistUpdateModel.setInputingFlg(false);
        goodsRegistUpdateModel.setCategoryTrees(goodsRegistUpdateModel.getCategoryTreesForRecovery());
        redirectAttributes.addFlashAttribute(FLASH_FROM_CONFIRM, true);
        return "redirect:/goods/registupdate/?goodGroupCode=" + goodsRegistUpdateModel.getGoodsGroupCode();
    }

    /**
     * 商品グループ詳細画像ファイル取得<br/>
     *
     * @param item        商品グループ詳細画像アイテム
     * @param realPath    商品画像ディレクトリパス
     * @param tmpFileName TMPファイル名
     * @return 詳細画像ファイル
     */
    private MultipartFile getSelectedDetailsImageFile(GoodsRegistUpdateImageItem item,
                                                      String realPath,
                                                      String tmpFileName) {
        putFile(item.getImageFile(), realPath + tmpFileName);
        return item.getImageFile();
    }

    /**
     * 商品グループ詳細画像ファイル取得<br/>
     *
     * @param item        商品グループ詳細画像アイテム
     * @param realPath    商品画像ディレクトリパス
     * @param tmpFileName TMPファイル名
     * @return 詳細画像ファイル
     */
    private MultipartFile getSelectedUnitImageFile(GoodsRegistUpdateUnitImage item,
                                                   String realPath,
                                                   String tmpFileName) {
        putFile(item.getUnitImage(), realPath + tmpFileName);
        return item.getUnitImage();
    }

    /**
     * ファイル移動処理<br/>
     *
     * @param src 移動元ファイル
     * @param dst 移動先ファイル名
     */
    private void putFile(final MultipartFile src, final String dst) {
        try {
            FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);
            fileOperationUtility.put(src, dst);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throwMessage("AGG001403");
        }
    }
}
