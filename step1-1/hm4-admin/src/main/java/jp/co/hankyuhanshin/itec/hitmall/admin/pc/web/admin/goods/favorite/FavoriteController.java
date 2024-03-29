/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.favorite;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadBottomGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadTopGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.GoodsController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFavoriteOutData;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFavoriteSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.favorite.FavoriteAllCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.favorite.FavoriteCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.favorite.FavoriteSearchResultListGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.FileDownloadException;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * お気に入り商品検索コントロール
 *
 * @author takashima
 */
@RequestMapping("/goods/favorite")
@Controller
@SessionAttributes(value = "favoriteModel")
@PreAuthorize("hasAnyAuthority('GOODS:4')")
public class FavoriteController extends AbstractController {
    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * お気に入り商品検索：デフォルトページ番号
     */
    private static final String DEFAULT_GOODSSEARCH_PNUM = "1";

    /**
     * お気に入り商品検索：デフォルト：ソート項目
     */
    private static final String DEFAULT_GOODSSEARCH_ORDER_FIELD = "goodsGroupCode";

    /**
     * お気に入り商品検索：デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_GOODSSEARCH_ORDER_ASC = true;

    /**
     * お気に入り商品検索ヘルパー<br/>
     */
    private final FavoriteHelper favoriteHelper;

    /**
     * お気に入り商品検索結果リスト取得サービス<br/>
     */
    private final FavoriteSearchResultListGetService favoriteSearchResultListGetService;

    /**
     * お気に入り商品検索CSV一括ダウンロードサービス<br/>
     */
    private final FavoriteAllCsvDownloadService favoriteAllCsvDownloadService;

    /**
     * お気に入り商品検索CSVダウンロードサービス<br/>
     */
    private final FavoriteCsvDownloadService favoriteCsvDownloadService;

    /**
     * コンストラクター
     *  @param favoriteHelper
     * @param favoriteSearchResultListGetService
     * @param favoriteAllCsvDownloadService
     * @param favoriteCsvDownloadService
     */
    @Autowired
    public FavoriteController(FavoriteHelper favoriteHelper,
                              FavoriteSearchResultListGetService favoriteSearchResultListGetService,
                              FavoriteAllCsvDownloadService favoriteAllCsvDownloadService,
                              FavoriteCsvDownloadService favoriteCsvDownloadService) {
        this.favoriteHelper = favoriteHelper;
        this.favoriteSearchResultListGetService = favoriteSearchResultListGetService;
        this.favoriteAllCsvDownloadService = favoriteAllCsvDownloadService;
        this.favoriteCsvDownloadService = favoriteCsvDownloadService;
    }

    /**
     * 画像表示処理<br/>
     * 初期表示用メソッド<br/>
     *
     * @param md
     * @param favoriteModel
     * @param model
     * @return
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/favorite/index")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> md,
                              FavoriteModel favoriteModel,
                              Model model) {

        // サブアプリケーション内の情報を初期化
        favoriteModel.setInputingFlg(false);

        // プルダウンアイテム情報を取得
        initDropDownValue(favoriteModel);

        return "goods/favorite/index";
    }

    /**
     * 検索イベント<br/>
     *
     * @param favoriteModel
     * @param error
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doFavoriteSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/favorite/index")
    public String doFavoriteSearch(@Validated(SearchGroup.class) FavoriteModel favoriteModel,
                                   BindingResult error,
                                   Model model) {
        if (error.hasErrors()) {
            return "goods/favorite/index";
        }
        // 初期ソートと1ページをセット
        favoriteModel.setOrderField(DEFAULT_GOODSSEARCH_ORDER_FIELD);
        favoriteModel.setOrderAsc(DEFAULT_GOODSSEARCH_ORDER_ASC);
        favoriteModel.setPageNumber(DEFAULT_GOODSSEARCH_PNUM);

        // 検索
        search(favoriteModel, model);

        return "goods/favorite/index";
    }

    /**
     * 検索結果の表示切替<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/favorite/index")
    public String doDisplayChange(@Validated(DisplayChangeGroup.class) FavoriteModel favoriteModel,
                                  BindingResult error,
                                  Model model) {

        if (error.hasErrors()) {
            return "goods/favorite/index";
        }

        // 検索結果チェック
        resultListCheck(favoriteModel);

        // 検索条件作成
        search(favoriteModel, model);

        return "goods/favorite/index";
    }

    /**
     * 検索処理<br/>
     */
    protected void search(FavoriteModel favoriteModel, Model model) {

        // 検索条件作成
        FavoriteSearchForBackDaoConditionDto favoriteSearchForBackDaoConditionDto =
                        favoriteHelper.toFavoriteSearchForBackDaoConditionDtoSearch(favoriteModel);

        //商品番号　複数検索のエラーチェック
        goodsCodeErrorCheck(favoriteModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(favoriteSearchForBackDaoConditionDto, favoriteModel.getPageNumber(),
                                     favoriteModel.getLimit(), favoriteModel.getOrderField(), favoriteModel.isOrderAsc()
                                    );

        // 取得
        List<FavoriteSearchResultDto> favoriteSearchResultDtoList =
                        favoriteSearchResultListGetService.execute(favoriteSearchForBackDaoConditionDto,
                                                                   HTypeSiteType.BACK
                                                                  );
        // ページにセット
        favoriteHelper.toPageForSearch(
                        favoriteSearchResultDtoList, favoriteSearchForBackDaoConditionDto, favoriteModel);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(favoriteSearchForBackDaoConditionDto, favoriteModel);
    }

    /**
     * CSVダウンロードイベント<br/>
     *
     * @param favoriteModel
     * @param error
     * @return
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doCsvDownloadAll")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/favorite/index")
    @HEHandler(exception = FileDownloadException.class, returnView = "goods/favorite/index")
    public void doCsvDownloadAll(@Validated(AllDownloadGroup.class) FavoriteModel favoriteModel,
                                 BindingResult error,
                                 HttpServletResponse response,
                                 Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        // 検索条件作成
        FavoriteSearchForBackDaoConditionDto favoriteSearchForBackDaoConditionDto =
                        favoriteHelper.toFavoriteSearchForBackDaoConditionDtoSearch(favoriteModel);

        //商品番号　複数検索のエラーチェック
        goodsCodeErrorCheck(favoriteModel);

        // 検索条件に基づいて会員CSV一括出力
        try {
            favoriteAllCsvDownloadService.execute(favoriteSearchForBackDaoConditionDto, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }
    }

    /**
     * CSVダウンロードイベント(検索結果上のボタン)
     *
     * @param favoriteModel
     * @param error
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doCsvDownloadSelectTop")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/favorite/index")
    @HEHandler(exception = FileDownloadException.class, returnView = "goods/favorite/index")
    public void doCsvDownloadSelectTop(@Validated(DownloadTopGroup.class) FavoriteModel favoriteModel,
                                       BindingResult error,
                                       HttpServletResponse response,
                                       Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        try {
            csvDownloadSelect(favoriteModel, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }
    }

    /**
     * CSVダウンロードイベント(検索結果下のボタン)
     *
     * @param favoriteModel
     * @param error
     * @return
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doCsvDownloadSelectBottom")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/favorite/index")
    @HEHandler(exception = FileDownloadException.class, returnView = "goods/favorite/index")
    public void doCsvDownloadSelectBottom(@Validated(DownloadBottomGroup.class) FavoriteModel favoriteModel,
                                          BindingResult error,
                                          HttpServletResponse response,
                                          Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        try {
            csvDownloadSelect(favoriteModel, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }
    }

    /**
     * CSV選択ダウンロード<br/>
     *
     * @param favoriteModel
     * @param response
     */
    protected void csvDownloadSelect(FavoriteModel favoriteModel, HttpServletResponse response) throws IOException {

        // 検索結果チェック
        resultListCheck(favoriteModel);

        // チェックボックスから、チェックされた商品SEQを取得する。
        List<String> favoriteSeqList = favoriteHelper.toFavoriteSeqList(favoriteModel);

        // チェック選択なし
        if (favoriteSeqList.isEmpty()) {
            throwMessage("AGG000102");
        }

        // 検索条件に基づいて会員CSV一括出力
        favoriteCsvDownloadService.execute(favoriteSeqList, response);

    }

    /**
     * お気に入り商品検索結果リストが空でないことをチェックする<br/>
     * (ブラウザバック後の選択出力などでの不具合防止のため)<br/>
     */
    protected void resultListCheck(FavoriteModel favoriteModel) {
        if (!favoriteModel.isResult() || favoriteModel.getResultItems().size() == 0) {
            return;
        }
        if (StringUtil.isEmpty(favoriteModel.getResultItems().get(0).getGoodsGroupCode())) {
            favoriteModel.setResultItems(null);
            this.throwMessage("AGG000103");
        }
    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param favoriteModel お気に入り商品検索モデル
     */
    protected void initDropDownValue(FavoriteModel favoriteModel) {

        // プルダウンアイテム情報を取得
        favoriteModel.setFavoriteSaleStatusItems(EnumTypeUtil.getEnumMap(HTypeFavoriteSaleStatus.class));
        favoriteModel.setFavoriteOutDataAllItems(EnumTypeUtil.getEnumMap(HTypeFavoriteOutData.class));
    }

    /**
     *　商品番号　複数検索のエラーメッセージを取得
     */
    private void goodsCodeErrorCheck(FavoriteModel favoriteModel) {

        // 検索条件の最新化 エラーがある場合は、終了
        if (favoriteModel.getMsgCodeList() != null && !favoriteModel.getMsgCodeList().isEmpty()) {
            List<String> msgCodeList = favoriteModel.getMsgCodeList();
            for (String messageCode : msgCodeList) {
                // メッセージ引数マップに一致するメッセージコードがある場合は引数として設定する
                if (favoriteModel.getMsgArgMap().containsKey(messageCode)) {
                    throwMessage(messageCode, favoriteModel.getMsgArgMap().get(messageCode));
                } else {
                    throwMessage(messageCode);
                }
            }
        }
    }

}
