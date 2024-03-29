package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadBottomGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadTopGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.memberinfo.validation.PeriodValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBusinessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoOutData;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadExtension;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailSendDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.MemberInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.image.MemberImageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.MemberInfoTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.Transformer;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryByMemberCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoAllCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoCsvDownloadService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoDetailsGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoDetailsUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoProcessCompleteMailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoResultSearchService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image.MemberInfoListImageService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderSearchOrderListGetService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.FileDownloadException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 会員検索コントロール
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/memberinfo")
@Controller
@SessionAttributes(value = {"memberInfoModel", "memberInfoDetailsModel"})
@PreAuthorize("hasAnyAuthority('MEMBER:4')")
public class MemberInfoController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoController.class);

    /**
     * 会員検索：デフォルトページ番号
     */
    private static final String DEFAULT_MEMBERSEARCH_PNUM = "1";

    /**
     * 会員検索：デフォルト：ソート項目
     */
    private static final String DEFAULT_MEMBERSEARCH_ORDER_FIELD = "memberInfoId";

    /**
     * 会員検索：デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_MEMBERSEARCH_ORDER_ASC = true;

    /**
     * 会員詳細：受注履歴：デフォルトページ番号
     */
    private static final String DEFAULT_ORDERHISTORY_PNUM = "1";

    /**
     * 会員詳細：受注履歴：デフォルト：ソート項目
     */
    private static final String DEFAULT_ORDERHISTORY_ORDER_FIELD = "orderTime";

    /**
     * 会員詳細：受注履歴：デフォルト：ソート条件(昇順/降順)
     */
    private static final boolean DEFAULT_ORDERHISTORY_ORDER_ASC = false;

    /**
     * 会員詳細：受注履歴：デフォルト：最大表示件数
     */
    private static final int DEFAULT_ORDERHISTORY_LIMIT = 10;

    private static final String MEMBER_INFO_INDEX_PAGE = "memberinfo/index";

    /**
     * 会員検索：デフォルト：最大表示件数
     */
    private static final int DEFAULT_MEMBERSEARCH_LIMIT = 100;

    /**
     * 表示モード:「list」の場合 再検索
     */
    public static final String FLASH_MD = "md";

    /**
     * 会員検索ページHelper
     */
    private final MemberInfoHelper memberInfoHelper;

    /**
     * 会員検索サービス
     */
    private final MemberInfoResultSearchService memberInfoResultSearchService;

    /**
     * 会員詳細情報取得サービス
     */
    private final MemberInfoDetailsGetService memberInfoDetailsGetService;

    /**
     * 受注検索（受注一覧）取得サービス
     */
    private final OrderSearchOrderListGetService orderSearchOrderListGetService;

    /**
     * 会員に紐付く問い合わせの存在チェックロジック
     */
    private final InquiryByMemberCheckLogic inquiryByMemberCheckLogic;

    /**
     * 会員データダウンロードサービス
     */
    private final MemberInfoCsvDownloadService memberInfoCsvDownloadService;

    /**
     * 会員データ一括ダウンロードサービス
     */
    private final MemberInfoAllCsvDownloadService memberInfoAllCsvDownloadService;

    /**
     * 期間の開始日付、終了日付の動的バリデータ
     */
    private final PeriodValidator periodValidator;

    /**
     * 環境定数
     */
    private final Environment environment;

    // 2023-renew No25 from here
    /**
     * 会員情報更新サービス
     */
    private final MemberInfoUpdateService memberInfoUpdateService;

    /**
     * 会員処理完了メール送信サービス
     */
    private final MemberInfoProcessCompleteMailSendService memberInfoProcessCompleteMailSendService;

    /**
     * ログイン可否判定
     */
    private final static String RESEND_CUSTOMER_NO_AND_PASSWORD_EMAIL_ERROR_MESSAGE_CODE = "PDR-2023RENEW-25-001-E";

    /**
     * DBサービスエラー
     */
    private final static String RESEND_CUSTOMER_NO_AND_PASSWORD_EMAIL_DB_SERVICE_ERROR_MESSAGE_CODE =
                    "PDR-2023RENEW-25-002-E";

    /**
     * EMAIL送信サービスエラー
     */
    private final static String RESEND_CUSTOMER_NO_AND_PASSWORD_EMAIL_SERVICE_ERROR_MESSAGE_CODE =
                    "PDR-2023RENEW-25-003-E";

    // 2023-renew No25 to here
    // 2023-renew No85-2 from here
    /**
     * 会員詳細情報更新サービス
     */
    private final MemberInfoDetailsUpdateService memberInfoDetailsUpdateService;

    /**
     * サービスエラー
     */
    private final static String CANCEL_MEMBER_SERVICE_ERROR_MESSAGE_CODE = "PDR-2023RENEW-85-2-001-E";

    /**
     * データをチェック
     */
    private final static String CANCEL_MEMBER_CHECK_DATA_MESSAGE_CODE = "PDR-2023RENEW-85-2-002-W";
    // 2023-renew No85-2 to here
    // 2023-renew No22 from here
    /**
     * 確認書類のストレージパス
     */
    private static final String REAL_PATH = "real.path.conf.document";

    /**
     * 管理サイトの構成パスの画像を表示
     */
    private static final String CONF_DOCUMENT_URI_NAME = "conf.document.uri.name";
    /**
     * 会員画像一覧サービスインターフェース
     */
    private final MemberInfoListImageService memberInfoListImageService;
    // 2023-renew No22 to here

    /**
     * コンストラクタ
     *
     * @param memberInfoHelper                         会員検索HELPER
     * @param memberInfoResultSearchService            バック会員検索サービス
     * @param memberInfoDetailsGetService              会員詳細取得サービス
     * @param orderSearchOrderListGetService           受注番号別一覧検索
     * @param inquiryByMemberCheckLogic                会員に紐付く問い合わせの存在チェック
     * @param memberInfoAllCsvDownloadService          CSVダウンロードサービス：全件出力
     * @param memberInfoCsvDownloadService             CSVダウンロードサービス：選択した会員のみ出力
     * @param periodValidator                          期間の開始日付、終了日付の動的バリデータ
     * @param environment                              環境定数
     * @param memberInfoDetailsUpdateService           会員詳細情報更新サービス
     * @param memberInfoUpdateService                  会員情報更新サービス
     * @param memberInfoProcessCompleteMailSendService 会員処理完了メール送信サービス
     */
    @Autowired
    public MemberInfoController(MemberInfoHelper memberInfoHelper,
                                MemberInfoResultSearchService memberInfoResultSearchService,
                                MemberInfoDetailsGetService memberInfoDetailsGetService,
                                OrderSearchOrderListGetService orderSearchOrderListGetService,
                                InquiryByMemberCheckLogic inquiryByMemberCheckLogic,
                                MemberInfoAllCsvDownloadService memberInfoAllCsvDownloadService,
                                MemberInfoCsvDownloadService memberInfoCsvDownloadService,
                                PeriodValidator periodValidator,
                                Environment environment,
                                MemberInfoDetailsUpdateService memberInfoDetailsUpdateService,
                                // 2023-renew No22 from here
                                MemberInfoListImageService memberInfoListImageService,
                                // 2023-renew No22 to here
                                // 2023-renew No25 from here
                                MemberInfoUpdateService memberInfoUpdateService,
                                MemberInfoProcessCompleteMailSendService memberInfoProcessCompleteMailSendService) {
        // 2023-renew No25 to here
        this.memberInfoHelper = memberInfoHelper;
        this.memberInfoResultSearchService = memberInfoResultSearchService;
        this.memberInfoDetailsGetService = memberInfoDetailsGetService;
        this.orderSearchOrderListGetService = orderSearchOrderListGetService;
        this.inquiryByMemberCheckLogic = inquiryByMemberCheckLogic;
        this.memberInfoAllCsvDownloadService = memberInfoAllCsvDownloadService;
        this.memberInfoCsvDownloadService = memberInfoCsvDownloadService;
        this.periodValidator = periodValidator;
        this.environment = environment;
        // 2023-renew No85-2 from here
        this.memberInfoDetailsUpdateService = memberInfoDetailsUpdateService;
        // 2023-renew No85-2 to here
        // 2023-renew No25 from here
        this.memberInfoUpdateService = memberInfoUpdateService;
        this.memberInfoProcessCompleteMailSendService = memberInfoProcessCompleteMailSendService;
        // 2023-renew No25 to here
        // 2023-renew No22 from here
        this.memberInfoListImageService = memberInfoListImageService;
        // 2023-renew No22 to here
    }

    @InitBinder(value = "memberInfoModel")
    public void initBinder(WebDataBinder error) {
        // メール件名の動的バリデータをセット
        error.addValidators(periodValidator);
    }

    /**
     * 初期処理
     *
     * @param md              RequestParam
     * @param memberInfoModel 会員検索モデル
     * @param model           Model
     */
    @GetMapping("/")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_INDEX_PAGE)
    public String doLoadIndex(@RequestParam(required = false) Optional<String> md,
                              @RequestParam(required = false) Optional<String> memberParam,
                              MemberInfoModel memberInfoModel,
                              Model model) {
        // PDR Migrate Customization from here
        //        /**
        //         *
        //         * PDR#11 08_データ連携（顧客情報）未承認状態リンク遷移による初期表示部分を追加<br/>
        //         * 会員検索ページアクションクラス<br/>
        //         *
        //         */
        // 未承認リンクからの遷移の場合は画面情報"承認状態"を未承認へ
        if (memberParam.isPresent() && "1".equals(memberParam.get())) {

            clearModel(MemberInfoModel.class, memberInfoModel, model);
            memberInfoModel.setApproveStatus(HTypeApproveStatus.OFF.getValue());
            memberInfoModel.setOnlineRegistFlag(HTypeOnlineRegistFlag.ON.getValue());
            // ページング関連項目初期化（limitは画面プルダウンで指定されてくる）
            memberInfoModel.setOrderAsc(DEFAULT_MEMBERSEARCH_ORDER_ASC);
            memberInfoModel.setOrderField(DEFAULT_MEMBERSEARCH_ORDER_FIELD);
            memberInfoModel.setPageNumber(DEFAULT_MEMBERSEARCH_PNUM);
            memberInfoModel.setLimit(DEFAULT_MEMBERSEARCH_LIMIT);
            // 再検索を実行
            search(memberInfoModel, model);
            // PDR Migrate Customization to here
        } else
            // 再検索の場合
            if (md.isPresent() || model.containsAttribute(FLASH_MD)) {
                // お問い合わせ詳細 ⇒ 紐づけた会員IDから会員詳細 ⇒ 戻る の流れで
                // 実施する際に、pageLimitが未設定のケースがあったため、こちらのロジックを追加する
                if (memberInfoModel.getLimit() == 0) {
                    memberInfoModel.setLimit(memberInfoModel.getPageDefaultLimitModel());
                }
                // 再検索を実行
                search(memberInfoModel, model);
            } else {
                clearModel(MemberInfoModel.class, memberInfoModel, model);
            }

        // プルダウンアイテム情報を取得
        initDropDownValue(memberInfoModel);

        return MEMBER_INFO_INDEX_PAGE;
    }

    /**
     * 検索
     *
     * @param memberInfoModel    会員検索モデル
     * @param error              BindingResult
     * @param redirectAttributes RedirectAttributes
     * @param model              Model
     */
    @PostMapping(value = "/", params = "doSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_INDEX_PAGE)
    public String doSearch(@Validated(SearchGroup.class) MemberInfoModel memberInfoModel,
                           BindingResult error,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        if (error.hasErrors()) {
            return MEMBER_INFO_INDEX_PAGE;
        }

        // ページング関連項目初期化（limitは画面プルダウンで指定されてくる）
        memberInfoModel.setPageNumber(DEFAULT_MEMBERSEARCH_PNUM);
        memberInfoModel.setOrderField(DEFAULT_MEMBERSEARCH_ORDER_FIELD);
        memberInfoModel.setOrderAsc(DEFAULT_MEMBERSEARCH_ORDER_ASC);

        // 検索
        search(memberInfoModel, model);
        return MEMBER_INFO_INDEX_PAGE;
    }

    /**
     * 検索結果の表示切替
     *
     * @param memberInfoModel 会員検索モデル
     * @param model           Model
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_INDEX_PAGE)
    public String doDisplayChange(@Validated(DisplayChangeGroup.class) MemberInfoModel memberInfoModel,
                                  BindingResult error,
                                  Model model) {

        if (error.hasErrors()) {
            return MEMBER_INFO_INDEX_PAGE;
        }

        // 検索結果チェック
        resultListCheck(memberInfoModel);
        search(memberInfoModel, model);
        return MEMBER_INFO_INDEX_PAGE;
    }

    /**
     * 詳細画面：初期処理
     *
     * @param memberInfoDetailsModel 会員詳細モデル
     * @param model                  Model
     */
    @GetMapping(value = "/details")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/memberinfo/")
    // 2023-renew No22 from here
    public String doLoadDetails(MemberInfoDetailsModel memberInfoDetailsModel, Model model, HttpSession httpSession) {
        // 2023-renew No22 to here
        // 会員SEQ必須の画面です。
        if (memberInfoDetailsModel.getMemberInfoSeq() == null) {
            // アカウントロックを解除したときは、getパラメータではなくunlockTargetSeqを利用して会員SEQを本画面に渡す
            if (memberInfoDetailsModel.getUnlockTargetSeq() != null) {
                memberInfoDetailsModel.setMemberInfoSeq(memberInfoDetailsModel.getUnlockTargetSeq());
            } else {
                return "redirect:/error";
            }
        }

        MemberInfoDetailsDto memberInfoDetailsDto;

        // 会員詳細取得サービス実行
        memberInfoDetailsDto = memberInfoDetailsGetService.execute(memberInfoDetailsModel.getMemberInfoSeq());
        if (memberInfoDetailsDto == null) {
            LOGGER.error("対象会員を取得できませんでした");
            return "redirect:/error";
        }

        // ページに反映
        memberInfoHelper.toModelDetailsForLoad(memberInfoDetailsDto, memberInfoDetailsModel);

        // ページング情報初期化（DetailsModel）
        memberInfoDetailsModel.setPageNumber(DEFAULT_ORDERHISTORY_PNUM);
        memberInfoDetailsModel.setLimit(DEFAULT_ORDERHISTORY_LIMIT);
        memberInfoDetailsModel.setOrderField(DEFAULT_ORDERHISTORY_ORDER_FIELD);
        memberInfoDetailsModel.setOrderAsc(DEFAULT_ORDERHISTORY_ORDER_ASC);

        // 注文履歴検索
        searchOrderHistory(memberInfoDetailsModel, model);

        // 遷移元チェック
        // fromが無い場合は、判定不能な為、「member」を設定
        if (memberInfoDetailsModel.getFrom() == null) {
            memberInfoDetailsModel.setFrom("member");
            // fromが「member」でない場合、検索条件を破棄する為、mdに空文字をセット
        } else if (!memberInfoDetailsModel.getFrom().equals("member")) {
            memberInfoDetailsModel.setMd("");
        }

        // 会員に紐付く問い合わせがあるかチェックし、結果を取得
        memberInfoDetailsModel.setInquiryFlag(
                        inquiryByMemberCheckLogic.execute(memberInfoDetailsModel.getMemberInfoSeq()));
        // 2023-renew No22 from here
        getUploadFiles(memberInfoDetailsModel);
        // 2023-renew No22 to here
        return "memberinfo/details";
    }

    /**
     * リダイレクトする
     *
     * @param memberInfoSeq 会員SEQ
     * @return string
     */
    @GetMapping(value = "/redirect-login-proxy")
    public String doRedirectToLoginProxy(@RequestParam(required = true) String memberInfoSeq) {

        String loginProxyUrl =
                        environment.getProperty("proxy.login.url") + "?memberInfoSeq=" + memberInfoSeq + "&from=member";

        return "redirect:" + loginProxyUrl;
    }

    /**
     * 受注履歴の表示切替
     *
     * @param memberInfoDetailsModel 会員検索モデル
     * @param model                  Model
     */
    @PostMapping(value = "/details", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_INDEX_PAGE)
    public String doDisplayChangeForOrderHistory(MemberInfoDetailsModel memberInfoDetailsModel, Model model) {
        searchOrderHistory(memberInfoDetailsModel, model);
        return "memberinfo/details";
    }

    /**
     * 全件CSV出力
     */
    @PreAuthorize("hasAnyAuthority('MEMBER:8')")
    @PostMapping(value = "/", params = "doAllDownload")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_INDEX_PAGE)
    @HEHandler(exception = FileDownloadException.class, returnView = MEMBER_INFO_INDEX_PAGE)
    public void doAllDownload(@Validated(AllDownloadGroup.class) MemberInfoModel memberInfoModel,
                              BindingResult error,
                              HttpServletResponse response,
                              Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        // 検索条件作成
        MemberInfoSearchForDaoConditionDto memberInfoConditionDto =
                        memberInfoHelper.toConditionDtoForSearch(memberInfoModel);

        if (HTypeMemberInfoOutData.MEMBER_CSV.getValue().equals(memberInfoModel.getMemberOutData())) {
            // 会員CSVのダウンロード開始
            try {
                memberInfoAllCsvDownloadService.execute(memberInfoConditionDto, response);
            } catch (IOException e) {
                LOGGER.error("例外処理が発生しました", e);
                throw new FileDownloadException(model);
            }
        }
    }

    /**
     * CSVダウンロード（一覧上部ボタン）
     */
    @PreAuthorize("hasAnyAuthority('MEMBER:8')")
    @PostMapping(value = "/", params = "doDownload1")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_INDEX_PAGE)
    @HEHandler(exception = FileDownloadException.class, returnView = MEMBER_INFO_INDEX_PAGE)
    public void doDownload1(@Validated(DownloadTopGroup.class) MemberInfoModel memberInfoModel,
                            BindingResult error,
                            HttpServletResponse response,
                            Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }
        // CSV選択ダウンロード
        try {
            downloadCheckedCsv(memberInfoModel, memberInfoModel.getCheckedMemberOutData1(), response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }
    }

    /**
     * CSVダウンロード（一覧下部ボタン）
     */
    @PreAuthorize("hasAnyAuthority('MEMBER:8')")
    @PostMapping(value = "/", params = "doDownload2")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_INDEX_PAGE)
    @HEHandler(exception = FileDownloadException.class, returnView = MEMBER_INFO_INDEX_PAGE)
    public void doDownload2(@Validated(DownloadBottomGroup.class) MemberInfoModel memberInfoModel,
                            BindingResult error,
                            HttpServletResponse response,
                            Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }
        // CSV選択ダウンロード
        try {
            downloadCheckedCsv(memberInfoModel, memberInfoModel.getCheckedMemberOutData2(), response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }
    }

    // 2023-renew No25 from here

    /**
     * 顧客番号・PW通知メール再送信する仕組み
     *
     * @param memberInfoDetailsModel メンバー情報 詳細モデル
     * @param error エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     */
    @PreAuthorize("hasAnyAuthority('MEMBER:8')")
    @PostMapping(value = "/details", params = "doResendCustomerNoAndPasswordEmail")
    public String doResendCustomerNoAndPasswordEmail(MemberInfoDetailsModel memberInfoDetailsModel,
                                                     BindingResult error,
                                                     RedirectAttributes redirectAttributes,
                                                     Model model) {

        MemberInfoDetailsDto memberInfoDetailsDto;

        // 会員詳細取得サービス実行
        memberInfoDetailsDto = memberInfoDetailsGetService.execute(memberInfoDetailsModel.getMemberInfoSeq());
        if (memberInfoDetailsDto == null) {
            LOGGER.error("対象会員を取得できませんでした");
            return "redirect:/error";
        }

        // ページに反映
        memberInfoHelper.toModelDetailsForLoad(memberInfoDetailsDto, memberInfoDetailsModel);

        // 会員の状態とログイン可否
        if (!memberInfoDetailsModel.isAdmission() || memberInfoDetailsModel.isAccountLock()) {
            LOGGER.error("ログイン不可会員に対して顧客番号・PW通知メール再送信処理を実行した場合エラー");
            throwMessage(RESEND_CUSTOMER_NO_AND_PASSWORD_EMAIL_ERROR_MESSAGE_CODE);
        }

        MemberInfoEntity memberInfoEntity = memberInfoDetailsModel.getMemberInfoEntity();
        // 新しいパスワードを作成する
        memberInfoHelper.resetPassword(memberInfoEntity);
        try {
            // 会員情報更新サービス実行。更新失敗したらここ例外投げられますよ。
            memberInfoUpdateService.execute(memberInfoEntity);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(RESEND_CUSTOMER_NO_AND_PASSWORD_EMAIL_DB_SERVICE_ERROR_MESSAGE_CODE);
        }

        try {
            // 顧客番号・パスワード通知メール送信フラグがONならばメール送信
            memberInfoProcessCompleteMailSendService.execute(
                            memberInfoEntity.getMemberInfoSeq(), HTypeMailTemplateType.RESEND_CUSTOMERNO_PASSWORD);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(RESEND_CUSTOMER_NO_AND_PASSWORD_EMAIL_SERVICE_ERROR_MESSAGE_CODE);
        }

        return "redirect:/memberinfo/details?memberInfoSeq=" + memberInfoEntity.getMemberInfoSeq() + "&from=member";
    }
    // 2023-renew No25 to here

    // 2023-renew No85-2 from here

    /**
     * 詳細画面：会員退会
     *
     * @param memberInfoDetailsModel 会員詳細モデル
     * @param error                  エラー
     * @param redirectAttributes     リダイレクトアトリビュート
     * @param model                  モデル
     * @return 詳細画面
     */
    @PreAuthorize("hasAnyAuthority('MEMBER:8')")
    @PostMapping(value = "/details", params = "doCancelMember")
    @HEHandler(exception = AppLevelListException.class, returnView = "memberinfo/details")
    public String doCancelMember(MemberInfoDetailsModel memberInfoDetailsModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        MemberInfoDetailsDto memberInfoDetailsDto = null;
        try {
            // 会員詳細取得サービス実行
            memberInfoDetailsDto = memberInfoDetailsGetService.execute(memberInfoDetailsModel.getMemberInfoSeq());
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(CANCEL_MEMBER_SERVICE_ERROR_MESSAGE_CODE);
        }

        if (memberInfoDetailsDto == null || checkCancelTargetMember(memberInfoDetailsDto.getMemberInfoEntity())) {
            throwMessage(CANCEL_MEMBER_CHECK_DATA_MESSAGE_CODE);
        } else {
            memberInfoHelper.updateMemberInfoEntity(memberInfoDetailsDto.getMemberInfoEntity());
        }

        try {
            // 会員詳細更新サービス実行
            memberInfoDetailsUpdateService.execute(memberInfoDetailsDto);
        } catch (Exception e) {
            LOGGER.error("否認処理によるDB更新に失敗した場合エラー", e);
            throwMessage(CANCEL_MEMBER_SERVICE_ERROR_MESSAGE_CODE);
        }
        return "redirect:/memberinfo/details";
    }

    // 2023-renew general-mail from here

    /**
     * メールテンプレート選択画面に遷移
     *
     * @param memberInfoDetailsModel
     * @param redirectAttributes
     * @param model
     * @return 遷移先
     */
    @PostMapping(value = "/details", params = "doMailTemplate")
    @HEHandler(exception = AppLevelListException.class, returnView = "memberinfo/details")
    public String doMailTemplate(@Validated MemberInfoDetailsModel memberInfoDetailsModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        // エラーチェック
        if (error.hasErrors()) {
            return "memberinfo/details";
        }

        try {
            // 会員詳細取得サービス実行
            MemberInfoDetailsDto memberInfoDetailsDto =
                            memberInfoDetailsGetService.execute(memberInfoDetailsModel.getMemberInfoSeq());
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(CANCEL_MEMBER_SERVICE_ERROR_MESSAGE_CODE);
        }

        // メール情報を作成
        MailSendDto mailSendDto = ApplicationContextUtility.getBean(MailSendDto.class);
        mailSendDto.setApplication(MailSendDto.MEMBER);
        mailSendDto.setDisplayName("会員詳細");
        mailSendDto.setMailDtoList(new ArrayList<>());
        mailSendDto.setAvailableTemplateTypeList(new ArrayList<>());

        // 通常注文：テンプレートタイプを設定 メールアドレス変更完了/顧客番号・パスワード通知/基幹情報変更完了/会員向け汎用
        List<HTypeMailTemplateType> availableTemplateTypeList = new ArrayList<>();

        availableTemplateTypeList.add(HTypeMailTemplateType.EMAIL_MODIFICATION_COMPLETE);
        availableTemplateTypeList.add(HTypeMailTemplateType.SEND_CUSTOMERNO_PASSWORD);
        availableTemplateTypeList.add(HTypeMailTemplateType.CORE_MEMBERINFO_MODIFICATION_COMPLETE);
        availableTemplateTypeList.add(HTypeMailTemplateType.GENERAL_MEMBER);
        mailSendDto.setAvailableTemplateTypeList(availableTemplateTypeList);

        // 会員情報をメール情報に追加
        MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);
        mailDto.setToList(Collections.singletonList(memberInfoDetailsModel.getMemberInfoId()));

        // メール用値マップの作成
        Transformer transformer = ApplicationContextUtility.getBean(MemberInfoTransformHelper.class);
        Map<String, Object> mailContentsMap = new HashMap<>();
        mailContentsMap.put("mailContentsMap", transformer.toValueMap(memberInfoDetailsModel.getMemberInfoEntity()));

        mailDto.setMailContentsMap(mailContentsMap);
        mailSendDto.getMailDtoList().add(mailDto);

        // 画面情報からMailSendDtoに変換してページに設定。
        redirectAttributes.addFlashAttribute("mailSendDto", mailSendDto);

        return "redirect:/mailtemplate/send/select";
    }
    // 2023-renew general-mail to here

    /**
     * メンバーを確認してキャンセルする
     *
     * @param memberInfoEntity 会員クラス
     * @return TRUE:エラー
     */
    protected boolean checkCancelTargetMember(MemberInfoEntity memberInfoEntity) {
        if (ObjectUtils.isNotEmpty(memberInfoEntity) && memberInfoEntity.getMemberInfoStatus()
                                                                        .equals(HTypeMemberInfoStatus.REMOVE)
            && memberInfoEntity.getApproveStatus().equals(HTypeApproveStatus.OFF)
            && memberInfoEntity.getOnlineRegistFlag().equals(HTypeOnlineRegistFlag.ON)) {
            return false;
        }
        return true;
    }
    // 2023-renew No85-2 to here

    /**
     * 検索処理
     *
     * @param memberInfoModel 会員検索モデル
     */
    protected void search(MemberInfoModel memberInfoModel, Model model) {
        // 検索条件作成
        MemberInfoSearchForDaoConditionDto memberInfoConditionDto =
                        memberInfoHelper.toConditionDtoForSearch(memberInfoModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(memberInfoConditionDto, memberInfoModel.getPageNumber(),
                                     memberInfoModel.getLimit(), memberInfoModel.getOrderField(),
                                     memberInfoModel.isOrderAsc()
                                    );

        // 取得
        List<MemberInfoEntity> memberInfoEntityList = memberInfoResultSearchService.execute(memberInfoConditionDto);
        // ページにセット
        memberInfoHelper.toPageForResultList(memberInfoEntityList, memberInfoModel, memberInfoConditionDto);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(memberInfoConditionDto, memberInfoModel);

        // 件数セット
        memberInfoModel.setTotalCount(memberInfoConditionDto.getTotalCount());
    }

    /**
     * 受注履歴　検索処理
     *
     * @param memberInfoDetailsModel 会員詳細モデル
     * @param model                  モデル
     */
    protected void searchOrderHistory(MemberInfoDetailsModel memberInfoDetailsModel, Model model) {
        // 注文履歴情報検索Dto取得
        OrderSearchConditionDto orderConditionDto = createOrderConditionDto(memberInfoDetailsModel.getMemberInfoSeq());
        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(orderConditionDto, memberInfoDetailsModel.getPageNumber(),
                                     memberInfoDetailsModel.getLimit(), memberInfoDetailsModel.getOrderField(),
                                     memberInfoDetailsModel.isOrderAsc()
                                    );

        // 注文履歴情報リスト取得サービス実行
        List<OrderSearchOrderResultDto> orderHistoryDtoList = orderSearchOrderListGetService.execute(orderConditionDto);

        // ページに反映
        memberInfoHelper.toModelForOrderHistory(orderHistoryDtoList, memberInfoDetailsModel, orderConditionDto);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(orderConditionDto, memberInfoDetailsModel);
        // 受注履歴総件数
        memberInfoDetailsModel.setTotalCount(orderConditionDto.getTotalCount());
    }

    /**
     * 選択された会員情報の対象CSVダウンロード
     *
     * @param memberInfoModel 会員検索モデル
     * @param outDataType     outDataType
     * @return responseEntity
     */
    protected void downloadCheckedCsv(MemberInfoModel memberInfoModel, String outDataType, HttpServletResponse response)
                    throws IOException {

        // 検索結果チェック
        resultListCheck(memberInfoModel);

        /*
         * ブラウザバックの対応としてmembmerInfoSeqをhiddenで持ち
         * resultItemsのセッションのmemberInfoSeqのみをサブミット時に
         * 更新するようにしている為、memberInfoSeqのみは、画面と同期とる それを利用してCSVの出力を行う
         */

        // 会員SEQリスト作成
        List<Integer> memberInfoSeqList = memberInfoHelper.toMemberInfoSeqList(memberInfoModel);

        // チェック選択なし
        if (memberInfoSeqList.isEmpty()) {
            throwMessage(MemberInfoModel.MSGCD_NOT_SELECTED_DATE);
        }

        if (HTypeMemberInfoOutData.MEMBER_CSV.getValue().equals(outDataType)) {
            // 会員CSVのダウンロード開始
            memberInfoCsvDownloadService.execute(memberInfoSeqList, response);
        }
    }
    // PDR Migrate Customization from here

    /**
     * 会員検索結果リストが空でないことをチェックする<br/>
     * (ブラウザバック後の選択出力などでの不具合防止のため)<br/>
     * 今改修で会員IDは空になる可能性があるため、リストの空チェックは承認状態を確認する<br/>
     *
     * @param memberInfoModel 会員検索モデル
     */
    protected void resultListCheck(MemberInfoModel memberInfoModel) {
        if (!memberInfoModel.isResult()) {
            return;
        }
        // PDR Migrate Customization from here
        if (StringUtil.isEmpty(memberInfoModel.getResultItems().get(0).getResultApproveStatus())) {
            memberInfoModel.setResultItems(null);
            this.throwMessage(MemberInfoModel.MSGCD_ILLEGAL_OPERATION);
        }
        // PDR Migrate Customization to here
    }
    // PDR Migrate Customization to here

    /**
     * 受注サマリDao用検索条件Dtoを作成する
     *
     * @param memberInfoSeq 会員SEQ
     * @return 受注サマリDao用検索条件Dto
     */
    protected OrderSearchConditionDto createOrderConditionDto(Integer memberInfoSeq) {
        OrderSearchConditionDto conditionDto = ApplicationContextUtility.getBean(OrderSearchConditionDto.class);
        conditionDto.setMemberInfoSeq(memberInfoSeq);

        return conditionDto;
    }

    /**
     * プルダウンアイテム情報を取得
     *
     * @param memberInfoModel 会員検索モデル
     */
    protected void initDropDownValue(MemberInfoModel memberInfoModel) {

        // プルダウンアイテム情報を取得
        memberInfoModel.setMemberInfoPrefectureItems(EnumTypeUtil.getEnumMap(HTypePrefectureType.class));
        memberInfoModel.setPeriodTypeItems(EnumTypeUtil.getEnumMap(HTypeMemberInfoStatus.class));
        memberInfoModel.setMemberInfoStatusItems(EnumTypeUtil.getEnumMap(HTypeMemberInfoStatus.class));
        memberInfoModel.setMemberInfoSexItems(EnumTypeUtil.getEnumMap(HTypeSexUnnecessaryAnswer.class));
        memberInfoModel.setLastLoginDeviceTypeItems(
                        EnumTypeUtil.getEnumMapWithIgnore(HTypeDeviceType.class, HTypeDeviceType.MB.getValue()));
        memberInfoModel.setMemberOutDataItems(EnumTypeUtil.getEnumMap(HTypeMemberInfoOutData.class));
        memberInfoModel.setCheckedMemberOutData1Items(EnumTypeUtil.getEnumMap(HTypeMemberInfoOutData.class));
        memberInfoModel.setCheckedMemberOutData2Items(EnumTypeUtil.getEnumMap(HTypeMemberInfoOutData.class));
        // PDR Migrate Customization from here
        memberInfoModel.setSendDirectMailFlagItems(EnumTypeUtil.getEnumMap(HTypeSendDirectMailFlag.class));
        memberInfoModel.setSendFaxPermitFlagItems(EnumTypeUtil.getEnumMap(HTypeSendFaxPermitFlag.class));
        memberInfoModel.setSendMailPermitFlagItems(EnumTypeUtil.getEnumMap(HTypeSendMailPermitFlag.class));
        memberInfoModel.setOnlineRegistFlagItems(EnumTypeUtil.getEnumMap(HTypeOnlineRegistFlag.class));
        memberInfoModel.setApproveStatusItems(EnumTypeUtil.getEnumMap(HTypeApproveStatus.class));
        memberInfoModel.setBusinessTypeItems(EnumTypeUtil.getEnumMap(HTypeBusinessType.class));
        // PDR Migrate Customization to here

    }

    // 2023-renew No22 from here

    /**
     * アップロードファイルを取得する
     *
     * @param memberInfoDetailsModel 会員詳細モデル
     */
    private void getUploadFiles(MemberInfoDetailsModel memberInfoDetailsModel) {

        Integer memberInfoSeq = memberInfoDetailsModel.getMemberInfoEntity().getMemberInfoSeq();
        // アップロードされたファイルをデータベースから取得する
        List<MemberImageDto> memberImageDtoList = this.memberInfoListImageService.execute(memberInfoSeq);
        List<UploadFileModel> uploadFileModelList = memberInfoHelper.toUploadFileModel(memberImageDtoList,
                                                                                       PropertiesUtil.getSystemPropertiesValue(
                                                                                                       CONF_DOCUMENT_URI_NAME)
                                                                                       + "/", memberInfoSeq
                                                                                      );
        memberInfoDetailsModel.setUploadFileModelList(uploadFileModelList);

    }

    /**
     * ファイルの更新日を取得する
     *
     * @param filePath: ファイルパス
     * @return: ファイルの日付を長い形式で更新します
     */
    private Long getUpdateDateOfFile(String filePath) {
        try {
            Path path = FileSystems.getDefault().getPath(filePath);
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);

            // Get the last modified date
            long lastModifiedTime = attributes.lastModifiedTime().toMillis();

            return lastModifiedTime;
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            return null;
        }
    }
    // 2023-renew No22 to here

}
