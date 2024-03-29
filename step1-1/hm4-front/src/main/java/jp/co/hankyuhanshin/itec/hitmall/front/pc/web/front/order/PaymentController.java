/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.order.OrderApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.coupon.CouponHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.AbstractOrderController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation.PaymentValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation.group.PaymentGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CouponUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderPendingUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.StockUtility;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 注文_お支払い方法選択 Controller
 *
 * @author ota-s5
 */
@RequestMapping("/order")
@Controller
@SessionAttributes({"orderCommonModel", "paymentModel"})
// 2023-renew No14 from here
public class PaymentController extends AbstractOrderController {

    /** メッセージコード：不正操作 */
    public static final String MSGCD_ILLEGAL_OPERATION_PAYMENT = "AOX000401";

    /** メッセージコード：利用可能お支払い方法が０件 */
    public static final String MSGCD_NO_SETTLEMENT_METHOD = "AOX000406";

    /** メッセージコード：存在しないお支払い方法が選択された */
    public static final String MSGCD_SELECT_NO_SETTLEMENT_METHOD = "AOX000407";

    // PDR Migrate Customization from here
    /** メッセージコード：保持カードが最大件数(10件)存在し、新しいカードを選択した場合 */
    public static final String MSGCD_CREDIT_MAX_REGISTED = "PDR-0022-001-A-";

    /**
     * ペイジェントの保持カード上限数
     */
    public static final int CREDIT_MAX_REGISTED_COUNT = 10;
    // PDR Migrate Customization to here

    /**
     * 決済方法用動的バリデータ
     */
    private final PaymentValidator paymentValidator;

    /**
     * コンストラクタ
     */
    @Autowired
    public PaymentController(CouponHelper couponHelper,
                             OrderHelper orderHelper,
                             PaymentHelper paymentHelper,
                             OrderUtility orderUtility,
                             ConversionUtility conversionUtility,
                             CommonInfoUtility commonInfoUtility,
                             CouponUtility couponUtility,
                             ComTransactionUtility comTransactionUtility,
                             DateUtility dateUtility,
                             StockUtility stockUtility,
                             GoodsUtility goodsUtility,
                             OrderPendingUtility orderPendingUtility,
                             OrderApi orderApi,
                             MemberInfoApi memberInfoApi,
                             WebapiApi webapiApi,
                             ShopApi shopApi,
                             PaymentValidator paymentValidator) {
        super(couponHelper, orderHelper, paymentHelper, orderUtility, conversionUtility, commonInfoUtility,
              couponUtility, comTransactionUtility, dateUtility, stockUtility, goodsUtility, orderPendingUtility,
              orderApi, memberInfoApi, webapiApi, shopApi
             );
        this.paymentValidator = paymentValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder error) {
        // お支払方法選択画面の動的バリデータをセット
        error.addValidators(paymentValidator);
    }

    /**
     * お支払方法選択画面：初期処理
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param paymentModel       お支払い方法選択画面Model
     * @param model              モデル
     * @param redirectAttributes リダイレクトアトリビュート
     * @param request            リクエスト
     * @return お支払方法選択画面
     */
    @GetMapping(value = {"/payment", "/payment.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "order/payment")
    public String doLoadPayment(OrderCommonModel orderCommonModel,
                                PaymentModel paymentModel,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest request) {

        // モデル初期化
        clearModel(PaymentModel.class, paymentModel, model);

        // 必要な情報がないor不正操作の場合、カート画面に遷移させる。
        if (!checkReceiveOrderDto(orderCommonModel, redirectAttributes, model)) {
            return "redirect:/cart/index.html";
        }

        // PDR Migrate Customization from here
        // ブラウザバックチェック用トークンを生成
        setToken(orderCommonModel);
        // PDR Migrate Customization to here

        // お支払方法選択画面：初期表示時の共通処理を実行
        doLoadPayment(orderCommonModel, paymentModel, model, redirectAttributes);

        if (hasErrorMessage()) {
            this.throwMessage();
        }

        // PDR Migrate Customization from here
        // トークンチェック
        String returnPage = checkToken(orderCommonModel, redirectAttributes, model, request);
        if (returnPage != null) {
            return returnPage;
        }
        // PDR Migrate Customization to here

        return "order/payment";
    }

    /**
     * 「お支払方法を変更する」ボタン押下処理
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param paymentModel       お支払い方法選択画面Model
     * @param error              エラーフラグ
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @return 注文内容確認画面
     */
    @PostMapping(value = {"/payment", "/payment.html"}, params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/payment")
    public String doConfirm(OrderCommonModel orderCommonModel,
                            @Validated(PaymentGroup.class) PaymentModel paymentModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model,
                            HttpServletRequest request) {

        // 必要な情報がないor不正操作の場合、カート画面に遷移させる。
        if (!checkReceiveOrderDto(orderCommonModel, redirectAttributes, model)) {
            return "redirect:/cart/index.html";
        }

        if (error.hasErrors()) {
            return "order/payment";
        }

        // PDR Migrate Customization from here
        // トークンチェック
        String returnPage = checkToken(orderCommonModel, redirectAttributes, model, request);
        if (returnPage != null) {
            return returnPage;
        }
        // PDR Migrate Customization to here

        // 2023-renew No14 from here
        // 受注金額0円判定フラグ：OFFの場合
        // ※ONの場合、無料決済に置き換える処理は注文確認画面で行うため、ここでは何もしない
        if (!paymentModel.isOrderPriceZero()) {
            // お支払方法選択画面：「お支払方法を変更する」ボタン押下時の共通処理を実行
            doPaymentConfirm(orderCommonModel, paymentModel);
        }
        // 2023-renew No14 to here

        // 注文確認画面へ遷移
        return "redirect:/order/confirm.html";
    }

}
// 2023-renew No14 to here
