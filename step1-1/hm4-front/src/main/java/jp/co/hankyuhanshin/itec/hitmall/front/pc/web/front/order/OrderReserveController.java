/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.helper.crypto.MD5Helper;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.AbstractReserveController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.ReserveItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.validation.ReserveValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.validation.group.ReserveGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.AbstractOrderController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 注文_セールde予約 Controller
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@RequestMapping("/order")
@Controller
@SessionAttributes({"orderCommonModel", "orderReserveModel"})
// 2023-renew No14 from here
public class OrderReserveController extends AbstractReserveController<OrderReserveModel, OrderReserveHelper> {

    /**
     * 注文_セールde予約画面 Helper
     */
    private final OrderReserveHelper orderReserveHelper;

    /**
     * コンストラクタ
     */
    @Autowired
    public OrderReserveController(ReserveValidator reserveValidator,
                                  OrderReserveHelper orderReserveHelper,
                                  OrderHelper orderHelper,
                                  GoodsHelper goodsHelper,
                                  GoodsApi goodsApi,
                                  WebapiApi webapiApi,
                                  ShopApi shopApi,
                                  CommonInfoUtility commonInfoUtility,
                                  OrderUtility orderUtility,
                                  ConversionUtility conversionUtility,
                                  GoodsUtility goodsUtility,
                                  DateUtility dateUtility) {
        super(reserveValidator, orderReserveHelper, orderHelper, goodsHelper, goodsApi, webapiApi, shopApi,
              commonInfoUtility, orderUtility, conversionUtility, goodsUtility, dateUtility
             );
        this.orderReserveHelper = orderReserveHelper;
    }

    /**
     * 注文_セールde予約画面：初期処理
     *
     * @param gcd 商品コード（リクエストパラメータ）
     * @param orderCommonModel  注文フロー共通Model
     * @param orderReserveModel 注文_セールde予約画面Model
     * @param error エラー
     * @param redirectAttributes リダイレクトアトリビュート
     * @param request リクエスト
     * @param model モデル
     * @return 商品詳細画面 string
     */
    @GetMapping(value = {"/reserve", "/reserve.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "order/reserve")
    protected String doLoadIndex(@RequestParam(required = false) String gcd,
                                 OrderCommonModel orderCommonModel,
                                 OrderReserveModel orderReserveModel,
                                 BindingResult error,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request,
                                 Model model) {

        // 引継ぎデータのチェック
        ReserveItem reserveItem = (ReserveItem) model.getAttribute(AbstractOrderController.ORDER_RESERVE_ITEM);
        if (ObjectUtils.isEmpty(reserveItem)) {
            addWarnMessage(AbstractOrderController.MSGCD_INVALID_POST_TOKEN,
                           new Object[] {PropertiesUtil.getSystemPropertiesValue("app.complement.url")},
                           redirectAttributes, model
                          );
            // 引継ぎデータがない場合、カート画面に遷移
            return "redirect:/cart/index.html";
        }

        // モデル初期化
        clearModel(OrderReserveModel.class, orderReserveModel, model);

        // ブラウザバックチェック用トークンを生成
        setToken(orderCommonModel);

        // リクエストパラメータのチェック → 画面表示情報取得 → トークンチェック
        orderReserveHelper.setRequestParam(orderReserveModel, gcd);
        if (!checkRequestParam(orderReserveModel, redirectAttributes, model) || !setDisplayInfo(
                        orderReserveModel, redirectAttributes, model) || !checkToken(
                        orderCommonModel, redirectAttributes, model, request)) {
            // 当画面表示不可の場合、カート画面に遷移
            return "redirect:/cart/index.html";
        }

        // 前画面からの引継ぎデータを基にセールde予約情報Itemを作成
        orderReserveHelper.setReserveItem(orderReserveModel, reserveItem);

        // 入力チェックを実行（初期値が予約可能範囲外の場合を考慮）
        validate(orderReserveModel, error);

        return "order/reserve";
    }

    /**
     * 「変更する」ボタン押下
     *
     * @param orderCommonModel  注文フロー共通Model
     * @param orderReserveModel 注文_セールde予約画面Model
     * @param error エラー
     * @param request リクエスト
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     * @return the string
     */
    @PostMapping(value = {"/reserve", "/reserve.html"}, params = "doOrderReserveConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/reserve")
    public String doOrderReserveConfirm(OrderCommonModel orderCommonModel,
                                        @Validated(ReserveGroup.class) OrderReserveModel orderReserveModel,
                                        BindingResult error,
                                        HttpServletRequest request,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {

        if (error.hasErrors()) {
            return "order/reserve";
        }

        // トークンチェック
        if (!checkToken(orderCommonModel, redirectAttributes, model, request)) {
            // 不正遷移の場合、カート画面に遷移
            return "redirect:/cart/index.html";
        }

        // 相関チェックを行う
        checkInput(orderReserveModel, redirectAttributes, model);

        // 入力された情報から引継ぎ用のセールde予約情報Itemを再生成し、セッションに格納する
        redirectAttributes.addFlashAttribute(AbstractOrderController.ORDER_RESERVE_ITEM,
                                             orderReserveHelper.getOrderReserveItem(orderReserveModel)
                                            );

        // 配送方法選択画面へ遷移
        return "redirect:/order/delivery.html";
    }

    /**
     * 「お届け内容の変更へ戻る」ボタン押下
     *
     * @param orderCommonModel  注文フロー共通Model
     * @param orderReserveModel 注文_セールde予約画面Model
     * @param error エラー
     * @param request リクエスト
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model モデル
     * @return the string
     */
    @PostMapping(value = {"/reserve", "/reserve.html"}, params = "doBack")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/reserve")
    public String doBack(OrderCommonModel orderCommonModel,
                         @Validated(ReserveGroup.class) OrderReserveModel orderReserveModel,
                         BindingResult error,
                         HttpServletRequest request,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        // 前画面からの引継がれたセールde予約情報Itemをそのままセッションに格納する
        redirectAttributes.addFlashAttribute(AbstractOrderController.ORDER_RESERVE_ITEM,
                                             orderReserveModel.getBeforeReserveItem()
                                            );

        // 配送方法選択画面へ遷移
        return "redirect:/order/delivery.html";
    }

    /**
     * ページにトークンをセットする<br/>
     *
     * @param orderCommonModel 注文フロー共通Model
     */
    protected void setToken(OrderCommonModel orderCommonModel) {
        // トークン生成
        String token = ApplicationContextUtility.getBean(MD5Helper.class).createHash(Long.toString(System.nanoTime()));
        // 生成したトークンをセッションと画面でそれぞれ保持
        orderCommonModel.setSToken(token);
        orderCommonModel.setPToken(token);
    }

    /**
     * ブラウザバックチェック
     *
     * @param orderCommonModel   注文フロー共通Model
     * @param redirectAttributes リダイレクトアトリビュート
     * @param model              モデル
     * @param request            リクエスト
     * @return True:正常、False：異常
     */
    protected boolean checkToken(OrderCommonModel orderCommonModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 HttpServletRequest request) {

        // GETで来た場合は、以下理由により常にOK
        // ・トークンがPOSTされない
        // ・必ずdoLoadを通る
        String method = request.getMethod();
        if (!"POST".equals(method)) {
            return true;
        }

        String sToken = orderCommonModel.getSToken();
        String pToken = orderCommonModel.getPToken();

        // トークンがずれている場合はカートに戻す
        // ※通常は/order/配下以外のページを表示すると、注文許可フラグが消えてエラー画面に遷移するため
        // ここに来た時点でサブアプリが消えていることはありえない
        // しかし、注文中に/login/order.html（注文ログイン画面）に直URLで遷移⇒ブラウザバックしてきた場合のみ、
        // ここに来た時点でsTokenがnullとなる
        // ⇒nullチェックが必要
        if (sToken == null || !sToken.equals(pToken)) {
            clearErrorList();
            addWarnMessage(AbstractOrderController.MSGCD_INVALID_POST_TOKEN, null, redirectAttributes, model);
            return false;
        }
        return true;
    }

}
// 2023-renew No14 to here
