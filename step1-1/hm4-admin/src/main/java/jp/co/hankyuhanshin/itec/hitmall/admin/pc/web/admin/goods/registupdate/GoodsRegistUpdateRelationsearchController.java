/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.ajax.MessageUtils;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.ajax.ValidatorMessage;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.AbstractGoodsRegistUpdateController;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.AbstractGoodsRegistUpdateModel;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.division.DivisionMapGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsSearchResultListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.relation.GoodsRelationListGetForBackService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.togetherbuy.GoodsTogetherBuyGroupListGetForBackService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 商品管理：商品登録更新（関連商品設定検索）アクション
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Controller
@RequestMapping(value = "/goods/registupdate/relationsearch")
@SessionAttributes(value = "goodsRegistUpdateRelationsearchModel")
@PreAuthorize("hasAnyAuthority('GOODS:8')")
public class GoodsRegistUpdateRelationsearchController extends AbstractGoodsRegistUpdateController {

    /**
     * 商品登録更新ページ/>
     */
    public static final String FLASH_GOODS_REGIST_UPDATES_MODEL = "goodsRegistUpdateModel";

    /**
     * 関連商品設定検索ページ/>
     */
    public static final String FLASH_GOODS_RELATION_SEARCH_MODEL = "goodsRegistUpdateRelationsearchModel";

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRegistUpdateRelationsearchController.class);

    /**
     * Helper
     */
    private final GoodsRegistUpdateRelationsearchHelper goodsRegistUpdateRelationsearchHelper;

    /**
     * 商品検索結果リスト取得Service
     */
    private final GoodsSearchResultListGetService goodsSearchResultListGetService;

    /**
     * カテゴリリスト取得
     */
    private final CategoryListGetService categoryListGetService;

    /**
     * 分類リスト取得サービス
     */
    private final DivisionMapGetService divisionMapGetService;

    /**
     * コンストラクター
     *
     * @param goodsGroupGetService
     * @param goodsRelationListGetForBackService
     * @param goodsRegistUpdateRelationsearchHelper
     * @param goodsSearchResultListGetService
     * @param categoryListGetService
     * @param divisionMapGetService
     */
    public GoodsRegistUpdateRelationsearchController(GoodsGroupGetService goodsGroupGetService,
                                                     GoodsRelationListGetForBackService goodsRelationListGetForBackService,
                                                     GoodsRegistUpdateRelationsearchHelper goodsRegistUpdateRelationsearchHelper,
                                                     GoodsSearchResultListGetService goodsSearchResultListGetService,
                                                     CategoryListGetService categoryListGetService,
                                                     DivisionMapGetService divisionMapGetService,
                                                     // 2023-renew No21 from here
                                                     GoodsTogetherBuyGroupListGetForBackService goodsTogetherBuyGroupListGetForBackService) {
        super(goodsGroupGetService, goodsRelationListGetForBackService, goodsTogetherBuyGroupListGetForBackService);
        // 2023-renew No21 to here
        this.goodsRegistUpdateRelationsearchHelper = goodsRegistUpdateRelationsearchHelper;
        this.goodsSearchResultListGetService = goodsSearchResultListGetService;
        this.categoryListGetService = categoryListGetService;
        this.divisionMapGetService = divisionMapGetService;
    }

    /**
     * 画像表示処理<br/>
     * 初期表示用メソッド<br/>
     *
     * @return ページクラス
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/registupdate/relationsearch")
    public String doLoadIndex(GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // コンポーネント値初期化
        initComponentValue(goodsRegistUpdateRelationsearchModel);

        if (model.containsAttribute(FLASH_GOODS_REGIST_UPDATES_MODEL)) {
            GoodsRegistUpdateModel goodsRegistUpdateModel =
                            (GoodsRegistUpdateModel) model.getAttribute(FLASH_GOODS_REGIST_UPDATES_MODEL);
            goodsRegistUpdateRelationsearchModel.setGoodsGroupCode(goodsRegistUpdateModel.getGoodsGroupCode());
            goodsRegistUpdateRelationsearchModel.setRedirectGoodsRelationEntityList(
                            goodsRegistUpdateModel.getRedirectGoodsRelationEntityList());
            goodsRegistUpdateRelationsearchModel.setTmpGoodsRelationEntityList(
                            goodsRegistUpdateModel.getTmpGoodsRelationEntityList());
            goodsRegistUpdateRelationsearchModel.setGoodsGroupDto(goodsRegistUpdateModel.getGoodsGroupDto());
            goodsRegistUpdateRelationsearchModel.setGoodsRelationEntityList(
                            goodsRegistUpdateModel.getGoodsRelationEntityList());
            goodsRegistUpdateRelationsearchModel.setScGoodsGroupSeq(goodsRegistUpdateModel.getScGoodsGroupSeq());
        }

        // カテゴリパスを保持するマップを作成
        this.setCategoryPathMap(goodsRegistUpdateRelationsearchModel);

        goodsRegistUpdateRelationsearchHelper.toPageForLoad(goodsRegistUpdateRelationsearchModel);
        // 検索条件がセッションに残っている場合は検索する
        if (goodsRegistUpdateRelationsearchModel.getGoodsGroupSearchForDaoConditionDto() != null
            && goodsRegistUpdateRelationsearchModel.isResultGoodsReration()) {
            GoodsSearchForBackDaoConditionDto conditionDto =
                            goodsRegistUpdateRelationsearchModel.getGoodsGroupSearchForDaoConditionDto();
            // 検索
            List<GoodsSearchResultDto> resultDtoList =
                            goodsSearchResultListGetService.execute(conditionDto, HTypeSiteType.BACK);

            // ページにセット
            goodsRegistUpdateRelationsearchHelper.toPageForSearch(
                            resultDtoList, goodsRegistUpdateRelationsearchModel, conditionDto);
            goodsRegistUpdateRelationsearchModel.setResultGoodsRelationFlg(true);
        }

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateRelationsearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        return "goods/registupdate/relationsearch";
    }

    /**
     * 関連商品追加・初期表示用メソッド（Ajax用）<br/>
     *
     * @param goodsRegistUpdateModel               商品管理：商品登録更新ページ
     * @param goodsRegistUpdateRelationsearchModel 商品管理：商品登録更新（関連商品設定検索）ページ
     * @param redirectAttributes
     * @param model
     * @return 関連商品情報
     */
    @PostMapping("/ajax")
    @ResponseBody
    public ResponseEntity<GoodsRegistUpdateRelationsearchModel> doLoadPopupRelationSearch(
                    @RequestBody GoodsRegistUpdateModel goodsRegistUpdateModel,
                    GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel,
                    RedirectAttributes redirectAttributes,
                    Model model) {

        // コンポーネント値初期化
        initComponentValue(goodsRegistUpdateRelationsearchModel);
        if (ObjectUtils.isNotEmpty(goodsRegistUpdateModel)) {
            goodsRegistUpdateRelationsearchModel.setGoodsGroupCode(goodsRegistUpdateModel.getGoodsGroupCode());
            goodsRegistUpdateRelationsearchModel.setRedirectGoodsRelationEntityList(
                            goodsRegistUpdateModel.getRedirectGoodsRelationEntityList());
            goodsRegistUpdateRelationsearchModel.setTmpGoodsRelationEntityList(
                            goodsRegistUpdateModel.getTmpGoodsRelationEntityList());
            goodsRegistUpdateRelationsearchModel.setGoodsGroupDto(goodsRegistUpdateModel.getGoodsGroupDto());
            goodsRegistUpdateRelationsearchModel.setGoodsRelationEntityList(
                            goodsRegistUpdateModel.getGoodsRelationEntityList());
            goodsRegistUpdateRelationsearchModel.setScGoodsGroupSeq(goodsRegistUpdateModel.getScGoodsGroupSeq());
        }

        // カテゴリパスを保持するマップを作成
        this.setCategoryPathMap(goodsRegistUpdateRelationsearchModel);

        goodsRegistUpdateRelationsearchHelper.toPageForLoad(goodsRegistUpdateRelationsearchModel);
        // 検索条件がセッションに残っている場合は検索する
        if (goodsRegistUpdateRelationsearchModel.getGoodsGroupSearchForDaoConditionDto() != null
            && goodsRegistUpdateRelationsearchModel.isResultGoodsReration()) {
            GoodsSearchForBackDaoConditionDto conditionDto =
                            goodsRegistUpdateRelationsearchModel.getGoodsGroupSearchForDaoConditionDto();
            // 検索
            List<GoodsSearchResultDto> resultDtoList =
                            goodsSearchResultListGetService.execute(conditionDto, HTypeSiteType.BACK);

            // ページにセット
            goodsRegistUpdateRelationsearchHelper.toPageForSearch(
                            resultDtoList, goodsRegistUpdateRelationsearchModel, conditionDto);
            goodsRegistUpdateRelationsearchModel.setResultGoodsRelationFlg(true);
        }
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateRelationsearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            ResponseEntity.badRequest().body(check);
        }
        return ResponseEntity.ok(goodsRegistUpdateRelationsearchModel);
    }

    /**
     * 関連商品追加処理<br/>
     *
     * @param goodsRegistUpdateRelationsearchModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @PostMapping(value = "", params = "doSelectRelationGoods")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/registupdate/relationsearch")
    public String doSelectRelationGoods(HttpServletRequest request,
                                        GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateRelationsearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        // 検索結果チェック
        resultListCheck(goodsRegistUpdateRelationsearchModel);
        // 関連商品追加前チェック
        checkDataBeforeAddRelationGoods(goodsRegistUpdateRelationsearchModel);
        // 関連商品追加
        goodsRegistUpdateRelationsearchHelper.toPageForAddRelationGoods(goodsRegistUpdateRelationsearchModel);
        goodsRegistUpdateRelationsearchHelper.toPageForNext(goodsRegistUpdateRelationsearchModel);

        redirectAttributes.addFlashAttribute(FLASH_GOODS_RELATION_SEARCH_MODEL, goodsRegistUpdateRelationsearchModel);

        return "redirect:/goods/registupdate/";
    }

    /**
     * 関連商品追加処理（Ajax用）<br/>
     *
     * @param goodsRegistUpdateRelationsearchModel 商品管理：商品登録更新（関連商品設定検索）ページ
     * @param redirectAttributes
     * @param model
     * @return 関連商品情報
     */
    @PostMapping(value = "doSelectRelationAjax")
    public ResponseEntity<?> doSelectRelationGoodsAjax(GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel,
                                                       RedirectAttributes redirectAttributes,
                                                       Model model) {
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateRelationsearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return ResponseEntity.badRequest().body(check);
        }
        // 検索結果チェック
        List<ValidatorMessage> validatorMessages1 = resultListCheckAjax(goodsRegistUpdateRelationsearchModel);
        if (!CollectionUtils.isEmpty(validatorMessages1)) {
            return ResponseEntity.badRequest().body(validatorMessages1);
        }
        // 関連商品追加前チェック
        List<ValidatorMessage> validatorMessages =
                        checkDataBeforeAddRelationGoodsAjax(goodsRegistUpdateRelationsearchModel);
        if (!CollectionUtils.isEmpty(validatorMessages)) {
            return ResponseEntity.badRequest().body(validatorMessages);
        }
        goodsRegistUpdateRelationsearchHelper.toPageForAddRelationGoods(goodsRegistUpdateRelationsearchModel);
        goodsRegistUpdateRelationsearchHelper.toPageForNext(goodsRegistUpdateRelationsearchModel);

        return ResponseEntity.ok(goodsRegistUpdateRelationsearchModel);
    }

    /**
     * 戻る処理<br/>
     *
     * @param goodsRegistUpdateRelationsearchModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @PostMapping(value = "", params = "doReturn")
    public String doReturn(GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateRelationsearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 選択商品情報を保持する
        goodsRegistUpdateRelationsearchHelper.toPageForNext(goodsRegistUpdateRelationsearchModel);

        redirectAttributes.addFlashAttribute(FLASH_GOODS_RELATION_SEARCH_MODEL, goodsRegistUpdateRelationsearchModel);
        return "redirect:/goods/registupdate/";
    }

    /**
     * 検索イベント<br/>
     *
     * @param goodsRegistUpdateRelationsearchModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @PostMapping(value = "", params = "doSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/registupdate/relationsearch")
    public String doSearch(@Validated(SearchGroup.class)
                                           GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel,
                           BindingResult error,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        if (error.hasErrors()) {
            return "goods/registupdate/relationsearch";
        }

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateRelationsearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        // ブラウザバック対応 - カテゴリパスがnullの場合、マップを作成
        if (goodsRegistUpdateRelationsearchModel.getCategoryPathMap() == null) {
            this.setCategoryPathMap(goodsRegistUpdateRelationsearchModel);
        }
        // 検索前チェック
        checkDataBeforeSearch(goodsRegistUpdateRelationsearchModel);

        // 検索条件作成
        GoodsSearchForBackDaoConditionDto conditionDto =
                        goodsRegistUpdateRelationsearchHelper.toGoodsGroupSearchForDaoConditionDtoForSearch(
                                        goodsRegistUpdateRelationsearchModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, "1", Integer.MAX_VALUE, "goodsGroupCode", true);

        // 検索条件をセッションに保存
        goodsRegistUpdateRelationsearchModel.setGoodsGroupSearchForDaoConditionDto(conditionDto);

        // 検索
        List<GoodsSearchResultDto> resultDtoList =
                        goodsSearchResultListGetService.execute(conditionDto, HTypeSiteType.BACK);

        // ページにセット
        goodsRegistUpdateRelationsearchHelper.toPageForSearch(
                        resultDtoList, goodsRegistUpdateRelationsearchModel, conditionDto);

        goodsRegistUpdateRelationsearchModel.setResultGoodsRelationFlg(true);

        // 画面再表示
        goodsRegistUpdateRelationsearchHelper.toPageForLoad(goodsRegistUpdateRelationsearchModel);

        return "goods/registupdate/relationsearch";
    }

    /**
     * 検索イベント(Ajax用)<br/>
     *
     * @param goodsRegistUpdateRelationsearchModel 関連商品設定検索
     * @param error
     * @param redirectAttributes
     * @param model
     * @return 関連商品情報
     */
    @PostMapping(value = "/post/ajax")
    public ResponseEntity<?> doSearchAjax(@Validated(SearchGroup.class)
                                                          GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel,
                                          BindingResult error,
                                          RedirectAttributes redirectAttributes,
                                          Model model) {

        if (error.hasErrors()) {
            List<ValidatorMessage> mapError = MessageUtils.getMessageErrorFromBindingResult(error);
            return ResponseEntity.badRequest().body(mapError);
        }
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateRelationsearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return ResponseEntity.badRequest().body(check);
        }
        // ブラウザバック対応 - カテゴリパスがnullの場合、マップを作成
        if (goodsRegistUpdateRelationsearchModel.getCategoryPathMap() == null) {
            this.setCategoryPathMap(goodsRegistUpdateRelationsearchModel);
        }
        // 検索前チェック
        List<ValidatorMessage> list = checkDataBeforeSearchAjax(goodsRegistUpdateRelationsearchModel);
        if (!CollectionUtils.isEmpty(list)) {
            return ResponseEntity.badRequest().body(list);
        }

        // 検索条件作成
        GoodsSearchForBackDaoConditionDto conditionDto =
                        goodsRegistUpdateRelationsearchHelper.toGoodsGroupSearchForDaoConditionDtoForSearch(
                                        goodsRegistUpdateRelationsearchModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, "1", Integer.MAX_VALUE, "goodsGroupCode", true);

        // 検索条件をセッションに保存
        goodsRegistUpdateRelationsearchModel.setGoodsGroupSearchForDaoConditionDto(conditionDto);

        // 検索
        List<GoodsSearchResultDto> resultDtoList =
                        goodsSearchResultListGetService.execute(conditionDto, HTypeSiteType.BACK);

        // ページにセット
        goodsRegistUpdateRelationsearchHelper.toPageForSearch(
                        resultDtoList, goodsRegistUpdateRelationsearchModel, conditionDto);

        goodsRegistUpdateRelationsearchModel.setResultGoodsRelationFlg(true);

        // 画面再表示
        goodsRegistUpdateRelationsearchHelper.toPageForLoad(goodsRegistUpdateRelationsearchModel);

        return ResponseEntity.ok(resultDtoList);
    }

    /**
     * 検索結果の表示切替<br/>
     *
     * @param goodsRegistUpdateRelationsearchModel
     * @param redirectAttributes
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/registupdate/relationsearch")
    public String doDisplayChange(@Validated(DisplayChangeGroup.class)
                                                  GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel,
                                  BindingResult error,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        if (error.hasErrors()) {
            return "goods/registupdate/relationsearch";
        }

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateRelationsearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 検索結果チェック
        resultListCheck(goodsRegistUpdateRelationsearchModel);

        // 検索イベントへ
        return doSearch(goodsRegistUpdateRelationsearchModel, error, redirectAttributes, model);
    }

    /**
     * キャンセルイベント<br/>
     * 未使用
     *
     * @param goodsRegistUpdateRelationsearchModel
     * @param redirectAttributes
     * @param model
     * @return 遷移元
     */
    @PostMapping(value = "", params = "doCancel")
    public String doCancel(GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateRelationsearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        // 戻り先画面(セッション保存用)がnullでない場合はそちらを使う
        if (goodsRegistUpdateRelationsearchModel.getStoredBackPage() != null) {
            goodsRegistUpdateRelationsearchModel.setBackPage(goodsRegistUpdateRelationsearchModel.getStoredBackPage());
        }
        // 新規登録または再利用元商品グループコードがあり、「商品詳細」画面から遷移してきた場合
        if ((!goodsRegistUpdateRelationsearchModel.isRegistFlg()
             || goodsRegistUpdateRelationsearchModel.getRecycledGoodsGroupCode() != null)
            // &&
            // ComponentUtil.equalsComponentClass(jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.DetailsPage.class,
            // goodsRegistUpdateRelationsearchModel.getBackPage())
        ) {
            // 商品詳細画面に商品グループコードを渡す
            if (goodsRegistUpdateRelationsearchModel.getRecycledGoodsGroupCode() != null) {
                // 再利用時は再利用元商品グループコードを設定する
                goodsRegistUpdateRelationsearchModel.setRedirectGoodsGroupCode(
                                goodsRegistUpdateRelationsearchModel.getRecycledGoodsGroupCode());
            } else {
                goodsRegistUpdateRelationsearchModel.setRedirectGoodsGroupCode(
                                goodsRegistUpdateRelationsearchModel.getGoodsGroupCode());
            }
            return "redirect:/goods/details/";
        }
        // それ以外の場合は、「商品検索」画面に遷移
        return "redirect:/goods/";
    }

    /**
     * 商品検索結果リストが空でないことをチェックする<br/>
     * (ブラウザバック後の選択出力などでの不具合防止のため)<br/>
     */
    private void resultListCheck(GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel) {
        if (!goodsRegistUpdateRelationsearchModel.isResultGoodsReration()) {
            return;
        }
        if (goodsRegistUpdateRelationsearchModel.getResultItems().get(0).getResultGoodsGroupCode() == null || "".equals(
                        goodsRegistUpdateRelationsearchModel.getResultItems().get(0).getResultGoodsGroupCode())) {
            goodsRegistUpdateRelationsearchModel.setResultGoodsRelationFlg(false);
            goodsRegistUpdateRelationsearchModel.setResultItems(null);
            this.throwMessage("AGG000906");
        }
    }

    /**
     * 商品検索結果リストが空でないことをチェックする<br/>
     * (ブラウザバック後の選択出力などでの不具合防止のため)<br/>
     *
     * @param goodsRegistUpdateRelationsearchModel 関連商品設定検索
     * @return
     */
    private List<ValidatorMessage> resultListCheckAjax(GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel) {
        List<ValidatorMessage> validatorMessages = new ArrayList<>();
        if (!goodsRegistUpdateRelationsearchModel.isResultGoodsReration()) {
            return validatorMessages;
        }
        if (goodsRegistUpdateRelationsearchModel.getResultItems().get(0).getResultGoodsGroupCode() == null || "".equals(
                        goodsRegistUpdateRelationsearchModel.getResultItems().get(0).getResultGoodsGroupCode())) {
            goodsRegistUpdateRelationsearchModel.setResultGoodsRelationFlg(false);
            goodsRegistUpdateRelationsearchModel.setResultItems(null);
            MessageUtils.getAllMessage(validatorMessages, "AGG000906", null);
        }
        return validatorMessages;
    }

    /**
     * アクション前処理<br/>
     * inteceptorより呼出し<br/>
     *
     * @param abstModel
     * @param redirectAttributes
     * @param model
     */
    public String preDoAction(AbstractGoodsRegistUpdateModel abstModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        // 不正操作チェック
        return checkIllegalOperation(abstModel, redirectAttributes, model);
    }

    /**
     * 検索前 入力情報チェック<br/>
     * Validatorで対応できないもの
     */
    private void checkDataBeforeSearch(GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel) {

        // 検索キーワード10件超エラー
        if (goodsRegistUpdateRelationsearchModel.getSearchRelationGoodsKeyword() != null) {
            String[] searchKeywordArray =
                            goodsRegistUpdateRelationsearchModel.getSearchRelationGoodsKeyword().split("[\\s|　]+");
            if (searchKeywordArray.length > 10) {
                addErrorMessage("AGG000904", new Object[] {searchKeywordArray.length});
            }
        }

        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            throwMessage();
        }
    }

    /**
     * 検索前 入力情報チェック（Ajax用）<br/>
     * Validatorで対応できないもの
     *
     * @param goodsRegistUpdateRelationsearchModel 関連商品設定検索
     */
    private List<ValidatorMessage> checkDataBeforeSearchAjax(GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel) {
        List<ValidatorMessage> list = new ArrayList<>();
        // 検索キーワード10件超エラー
        if (goodsRegistUpdateRelationsearchModel.getSearchRelationGoodsKeyword() != null) {
            String[] searchKeywordArray =
                            goodsRegistUpdateRelationsearchModel.getSearchRelationGoodsKeyword().split("[\\s|　]+");
            if (searchKeywordArray.length > 10) {
                MessageUtils.getAllMessage(list, "AGG000904", new Object[] {searchKeywordArray.length});
            }
        }
        return list;
    }

    /**
     * 関連商品追加前 入力情報チェック<br/>
     * Validatorで対応できないもの
     */
    private void checkDataBeforeAddRelationGoods(GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel) {
        int selectedCount = 0;

        if (goodsRegistUpdateRelationsearchModel.getTmpGoodsRelationEntityList() != null) {
            int count = goodsRegistUpdateRelationsearchModel.getTmpGoodsRelationEntityList().size();

            boolean overMaxRelGoods = false;
            for (Iterator<GoodsRegistUpdateRelationsearchItem> it =
                 goodsRegistUpdateRelationsearchModel.getResultItems().iterator(); it.hasNext(); ) {
                GoodsRegistUpdateRelationsearchItem item = it.next();
                if (item.isResultCheck()) {
                    count++;
                    selectedCount++;

                    // 関連商品保持可能上限を超えるとエラー
                    if (!overMaxRelGoods && count > goodsRegistUpdateRelationsearchModel.getGoodsRelationAmount()) {
                        addErrorMessage("AGG000902",
                                        new Object[] {goodsRegistUpdateRelationsearchModel.getGoodsRelationAmount()}
                                       );
                        overMaxRelGoods = true;
                    }

                    // 更新時、自身の商品グループでないことを確認する
                    if (goodsRegistUpdateRelationsearchModel.getGoodsGroupDto().getGoodsGroupEntity() != null &&
                        goodsRegistUpdateRelationsearchModel.getGoodsGroupDto()
                                                            .getGoodsGroupEntity()
                                                            .getGoodsGroupCode() != null &&
                        goodsRegistUpdateRelationsearchModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq()
                        != null && item.getResultGoodsGroupCode()
                                       .equals(goodsRegistUpdateRelationsearchModel.getGoodsGroupDto()
                                                                                   .getGoodsGroupEntity()
                                                                                   .getGoodsGroupCode())) {
                        addErrorMessage("AGG000901");
                    }

                    // 同一商品グループがtmp関連商品リストにないことを確認する
                    for (GoodsRelationEntity goodsRelationEntity : goodsRegistUpdateRelationsearchModel.getTmpGoodsRelationEntityList()) {
                        if (item.getResultGoodsGroupCode().equals(goodsRelationEntity.getGoodsGroupCode())) {
                            // 関連商品重複チェックエラー
                            addErrorMessage("AGG000903", new Object[] {item.getResultGoodsGroupCode()});
                        }
                    }
                }
            }
        }

        // 選択してない場合はエラー
        if (selectedCount == 0) {
            addErrorMessage("AGG000907");
        }

        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            throwMessage();
        }

    }

    /**
     * 関連商品追加前 入力情報チェック(Ajax用)<br/>
     * Validatorで対応できないもの
     *
     * @param goodsRegistUpdateRelationsearchModel 関連商品設定検索
     */
    private List<ValidatorMessage> checkDataBeforeAddRelationGoodsAjax(GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel) {
        int selectedCount = 0;
        List<ValidatorMessage> validatorMessages = new ArrayList<>();
        if (goodsRegistUpdateRelationsearchModel.getTmpGoodsRelationEntityList() != null) {
            int count = goodsRegistUpdateRelationsearchModel.getTmpGoodsRelationEntityList().size();
            boolean overMaxRelGoods = false;
            for (Iterator<GoodsRegistUpdateRelationsearchItem> it =
                 goodsRegistUpdateRelationsearchModel.getResultItems().iterator(); it.hasNext(); ) {
                GoodsRegistUpdateRelationsearchItem item = it.next();
                if (item.isResultCheck()) {
                    count++;
                    selectedCount++;

                    // 関連商品保持可能上限を超えるとエラー
                    if (!overMaxRelGoods && count > goodsRegistUpdateRelationsearchModel.getGoodsRelationAmount()) {
                        MessageUtils.getAllMessage(validatorMessages, "AGG000902",
                                                   new Object[] {goodsRegistUpdateRelationsearchModel.getGoodsRelationAmount()}
                                                  );
                        overMaxRelGoods = true;
                    }

                    // 更新時、自身の商品グループでないことを確認する
                    if (goodsRegistUpdateRelationsearchModel.getGoodsGroupDto().getGoodsGroupEntity() != null &&
                        goodsRegistUpdateRelationsearchModel.getGoodsGroupDto()
                                                            .getGoodsGroupEntity()
                                                            .getGoodsGroupCode() != null &&
                        goodsRegistUpdateRelationsearchModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq()
                        != null && item.getResultGoodsGroupCode()
                                       .equals(goodsRegistUpdateRelationsearchModel.getGoodsGroupDto()
                                                                                   .getGoodsGroupEntity()
                                                                                   .getGoodsGroupCode())) {
                        MessageUtils.getAllMessage(validatorMessages, "AGG000901",
                                                   new Object[] {goodsRegistUpdateRelationsearchModel.getGoodsRelationAmount()}
                                                  );
                    }

                    // 同一商品グループがtmp関連商品リストにないことを確認する
                    for (GoodsRelationEntity goodsRelationEntity : goodsRegistUpdateRelationsearchModel.getTmpGoodsRelationEntityList()) {
                        if (item.getResultGoodsGroupCode().equals(goodsRelationEntity.getGoodsGroupCode())) {
                            // 関連商品重複チェックエラー
                            MessageUtils.getAllMessage(validatorMessages, "AGG000903",
                                                       new Object[] {item.getResultGoodsGroupCode()}
                                                      );
                        }
                    }
                }
            }
        }

        // 選択してない場合はエラー
        if (selectedCount == 0) {
            MessageUtils.getAllMessage(validatorMessages, "AGG000907", null);
        }
        return validatorMessages;
    }

    /**
     * カテゴリパスを保持するマップを作成<br/>
     */
    private void setCategoryPathMap(GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel) {
        // 検索条件
        CategorySearchForDaoConditionDto condition =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
        condition.setOrderField("categorypath");
        condition.setOrderAsc(true);

        // 検索
        List<CategoryEntity> list = categoryListGetService.execute(condition, HTypeSiteType.BACK);
        // 結果設定
        goodsRegistUpdateRelationsearchHelper.setCategoryPathMap(goodsRegistUpdateRelationsearchModel, list);
    }

    /**
     * コンポーネント値初期化
     *
     * @param goodsRegistUpdateRelationsearchModel
     */
    private void initComponentValue(GoodsRegistUpdateRelationsearchModel goodsRegistUpdateRelationsearchModel) {
        goodsRegistUpdateRelationsearchModel.setInputingFlg(true);
        goodsRegistUpdateRelationsearchModel.setSearchCategoryIdItems(divisionMapGetService.getCategoryMapList());
    }
}
