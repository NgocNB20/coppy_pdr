/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.mail;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoMailUpdateSendMailRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * メールアドレス変更 Controller
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@SessionAttributes(value = "memberMailModel")
@RequestMapping("/member/mail")
@Controller
public class MemberMailControler extends AbstractController {

    /**
     * 確認メールDBユニーク<br/>
     */
    protected static final String MSGCD_DB_UNIQUE_CONFIRMMAIL_PASSWORD_FAIL = "AMX000801";

    /**
     * メールアドレス不正<br/>
     */
    protected static final String MSGCD_MAIL_ADDRESS_FAIL = "AMX000802";

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberMailControler.class);

    /**
     * メールアドレス変更 Helper<br/>
     */
    private MemberMailHelper memberMailHelper;

    /**
     * 会員API<br/>
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * コンストラクタ
     *
     * @param memberMailHelper メールアドレス変更
     * @param memberInfoApi    会員API
     */
    @Autowired
    public MemberMailControler(MemberMailHelper memberMailHelper, MemberInfoApi memberInfoApi) {
        this.memberMailHelper = memberMailHelper;
        this.memberInfoApi = memberInfoApi;
    }

    /**
     * 変更画面：初期処理
     *
     * @param memberMailModel
     * @param model
     * @return 変更画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/", "/index.html"})
    // PDR Migrate Customization to here
    @HEHandler(exception = AppLevelListException.class, returnView = "member/mail/index")
    protected String doLoadIndex(MemberMailModel memberMailModel, Model model) {

        // 初期化処理
        clearModel(MemberMailModel.class, memberMailModel, model);

        // 会員情報取得
        Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();

        MemberInfoEntityResponse memberInfoEntityResponse = null;
        try {
            memberInfoEntityResponse = memberInfoApi.getByMemberInfoSeq(memberInfoSeq);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        MemberInfoEntity memberInfoEntity = memberMailHelper.toMemberInfoEntity(memberInfoEntityResponse);

        // メールアドレスをセット
        memberMailHelper.toPageForLoad(memberInfoEntity, memberMailModel);

        return "member/mail/index";
    }

    /**
     * メールアドレス送信確認処理<br/>
     *
     * @param memberMailModel
     * @param error
     * @param model
     * @return メールアドレス送信完了画面
     */
    @PostMapping(value = {"/", "index.html"}, params = "doOnceMailAddressSendConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/mail/index")
    public String doOnceMailAddressSendConfirm(@Validated MemberMailModel memberMailModel,
                                               BindingResult error,
                                               RedirectAttributes redirectAttributes,
                                               Model model) {

        if (error.hasErrors()) {
            return "member/mail/index";
        }

        // メールアドレスがない場合は、画面再表示メッセージ
        if (StringUtils.isEmpty(memberMailModel.getMemberInfoMail())) {
            throwMessage(MSGCD_MAIL_ADDRESS_FAIL);
        }

        Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();

        try {
            // 変更メールアドレス登録

            MemberInfoMailUpdateSendMailRequest req = new MemberInfoMailUpdateSendMailRequest();
            req.setMemberInfoSeq(memberInfoSeq);
            req.setMemberInfoNewMail(memberMailModel.getMemberInfoNewMail());
            try {
                memberInfoApi.updateMail(req);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }

        } catch (DuplicateKeyException dke) {
            // Exceptionログを出力しておく
            ApplicationLogUtility appLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            appLogUtility.writeExceptionLog(dke);

            // 万が一ユニークキー制約違反が発生した場合、再度の入力を促す
            addMessage(MSGCD_DB_UNIQUE_CONFIRMMAIL_PASSWORD_FAIL, redirectAttributes, model);
            return "member/mail/index";
        }

        // メールアドレス送信完了画面へ遷移
        return "redirect:/member/mail/complete.html";
    }

    /**
     * 入力画面：初期処理
     *
     * @param memberMailModel
     * @param model
     * @return 入力画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/complete", "/complete.html"})
    // PDR Migrate Customization to here
    protected String doLoadComplete(MemberMailModel memberMailModel, Model model) {

        return "member/mail/complete";
    }
}
