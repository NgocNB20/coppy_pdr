package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.UploadDocsGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.memberinfo.validation.UploadDocsValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.service.common.impl.HmFrontUserDetailsServiceImpl;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccountingType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCashDeliveryUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfDocumentType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCreditPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDirectDebitUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDrugSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMedicalEquipmentSalesType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberListType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMonthlyPayUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineLoginAdvisability;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTransferPaymentUseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadExtension;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.image.MemberImageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member.WebApiAddUserInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetCutomerNoNextValLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.salesadvisability.CheckConfDocumentLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiAddUserInformationLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoDataCheckService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoDetailsGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoDetailsUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoProcessCompleteMailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image.MemberInfoDeleteImageService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image.MemberInfoListImageService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image.MemberInfoSaveImageService;
import jp.co.hankyuhanshin.itec.hitmall.service.zipcode.ZipCodeMatchingCheckService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 会員情報変更 Controller
 */
@RequestMapping("/memberinfo/update/")
@Controller
@SessionAttributes(value = {"memberInfoUpdateModel"})
@PreAuthorize("hasAnyAuthority('MEMBER:8')")
public class MemberInfoUpdateController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoUpdateController.class);

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    private static final String REDIRECT_ERROR_PAGE = "redirect:/error";

    private static final String MEMBER_INFO_UPDATE_INDEX_PAGE = "memberinfo/update/index";

    private static final String MEMBER_INFO_DETAILS_INDEX_PAGE = "memberinfo/details";

    /**
     * TEL・FAXのみ会員の承認状態を「未承認」から「承認」にした場合のメッセージ<br/>
     * <code>MSGCD_MEMBER_APPROVESTATUS_TELFAX</code>
     */
    public static final String MSGCD_MEMBER_APPROVESTATUS_TELFAX = "PDR-0195-001-A-";

    /**
     * 確認書類が使用不可であるときのメッセージ
     */
    public static final String MSGCD_MEMBER_NOT_SETTABLE_CONFDOCUMENT = "PDR-0437-001-A-";

    /**
     * 都道府県と郵便番号の整合性エラー:AMR000406
     * 定数にWを付与
     */
    protected static final String MSGCD_PREFECTURE_CONSISTENCY = "AMR000406W";

    /**
     * メッセージコード：入会中にもかかわらず、退会日が入力されている場合のエラーメッセージ
     */
    public static final String MSGCD_SECESSION_DATE_UNNECESSARY = "AMX000405";

    /**
     * メッセージコード：入会日が未来日付になっている場合のエラーメッセージ
     */
    public static final String MSGCD_ADMISSION_DATE_MUST_NOT_BE_FUTURE = "AMX000406";

    /**
     * アカウントロック解除の成功メッセージ
     */
    public static final String MSGCD_SUCCESSFUL_UNLOCK = "AMX000403";

    /**
     * 確認画面から
     */
    public static final String FLASH_FROM_CONFIRM = "confirm";

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * 会員情報変更 Helper
     */
    private final MemberInfoUpdateHelper memberInfoUpdateHelper;

    /**
     * 会員確認HELPER
     */
    private final MemberInfoConfirmHelper memberInfoConfirmHelper;

    /**
     * 会員詳細取得サービス
     */
    private final MemberInfoDetailsGetService memberInfoDetailsGetService;

    /**
     * 郵便番号整合性チェックService
     */
    private final ZipCodeMatchingCheckService zipCodeMatchingCheckService;

    /**
     * 会員情報データチェックサービス
     */
    private final MemberInfoDataCheckService memberInfoDataCheckService;

    /**
     * 会員詳細情報更新サービス
     */
    private final MemberInfoDetailsUpdateService memberInfoDetailsUpdateService;

    /**
     * 確認書類チェックロジック
     */
    private final CheckConfDocumentLogic checkConfDocumentLogic;

    /**
     * 会員処理完了メール送信サービス
     */
    private final MemberInfoProcessCompleteMailSendService memberInfoProcessCompleteMailSendService;

    /**
     * 顧客番号採番ロジック
     */
    private final MemberInfoGetCutomerNoNextValLogic memberInfoGetCutomerNoNextValLogic;

    /**
     * 会員情報登録Web-API連携ロジック
     */
    private final WebApiAddUserInformationLogic webApiAddUserInformationLogic;

    /**
     * 会員情報データチェックロジック
     */
    private final MemberInfoDataCheckLogic memberInfoDataCheckLogic;
    // 2023-renew No22 from here
    /**
     * 確認書類ストレージパス
     */
    private static final String REAL_PATH = "real.path.conf.document";
    /**
     * ドキュメントのディレクトリ名を確認する
     */
    private static final String CONF_DOCUMENT_DIR_NAME = "conf.document.dir.name";

    /**
     * 一時的なパス
     */
    private static final String TMP_PATH = "tmp.path";

    /**
     * 一時ファイルをアップロードするフォルダーのパス
     */
    private static final String REAL_TMP_PATH = "real.tmp.path";
    /**
     * バリデーター アップロードファイルを検証する
     */
    private final UploadDocsValidator uploadDocsValidator;
    /**
     * ファイルが指定されていません
     * <code>PDR-2023RENEW-22-006-E</code>
     */
    private static final String MSGCD_FILE_IS_NOT_SPECIFIED = "PDR-2023RENEW-22-007-E";
    /**
     * 会員画像一覧サービスインターフェース
     */
    private final MemberInfoListImageService memberInfoListImageService;
    /**
     * 会員検索ページHelper
     */
    private final MemberInfoHelper memberInfoHelper;

    /**
     * 会員イメージサービスインターフェース
     */
    private final MemberInfoDeleteImageService memberInfoDeleteImageService;

    /**
     * 会員イメージサービスインターフェース
     */
    private final MemberInfoSaveImageService memberInfoSaveImageService;

    /**
     * 会員イメージサービスインターフェース
     */
    private final String BACKUP_FOLDER_PREFIX = "backup";
    // 2023-renew No22 to here

    /**
     * コンストラクタ
     *
     * @param memberInfoUpdateHelper                   会員検索HELPER
     * @param memberInfoDetailsGetService              会員詳細取得サービス
     * @param zipCodeMatchingCheckService              郵便番号整合性チェックService
     * @param memberInfoDataCheckService               会員情報データチェックサービス
     * @param memberInfoConfirmHelper                  会員確認HELPER
     * @param memberInfoDetailsUpdateService           会員詳細情報更新サービス
     * @param checkConfDocumentLogic                   確認書類チェックロジック
     * @param memberInfoProcessCompleteMailSendService 会員処理完了メール送信サービス
     * @param memberInfoGetCutomerNoNextValLogic       顧客番号採番ロジック
     * @param webApiAddUserInformationLogic            会員情報登録Web-API連携ロジック
     * @param memberInfoListImageService               会員画像一覧サービスインターフェース
     * @param memberInfoHelper                         会員検索HELPER
     * @param memberInfoDataCheckLogic                 会員情報データチェックロジック
     */
    @Autowired
    public MemberInfoUpdateController(MemberInfoUpdateHelper memberInfoUpdateHelper,
                                      MemberInfoDetailsGetService memberInfoDetailsGetService,
                                      ZipCodeMatchingCheckService zipCodeMatchingCheckService,
                                      MemberInfoDataCheckService memberInfoDataCheckService,
                                      MemberInfoConfirmHelper memberInfoConfirmHelper,
                                      MemberInfoDetailsUpdateService memberInfoDetailsUpdateService,
                                      CheckConfDocumentLogic checkConfDocumentLogic,
                                      MemberInfoProcessCompleteMailSendService memberInfoProcessCompleteMailSendService,
                                      MemberInfoGetCutomerNoNextValLogic memberInfoGetCutomerNoNextValLogic,
                                      WebApiAddUserInformationLogic webApiAddUserInformationLogic,
                                      MemberInfoDataCheckLogic memberInfoDataCheckLogic,
                                      // 2023-renew No22 from here
                                      MemberInfoListImageService memberInfoListImageService,
                                      MemberInfoHelper memberInfoHelper,
                                      MemberInfoDeleteImageService memberInfoDeleteImageService,
                                      MemberInfoSaveImageService memberInfoSaveImageService,
                                      UploadDocsValidator uploadDocsValidator) {
        // 2023-renew No22 to here
        this.memberInfoUpdateHelper = memberInfoUpdateHelper;
        this.memberInfoDetailsGetService = memberInfoDetailsGetService;
        this.zipCodeMatchingCheckService = zipCodeMatchingCheckService;
        this.memberInfoDataCheckService = memberInfoDataCheckService;
        this.memberInfoConfirmHelper = memberInfoConfirmHelper;
        this.memberInfoDetailsUpdateService = memberInfoDetailsUpdateService;
        this.checkConfDocumentLogic = checkConfDocumentLogic;
        this.memberInfoProcessCompleteMailSendService = memberInfoProcessCompleteMailSendService;
        this.memberInfoGetCutomerNoNextValLogic = memberInfoGetCutomerNoNextValLogic;
        this.webApiAddUserInformationLogic = webApiAddUserInformationLogic;
        this.memberInfoDataCheckLogic = memberInfoDataCheckLogic;
        // 2023-renew No22 from here
        this.uploadDocsValidator = uploadDocsValidator;
        this.memberInfoListImageService = memberInfoListImageService;
        this.memberInfoHelper = memberInfoHelper;
        this.memberInfoDeleteImageService = memberInfoDeleteImageService;
        this.memberInfoSaveImageService = memberInfoSaveImageService;
        // 2023-renew No22 to here
    }

    // 2023-renew No22 from here

    /**
     * 動的バリデーター
     *
     * @param error
     */
    @InitBinder(value = "memberInfoUpdateModel")
    public void initBinder(WebDataBinder error) {
        // 確認書類画像の動的バリデータをセット
        error.addValidators(uploadDocsValidator);
    }
    // 2023-renew No22 to here

    /**
     * 入力画面：初期処理
     *
     * @param memberInfoUpdateModel 会員情報変更モデル
     * @param model                 the model
     * @return string
     */
    @GetMapping("")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_UPDATE_INDEX_PAGE)
    public String doLoadIndex(MemberInfoUpdateModel memberInfoUpdateModel, Model model) throws IOException {

        if (memberInfoUpdateModel.isReloadFlag() || (model.getAttribute(FLASH_FROM_CONFIRM) != null && Objects.equals(
                        model.getAttribute(FLASH_FROM_CONFIRM), Boolean.TRUE))) {
            memberInfoUpdateModel.setReloadFlag(false);
            return MEMBER_INFO_UPDATE_INDEX_PAGE;
        }

        MemberInfoDetailsDto memberInfoDetailsDto;
        try {
            // 会員詳細取得サービス実行
            memberInfoDetailsDto = memberInfoDetailsGetService.execute(memberInfoUpdateModel.getMemberInfoSeq());

        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            return REDIRECT_ERROR_PAGE;
        }

        clearModel(MemberInfoUpdateModel.class, memberInfoUpdateModel, model);

        // ページに反映
        memberInfoUpdateHelper.toPageForLoad(memberInfoDetailsDto, memberInfoUpdateModel);

        // 動的コンポーネント作成
        initComponents(memberInfoUpdateModel);
        // 2023-renew No22 from here
        getUploadFiles(memberInfoUpdateModel);
        // 2023-renew No22 to here

        return MEMBER_INFO_UPDATE_INDEX_PAGE;
    }

    /**
     * 入力画面：確認画面に遷移
     *
     * @param memberInfoUpdateModel 会員情報変更モデル
     */
    @PostMapping(value = "", params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_UPDATE_INDEX_PAGE)
    public String doConfirm(@Validated({ConfirmGroup.class}) MemberInfoUpdateModel memberInfoUpdateModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        if (error.hasErrors()) {
            return MEMBER_INFO_UPDATE_INDEX_PAGE;
        }

        // 入力チェック。POSTの値の書き換えチェック
        checkIllegalParameter(memberInfoUpdateModel);

        MemberInfoDetailsDto memberInfoDetailsDto =
                        memberInfoUpdateHelper.toMemberInfoDetailsDtoForUpdate(memberInfoUpdateModel);

        memberInfoDataCheckService.execute(memberInfoDetailsDto.getMemberInfoEntity());

        // エラーがある場合は投げる
        if (hasErrorMessage()) {
            throwMessage();
        }

        return "redirect:/memberinfo/update/confirm";

    }

    /**
     * アカウントロックを解除する
     *
     * @param memberInfoUpdateModel 会員情報変更モデル
     * @param redirectAttrs         redirectAttrs
     * @param model                 model
     * @return string
     */
    @PostMapping(value = "", params = "doOnceUnLock")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_DETAILS_INDEX_PAGE)
    public String doOnceUnLock(MemberInfoUpdateModel memberInfoUpdateModel,
                               RedirectAttributes redirectAttrs,
                               Model model) {

        HmFrontUserDetailsServiceImpl userDetailsService =
                        ApplicationContextUtility.getBean(HmFrontUserDetailsServiceImpl.class);

        userDetailsService.resetLoginFailureCount(memberInfoUpdateModel.getMemberInfoSeq());

        addInfoMessage(MSGCD_SUCCESSFUL_UNLOCK, null, redirectAttrs, model);

        return "redirect:/memberinfo/details/?memberInfoSeq=" + memberInfoUpdateModel.getMemberInfoSeq();

    }

    /**
     * 確認画面：初期処理
     *
     * @param memberInfoUpdateModel 会員情報変更モデル
     * @return string
     */
    @GetMapping(value = "/confirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/memberinfo/update?from=confirm")
    public String doLoadConfirm(MemberInfoUpdateModel memberInfoUpdateModel, Model model) {

        // ブラウザバックの場合、処理しない
        if (memberInfoUpdateModel.getMemberInfoEntity() == null) {
            return "redirect:/memberinfo/";
        }

        // 入力情報を画面に反映
        memberInfoConfirmHelper.toPageForLoad(memberInfoUpdateModel.getMemberInfoDetailsDto(), memberInfoUpdateModel);

        return "memberinfo/update/confirm";
    }

    /**
     * キャンセル
     *
     * @param memberInfoUpdateModel 会員情報変更モデル
     * @param redirectAttributes    redirectAttributes
     * @return string
     */
    @PostMapping(value = "/confirm", params = "doCancel")
    public String doCancel(MemberInfoUpdateModel memberInfoUpdateModel,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        redirectAttributes.addFlashAttribute(FLASH_FROM_CONFIRM, true);

        return "redirect:/memberinfo/update/?memberInfoSeq=" + memberInfoUpdateModel.getMemberInfoSeq();
    }

    /**
     * 更新処理
     *
     * @param memberInfoUpdateModel 会員情報変更モデル
     * @param redirectAttributes    redirectAtrributes
     * @param sessionStatus         sessionStatus
     * @return string
     */
    @PostMapping(value = "/confirm", params = "doOnceMemberUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "memberinfo/update/confirm")
    public String doOnceMemberUpdate(MemberInfoUpdateModel memberInfoUpdateModel,
                                     RedirectAttributes redirectAttributes,
                                     SessionStatus sessionStatus,
                                     Model model) {
        // PDR Migrate Customization from here
        // 修正情報取得
        MemberInfoEntity memberInfoEntity = memberInfoUpdateModel.getMemberInfoDetailsDto().getMemberInfoEntity();

        // 会員業務ヘルパークラスを取得する
        MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);
        memberInfoEntity.setMemberInfoUniqueId(memberInfoUtility.createShopUniqueId(memberInfoEntity.getShopSeq(),
                                                                                    memberInfoEntity.getMemberInfoMail()
                                                                                   ));

        // 修正された会員IDがすでに登録されていないかチェック
        memberInfoDataCheckLogic.execute(memberInfoEntity);

        // 入力画面で承認状態が「未承認」から「承認」に変更された場合は新規登録処理を行う
        if (!HTypeApproveStatus.ON.equals(memberInfoUpdateModel.getMemberInfoEntity().getApproveStatus())
            && HTypeApproveStatus.ON.equals(memberInfoEntity.getApproveStatus())) {
            // 顧客番号を取得
            // すでに顧客番号がある場合は採番しない
            if (memberInfoEntity.getCustomerNo() == null) {
                memberInfoEntity.setCustomerNo(memberInfoGetCutomerNoNextValLogic.execute());
            }
            // PDR Migrate Customization from here
            // サブシステム側との連携のため、予め決済代行IDに顧客番号を設定
            memberInfoEntity.setPaymentMemberId(memberInfoEntity.getCustomerNo().toString());
            // 決済代行会社カード保持種別 カード情報登録済 を設定
            memberInfoEntity.setPaymentCardRegistType(HTypeCardRegistType.REGISTERED);
            // PDR Migrate Customization to here

            // 日付関連Helper取得
            DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
            // 入会日にシステム日付を登録
            memberInfoEntity.setAdmissionYmd(dateUtility.getCurrentYmd());
            /*---------- #216 start----------*/
            // 退会日をクリア
            memberInfoEntity.setSecessionYmd(null);
            /*---------- #216 end----------*/
            // 会員情報から会員SEQ取得
            Integer memberInfoSeq = memberInfoEntity.getMemberInfoSeq();

            // 会員詳細更新サービス実行
            memberInfoDetailsUpdateService.execute(memberInfoUpdateModel.getMemberInfoDetailsDto());

            // 会員情報登録WEB-APIリクエストDto生成
            WebApiAddUserInformationRequestDto reqDto =
                            memberInfoConfirmHelper.createWebApiAddUserInformationRequestDto(memberInfoEntity);
            // 会員情報登録WEB-API実行
            webApiAddUserInformationLogic.execute(reqDto);

            // 顧客番号・パスワード通知メール送信フラグがONならばメール送信
            if (memberInfoUpdateModel.isSendNoticeMailFlag()) {
                memberInfoProcessCompleteMailSendService.execute(
                                memberInfoEntity.getMemberInfoSeq(), HTypeMailTemplateType.SEND_CUSTOMERNO_PASSWORD);
            }
        } else {

            // 会員情報から会員SEQ取得
            Integer memberInfoSeq = memberInfoEntity.getMemberInfoSeq();

            // 会員詳細更新サービス実行
            memberInfoDetailsUpdateService.execute(memberInfoUpdateModel.getMemberInfoDetailsDto());

            // 再検索フラグをセット
            redirectAttributes.addFlashAttribute(FLASH_MD, MODE_LIST);

        }
        // PDR Migrate Customization to here
        // Modelをセッションより破棄
        sessionStatus.setComplete();
        // 2023-renew No22 from here
        String targetPath = PropertiesUtil.getSystemPropertiesValue(REAL_PATH) + File.separator
                            + memberInfoEntity.getMemberInfoSeq();
        String sourcePath = PropertiesUtil.getSystemPropertiesValue(REAL_TMP_PATH)
                            + PropertiesUtil.getSystemPropertiesValue(CONF_DOCUMENT_DIR_NAME) + File.separator
                            + memberInfoEntity.getMemberInfoSeq();
        copyFiles(sourcePath, targetPath, memberInfoUpdateModel.getUploadFileModelList(),
                  memberInfoEntity.getMemberInfoSeq()
                 );
        // 2023-renew No22 to here

        return "redirect:/memberinfo/";
    }
    // PDR Migrate Customization from here

    // 2023-renew No22 from here

    /**
     * 確認書類をアップロードする
     *
     * @param memberInfoUpdateModel 会員情報変更モデル
     * @param error                 BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @param model                 Model
     * @return string
     */
    @PostMapping(value = "", params = "doUploadFile")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_UPDATE_INDEX_PAGE)
    public String doUploadFile(@Validated({UploadDocsGroup.class}) MemberInfoUpdateModel memberInfoUpdateModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        Integer memberInfoSeq = memberInfoUpdateModel.getMemberInfoEntity().getMemberInfoSeq();
        if (error.hasErrors()) {
            memberInfoUpdateModel.setReloadFlag(true);
            return MEMBER_INFO_UPDATE_INDEX_PAGE;
        }
        MultipartFile[] uploadFiles = memberInfoUpdateModel.getUploadFiles();
        memberInfoUpdateModel.setErrorUpload(false);
        if (memberInfoUpdateModel.getUploadFileModelList() == null) {
            List<UploadFileModel> uploadFileList = new ArrayList<>();
            memberInfoUpdateModel.setUploadFileModelList(uploadFileList);
        }
        uploadFile(uploadFiles, memberInfoUpdateModel, memberInfoSeq);
        memberInfoUpdateModel.setErrorUpload(false);
        memberInfoUpdateModel.setReloadFlag(true);
        return "redirect:/memberinfo/update/?memberInfoSeq=" + memberInfoSeq;
    }

    /**
     * ファイルを削除する
     *
     * @param memberInfoUpdateModel 会員情報変更モデル
     * @param error                 BindingResult
     * @param redirectAttributes    RedirectAttributes
     * @param model                 Model
     * @return string
     */
    @PostMapping(value = "", params = "doDeleteFile")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_UPDATE_INDEX_PAGE)
    public String doDeleteFile(@Validated({ConfirmGroup.class}) MemberInfoUpdateModel memberInfoUpdateModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        Integer memberInfoSeq = memberInfoUpdateModel.getMemberInfoEntity().getMemberInfoSeq();
        List<UploadFileModel> uploadFileModelList = memberInfoUpdateModel.getUploadFileModelList();
        deleteFile(memberInfoSeq, uploadFileModelList, memberInfoUpdateModel.getFileNo());
        memberInfoUpdateModel.setErrorUpload(false);
        memberInfoUpdateModel.setReloadFlag(true);
        return "redirect:/memberinfo/update/?memberInfoSeq=" + memberInfoSeq;

    }

    /**
     * アップロードファイルを取得する
     *
     * @param memberInfoUpdateModel 会員情報変更モデル
     */
    private void getUploadFiles(MemberInfoUpdateModel memberInfoUpdateModel) {
        if (memberInfoUpdateModel.getUploadFileModelList() == null)
            memberInfoUpdateModel.setUploadFileModelList(new ArrayList<>());
        Integer memberInfoSeq = memberInfoUpdateModel.getMemberInfoEntity().getMemberInfoSeq();
        // アップロードされたファイルをデータベースから取得する
        List<MemberImageDto> memberImageDtoList = this.memberInfoListImageService.execute(memberInfoSeq);
        List<UploadFileModel> uploadFileModelList = memberInfoHelper.toUploadFileModel(memberImageDtoList,
                                                                                       PropertiesUtil.getSystemPropertiesValue(
                                                                                                       TMP_PATH)
                                                                                       + PropertiesUtil.getSystemPropertiesValue(
                                                                                                       CONF_DOCUMENT_DIR_NAME)
                                                                                       + "/", memberInfoSeq
                                                                                      );
        String sourcePath = PropertiesUtil.getSystemPropertiesValue(REAL_PATH) + File.separator + memberInfoSeq;
        String targetPath = PropertiesUtil.getSystemPropertiesValue(REAL_TMP_PATH)
                            + PropertiesUtil.getSystemPropertiesValue(CONF_DOCUMENT_DIR_NAME) + File.separator
                            + memberInfoSeq;
        copyRealPathToTmp(sourcePath, targetPath);
        memberInfoUpdateModel.setUploadFileModelList(uploadFileModelList);
    }

    /**
     * ファイルを移動する
     *
     * @param sourcePath ソースパス
     * @param targetPath ターゲットパス
     */
    private void copyRealPathToTmp(String sourcePath, String targetPath) {

        File folder = new File(targetPath);
        File folderSource = new File(sourcePath);
        try {
            if (folder.isDirectory() && folder.exists()) {
                FileUtils.deleteDirectory(new File(targetPath));
            }
            if (!folderSource.exists())
                folderSource.mkdirs();
            FileUtils.copyDirectory(new File(sourcePath), new File(targetPath));
        } catch (IOException e) {
            LOGGER.error("ファイルのコピーに失敗する", e);
            throw new RuntimeException("一時リポジトリを作成できません");
        }

    }

    /**
     * ファイルを移動する
     *
     * @param sourcePath ソースパス
     * @param targetPath ターゲットパス
     * @param uploadFileModelList アップロードファイルの登録
     */
    private void copyFiles(String sourcePath,
                           String targetPath,
                           List<UploadFileModel> uploadFileModelList,
                           Integer memberInfoSeq) {

        File folder = new File(targetPath);
        File folderSource = new File(sourcePath);

        // バックアップフォルダーを作成する
        copyRealPathToTmp(targetPath, sourcePath + "_" + BACKUP_FOLDER_PREFIX);
        File backupFolder = new File(sourcePath + "_" + BACKUP_FOLDER_PREFIX);
        try {

            if (folder.isDirectory() && folder.exists()) {
                FileUtils.cleanDirectory(new File(targetPath));
            } else {
                folder.mkdirs();
            }
            if (!folderSource.exists())
                folderSource.mkdirs();
            memberInfoDeleteImageService.execute(memberInfoSeq);
            int count = 0;
            for (int i = 0; i < uploadFileModelList.size(); i++) {
                UploadFileModel file = uploadFileModelList.get(i);
                if (!file.getIsDeleted()) {
                    count++;
                    memberInfoSaveImageService.execute(memberInfoSeq, count, file.getFileName(),
                                                       file.getExtensionType().getValue()
                                                      );
                    String fileSourcePath = sourcePath + File.separator + file.getFileName();
                    File sourceFile = new File(fileSourcePath);
                    if (!sourceFile.exists()) {
                        LOGGER.error("画像が取得できない場合エラー");
                        throwMessage(MSGCD_FILE_IS_NOT_SPECIFIED);
                    }
                    String destinationPath = targetPath + File.separator + file.getFileName();
                    Path source = Paths.get(fileSourcePath);
                    Path destination = Paths.get(destinationPath);
                    Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
                }
            }
            // 一時フォルダーを削除する
            FileUtils.deleteDirectory(folderSource);
            FileUtils.deleteDirectory(backupFolder);
        } catch (IOException e) {
            // バックアップから復元する
            copyRealPathToTmp(sourcePath + "_" + BACKUP_FOLDER_PREFIX, targetPath);
            // バックアップフォルダを削除する
            backupFolder.delete();

            LOGGER.error("ファイルのコピーに失敗する", e);
            throw new RuntimeException("一時リポジトリを作成できません");
        }

    }

    /**
     * ファイルを削除する
     *
     * @param memberInfoSeq 会員SEQ
     * @param list
     * @param no
     */
    private void deleteFile(Integer memberInfoSeq, List<UploadFileModel> list, Integer no) {
        try {
            int index = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getFileNo().equals(no)) {
                    index = i;
                }
            }
            if (index != -1) {
                String fileName = list.get(index).getFileName();
                String filePath = PropertiesUtil.getSystemPropertiesValue(REAL_TMP_PATH)
                                  + PropertiesUtil.getSystemPropertiesValue(CONF_DOCUMENT_DIR_NAME) + File.separator
                                  + memberInfoSeq + File.separator + fileName;
                FileUtils.forceDelete(new File(filePath));
                list.get(index).setIsDeleted(Boolean.TRUE);
            }
        } catch (IOException e) {
            LOGGER.error("ファイルの削除に失敗する", e);
            throw new RuntimeException("ファイルの削除に失敗する");
        }

    }

    /**
     * 確認書類をアップロードする
     * @param uploadFiles: アップロードするファイルが選択されました
     * @param memberInfoUpdateModel 会員情報変更モデル
     * @param memberInfoSeq メンバーID
     */
    private void uploadFile(MultipartFile[] uploadFiles,
                            MemberInfoUpdateModel memberInfoUpdateModel,
                            Integer memberInfoSeq) {

        String folderPath = PropertiesUtil.getSystemPropertiesValue(REAL_TMP_PATH)
                            + PropertiesUtil.getSystemPropertiesValue(CONF_DOCUMENT_DIR_NAME) + File.separator
                            + memberInfoSeq;
        createFolder(folderPath);
        try {
            for (MultipartFile item : uploadFiles) {
                UploadFileModel uploadFileModel = new UploadFileModel();

                String originName = item.getOriginalFilename();
                uploadFileModel.setOriginName(originName);
                String fileNameWithDate = memberInfoHelper.renameFile(originName);
                uploadFileModel.setFileName(fileNameWithDate);

                if (memberInfoUpdateModel.getUploadFileModelList().isEmpty()) {
                    uploadFileModel.setFileNo(0);
                } else {
                    Optional<UploadFileModel> maxNoFile = memberInfoUpdateModel.getUploadFileModelList()
                                                                               .stream()
                                                                               .max(Comparator.comparingInt(
                                                                                               UploadFileModel::getFileNo));
                    Integer lastIndex = -1;
                    if (maxNoFile.isPresent()) {
                        lastIndex = maxNoFile.get().getFileNo();
                    }
                    uploadFileModel.setFileNo(lastIndex + 1);
                }
                String contentType = item.getContentType();
                if (contentType != null && contentType.contains("pdf")) {
                    uploadFileModel.setExtensionType(HTypeUploadExtension.PDF);
                } else if (contentType != null && contentType.contains("png")) {
                    uploadFileModel.setExtensionType(HTypeUploadExtension.PNG);
                }
                uploadFileModel.setFilePath(PropertiesUtil.getSystemPropertiesValue(TMP_PATH)
                                            + PropertiesUtil.getSystemPropertiesValue(CONF_DOCUMENT_DIR_NAME) + "/"
                                            + memberInfoSeq + "/" + fileNameWithDate);
                String target = folderPath + File.separator + fileNameWithDate;
                Files.copy(item.getInputStream(), Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
                memberInfoUpdateModel.getUploadFileModelList().add(uploadFileModel);
            }
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * フォルダーを作る
     * @param folderPath: フォルダ名
     *
     * 関数名パラメーターで新しいフォルダーを作成します
     * フォルダが存在する場合は何もしない
     */
    private void createFolder(String folderPath) {
        Path path = Paths.get(folderPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                LOGGER.error("ファイルのコピーに失敗する", e);
                throw new RuntimeException("一時リポジトリを作成できません");
            }
        }
    }
    // 2023-renew No22 to here

    /**
     * 入力チェック<br/>
     * 都道府県入力値チェックを削除<br/>
     * 入会日チェックを削除<br/>
     * 退会日チェックを削除<br/>
     * TEL・FAXのみ会員の承認状態を未承認→承認に変更した場合のチェックを追加<br/>
     *
     * @param memberInfoUpdateModel 会員情報変更モデル
     */
    protected void checkIllegalParameter(MemberInfoUpdateModel memberInfoUpdateModel) {

        // PDR Migrate Customization from here
        // TEL・FAXのみ会員で承認状態が未承認の場合
        if (HTypeOnlineRegistFlag.OFF.equals(memberInfoUpdateModel.getMemberInfoEntity().getOnlineRegistFlag())
            && HTypeApproveStatus.OFF.equals(memberInfoUpdateModel.getMemberInfoEntity().getApproveStatus())) {
            // 承認状態を未承認から承認に変更した場合
            if (HTypeApproveStatus.ON.getValue().equals(memberInfoUpdateModel.getApproveStatus())) {
                addErrorMessage(MSGCD_MEMBER_APPROVESTATUS_TELFAX);
            }
        }
        // PDR Migrate Customization to here

        // 確認書類チェック
        if (!checkConfDocumentLogic.execute(
                        memberInfoUpdateModel.getBusinessType(), memberInfoUpdateModel.getConfDocumentType())) {
            addErrorMessage(MSGCD_MEMBER_NOT_SETTABLE_CONFDOCUMENT);
        }

    }
    // PDR Migrate Customization to here

    /**
     * 動的コンポーネント作成
     *
     * @param memberInfoUpdateModel 会員情報変更モデル
     */
    protected void initComponents(MemberInfoUpdateModel memberInfoUpdateModel) {

        memberInfoUpdateModel.setMemberInfoStatusItems(EnumTypeUtil.getEnumMap(HTypeMemberInfoStatus.class));

        // 都道府県区分値を取得（北海道：北海道）
        Map<String, String> prefectureTypeItems = EnumTypeUtil.getEnumMap(HTypePrefectureType.class, true);
        memberInfoUpdateModel.setMemberInfoPrefectureItems(prefectureTypeItems);

        // PDR Migrate Customization from here
        // 顧客区分区分値を取得（北海道：北海道）
        memberInfoUpdateModel.setBusinessTypeItems(EnumTypeUtil.getEnumMap(HTypeBusinessType.class));

        //確認書類区分値を取得（北海道：北海道）
        memberInfoUpdateModel.setConfDocumentTypeItems(EnumTypeUtil.getEnumMap(HTypeConfDocumentType.class));

        // 医薬品・注射針販売区分区分値を取得（北海道：北海道）
        memberInfoUpdateModel.setDrugSalesTypeItems(EnumTypeUtil.getEnumMap(HTypeDrugSalesType.class));

        memberInfoUpdateModel.setDentalMonopolySalesTypeItems(
                        EnumTypeUtil.getEnumMap(HTypeDentalMonopolySalesType.class));

        memberInfoUpdateModel.setCreditPaymentUseFlagItems(EnumTypeUtil.getEnumMap(HTypeCreditPaymentUseFlag.class));

        memberInfoUpdateModel.setCashDeliveryUseFlagItems(EnumTypeUtil.getEnumMap(HTypeCashDeliveryUseFlag.class));

        memberInfoUpdateModel.setMonthlyPayUseFlagItems(EnumTypeUtil.getEnumMap(HTypeMonthlyPayUseFlag.class));

        memberInfoUpdateModel.setAccountingTypeItems(EnumTypeUtil.getEnumMap(HTypeAccountingType.class));

        memberInfoUpdateModel.setMedicalEquipmentSalesTypeItems(
                        EnumTypeUtil.getEnumMap(HTypeMedicalEquipmentSalesType.class));

        memberInfoUpdateModel.setTransferPaymentUseFlagItems(
                        EnumTypeUtil.getEnumMap(HTypeTransferPaymentUseFlag.class));

        memberInfoUpdateModel.setMemberListTypeItems(EnumTypeUtil.getEnumMap(HTypeMemberListType.class));

        memberInfoUpdateModel.setOnlineLoginAdvisabilityItems(
                        EnumTypeUtil.getEnumMap(HTypeOnlineLoginAdvisability.class));

        memberInfoUpdateModel.setApproveStatusItems(EnumTypeUtil.getEnumMap(HTypeApproveStatus.class));

        memberInfoUpdateModel.setDirectDebitUseFlagItems(EnumTypeUtil.getEnumMap(HTypeDirectDebitUseFlag.class));
        // PDR Migrate Customization to here
    }
}
