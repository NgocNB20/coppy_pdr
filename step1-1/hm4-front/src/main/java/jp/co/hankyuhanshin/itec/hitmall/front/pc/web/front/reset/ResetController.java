/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.reset;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByMemberInfoIdOrCustumerNoGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PasswordResetSendMailRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.loginadvisability.LoginAdvisabilityEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * パスワードリセット Controller
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@SessionAttributes(value = "resetModel")
@RequestMapping("/reset/")
@Controller
public class ResetController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ResetController.class);

    /* message code */

    /**
     * 不正遷移
     */
    protected static final String MSGCD_DB_UNIQUE_CONFIRMMAIL_PASSWORD_FAIL = "APX000101";

    // 2023-renew No85-1 from here
    /**
     * 未登録エラー
     */
    private static final String MSGCD_EMAIL_NOT_EXIST = "PDR-0024-001-A-";

    /**
     * 退会エラー
     * ログイン不可エラー
     */
    private static final String MSGCD_EMAIL_NOTMATCH = "PDR-0024-002-A-";
    // 2023-renew No85-1 to here

    /**
     * 会員情報Api
     */
    private final MemberInfoApi memberInfoApi;

    // 2023-renew No85-1 from here
    /**
     * パスワードリセット Helper
     */
    private final ResetHelper resetHelper;
    // 2023-renew No85-1 to here

    /**
     * コンストラクタ
     *
     * @param memberInfoApi 会員情報Api
     * @param resetHelper パスワードリセット Helper
     */
    @Autowired
    public ResetController(MemberInfoApi memberInfoApi, ResetHelper resetHelper) {
        this.memberInfoApi = memberInfoApi;
        // 2023-renew No85-1 from here
        this.resetHelper = resetHelper;
        // 2023-renew No85-1 to here
    }
    // PDR Migrate Customization to here

    /**
     * パスワードリセット入力画面：初期処理
     *
     * @param resetModel パスワードリセット Model
     * @param model      モデル
     * @return パスワードリセット入力画面 string
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/", "/index.html"})
    // PDR Migrate Customization to here
    public String doLoadIndex(ResetModel resetModel, Model model) {
        // パスワードリセット入力画面のモデルをクリア
        clearModel(ResetModel.class, resetModel, model);

        // 2023-renew No85-1 from here
        String memberInfoMail = (String) model.getAttribute("memberInfoMail");
        String memberInfoTel = (String) model.getAttribute("memberInfoTel");
        if (StringUtils.isNotEmpty(memberInfoTel)) {
            resetModel.setMemberInfoTelOrCustomerNo(memberInfoTel);
        }
        if (StringUtils.isNotEmpty(memberInfoMail)) {
            resetModel.setMemberInfoMail(memberInfoMail);
        }
        // 2023-renew No85-1 to here

        return "reset/index";

    }

    /**
     * パスワードリセットメール送信処理
     *
     * @param resetModel         パスワードリセット Model
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return パスワードリセットメール送信完了画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doOncePassWordResetMailSend")
    @HEHandler(exception = AppLevelListException.class, returnView = "reset/index")
    public String doOncePassWordResetMailSend(@Validated ResetModel resetModel,
                                              BindingResult error,
                                              RedirectAttributes redirectAttributes,
                                              Model model) {

        if (error.hasErrors()) {
            return "reset/index";
        }

        // 2023-renew No85-1 from here
        MemberInfoByMemberInfoIdOrCustumerNoGetRequest memberInfoByMemberInfoIdOrCustumerNoGetRequest =
                        new MemberInfoByMemberInfoIdOrCustumerNoGetRequest();
        memberInfoByMemberInfoIdOrCustumerNoGetRequest.setMemberInfoIdOrCustomerNo(resetModel.getMemberInfoMail());
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

        // 会員に変換
        MemberInfoEntity memberInfoEntity = resetHelper.toMemberInfoEntity(memberInfoEntityResponse);

        //(1) 画面．メールアドレス == 会員TBL．会員メールアドレス
        //(2) 画面．電話番号 == 会員TBL．会員TEL
        //(3) 画面．お客様番号 == 会員TBL．顧客番号
        // 条件(1)(2)、または(1)(3)で会員情報が取得できない場合
        // エラーメッセージを出力し自画面遷移(未登録エラー)。
        if (ObjectUtils.isEmpty(memberInfoEntity)) {
            throwMessage(MSGCD_EMAIL_NOT_EXIST);
        }

        // エラーメッセージを出力し自画面遷移(未登録エラー)。
        if (memberInfoEntity.getCustomerNo() != null) {
            if (!resetModel.getMemberInfoTelOrCustomerNo().equals(memberInfoEntity.getCustomerNo().toString())
                && !resetModel.getMemberInfoTelOrCustomerNo().equals(memberInfoEntity.getMemberInfoTel())) {
                throwMessage(MSGCD_EMAIL_NOTMATCH);
            }
        } else if (!resetModel.getMemberInfoTelOrCustomerNo().equals(memberInfoEntity.getMemberInfoTel())) {
            throwMessage(MSGCD_EMAIL_NOTMATCH);
        }

        // (1)(2)、または(1)(3)を満たす会員が(4)を満たさない場合
        // エラーメッセージを出力し自画面遷移。
        if (!memberInfoEntity.getMemberInfoStatus().equals(HTypeMemberInfoStatus.ADMISSION)) {
            throwMessage(MSGCD_EMAIL_NOTMATCH);
        }

        // 登録チェック用にコピーを作成
        MemberInfoEntity memberInfoCheckEntity = CopyUtil.deepCopy(memberInfoEntity);

        LoginAdvisabilityGetRequest loginAdvisabilityGetRequest =
                        resetHelper.toLoginAdvisabilityGetRequest(memberInfoCheckEntity);

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
                        resetHelper.toLoginAdvisabilityEntity(loginAdvisabilityEntityResponse);
        if (loginAdvisabilityEntity.getLoginAdvisabilitytype() == null) {
            // ログイン可否判定取得に失敗した、エラーメッセージ表示
            throwMessage(MSGCD_EMAIL_NOTMATCH);
        }

        // ・(1)(2)、または(1)(3)を満たす会員が(5)を満たさない場合
        // エラーメッセージを出力し自画面遷移。
        if (loginAdvisabilityEntity.getLoginAdvisabilitytype().equals("0")) {
            throwMessage(MSGCD_EMAIL_NOTMATCH);
        }
        // 2023-renew No85-1 to here

        PasswordResetSendMailRequest passwordResetSendMailRequest = new PasswordResetSendMailRequest();
        passwordResetSendMailRequest.setMemberInfoMail(resetModel.getMemberInfoMail());
        // 2023-renew No85-1 from here
        passwordResetSendMailRequest.setMemberInfoTel(memberInfoEntity.getMemberInfoTel());
        // 2023-renew No85-1 to here
        // PDR Migrate Customization from here
        try {
            memberInfoApi.updatePasswordResetSendMail(passwordResetSendMailRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 完了画面へ遷移
        return "redirect:/reset/complete.html";
    }

    /**
     * パスワードリセットメール送信完了画面：画面表示処理
     *
     * @param resetModel    パスワードリセット Model
     * @param sessionStatus セクションステータス
     * @param model         モデル
     * @return パスワードリセットメール送信完了画面 string
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/complete", "/complete.html"})
    // PDR Migrate Customization to here
    protected String doLoadComplete(ResetModel resetModel, SessionStatus sessionStatus, Model model) {

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "reset/complete";

    }

}
