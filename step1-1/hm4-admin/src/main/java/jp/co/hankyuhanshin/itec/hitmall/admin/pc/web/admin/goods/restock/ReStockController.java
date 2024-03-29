/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayTopGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadTopGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.api.cuenote.CuenoteAPIConstant;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockOutData;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock.ReStockAnnounceMailDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiGetDeliveryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockAddImportListDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.restock.ReStockAnnounceMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiAddressImportLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiDeliveryReserveLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiGetDeliveryLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiMailSetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.mail.SendAdminMailLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.NoMailRequiredMemberInfoLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.LoginAdvisabilityGetCanNotLoginMemberLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.ReStockAddImportListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.ReStockAllCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.ReStockCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.ReStockSearchResultListGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.FileDownloadException;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.http.conn.ConnectTimeoutException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 入荷お知らせ商品検索コントロール
 *
 * @author st75001
 */
@RequestMapping("/goods/restock")
@Controller
@SessionAttributes(value = "reStockModel")
@PreAuthorize("hasAnyAuthority('GOODS:4')")
public class ReStockController extends AbstractReStockController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReStockController.class);

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * 入荷お知らせ商品検索：デフォルトページ番号
     */
    private static final String DEFAULT_RE_STOCK_SEARCH_PNUM = "1";

    /**
     * 入荷お知らせ商品検索：デフォルト：ソート項目
     */
    private static final String DEFAULT_RE_STOCK_ORDER_FIELD = "goodsGroupCode";

    /**
     * 入荷お知らせ商品検索：デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_RE_STOCK_ORDER_ASC = true;

    /**
     * 入荷お知らせ商品検索ヘルパー<br/>
     */
    private final ReStockHelper reStockHelper;

    /**
     * 入荷お知らせ商品検索結果リスト取得サービス<br/>
     */
    private final ReStockSearchResultListGetService reStockSearchResultListGetService;

    /**
     * 入荷お知らせ商品検索CSV一括ダウンロードサービス<br/>
     */
    private final ReStockAllCsvDownloadService reStockAllCsvDownloadService;

    /**
     * 入荷お知らせ商品CSVダウンロードサービス
     */
    private final ReStockCsvDownloadService reStockCsvDownloadService;

    /**
     * 入荷お知らせ商品検索結果リスト取得サービス
     */
    private final ReStockAddImportListGetService reStockAddImportListGetService;

    /**
     * Cuenote API 配信情報取得API logic
     */
    private final CuenoteApiGetDeliveryLogic cuenoteApiGetDeliveryLogic;

    /**
     * 管理者メール送信 logic
     */
    private final SendAdminMailLogic sendAdminMailLogic;

    /**
     * 日付関連Helper取得
     */
    private final DateUtility dateUtility;

    /**
     * 配信中判定
     */
    private final static String CHK_DELIVERING_ERROR_MESSAGE_CODE = "PDR-2023RENEW-0517-001-E";

    /**
     * 未送信判定
     */
    private final static String CHK_UNDELIVERED_ERROR_MESSAGE_CODE = "PDR-2023RENEW-0517-002-E";

    /**
     * 入荷済み判定
     */
    private final static String CHK_NO_RESTOCK_ERROR_MESSAGE_CODE = "PDR-2023RENEW-0517-003-E";

    /**
     * 入荷お知らせメール送信完了メッセージ
     */
    private final static String SEND_MAIL_COMPLETE_MESSAGE_CODE = "PDR-2023RENEW-0517-004-I";

    /**
     * メール送信状況確認完了メッセージ
     */
    private final static String SEND_MAIL_CONFIRM_CHK_MESSAGE_CODE = "PDR-2023RENEW-0517-005-I";

    /**
     * 配信中メール存在なし
     */
    private final static String NO_DELIVERED_RESTOCK_MAIL = "PDR-2023RENEW-0517-006-I";

    /**
     * メール配信状況確認失敗
     */
    private final static String CONFIRM_MAIL_ERROR_MESSAGE_CODE = "PDR-2023RENEW-0517-008-E";


    /**
     * チェック未選択メッセージ
     */
    private static final String MSGCD_SELECT_REQUIRED = "AOX001202W";

    /**
     * 処理名
     */
    private final static String PROCESS_NAME_DELIVERY_CONFIRM = "配信状況確認";

    /**
     * コンストラクター
     *
     * @param reStockHelper 入荷お知らせ商品検索Helper
     * @param reStockSearchResultListGetService 入荷お知らせ商品検索結果リスト取得サービス
     * @param reStockAllCsvDownloadService 入荷お知らせ商品CSV一括ダウンロードサービス
     * @param reStockCsvDownloadService 入荷お知らせ商品CSVダウンロードサービス
     * @param reStockAddImportListGetService 入荷お知らせ商品検索結果リスト取得サービス
     * @param cuenoteApiAddressImportLogic Cuenote API アドレス帳インポートAPI
     * @param cuenoteApiMailSetLogic Cuenote API メール文書セット複製API Logic
     * @param cuenoteApiDeliveryReserveLogic Cuenote API 配信情報予約API
     * @param reStockAnnounceMailDao 入荷お知らせメールDao
     * @param memberInfoDao 会員情報Dao
     * @param cuenoteApiGetDeliveryLogic Cuenote API 配信情報確認API
     */
    @Autowired
    public ReStockController(ReStockHelper reStockHelper,
                             ReStockSearchResultListGetService reStockSearchResultListGetService,
                             ReStockAllCsvDownloadService reStockAllCsvDownloadService,
                             ReStockCsvDownloadService reStockCsvDownloadService,
                             ReStockAddImportListGetService reStockAddImportListGetService,
                             CuenoteApiAddressImportLogic cuenoteApiAddressImportLogic,
                             CuenoteApiMailSetLogic cuenoteApiMailSetLogic,
                             CuenoteApiDeliveryReserveLogic cuenoteApiDeliveryReserveLogic,
                             CuenoteApiGetDeliveryLogic cuenoteApiGetDeliveryLogic,
                             SendAdminMailLogic sendAdminMailLogic,
                             AbstractReStockHelper abstractReStockHelper,
                             LoginAdvisabilityGetCanNotLoginMemberLogic loginAdvisabilityGetCanNotLoginMemberLogic,
                             NoMailRequiredMemberInfoLogic noMailRequiredMemberInfoLogic,
                             MemberInfoDao memberInfoDao,
                             ReStockAnnounceMailDao reStockAnnounceMailDao) {
        super(abstractReStockHelper
                , memberInfoDao
                , reStockAnnounceMailDao
                , cuenoteApiAddressImportLogic
                , cuenoteApiMailSetLogic
                , cuenoteApiDeliveryReserveLogic
                , sendAdminMailLogic
                , loginAdvisabilityGetCanNotLoginMemberLogic
                , noMailRequiredMemberInfoLogic);
        this.reStockHelper = reStockHelper;
        this.reStockSearchResultListGetService = reStockSearchResultListGetService;
        this.reStockAllCsvDownloadService = reStockAllCsvDownloadService;
        this.reStockCsvDownloadService = reStockCsvDownloadService;
        this.reStockAddImportListGetService = reStockAddImportListGetService;
        this.sendAdminMailLogic = sendAdminMailLogic;
        this.cuenoteApiGetDeliveryLogic = cuenoteApiGetDeliveryLogic;
        this.dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
    }

    /**
     * 画像表示処理<br/>
     * 初期表示用メソッド<br/>
     *
     * @param md
     * @param reStockModel
     * @param model
     * @return
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/restock/index")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> md, ReStockModel reStockModel, Model model) {

        // サブアプリケーション内の情報を初期化
        reStockModel.setInputingFlg(false);

        // 再検索の場合
        if (md.isPresent() || model.containsAttribute(FLASH_MD)) {
            // 再検索を実行
            search(reStockModel, model);
        } else {
            clearModel(ReStockModel.class, reStockModel, model);
        }

        // プルダウンアイテム情報を取得
        initDropDownValue(reStockModel);

        return "goods/restock/index";
    }

    /**
     * 検索イベント<br/>
     *
     * @param reStockModel
     * @param error
     * @param model
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doReStockSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/restock/index")
    public String doReStockSearch(@Validated(SearchGroup.class) ReStockModel reStockModel, BindingResult error, Model model) {
        if (error.hasErrors()) {
            return "goods/restock/index";
        }
        // 初期ソートと1ページをセット
        reStockModel.setOrderField(DEFAULT_RE_STOCK_ORDER_FIELD);
        reStockModel.setOrderAsc(DEFAULT_RE_STOCK_ORDER_ASC);
        reStockModel.setPageNumber(DEFAULT_RE_STOCK_SEARCH_PNUM);

        // 検索
        search(reStockModel, model);

        return "goods/restock/index";
    }

    /**
     * 検索結果の表示切替<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/restock/index")
    public String doDisplayChange(@Validated(DisplayChangeGroup.class) ReStockModel reStockModel,
                                  BindingResult error,
                                  Model model) {

        if (error.hasErrors()) {
            return "goods/restock/index";
        }

        // 検索結果チェック
        resultListCheck(reStockModel);

        // 検索条件作成
        search(reStockModel, model);

        return "goods/restock/index";
    }

    /**
     * 検索処理<br/>
     */
    protected void search(ReStockModel reStockModel, Model model) {

        // 検索条件作成
        ReStockSearchForBackDaoConditionDto reStockSearchForBackDaoConditionDto =
                        reStockHelper.toReStockSearchForBackDaoConditionDtoForSearch(reStockModel);

        if (reStockModel.getMsgCodeList() != null && !reStockModel.getMsgCodeList().isEmpty()) {
            List<String> msgCodeList = reStockModel.getMsgCodeList();
            for (String messageCode : msgCodeList) {
                // メッセージ引数マップに一致するメッセージコードがある場合は引数として設定する
                if (reStockModel.getMsgArgMap().containsKey(messageCode)) {
                    throwMessage(messageCode, reStockModel.getMsgArgMap().get(messageCode));
                } else {
                    throwMessage(messageCode);
                }
            }
        }

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(reStockSearchForBackDaoConditionDto, reStockModel.getPageNumber(),
                                     reStockModel.getLimit(), reStockModel.getOrderField(), reStockModel.isOrderAsc()
                                    );

        // 取得
        List<ReStockSearchResultDto> reStockSearchResultDtoList =
                        reStockSearchResultListGetService.execute(reStockSearchForBackDaoConditionDto, HTypeSiteType.BACK);

        // ページにセット
        reStockHelper.toPageForSearch(reStockSearchResultDtoList, reStockSearchForBackDaoConditionDto, reStockModel);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(reStockSearchForBackDaoConditionDto, reStockModel);
    }

    /**
     * CSVダウンロードイベント<br/>
     *
     * @param reStockModel
     * @param error
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doCsvDownloadAll")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/restock/index")
    @HEHandler(exception = FileDownloadException.class, returnView = "goods/restock/index")
    public void doCsvDownloadAll(@Validated(AllDownloadGroup.class) ReStockModel reStockModel,
                                 BindingResult error,
                                 HttpServletResponse response,
                                 Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        // 検索条件作成
       ReStockSearchForBackDaoConditionDto reStockSearchForBackDaoConditionDto =
                        reStockHelper.toReStockSearchForBackDaoConditionDtoForSearch(reStockModel);

       if (reStockModel.getMsgCodeList() != null && !reStockModel.getMsgCodeList().isEmpty()) {
           List<String> msgCodeList = reStockModel.getMsgCodeList();
           for (String messageCode : msgCodeList) {
               // メッセージ引数マップに一致するメッセージコードがある場合は引数として設定する
               if (reStockModel.getMsgArgMap().containsKey(messageCode)) {
                   throwMessage(messageCode, reStockModel.getMsgArgMap().get(messageCode));
               } else {
                   throwMessage(messageCode);
               }
           }
       }

        // 検索条件に基づいて会員CSV一括出力
        try {
            reStockAllCsvDownloadService.execute(reStockSearchForBackDaoConditionDto, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }
    }

    /**
     * CSVダウンロードイベント(検索結果上のボタン)
     *
     * @param reStockModel
     * @param error
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doCsvDownloadSelectTop")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/restock/index")
    @HEHandler(exception = FileDownloadException.class, returnView = "goods/restock/index")
    public void doCsvDownloadSelectTop(@Validated(DownloadTopGroup.class) ReStockModel reStockModel,
                                       BindingResult error,
                                       HttpServletResponse response,
                                       Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        try {
            csvDownloadSelect(reStockModel, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }
    }

    /**
     * CSV選択ダウンロード<br/>
     *
     * @param reStockModel
     * @param response
     */
    protected void csvDownloadSelect(ReStockModel reStockModel, HttpServletResponse response) throws IOException {

        // 検索結果チェック
        resultListCheck(reStockModel);

        // チェックボックスから、チェックされた商品を取得する。
        List<String> keyList = reStockHelper.toKeyList(reStockModel);

        // チェック選択なし
        if (keyList.isEmpty()) {
            throwMessage("AGG000102");
        }

        // 検索条件に基づいて会員CSV一括出力
        reStockCsvDownloadService.execute(false, keyList, response);

    }

    /**
     * 商品検索結果リストが空でないことをチェックする<br/>
     * (ブラウザバック後の選択出力などでの不具合防止のため)<br/>
     */
    protected void resultListCheck(ReStockModel reStockModel) {
        if (!reStockModel.isResult() || reStockModel.getResultItems().size() == 0) {
            return;
        }
        if (StringUtil.isEmpty(reStockModel.getResultItems().get(0).getResultGoodsGroupCode())) {
            reStockModel.setResultItems(null);
            this.throwMessage("AGG000103");
        }
    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param reStockModel モデル
     */
    protected void initDropDownValue(ReStockModel reStockModel) {

        // プルダウンアイテム情報を取得
        reStockModel.setReStockOutDataAllItems(EnumTypeUtil.getEnumMap(HTypeReStockOutData.class));
        reStockModel.setReStockOutDataSelectTopItems(EnumTypeUtil.getEnumMap(HTypeReStockOutData.class));
        reStockModel.setReStockStatusItems(EnumTypeUtil.getEnumMap(HTypeReStockStatus.class));
        reStockModel.setDeliveryStatusItems(EnumTypeUtil.getEnumMap(HTypeMailDeliveryStatus.class));
    }

    /**
     * 入荷お知らせメール送信イベント
     *
     * @param reStockModel
     * @param error
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doReStockSendMail")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/restock/index")
    public String doReStockSendMail(@Validated(DisplayTopGroup.class) ReStockModel reStockModel,
                                       BindingResult error,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {

        // 更新対象
        List<ReStockAnnounceMailEntity> reStockAnnounceMailEntityList = new ArrayList<>();

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }
        // 検索結果リストが空でないことをチェック
        resultListCheck(reStockModel);

        if (!reStockModel.isResult() || reStockModel.getResultItems().size() == 0) {
            this.throwMessage(MSGCD_SELECT_REQUIRED, new String[]{"メール送信対象"});
        }else{
            // 全ての商品の中で、入荷メール送信状態=「送信中」のものが1件でも存在する場合
            List<ReStockAnnounceMailEntity> reStockAnnounceMailDeliveringEntityList = reStockAnnounceMailDao.getReStockAnnounceMailDeliveryedList();
            if (reStockAnnounceMailDeliveringEntityList.size() > 0){
                throwMessage(CHK_DELIVERING_ERROR_MESSAGE_CODE);
            }
        }

        // チェックボックスから、チェックされた商品を取得する。
        List<ReStockResultItem> tmpReStockResultItem = reStockHelper.toSendMailList(reStockModel);

        // チェック選択なし
        if (tmpReStockResultItem.isEmpty()) {
            this.throwMessage(MSGCD_SELECT_REQUIRED, new String[] {"メール送信対象"});
        }

        List<String> keyList = new ArrayList<>();
        for (ReStockResultItem reStockResultItem : tmpReStockResultItem) {
            // チェックされた商品の中で、入荷メール送信状態≠「未配信」のものが１件でも存在する場合
            if (!HTypeMailDeliveryStatus.UNDELIVERED.getValue().equals(reStockResultItem.getResultDeliveryStatus())) {
                throwMessage(CHK_UNDELIVERED_ERROR_MESSAGE_CODE);
            }
            // チェックされた商品の中で、入荷状態≠入荷済み　でない場合
            if (!HTypeReStockStatus.RESTOCK.getValue().equals(reStockResultItem.getResultReStockStatus())) {
                throwMessage(CHK_NO_RESTOCK_ERROR_MESSAGE_CODE);
            }

            // 更新対象の入荷お知らせメールを取得
            List<ReStockAnnounceMailEntity> tmpReStockAnnounceMailEntityList = reStockAnnounceMailDao.getReStockAnnounceMailEntityGoodsSeqList(reStockResultItem.getResultGoodsSeq());

            for (ReStockAnnounceMailEntity reStockAnnounceMailEntity : tmpReStockAnnounceMailEntityList) {
                if (HTypeMailDeliveryStatus.UNDELIVERED.equals(reStockAnnounceMailEntity.getDeliveryStatus())) {
                    reStockAnnounceMailEntityList.add(reStockAnnounceMailEntity);
                }
            }
            keyList.add(reStockResultItem.getResultKey());
        }

        List<ReStockAddImportListDto> adImportReqDtoList = new ArrayList<>();
        List<String> tmpKey = new ArrayList<>();
        int cnt = 0;
        for (String key: keyList){
            tmpKey.add(key);
            // 全件をSQLのIN句に追加すると件数次第でエラーとなる為、20000件ずつ分けて実行する。
            if((cnt > 0 && cnt%20000 == 0) || cnt == keyList.size() - 1){
                // メール配信対象を取得
                List<ReStockAddImportListDto> tmpAdImportReqDtoList = reStockAddImportListGetService.execute(false, tmpKey);
                adImportReqDtoList.addAll(tmpAdImportReqDtoList);
                tmpKey = new ArrayList<>();
            }
            cnt++;
        }

        // cuenote配信予約
        cuenoteMailReserve(adImportReqDtoList, reStockAnnounceMailEntityList);

        addInfoMessage(SEND_MAIL_COMPLETE_MESSAGE_CODE, null, redirectAttributes, model);
        search(reStockModel, model);
        return "goods/restock/index";
    }

    /**
     * 入荷お知らせメール送信状況確認イベント
     *
     * @param reStockModel
     * @param error
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doSentMailConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/restock/index")
    public String doSentMailConfirm(@Validated(DisplayTopGroup.class) ReStockModel reStockModel,
                                    BindingResult error,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {

        // 更新対象
        List<ReStockAnnounceMailEntity> reStockAnnounceMailEntityList = reStockAnnounceMailDao.getReStockAnnounceMailDeliveryedList();
        if (reStockAnnounceMailEntityList.size() == 0){
            throwMessage(NO_DELIVERED_RESTOCK_MAIL);
        }
        CuenoteApiGetDeliveryResponseDto cuenoteApiGetDeliveryResponseDto = new CuenoteApiGetDeliveryResponseDto();
        // 配信中の配信ＩＤは原則一件のみ
        String deliveryId = reStockAnnounceMailEntityList.get(0).getDeliveryId();
        String exceptionName;
        try {
            // 配信情報取得API実行
            cuenoteApiGetDeliveryResponseDto = cuenoteApiGetDeliveryLogic.execute(deliveryId);

            // エラーの場合
            if(cuenoteApiGetDeliveryResponseDto == null){
                LOGGER.error("APIエラー");
                throwMessage();
            }
        } catch (SocketTimeoutException | ConnectTimeoutException e) {
            LOGGER.error("タイムアウト例外処理が発生しました", e);

            exceptionName = "配信情報取得" + API_ERROR_TIMEOUT_MESSAGE;
            mailMessageDetail = sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_CONFIRM, API_ERROR_TIMEOUT_RECOVERY_MESSAGE);
            // メール送信
            sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_CONFIRM, HTypeMailTemplateType.DELIVERY_CONFIRM_ERROR, true);
            throwMessage(CONFIRM_MAIL_ERROR_MESSAGE_CODE);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);

            exceptionName = "配信情報取得" + API_ERROR_MESSAGE;
            mailMessageDetail = sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_CONFIRM, API_ERROR_RECOVERY_MESSAGE);

            // メール送信
            sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_CONFIRM, HTypeMailTemplateType.DELIVERY_CONFIRM_ERROR, true);
            throwMessage(CONFIRM_MAIL_ERROR_MESSAGE_CODE);
        }

        HTypeMailDeliveryStatus deliveryStatus = deliveryStatusReflection(cuenoteApiGetDeliveryResponseDto, reStockAnnounceMailEntityList);

        // 処理結果詳細生成
        // メールメッセージ初期化
        mailMessageDetail = sendAdminMailLogic.createSuccessDeliveryConfirmMailDetail(cuenoteApiGetDeliveryResponseDto, deliveryStatus);
        // メール送信
        sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_CONFIRM, HTypeMailTemplateType.DELIVERY_CONFIRM, false);

        addInfoMessage(SEND_MAIL_CONFIRM_CHK_MESSAGE_CODE, null, redirectAttributes, model);
        search(reStockModel, model);
        return "goods/restock/index";
    }

    /**
     * 配信状況反映
     *
     * @param cuenoteApiGetDeliveryResponseDto 配信情報取得APIレスポンスDTO
     * @param reStockAnnounceMailEntityList 入荷お知らせメール更新対象
     * @return 配信状況
     */
    protected HTypeMailDeliveryStatus deliveryStatusReflection(CuenoteApiGetDeliveryResponseDto cuenoteApiGetDeliveryResponseDto, List<ReStockAnnounceMailEntity> reStockAnnounceMailEntityList) {
        // 配信ステータスのチェック
        HTypeMailDeliveryStatus deliveryStatus = null;
        Timestamp currentTime = dateUtility.getCurrentTime();
        // ステータスが配信中かをチェック
        switch (cuenoteApiGetDeliveryResponseDto.getMtaStatus()){
            case CuenoteAPIConstant.MTA_STATUS_WAIT:
            case CuenoteAPIConstant.MTA_STATUS_PREPARE:
            case CuenoteAPIConstant.MTA_STATUS_PREPARING:
            case CuenoteAPIConstant.MTA_STATUS_DELIVERING:
                deliveryStatus =  HTypeMailDeliveryStatus.DELIVERING;
                break;
        }

        // 失敗件数があればステータスを配信失敗とする。
        if(deliveryStatus == null){
            if(cuenoteApiGetDeliveryResponseDto.getStatFailure() + cuenoteApiGetDeliveryResponseDto.getStatDeferral() == 0) {
                deliveryStatus = HTypeMailDeliveryStatus.DELIVERED;
            }else{
                deliveryStatus = HTypeMailDeliveryStatus.FAILED;
            }
        }

        Timestamp deliveryEndTime = null;
        if(HTypeMailDeliveryStatus.DELIVERED.equals(deliveryStatus)) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(cuenoteApiGetDeliveryResponseDto.deliveryEndTime, formatter);
            deliveryEndTime = Timestamp.from(offsetDateTime.toInstant());
        }

        // 入荷お知らせメールを更新
        for (ReStockAnnounceMailEntity reStockAnnounceMailEntity : reStockAnnounceMailEntityList ) {
            reStockAnnounceMailEntity.setDeliveryStatus(deliveryStatus);
            if(HTypeMailDeliveryStatus.DELIVERED.equals(deliveryStatus)) {
                reStockAnnounceMailEntity.setDeliveryTime(deliveryEndTime);
            }
            reStockAnnounceMailEntity.setUpdateTime(currentTime);
            reStockAnnounceMailDao.update(reStockAnnounceMailEntity);
        }

        return deliveryStatus;
    }
}
