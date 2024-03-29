/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.freearea.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.UploadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.holiday.bundledupload.DeliveryHolidayBundledUploadModel;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.freearea.registupdate.validation.FreeareaRegistUpdateValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUkFeedInfoSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaTargetGoodsCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaViewableMemberCountGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaViewableMemberCsvDownloadLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaViewableMemberCsvUploadLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaViewableMemberDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaViewableMemberRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea.FreeAreaGetForBackService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea.FreeAreaRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea.FreeAreaUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.FileDownloadException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadError;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvValidationResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.InvalidDetail;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.FileOperationUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * フリーエリア登録・更新画面
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@SessionAttributes(value = "freeareaRegistUpdateModel")
@RequestMapping("/freearea")
@Controller
@PreAuthorize("hasAnyAuthority('SHOP:4')")
public class FreeareaRegistUpdateController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FreeareaRegistUpdateController.class);

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "ASF000202";
    /**
     * メッセージコード：更新中データ削除
     */
    protected static final String MSGCD_DATA_NOT_EXIST = "ASF000203";
    /**
     * メッセージコード：バリデーション失敗
     */
    protected static final String MSGCD_CSV_INVALID = "PKG-3642-001-A-";

    /**
     * フリーエリア本文PC
     */
    public static final String FLASH_FREEAREA_BODY_PC = "redirectFreeareaBodyPC";

    /**
     * フリーエリアタイトル
     */
    public static final String FLASH_FREEAREA_TITLE_PC = "redirectFreeareaTitlePC";

    /**
     * helper
     */
    private FreeareaRegistUpdateHelper freeareaRegistUpdateHelper;

    /**
     * フリーエリア取得サービス
     */
    private FreeAreaGetForBackService freeAreaGetForBackService;

    /**
     * フリーエリア取得ロジック
     */
    private FreeAreaGetLogic freeAreaGetLogic;

    /**
     * フリーエリア表示対象会員件数取得ロジック
     */
    public FreeAreaViewableMemberCountGetLogic freeAreaViewableMemberCountGetLogic;

    /**
     * フリーエリア表示対象会員アップロードロジック
     */
    public FreeAreaViewableMemberCsvUploadLogic freeAreaViewableMemberCsvUploadLogic;

    /**
     * フリーエリア表示対象会員ダウンロードロジック
     */
    public FreeAreaViewableMemberCsvDownloadLogic freeAreaViewableMemberCsvDownloadLogic;

    /**
     * 対象商品チェックロジック
     */
    private FreeAreaTargetGoodsCheckLogic freeAreaTargetGoodsCheckLogic;

    /**
     * フリーエリア登録サービス
     */
    private FreeAreaRegistService freeAreaRegistService;

    /**
     * フリーエリア更新サービス
     */
    private FreeAreaUpdateService freeAreaUpdateService;

    /**
     * フリーエリア表示対象会員登録ロジック
     */
    private FreeAreaViewableMemberRegistLogic freeAreaViewableMemberRegistLogic;

    /**
     * フリーエリア表示対象会員削除ロジック
     */
    private FreeAreaViewableMemberDeleteLogic freeAreaViewableMemberDeleteLogic;

    /**
     * 表示対象顧客番号アップロードの動的バリデータ
     */
    private FreeareaRegistUpdateValidator freeareaRegistUpdateValidator;

    /**
     * コンストラクタ
     *
     * @param freeareaRegistUpdateHelper
     * @param freeAreaGetForBackService
     * @param freeAreaGetLogic
     * @param freeAreaViewableMemberCountGetLogic
     * @param freeAreaViewableMemberCsvUploadLogic
     * @param freeAreaViewableMemberCsvDownloadLogic
     * @param freeAreaTargetGoodsCheckLogic
     * @param freeAreaRegistService
     * @param freeAreaUpdateService
     * @param freeAreaViewableMemberRegistLogic
     * @param freeAreaViewableMemberDeleteLogic
     * @param freeareaRegistUpdateValidator
     */
    @Autowired
    public FreeareaRegistUpdateController(FreeareaRegistUpdateHelper freeareaRegistUpdateHelper,
                                          FreeAreaGetForBackService freeAreaGetForBackService,
                                          FreeAreaGetLogic freeAreaGetLogic,
                                          FreeAreaViewableMemberCountGetLogic freeAreaViewableMemberCountGetLogic,
                                          FreeAreaViewableMemberCsvUploadLogic freeAreaViewableMemberCsvUploadLogic,
                                          FreeAreaViewableMemberCsvDownloadLogic freeAreaViewableMemberCsvDownloadLogic,
                                          FreeAreaTargetGoodsCheckLogic freeAreaTargetGoodsCheckLogic,
                                          FreeAreaRegistService freeAreaRegistService,
                                          FreeAreaUpdateService freeAreaUpdateService,
                                          FreeAreaViewableMemberRegistLogic freeAreaViewableMemberRegistLogic,
                                          FreeAreaViewableMemberDeleteLogic freeAreaViewableMemberDeleteLogic,
                                          FreeareaRegistUpdateValidator freeareaRegistUpdateValidator) {
        this.freeareaRegistUpdateHelper = freeareaRegistUpdateHelper;
        this.freeAreaGetForBackService = freeAreaGetForBackService;
        this.freeAreaGetLogic = freeAreaGetLogic;
        this.freeAreaViewableMemberCountGetLogic = freeAreaViewableMemberCountGetLogic;
        this.freeAreaViewableMemberCsvUploadLogic = freeAreaViewableMemberCsvUploadLogic;
        this.freeAreaViewableMemberCsvDownloadLogic = freeAreaViewableMemberCsvDownloadLogic;
        this.freeAreaTargetGoodsCheckLogic = freeAreaTargetGoodsCheckLogic;
        this.freeAreaRegistService = freeAreaRegistService;
        this.freeAreaUpdateService = freeAreaUpdateService;
        this.freeAreaViewableMemberRegistLogic = freeAreaViewableMemberRegistLogic;
        this.freeAreaViewableMemberDeleteLogic = freeAreaViewableMemberDeleteLogic;
        this.freeareaRegistUpdateValidator = freeareaRegistUpdateValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder error) {
        // 表示対象顧客番号アップロードの動的バリデータをセット
        error.addValidators(freeareaRegistUpdateValidator);
    }

    /**
     * 初期処理
     *
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @GetMapping(value = "/registupdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "freearea/registupdate/index")
    public String doLoadIndex(@RequestParam(required = false) Optional<Integer> freeAreaSeq,
                              FreeareaRegistUpdateModel freeareaRegistUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // フリーエリアデータ存在チェック
        String check = preDoAction(freeareaRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // プルダウンアイテム情報を取得
        initComponentValue(freeareaRegistUpdateModel);

        FreeAreaEntity freeAreaEntity = null;

        // 確認画面からの遷移でなければ
        if (!freeareaRegistUpdateModel.isFromConfirm()) {
            // モデルのクリア処理
            clearModel(FreeareaRegistUpdateModel.class, freeareaRegistUpdateModel, model);
            initComponentValue(freeareaRegistUpdateModel);

            // 指定時
            if (freeAreaSeq.isPresent()) {

                // 更新の場合再検索フラグをセット
                try {
                    freeAreaEntity = freeAreaGetForBackService.execute(freeAreaSeq.get());
                } catch (Exception e) {
                    // 取得失敗時、エラー画面へ遷移
                    LOGGER.error("例外処理が発生しました", e);
                    return "redirect:/error";
                }
                // 変更前情報
                freeareaRegistUpdateModel.setOriginalFreeAreaEntity(CopyUtil.deepCopy(freeAreaEntity));
                freeareaRegistUpdateModel.setViewableMemberCount(
                                freeAreaViewableMemberCountGetLogic.execute(freeAreaSeq.get()));
            } else {
                freeAreaEntity = ApplicationContextUtility.getBean(FreeAreaEntity.class);
                freeareaRegistUpdateModel.setOriginalFreeAreaEntity(null);
                freeareaRegistUpdateModel.setViewableMemberCount(0);
            }
            freeareaRegistUpdateModel.setViewableMemberList(null);
        }

        // ページ反映
        freeareaRegistUpdateHelper.toPageForLoad(freeareaRegistUpdateModel, freeAreaEntity);

        // 修正画面の場合、画面用フリーエリアSEQを設定
        if (freeareaRegistUpdateModel.getFreeAreaEntity() != null
            && freeareaRegistUpdateModel.getFreeAreaEntity().getFreeAreaSeq() != null) {
            freeareaRegistUpdateModel.setScFreeAreaSeq(freeareaRegistUpdateModel.getFreeAreaEntity().getFreeAreaSeq());
        }

        // フラグリセット
        freeareaRegistUpdateModel.setFromConfirm(false);

        return "freearea/registupdate/index";
    }

    /**
     * 確認画面へ遷移
     *
     * @return 確認画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/registupdate", params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "freearea/registupdate/index")
    public String doConfirm(@Validated(ConfirmGroup.class) FreeareaRegistUpdateModel freeareaRegistUpdateModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        if (error.hasErrors()) {
            return "freearea/registupdate/index";
        }

        // 不正操作チェック
        if (!freeareaRegistUpdateModel.isNormality()) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/freearea/";
        }

        // フリーエリアデータ存在チェック
        String check = preDoAction(freeareaRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        freeareaRegistUpdateHelper.toPageForConfirm(freeareaRegistUpdateModel);

        // 対象商品のチェック
        freeAreaTargetGoodsCheckLogic.execute(freeareaRegistUpdateModel.getTargetGoods());

        return "redirect:/freearea/registupdate/confirm";
    }

    /**
     * アップロード
     *
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/registupdate", params = "doUpload")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/freearea/registupdate/")
    public String doUpload(@Validated(UploadGroup.class) FreeareaRegistUpdateModel freeareaRegistUpdateModel,
                           BindingResult error,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        if (error.hasErrors()) {
            return "freearea/registupdate/index";
        }

        // CSVHelper取得
        CsvUtility csvUtility = ApplicationContextUtility.getBean(CsvUtility.class);

        // ファイル操作Helper取得
        FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);

        // 不正操作チェック
        if (!freeareaRegistUpdateModel.isNormality()) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/freearea/";
        }

        // フリーエリアデータ存在チェック
        String check = preDoAction(freeareaRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // アップロードファイルをテンプファイルとして保存
        String tmpFileName = csvUtility.getUploadCsvTmpFileName("freeAreaViewableMember");

        try {
            fileOperationUtility.put(freeareaRegistUpdateModel.getUploadFile(), tmpFileName);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(DeliveryHolidayBundledUploadModel.MSGCD_FAIL_DELETE, redirectAttributes, model);
            return "freearea/registupdate/index";
        }

        // ファイルを作成
        File upLoadFile = new File(tmpFileName);

        // アップロードサービス実行
        csvUpload(freeareaRegistUpdateModel, upLoadFile);

        // エラーを表示
        if (hasErrorMessage()) {
            throwMessage();
        }

        // 正常終了時
        return "freearea/registupdate/index";
    }

    /**
     * CSVファイルをアップロード
     */
    protected void csvUpload(FreeareaRegistUpdateModel freeareaRegistUpdateModel, File upLoadFile) {

        // アップロードサービス実行
        List<Integer> memberList = new ArrayList<>();
        CsvUploadResult uploadResult =
                        freeAreaViewableMemberCsvUploadLogic.execute(upLoadFile, CsvUploadResult.CSV_UPLOAD_VALID_LIMIT,
                                                                     CsvUploadResult.CSV_UPLOAD_VALID_RECORD, memberList
                                                                    );

        // CSV に不具合がある場合は画面遷移せずエラーを表示
        // バリデーションエラーを追加
        if (uploadResult.isInValid()) {
            CsvValidationResult vResult = uploadResult.getCsvValidationResult();
            for (InvalidDetail detail : vResult.getInvalidDetailList()) {
                addErrorMessage(MSGCD_CSV_INVALID, new String[] {detail.getRow().toString(), detail.getMessage()});
            }
            return;
        }

        // エラーを追加
        if (uploadResult.isError()) {
            List<CsvUploadError> errList = uploadResult.getCsvUploadErrorlList();
            for (CsvUploadError result : errList) {
                addErrorMessage(result.getMessageCode(), result.getArgs());
            }
            return;
        }
        freeareaRegistUpdateModel.setViewableMemberList(memberList);
        freeareaRegistUpdateModel.setViewableMemberCount(memberList.size());
    }

    /**
     * ダウンロード
     *
     * @param freeareaRegistUpdateModel
     * @param error
     * @param model
     * @return 自画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/registupdate", params = "doDownload")
    @HEHandler(exception = AppLevelListException.class, returnView = "freearea/registupdate/index")
    @HEHandler(exception = FileDownloadException.class, returnView = "freearea/registupdate/index")
    public void doDownload(@Validated(AllDownloadGroup.class) FreeareaRegistUpdateModel freeareaRegistUpdateModel,
                           BindingResult error,
                           HttpServletResponse response,
                           Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        Integer freeAreaSeq = freeareaRegistUpdateModel.getFreeAreaEntity().getFreeAreaSeq();
        // 新規登録時存在しないフリーエリアを指定（出力データ0件）
        if (freeAreaSeq == null) {
            freeAreaSeq = 0;
        }

        try {
            freeAreaViewableMemberCsvDownloadLogic.execute(freeAreaSeq, response);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }
    }

    /**
     * 確認画面：初期処理
     *
     * @param freeareaRegistUpdateModel フリーエリア登録・更新画面
     * @param model                     Model
     * @return 自画面(エラー時 、 検索画面)
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @GetMapping(value = "/registupdate/confirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "freearea/registupdate/confirm")
    public String doLoadConfirm(FreeareaRegistUpdateModel freeareaRegistUpdateModel,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // フリーエリアデータ存在チェック
        String check = preDoAction(freeareaRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (this.hasErrorInput(freeareaRegistUpdateModel)) {
            // 入力値の整合性が取れない場合、検索画面へ
            return "redirect:/freearea/";
        }
        // 修正の場合、画面用フリーエリアSEQを設定
        if (freeareaRegistUpdateModel.getFreeAreaEntity().getFreeAreaSeq() != null) {
            freeareaRegistUpdateModel.setScFreeAreaSeq(freeareaRegistUpdateModel.getFreeAreaEntity().getFreeAreaSeq());

            // 入力値からエンティティを作成（変更後データ）
            FreeAreaEntity modifiedFreeAreaEntity =
                            freeareaRegistUpdateHelper.toFreeAreaEntityForNewsRegist(freeareaRegistUpdateModel);

            // 変更前データと変更後データの差異リスト作成
            List<String> modifiedList = DiffUtil.diff(freeareaRegistUpdateModel.getOriginalFreeAreaEntity(),
                                                      modifiedFreeAreaEntity
                                                     );
            freeareaRegistUpdateModel.setModifiedList(modifiedList);
        }
        return "freearea/registupdate/confirm";
    }

    /**
     * フリーエリア登録更新処理
     * 正常終了後はフリーエリア検索画面へ遷移
     *
     * @return フリーエリア検索画面
     */
    @PreAuthorize("hasAnyAuthority('SHOP:8')")
    @PostMapping(value = "/registupdate/confirm", params = "doOnceFreeAreaRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "freearea/registupdate/confirm")
    public String doOnceFreeAreaRegist(FreeareaRegistUpdateModel freeareaRegistUpdateModel,
                                       RedirectAttributes redirectAttributes,
                                       SessionStatus sessionStatus,
                                       Model model) {

        // フリーエリアデータ存在チェック
        String check = preDoAction(freeareaRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (this.hasErrorInput(freeareaRegistUpdateModel)) {
            // 入力値の整合性が取れない場合、検索画面へ
            return "redirect:/freearea/";
        }

        // 画面内容反映
        FreeAreaEntity freeAreaEntity =
                        freeareaRegistUpdateHelper.toFreeAreaEntityForNewsRegist(freeareaRegistUpdateModel);

        // 対象商品のチェック
        freeAreaTargetGoodsCheckLogic.execute(freeAreaEntity.getTargetGoods());

        // フリーエリアSEQを保持していない場合
        if (freeAreaEntity.getFreeAreaSeq() == null) {
            // 登録処理
            freeAreaRegistService.execute(freeAreaEntity);

        } else {
            // 更新処理
            freeAreaUpdateService.execute(freeAreaEntity);

            if (freeareaRegistUpdateModel.getViewableMemberList() != null) {
                freeAreaViewableMemberDeleteLogic.execute(freeAreaEntity.getFreeAreaSeq());
            }

            // 再検索フラグをセット
            redirectAttributes.addFlashAttribute(FLASH_MD, MODE_LIST);
        }

        if (CollectionUtil.isNotEmpty(freeareaRegistUpdateModel.getViewableMemberList())) {
            freeAreaViewableMemberRegistLogic.execute(
                            freeAreaEntity.getFreeAreaSeq(), freeareaRegistUpdateModel.getViewableMemberList());
        }

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "redirect:/freearea/";
    }

    /**
     * 登録更新画面へ遷移
     *
     * @return 登録更新画面
     */
    @PostMapping(value = "/registupdate/confirm", params = "doCancel")
    @HEHandler(exception = AppLevelListException.class, returnView = "freearea/registupdate/confirm")
    public String doCancel(FreeareaRegistUpdateModel freeareaRegistUpdateModel,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        // フリーエリアデータ存在チェック
        String check = preDoAction(freeareaRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        freeareaRegistUpdateModel.setFromConfirm(true);

        return "redirect:/freearea/registupdate";
    }

    /**
     * プレビュー画面：初期処理
     *
     * @param previewScreen
     * @param freeAreaSeq
     * @param freeareaRegistUpdateModel
     * @param redirectAttributes
     * @param model
     * @return プレビュー画面
     */
    @GetMapping(value = "/registupdate/preview")
    @HEHandler(exception = AppLevelListException.class, returnView = "freearea/registupdate/preview")
    protected String doLoadPreview(@RequestParam(required = false) Optional<String> previewScreen,
                                   @RequestParam(required = false) Optional<Integer> freeAreaSeq,
                                   FreeareaRegistUpdateModel freeareaRegistUpdateModel,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        if (freeAreaSeq.isPresent() & previewScreen.isPresent() && "index".equals(previewScreen.get())) {
            // フリーエリア取得
            FreeAreaEntity freeAreaEntity = freeAreaGetForBackService.execute(freeAreaSeq.get());

            if (freeAreaEntity == null) {
                // 特集ページ用フリーエリアが非公開または存在しない
                LOGGER.debug("存在しない、もしくは公開されていない特集ページが選択されました");
                return "redirect:/error";
            }

            freeareaRegistUpdateHelper.toPageForLoadPreview(freeareaRegistUpdateModel, freeAreaEntity);

        }

        return "freearea/registupdate/previewpc";
    }

    /**
     * アクション実行前処理
     *
     * @param freeareaRegistUpdateModel フリーエリア登録・更新画面Model
     * @param redirectAttributes        RedirectAttributes
     * @param model                     Model
     * @return チェック結果（チェックNGの場合は、遷移先画面Viewを返却。チェックOK時はNULL返却）
     */
    public String preDoAction(FreeareaRegistUpdateModel freeareaRegistUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        String returnView = null;
        // 不正操作チェック
        returnView = checkIllegalOperation(freeareaRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(returnView)) {
            return returnView;
        }

        // フリーエリアデータ存在チェック
        returnView = checkFreeAreaDataExist(freeareaRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(returnView)) {
            return returnView;
        }

        // チェックＯＫ（null返却）
        return null;
    }

    /**
     * 不正操作チェック
     *
     * @param freeareaRegistUpdateModel フリーエリア登録・更新画面Model
     * @param redirectAttributes        RedirectAttributes
     * @param model                     Model
     * @return チェック結果（チェックNGの場合は、遷移先画面Viewを返却。チェックOK時はNULL返却）
     */
    protected String checkIllegalOperation(FreeareaRegistUpdateModel freeareaRegistUpdateModel,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {
        Integer scFreeAreaSeq = freeareaRegistUpdateModel.getScFreeAreaSeq();
        Integer dbFreeAreaSeq = null;
        if (freeareaRegistUpdateModel.getFreeAreaEntity() != null) {
            dbFreeAreaSeq = freeareaRegistUpdateModel.getFreeAreaEntity().getFreeAreaSeq();
        }

        boolean isError = false;

        if (scFreeAreaSeq == null && dbFreeAreaSeq != null) {
            // 登録画面にも関わらず、フリーエリアSEQのDB情報を保持している場合エラー
            isError = true;

        } else if (scFreeAreaSeq != null && dbFreeAreaSeq == null) {
            // 修正画面にも関わらず、フリーエリアSEQのDB情報を保持していない場合エラー
            isError = true;

        } else if (scFreeAreaSeq != null && !scFreeAreaSeq.equals(dbFreeAreaSeq)) {
            // 画面用フリーエリアSEQとDB用フリーエリアSEQが異なる場合エラー
            isError = true;
        }

        if (isError) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/freearea/";
        }
        // エラーがない場合はnull返却
        return null;
    }

    /**
     * フリーエリアデータ存在チェック<br />
     * フリーエリア更新時に該当フリーエリアが削除されていた場合、エラーメッセージを登録する
     *
     * @param freeareaRegistUpdateModel フリーエリア登録・更新画面Model
     * @param redirectAttributes        RedirectAttributes
     * @param model                     Model
     * @return チェック結果（チェックNGの場合は、遷移先画面Viewを返却。チェックOK時はNULL返却）
     */
    protected String checkFreeAreaDataExist(FreeareaRegistUpdateModel freeareaRegistUpdateModel,
                                            RedirectAttributes redirectAttributes,
                                            Model model) {
        // フリーエリアSEQ取得
        // ※FreeAreaEntityにインスタンスが設定されている場合のみ
        Integer freeAreaSeq = null;
        if (freeareaRegistUpdateModel.getFreeAreaEntity() != null) {
            freeAreaSeq = freeareaRegistUpdateModel.getFreeAreaEntity().getFreeAreaSeq();
        }
        if (freeAreaSeq == null) {
            // 新規登録処理の場合（フリーエリアSEQを保持していない場合）、チェック終了
            return null;
        }
        // 編集中フリーエリア取得
        FreeAreaEntity freeAreaEntity =
                        freeAreaGetLogic.execute(getCommonInfo().getCommonInfoBase().getShopSeq(), freeAreaSeq);
        if (freeAreaEntity == null) {
            // 編集中フリーエリアが削除されている場合、エラー
            String appComplementUrl = PropertiesUtil.getSystemPropertiesValue("app.complement.url");
            addMessage(MSGCD_DATA_NOT_EXIST, new Object[] {appComplementUrl}, redirectAttributes, model);
            return "freearea/registupdate/index";
        }
        return null;
    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param freeareaRegistUpdateModel フリーエリア登録・更新画面
     */
    private void initComponentValue(FreeareaRegistUpdateModel freeareaRegistUpdateModel) {
        freeareaRegistUpdateModel.setSiteMapFlagItems(EnumTypeUtil.getEnumMap(HTypeSiteMapFlag.class));
        // 2023-renew No36-1, No61,67,95 from here
        freeareaRegistUpdateModel.setUkFeedInfoSendFlagItems(EnumTypeUtil.getEnumMap(HTypeUkFeedInfoSendFlag.class));
        // 2023-renew No36-1, No61,67,95 to here
    }

    /**
     * 必須保持値・入力項目の有無で整合性をチェックする
     *
     * @return true:エラーあり false:エラーなし
     */
    private boolean hasErrorInput(FreeareaRegistUpdateModel freeareaRegistUpdateModel) {

        FreeAreaEntity freeAreaEntity = freeareaRegistUpdateModel.getFreeAreaEntity();
        if (freeAreaEntity == null) {
            return true;
        }

        if (freeareaRegistUpdateModel.getFreeAreaKey() == null || freeareaRegistUpdateModel.getOpenStartDate() == null
            || freeareaRegistUpdateModel.getOpenStartTime() == null) {
            return true;
        }

        return false;

    }

}
