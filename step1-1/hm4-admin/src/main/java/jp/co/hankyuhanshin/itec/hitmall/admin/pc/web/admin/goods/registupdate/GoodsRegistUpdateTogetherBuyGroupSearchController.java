// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2008 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.ajax.MessageUtils;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.ajax.ValidatorMessage;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.AbstractGoodsRegistUpdateController;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.AbstractGoodsRegistUpdateModel;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRegisterMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.division.DivisionMapGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.CategoryListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsSearchResultListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.relation.GoodsRelationListGetForBackService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.togetherbuy.GoodsTogetherBuyGroupListGetForBackService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 商品管理：商品登録更新（よく一緒に購入される商品設定検索）アクション
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Controller
@RequestMapping(value = "/goods/registupdate/goodstogetherbuygroupsearch")
@SessionAttributes(value = "goodsRegistUpdateTogetherBuyGroupSearchModel")
@PreAuthorize("hasAnyAuthority('GOODS:8')")
public class GoodsRegistUpdateTogetherBuyGroupSearchController extends AbstractGoodsRegistUpdateController {

    /**
     * 商品登録更新ページ/>
     */
    public static final String FLASH_GOODS_REGIST_UPDATES_MODEL = "goodsRegistUpdateModel";

    /**
     * よく一緒に購入される商品設定検索ページ/>
     */
    public static final String FLASH_GOODS_TOGETHER_BUY_GROUP_SEARCH_MODEL =
                    "goodsRegistUpdateTogetherBuyGroupSearchModel";

    /**
     * Logger
     */
    private static final Logger LOGGER =
                    LoggerFactory.getLogger(GoodsRegistUpdateTogetherBuyGroupSearchController.class);

    /**
     * Helper
     */
    private final GoodsRegistUpdateTogetherBuyGroupSearchHelper goodsRegistUpdateTogetherBuyGroupSearchHelper;

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
     * @param goodsRegistUpdateTogetherBuyGroupSearchHelper
     * @param goodsSearchResultListGetService
     * @param categoryListGetService
     * @param divisionMapGetService
     */
    public GoodsRegistUpdateTogetherBuyGroupSearchController(GoodsGroupGetService goodsGroupGetService,
                                                             GoodsRelationListGetForBackService goodsRelationListGetForBackService,
                                                             GoodsRegistUpdateTogetherBuyGroupSearchHelper goodsRegistUpdateTogetherBuyGroupSearchHelper,
                                                             GoodsSearchResultListGetService goodsSearchResultListGetService,
                                                             CategoryListGetService categoryListGetService,
                                                             DivisionMapGetService divisionMapGetService,
                                                             GoodsTogetherBuyGroupListGetForBackService goodsTogetherBuyGroupListGetForBackService) {
        // 2023-renew No21 from here
        super(goodsGroupGetService, goodsRelationListGetForBackService, goodsTogetherBuyGroupListGetForBackService);
        // 2023-renew No21 to here
        this.goodsRegistUpdateTogetherBuyGroupSearchHelper = goodsRegistUpdateTogetherBuyGroupSearchHelper;
        this.goodsSearchResultListGetService = goodsSearchResultListGetService;
        this.categoryListGetService = categoryListGetService;
        this.divisionMapGetService = divisionMapGetService;
    }

    /**
     * よく一緒に購入される商品追加・初期表示用メソッド（Ajax用）<br/>
     *
     * @param goodsRegistUpdateModel               商品管理：商品登録更新ページ
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel 商品管理：商品登録更新（よく一緒に購入される商品設定検索）ページ
     * @param redirectAttributes
     * @param model
     * @return よく一緒に購入される商品情報
     */
    @PostMapping("/ajax")
    @ResponseBody
    public ResponseEntity<GoodsRegistUpdateTogetherBuyGroupSearchModel> doLoadPopupGoodsTogetherBuyGroupSearch(
                    @RequestBody GoodsRegistUpdateModel goodsRegistUpdateModel,
                    GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel,
                    RedirectAttributes redirectAttributes,
                    Model model) {

        // コンポーネント値初期化
        initComponentValue(goodsRegistUpdateTogetherBuyGroupSearchModel);
        if (ObjectUtils.isNotEmpty(goodsRegistUpdateModel)) {
            goodsRegistUpdateTogetherBuyGroupSearchModel.setGoodsGroupCode(goodsRegistUpdateModel.getGoodsGroupCode());
            goodsRegistUpdateTogetherBuyGroupSearchModel.setRedirectGoodsTogetherBuyGroupEntityList(
                            goodsRegistUpdateModel.getRedirectGoodsTogetherBuyGroupEntityList());
            goodsRegistUpdateTogetherBuyGroupSearchModel.setTmpGoodsTogetherBuyGroupEntityList(
                            goodsRegistUpdateModel.getTmpGoodsTogetherBuyGroupEntityList());
            goodsRegistUpdateTogetherBuyGroupSearchModel.setGoodsGroupDto(goodsRegistUpdateModel.getGoodsGroupDto());
            goodsRegistUpdateTogetherBuyGroupSearchModel.setGoodsTogetherBuyGroupEntityList(
                            goodsRegistUpdateModel.getGoodsTogetherBuyGroupEntityList());
            goodsRegistUpdateTogetherBuyGroupSearchModel.setScGoodsGroupSeq(
                            goodsRegistUpdateModel.getScGoodsGroupSeq());
        }

        // カテゴリパスを保持するマップを作成
        this.setCategoryPathMap(goodsRegistUpdateTogetherBuyGroupSearchModel);

        goodsRegistUpdateTogetherBuyGroupSearchHelper.toPageForLoad(goodsRegistUpdateTogetherBuyGroupSearchModel);
        // 検索条件がセッションに残っている場合は検索する
        if (goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsGroupSearchForDaoConditionDto() != null
            && goodsRegistUpdateTogetherBuyGroupSearchModel.isResultGoodsReration()) {
            GoodsSearchForBackDaoConditionDto conditionDto =
                            goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsGroupSearchForDaoConditionDto();
            // 検索
            List<GoodsSearchResultDto> resultDtoList =
                            goodsSearchResultListGetService.execute(conditionDto, HTypeSiteType.BACK);

            // ページにセット
            goodsRegistUpdateTogetherBuyGroupSearchHelper.toPageForSearch(
                            resultDtoList, goodsRegistUpdateTogetherBuyGroupSearchModel, conditionDto);
            goodsRegistUpdateTogetherBuyGroupSearchModel.setResultGoodsTogetherBuyGroupFlg(true);
        }
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateTogetherBuyGroupSearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            ResponseEntity.badRequest().body(check);
        }
        return ResponseEntity.ok(goodsRegistUpdateTogetherBuyGroupSearchModel);
    }

    /**
     * よく一緒に購入される商品追加処理（Ajax用）<br/>
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel 商品管理：商品登録更新（よく一緒に購入される商品設定検索）ページ
     * @param redirectAttributes
     * @param model
     * @return よく一緒に購入される商品情報
     */
    @PostMapping(value = "doSelectGoodsTogetherBuyGroupAjax")
    public ResponseEntity<?> doSelectGoodsTogetherBuyGroupAjax(GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel,
                                                               RedirectAttributes redirectAttributes,
                                                               Model model) {
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateTogetherBuyGroupSearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return ResponseEntity.badRequest().body(check);
        }
        // 検索結果チェック
        List<ValidatorMessage> validatorMessages1 = resultListCheckAjax(goodsRegistUpdateTogetherBuyGroupSearchModel);
        if (!CollectionUtils.isEmpty(validatorMessages1)) {
            return ResponseEntity.badRequest().body(validatorMessages1);
        }
        // よく一緒に購入される商品追加前チェック
        List<ValidatorMessage> validatorMessages =
                        checkDataBeforeAddGoodsTogetherBuyGroupAjax(goodsRegistUpdateTogetherBuyGroupSearchModel);
        if (!CollectionUtils.isEmpty(validatorMessages)) {
            return ResponseEntity.badRequest().body(validatorMessages);
        }
        goodsRegistUpdateTogetherBuyGroupSearchHelper.toPageForAddGoodsTogetherBuyGroup(
                        goodsRegistUpdateTogetherBuyGroupSearchModel);
        goodsRegistUpdateTogetherBuyGroupSearchHelper.toPageForNext(goodsRegistUpdateTogetherBuyGroupSearchModel);

        return ResponseEntity.ok(goodsRegistUpdateTogetherBuyGroupSearchModel);
    }

    /**
     * 戻る処理<br/>
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel
     * @param redirectAttributes
     * @param model
     * @return ページクラス
     */
    @PostMapping(value = "", params = "doReturn")
    public String doReturn(GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        // 実行前処理
        String check = preDoAction(goodsRegistUpdateTogetherBuyGroupSearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 選択商品情報を保持する
        goodsRegistUpdateTogetherBuyGroupSearchHelper.toPageForNext(goodsRegistUpdateTogetherBuyGroupSearchModel);

        redirectAttributes.addFlashAttribute(
                        FLASH_GOODS_TOGETHER_BUY_GROUP_SEARCH_MODEL, goodsRegistUpdateTogetherBuyGroupSearchModel);
        return "redirect:/goods/registupdate/";
    }

    /**
     * 検索イベント(Ajax用)<br/>
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel よく一緒に購入される商品設定検索
     * @param error
     * @param redirectAttributes
     * @param model
     * @return よく一緒に購入される商品情報
     */
    @PostMapping(value = "/post/ajax")
    public ResponseEntity<?> doSearchAjax(@Validated(SearchGroup.class)
                                          GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel,
                                          BindingResult error,
                                          RedirectAttributes redirectAttributes,
                                          Model model) {

        if (error.hasErrors()) {
            List<ValidatorMessage> mapError = MessageUtils.getMessageErrorFromBindingResult(error);
            return ResponseEntity.badRequest().body(mapError);
        }
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateTogetherBuyGroupSearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return ResponseEntity.badRequest().body(check);
        }
        // ブラウザバック対応 - カテゴリパスがnullの場合、マップを作成
        if (goodsRegistUpdateTogetherBuyGroupSearchModel.getCategoryPathMap() == null) {
            this.setCategoryPathMap(goodsRegistUpdateTogetherBuyGroupSearchModel);
        }
        // 検索前チェック
        List<ValidatorMessage> list = checkDataBeforeSearchAjax(goodsRegistUpdateTogetherBuyGroupSearchModel);
        if (!CollectionUtils.isEmpty(list)) {
            return ResponseEntity.badRequest().body(list);
        }

        // 検索条件作成
        GoodsSearchForBackDaoConditionDto conditionDto =
                        goodsRegistUpdateTogetherBuyGroupSearchHelper.toGoodsGroupSearchForDaoConditionDtoForSearch(
                                        goodsRegistUpdateTogetherBuyGroupSearchModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, "1", Integer.MAX_VALUE, "goodsGroupCode", true);

        // 検索条件をセッションに保存
        goodsRegistUpdateTogetherBuyGroupSearchModel.setGoodsGroupSearchForDaoConditionDto(conditionDto);

        // 検索
        List<GoodsSearchResultDto> resultDtoList =
                        goodsSearchResultListGetService.execute(conditionDto, HTypeSiteType.BACK);

        // ページにセット
        goodsRegistUpdateTogetherBuyGroupSearchHelper.toPageForSearch(
                        resultDtoList, goodsRegistUpdateTogetherBuyGroupSearchModel, conditionDto);

        goodsRegistUpdateTogetherBuyGroupSearchModel.setResultGoodsTogetherBuyGroupFlg(true);

        // 画面再表示
        goodsRegistUpdateTogetherBuyGroupSearchHelper.toPageForLoad(goodsRegistUpdateTogetherBuyGroupSearchModel);

        return ResponseEntity.ok(resultDtoList);
    }

    /**
     * キャンセルイベント<br/>
     * 未使用
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel
     * @param redirectAttributes
     * @param model
     * @return 遷移元
     */
    @PostMapping(value = "", params = "doCancel")
    public String doCancel(GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        // 実行前処理
        String check = preDoAction(goodsRegistUpdateTogetherBuyGroupSearchModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        // 戻り先画面(セッション保存用)がnullでない場合はそちらを使う
        if (goodsRegistUpdateTogetherBuyGroupSearchModel.getStoredBackPage() != null) {
            goodsRegistUpdateTogetherBuyGroupSearchModel.setBackPage(
                            goodsRegistUpdateTogetherBuyGroupSearchModel.getStoredBackPage());
        }
        // 新規登録または再利用元商品グループコードがあり、「商品詳細」画面から遷移してきた場合
        if ((!goodsRegistUpdateTogetherBuyGroupSearchModel.isRegistFlg()
             || goodsRegistUpdateTogetherBuyGroupSearchModel.getRecycledGoodsGroupCode() != null)) {
            // 商品詳細画面に商品グループコードを渡す
            if (goodsRegistUpdateTogetherBuyGroupSearchModel.getRecycledGoodsGroupCode() != null) {
                // 再利用時は再利用元商品グループコードを設定する
                goodsRegistUpdateTogetherBuyGroupSearchModel.setRedirectGoodsGroupCode(
                                goodsRegistUpdateTogetherBuyGroupSearchModel.getRecycledGoodsGroupCode());
            } else {
                goodsRegistUpdateTogetherBuyGroupSearchModel.setRedirectGoodsGroupCode(
                                goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsGroupCode());
            }
            return "redirect:/goods/details/";
        }
        // それ以外の場合は、「商品検索」画面に遷移
        return "redirect:/goods/";
    }

    /**
     * 商品検索結果リストが空でないことをチェックする<br/>
     * (ブラウザバック後の選択出力などでの不具合防止のため)<br/>
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel よく一緒に購入される商品設定検索
     * @return
     */
    private List<ValidatorMessage> resultListCheckAjax(GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel) {
        List<ValidatorMessage> validatorMessages = new ArrayList<>();
        if (!goodsRegistUpdateTogetherBuyGroupSearchModel.isResultGoodsReration()) {
            return validatorMessages;
        }
        if (goodsRegistUpdateTogetherBuyGroupSearchModel.getResultItems().get(0).getResultGoodsGroupCode() == null
            || "".equals(goodsRegistUpdateTogetherBuyGroupSearchModel.getResultItems()
                                                                     .get(0)
                                                                     .getResultGoodsGroupCode())) {
            goodsRegistUpdateTogetherBuyGroupSearchModel.setResultGoodsTogetherBuyGroupFlg(false);
            goodsRegistUpdateTogetherBuyGroupSearchModel.setResultItems(null);
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
     * 検索前 入力情報チェック（Ajax用）<br/>
     * Validatorで対応できないもの
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel よく一緒に購入される商品設定検索
     */
    private List<ValidatorMessage> checkDataBeforeSearchAjax(GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel) {
        List<ValidatorMessage> list = new ArrayList<>();
        // 検索キーワード10件超エラー
        if (goodsRegistUpdateTogetherBuyGroupSearchModel.getSearchGoodsTogetherBuyGroupKeyword() != null) {
            String[] searchKeywordArray =
                            goodsRegistUpdateTogetherBuyGroupSearchModel.getSearchGoodsTogetherBuyGroupKeyword()
                                                                        .split("[\\s|　]+");
            if (searchKeywordArray.length > 10) {
                MessageUtils.getAllMessage(list, "AGG000904", new Object[] {searchKeywordArray.length});
            }
        }
        return list;
    }

    /**
     * よく一緒に購入される商品追加前 入力情報チェック(Ajax用)<br/>
     * Validatorで対応できないもの
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel よく一緒に購入される商品設定検索
     */
    private List<ValidatorMessage> checkDataBeforeAddGoodsTogetherBuyGroupAjax(
                    GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel) {
        int selectedCount = 0;
        List<ValidatorMessage> validatorMessages = new ArrayList<>();
        if (goodsRegistUpdateTogetherBuyGroupSearchModel.getTmpGoodsTogetherBuyGroupEntityList() != null) {
            int count = goodsRegistUpdateTogetherBuyGroupSearchModel.getTmpGoodsTogetherBuyGroupEntityList().size();
            boolean overMaxRelGoods = false;
            for (Iterator<GoodsRegistUpdateTogetherBuyGroupSearchItem> it =
                 goodsRegistUpdateTogetherBuyGroupSearchModel.getResultItems().iterator(); it.hasNext(); ) {
                GoodsRegistUpdateTogetherBuyGroupSearchItem item = it.next();
                if (item.isResultCheck()) {
                    count++;
                    selectedCount++;

                    // よく一緒に購入される商品保持可能上限を超えるとエラー
                    if (!overMaxRelGoods
                        && count > goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsTogetherBuyAmount()) {
                        MessageUtils.getAllMessage(validatorMessages, "PDR-2023RENEW-21-003-",
                                                   new Object[] {goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsTogetherBuyAmount()}
                                                  );
                        overMaxRelGoods = true;
                    }

                    // 更新時、自身の商品グループでないことを確認する
                    if (goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsGroupDto().getGoodsGroupEntity() != null &&
                        goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsGroupDto()
                                                                    .getGoodsGroupEntity()
                                                                    .getGoodsGroupCode() != null &&
                        goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsGroupDto()
                                                                    .getGoodsGroupEntity()
                                                                    .getGoodsGroupSeq() != null
                        && item.getResultGoodsGroupCode()
                               .equals(goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsGroupDto()
                                                                                   .getGoodsGroupEntity()
                                                                                   .getGoodsGroupCode())) {
                        MessageUtils.getAllMessage(validatorMessages, "PDR-2023RENEW-21-002-",
                                                   new Object[] {goodsRegistUpdateTogetherBuyGroupSearchModel.getGoodsTogetherBuyAmount()}
                                                  );
                    }

                    // 同一商品グループがtmpよく一緒に購入される商品リストにないことを確認する
                    for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : goodsRegistUpdateTogetherBuyGroupSearchModel.getTmpGoodsTogetherBuyGroupEntityList()) {
                        if (HTypeRegisterMethodType.BACK.getValue().equals(item.getRegistMethod()) && item.getResultGoodsGroupCode().equals(goodsTogetherBuyGroupEntity.getGoodsGroupCode())) {
                            // よく一緒に購入される商品重複チェックエラー
                            MessageUtils.getAllMessage(validatorMessages, "PDR-2023RENEW-21-005-",
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
    private void setCategoryPathMap(GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel) {
        // 検索条件
        CategorySearchForDaoConditionDto condition =
                        ApplicationContextUtility.getBean(CategorySearchForDaoConditionDto.class);
        condition.setOrderField("categorypath");
        condition.setOrderAsc(true);

        // 検索
        List<CategoryEntity> list = categoryListGetService.execute(condition, HTypeSiteType.BACK);
        // 結果設定
        goodsRegistUpdateTogetherBuyGroupSearchHelper.setCategoryPathMap(
                        goodsRegistUpdateTogetherBuyGroupSearchModel, list);
    }

    /**
     * コンポーネント値初期化
     *
     * @param goodsRegistUpdateTogetherBuyGroupSearchModel 商品管理：商品登録更新（よく一緒に購入される商品設定検索）ページ
     */
    private void initComponentValue(GoodsRegistUpdateTogetherBuyGroupSearchModel goodsRegistUpdateTogetherBuyGroupSearchModel) {
        goodsRegistUpdateTogetherBuyGroupSearchModel.setInputingFlg(true);
        goodsRegistUpdateTogetherBuyGroupSearchModel.setSearchCategoryIdItems(
                        divisionMapGetService.getCategoryMapList());
    }
}
// 2023-renew No21 to here
