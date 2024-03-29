/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.coupon;

import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.coupon.AbstractCouponController;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * クーポン一覧 Controller
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@SessionAttributes(value = "couponModel")
@RequestMapping("/member/coupon")
@Controller
// 2023-renew No24 from here
public class CouponController extends AbstractCouponController {

    /**
     * SmartValidator
     */
    private final SmartValidator validator;

    /**
     * クーポンHelper
     */
    private final CouponHelper couponHelper;

    /**
     * コンストラクター
     *
     * @param webapiApi    WEB-APIApi
     * @param couponHelper クーポンHelper
     * @param commonInfoUtility 共通情報ヘルパークラス
     */
    @Autowired
    public CouponController(SmartValidator validator,
                            WebapiApi webapiApi,
                            CouponHelper couponHelper,
                            CommonInfoUtility commonInfoUtility) {
        super(webapiApi, couponHelper, commonInfoUtility);
        this.validator = validator;
        this.couponHelper = couponHelper;
    }

    /**
     * クーポン一覧画面：初期表示処理
     *
     * @param coupon クーポンコード
     * @param couponModel クーポン一覧Model
     * @param error エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     * @param request リクエスト
     * @param response レスポンス
     * @return 遷移先画面
     */
    @GetMapping(value = {"/", "/index.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "member/coupon/index")
    protected String doLoadIndex(@RequestParam(required = false) String coupon,
                                 CouponModel couponModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        // 初期化処理
        clearModel(CouponModel.class, couponModel, model);

        if (StringUtil.isNotEmpty(coupon)) {
            // リクエストパラメータでクーポンコードを指定している場合
            couponModel.setCouponCodeAdd(coupon);

            // クーポンコードの入力チェック
            validator.validate(couponModel, error);
            if (!error.hasErrors()) {
                // チェックOKの場合、クーポン取得処理へ移行
                return doAddCoupon(couponModel, error, redirectAttributes, model, request);
            }
        }

        // 画面表示情報取得
        setDisplayInfo(couponModel, redirectAttributes, model, request);

        return "member/coupon/index";
    }

    /**
     * 「クーポンを取得」ボタン押下
     *
     * @param couponModel クーポン一覧Model
     * @param error エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     * @param request リクエスト
     * @return 遷移先画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doAddCoupon")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/coupon/index")
    protected String doAddCoupon(@Validated CouponModel couponModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 HttpServletRequest request) {

        if (error.hasErrors()) {
            return "member/coupon/index";
        }

        // クーポン取得APIの呼び出し
        if (executeWebApiAddCouponAndSetCouponName(couponModel.getCouponCodeAdd(), model, redirectAttributes)) {
            // 成功した場合、クーポンコード（入力）を初期化
            couponModel.setCouponCodeAdd(null);
        }

        // 画面表示情報取得
        setDisplayInfo(couponModel, redirectAttributes, model, request);

        return "member/coupon/index";
    }

    /**
     *「このクーポンを使う」ボタン押下
     *
     * @param couponModel クーポン一覧Model
     * @return 遷移先画面
     */
    @PostMapping(value = {"/", "/index.html"}, params = "doSelectCoupon")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/coupon/index")
    protected String doSelectCoupon(CouponModel couponModel) {
        // cookieへの保持は当メソッド処理前に「/common/js/coupon.js」で実施済
        // カート画面へ遷移
        return "redirect:/cart/index.html";
    }

    /**
     * 画面表示情報取得
     * 画面に表示する情報を取得し、ページクラスにセットします。
     *
     * @param couponModel クーポン一覧Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     * @param request リクエスト
     */
    protected void setDisplayInfo(CouponModel couponModel,
                                  RedirectAttributes redirectAttributes,
                                  Model model,
                                  HttpServletRequest request) {

        // 利用可能クーポン一覧取得APIの呼び出しを行い、セッション情報の利用可能クーポン一覧数を更新
        couponHelper.toPageForLoad(couponModel, executeWebApiGetCouponListAndSetCouponCount(model, redirectAttributes));

        // 選択中のクーポンコードがある場合、初期値に設定
        couponModel.setCouponCode(getSelectCouponCode(null, redirectAttributes, model, request));
        // ログイン会員ID（cookie用）
        couponModel.setLoginUserId(getCommonInfo().getCommonInfoUser().getMemberInfoId());

    }

}

// 2023-renew No24 to here
