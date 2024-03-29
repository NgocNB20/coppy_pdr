/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.reset;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ConfirmMailEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ConfirmMailGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoPasswordResetUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.PasswordResetMemberInfoGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.confirmmail.ConfirmMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
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
@SessionAttributes(value = "resetPwregistModel")
@RequestMapping("/reset/")
@Controller
public class ResetPwregistController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ResetPwregistController.class);

    /**
     * 有効期限切れ・パラメータ不正で確認メール情報が取得できなかった場合のエラー
     */
    private static final String MSGCD_CONFIRMMAIL_GET_FAIL = "APX000102";

    /**
     * 不正遷移
     */
    protected static final String MSGCD_REFERER_FAIL = "APX000301";

    /**
     * パスワードリセット Helper
     */
    private final ResetPwregistHelper resetHelper;

    /**
     * 会員情報Api
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * コンストラクタ
     *
     * @param resetHelper   パスワードリセット Helper
     * @param memberInfoApi 会員情報Api
     */
    @Autowired
    public ResetPwregistController(ResetPwregistHelper resetHelper, MemberInfoApi memberInfoApi) {
        this.resetHelper = resetHelper;
        this.memberInfoApi = memberInfoApi;
    }

    /**
     * パスワード登録画面：初期処理
     *
     * @param resetPwregistModel パスワードリセット登録 Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return パスワード登録画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/pwregist", "/pwregist.html"})
    // PDR Migrate Customization to here
    @HEHandler(exception = AppLevelListException.class, returnView = "reset/pwregist")
    public String doLoadPwregist(ResetPwregistModel resetPwregistModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        resetPwregistModel.setErrorUrl(true);

        // パラメータチェック
        if (StringUtils.isEmpty(resetPwregistModel.getMid())) {
            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
            return "redirect:/error.html";
        }
        ConfirmMailGetRequest confirmMailGetRequest = new ConfirmMailGetRequest();
        confirmMailGetRequest.setPassword(resetPwregistModel.getMid());
        confirmMailGetRequest.setConfirmMailType(HTypeConfirmMailType.PASSWORD_REISSUE.getValue());
        try {
            ConfirmMailEntityResponse confirmMailEntityResponse = null;
            try {
                confirmMailEntityResponse = memberInfoApi.getByConfirmMail(confirmMailGetRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            // 確認メール情報取得
            ConfirmMailEntity confirmMailEntity = resetHelper.toConfirmMailEntity(confirmMailEntityResponse);

            // 確認メール情報の取得チェック
            if (confirmMailEntity == null) {
                // パラメータ不正、または有効期限切れなどで確認メール情報の取得ができなかった場合、メッセージを表示
                resetPwregistModel.setErrorUrl(true);
                throwMessage(MSGCD_CONFIRMMAIL_GET_FAIL);
            }

            PasswordResetMemberInfoGetRequest memberInfoUpdateMailGetRequest = new PasswordResetMemberInfoGetRequest();
            memberInfoUpdateMailGetRequest.setMid(resetPwregistModel.getMid());
            MemberInfoEntityResponse memberInfoEntityResponse = null;
            try {
                memberInfoEntityResponse = memberInfoApi.getByPasswordReset(memberInfoUpdateMailGetRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
            // パスワード再設定会員情報取得
            MemberInfoEntity memberInfoEntity = resetHelper.toMemberInfoEntity(memberInfoEntityResponse);
            // ページにセット
            resetHelper.toPageForLoad(memberInfoEntity, resetPwregistModel);

        } catch (AppLevelListException appLevelListException) {

            // メッセージを取得し、FlashAttributeにセット
            for (AppLevelException e : appLevelListException.getErrorList()) {
                addMessage(e.getMessageCode(), e.getArgs(), redirectAttributes, model);
            }
            return "redirect:/error.html";
        }

        // 有効なURLでしたとさ
        resetPwregistModel.setErrorUrl(false);

        return "reset/pwregist";

    }

    /**
     * パスワードリセット処理
     *
     * @param resetPwregistModel パスワードリセット登録 Model
     * @param error              エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return 完了画面へ
     */
    @PostMapping(value = {"/pwregist", "/pwregist.html"}, params = "doOncePassWordReset")
    @HEHandler(exception = AppLevelListException.class, returnView = "reset/pwregist")
    public String doOncePassWordReset(@Validated ResetPwregistModel resetPwregistModel,
                                      BindingResult error,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {

        // パラメータチェック
        if (checkInput(resetPwregistModel)) {
            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
            return "redirect:/error.html";
        }

        if (error.hasErrors()) {
            return "reset/pwregist";
        }
        MemberInfoEntityRequest memberInfoEntityRequest =
                        resetHelper.toMemberInfoEntityRequest(resetPwregistModel.getMemberInfoEntity());

        MemberInfoPasswordResetUpdateRequest memberInfoPasswordResetUpdateRequest =
                        new MemberInfoPasswordResetUpdateRequest();

        memberInfoPasswordResetUpdateRequest.setMid(resetPwregistModel.getMid());
        memberInfoPasswordResetUpdateRequest.setMemberInfoEntity(memberInfoEntityRequest);
        memberInfoPasswordResetUpdateRequest.setMemberInfoNewPassWord(resetPwregistModel.getMemberInfoNewPassWord());

        try {
            memberInfoApi.updateMemberInfoPassword(memberInfoPasswordResetUpdateRequest);

        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 完了画面へ
        return "redirect:/reset/pwcomplete.html";
    }

    /**
     * 画面不正チェック
     * 必要なデータの有無をチェック
     *
     * @return true=エラー、false=エラーなし
     */
    protected boolean checkInput(ResetPwregistModel resetPwregistModel) {
        if (resetPwregistModel.getMemberInfoEntity() == null) {
            return true;
        }
        if (resetPwregistModel.getMemberInfoEntity().getMemberInfoSeq() == null) {
            return true;
        }
        if (StringUtils.isEmpty(resetPwregistModel.getMemberInfoEntity().getMemberInfoPassword())) {
            return true;
        }
        return false;
    }

    /**
     * パスワード登録完了画面：画面表示処理
     *
     * @param resetPwregistModel パスワードリセット登録 Model
     * @param sessionStatus      セクションステータス
     * @param model              モデル
     * @return パスワード登録完了画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/pwcomplete", "/pwcomplete.html"})
    // PDR Migrate Customization to here
    protected String doLoadPwcomplete(ResetPwregistModel resetPwregistModel, SessionStatus sessionStatus, Model model) {

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "reset/pwcomplete";
    }

}
