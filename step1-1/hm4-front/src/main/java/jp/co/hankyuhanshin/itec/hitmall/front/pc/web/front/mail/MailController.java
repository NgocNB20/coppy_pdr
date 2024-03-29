/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.mail;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoMailScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoUpdateMailGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.mail.MemberMailModel;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/**
 * メールアドレス変更 Controller
 * // PDR Migrate Customization from here
 * PDR#011 08_データ連携（顧客情報）
 *
 * <pre>
 * 会員メールアドレス変更アクション
 * <pre>
 * @author satoh
 * // PDR Migrate Customization to here
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@SessionAttributes(value = "mailModel")
@RequestMapping("/mail")
@Controller
public class MailController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MailController.class);

    /**
     * メールパスワード不正
     */
    protected static final String MSGCD_MAILPASSWORD_FAIL = "AMX001001";

    /**
     * メールアドレス変更 Helper
     */
    private final MailHelper mailHelper;

    /**
     * 会員Api
     */
    private final MemberInfoApi memberInfoApi;

    // PDR Migrate Customization for v4 from here
    //    /**
    //     * Persistent Token方式を利用する場合のトークンリポジトリ
    //     */
    //    private final PersistentTokenBasedRememberMeServices rememberMeTokenService;
    // PDR Migrate Customization for v4 to here

    @Autowired
    public MailController(MailHelper mailHelper, MemberInfoApi memberInfoApi
                          // PDR Migrate Customization for v4 from here
                          //            , PersistentTokenBasedRememberMeServices rememberMeTokenService
                          // PDR Migrate Customization for v4 to here
                         ) {
        this.mailHelper = mailHelper;
        this.memberInfoApi = memberInfoApi;
        // PDR Migrate Customization for v4 from here
        //        this.rememberMeTokenService = rememberMeTokenService;
        // PDR Migrate Customization for v4 to here
    }

    /**
     * 変更画面：初期処理
     *
     * @param mailModel          メールアドレス変更 Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @return 変更画面
     */
    @GetMapping(value = {"/", "/regist.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "mail/regist")
    protected String doLoadIndex(MailModel mailModel, RedirectAttributes redirectAttributes, Model model) {

        mailModel.setErrorUrl(true);
        // パラメータチェック
        if (StringUtils.isEmpty(mailModel.getMid())) {
            addMessage(MSGCD_MAILPASSWORD_FAIL, redirectAttributes, model);
            return "redirect:/error.html";
        }

        // 取得情報
        MemberInfoUpdateMailGetRequest memberInfoUpdateMailGetRequest = new MemberInfoUpdateMailGetRequest();
        memberInfoUpdateMailGetRequest.setMid(mailModel.getMid());

        MemberInfoEntityResponse memberInfoEntityResponse = null;
        try {
            memberInfoEntityResponse = memberInfoApi.getByUpdateMail(memberInfoUpdateMailGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        MemberInfoEntity memberInfoEntity = mailHelper.toMemberInfoEntity(memberInfoEntityResponse);

        // SEQから変更前の会員情報を取得

        MemberInfoEntityResponse preMemberInfoEntityResponse = null;
        try {
            preMemberInfoEntityResponse = memberInfoApi.getByMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        MemberInfoEntity preMemberInfoEntity = mailHelper.toMemberInfoEntity(preMemberInfoEntityResponse);

        // 変更前のメアドをセット
        mailModel.setPreMemberInfoMail(preMemberInfoEntity.getMemberInfoMail());

        // ページに反映
        mailHelper.toPageForLoad(memberInfoEntity, mailModel);

        // 有効なURLから遷移してきた。
        mailModel.setErrorUrl(false);

        return "mail/regist";

    }

    /**
     * メールアドレスの変更処理
     * <p>
     * // PDR Migrate Customization from here
     * <pre>
     * WEB-API連携 会員情報更新処理 追加
     * 変数等を今対応用に修正
     * <pre>
     * // PDR Migrate Customization to here
     *
     * @return 完了画面
     */
    //    PDR Migrate Customization from here
    @PostMapping(value = {"/", "/regist.html"}, params = "doOnceMailUpdate")
    //    PDR Migrate Customization to here
    @HEHandler(exception = AppLevelListException.class, returnView = "mail/regist")
    public String doOnceMailUpdate(@Validated MailModel mailModel,
                                   BindingResult error,
                                   RedirectAttributes redirectAttributes,
                                   Model model,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {

        //  PDR Migrate Customization from here
        // PDR Migrate Customization from here
        // メールアドレスチェック 削除
        // PDR Migrate Customization to here
        // PDR Migrate Customization from here

        // 変更会員情報
        MemberInfoEntity memberInfoEntity;
        try {
            memberInfoEntity = mailHelper.toMemberInfoEntityForMailUpdate(mailModel);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(MSGCD_MAILPASSWORD_FAIL, redirectAttributes, model);
            return "redirect:/error.html";
        }

        MemberInfoMailScreenUpdateRequest memberInfoMailScreenUpdateRequest =
                        mailHelper.toMemberInfoMailScreenUpdateRequest(memberInfoEntity, mailModel.getMid(),
                                                                       mailModel.getMemberInfoMail(),
                                                                       HTypeMailTemplateType.EMAIL_MODIFICATION_COMPLETE
                                                                      );

        try {
            memberInfoApi.updateMailScreen(memberInfoMailScreenUpdateRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // PDR Migrate Customization from here
        // メール送信完了画面に遷移
        return "redirect:/mail/complete.html";
        // PDR Migrate Customization to here
    }

    /**
     * 変更画面：初期処理
     *
     * @param memberMailModel メールアドレス変更 Model
     * @param sessionStatus   セッション
     * @param model           モデル
     * @return 変更画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/complete", "/complete.html"})
    // PDR Migrate Customization to here
    protected String doLoadComplete(MemberMailModel memberMailModel, SessionStatus sessionStatus, Model model) {

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "mail/complete";

    }

}
