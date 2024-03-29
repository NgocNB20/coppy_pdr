/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2023 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.card;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardBrandGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardBrandGetResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginFailureWithLockTimeUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.SendMailForLockAccountByRegistCreditErrorRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeExpirationDateMonth;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.card.validation.MemberCardValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.card.validation.group.MemberCardGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm.MemberConfirmHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm.MemberRegistedCardItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.PaymentController;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * カード情報画面 Controller
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@RequestMapping("/member/card")
@Controller
@SessionAttributes({"memberCardModel"})
// 2023-renew No60 from here
public class MemberCardController extends AbstractController {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberCardController.class);

    /** エラーメッセージ：入力チェックエラー（設計外だが入力エリアが画面下部にあるので、チェックエラーに気づきにくいため当メッセージも追加出力） */
    private static final String MSGCD_INPUT_ERROR = "LMC001038E";

    /** エラーメッセージ：保持カード数が上限を超えた場合 */
    private static final String MSGCD_CREDIT_MAX_REGISTED = "PDR-2023RENEW-60-002-";

    /** エラーメッセージ：カード登録の失敗回数が上限を超えた場合 */
    private static final String MSGCD_LOCK_ACCOUNT_MEMBER = "PDR-2023RENEW-60-003-";

    /** 通知メッセージ：登録完了（設計外だがデザインに存在するので追加） */
    private static final String MSGCD_REGIST_SUCCESS = "PDR-2023RENEW-60-004-";

    /** 通知メッセージ：削除完了（設計外だがデザインに存在するので追加） */
    private static final String MSGCD_DELETE_SUCCESS = "PDR-2023RENEW-60-005-";

    /**
     * カード情報画面 Validator
     */
    private final MemberCardValidator memberCardValidator;

    /**
     * カード情報画面 Helper
     */
    private final MemberCardHelper memberCardHelper;

    /**
     * 会員情報画面 Helper
     */
    private final MemberConfirmHelper memberConfirmHelper;

    /**
     * 共通情報ユーティリティ
     */
    private final CommonInfoUtility commonInfoUtility;

    /**
     * 通信ユーティリティ
     */
    private final ComTransactionUtility comTransactionUtility;

    /**
     * 受注業務ユーティリティ
     */
    private final OrderUtility orderUtility;

    /**
     * 日付関連ユーティリティ
     */
    private final DateUtility dateUtility;

    /**
     * 会員API
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * コンストラクタ
     */
    @Autowired
    public MemberCardController(MemberCardValidator memberCardValidator,
                                MemberCardHelper memberCardHelper,
                                MemberConfirmHelper memberConfirmHelper,
                                CommonInfoUtility commonInfoUtility,
                                ComTransactionUtility comTransactionUtility,
                                OrderUtility orderUtility,
                                DateUtility dateUtility,
                                MemberInfoApi memberInfoApi) {
        this.memberCardValidator = memberCardValidator;
        this.memberCardHelper = memberCardHelper;
        this.memberConfirmHelper = memberConfirmHelper;
        this.commonInfoUtility = commonInfoUtility;
        this.comTransactionUtility = comTransactionUtility;
        this.orderUtility = orderUtility;
        this.dateUtility = dateUtility;
        this.memberInfoApi = memberInfoApi;
    }

    @InitBinder
    public void initBinder(WebDataBinder error) {
        // create validate
        error.addValidators(memberCardValidator);
    }

    /**
     * カード情報画面：初期処理
     *
     * @param memberCardModel カード情報画面 Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param request リクエスト
     * @param model モデル
     * @return 遷移先
     */
    @GetMapping(value = {"/", "/index.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "member/card/index.html")
    protected String doLoadIndex(MemberCardModel memberCardModel,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request,
                                 Model model) {
        // モデル初期化
        clearModel(MemberCardModel.class, memberCardModel, model);

        // カードブランドリスト取得
        CardBrandGetRequest cardBrandGetRequest = new CardBrandGetRequest();
        cardBrandGetRequest.setFrontDisplayFlag(true);
        CardBrandGetResponse cardBrandGetResponse = null;
        try {
            cardBrandGetResponse = memberInfoApi.getCardBrandList(cardBrandGetRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // クレジットカード会社リストを作成
        memberCardModel.setCardBrandItems(orderUtility.createCardBrandItemList(
                        memberCardHelper.toCardBrandEntityList(cardBrandGetResponse)));
        // 有効期限（月）のプルダウン作成
        memberCardModel.setExpirationDateMonthItems(EnumTypeUtil.getEnumMap(HTypeExpirationDateMonth.class));
        // 有効期限（年）のプルダウン作成
        memberCardModel.setExpirationDateYearItems(orderUtility.createExpirationDateYearItems());

        // トークン決済用項目
        memberCardModel.setMerchantId(PropertiesUtil.getSystemPropertiesValue("paygent.merchant.id"));
        memberCardModel.setPaygentTokenKey(PropertiesUtil.getSystemPropertiesValue("paygent.token.key"));

        // サブシステム側との連携のため
        // クレジットカード情報保持種別に関わらず
        // ペイジェントを参照し、該当カード情報が存在するか確認
        ComResultDto comResultDto = null;
        try {
            // カード情報照会
            CardInfoRequest cardInfoRequest = new CardInfoRequest();
            cardInfoRequest.setSessionId(getCommonInfo().getCommonInfoBase().getSessionId());
            cardInfoRequest.setAccessUid(getCommonInfo().getCommonInfoBase().getAccessUid());
            CardInfoResponse cardInfoResponse = null;
            try {
                cardInfoResponse = memberInfoApi.getCardInfoByMemberInfoSeq(
                                getCommonInfo().getCommonInfoUser().getMemberInfoSeq(), cardInfoRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }

            comResultDto = memberConfirmHelper.toComResultDto(cardInfoResponse);
        } catch (Throwable e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(ComTransactionUtility.MSGCD_CREDIT_CARD_INFO_GET_ERROR, null, redirectAttributes, model);
            return "member/card/index";
        }

        // 存在しない
        if (comTransactionUtility.isErrorOccurred(comResultDto)) {
            CheckMessageDto messageDto = comResultDto.getMessageList().get(0);
            addMessage(messageDto.getMessageId(), messageDto.getArgs(), redirectAttributes, model);
            return "member/card/index";
        }

        // 登録済みカード情報Itemリスト作成
        memberCardHelper.setRegistedPaygentCardInfo(comResultDto, memberCardModel);

        return "/member/card/index";
    }

    /**
     * 「削除」ボタン押下
     *
     * @param memberCardModel カード情報画面 Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     * @return 遷移先
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doOnceDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/card/index")
    public String doOnceDelete(MemberCardModel memberCardModel, RedirectAttributes redirectAttributes, Model model) {

        // 指定したカードの情報取得
        MemberRegistedCardItem item =
                        memberCardModel.getRegistedCardItems().get(memberCardModel.getRegistedCardIndex());

        CardInfoDeleteRequest request = new CardInfoDeleteRequest();
        request.setCardId(item.getCardId());
        request.setMemberInfoSeq(String.valueOf(getCommonInfo().getCommonInfoUser().getMemberInfoSeq()));

        // カード削除処理
        try {
            memberInfoApi.updateCardInfo(request);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 削除完了メッセージ（設計外だがデザインに存在するので追加）
        addInfoMessage(MSGCD_DELETE_SUCCESS, null, redirectAttributes, model);

        return "redirect:/member/card/index.html";
    }

    /**
     * 「登録」ボタン押下
     *
     * @param memberCardModel カード情報画面 Model
     * @param error エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doOnceRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/card/index")
    public String doOnceRegist(@Validated(MemberCardGroup.class) MemberCardModel memberCardModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               Model model,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        if (error.hasErrors()) {
            // 入力チェックエラー
            // 設計外だが入力エリアが画面下部にあるので、チェックエラーに気づきにくいため当メッセージも追加出力
            addMessage(MSGCD_INPUT_ERROR, redirectAttributes, model);
            return "member/card/index";
        }

        // ペイジェントに登録済カードが10枚存在する場合エラー
        if (CollectionUtils.isNotEmpty(memberCardModel.getRegistedCardItems())
            && memberCardModel.getRegistedCardItems().size() >= PaymentController.CREDIT_MAX_REGISTED_COUNT) {
            addMessage(MSGCD_CREDIT_MAX_REGISTED, redirectAttributes, model);
            return "redirect:/member/card/index.html";
        }

        // カード登録処理
        try {
            memberInfoApi.registCardInfo(memberCardHelper.toCardInfoRegisterRequest(memberCardModel,
                                                                                    commonInfoUtility.getMemberInfoEntity(
                                                                                                    getCommonInfo())
                                                                                   ));
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);

            // ※補足
            // memberInfoApi.registCardInfo ではカード情報登録しか行わないので、
            // eの内容の判定（ペイジェント関連のエラーかどうか）までは行わない。
            // （pdr-frontsite-serviceとの通信エラー等の可能性は残るが、その場合FRONT全般が正常に動作しなくなるのでそこまで考慮しない）

            if (processLockAccount()) {
                // アカウントをロックした場合、メッセージをセットしてログアウト
                addMessage(MSGCD_LOCK_ACCOUNT_MEMBER, redirectAttributes, model);
                SecurityContextHolder.clearContext();
                new SecurityContextLogoutHandler().logout(
                                request, response, SecurityContextHolder.getContext().getAuthentication());
                return "redirect:/login/member.html";
            }

            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // クレジット登録が成功した場合、セッションに保持したクレジットカードエラー回数（集計値）を初期化
        getCommonInfo().getCommonInfoBase().setCreditErrorCount(null);

        // 登録完了メッセージ（設計外だがデザインに存在するので追加）
        addInfoMessage(MSGCD_REGIST_SUCCESS, null, redirectAttributes, model);

        return "redirect:/member/card/index.html";
    }

    /**
     * アカウントロック処理
     *
     * @return True：アカウントロックを実行、False：アカウントロックを省略（又は 失敗）
     */
    private boolean processLockAccount() {

        // クレジットカードエラー回数（集計値）をインクリメント
        int updatedCreditErrCount = getCommonInfo().getCommonInfoBase().getCreditErrorCount() + 1;
        // クレジットカードエラー回数上限（設定値）を超えない場合、アカウントロックは行わない
        if (updatedCreditErrCount < getCommonInfo().getCommonInfoShop().getCreditErrorCountLimit()) {
            // インクリメントした値をセッションに保持
            getCommonInfo().getCommonInfoBase().setCreditErrorCount(updatedCreditErrCount);
            return false;
        }

        //
        // 上限を超えた場合、以降の処理を行う

        // アカウントをロックする
        LoginFailureWithLockTimeUpdateRequest loginFailureWithLockTimeUpdateRequest =
                        new LoginFailureWithLockTimeUpdateRequest();
        loginFailureWithLockTimeUpdateRequest.setMemberInfoSeq(getCommonInfo().getCommonInfoUser().getMemberInfoSeq());
        loginFailureWithLockTimeUpdateRequest.setAccountLockTime(dateUtility.getCurrentTime());
        try {
            memberInfoApi.updateLoginFailureWithLocktime(loginFailureWithLockTimeUpdateRequest);
        } catch (HttpClientErrorException e) {
            // アカウントロックに失敗してもログだけ吐いてエラーとしない
            // ※どうせ呼び出し元でカード登録処理のエラーをthrowするので
            LOGGER.error("アカウントロックに失敗しました。", e);
            return false;
        }

        //
        // アカウントロックに成功した場合のみ、以降の処理を行う

        // 管理者メールを送信する
        SendMailForLockAccountByRegistCreditErrorRequest sendMailForLockAccountByRegistCreditErrorRequest =
                        new SendMailForLockAccountByRegistCreditErrorRequest();
        sendMailForLockAccountByRegistCreditErrorRequest.setCustomerNo(
                        commonInfoUtility.getCustomerNo(getCommonInfo()));
        sendMailForLockAccountByRegistCreditErrorRequest.setOfficeName(
                        getCommonInfo().getCommonInfoUser().getMemberInfoLastName());
        try {
            memberInfoApi.sendMailForLockAccountByRegistCreditError(sendMailForLockAccountByRegistCreditErrorRequest);
        } catch (HttpClientErrorException e) {
            // メール送信に失敗してもログだけ吐いてエラーとしない
            LOGGER.error("管理者メール（アカウントロック通知）の送信に失敗しました", e);
        }

        return true;
    }

}
// 2023-renew No60 to here

