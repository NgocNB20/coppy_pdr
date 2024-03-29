/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.pass;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoPasswordUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ResultCountResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.pass.validation.group.PassWordUpdateGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.service.common.impl.HmFrontUserDetailsServiceImpl;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * パスワード変更 Controller
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@SessionAttributes(value = "memberPassModel")
@RequestMapping("/member/pass/")
@Controller
public class MemberPassController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberPassController.class);

    /**
     * 認証サービス
     */
    private final HmFrontUserDetailsServiceImpl userDetailsService;
    /**
     * 商品API
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * メンバーパスHelper
     */
    private final MemberPassHelper memberPassHelper;

    /**
     * コンストラクタ
     *
     * @param userDetailsService
     * @param memberInfoApi
     * @param memberPassHelper
     */
    public MemberPassController(HmFrontUserDetailsServiceImpl userDetailsService,
                                MemberInfoApi memberInfoApi,
                                MemberPassHelper memberPassHelper) {
        this.userDetailsService = userDetailsService;
        this.memberInfoApi = memberInfoApi;
        this.memberPassHelper = memberPassHelper;
    }

    /**
     * パスワード変更入力画面：初期処理
     *
     * @param memberPassModel
     * @param model
     * @return パスワードリセット入力画面
     */
    // PDR Migrate Customization from here
    @GetMapping(value = {"/", "index.html"})
    // PDR Migrate Customization to here
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/error.html")
    public String doLoadIndex(MemberPassModel memberPassModel, HttpServletRequest request, Model model) {

        MemberInfoEntity memberInfoEntity = null;
        MemberInfoEntityResponse memberInfoEntityResponse = null;

        // 会員Seqを取得する
        HttpSession session = request.getSession();
        Integer memberInfoSeq;
        if (session.getAttribute("memberInfoSeq") != null) {
            // 会員パスワードの変更必要がある場合
            memberInfoSeq = (Integer) session.getAttribute("memberInfoSeq");
            if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            }
        } else {
            memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();
        }

        // 会員Seqをチェックする
        if (memberInfoSeq == null) {
            return "redirect:/login/member.html";
        }

        // 会員情報取得サービス実行
        try {
            memberInfoEntityResponse = memberInfoApi.getByMemberInfoSeq(memberInfoSeq);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        memberInfoEntity = memberPassHelper.toMemberEntity(memberInfoEntityResponse);
        // ページに取得した会員情報を設定
        memberPassModel.setMemberInfoEntity(memberInfoEntity);

        return "member/pass/index";

    }

    /**
     * パスワード変更処理<br/>
     *
     * @param memberPassModel
     * @param error
     * @param request
     * @param model
     * @return パスワード変更完了画面
     */
    @PostMapping(value = {"/", "index.html"}, params = "doOncePassWordUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/pass/index")
    public String doOncePassWordUpdate(@Validated(PassWordUpdateGroup.class) MemberPassModel memberPassModel,
                                       BindingResult error,
                                       HttpServletRequest request,
                                       Model model) {

        if (error.hasErrors()) {
            return "member/pass/index";
        }

        boolean isPasswordNeedChange = memberPassModel.getMemberInfoEntity().isPasswordNeedChange();

        MemberInfoPasswordUpdateRequest memberInfoPasswordUpdateRequest = new MemberInfoPasswordUpdateRequest();
        memberInfoPasswordUpdateRequest.setPassword(memberPassModel.getMemberInfoPassWord());
        memberInfoPasswordUpdateRequest.setNewPassword(memberPassModel.getMemberInfoNewPassWord());
        MemberInfoEntityRequest memberInfoEntityRequest =
                        memberPassHelper.toMemberInfoEntityRequest(memberPassModel.getMemberInfoEntity());
        memberInfoPasswordUpdateRequest.setMemberInfoEntity(memberInfoEntityRequest);
        int ret = 0;
        ResultCountResponse resultCountResponse = null;
        try {
            resultCountResponse = memberInfoApi.updatePassword(memberInfoPasswordUpdateRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }
        if (resultCountResponse != null && resultCountResponse.getResultCount() != null) {
            ret = resultCountResponse.getResultCount();
        }

        if (ret > 0 && isPasswordNeedChange) {
            // パスワード変更要求フラグ更新
            MemberInfoEntity memberInfoEntity = memberPassModel.getMemberInfoEntity();
            memberInfoEntity.setPasswordNeedChangeFlag(HTypePasswordNeedChangeFlag.OFF);

            userDetailsService.updateUserInfo(memberInfoEntity.getMemberInfoId());

            // 会員Seqをセッションに削除する
            HttpSession session = request.getSession();
            session.removeAttribute("memberInfoSeq");
        }

        // パスワード変更完了画面へ遷移
        return "redirect:/member/pass/complete.html";
    }

    /**
     * パスワード変更完了画面：画面表示処理
     *
     * @param memberPassModel
     * @param sessionStatus
     * @param model
     * @return パスワード変更完了画面
     */
    @GetMapping(value = {"/complete", "/complete.html"})
    public String doLoadConfirm(MemberPassModel memberPassModel, SessionStatus sessionStatus, Model model) {
        sessionStatus.setComplete();
        return "member/pass/complete";

    }
}
