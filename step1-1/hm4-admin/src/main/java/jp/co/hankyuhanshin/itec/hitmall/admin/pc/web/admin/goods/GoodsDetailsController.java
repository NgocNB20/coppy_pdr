/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods;

import jp.co.hankyuhanshin.itec.hitmall.admin.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.division.DivisionMapGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupRegistUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.relation.GoodsRelationListGetForBackService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.togetherbuy.GoodsTogetherBuyGroupListGetForBackService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.ShopGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.icon.GoodsInformationIconListGetService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 商品管理：商品詳細コントローラー
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/goods/details")
@Controller
@SessionAttributes(value = "goodsDetailsModel")
@PreAuthorize("hasAnyAuthority('GOODS:4')")
public class GoodsDetailsController extends AbstractGoodsRegistUpdateController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsDetailsController.class);

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * 商品登録更新確認ページDxo<br/>
     */
    private final GoodsDetailsHelper goodsDetailsHelper;

    /**
     * 商品グループ登録更新サービス<br/>
     */
    private final GoodsGroupRegistUpdateService goodsGroupRegistUpdateService;

    /**
     * カテゴリリスト取得サービス<br/>
     */
    private final CategoryListGetService categoryListGetService;

    /**
     * アイコンリスト取得サービス<br/>
     */
    private final GoodsInformationIconListGetService goodsInformationIconListGetService;

    /**
     * ショップ情報取得サービス
     */
    private final ShopGetService shopInfoGetService;

    /**
     * 分類リスト取得サービス
     */
    private final DivisionMapGetService divisionMapGetService;

    /**
     * コンストラクター
     *
     * @param goodsDetailsHelper
     * @param goodsGroupRegistUpdateService
     * @param categoryListGetService
     * @param goodsInformationIconListGetService
     * @param shopInfoGetService
     */
    @Autowired
    public GoodsDetailsController(GoodsGroupGetService goodsGroupGetService,
                                  GoodsRelationListGetForBackService goodsRelationListGetForBackService,
                                  GoodsDetailsHelper goodsDetailsHelper,
                                  GoodsGroupRegistUpdateService goodsGroupRegistUpdateService,
                                  CategoryListGetService categoryListGetService,
                                  GoodsInformationIconListGetService goodsInformationIconListGetService,
                                  ShopGetService shopInfoGetService,
                                  DivisionMapGetService divisionMapGetService,
                                  // 2023-renew No21 from here
                                  GoodsTogetherBuyGroupListGetForBackService goodsTogetherBuyGroupListGetForBackService) {
        super(goodsGroupGetService, goodsRelationListGetForBackService, goodsTogetherBuyGroupListGetForBackService);
        // 2023-renew No21 to here
        this.goodsDetailsHelper = goodsDetailsHelper;
        this.goodsGroupRegistUpdateService = goodsGroupRegistUpdateService;
        this.categoryListGetService = categoryListGetService;
        this.goodsInformationIconListGetService = goodsInformationIconListGetService;
        this.shopInfoGetService = shopInfoGetService;
        this.divisionMapGetService = divisionMapGetService;
    }

    /**
     * 画像表示処理<br/>
     * 初期表示用メソッド<br/>
     *
     * @param goodsGroupCode
     * @param goodsDetailsModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping(value = "/")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/details")
    public String doLoadDetails(@RequestParam(required = false) Optional<String> goodsGroupCode,
                                GoodsDetailsModel goodsDetailsModel,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // モデル初期化
        clearModel(GoodsDetailsModel.class, goodsDetailsModel, model);

        if (goodsGroupCode.isPresent()) {
            goodsDetailsModel.setRedirectGoodsGroupCode(goodsGroupCode.get());
        }

        // 共通初期表示処理
        String errorClass = super.loadPage(goodsDetailsModel, goodsDetailsHelper, redirectAttributes, model);
        // 共通初期表示エラー時
        if (errorClass != null) {
            return errorClass;
        }

        // 商品グループが存在しない場合エラー
        if (goodsDetailsModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq() == null) {
            // 商品グループコード未指定の場合
            addMessage("AGG000002", redirectAttributes, model);
            return "redirect:/error";
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
                        goodsDetailsModel.getGoodsGroupDto().getCategoryGoodsEntityList();
        if (categoryGoodsEntityList != null) {
            for (CategoryGoodsEntity categoryGoodsEntity : categoryGoodsEntityList) {
                categorySearchForDaoConditionDto.getCategorySeqList().add(categoryGoodsEntity.getCategorySeq());
            }
            categorySearchForDaoConditionDto.setMaxHierarchical(null);
            categorySearchForDaoConditionDto.setOpenStatus(null);
            categorySearchForDaoConditionDto.setOrderField("categorypath");
            categorySearchForDaoConditionDto.setOrderAsc(true);
            // 登録カテゴリリスト取得
            List<CategoryEntity> registCategoryList = new ArrayList<>();
            if (categorySearchForDaoConditionDto.getCategorySeqList().size() > 0) {
                registCategoryList =
                                categoryListGetService.execute(categorySearchForDaoConditionDto, HTypeSiteType.BACK);
                goodsDetailsModel.setRegistCategoryEntityList(registCategoryList);
            }

            // 全カテゴリリスト取得
            categorySearchForDaoConditionDto.setCategorySeqList(null);
            List<CategoryEntity> allCategoryList =
                            categoryListGetService.execute(categorySearchForDaoConditionDto, HTypeSiteType.BACK);
            goodsDetailsModel.setCategoryEntityList(allCategoryList);
        }

        // ショップ情報取得
        ShopEntity shopEntity = shopInfoGetService.execute();

        // ページ表示情報の編集
        goodsDetailsHelper.toPageForLoad(shopEntity, goodsDetailsModel);

        goodsDetailsModel.setRedirectRecycle(null);

        return "goods/details";
    }

    /**
     * 戻るイベント<br/>
     *
     * @param goodsDetailsModel
     * @param redirectAttributes
     * @param sessionStatus
     * @param model
     * @return 遷移元
     */
    @PostMapping(value = "/", params = "doBack")
    public String doBack(GoodsDetailsModel goodsDetailsModel,
                         RedirectAttributes redirectAttributes,
                         SessionStatus sessionStatus,
                         Model model) {

        // 再検索フラグをセット
        redirectAttributes.addFlashAttribute(FLASH_MD, MODE_LIST);
        // Modelをセッションより破棄
        sessionStatus.setComplete();
        return "redirect:/goods/";
    }

    /**
     * 削除イベント<br/>
     *
     * @param goodsDetailsModel
     * @param redirectAttributes
     * @param sessionStatus
     * @param model
     * @return 商品検索画面
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doOnceDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/details")
    public String doOnceDelete(GoodsDetailsModel goodsDetailsModel,
                               RedirectAttributes redirectAttributes,
                               SessionStatus sessionStatus,
                               Model model) {

        // 入力情報チェック
        checkDataForDelete(goodsDetailsModel);

        goodsDetailsHelper.toPageForDelete(goodsDetailsModel);
        try {
            CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
            goodsGroupRegistUpdateService.execute(
                            goodsDetailsModel.getGoodsGroupDto(),
                            Arrays.asList(goodsDetailsModel.getGoodsRelationEntityList()
                                                           .toArray(new GoodsRelationEntity[] {})),
                            // 2023-renew No21 from here
                            Arrays.asList(goodsDetailsModel.getGoodsTogetherBuyGroupEntityList()
                                                           .toArray(new GoodsTogetherBuyGroupEntity[] {})),
                            // 2023-renew No21 to here
                            goodsDetailsModel.getGoodsGroupImageRegistUpdateDtoList(),
                            GoodsGroupRegistUpdateService.PROCESS_TYPE_FROM_SCREEN,
                            commonInfoUtility.getAdministratorName(getCommonInfo())
                                                 );

        } catch (AppLevelListException apple) {
            LOGGER.error("例外処理が発生しました", apple);
            // 削除失敗時の公開状態戻し処理
            if (goodsDetailsModel.getOldGoodsOpenStatusPC() != null) {
                restoreData(goodsDetailsModel);
            }
            throw apple;
        }

        // 再検索フラグをセット
        redirectAttributes.addFlashAttribute(FLASH_MD, MODE_LIST);

        // Modelをセッションより破棄
        sessionStatus.setComplete();
        return "redirect:/goods/";
    }

    /**
     * 入庫履歴へ
     *
     * @param goodsDetailsModel
     * @return
     */
    @PostMapping(value = "/", params = "doLoadHistorySupplement")
    public String doLoadHistorySupplement(GoodsDetailsModel goodsDetailsModel, SessionStatus sessionStatus) {
        // Modelをセッションより破棄
        Integer selectedGoodsSeq = goodsDetailsModel.getSelectGoodsSeq();
        sessionStatus.setComplete();
        return "redirect:/goods/stock/details?goodsSeq=" + selectedGoodsSeq;
    }

    /**
     * エラー時のデータ復元<br/>
     *
     * @param goodsDetailsModel
     */
    private void restoreData(GoodsDetailsModel goodsDetailsModel) {
        GoodsGroupEntity goodsGroupEntity = goodsDetailsModel.getGoodsGroupDto().getGoodsGroupEntity();
        goodsGroupEntity.setGoodsOpenStatusPC(goodsDetailsModel.getOldGoodsOpenStatusPC());
    }

    /**
     * 入力情報チェック（削除処理用）<br/>
     * Validatorで対応できないもの
     *
     * @param goodsDetailsModel
     */
    private void checkDataForDelete(GoodsDetailsModel goodsDetailsModel) {
        // 既に削除済みエラー
        if (HTypeOpenDeleteStatus.DELETED == goodsDetailsModel.getGoodsGroupDto()
                                                              .getGoodsGroupEntity()
                                                              .getGoodsOpenStatusPC()) {
            addErrorMessage("AGG001101");
        }
        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            throwMessage();
        }
    }
}
