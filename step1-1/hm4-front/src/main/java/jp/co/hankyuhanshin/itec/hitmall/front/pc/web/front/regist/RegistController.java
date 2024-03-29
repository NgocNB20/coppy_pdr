/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByMemberInfoIdOrCustumerNoGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByTelAndCustomerNoGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByTelGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoImageRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoScreenRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ZipcodeApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ResultFlagResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ZipCodeMatchingCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeFrontBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberImage;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.MedicalTreatmentDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.loginadvisability.LoginAdvisabilityEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.MemberHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.UploadDocsValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.group.ConfirmInfoGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.group.InputMailGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.group.InputmemnoGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.group.InputtelGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.group.RegistGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.group.UploadDocsGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * // PDR Migrate Customization from here
 * PDR#011 08_データ連携（顧客情報）
 * <pre>
 * 会員情報表示アクション
 * </pre>
 * <pre>
 * 本会員登録確認アクション
 * </pre>
 * お客様情報入力画面アクション
 * </pre>
 * </pre>
 * お客様番号入力アクション
 * </pre>
 * </pre>
 * 電話番号入力アクションクラス
 * </pre>
 *
 * @author satoh
 * @author kimura
 * @version $Revision:$
 * // PDR Migrate Customization to here
 * 本会員登録 Controller
 */
@RequestMapping("/regist")
@Controller
@SessionAttributes(value = "registModel")
public class RegistController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistController.class);

    /**
     * 不正遷移:AMR000401
     */
    protected static final String MSGCD_REFERER_FAIL = "AMR000401";

    /**
     * 不正遷移:AMR000501
     */
    protected static final String MSGCD_REFERER_FAIL_CONFIRM = "AMR000501";

    /**
     * DB一意制約:AMR000502
     */
    protected static final String MSGCD_DB_UNIQUE_MEMBERID_FAIL_CONFIRM = "AMR000502";

    /**
     * HTMLメール不可:AMR000402
     */
    protected static final String MSGCD_HTML_MAIL_FAIL = "AMR000402";

    /**
     * 都道府県のプルダウンいじられた:AMR000403
     */
    protected static final String MSGCD_ILLEGAL_PREFECTURE = "AMR000403";

    /**
     * 性別のラジオボタンいじられた:AMR000405
     */
    protected static final String MSGCD_ILLEGAL_SEX = "AMR000405";

    /**
     * 都道府県と郵便番号の整合性エラー:AMR000406
     * 定数にWを付与
     */
    protected static final String MSGCD_PREFECTURE_CONSISTENCY = "AMR000406W";

    /**
     * 都道府県フィールド名
     **/
    protected static final String FILED_NAME_PREFECTURE = "memberInfoPrefecture";

    /**
     * 前画面が確認画面の「修正する」からであるかを判断するフラグ
     */
    protected static final String FLAG_FROMCONFIRM = "fromConfirm";

    // PDR Migrate Customization from here
    // 2023-renew No85-1 from here
    /**
     * 前画面が確認画面の「ご登録電話番号入力」からであるかを判断するフラグ
     */
    protected static final String FLAG_FROMINPUTTEL = "fromInputtel";

    /**
     * 前画面が確認画面の「ご登録メールアドレス入力」からであるかを判断するフラグ
     */
    protected static final String FLAG_FROMINPUTMAIL = "fromInputMail";

    /**
     * 前画面が確認画面の「お客様番号入力」からであるかを判断するフラグ
     */
    protected static final String FLAG_FROMINPUTCUSTOMERNO = "fromInputCustomerNo";
    // 2023-renew No85-1 to here

    /**
     * お客様番号と電話番号の組み合わせに合致する会員の名簿区分が「1:顧客」以外の場合エラー
     * <code>MSGCD_MEMBERLIST_TYPE_ERR</code>
     */
    public static final String MSGCD_MEMBERLIST_TYPE_ERR = "PDR-0047-001-A-";

    /**
     * お客様番号と電話番号の組み合わせに合致する会員が存在しないエラー
     * <code>MSGCD_NOTFOUND_MEMBERINFO</code>
     */
    public static final String MSGCD_NOTFOUND_MEMBERINFO = "PDR-0011-001-A-";

    /**
     * 業種のラジオボタンが不正な値に置き換えられたエラー
     * <code>MSGCD_ILLEGAL_BUSINESSTYPE</code>
     */
    public static final String MSGCD_ILLEGAL_BUSINESSTYPE = "PDR-0011-003-A-";

    /**
     * 既にパスワードが発行されている場合のメッセージ
     * <code>MSGCD_SET_PASSWORD</code>
     */
    public static final String MSGCD_SET_PASSWORD = "PDR-0011-004-A-";

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "AYD000601";

    // 2023-renew No22 from here
    /**
     * 一時ファイルをアップロードするフォルダーのパス
     */
    private static final String TMP_PATH = "real.tmp.path.conf.document";

    /**
     * 実パスストレージのアップロードファイル
     */
    private static final String REAL_PATH = "real.path.conf.document";

    /**
     * 一時ファイルを保存するディレクトリのパス
     */
    private static final String TMP_URI_CONFIRM_DOCUMENT = "tmp.uri.conf.document";
    /**
     * ファイルが指定されていません
     * <code>PDR-2023RENEW-22-006-E</code>
     */
    private static final String MSGCD_FILE_IS_NOT_SPECIFIED = "PDR-2023RENEW-22-006-E";

    /**
     * メンバーヘルパー
     */
    private final MemberHelper memberHelper;
    // 2023-renew No22 to here
    // 2023-renew No85-1 from here
    /**
     * メッセージコード：ログイン不可
     */
    public static final String MSGCD_NOT_POSSIBLE_LOGIN = "PDR-2023RENEW-85-1-001-";
    // 2023-renew No85-1 to here

    /**
     * 診療項目Dto
     */
    public final MedicalTreatmentDto medicalTreatmentDto;
    // PDR Migrate Customization to here

    /**
     * 本会員登録Helper
     */
    private final RegistHelper registHelper;

    /**
     * 郵便番号API
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * 郵便番号API
     */
    private final ZipcodeApi zipcodeApi;
    // 2023-renew No22 from here
    /**
     * ファイルのアップロードバリデーター
     */
    private final UploadDocsValidator uploadDocsValidator;
    // 2023-renew No22 to here

    /**
     * コンストラクタ
     *
     * @param medicalTreatmentDto 診療項目Dtoクラス
     * @param registHelper        本会員登録Helper
     * @param memberInfoApi       郵便番号API
     * @param zipcodeApi          郵便番号API
     * @param uploadDocsValidator バリデータアップロードファイル
     * @param memberHelper        メンバーヘルパー
     */
    @Autowired
    public RegistController(MedicalTreatmentDto medicalTreatmentDto,
                            RegistHelper registHelper,
                            MemberInfoApi memberInfoApi,
                            ZipcodeApi zipcodeApi,
                            // 2023-renew No22 from here
                            UploadDocsValidator uploadDocsValidator,
                            MemberHelper memberHelper) {
        // 2023-renew No22 to here
        this.medicalTreatmentDto = medicalTreatmentDto;
        this.registHelper = registHelper;
        this.zipcodeApi = zipcodeApi;
        this.memberInfoApi = memberInfoApi;
        // 2023-renew No22 from here
        this.uploadDocsValidator = uploadDocsValidator;
        this.memberHelper = memberHelper;
        // 2023-renew No22 to here
    }

    // 2023-renew No22 from here

    /**
     * 標準ファイルの動的バリデーター
     *
     * @param error
     */
    @InitBinder(value = "registModel")
    public void initBinder(WebDataBinder error) {
        // 規格画像の動的バリデータをセット
        error.addValidators(uploadDocsValidator);
    }
    // 2023-renew No22 to here

    /**
     * お客様情報入力画面：初期処理
     *
     * <pre>
     * 有効URLかどうかのチェックを削除
     * </pre>
     *
     * @param registModel 本会員登録 Model
     * @param model       モデル
     * @return 入力画面
     */
    @GetMapping(value = {"/", "/index.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "regist/index")
    // PDR Migrate Customization from here
    // 2023-renew No22 from here
    protected String doLoadIndex(RegistModel registModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 HttpSession httpSession) {
        // 2023-renew No22 to here
        // PDR Migrate Customization to here

        // 2023-renew No22 from here
        if (registModel.isReloadFlag()) {
            registModel.setReloadFlag(false);
            return "regist/index";
        }
        MultipartFile[] uploadFilesTmp = registModel.getUploadFiles();
        // 2023-renew No22 to here
        // 確認画面からの遷移の場合 セッション情報を表示
        if (model.containsAttribute(FLAG_FROMCONFIRM)) {
            // パラメータチェック
            if (checkInput(registModel)) {
                registModel.setErrorUrl(true);
                addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
                return "redirect:/error.html";
            }
            return "regist/index";
        }

        // PDR Migrate Customization from here
        // 2023-renew No85-1 from here
        if (model.containsAttribute(FLAG_FROMINPUTMAIL) || model.containsAttribute(FLAG_FROMINPUTCUSTOMERNO)) {
            // 動的コンポーネント作成
            initComponents(registModel);

            // PDR Migrate Customization from here
            // 有効URLの処理を行わないためチェックを削除
            // 仮会員登録を行わないため、仮会員登録情報取得を削除
            // 仮会員登録情報を画面に設定する処理を削除

            registHelper.toPageForLoad(registModel);

            // 診療項目を設定
            setMedicalTreatment(registModel);
            // PDR Migrate Customization to here

            // 有効なURLだった
            registModel.setErrorUrl(false);
            if (model.containsAttribute(FLAG_FROMINPUTCUSTOMERNO)) {
                registModel.setFromInputMemNoExist(true);
            }

            // 2023-renew No22 from here
            // 登録セッションを作成する
            registModel.setUploadFiles(uploadFilesTmp);
            if (registModel.getUploadFiles() != null && registModel.getUploadFiles().length > 0) {
                String key = getRegistSession(httpSession);
                String filePath = createFolder(key, TMP_PATH);
                doUploadFile(registModel, filePath, registModel.getUploadFiles());
            }
            // 2023-renew No22 to here

            return "regist/index";

        } else {
            // 不正遷移の場合
            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
            return "redirect:/error.html";
        }
        // 2023-renew No85-1 to here
    }

    /**
     * お客様情報入力画面：確認画面に遷移
     *
     * @param registModel        本会員登録 Model
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return 確認画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "regist/index")
    public String doConfirm(@Validated(RegistGroup.class) RegistModel registModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        // PDR Migrate Customization from here
        if (error.hasErrors()) {
            return "regist/index";
        }
        // PDR Migrate Customization to here

        // パラメータチェック
        if (StringUtils.isEmpty(registModel.getMemberInfoMail())) {
            addMessage(RegistModel.MSGCD_SESSION_DESTROY, redirectAttributes, model);
            return "redirect:/error.html";
        }

        // 都道府県と郵便番号が不一致の場合
        if (!checkPrefectureAndZipCode(registModel)) {
            error.rejectValue(FILED_NAME_PREFECTURE, MSGCD_PREFECTURE_CONSISTENCY);
        }

        if (error.hasErrors()) {
            return "regist/index";
        }

        // 入力チェック。POSTの値の書き換えチェック
        checkIllegalParameter(registModel);

        return "redirect:/regist/confirm.html";
    }

    // 2023-renew No85-1 from here

    /**
     * 入力画面：確認画面に遷移
     *
     * @param registModel        本会員登録 Model
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return 確認画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doConfirmUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "regist/index")
    public String doConfirmUpdate(@Validated(ConfirmInfoGroup.class) RegistModel registModel,
                                  BindingResult error,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        if (error.hasErrors()) {
            return "regist/index";
        }

        // パラメータチェック
        if (StringUtils.isEmpty(registModel.getMemberInfoMail())) {
            addMessage(RegistModel.MSGCD_SESSION_DESTROY, redirectAttributes, model);
            return "redirect:/error.html";
        }

        return "redirect:/regist/confirm.html";
    }
    // 2023-renew No85-1 to here

    // PDR Migrate Customization from here
    /*
     * ご登録電話番号入力画面：初期表示
     */
    @GetMapping(value = {"/inputtel", "/inputtel.html"})
    public String doLoadInputtel(RegistModel registModel, Model model) {

        // モデル初期化
        clearModel(RegistModel.class, registModel, model);

        return "regist/inputtel";
    }

    /**
     * ご登録電話番号入力画面：次へ進む
     *
     * @param registModel 本会員登録 Model
     * @return お客様番号入力画面 または 会員情報入力画面
     */
    @PostMapping(value = {"/inputtel", "/inputtel.html"}, params = "doConfirmInputtel")
    @HEHandler(exception = AppLevelListException.class, returnView = "regist/inputtel")
    public String doConfirmInputtel(@Validated(InputtelGroup.class) RegistModel registModel,
                                    BindingResult error,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {

        if (error.hasErrors()) {
            return "regist/inputtel";
        }
        MemberInfoByTelGetRequest request = new MemberInfoByTelGetRequest();
        request.setMemberInfoTel(registModel.getMemberInfoTel());

        MemberInfoEntityResponse memberInfoEntityResponse = null;
        try {
            memberInfoEntityResponse = memberInfoApi.getByTel(request);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 電話番号から会員情報を取得します。
        MemberInfoEntity memberInfoEntity = registHelper.toMemberInfoEntity(memberInfoEntityResponse);
        // 2023-renew No85-1 from here

        redirectAttributes.addFlashAttribute(FLAG_FROMINPUTTEL, null);
        if (memberInfoEntity == null) {
            return "redirect:/regist/inputmail.html";
        }

        // 2023-renew No85-1 to here
        registModel.setFromInputTelExist(true);
        return "redirect:/regist/inputmemno.html";
    }
    // PDR Migrate Customization to here

    /**
     * 確認画面：初期処理
     *
     * @param registModel        本会員登録 Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return 確認画面
     */
    @GetMapping(value = {"/confirm", "/confirm.html"})
    protected String doLoadConfirm(RegistModel registModel, RedirectAttributes redirectAttributes, Model model) {

        // 不正遷移チェック 必須項目の有無で入力画面へ遷移
        if (checkInput(registModel)) {
            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
            return "redirect:/error.html";
        }

        // PDR Migrate Customization from here
        // 会員追加属性選択結果をページに反映
        registHelper.toPageForLoadDoLoadConfirm(registModel);
        // PDR Migrate Customization to here
        return "regist/confirm";
    }

    /**
     * 確認画面：入力画面に遷移
     *
     * @param registModel        本会員登録 Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return 入力画面
     */
    @PostMapping(value = {"/confirm", "/confirm.html"}, params = "doIndex")
    public String doIndexConfirm(RegistModel registModel, RedirectAttributes redirectAttributes, Model model) {

        // 確認メールアドレスを初期化する
        registModel.setMemberInfoMailConfirm(null);

        // 遷移元フラグ設定
        redirectAttributes.addFlashAttribute(FLAG_FROMCONFIRM, null);

        return "redirect:/regist/index.html";
    }

    /**
     * 本会員登録処理
     *
     * @param request            リクエスト
     * @param registModel        本会員登録 Model
     * @param sessionStatus      セクションステータス
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return 完了画面
     */
    @PostMapping(value = {"/confirm", "/confirm.html"}, params = "doOnceMemberInfoRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "regist/confirm")
    public String doOnceMemberInfoRegist(HttpServletRequest request,
                                         RegistModel registModel,
                                         BindingResult error,
                                         SessionStatus sessionStatus,
                                         RedirectAttributes redirectAttributes,
                                         Model model) {

        // 会員エンティティ情報の作成
        MemberInfoEntity memberInfoEntity = registHelper.toMemberInfoEntityForMemberInfoRegist(registModel);

        String accessUidCommonInfo = getCommonInfo().getCommonInfoBase().getAccessUid();
        Boolean isSiteBack = getCommonInfo().getCommonInfoBase().getSiteType().isBack();
        String campainCode = getCommonInfo().getCommonInfoBase().getCampaignCode();

        MemberInfoScreenRegistRequest memberInfoScreenRegistRequest =
                        registHelper.toMemberInfoScreenRegistRequest(memberInfoEntity, accessUidCommonInfo, isSiteBack,
                                                                     campainCode
                                                                    );

        try {
            // 2023-renew No22 from here
            MemberInfoEntityResponse memberInfoEntityResponse =
                            memberInfoApi.registMemberInfoScreen(memberInfoScreenRegistRequest);
            Integer memberInfoSeq = memberInfoEntityResponse.getMemberInfoSeq();
            copyTmpToRealPath(memberInfoSeq, registModel.getRegistUploadFiles(), request.getSession());
            // 2023-renew No22 to here
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return "redirect:/regist/complete.html";
    }

    /**
     * 完了画面：画面表示処理
     *
     * @param registModel   本会員登録 Model
     * @param sessionStatus セクションステータス
     * @return 完了画面
     */
    @GetMapping(value = {"/complete", "/complete.html"})
    public String doLoadComplete(RegistModel registModel, SessionStatus sessionStatus) {

        // PDR Migrate Customization from here
        // Modelをセッションより破棄
        sessionStatus.setComplete();
        // PDR Migrate Customization to here
        return "regist/complete";
    }

    /**
     * 動的コンポーネント作成
     *
     * @param registModel 本会員登録 Model
     */
    protected void initComponents(RegistModel registModel) {

        // 都道府県区分値を取得（北海道：北海道）
        Map<String, String> prefectureTypeItems = EnumTypeUtil.getEnumMap(HTypePrefectureType.class, true);
        registModel.setMemberInfoPrefectureItems(prefectureTypeItems);

        // 性別(未回答)区分値を取得
        Map<String, String> memberInfoSexItems = EnumTypeUtil.getEnumMap(HTypeSexUnnecessaryAnswer.class);
        registModel.setMemberInfoSexItems(memberInfoSexItems);

        // PDR Migrate Customization from here
        Map<String, String> businessTypeItems = EnumTypeUtil.getEnumMap(HTypeFrontBusinessType.class);
        registModel.setBusinessTypeItems(businessTypeItems);
        // PDR Migrate Customization to here
    }

    /**
     * 必須項目を全てチェックし、不正遷移かどうかをチェック
     * <p>
     * // PDR Migrate Customization from here
     * <pre>
     * チェック項目を修正
     * 大まかな流れはPKG標準のソースを流用
     * 以下の項目のチェックを削除
     * ・氏名(名)
     * ・フリガナ(名)
     * ・性別
     * ・パスワード
     * </pre>
     * // PDR Migrate Customization from here
     *
     * @param registModel 本会員登録 Model
     * @return true=不正、false=正常
     */
    protected boolean checkInput(RegistModel registModel) {

        // メール
        if (StringUtils.isEmpty(registModel.getMemberInfoMail())) {
            return true;
        }
        // PDR Migrate Customization from here
        // コメント修正
        // 事業所名
        if (StringUtils.isEmpty(registModel.getMemberInfoLastName())) {
            return true;
        }
        // 事業所名フリガナ
        if (StringUtils.isEmpty(registModel.getMemberInfoLastKana())) {
            return true;
        }
        // 項目追加
        // 業種
        if (StringUtils.isEmpty((registModel).getBusinessType())) {
            return true;
        }
        // 氏名(名)、フリガナ(名)、性別のチェックを削除
        // 電話番号
        if (StringUtils.isEmpty(registModel.getMemberInfoTel())) {
            return true;
        }
        // PDR Migrate Customization to here
        // 郵便番号1
        if (StringUtils.isEmpty(registModel.getMemberInfoZipCode1())) {
            return true;
        }
        // 郵便番号2
        if (StringUtils.isEmpty(registModel.getMemberInfoZipCode2())) {
            return true;
        }
        // 都道府県
        // 2023-renew No85-1 from here
        if (StringUtils.isEmpty(registModel.getMemberInfoPrefecture()) && !registModel.isFromInputMemNoExist()) {
            return true;
        }
        // 2023-renew No85-1 to here
        // 住所1
        if (StringUtils.isEmpty(registModel.getMemberInfoAddress1())) {
            return true;
        }
        // 住所2
        if (StringUtils.isEmpty(registModel.getMemberInfoAddress2())) {
            return true;
        }
        // PDR Migrate Customization from here
        // パスワードのチェックを削除
        // PDR Migrate Customization to here
        return false;
    }

    /**
     * 都道府県と郵便番号の不整合チェック
     *
     * @param registModel 本会員登録 Model
     */
    protected boolean checkPrefectureAndZipCode(RegistModel registModel) {

        // nullの場合service未実行(必須チェックは別タスク)
        if (StringUtils.isEmpty(registModel.getMemberInfoZipCode1()) || StringUtils.isEmpty(
                        registModel.getMemberInfoZipCode2()) || StringUtils.isEmpty(
                        registModel.getMemberInfoPrefecture())) {
            return true;
        }
        ZipCodeMatchingCheckRequest zipCodeMatchingCheckRequest = new ZipCodeMatchingCheckRequest();

        zipCodeMatchingCheckRequest.setZipCode(
                        registModel.getMemberInfoZipCode1() + registModel.getMemberInfoZipCode2());
        zipCodeMatchingCheckRequest.setPrefecture(registModel.getMemberInfoPrefecture());

        ResultFlagResponse response = null;
        try {
            response = zipcodeApi.registCheckZipcodeMatching(zipCodeMatchingCheckRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 郵便番号住所情報取得サービス実行
        return response.getResultFlag();
    }

    /**
     * 入力チェック
     * // PDR Migrate Customization from here
     * <pre>
     * 大まかな流れはPKG標準のソースを流用
     * 性別のチェックを削除
     * 業種の入力チェックを追加
     * </pre>
     * // PDR Migrate Customization to here
     *
     * @param registModel 本会員登録 Model
     */
    protected void checkIllegalParameter(RegistModel registModel) {

        // 都道府県入力値チェック
        checkPrefecture(registModel);
        // PDR Migrate Customization from here
        // 性別の入力チェックを削除
        // 業種の入力チェック
        checkBusinessType(registModel);
        // PDR Migrate Customization to here
        // 性別入力値チェック
        checkSex(registModel);

    }

    // PDR Migrate Customization from here

    /**
     * 業種の入力チェック
     * POSTされる値の書き換え対策
     */
    protected void checkBusinessType(RegistModel registModel) {
        Map<String, String> businessTypeItems = registModel.getBusinessTypeItems();
        String selectedValue = registModel.getBusinessType();
        String key = null;

        for (Map.Entry<String, String> entry : businessTypeItems.entrySet()) {
            key = entry.getKey();
            if (selectedValue.equals(key)) {
                return;
            }
        }

        addErrorMessage(MSGCD_ILLEGAL_BUSINESSTYPE);
    }

    /**
     * 診療項目表示設定
     *
     * @param registModel ページクラス
     */
    protected void setMedicalTreatment(RegistModel registModel) {

        // 診療項目1_タイトル
        registModel.setMedicalTreatment1Title(medicalTreatmentDto.getMedicalTreatment1Title());
        // 診療項目2_タイトル
        registModel.setMedicalTreatment2Title(medicalTreatmentDto.getMedicalTreatment2Title());
        // 診療項目3_タイトル
        registModel.setMedicalTreatment3Title(medicalTreatmentDto.getMedicalTreatment3Title());
        // 診療項目4_タイトル
        registModel.setMedicalTreatment4Title(medicalTreatmentDto.getMedicalTreatment4Title());
        // 診療項目5_タイトル
        registModel.setMedicalTreatment5Title(medicalTreatmentDto.getMedicalTreatment5Title());
        // 診療項目6_タイトル
        registModel.setMedicalTreatment6Title(medicalTreatmentDto.getMedicalTreatment6Title());
        // 診療項目7_タイトル
        registModel.setMedicalTreatment7Title(medicalTreatmentDto.getMedicalTreatment7Title());
        // 診療項目8_タイトル
        registModel.setMedicalTreatment8Title(medicalTreatmentDto.getMedicalTreatment8Title());
        // 診療項目9_タイトル
        registModel.setMedicalTreatment9Title(medicalTreatmentDto.getMedicalTreatment9Title());
        // 診療項目10_タイトル
        registModel.setMedicalTreatment10Title(medicalTreatmentDto.getMedicalTreatment10Title());

        // 診療項目1_表示判定
        registModel.setMedicalTreatment1Disp(medicalTreatmentDto.getMedicalTreatment1Disp());
        // 診療項目2_表示判定
        registModel.setMedicalTreatment2Disp(medicalTreatmentDto.getMedicalTreatment2Disp());
        // 診療項目3_表示判定
        registModel.setMedicalTreatment3Disp(medicalTreatmentDto.getMedicalTreatment3Disp());
        // 診療項目4_表示判定
        registModel.setMedicalTreatment4Disp(medicalTreatmentDto.getMedicalTreatment4Disp());
        // 診療項目5_表示判定
        registModel.setMedicalTreatment5Disp(medicalTreatmentDto.getMedicalTreatment5Disp());
        // 診療項目6_表示判定
        registModel.setMedicalTreatment6Disp(medicalTreatmentDto.getMedicalTreatment6Disp());
        // 診療項目7_表示判定
        registModel.setMedicalTreatment7Disp(medicalTreatmentDto.getMedicalTreatment7Disp());
        // 診療項目8_表示判定
        registModel.setMedicalTreatment8Disp(medicalTreatmentDto.getMedicalTreatment8Disp());
        // 診療項目9_表示判定
        registModel.setMedicalTreatment9Disp(medicalTreatmentDto.getMedicalTreatment9Disp());
        // 診療項目10_表示判定
        registModel.setMedicalTreatment10Disp(medicalTreatmentDto.getMedicalTreatment10Disp());
    }
    // PDR Migrate Customization to here

    /**
     * 都道府県の入力チェック
     * POSTされるプルダウンの値の書き換え対策
     *
     * @param registModel 本会員登録 Model
     */
    protected void checkPrefecture(RegistModel registModel) {

        Map<String, String> prefectureMap = EnumTypeUtil.getEnumMap(HTypePrefectureType.class, true);
        boolean existFlag = prefectureMap.containsKey(registModel.getMemberInfoPrefecture());
        if (!existFlag) {
            throwMessage(MSGCD_ILLEGAL_PREFECTURE);
        }
    }

    /**
     * 性別(未回答)の入力チェック
     * POSTされる値の書き換え対策
     *
     * @param registModel 本会員登録 Model
     */
    protected void checkSex(RegistModel registModel) {

        Map<String, String> memberInfoSexMap = EnumTypeUtil.getEnumMap(HTypeSexUnnecessaryAnswer.class);
        boolean existFlag = memberInfoSexMap.containsKey(registModel.getMemberInfoSex());
        if (!existFlag) {
            throwMessage(MSGCD_ILLEGAL_SEX);
        }
        return;
    }

    // PDR Migrate Customization from here

    /**
     * お客様番号入力画面：初期処理
     * <pre>
     * 前画面で必要な情報が入力されているかチェック
     * </pre>
     *
     * @param registModel        本会員登録 Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return 入力画面
     */
    @GetMapping(value = {"/inputmemno", "/inputmemno.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "regist/inputmemno")
    protected String doLoadInputMemNo(RegistModel registModel, RedirectAttributes redirectAttributes, Model model) {

        // 不正遷移の場合はエラーページへ遷移させる。
        // パラメータチェック
        // 2023-renew No85-1 from here
        if (StringUtils.isEmpty(registModel.getMemberInfoTel()) || !model.containsAttribute(FLAG_FROMINPUTTEL)) {
            // 2023-renew No85-1 to here
            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
            return "redirect:/error.html";
        }
        // 自画面表示
        return "regist/inputmemno";
    }

    /**
     * お客様番号入力画面：次へ進む
     *
     * 電話番号と顧客番号から新規会員登録可能かどうか判定し
     * 画面遷移を行う。
     *
     * @param registModel        本会員登録 Model
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return お客様番号入力画面 または 会員情報入力画面
     */
    @PostMapping(value = {"/inputmemno", "/inputmemno.html"}, params = "doConfirmInputMemno")
    @HEHandler(exception = AppLevelListException.class, returnView = "regist/inputmemno")
    public String doConfirmInputMemNo(@Validated(InputmemnoGroup.class) RegistModel registModel,
                                      BindingResult error,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {

        if (error.hasErrors()) {
            return "regist/inputmemno";
        }

        // 必要な情報が存在しない場合はエラー画面へ遷移
        if (StringUtils.isEmpty(registModel.getMemberInfoTel())) {
            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
            return "redirect:/error.html";
        }

        /// ⅰ)顧客番号&電話番号から会員情報取得
        MemberInfoByTelAndCustomerNoGetRequest memberInfoByTelAndCustomerNoGetRequest =
                        new MemberInfoByTelAndCustomerNoGetRequest();
        memberInfoByTelAndCustomerNoGetRequest.setCustomerNo(registModel.getCustomerNo());
        memberInfoByTelAndCustomerNoGetRequest.setMemberInfoTel(registModel.getMemberInfoTel());

        MemberInfoEntityResponse memberInfoEntityResponse = null;
        try {
            memberInfoEntityResponse = memberInfoApi.getByTelCustomerNo(memberInfoByTelAndCustomerNoGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        MemberInfoEntity memberInfoEntity = registHelper.toMemberInfoEntity(memberInfoEntityResponse);

        /// ⅱ) i)で会員情報取得出来た場合、ログイン可能な会員か判定
        if (memberInfoEntity != null) {

            // 登録チェック用にコピーを作成
            MemberInfoEntity memberInfoCheckEntity = CopyUtil.deepCopy(memberInfoEntity);

            LoginAdvisabilityGetRequest loginAdvisabilityGetRequest =
                            registHelper.toLoginAdvisabilityGetRequest(memberInfoCheckEntity);

            LoginAdvisabilityEntityResponse loginAdvisabilityEntityResponse = null;
            try {
                loginAdvisabilityEntityResponse = memberInfoApi.getByLoginAdvisability(loginAdvisabilityGetRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            LoginAdvisabilityEntity loginAdvisabilityEntity =
                            registHelper.toLoginAdvisabilityEntity(loginAdvisabilityEntityResponse);
            // 2023-renew No85-1 from here
            redirectAttributes.addFlashAttribute(FLAG_FROMINPUTCUSTOMERNO, true);
            if (loginAdvisabilityEntity.getLoginAdvisabilitytype() == null) {
                // ログイン可否判定取得に失敗した、エラーメッセージを次画面で表示
                redirectAttributes.addFlashAttribute("msgcdSetCustomerNo", MSGCD_MEMBERLIST_TYPE_ERR);
                registModel.setFromInputMemNoExist(false);
                return "redirect:/regist/inputmail.html";
            }

            /// ⅲ) ⅱ)でログイン不可の場合は、既存会員か判定
            if (loginAdvisabilityEntity.getLoginAdvisabilitytype().equals("0")) {
                // ログイン可否をチェックする為に条件を変更（会員状態と承認状態はそれぞれ「入会」、「承認」としてチェックするため
                memberInfoCheckEntity.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION);
                memberInfoCheckEntity.setApproveStatus(HTypeApproveStatus.ON);
                loginAdvisabilityGetRequest = registHelper.toLoginAdvisabilityGetRequest(memberInfoCheckEntity);

                LoginAdvisabilityEntityResponse existMemberLoginAdvisabilityEntityResponse = null;
                try {
                    existMemberLoginAdvisabilityEntityResponse =
                                    memberInfoApi.getByLoginAdvisability(loginAdvisabilityGetRequest);
                } catch (HttpClientErrorException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    // AppLevelListExceptionへ変換
                    addAppLevelListException(e);
                    throwMessage();
                }

                LoginAdvisabilityEntity existMemberLoginAdvisabilityEntity =
                                registHelper.toLoginAdvisabilityEntity(existMemberLoginAdvisabilityEntityResponse);

                if (existMemberLoginAdvisabilityEntity.getLoginAdvisabilitytype() == null) {
                    // ログイン可否判定取得に失敗した、エラーメッセージを次画面で表示
                    redirectAttributes.addFlashAttribute("msgcdSetCustomerNo", MSGCD_MEMBERLIST_TYPE_ERR);
                    registModel.setFromInputMemNoExist(false);
                    return "redirect:/regist/inputmail.html";
                }

                if (existMemberLoginAdvisabilityEntity.getLoginAdvisabilitytype().equals("0")) {
                    // ログイン可否判定取得に失敗した、エラーメッセージを次画面で表示
                    redirectAttributes.addFlashAttribute("msgcdSetCustomerNo", MSGCD_MEMBERLIST_TYPE_ERR);
                    registModel.setFromInputMemNoExist(false);
                    return "redirect:/regist/inputmail.html";
                }

                // 既存会員の場合は、会員情報表示画面へ遷移
                registModel.setMemberInfoEntity(memberInfoEntity);
                if (StringUtils.isNotEmpty(memberInfoEntity.getMemberInfoFax())) {
                    registModel.setMemberInfoFaxExist(true);
                }
                return "redirect:/regist/index.html";

            } else {

                // ログイン可能な場合は、「パスワードを忘れた場合画面」へ
                if (registModel.isFromInputTelExist()) {
                    redirectAttributes.addFlashAttribute("memberInfoTel", registModel.getMemberInfoTel());
                }
                registModel.setFromInputMemNoExist(true);
                return "redirect:/reset/index.html";
            }
            // 2023-renew No85-1 to here

        } else {
            // i)で会員情報取得出来なかった場合
            String appComplementUrl = PropertiesUtil.getSystemPropertiesValue("app.complement.url");
            addMessage(MSGCD_NOTFOUND_MEMBERINFO, new Object[] {appComplementUrl}, redirectAttributes, model);
            // 2023-renew No85-1 from here
            // 遷移先に渡す情報を設定（エラーメッセージを次画面で表示）
            redirectAttributes.addFlashAttribute("msgcdSetCustomerNo", MSGCD_NOTFOUND_MEMBERINFO);
            redirectAttributes.addFlashAttribute(FLAG_FROMINPUTCUSTOMERNO, true);

            registModel.setFromInputMemNoExist(false);

            return "redirect:/regist/inputmail.html";
        }
        // 2023-renew No85-1 to here
    }

    // 2023-renew No85-1 from here

    /**
     * リダイレクトメールに移動
     *
     * @param registModel
     * @param error
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping(value = {"/inputmemno", "/inputmemno.html"}, params = "goToInputMail")
    @HEHandler(exception = AppLevelListException.class, returnView = "regist/inputmemno")
    public String goToInputMail(@Validated(InputmemnoGroup.class) RegistModel registModel,
                                BindingResult error,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        String memberInfoTel = registModel.getMemberInfoTel();
        if (StringUtils.isNotEmpty(memberInfoTel)) {
            registModel.setMemberInfoTel(memberInfoTel);
        }
        redirectAttributes.addFlashAttribute(FLAG_FROMINPUTCUSTOMERNO, true);
        return "redirect:/regist/inputmail.html";
    }
    // 2023-renew No85-1 to here

    // 2023-renew No85-1 from here
    //    /**
    //     * 入力画面：初期処理
    //     *
    //     * <pre>
    //     * 前画面で必要な情報が入力されているかチェックを行う。
    //     * </pre>
    //     *
    //     * @return 自画面
    //     */
    //    @GetMapping(value = {"/confirminfo", "/confirminfo.html"})
    //    protected String doLoadConfirminfo(RegistModel registModel, RedirectAttributes redirectAttributes, Model model) {
    //
    //        // 不正遷移の場合はエラーページへ遷移させる。
    //        // パラメータチェック
    //        if (checkInputConfirminfo(registModel)) {
    //            // 前画面で会員情報Entityが設定されていない場合は不正遷移とする。
    //            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
    //            return "redirect:/error.html";
    //        }
    //
    //        // 画面に値を設定
    //        registHelper.toPageForLoadConfirmInfo(registModel);
    //
    //        if (registModel.isSetPassword()) {
    //            String appComplementUrl = PropertiesUtil.getSystemPropertiesValue("app.complement.url");
    //            addMessage(MSGCD_SET_PASSWORD, new Object[] {appComplementUrl}, redirectAttributes, model);
    //
    //            // 値の電子メールをリダイレクトし、ページをリセットするように指示します。
    //            redirectAttributes.addFlashAttribute("memberInfoTel", registModel.getMemberInfoEntity().getMemberInfoTel());
    //            return "redirect:/reset/index.html";
    //        }
    //
    //        // 自画面表示
    //        return "regist/confirminfo";
    //    }
    // 2023-renew No85-1 to here

    /**
     * 入力画面：会員情報更新処理を行います。
     * <pre>
     * 会員情報更新処理を行い、WEB-API連携をし
     * 顧客番号・パスワード通知メールを送信します。
     * </pre>
     *
     * @param registModel        本会員登録 Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param error              エラー
     * @param model              モデル
     * @return 申込み完了画面
     */
    // 2023-renew No85-1 from here
    @PostMapping(value = {"/confirm", "/confirm.html"}, params = "doOnceMemberInfoUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "regist/confirm")
    // 2023-renew No85-1 to here
    public String doOnceMemberInfoUpdate(@Validated(ConfirmInfoGroup.class) RegistModel registModel,
                                         BindingResult error,
                                         RedirectAttributes redirectAttributes,
                                         Model model,
                                         // 2023-renew No22 from here
                                         HttpServletRequest request) {
        // 2023-renew No22 to here

        if (error.hasErrors()) {
            // 2023-renew No85-1 from here
            return "regist/confirm";
            // 2023-renew No85-1 to here
        }

        MemberInfoEntity memberInfoEntity = null;
        // 検索処理
        try {
            // メールアドレスを会員情報Entityに設定
            memberInfoEntity = registHelper.toMemberInfoEntityForMemberInfoUpdate(registModel);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/regist/index.html";
        }

        MemberInfoScreenUpdateRequest memberInfoScreenUpdateRequest =
                        registHelper.toMemberInfoScreenUpdateRequest(memberInfoEntity);
        // 2023-renew No85-1 from here
        settingMemberInfo(memberInfoScreenUpdateRequest);
        // 2023-renew No85-1 to here
        try {
            // 2023-renew No22 from here
            MemberInfoEntityResponse memberInfoEntityResponse =
                            memberInfoApi.updateMemberInfoScreen(memberInfoScreenUpdateRequest);
            Integer memberInfoSeq = memberInfoEntityResponse.getMemberInfoSeq();
            copyTmpToRealPath(memberInfoSeq, registModel.getRegistUploadFiles(), request.getSession());
            // 2023-renew No22 to here
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 2023-renew No85-1 from here
        // から客様番号画面
        registModel.setFromInputMemNoExist(false);
        // 2023-renew No85-1 to here

        return "redirect:/regist/completeapply.html";
    }

    /**
     * 完了画面：画面表示処理
     *
     * @param registModel   本会員登録 Model
     * @param sessionStatus セクションステータス
     * @return 完了画面
     */
    @GetMapping(value = {"/completeapply", "/completeapply.html"})
    public String doLoadCompleteapply(RegistModel registModel, SessionStatus sessionStatus) {

        // Modelをセッションより破棄
        sessionStatus.setComplete();
        return "regist/completeapply";
    }

    // 2023-renew No85-1 from here

    /**
     * ご登録メールアドレス入力画面：初期処理
     *
     * @param registModel        本会員登録 Model
     * @param model              モデル
     * @return 入力画面
     */
    @GetMapping(value = {"/inputmail", "/inputmail.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "regist/inputmail")
    public String doLoadInputMail(RegistModel registModel, RedirectAttributes redirectAttributes, Model model) {

        if (!model.containsAttribute(FLAG_FROMINPUTTEL) && !model.containsAttribute(FLAG_FROMINPUTCUSTOMERNO)) {
            // 前画面で会員情報Entityが設定されていない場合は不正遷移とする。
            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
            return "redirect:/error.html";
        }

        // 電話番号とお客様番号が一致せず、お客様番号入力画面から遷移してきた場合、エラーメッセージを表示する。
        String msgcdSetCustomerNo = (String) model.getAttribute("msgcdSetCustomerNo");
        if (StringUtils.isNotEmpty(msgcdSetCustomerNo)) {
            throwMessage(msgcdSetCustomerNo);
        }

        return "regist/inputmail";
    }

    /**
     * ご登録メールアドレス入力画面：次へ進む
     *
     * @param registModel 本会員登録 Model
     * @return おパスワード再設定画面 または お客様情報入力画面
     */
    @PostMapping(value = {"/inputmail", "/inputmail.html"}, params = "doConfirmInputMail")
    @HEHandler(exception = AppLevelListException.class, returnView = "regist/inputmail")
    public String doConfirmInputMail(@Validated(InputMailGroup.class) RegistModel registModel,
                                     BindingResult error,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        if (error.hasErrors()) {
            return "regist/inputmail";
        }

        /// ⅰ) 会員情報の取得
        MemberInfoByMemberInfoIdOrCustumerNoGetRequest memberInfoByMemberInfoIdOrCustumerNoGetRequest =
                        new MemberInfoByMemberInfoIdOrCustumerNoGetRequest();
        memberInfoByMemberInfoIdOrCustumerNoGetRequest.setMemberInfoIdOrCustomerNo(registModel.getMemberInfoMail());
        MemberInfoEntityResponse memberInfoEntityResponse = null;
        try {
            memberInfoEntityResponse =
                            memberInfoApi.getByMemberInfoOrCustomerNo(memberInfoByMemberInfoIdOrCustumerNoGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        MemberInfoEntity memberInfoEntity = registHelper.toMemberInfoEntity(memberInfoEntityResponse);

        // 会員情報を取得できない場合、お客様情報入力画面に遷移する。
        if (ObjectUtils.isEmpty(memberInfoEntity)) {
            redirectAttributes.addFlashAttribute(FLAG_FROMINPUTMAIL, true);
            return "redirect:/regist/index.html";
        }

        /// ⅱ)会員情報が取得できた場合は、ログイン可能な会員か判定する
        // 登録チェック用にコピーを作成
        MemberInfoEntity memberInfoCheckEntity = CopyUtil.deepCopy(memberInfoEntity);

        LoginAdvisabilityGetRequest loginAdvisabilityGetRequest =
                        registHelper.toLoginAdvisabilityGetRequest(memberInfoCheckEntity);

        LoginAdvisabilityEntityResponse loginAdvisabilityEntityResponse = null;
        try {
            loginAdvisabilityEntityResponse = memberInfoApi.getByLoginAdvisability(loginAdvisabilityGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        LoginAdvisabilityEntity loginAdvisabilityEntity =
                        registHelper.toLoginAdvisabilityEntity(loginAdvisabilityEntityResponse);
        if (loginAdvisabilityEntity.getLoginAdvisabilitytype() == null) {
            // ログイン可否判定取得に失敗した、エラーメッセージ表示
            throwMessage(MSGCD_NOT_POSSIBLE_LOGIN);
        }
        // ログイン不可の場合は、エラーメッセージを出力し自画面遷移。
        if (loginAdvisabilityEntity.getLoginAdvisabilitytype().equals("0")) {
            throwMessage(MSGCD_NOT_POSSIBLE_LOGIN);
        }

        // 電話番号情報をFlashAttributesに保存
        if (registModel.isFromInputTelExist()) {
            redirectAttributes.addFlashAttribute("memberInfoTel", registModel.getMemberInfoTel());
        }
        // 会員メールアドレス情報をFlashAttributesに保存
        redirectAttributes.addFlashAttribute("memberInfoMail", registModel.getMemberInfoMail());

        // ログイン可能な場合、パスワード再設定画面に遷移する
        return "redirect:/reset/index.html";

    }
    // 2023-renew No85-1 to here

    /**
     * 必須項目を全てチェックし、不正遷移かどうかをチェック
     *
     * @return true=不正、false=正常
     */
    protected boolean checkInputConfirminfo(RegistModel registModel) {

        if (registModel.getMemberInfoEntity() == null) {
            return true;
        }
        // 顧客番号
        if (registModel.getMemberInfoEntity().getCustomerNo() == null) {
            return true;
        }
        // 事業所名
        if (StringUtils.isEmpty(registModel.getMemberInfoEntity().getMemberInfoLastName())) {
            return true;
        }
        // 電話番号
        if (StringUtils.isEmpty(registModel.getMemberInfoEntity().getMemberInfoTel())) {
            return true;
        }
        // 郵便番号
        if (StringUtils.isEmpty(registModel.getMemberInfoEntity().getMemberInfoZipCode())) {
            return true;
        }
        // 住所1
        if (StringUtils.isEmpty(registModel.getMemberInfoEntity().getMemberInfoAddress1())) {
            return true;
        }
        // 住所2
        if (StringUtils.isEmpty(registModel.getMemberInfoEntity().getMemberInfoAddress2())) {
            return true;
        }
        return false;
    }
    // PDR Migrate Customization to here
    // 2023-renew No22 from here

    /**
     * 確認書類をアップロードする
     *
     * @param registModel 本会員登録 Model
     * @param error       BindingResult
     * @param model       Model
     * @return string
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doUploadFile")
    public String doUploadFile(@Validated(UploadDocsGroup.class) RegistModel registModel,
                               BindingResult error,
                               Model model,
                               HttpSession session) {
        if (error.hasErrors()) {
            registModel.setReloadFlag(true);
            return "regist/index";
        }

        MultipartFile[] uploadFiles = registModel.getUploadFiles();
        registModel.setErrorUpload(false);
        if (registModel.getRegistUploadFiles() == null) {
            List<RegistUploadFile> uploadFileList = new ArrayList<>();
            registModel.setRegistUploadFiles(uploadFileList);
        }
        if (registModel.getRegistUploadFiles().size() + uploadFiles.length > 3) {
            registModel.setErrorUpload(true);
            registModel.setReloadFlag(true);
            return "redirect:/regist/index.html";
        }
        String key = getRegistSession(session);
        String filePath = createFolder(key, TMP_PATH);
        doUploadFile(registModel, filePath, uploadFiles);

        registModel.setReloadFlag(true);

        return "redirect:/regist/index.html";
    }

    /**
     * ファイルを削除する
     *
     * @param registModel 本会員登録 Model
     * @param error                 BindingResult
     * @param model                 Model
     * @return string
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doDeleteFile")
    public String doDeleteFile(RegistModel registModel, BindingResult error, Model model, HttpSession session) {

        if (registModel.getFileNo() != null) {
            int index = -1;
            for (int i = 0; i < registModel.getRegistUploadFiles().size(); i++) {
                if (registModel.getRegistUploadFiles().get(i).getFileNo().equals(registModel.getFileNo())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                registModel.getRegistUploadFiles().remove(index);
            }
        }

        registModel.setErrorUpload(false);
        registModel.setReloadFlag(true);

        return "redirect:/regist/index.html";
    }

    // 2023-renew No85-1 from here
    // 不要になった画面
    //
    //    /**
    //     * 確認書類をアップロードする
    //     *
    //     * @param registModel 本会員登録 Model
    //     * @param error                 BindingResult
    //     * @param model                 Model
    //     * @param session               HttpSession
    //     * @return string
    //     */
    //    @PostMapping(value = {"/confirminfo", "/confirminfo.html"}, params = "doUploadFileConfirmInfo")
    //    public String doUploadFileConfirmInfo(RegistModel registModel,
    //                                          BindingResult error,
    //                                          Model model,
    //                                          HttpSession session) {
    //        MultipartFile[] uploadFiles = registModel.getUploadFiles();
    //        registModel.setErrorUpload(false);
    //        if (registModel.getRegistUploadFiles() == null) {
    //            List<RegistUploadFile> uploadFileList = new ArrayList<>();
    //            registModel.setRegistUploadFiles(uploadFileList);
    //        }
    //        if (registModel.getRegistUploadFiles().size() + uploadFiles.length > 3) {
    //            registModel.setErrorUpload(true);
    //            registModel.setReloadFlag(true);
    //            return "redirect:/regist/confirminfo.html";
    //        }
    //        String key = getRegistSession(session);
    //        String filePath = createFolder(key, TMP_PATH);
    //        doUploadFile(registModel, filePath, uploadFiles);
    //
    //        registModel.setReloadFlag(true);
    //
    //        return "redirect:/regist/confirminfo.html";
    //    }
    //
    //    /**
    //     * ファイルを削除する
    //     *
    //     * @param registModel 本会員登録 Model
    //     * @param error                 BindingResult
    //     * @param model                 Model
    //     * @param session               HttpSession
    //     * @return string
    //     */
    //    @PostMapping(value = {"/confirminfo", "/confirminfo.html"}, params = "doDeleteFileConfirmInfo")
    //    public String doDeleteFileConfirmInfo(RegistModel registModel,
    //                                          BindingResult error,
    //                                          Model model,
    //                                          HttpSession session) throws IOException {
    //
    //        if (registModel.getFileNo() != null) {
    //            int index = -1;
    //            for (int i = 0; i < registModel.getRegistUploadFiles().size(); i++) {
    //                if (registModel.getRegistUploadFiles().get(i).getFileNo().equals(registModel.getFileNo())) {
    //                    index = i;
    //                    break;
    //                }
    //            }
    //            if (index != -1) {
    //                String registSession = getRegistSession(session);
    //                String fileName = registModel.getRegistUploadFiles().get(index).getFileName();
    //                String filePath = PropertiesUtil.getSystemPropertiesValue(TMP_PATH) + File.separator + registSession
    //                                  + File.separator + fileName;
    //                FileUtils.forceDelete(new File(filePath));
    //                registModel.getRegistUploadFiles().remove(index);
    //            }
    //        }
    //
    //        registModel.setErrorUpload(false);
    //        registModel.setReloadFlag(true);
    //
    //        return "redirect:/regist/confirminfo.html";
    //    }
    // 2023-renew No85-1 to here

    /**
     * 確認書類をアップロードする
     *
     * @param registModel
     * @param filePath
     * @param uploadFiles
     */
    private void doUploadFile(RegistModel registModel, String filePath, MultipartFile[] uploadFiles) {
        try {
            if (registModel.getRegistUploadFiles() == null)
                registModel.setRegistUploadFiles(new ArrayList<>());
            for (MultipartFile uploadFile : uploadFiles) {
                RegistUploadFile registUploadFile = new RegistUploadFile();
                String newFileName = memberHelper.renameFile(uploadFile.getOriginalFilename());
                registUploadFile.setOriginName(uploadFile.getOriginalFilename());
                registUploadFile.setFilePath(
                                PropertiesUtil.getSystemPropertiesValue(TMP_URI_CONFIRM_DOCUMENT) + "/" + newFileName);
                String target = filePath + File.separator + newFileName;
                Files.copy(uploadFile.getInputStream(), Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
                registUploadFile.setFileName(newFileName);
                if (registModel.getRegistUploadFiles().isEmpty()) {
                    registUploadFile.setFileNo(1);
                } else {
                    Optional<RegistUploadFile> maxNoFile = registModel.getRegistUploadFiles()
                                                                      .stream()
                                                                      .max(Comparator.comparingInt(
                                                                                      RegistUploadFile::getFileNo));
                    Integer lastIndex = -1;
                    if (maxNoFile.isPresent()) {
                        lastIndex = maxNoFile.get().getFileNo();
                    }
                    registUploadFile.setFileNo(lastIndex + 1);
                }
                if (uploadFile.getContentType() != null && uploadFile.getContentType().contains("pdf")) {
                    registUploadFile.setExtensionType(HTypeMemberImage.PDF);
                } else if (uploadFile.getContentType() != null && uploadFile.getContentType().contains("png")) {
                    registUploadFile.setExtensionType(HTypeMemberImage.PNG);
                }
                registModel.getRegistUploadFiles().add(registUploadFile);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * セッションを取得する
     *
     * @param session HttpSession
     * @return 時間
     */
    private String getRegistSession(HttpSession session) {
        return session.getId();
    }

    /**
     * 一時フォルダーを作成する
     *
     * @param key      セッション時間
     * @param typePath 一時ファイルをアップロードするフォルダーのパス
     * @return 一時フォルダ名
     */
    private String createFolder(String key, String typePath) {
        String filePath = PropertiesUtil.getSystemPropertiesValue(typePath) + File.separator + key;
        Path path = Paths.get(filePath);
        try {
            if (!(Files.exists(path) && Files.isDirectory(path))) {
                Files.createDirectories(path);
            }
            if (typePath.equals(REAL_PATH)) {
                FileUtils.cleanDirectory(new File(filePath));
            }
        } catch (IOException e) {
            LOGGER.error("ファイルのコピーに失敗する", e);
            throw new RuntimeException("一時リポジトリを作成できません");
        }
        return filePath;
    }

    /**
     * ファイルを保存
     */
    private void copyTmpToRealPath(Integer memberInfoSeq, List<RegistUploadFile> files, HttpSession session) {
        if (files == null)
            files = new ArrayList<>();
        String key = getRegistSession(session);
        String tmpPathFolder = createFolder(key, TMP_PATH);
        String realPathFolder = createFolder(String.valueOf(memberInfoSeq), REAL_PATH);
        try {
            int count = 1;
            for (RegistUploadFile file : files) {
                String sourcePath = tmpPathFolder + File.separator + file.getFileName();
                File sourceFile = new File(sourcePath);
                if (!sourceFile.exists()) {
                    LOGGER.error("画像が取得できない場合エラー");
                    throwMessage(MSGCD_FILE_IS_NOT_SPECIFIED);
                }
                String fileNameWithDate = memberHelper.renameFile(file.getOriginName());
                String destinationPath = realPathFolder + File.separator + fileNameWithDate;
                Path source = Paths.get(sourcePath);
                Path destination = Paths.get(destinationPath);
                Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
                // データベースに保存
                file.setFileName(fileNameWithDate);
                saveImage(memberInfoSeq, file, count);
                count++;
            }
            FileUtils.deleteDirectory(new File(tmpPathFolder));
        } catch (IOException e) {
            LOGGER.error("ファイルのコピーに失敗する", e);
        }
    }

    private void saveImage(Integer memberInfoSeq, RegistUploadFile registUploadFile, int no) {
        MemberInfoImageRequest request = new MemberInfoImageRequest();
        request.setMemberInfoSeq(memberInfoSeq);
        request.setMemberImageNo(no);
        request.setMemberImageFileName(registUploadFile.getFileName());
        request.setMemberImageType(registUploadFile.getExtensionType().getValue());
        memberInfoApi.createMemberImage(request);
    }
    // 2023-renew No22 to here

    // 2023-renew No85-1 from here

    /**
     * 会員情報の設定
     *
     * @param memberInfoScreenUpdateRequest 本会員登録画面用会員情報更新リクエスト
     */
    protected void settingMemberInfo(MemberInfoScreenUpdateRequest memberInfoScreenUpdateRequest) {

        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        memberInfoScreenUpdateRequest.setApproveStatus(HTypeApproveStatus.ON.getValue());
        memberInfoScreenUpdateRequest.setMemberInfoStatus(HTypeMemberInfoStatus.ADMISSION.getValue());
        memberInfoScreenUpdateRequest.setOnlineRegistFlag(HTypeOnlineRegistFlag.ON.getValue());
        memberInfoScreenUpdateRequest.setPasswordNeedChangeFlag(HTypePasswordNeedChangeFlag.ON.getValue());
        memberInfoScreenUpdateRequest.setAdmissionYmd(dateUtility.getCurrentYmd());
        memberInfoScreenUpdateRequest.setSecessionYmd(null);
    }
    // 2023-renew No85-1 to here
}
