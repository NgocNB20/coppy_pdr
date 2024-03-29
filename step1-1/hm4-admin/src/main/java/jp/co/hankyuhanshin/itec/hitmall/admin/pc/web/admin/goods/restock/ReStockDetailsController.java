/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayTopGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadTopGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.GoodsDetailsModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockOutData;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockStatus;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock.ReStockAnnounceMailDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.MemberInfoDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockAddImportListDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockDetailsResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.restock.ReStockAnnounceMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiAddressImportLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiDeliveryReserveLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiMailSetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.mail.SendAdminMailLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.NoMailRequiredMemberInfoLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.LoginAdvisabilityGetCanNotLoginMemberLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.ReStockAddImportListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.ReStockCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.restock.ReStockDetailsResultListGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.FileDownloadException;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 入荷お知らせ商品検索コントロール
 *
 * @author st75001
 */
@RequestMapping("/goods/restock/details")
@Controller
@SessionAttributes(value = "reStockDetailsModel")
@PreAuthorize("hasAnyAuthority('GOODS:4')")
public class ReStockDetailsController extends AbstractReStockController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReStockDetailsController.class);

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * 入荷お知らせ商品詳細ヘルパー<br/>
     */
    private final ReStockDetailsHelper reStockDetailsHelper;

    /**
     * 入荷お知らせ商品詳細結果リスト取得サービス<br/>
     */
    private final ReStockDetailsResultListGetService reStockDetailsResultListGetService;

    /**
     * 入荷お知らせ商品CSVダウンロードサービス
     */
    private final ReStockCsvDownloadService reStockCsvDownloadService;

    /**
     * 入荷お知らせメールアドレス帳登録情報取得サービス
     */
    private final ReStockAddImportListGetService reStockAddImportListGetService;

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
     * チェック未選択メッセージ
     */
    private static final String MSGCD_SELECT_REQUIRED = "AOX001202W";

    /**
     * コンストラクター
     *
     * @param reStockCsvDownloadService 入荷お知らせ商品CSVダウンロードサービス
     * @param reStockAddImportListGetService 入荷お知らせ商品検索結果リスト取得サービス
     * @param cuenoteApiAddressImportLogic Cuenote API アドレス帳インポートAPI
     * @param cuenoteApiMailSetLogic Cuenote API メール文書セット複製API Logic
     * @param cuenoteApiDeliveryReserveLogic Cuenote API 配信情報予約API
     */
    @Autowired
    public ReStockDetailsController(ReStockDetailsHelper reStockDetailsHelper,
                                    ReStockDetailsResultListGetService reStockDetailsResultListGetService,
                                    ReStockCsvDownloadService reStockCsvDownloadService,
                                    ReStockAddImportListGetService reStockAddImportListGetService,
                                    CuenoteApiAddressImportLogic cuenoteApiAddressImportLogic,
                                    CuenoteApiMailSetLogic cuenoteApiMailSetLogic,
                                    CuenoteApiDeliveryReserveLogic cuenoteApiDeliveryReserveLogic,
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
        this.reStockDetailsHelper = reStockDetailsHelper;
        this.reStockDetailsResultListGetService = reStockDetailsResultListGetService;
        this.reStockCsvDownloadService = reStockCsvDownloadService;
        this.reStockAddImportListGetService = reStockAddImportListGetService;
    }

    /**
     * 画像表示処理<br/>
     * 初期表示用メソッド<br/>
     *
     * @param key キー（商品SEQ+入荷状態+配信ID+配信状況)
     * @param reStockDetailsModel 入荷お知らせ商品詳細情報モデル
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/restock/details")
    public String doLoadIndex(@RequestParam(required = true) Optional<String> key,
                              ReStockDetailsModel reStockDetailsModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // モデル初期化
        clearModel(GoodsDetailsModel.class, reStockDetailsModel, model);

        if (key.isPresent()) {
            reStockDetailsModel.setRedirectKey(key.get());
        }

        // データを取得
        List<ReStockDetailsResultDto> reStockDetailsResultDto = reStockDetailsResultListGetService.execute(reStockDetailsModel.getRedirectKey());

        // 存在しない場合エラー
        if (reStockDetailsResultDto == null) {
            addMessage("AGG000002", redirectAttributes, model);
            return "redirect:/error";
        }

        // ページにセット
        reStockDetailsHelper.toPageForDetails(reStockDetailsResultDto, reStockDetailsModel);

        // プルダウンアイテム情報を取得
        initDropDownValue(reStockDetailsModel);

        return "goods/restock/details";
    }

    /**
     * 戻るイベント<br/>
     *
     * @param reStockDetailsModel
     * @param redirectAttributes
     * @param sessionStatus
     * @param model
     * @return 遷移元
     */
    @PostMapping(value = "/", params = "doBack")
    public String doBack(ReStockDetailsModel reStockDetailsModel,
                         RedirectAttributes redirectAttributes,
                         SessionStatus sessionStatus,
                         Model model) {

        // 再検索フラグをセット
        redirectAttributes.addFlashAttribute(FLASH_MD, MODE_LIST);
        // Modelをセッションより破棄
        sessionStatus.setComplete();
        return "redirect:/goods/restock/";
    }

    /**
     * CSVダウンロードイベント
     *
     * @param reStockDetailsModel
     * @param error
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doCsvDownloadSelectTop")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/restock/details")
    @HEHandler(exception = FileDownloadException.class, returnView = "goods/restock/details")
    public void doCsvDownloadSelectTop(@Validated(DownloadTopGroup.class) ReStockDetailsModel reStockDetailsModel,
                                       BindingResult error,
                                       HttpServletResponse response,
                                       Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        try {
            csvDownloadSelect(reStockDetailsModel, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }
    }

    /**
     * CSV選択ダウンロード<br/>
     *
     * @param reStockDetailsModel
     * @param response
     */
    protected void csvDownloadSelect(ReStockDetailsModel reStockDetailsModel, HttpServletResponse response) throws IOException {

        // 検索結果チェック
        resultListCheck(reStockDetailsModel);

        // チェックボックスから、チェックされた商品を取得する。
        List<String> keyList = reStockDetailsHelper.toKeyList(reStockDetailsModel);

        // チェック選択なし
        if (keyList.isEmpty()) {
            throwMessage("AGG000102");
        }

        // 検索条件に基づいて会員CSV一括出力
        reStockCsvDownloadService.execute(true, keyList, response);

    }

    /**
     * リストが空でないことをチェックする<br/>
     * (ブラウザバック後の選択出力などでの不具合防止のため)<br/>
     */
    protected void resultListCheck(ReStockDetailsModel reStockDetailsModel) {
        if (!reStockDetailsModel.isResult() || reStockDetailsModel.getResultItems().size() == 0) {
            return;
        }
        if (StringUtil.isEmpty(reStockDetailsModel.getResultItems().get(0).getResultKey())) {
            reStockDetailsModel.setResultItems(null);
            this.throwMessage("AGG000103");
        }
    }

    /**
     * 入荷お知らせメール送信イベント
     *
     * @param reStockDetailsModel
     * @param error
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('GOODS:8')")
    @PostMapping(value = "/", params = "doReStockSendMail")
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/restock/details")
    public String doReStockSendMail(@Validated(DisplayTopGroup.class) ReStockDetailsModel reStockDetailsModel,
                                       BindingResult error,
                                       RedirectAttributes redirectAttributes,
                                       SessionStatus sessionStatus,
                                       Model model) {

        // 更新対象
        List<ReStockAnnounceMailEntity> reStockAnnounceMailEntityList = new ArrayList<>();

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }
        // リストが空でないことをチェック
        resultListCheck(reStockDetailsModel);

        if (!reStockDetailsModel.isResult() || reStockDetailsModel.getResultItems().size() == 0) {
            this.throwMessage(MSGCD_SELECT_REQUIRED, new String[]{"メール送信対象"});
        }else{
            // 全ての商品の中で、入荷メール送信状態=「送信中」のものが1件でも存在する場合
            List<ReStockAnnounceMailEntity> reStockAnnounceMailDeliveringEntityList = reStockAnnounceMailDao.getReStockAnnounceMailDeliveryedList();
            if (reStockAnnounceMailDeliveringEntityList.size() > 0){
                throwMessage(CHK_DELIVERING_ERROR_MESSAGE_CODE);
            }
        }

        // チェックボックスから、チェックされた会員を取得する。
        List<ReStockDetailsResultItem> tmpReStockDetailsResultItem = reStockDetailsHelper.toSendMailList(reStockDetailsModel);

        // チェック選択なし
        if (tmpReStockDetailsResultItem.isEmpty()) {
            this.throwMessage(MSGCD_SELECT_REQUIRED, new String[] {"メール送信対象"});
        }

        List<String> keyList = new ArrayList<>();
        // 入荷状態≠入荷済み　でない場合
        if (!HTypeReStockStatus.RESTOCK.equals(reStockDetailsModel.getReStockStatus())) {
            throwMessage(CHK_NO_RESTOCK_ERROR_MESSAGE_CODE);
        }
        for (ReStockDetailsResultItem reStockDetailsResultItem : tmpReStockDetailsResultItem) {
            // チェックされた商品の中で、入荷メール送信状態≠「未配信」のものが１件でも存在する場合
            if (!HTypeMailDeliveryStatus.UNDELIVERED.getValue().equals(reStockDetailsResultItem.getDeliveryStatus())) {
                throwMessage(CHK_UNDELIVERED_ERROR_MESSAGE_CODE);
            }

            // 更新対象の入荷お知らせメールを取得
            ReStockAnnounceMailEntity tmpReStockAnnounceMailEntity = reStockAnnounceMailDao.getEntity(reStockDetailsModel.getGoodsSeq()
                    , reStockDetailsResultItem.getMemberInfoSeq(), reStockDetailsResultItem.getVersionNo());

            if (HTypeMailDeliveryStatus.UNDELIVERED.equals(tmpReStockAnnounceMailEntity.getDeliveryStatus())) {
                reStockAnnounceMailEntityList.add(tmpReStockAnnounceMailEntity);
            }

            keyList.add(reStockDetailsResultItem.getResultKey());
        }

        // メール配信対象を取得
        List<ReStockAddImportListDto> adImportReqDtoList = reStockAddImportListGetService.execute(true, keyList);

        // cuenote配信予約
        cuenoteMailReserve(adImportReqDtoList, reStockAnnounceMailEntityList);

        addInfoMessage(SEND_MAIL_COMPLETE_MESSAGE_CODE, null, redirectAttributes, model);

        // 検索画面に遷移
        // 再検索フラグをセット
        redirectAttributes.addFlashAttribute(FLASH_MD, MODE_LIST);
        // Modelをセッションより破棄
        sessionStatus.setComplete();
        return "redirect:/goods/restock/";
    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param reStockDetailsModel モデル
     */
    protected void initDropDownValue(ReStockDetailsModel reStockDetailsModel) {

        // プルダウンアイテム情報を取得
        reStockDetailsModel.setReStockOutDataSelectTopItems(EnumTypeUtil.getEnumMap(HTypeReStockOutData.class));
    }
}
