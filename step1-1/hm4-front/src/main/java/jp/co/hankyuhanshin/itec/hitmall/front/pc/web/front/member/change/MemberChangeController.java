/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.change;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoConfirmScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ZipcodeApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ResultFlagResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ZipCodeMatchingCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSexUnnecessaryAnswer;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
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
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 会員情報変更 Controller
 *
 * @author kimura
 */
@RequestMapping("/member/change")
@Controller
@SessionAttributes(value = "memberChangeModel")
public class MemberChangeController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberChangeController.class);

    /**
     * 不正遷移
     */
    protected static final String MSGCD_REFERER_FAIL = "AMX000301";

    /**
     * 不正遷移
     */
    protected static final String MSGCD_REFERER_FAIL_CONFIRM = "AMX000401";

    /**
     * 都道府県のプルダウンいじられた
     */
    protected static final String MSGCD_ILLEGAL_PREFECTURE = "AMX000302";

    /**
     * 性別のラジオボタンいじられた
     */
    protected static final String MSGCD_ILLEGAL_SEX = "AMX000303";

    /**
     * 都道府県と郵便番号の整合性エラー ※W付与
     */
    protected static final String MSGCD_PREFECTURE_CONSISTENCY = "AMX000304W";

    /**
     * 都道府県フィールド名
     **/
    protected static final String FILED_NAME_PREFECTURE = "memberInfoPrefecture";

    /**
     * 前画面が確認画面の「修正する」からであるかを判断するフラグ
     */
    protected static final String FLAG_FROMCONFIRM = "fromConfirm";

    /**
     * 会員情報変更Helper
     */
    private final MemberChangeHelper memberChangeHelper;

    // 廃止不要機能
    //    /**
    //     * 認証サービス
    //     */
    //    private final HmFrontUserDetailsServiceImpl userDetailsService;

    /**
     * 会員Api
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * ショップApi
     */
    private final ShopApi shopApi;

    /**
     * 郵便番号住所情報Api
     */
    private final ZipcodeApi zipcodeApi;

    /**
     * コンストラクタ
     *
     * @param memberChangeHelper
     * @param memberInfoApi
     * @param shopApi
     * @param zipcodeApi
     */
    @Autowired
    public MemberChangeController(MemberChangeHelper memberChangeHelper,
                                  MemberInfoApi memberInfoApi,
                                  ShopApi shopApi,
                                  ZipcodeApi zipcodeApi) {
        this.memberChangeHelper = memberChangeHelper;
        this.memberInfoApi = memberInfoApi;
        this.shopApi = shopApi;
        this.zipcodeApi = zipcodeApi;
    }

    /**
     * 入力画面：初期処理
     *
     * @param memberChangeModel
     * @param model
     * @return 入力画面
     */
    @GetMapping(value = {"/", "/index.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "member/change/index")
    protected String doLoadIndex(MemberChangeModel memberChangeModel, Model model)
                    throws InvocationTargetException, IllegalAccessException {

        // 確認画面からの遷移の場合は、セッション情報を表示
        if (model.containsAttribute(FLAG_FROMCONFIRM)) {

            // 必須項目がない場合 エラー
            if (checkInput(memberChangeModel)) {
                throwMessage(MSGCD_REFERER_FAIL);
            } else {
                return "member/change/index";
            }
        }

        // モデル初期化
        clearModel(MemberChangeModel.class, memberChangeModel, model);

        // 動的コンポーネント作成
        initComponents(memberChangeModel);

        // 会員SEQから会員情報取得
        Integer memberInfoSeq = memberChangeModel.getCommonInfo().getCommonInfoUser().getMemberInfoSeq();
        ;
        MemberInfoEntityResponse memberInfoEntityResponse = null;
        try {
            memberInfoEntityResponse = memberInfoApi.getByMemberInfoSeq(memberInfoSeq);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        MemberInfoEntity memberInfoEntity = memberChangeHelper.toMemberInfoEntity(memberInfoEntityResponse);

        // 変更前情報
        memberChangeModel.setOriginalMemberInfoEntity(CopyUtil.deepCopy(memberInfoEntity));

        // 取得情報をページへセット
        try {
            memberChangeHelper.toPageForLoad(memberInfoEntity, memberChangeModel);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("例外処理が発生しました", e);
            throwMessage(MSGCD_REFERER_FAIL);
        }

        return "member/change/index";
    }

    /**
     * 入力画面：確認画面に遷移
     *
     * @param memberChangeModel
     * @param error
     * @param redirectAttributes
     * @param model
     * @return 確認画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/change/index")
    public String doConfirm(@Validated MemberChangeModel memberChangeModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        // 都道府県と郵便番号が不一致の場合
        if (!checkPrefectureAndZipCode(memberChangeModel)) {
            error.rejectValue(FILED_NAME_PREFECTURE, MSGCD_PREFECTURE_CONSISTENCY);
        }

        if (error.hasErrors()) {
            return "member/change/index";
        }

        // 入力チェック。POSTされた値が不正でないか。
        checkIllegalParameter(memberChangeModel);

        MemberInfoEntity memberInfoEntity = memberChangeModel.getMemberInfoEntity();

        // Sessionの値がなくなっているかのNullチェックを行う
        if (StringUtils.isEmpty(memberInfoEntity.getMemberInfoId())) {
            // モデル初期化後、エラーページへリダイレクト（現行でもここに侵入する可能性なし）
            clearModel(MemberChangeModel.class, memberChangeModel, model);
            addMessage(MSGCD_REFERER_FAIL, redirectAttributes, model);
            return "redirect:/error.html";
        }

        return "redirect:/member/change/confirm.html";
    }

    /**
     * 確認画面：入力画面に遷移
     *
     * @param memberChangeModel
     * @param redirectAttributes
     * @param model
     * @return 入力画面
     */
    @PostMapping(value = {"/confirm", "/confirm.html"}, params = "doIndex")
    public String doIndexConfirm(MemberChangeModel memberChangeModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        // 遷移元フラグ設定
        redirectAttributes.addFlashAttribute(FLAG_FROMCONFIRM, null);

        return "redirect:/member/change/index.html";
    }

    /**
     * 確認画面：初期処理
     *
     * @param memberChangeModel
     * @param redirectAttributes
     * @param model
     * @return 確認画面
     */
    @GetMapping(value = {"/confirm", "/confirm.html"})
    protected String doLoadConfirm(MemberChangeModel memberChangeModel,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        // 不正遷移チェック
        if (checkInput(memberChangeModel)) {
            addMessage(MSGCD_REFERER_FAIL_CONFIRM, redirectAttributes, model);
            return "redirect:/member/change/index.html";
        }

        // 入力値からエンティティを作成（変更後データ）
        MemberInfoEntity modifiedMemberInfoEntity;
        try {
            modifiedMemberInfoEntity = memberChangeHelper.toMemberInfoEntityForMemberInfoUpdate(memberChangeModel);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(MSGCD_REFERER_FAIL_CONFIRM, redirectAttributes, model);
            return "redirect:/member/change/index.html";
        }

        // 変更前データと変更後データの差異リスト作成
        List<String> modifiedList =
                        DiffUtil.diff(memberChangeModel.getOriginalMemberInfoEntity(), modifiedMemberInfoEntity);
        memberChangeModel.setModifiedList(modifiedList);

        return "member/change/confirm";
    }

    // 2023-renew AddNo2 from here
//    /**
//     * 本会員登録処理<br/>
//     *
//     * @param memberChangeModel
//     * @param sessionStatus
//     * @param redirectAttributes
//     * @param model
//     * @return 完了画面
//     */
//    @PostMapping(value = {"/confirm", "/confirm.html"}, params = "doOnceMemberInfoUpdate")
//    @HEHandler(exception = AppLevelListException.class, returnView = "member/change/confirm")
//    public String doOnceMemberInfoUpdate(MemberChangeModel memberChangeModel,
//                                         SessionStatus sessionStatus,
//                                         RedirectAttributes redirectAttributes,
//                                         Model model,
//                                         HttpServletRequest request) {
//
//        // 会員情報の作成
//        MemberInfoEntity memberInfoEntity;
//        try {
//            memberInfoEntity = memberChangeHelper.toMemberInfoEntityForMemberInfoUpdate(memberChangeModel);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            LOGGER.error("例外処理が発生しました", e);
//            addMessage(MSGCD_REFERER_FAIL_CONFIRM, redirectAttributes, model);
//            return "redirect:/member/change/index.html";
//        }
//
//        MemberInfoConfirmScreenUpdateRequest memberRequest =
//                        memberChangeHelper.toMemberInfoConfirmScreenUpdateRequest(memberInfoEntity);
//
//        // 会員情報更新
//        try {
////            memberInfoApi.updateConfirmScreen(memberRequest);
//        } catch (HttpClientErrorException e) {
//            LOGGER.error("例外処理が発生しました", e);
//            // AppLevelListExceptionへ変換
//            addAppLevelListException(e);
//            throwMessage();
//        }
//
//        // セッションのユーザー情報を更新
//        request.setAttribute("isCheckInfo", true);
//
//        // 廃止不要機能
//        //        userDetailsService.updateUserInfo(this.getCommonInfo().getCommonInfoUser().getMemberInfoId());
//
//        // Modelをセッションより破棄
//        sessionStatus.setComplete();
//
//        // 完了画面へ遷移
//        return "redirect:/member/change/complete.html";
//    }
    // 2023-renew AddNo2 to here

    /**
     * 完了画面：画面表示処理
     *
     * @return 完了画面
     */
    @GetMapping(value = {"/complete", "/complete.html"})
    public String doLoadComplete() {

        return "member/change/complete";
    }

    /**
     * 動的コンポーネント作成
     *
     * @param memberChangeModel
     */
    protected void initComponents(MemberChangeModel memberChangeModel) {

        // 都道府県区分値を取得（北海道：北海道）
        Map<String, String> prefectureTypeItems = EnumTypeUtil.getEnumMap(HTypePrefectureType.class, true);
        memberChangeModel.setMemberInfoPrefectureItems(prefectureTypeItems);

        // 性別(未回答)区分値を取得
        Map<String, String> memberInfoSexItems = EnumTypeUtil.getEnumMap(HTypeSexUnnecessaryAnswer.class);
        memberChangeModel.setMemberInfoSexItems(memberInfoSexItems);
    }

    /**
     * 必須項目を全てチェックし、不正遷移かどうかをチェック<br/>
     *
     * @return true=不正、false=正常
     */
    protected boolean checkInput(MemberChangeModel memberChangeModel) {

        // 氏名(姓)
        if (StringUtils.isEmpty(memberChangeModel.getMemberInfoLastName())) {
            return true;
        }

        // 氏名(名)
        if (StringUtils.isEmpty(memberChangeModel.getMemberInfoFirstName())) {
            return true;
        }

        // フリガナ(セイ)
        if (StringUtils.isEmpty(memberChangeModel.getMemberInfoLastKana())) {
            return true;
        }

        // フリガナ(メイ)
        if (StringUtils.isEmpty(memberChangeModel.getMemberInfoFirstKana())) {
            return true;
        }

        // 性別
        if (StringUtils.isEmpty(memberChangeModel.getMemberInfoSex())) {
            return true;
        }

        // 電話番号
        if (StringUtils.isEmpty(memberChangeModel.getMemberInfoTel())) {
            return true;
        }

        // 郵便番号1
        if (StringUtils.isEmpty(memberChangeModel.getMemberInfoZipCode1())) {
            return true;
        }

        // 郵便番号2
        if (StringUtils.isEmpty(memberChangeModel.getMemberInfoZipCode2())) {
            return true;
        }

        // 都道府県
        if (StringUtils.isEmpty(memberChangeModel.getMemberInfoPrefecture())) {
            return true;
        }

        // 住所1
        if (StringUtils.isEmpty(memberChangeModel.getMemberInfoAddress1())) {
            return true;
        }

        // 住所2
        if (StringUtils.isEmpty(memberChangeModel.getMemberInfoAddress2())) {
            return true;
        }

        return false;
    }

    /**
     * 都道府県と郵便番号の不整合チェック<br/>
     *
     * @param memberChangeModel
     */
    protected boolean checkPrefectureAndZipCode(MemberChangeModel memberChangeModel) {

        // nullの場合service未実行(必須チェックは別タスク)
        if (StringUtils.isEmpty(memberChangeModel.getMemberInfoZipCode1()) || StringUtils.isEmpty(
                        memberChangeModel.getMemberInfoZipCode2()) || StringUtils.isEmpty(
                        memberChangeModel.getMemberInfoPrefecture())) {
            return true;
        }
        ZipCodeMatchingCheckRequest request = new ZipCodeMatchingCheckRequest();
        request.setZipCode(memberChangeModel.getMemberInfoZipCode1() + memberChangeModel.getMemberInfoZipCode2());
        request.setPrefecture(memberChangeModel.getMemberInfoPrefecture());

        ResultFlagResponse checkZipcodeMatching = null;
        try {
            checkZipcodeMatching = zipcodeApi.registCheckZipcodeMatching(request);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 郵便番号住所情報取得サービス実行
        return checkZipcodeMatching.getResultFlag();
    }

    /**
     * 入力チェック
     *
     * @param memberChangeModel
     */
    protected void checkIllegalParameter(MemberChangeModel memberChangeModel) {

        // 都道府県入力値チェック
        checkPrefecture(memberChangeModel);
        // 性別入力値チェック
        checkSex(memberChangeModel);

    }

    /**
     * 都道府県の入力チェック<br/>
     * POSTされるプルダウンの値の書き換え対策
     *
     * @param memberChangeModel
     */
    protected void checkPrefecture(MemberChangeModel memberChangeModel) {

        Map<String, String> prefectureMap = EnumTypeUtil.getEnumMap(HTypePrefectureType.class, true);
        boolean existFlag = prefectureMap.containsKey(memberChangeModel.getMemberInfoPrefecture());
        if (!existFlag) {
            throwMessage(MSGCD_ILLEGAL_PREFECTURE);
        }
    }

    /**
     * 性別(未回答)の入力チェック<br/>
     * POSTされる値の書き換え対策
     *
     * @param memberChangeModel
     */
    protected void checkSex(MemberChangeModel memberChangeModel) {

        Map<String, String> memberInfoSexMap = EnumTypeUtil.getEnumMap(HTypeSexUnnecessaryAnswer.class);
        boolean existFlag = memberInfoSexMap.containsKey(memberChangeModel.getMemberInfoSex());
        if (!existFlag) {
            throwMessage(MSGCD_ILLEGAL_SEX);
        }
        return;
    }
}
