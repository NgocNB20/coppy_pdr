/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.ajax.MessageUtils;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.ajax.ValidatorMessage;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.validation.detailsupdate.DetailsUpdateValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.validation.detailsupdate.group.AdditionalChargeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.validation.detailsupdate.group.OnceOrderCancelGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.validation.detailsupdate.group.OrderGoodsDeleteGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.validation.detailsupdate.group.OrderGoodsModifyGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details.validation.detailsupdate.group.ReCalculateGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCouponLimitTargetType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGmoReleaseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInvoiceAttachmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUseConveni;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractOrderCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.SettlememtMismatchCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.BillPriceCalculateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.ConvenienceLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsMixedTaxCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.ReceiveOrderCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.ReceiveOrderUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsDetailsListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.DeliveryMethodSelectListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ReceiveOrderCancelService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ReceiveOrderGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.SettlementMethodSelectListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.zipcode.ZipCodeMatchingCheckService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.seasar.EmptyRuntimeException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.UIComponentUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 受注詳細アクション<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/order/detailsupdate")
@Controller
@SessionAttributes(value = "detailsUpdateModel")
@PreAuthorize("hasAnyAuthority('ORDER:8')")
public class DetailsUpdateController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailsUpdateController.class);

    /**
     * パラメータエラー
     */
    public static final String MSGCD_PARAM_ERROR = "AOX002201";

    /**
     * エラーコード。郵便番号と都道府県とが一致しない
     */
    public static final String MSGCD_PREFECTURE_CONSISTENCY = "AOX001011";

    /**
     * エラーコード。受注商品が未選択
     */
    public static final String MSGCD_DELETE_FAIL = "AOX001012";

    /**
     * エラーコード。請求決済エラー時はカード決済選択不可
     */
    public static final String MSGCD_SETTLEMENT_METHOD_FAIL = "AOX001013";

    /**
     * エラーコード。請求エラー
     */
    public static final String MSGCD_ORDER_BILL_EMERGENCY = "AOX001008";

    /**
     * 割引対象金額がクーポン適用金額の条件を満たしていない場合エラー
     */
    public static final String MSGCD_NOTFULL_COUPONDISCOUNTCONDITION = "PKG-4243-001-A-";

    /**
     * 表示モード(md):list 検索画面の再検索実行
     */
    public static final String MODE_LIST = "list";

    /**
     * modifiedReceiveOrder に処理履歴詳細画面で取得した受注情報が入っている場合は true
     *
     * <pre>
     * 受注詳細修正確認画面　⇒　処理履歴詳細画面　⇒　ブラウザバックで修正実行
     * とすると処理履歴の内容で更新してしまう為、フラグでチェックする
     * </pre>
     */
    public static final String FLASH_HISTORYDETAILSFLAG = "historyDetailsFlag";

    /**
     * マスタ最新値変更エラー：インデント
     */
    public static final String INDENT = "&nbsp;&nbsp;";

    /**
     * マスタ最新値変更エラー：改行
     */
    public static final String NEW_LINE = "<br />";

    /**
     * マスタ最新値変更エラー：比較結果判定用Map
     */
    public static final Map<String, String> DIFF_ERROR_ITEM_MAP;

    static {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("orderSettlementEntity.carriage", "・受注商品 ： 送料");
        map.put("orderSettlementEntity.settlementCommission", "・受注商品 ： 手数料");
        DIFF_ERROR_ITEM_MAP = map;
    }

    /**
     * re-authorization not possible
     */
    public static final String REAUTHORIZATION_NOT_ALLOWED = "PKG-3773-007-L-";

    /**
     * 請求情報の不整合チェックLogic
     */
    private SettlememtMismatchCheckLogic settlememtMismatchCheckLogic;

    /**
     * 受注詳細情報取得サービス
     */
    private ReceiveOrderGetService receiveOrderGetService;

    /**
     * 受注詳細ページ変換
     */
    private DetailsUpdateHelper detailsupdateHelper;

    /**
     * 決済方法リスト取得サービス
     */
    private SettlementMethodSelectListGetService settlementMethodSelectListGetService;

    /**
     * 配送方法リスト取得サービス
     */
    private DeliveryMethodSelectListGetService deliveryMethodListGetService;

    /**
     * 受注キャンセルサービス
     */
    private ReceiveOrderCancelService receiveOrderCancelService;

    /**
     * 受注詳細DTOリスト
     */
    private GoodsDetailsListGetService goodsDetailsListGetService;

    /**
     * 受注情報取得サービス
     */
    private DeliveryMethodGetService deliveryMethodGetService;

    /**
     * 郵便番号整合性チェックサービス
     */
    private ZipCodeMatchingCheckService zipCodeMatchingCheckService;

    /**
     * コンビニ名称リスト取得ロジック
     */
    private ConvenienceLogic convenienceLogic;

    /**
     * 受注修正チェックロジック
     */
    private ReceiveOrderCheckLogic receiveOrderCheckLogic;

    /**
     * 受注関連ユーティリティ
     */
    private OrderUtility orderUtility;

    /**
     * 受注修正ロジック
     */
    private ReceiveOrderUpdateLogic receiveOrderUpdateLogic;

    /**
     * 同一商品、税率混在チェックLogic
     */
    private OrderGoodsMixedTaxCheckLogic orderGoodsMixedTaxCheckLogic;

    /**
     * お届け時間帯用動的バリデータ
     */
    private DetailsUpdateValidator detailsUpdateValidator;

    /**
     * 確認画面から
     */
    public static final String FLASH_FROM_CONFIRM = "fromConfirm";

    /**
     * コンストラクター
     *
     * @param settlememtMismatchCheckLogic
     * @param receiveOrderGetService
     * @param detailsupdateHelper
     * @param settlementMethodSelectListGetService
     * @param deliveryMethodListGetService
     * @param receiveOrderCancelService
     * @param goodsDetailsListGetService
     * @param deliveryMethodGetService
     * @param zipCodeMatchingCheckService
     * @param convenienceLogic
     * @param receiveOrderCheckLogic
     * @param orderUtility
     * @param receiveOrderUpdateLogic
     * @param orderGoodsMixedTaxCheckLogic
     */
    @Autowired
    public DetailsUpdateController(SettlememtMismatchCheckLogic settlememtMismatchCheckLogic,
                                   ReceiveOrderGetService receiveOrderGetService,
                                   DetailsUpdateHelper detailsupdateHelper,
                                   SettlementMethodSelectListGetService settlementMethodSelectListGetService,
                                   DeliveryMethodSelectListGetService deliveryMethodListGetService,
                                   ReceiveOrderCancelService receiveOrderCancelService,
                                   GoodsDetailsListGetService goodsDetailsListGetService,
                                   DeliveryMethodGetService deliveryMethodGetService,
                                   ZipCodeMatchingCheckService zipCodeMatchingCheckService,
                                   ConvenienceLogic convenienceLogic,
                                   ReceiveOrderCheckLogic receiveOrderCheckLogic,
                                   OrderUtility orderUtility,
                                   ReceiveOrderUpdateLogic receiveOrderUpdateLogic,
                                   OrderGoodsMixedTaxCheckLogic orderGoodsMixedTaxCheckLogic,
                                   DetailsUpdateValidator detailsUpdateValidator) {
        this.settlememtMismatchCheckLogic = settlememtMismatchCheckLogic;
        this.receiveOrderGetService = receiveOrderGetService;
        this.detailsupdateHelper = detailsupdateHelper;
        this.settlementMethodSelectListGetService = settlementMethodSelectListGetService;
        this.deliveryMethodListGetService = deliveryMethodListGetService;
        this.receiveOrderCancelService = receiveOrderCancelService;
        this.goodsDetailsListGetService = goodsDetailsListGetService;
        this.deliveryMethodGetService = deliveryMethodGetService;
        this.zipCodeMatchingCheckService = zipCodeMatchingCheckService;
        this.convenienceLogic = convenienceLogic;
        this.receiveOrderCheckLogic = receiveOrderCheckLogic;
        this.orderUtility = orderUtility;
        this.receiveOrderUpdateLogic = receiveOrderUpdateLogic;
        this.orderGoodsMixedTaxCheckLogic = orderGoodsMixedTaxCheckLogic;
        this.detailsUpdateValidator = detailsUpdateValidator;
    }

    @InitBinder(value = "detailsUpdateModel")
    public void initBinder(WebDataBinder error) {
        // 商品詳細更新画面の動的バリデータをセット
        error.addValidators(detailsUpdateValidator);
    }

    /**
     * 初期処理<br/>
     *
     * @return 自画面
     */
    @GetMapping(value = "")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/detailsupdate")
    public String doLoadIndex(@RequestParam(required = false) Optional<String> md,
                              @RequestParam(required = false) Optional<String> orderCode,
                              @RequestParam(required = false) Optional<String> from,
                              DetailsUpdateModel detailsUpdateModel,
                              BindingResult error,
                              RedirectAttributes redirectAttrs,
                              Model model) { // ,

        if (from.isPresent() && from.get().equals("confirm")) {
            return "order/details/detailsupdate";
        }

        // コンポーネント値初期化
        initComponentValue(detailsUpdateModel);
        // キープセッションを確認してください
        if (md.isPresent() && md.get().equalsIgnoreCase("confirm")) {
            model.addAttribute(FLASH_FROM_CONFIRM, true);
        }

        if (!model.containsAttribute(FLASH_FROM_CONFIRM)) {
            clearModel(DetailsUpdateModel.class, detailsUpdateModel, model);
            initComponentValue(detailsUpdateModel);
        }

        ReceiveOrderDto original = null;
        ReceiveOrderDto modified = null;
        String mdFromParam = StringUtils.EMPTY;

        if (model.containsAttribute(DetailsUpdateModel.FLASH_GOODS_SEARCH_MODEL)) {
            GoodsSearchModel goodsSearchModel =
                            (GoodsSearchModel) model.getAttribute(DetailsUpdateModel.FLASH_GOODS_SEARCH_MODEL);
            modified = goodsSearchModel.getModifiedReceiveOrder();
            original = goodsSearchModel.getOriginalReceiveOrder();
            detailsUpdateModel.setModifiedReceiveOrder(modified);
            detailsUpdateModel.setOriginalReceiveOrder(original);
        } else if (model.containsAttribute(DetailsUpdateModel.FLASH_ADDITIONAL_CHARGE_MODEL)) {
            OrderAdditionalChargeModel orderAdditionalChargeModel = (OrderAdditionalChargeModel) model.getAttribute(
                            DetailsUpdateModel.FLASH_ADDITIONAL_CHARGE_MODEL);
            modified = orderAdditionalChargeModel.getModifiedReceiveOrder();
            original = orderAdditionalChargeModel.getOriginalReceiveOrder();
            detailsUpdateModel.setModifiedReceiveOrder(modified);
            detailsUpdateModel.setOriginalReceiveOrder(original);
        } else {
            modified = detailsUpdateModel.getModifiedReceiveOrder();
        }

        if (md.isPresent()) {
            mdFromParam = md.get();
        }

        if (orderCode.isPresent()) {
            detailsUpdateModel.setOrderCode(orderCode.get());
        }

        if (("new".equals(mdFromParam)) || (modified == null)) {

            // 不正操作チェック
            illegalOperationCheck(true, detailsUpdateModel, redirectAttrs, model);

            // 受注詳細情報取得サービス実行
            String orderCodeParam = detailsUpdateModel.getOrderCode();
            original = receiveOrderGetService.execute(orderCodeParam);

            // 受注キャンセルチェック
            if (HTypeCancelFlag.ON == original.getOrderSummaryEntity().getCancelFlag()) {
                // 受注詳細ページへリダイレクト
                return "redirect:/order/details/?md=cou&orderCode=" + detailsUpdateModel.getOrderCode();
            }

            modified = CopyUtil.deepCopy(original);
            // 受注詳細情報保持
            detailsUpdateModel.setOriginalReceiveOrder(original);
            detailsUpdateModel.setModifiedReceiveOrder(modified);
            detailsUpdateModel.setHistoryDetailsFlag(false);
            detailsUpdateModel.setOrderReceiverItem(null);

        } else {
            // 不正操作チェック
            illegalOperationCheck(false, detailsUpdateModel, redirectAttrs, model);
        }

        // 受注詳細情報リスト取得
        List<GoodsDetailsDto> goodsDetailsList =
                        goodsDetailsListGetService.execute(detailsupdateHelper.getOrderGoodsSeqList(modified));
        // 商品詳細情報リストをセット
        detailsUpdateModel.setGoodsDetailsList(goodsDetailsList);

        // コンビニ名称リストをセット
        createConvenienceSelect(detailsUpdateModel);

        this.toDisplay(detailsUpdateModel, redirectAttrs, model);

        if ("new".equals(mdFromParam)) {
            // マスタ値変更アラートメッセージ表示
            List<String> orderInfoDiff = DiffUtil.diff(original, modified);
            if (orderInfoDiff != null && orderInfoDiff.size() > 0) {
                this.createDiffMessage(orderInfoDiff, redirectAttrs, model);
            }
        }

        // 請求決済エラーの場合はメッセージを表示
        if ((detailsUpdateModel.getEmergencyFlag() == HTypeEmergencyFlag.ON)) {
            // 受注状態が出荷完了以外の場合
            if (detailsUpdateModel.getOrderStatus() != HTypeOrderStatus.SHIPMENT_COMPLETION) {
                addMessage(ShipmentRegistService.MSGCD_EMERGENCY_BEFOLE_SHIPMENT_ERROR, redirectAttrs, model);
            }
            addMessage(ShipmentRegistService.MSGCD_EMERGENCY_ERROR, redirectAttrs, model);
        }

        // 「受注修正確認」画面から戻ってきた時に「決済手数料ダイアログ選択フラグ」をリセット
        detailsUpdateModel.setCommissionSelected(false);

        return "order/details/detailsupdate";
    }

    /**
     * 初期処理<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/loadIndexAjax")
    @ResponseBody
    public ResponseEntity<?> doLoadIndexAjax(@RequestParam(required = false) Optional<String> md,
                                             @RequestParam(required = false) Optional<String> orderCode,
                                             DetailsUpdateModel detailsUpdateModel,
                                             @RequestBody GoodsSearchModel goodsSearchModel,
                                             RedirectAttributes redirectAttrs,
                                             Model model) {
        List<ValidatorMessage> messageList = new ArrayList<>();
        ReceiveOrderDto original = null;
        ReceiveOrderDto modified = null;
        String mdFromParam = StringUtils.EMPTY;

        if (!ObjectUtils.isEmpty(goodsSearchModel)) {
            modified = goodsSearchModel.getModifiedReceiveOrder();
            original = goodsSearchModel.getOriginalReceiveOrder();
            detailsUpdateModel.setModifiedReceiveOrder(modified);
            detailsUpdateModel.setOriginalReceiveOrder(original);
        } else if (model.containsAttribute(DetailsUpdateModel.FLASH_ADDITIONAL_CHARGE_MODEL)) {
            OrderAdditionalChargeModel orderAdditionalChargeModel = (OrderAdditionalChargeModel) model.getAttribute(
                            DetailsUpdateModel.FLASH_ADDITIONAL_CHARGE_MODEL);
            modified = orderAdditionalChargeModel.getModifiedReceiveOrder();
            original = orderAdditionalChargeModel.getOriginalReceiveOrder();
            detailsUpdateModel.setModifiedReceiveOrder(modified);
            detailsUpdateModel.setOriginalReceiveOrder(original);
        } else {
            modified = detailsUpdateModel.getModifiedReceiveOrder();
        }

        if (md.isPresent()) {
            mdFromParam = md.get();
        }

        if (orderCode.isPresent()) {
            detailsUpdateModel.setOrderCode(orderCode.get());
        }

        if (("new".equals(mdFromParam)) || (modified == null)) {

            // 不正操作チェック
            illegalOperationCheck(true, detailsUpdateModel, redirectAttrs, model);

            // 受注詳細情報取得サービス実行
            String orderCodeParam = detailsUpdateModel.getOrderCode();
            original = receiveOrderGetService.execute(orderCodeParam);

            // 受注キャンセルチェック
            //            if (HTypeCancelFlag.ON == original.getOrderSummaryEntity().getCancelFlag()) {
            //                // 受注詳細ページへリダイレクト
            //                return "redirect:/order/details/?md=cou&orderCode=" + detailsUpdateModel.getOrderCode();
            //            }

            modified = CopyUtil.deepCopy(original);
            // 受注詳細情報保持
            detailsUpdateModel.setOriginalReceiveOrder(original);
            detailsUpdateModel.setModifiedReceiveOrder(modified);
            detailsUpdateModel.setHistoryDetailsFlag(false);
            detailsUpdateModel.setOrderReceiverItem(null);

        } else {
            // 不正操作チェック
            illegalOperationCheck(false, detailsUpdateModel, redirectAttrs, model);
        }

        // 受注詳細情報リスト取得
        List<GoodsDetailsDto> goodsDetailsList =
                        goodsDetailsListGetService.execute(detailsupdateHelper.getOrderGoodsSeqList(modified));
        // 商品詳細情報リストをセット
        detailsUpdateModel.setGoodsDetailsList(goodsDetailsList);

        // コンビニ名称リストをセット
        createConvenienceSelect(detailsUpdateModel);

        this.toDisplay(detailsUpdateModel, redirectAttrs, model);

        if ("new".equals(mdFromParam)) {
            // マスタ値変更アラートメッセージ表示
            List<String> orderInfoDiff = DiffUtil.diff(original, modified);
            if (orderInfoDiff != null && orderInfoDiff.size() > 0) {
                this.createDiffMessage(orderInfoDiff, redirectAttrs, model);

            }
        }

        // 請求決済エラーの場合はメッセージを表示
        if ((detailsUpdateModel.getEmergencyFlag() == HTypeEmergencyFlag.ON)) {
            // 受注状態が出荷完了以外の場合
            if (detailsUpdateModel.getOrderStatus() != HTypeOrderStatus.SHIPMENT_COMPLETION) {
                MessageUtils.getAllMessage(
                                messageList, ShipmentRegistService.MSGCD_EMERGENCY_BEFOLE_SHIPMENT_ERROR, null);
                return ResponseEntity.badRequest().body(messageList);
            }
            MessageUtils.getAllMessage(messageList, ShipmentRegistService.MSGCD_EMERGENCY_ERROR, null);
            return ResponseEntity.badRequest().body(messageList);
        }

        // コンポーネント値初期化
        initComponentValue(detailsUpdateModel);

        // 「受注修正確認」画面から戻ってきた時に「決済手数料ダイアログ選択フラグ」をリセット
        detailsUpdateModel.setCommissionSelected(false);

        return ResponseEntity.ok("success");
    }

    /**
     * 初期処理<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "")
    @ResponseBody
    public ResponseEntity<?> doLoadIndexAjaxCharge(DetailsUpdateModel detailsUpdateModel,
                                                   @RequestBody OrderAdditionalChargeModel orderAdditionalChargeModel) {
        if (!ObjectUtils.isEmpty(orderAdditionalChargeModel)) {
            detailsUpdateModel.setModifiedReceiveOrder(orderAdditionalChargeModel.getModifiedReceiveOrder());
            detailsUpdateModel.setOriginalReceiveOrder(orderAdditionalChargeModel.getOriginalReceiveOrder());
        }
        return ResponseEntity.ok("success");
    }

    /**
     * 受注詳細修正AJAX呼出<br/>
     *
     * @param deliveryMethodSeq
     * @return お届け時間帯
     */
    @GetMapping(value = "/ajax")
    @ResponseBody
    public ResponseEntity<String> doChangeTimeZone(@RequestParam(required = false) Optional<String> deliveryMethodSeq) {

        // トップ画面アラートから遷移したとき、商品SEQを取得する。
        if (!deliveryMethodSeq.isPresent()) {
            return ResponseEntity.ok("選択不可");
        }
        String requestDeliveryMethodSeq = deliveryMethodSeq.get();
        Integer iDeliveryMethodSeq = 0;
        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        iDeliveryMethodSeq = conversionUtility.toInteger(requestDeliveryMethodSeq);

        DeliveryMethodEntity deliveryMethodEntity = deliveryMethodGetService.execute(iDeliveryMethodSeq);
        if (deliveryMethodEntity == null) {
            return ResponseEntity.ok("選択不可");
        }

        List<String> list = new ArrayList<>();
        if (deliveryMethodEntity.getReceiverTimeZone1() != null) {
            list.add(deliveryMethodEntity.getReceiverTimeZone1());
        }
        if (deliveryMethodEntity.getReceiverTimeZone2() != null) {
            list.add(deliveryMethodEntity.getReceiverTimeZone2());
        }
        if (deliveryMethodEntity.getReceiverTimeZone3() != null) {
            list.add(deliveryMethodEntity.getReceiverTimeZone3());
        }
        if (deliveryMethodEntity.getReceiverTimeZone4() != null) {
            list.add(deliveryMethodEntity.getReceiverTimeZone4());
        }
        if (deliveryMethodEntity.getReceiverTimeZone5() != null) {
            list.add(deliveryMethodEntity.getReceiverTimeZone5());
        }
        if (deliveryMethodEntity.getReceiverTimeZone6() != null) {
            list.add(deliveryMethodEntity.getReceiverTimeZone6());
        }
        if (deliveryMethodEntity.getReceiverTimeZone7() != null) {
            list.add(deliveryMethodEntity.getReceiverTimeZone7());
        }
        if (deliveryMethodEntity.getReceiverTimeZone8() != null) {
            list.add(deliveryMethodEntity.getReceiverTimeZone8());
        }
        if (deliveryMethodEntity.getReceiverTimeZone9() != null) {
            list.add(deliveryMethodEntity.getReceiverTimeZone9());
        }
        if (deliveryMethodEntity.getReceiverTimeZone10() != null) {
            list.add(deliveryMethodEntity.getReceiverTimeZone10());
        }

        StringBuilder strbuff = new StringBuilder(200);
        strbuff.append("<select id=\"receiverTimeZone\" name=\"orderReceiverItem.receiverTimeZone\" value=\"orderReceiverItem.receiverTimeZone\" class=\"c-form-control w160px\">");
        strbuff.append("<option value=\"\">選択してください</option>");

        for (String str : list) {
            strbuff.append("<option value=\"");
            strbuff.append(str);
            strbuff.append("\">");
            strbuff.append(str);
            strbuff.append("</option>");
        }
        strbuff.append("</select>");
        return ResponseEntity.ok(strbuff.toString());
    }

    /**
     * マスタ値変更アラートメッセージ表示<br/>
     * マスタ変更により、受注情報が変更された場合、アラートを表示する<br/>
     *
     * @param orderInfoDiff DTO相違点リスト
     * @param model
     */
    private void createDiffMessage(List<String> orderInfoDiff, RedirectAttributes redirectAttributes, Model model) {

        StringBuilder sb = new StringBuilder();
        for (String a : orderInfoDiff) {
            for (String key : DIFF_ERROR_ITEM_MAP.keySet()) {
                if (a.indexOf(key) >= 0) {
                    String value = DIFF_ERROR_ITEM_MAP.get(key);
                    if (sb.length() > 0) {
                        sb.append(NEW_LINE);
                    }
                    sb.append(INDENT + value);
                    break;
                }
            }
        }

        if (sb.length() > 0) {
            this.addMessage("AOX001021W", new Object[] {sb}, redirectAttributes, model);
        }
        return;
    }

    /**
     * 受注詳細修正確認画面へ遷移<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/detailsupdate")
    public String doConfirm(@Validated(ConfirmGroup.class) DetailsUpdateModel detailsUpdateModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (error.hasErrors()) {
            return "order/details/detailsupdate";
        }

        // 請求決済エラーでGMO連携解除にチェックされていないときは決済方法が変更されているかをチェックする
        // 決済方法が変更されていなければエラー
        if (detailsUpdateModel.getModifiedReceiveOrder().getOrderBillEntity().getEmergencyFlag()
            == HTypeEmergencyFlag.ON && !detailsUpdateModel.isCancelOfCooperation()) {
            String orgSettlementMethod = detailsUpdateModel.getOriginalReceiveOrder()
                                                           .getOrderSummaryEntity()
                                                           .getSettlementMethodSeq()
                                                           .toString();
            if (detailsUpdateModel.getUpdateSettlementMethodSeq().equals(orgSettlementMethod)) {
                throwMessage(MSGCD_SETTLEMENT_METHOD_FAIL);
            }
        }
        // GMO連携解除チェック時処理
        // GMO連携解除フラグを RELEASE 連携解除 に設定する
        // 異常フラグをOFFに設定する
        if (detailsUpdateModel.isCancelOfCooperation()) {
            if (detailsUpdateModel.isCancelOfCooperationView()) {
                detailsUpdateModel.getModifiedReceiveOrder()
                                  .getOrderBillEntity()
                                  .setGmoReleaseFlag(HTypeGmoReleaseFlag.RELEASE);
                detailsUpdateModel.getModifiedReceiveOrder()
                                  .getOrderBillEntity()
                                  .setEmergencyFlag(HTypeEmergencyFlag.OFF);
            }
        }

        boolean result = confirm(false, false, detailsUpdateModel, redirectAttributes, model);
        if (!result) {
            return "order/details/detailsupdate";
        }

        ReceiveOrderDto modified = detailsUpdateModel.getModifiedReceiveOrder();
        // Original order price
        // 初回受注金額を取得している
        HTypeOrderStatus orderStatus =
                        orderUtility.getOrderStatusByOrderDelivery(modified.getOrderSummaryEntity().getOrderStatus(),
                                                                   modified.getOrderDeliveryDto()
                                                                  );

        if (HTypeOrderStatus.SHIPMENT_COMPLETION == orderStatus || HTypeOrderStatus.PART_SHIPMENT == orderStatus) {
            // 出荷済 and 手数料が変わる場合
            if (!modified.getOrderSettlementEntity()
                         .getSettlementCommission()
                         .equals(modified.getOriginalCommission())) {
                // 自画面で手数料確認ダイアログを表示
                detailsUpdateModel.setCommissionDialogDisplay(true);
            }
            // 出荷済 and 送料が変わる場合
            if (!modified.getOrderSettlementEntity().getCarriage().equals(modified.getOriginalCarriage())) {
                detailsUpdateModel.setCarriageDialogDisplay(true);
            }
        }

        // クーポンダイアログ表示確認
        if (checkCouponDialog(modified, detailsUpdateModel)) {
            // クーポンダイアログ表示
            detailsUpdateModel.setCouponCancelDialogDisplay(true);
            detailsUpdateModel.setCouponDiscountPriceDisp(modified.getCoupon().getDiscountPrice());

            // 対象商品がすべてキャンセルされている場合
            if (detailsUpdateModel.isCouponTargetGoodsCancelMessgeFlg()) {
                detailsupdateHelper.setCouponTargetGoodsForJs(detailsUpdateModel, modified.getCoupon());
            } else {
                // クーポン情報を設定
                detailsUpdateModel.setGoodsPriceTotalDisp(modified.getOrderSettlementEntity().getGoodsPriceTotal());
                detailsUpdateModel.setCouponDiscountLowerOrderPriceDisp(
                                modified.getCoupon().getDiscountLowerOrderPrice());
            }
        }

        if (detailsUpdateModel.isCommissionDialogDisplay() || detailsUpdateModel.isCarriageDialogDisplay()
            || detailsUpdateModel.isCouponCancelDialogDisplay()) {
            return "order/details/detailsupdate";
        }

        return "redirect:/order/detailsupdate/confirm";

    }

    /**
     * クーポンダイアログ出力チェック<br/>
     *
     * @param modified 変更後受注DTO
     * @return true..出力 / false..出力しない
     */
    private boolean checkCouponDialog(ReceiveOrderDto modified, DetailsUpdateModel detailsUpdateModel) {

        boolean checkFlg = false;

        if (detailsUpdateModel.getUseCouponFlg().equals("false")) {
            return false;
        }

        // 修正後のクーポン利用状態を取得
        HTypeCouponLimitTargetType modifiedcouponLimitTargetType =
                        modified.getOrderIndexEntity().getCouponLimitTargetType();
        boolean cancelFlg = false;
        CouponEntity couponEntity = detailsUpdateModel.getModifiedReceiveOrder().getCoupon();

        // 修正後のクーポン利用状態が"無効"以外の場合
        if (!HTypeCouponLimitTargetType.OFF.equals(modifiedcouponLimitTargetType)) {
            if (StringUtil.isEmpty(couponEntity.getTargetGoods())) {
                if (checkGoodsGcntAllZero(detailsUpdateModel)) {
                    cancelFlg = true;
                }
            } else {
                if (checkTargetGoodsGcntZero(couponEntity, detailsUpdateModel)) {
                    cancelFlg = true;
                }
            }
            if (cancelFlg) {
                detailsUpdateModel.setCouponTargetGoodsCancelMessgeFlg(true);
                checkFlg = true;
            } else {
                if (checkDiscountLowerOrderPrice(modified.getCoupon(), modified)) {
                    detailsUpdateModel.setCouponDiscountLowerOrderPriceMessgeFlg(true);
                    checkFlg = true;
                }
            }
        }

        return checkFlg;
    }

    /**
     * 商品数量がすべて 0個 か<br/>
     *
     * @return true..全て0個
     */
    private boolean checkGoodsGcntAllZero(DetailsUpdateModel detailsUpdateModel) {

        boolean checkFlg = false;

        for (OrderGoodsUpdateItem orderGoodsUpdateItem : detailsUpdateModel.getOrderReceiverItem()
                                                                           .getOrderGoodsUpdateItems()) {
            if (orderGoodsUpdateItem.getUpdateGoodsCount().equals("0")) {
                checkFlg = true;
            } else {
                return false;
            }
        }
        return checkFlg;
    }

    /**
     * クーポン対象商品数量が 0個か<br/>
     *
     * @param couponEntity クーポンエンティティ
     * @return true..0個
     */
    private boolean checkTargetGoodsGcntZero(CouponEntity couponEntity, DetailsUpdateModel detailsUpdateModel) {

        boolean checkFlg = false;
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        List<String> targetGoodsList = Arrays.asList(conversionUtility.toDivArray(couponEntity.getTargetGoods()));

        for (OrderGoodsUpdateItem orderGoodsUpdateItem : detailsUpdateModel.getOrderReceiverItem()
                                                                           .getOrderGoodsUpdateItems()) {
            if (targetGoodsList.contains(orderGoodsUpdateItem.getGoodsCode())) {
                if (orderGoodsUpdateItem.getUpdateGoodsCount().equals("0")) {
                    checkFlg = true;
                } else {
                    return false;
                }
            }
        }
        return checkFlg;
    }

    /**
     * 手数料確認ダイアログからのリクエスト
     *
     * @return 受注詳細修正確認画面
     */
    @PostMapping(value = "/", params = "doConfirmCommissionDialog")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/detailsupdate")
    public String doConfirmCommissionDialog(DetailsUpdateModel detailsUpdateModel,
                                            BindingResult error,
                                            RedirectAttributes redirectAttributes,
                                            Model model) {
        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        if (detailsUpdateModel.isCarriageDialogDisplay() || detailsUpdateModel.isCouponCancelDialogDisplay()) {
            detailsUpdateModel.setCommissionSelected(true);
            return "order/details/detailsupdate";
        }
        boolean result = confirm(detailsUpdateModel.isCommissionCalcFlag(), detailsUpdateModel.isCarriageCalcFlag(),
                                 detailsUpdateModel, redirectAttributes, model
                                );
        if (!result) {
            return "order/details/detailsupdate";
        }

        // フラグをリセット
        detailsUpdateModel.setCommissionDialogDisplay(false);

        return "redirect:/order/detailsupdate/confirm";
    }

    /**
     * 送料確認ダイアログからのリクエスト
     *
     * @return 受注詳細修正確認画面
     */
    @PostMapping(value = "/", params = "doConfirmCarriageDialog")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/detailsupdate")
    public String doConfirmCarriageDialog(DetailsUpdateModel detailsUpdateModel,
                                          BindingResult error,
                                          RedirectAttributes redirectAttributes,
                                          Model model) {
        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        if (detailsUpdateModel.isCouponCancelDialogDisplay()) {
            detailsUpdateModel.setCarriageSelected(true);
            return "order/details/detailsupdate";
        }
        boolean result = confirm(detailsUpdateModel.isCommissionCalcFlag(), detailsUpdateModel.isCarriageCalcFlag(),
                                 detailsUpdateModel, redirectAttributes, model
                                );
        if (!result) {
            return "order/details/detailsupdate";
        }

        // フラグをリセット
        detailsUpdateModel.setCarriageDialogDisplay(false);

        return "redirect:/order/detailsupdate/confirm";
    }

    /**
     * クーポンキャンセルダイアログからのリクエスト
     *
     * @return 受注詳細修正確認画面
     */
    @PostMapping(value = "/", params = "doConfirmCouponDialog")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/detailsupdate")
    public String doConfirmCouponDialog(DetailsUpdateModel detailsUpdateModel,
                                        BindingResult error,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {
        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        boolean result = confirm(detailsUpdateModel.isCommissionCalcFlag(), detailsUpdateModel.isCarriageCalcFlag(),
                                 detailsUpdateModel, redirectAttributes, model
                                );
        if (!result) {
            return "order/details/detailsupdate";
        }
        return "redirect:/order/detailsupdate/confirm";

    }

    /**
     * 確認ボタン押下時の共通処理
     *
     * @param commissionCalcFlag 決済手数料適用フラグ。true..計算前の手数料を適用する
     * @param carriageCalcFlag   送料適用フラグ。true..計算前の送料を適用する
     * @param model
     * @return true = OK, false = NG
     */
    private boolean confirm(boolean commissionCalcFlag,
                            boolean carriageCalcFlag,
                            DetailsUpdateModel detailsUpdateModel,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        // 都道府県と郵便番号の整合性チェック
        if (checkPrefecture(detailsUpdateModel, redirectAttributes, model)) {
            return false;
        }

        // お届け希望日チェック
        this.checkReceiverDate(detailsUpdateModel);

        detailsupdateHelper.setOriginalReceiverData(detailsUpdateModel);

        detailsupdateHelper.toModifiedReceiveOrderDto(detailsUpdateModel);
        detailsupdateHelper.fixingGoods(detailsUpdateModel);

        ReceiveOrderDto modified = detailsUpdateModel.getModifiedReceiveOrder();
        ReceiveOrderDto original = detailsUpdateModel.getOriginalReceiveOrder();

        // 受注修正チェック
        OrderMessageDto orderMessageDto =
                        receiveOrderCheckLogic.execute(modified, original, true, commissionCalcFlag, carriageCalcFlag,
                                                       null, HTypeSiteType.BACK, null,
                                                       getCommonInfo().getCommonInfoAdministrator().getAdministratorId()
                                                      );
        detailsUpdateModel.setOrderMessageDto(orderMessageDto);

        // エラーがある場合
        if (orderMessageDto.hasErrorMessage()) {
            toPage(detailsUpdateModel, redirectAttributes, model);
            return false;
        }

        return true;
    }

    /**
     * お届け希望日チェック<br/>
     */
    private void checkReceiverDate(DetailsUpdateModel detailsUpdateModel) {
        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        // 注文日時を取得
        Timestamp orderDate = detailsUpdateModel.getOriginalReceiveOrder().getOrderSummaryEntity().getOrderTime();
        // 入力されたお届け希望日を取得
        String orderHms = dateUtility.format(orderDate, DateUtility.HMS);

        if (!StringUtil.isEmpty(detailsUpdateModel.getOrderReceiverItem().getUpdateReceiverDate())) {
            Timestamp inputDate = conversionUtility.toTimeStamp(
                            detailsUpdateModel.getOrderReceiverItem().getUpdateReceiverDate(), orderHms);
            // 注文日より過去の日付を入力された場合エラー
            if (inputDate.compareTo(orderDate) < 0) {
                throwMessage("AOX001020");
            }
        }
    }

    /**
     * 受注キャンセル実行アクション<br/>
     *
     * @return 受注詳細ページ
     */
    // @TakeOver(type = TakeOverType.EXCLUDE, properties =
    // "originalReceiveOrder,modifiedReceiveOrder")
    @PostMapping(value = "/", params = "doOnceOrderCancel")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/detailsupdate")
    public String doOnceOrderCancel(@Validated(OnceOrderCancelGroup.class) DetailsUpdateModel detailsUpdateModel,
                                    BindingResult error,
                                    RedirectAttributes redirectAttributes,
                                    SessionStatus sessionStatus,
                                    Model model) {
        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (error.hasErrors()) {
            return "order/details/detailsupdate";
        }

        // 請求情報の不整合チェック処理の初期処理
        String orderCode = detailsUpdateModel.getOrderCode();
        settlememtMismatchCheckLogic.initOrderCancel(orderCode);
        try {
            HTypeCouponLimitTargetType couponLimitTargetType = null;
            if (StringUtils.isEmpty(detailsUpdateModel.getCouponLimitTargetTypeValue())) {
                couponLimitTargetType = detailsUpdateModel.getCouponLimitTargetType();
            } else {
                couponLimitTargetType = EnumTypeUtil.getEnumFromValue(HTypeCouponLimitTargetType.class,
                                                                      detailsUpdateModel.getCouponLimitTargetTypeValue()
                                                                     );
            }

            // 共通情報ヘルパークラスを取得
            CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);

            // 受注キャンセルサービス実行
            receiveOrderCancelService.execute(orderCode, detailsUpdateModel.getOrderVersionNo(),
                                              detailsUpdateModel.getMemo(), couponLimitTargetType,
                                              commonInfoUtility.getAdministratorName(getCommonInfo())
                                             );

            // 請求情報の不整合チェック
            settlememtMismatchCheckLogic.execute();

            redirectAttributes.addFlashAttribute(DetailsUpdateModel.FLASH_ORDERCODE, detailsUpdateModel.getOrderCode());
            // Modelをセッションより破棄
            sessionStatus.setComplete();
            return "redirect:/order/details/";

        } catch (Throwable th) {
            LOGGER.error("例外処理が発生しました", th);
            // 請求情報の不整合チェック
            settlememtMismatchCheckLogic.execute(th);
            throw th;
        }
    }

    /**
     * 商品追加画面遷移<br/>
     *
     * @return 受注修正商品検索ページ
     */
    @PostMapping(value = "/", params = "doOrderGoodsModify")
    public String doOrderGoodsModify(@Validated(OrderGoodsModifyGroup.class) DetailsUpdateModel detailsUpdateModel,
                                     BindingResult error,
                                     RedirectAttributes redirectAttrs,
                                     Model model) {
        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttrs, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (error.hasErrors()) {
            return "order/details/detailsupdate";
        }

        detailsupdateHelper.toModifiedReceiveOrderDto(detailsUpdateModel);
        redirectAttrs.addFlashAttribute(DetailsUpdateModel.FLASH_UPDATE_MODEL, detailsUpdateModel);
        return "redirect:/order/details/goodssearch/";
    }

    /**
     * 商品追加画面遷移<br/>
     *
     * @return 受注修正商品検索ページ
     */
    @PostMapping(value = "doOrderGoodsModifyAjax")
    public ResponseEntity<?> doOrderGoodsModifyAjax(
                    @Validated(OrderGoodsModifyGroup.class) DetailsUpdateModel detailsUpdateModel,
                    BindingResult error,
                    RedirectAttributes redirectAttrs,
                    Model model) {
        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttrs, model);
        if (StringUtils.isNotEmpty(check)) {
            ResponseEntity.badRequest().body(check);
        }

        if (error.hasErrors()) {
            List<ValidatorMessage> mapError = MessageUtils.getMessageErrorFromBindingResult(error);
            return ResponseEntity.badRequest().body(mapError);
        }

        detailsupdateHelper.toModifiedReceiveOrderDto(detailsUpdateModel);

        return ResponseEntity.ok(detailsUpdateModel);
    }

    /**
     * 追加料金画面遷移<br/>
     *
     * @return その他追加料金ページ
     */
    @PostMapping(value = "/", params = "doAdditionalCharge")
    public String doAdditionalCharge(@Validated(AdditionalChargeGroup.class) DetailsUpdateModel detailsUpdateModel,
                                     BindingResult error,
                                     RedirectAttributes redirectAttrs,
                                     Model model) {
        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttrs, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (error.hasErrors()) {
            return "order/details/detailsupdate";
        }

        detailsupdateHelper.toModifiedReceiveOrderDto(detailsUpdateModel);
        redirectAttrs.addFlashAttribute(DetailsUpdateModel.FLASH_UPDATE_MODEL, detailsUpdateModel);
        return "redirect:/order/details/additionalcharge/";
    }

    /**
     * 追加料金画面遷移<br/>
     *
     * @return その他追加料金ページ
     */
    @PostMapping(value = "/doAdditionalChargeAjax")
    @ResponseBody
    public ResponseEntity<?> doAdditionalChargeAjax(DetailsUpdateModel detailsUpdateModel) {
        // 実行前処理
        List<ValidatorMessage> check = illegalOperationCheckAjax(false, detailsUpdateModel);
        if (!CollectionUtils.isEmpty(check)) {
            return ResponseEntity.badRequest().body(check);
        }
        detailsupdateHelper.toModifiedReceiveOrderDto(detailsUpdateModel);
        return ResponseEntity.ok(detailsUpdateModel);
    }

    /**
     * 商品削除アクション<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doOrderGoodsDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/detailsupdate")
    public String doOrderGoodsDelete(@Validated(OrderGoodsDeleteGroup.class) DetailsUpdateModel detailsUpdateModel,
                                     BindingResult error,
                                     Model model,
                                     RedirectAttributes redirectAttrs) {

        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttrs, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (error.hasErrors()) {
            return "order/details/detailsupdate";
        }

        // 商品が未選択の場合はエラーメッセージを出力
        if (!isGoodsSelect(detailsUpdateModel)) {
            throwMessage(MSGCD_DELETE_FAIL);
        }

        // 対象商品の数量0更新
        detailsupdateHelper.toModifiedReceiveOrderDto(detailsUpdateModel);
        detailsupdateHelper.deleteGoods(detailsUpdateModel);
        this.toDisplay(detailsUpdateModel, redirectAttrs, model);
        return "order/details/detailsupdate";
    }

    /**
     * 再計算処理<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doReCalculate")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/detailsupdate")
    public String doReCalculate(@Validated(ReCalculateGroup.class) DetailsUpdateModel detailsUpdateModel,
                                BindingResult error,
                                Model model,
                                RedirectAttributes redirectAttrs) {
        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttrs, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (error.hasErrors()) {
            return "order/details/detailsupdate";
        }

        detailsupdateHelper.toModifiedReceiveOrderDto(detailsUpdateModel);
        this.toDisplay(detailsUpdateModel, redirectAttrs, model);
        return "order/details/detailsupdate";
    }

    /**
     * 受注詳細修正画面反映(受注可能チェック付)<br/>
     *
     * @param detailsUpdateModel
     * @param redirectAttrs
     * @param model
     */
    private void toDisplay(DetailsUpdateModel detailsUpdateModel, RedirectAttributes redirectAttrs, Model model) {
        ReceiveOrderDto original = detailsUpdateModel.getOriginalReceiveOrder();
        ReceiveOrderDto modified = detailsUpdateModel.getModifiedReceiveOrder();
        // 受注修正チェック
        OrderMessageDto orderMessageDto =
                        receiveOrderCheckLogic.execute(modified, original, true, false, false, null, HTypeSiteType.BACK,
                                                       null,
                                                       getCommonInfo().getCommonInfoAdministrator().getAdministratorId()
                                                      );
        detailsUpdateModel.setOrderMessageDto(orderMessageDto);

        toPage(detailsUpdateModel, redirectAttrs, model);
    }

    /**
     * クーポン利用状況変更処理<br/>
     *
     * @param detailsUpdateModel
     * @param redirectAttrs
     * @param model
     * @return
     */
    @PostMapping(value = "/", params = "doCouponLimitTarget")
    public String doCouponLimitTarget(DetailsUpdateModel detailsUpdateModel,
                                      RedirectAttributes redirectAttrs,
                                      Model model) {
        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttrs, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        detailsupdateHelper.toModifiedCouponLimitTarget(detailsUpdateModel);
        this.toDisplay(detailsUpdateModel, redirectAttrs, model);
        return "order/details/detailsupdate";
    }

    /**
     * 受注詳細修正画面反映<br/>
     *
     * @param detailsUpdateModel
     * @param redirectAttrs
     * @param model
     */
    private void toPage(DetailsUpdateModel detailsUpdateModel, RedirectAttributes redirectAttrs, Model model) {

        ReceiveOrderDto modified = detailsUpdateModel.getModifiedReceiveOrder();
        OrderMessageDto orderMessageDto = detailsUpdateModel.getOrderMessageDto();

        // エラーチェック
        boolean settlementerror = false;
        boolean deliveryerror = false;
        List<CheckMessageDto> cmDtoList = orderMessageDto.getOrderMessageList();
        if (cmDtoList != null && !cmDtoList.isEmpty()) {
            for (CheckMessageDto cmDto : cmDtoList) {
                // 配送方法・決済方法選択不可能エラーが存在するかチェック
                if (AbstractOrderCheckLogic.MSGCD_SELECT_SETTLEMENTMETHOD_ZERO.equals(cmDto.getMessageId())) {
                    settlementerror = true;
                } else if (AbstractOrderCheckLogic.MSGCD_SELECT_DELIVERYMETHOD_ZERO.equals(cmDto.getMessageId())
                           || BillPriceCalculateLogic.MSGCD_PREFECTURE_ERROR.equals(cmDto.getMessageId())) {
                    deliveryerror = true;
                }
            }
        }

        // 受注詳細情報ページ反映
        detailsupdateHelper.toPage(detailsUpdateModel, modified);

        // 受注キャンセル、確認ボタンの非活性にするか判定
        // →PKG標準では常に活性（案件カスタマイズで条件判定が必要な場合に修正すること）
        detailsUpdateModel.setOrderCancelModifyPossible(true);

        boolean senderSkipFlag = PropertiesUtil.getSystemPropertiesValueToBool("amazon.sender.skip");
        if (HTypeSettlementMethodType.AMAZON_PAYMENT == detailsUpdateModel.getOriginalReceiveOrder()
                                                                          .getSettlementMethodEntity()
                                                                          .getSettlementMethodType()
            && senderSkipFlag) {
            detailsUpdateModel.setDisplayMemberInfo(false);
        } else {
            detailsUpdateModel.setDisplayMemberInfo(true);
        }

        // 配送可能を取得
        OrderReceiverUpdateItem receiverItem = detailsUpdateModel.getOrderReceiverItem();

        if (!deliveryerror) {
            try {
                List<DeliveryDto> deliveryDtoList = deliveryMethodListGetService.execute(modified);
                // 配送方法リストセット
                detailsupdateHelper.toDeliveryList(detailsUpdateModel, deliveryDtoList);
            } catch (EmptyRuntimeException e) {
                // 処理なし⇒リスト空もしくは元の配送方法リストを利用
                LOGGER.error("例外処理が発生しました", e);
            }
        } else {
            detailsupdateHelper.toDeliveryList(detailsUpdateModel, new ArrayList<DeliveryDto>());
        }
        // お届け時間帯チェック用設定
        Map<Object, Object> deliveryTimeZoneList = new HashMap<>();
        Integer deliveryMethodSeq = Integer.valueOf(receiverItem.getUpdateDeliveryMethodSeq());
        DeliveryMethodEntity deliveryEntity = deliveryMethodGetService.execute(deliveryMethodSeq);
        detailsupdateHelper.toTimeZoneDeleveryList(deliveryTimeZoneList, deliveryEntity, deliveryMethodSeq);
        receiverItem.setDeliveryTimeZoneList(deliveryTimeZoneList);

        // お届け希望時間帯リスト設定
        DeliveryMethodEntity deliveryMethodEntity =
                        deliveryMethodGetService.execute(receiverItem.getDeliveryMethodSeq());
        detailsupdateHelper.setTimeZoneItem(detailsUpdateModel, deliveryMethodEntity);

        if (!settlementerror) {
            // 決済可能リスト取得
            List<SettlementDto> settlementDtoList =
                            settlementMethodSelectListGetService.execute(modified, true, null, HTypeSiteType.BACK);
            // 決済方法リストセット
            detailsupdateHelper.toSettlementList(detailsUpdateModel, settlementDtoList);
        }
        if (modified.getMulPayBillEntity() == null
            && detailsUpdateModel.getOriginalReceiveOrder().getMulPayBillEntity() != null) {
            // 編集前＝マルチペイ決済、編集後＝非マルチペイ決済で確認画面から戻ってきたとき用
            detailsupdateHelper.toPage(
                            detailsUpdateModel, detailsUpdateModel.getOriginalReceiveOrder().getMulPayBillEntity());
        }
        if (modified.getOrderSettlementEntity().getSettlementMethodSeq() != null) {
            detailsUpdateModel.setUpdateSettlementMethodSeq(
                            modified.getOrderSettlementEntity().getSettlementMethodSeq().toString());
        }

        // エラーメッセージを表示
        if (cmDtoList != null && !cmDtoList.isEmpty()) {
            for (CheckMessageDto cmDto : cmDtoList) {
                this.addMessage(cmDto.getMessageId(), cmDto.getArgs(), redirectAttrs, model);
            }
        }
        // 商品系エラーの確認
        if (orderMessageDto.hasGoodsErrorMessage()) {
            this.addMessage("AOX001001", redirectAttrs, model);
        }
        // 商品点数チェック
        if (detailsUpdateModel.getOrderGoodsCountTotal().equals(BigDecimal.ZERO)) {
            this.addMessage("AOX001009", redirectAttrs, model);
        }
        if (!HTypeCouponLimitTargetType.OFF.getValue().equals(detailsUpdateModel.getCouponLimitTargetTypeValue())) {
            // クーポン適用金額チェック
            if (checkDiscountLowerOrderPrice(modified.getCoupon(), modified)) {
                this.addMessage(MSGCD_NOTFULL_COUPONDISCOUNTCONDITION, redirectAttrs, model);
            }
        }

    }

    /**
     * <pre>
     * 郵便番号検索ボタンのIDに連番を付ける為に定義
     * 連番は通常ボタンとAjax用ボタンの切り替えに使用
     * </pre>
     *
     * @return 自画面
     */
    @PostMapping(value = "/", params = "doZipCodeSearchToReceiverAjax")
    public String doZipCodeSearchToReceiverAjax(DetailsUpdateModel detailsUpdateModel,
                                                RedirectAttributes redirectAttributes,
                                                Model model) {
        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        return "order/details/detailsupdate";
    }

    /**
     * アクション実行前処理
     *
     * @param detailsUpdateModel
     * @param redirectAttributes
     */
    public String preDoAction(DetailsUpdateModel detailsUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        // 不正操作チェック
        return illegalOperationCheck(false, detailsUpdateModel, redirectAttributes, model);
    }

    /**
     * 不正操作チェック
     *
     * @param isLoadAction ロードアクションフラグ
     */
    private String illegalOperationCheck(boolean isLoadAction,
                                         DetailsUpdateModel detailsUpdateModel,
                                         RedirectAttributes redirectAttributes,
                                         Model model) {
        String orderCode = detailsUpdateModel.getOrderCode();
        if (orderCode == null) {
            addMessage(MSGCD_PARAM_ERROR, redirectAttributes, model);
            return "redirect:/error";
        }

        if (!isLoadAction) {
            ReceiveOrderDto modified = detailsUpdateModel.getModifiedReceiveOrder();
            if (modified == null || !orderCode.equals(modified.getOrderSummaryEntity().getOrderCode())) {
                redirectAttributes.addFlashAttribute(DetailsUpdateModel.FLASH_MD, MODE_LIST);
                addMessage(MSGCD_PARAM_ERROR, redirectAttributes, model);
                return "redirect:/order/";
            }
            if (detailsUpdateModel.isHistoryDetailsFlag()) {
                redirectAttributes.addFlashAttribute(
                                DetailsUpdateModel.FLASH_ORDERCODE, detailsUpdateModel.getOrderCode());
                addMessage(MSGCD_PARAM_ERROR, redirectAttributes, model);
                return "redirect:/order/details/";
            }
        }
        return "";
    }

    /**
     * 不正操作チェック
     *
     * @param isLoadAction ロードアクションフラグ
     */
    private List<ValidatorMessage> illegalOperationCheckAjax(boolean isLoadAction,
                                                             DetailsUpdateModel detailsUpdateModel) {
        List<ValidatorMessage> list = new ArrayList<>();
        String orderCode = detailsUpdateModel.getOrderCode();
        if (orderCode == null) {
            MessageUtils.getAllMessage(list, MSGCD_PARAM_ERROR, null);
            return list;
        }

        if (!isLoadAction) {
            ReceiveOrderDto modified = detailsUpdateModel.getModifiedReceiveOrder();
            if (modified == null || !orderCode.equals(modified.getOrderSummaryEntity().getOrderCode())) {
                MessageUtils.getAllMessage(list, MSGCD_PARAM_ERROR, null);
                return list;
            }
            if (detailsUpdateModel.isHistoryDetailsFlag()) {
                MessageUtils.getAllMessage(list, MSGCD_PARAM_ERROR, null);
                return list;
            }
        }
        return list;
    }

    /**
     * 都道府県の入力チェック<br/>
     *
     * @param detailsUpdateModel
     * @param redirectAttrs
     * @param model
     * @return true : エラーあり false：エラーなし
     */
    private boolean checkPrefecture(DetailsUpdateModel detailsUpdateModel,
                                    RedirectAttributes redirectAttrs,
                                    Model model) {
        boolean errorFlg = false;

        // 郵便番号住所情報取得サービス実行
        boolean ordererResultFlag = zipCodeMatchingCheckService.execute(detailsUpdateModel.getOrderZipCode(),
                                                                        detailsUpdateModel.getOrderPrefecture()
                                                                       );
        if (!ordererResultFlag) {
            String[] arg = {"お客様情報"};
            addMessage(MSGCD_PREFECTURE_CONSISTENCY, arg, redirectAttrs, model);
            errorFlg = true;
        }

        OrderReceiverUpdateItem orderReceiverUpdateItem = detailsUpdateModel.getOrderReceiverItem();
        // 郵便番号住所情報取得サービス実行
        boolean receiverResultFlag = zipCodeMatchingCheckService.execute(orderReceiverUpdateItem.getReceiverZipCode(),
                                                                         orderReceiverUpdateItem.getReceiverPrefecture()
                                                                        );
        if (!receiverResultFlag) {
            String[] arg = {"お届け先情報"};
            addMessage(MSGCD_PREFECTURE_CONSISTENCY, arg, redirectAttrs, model);
            errorFlg = true;
        }
        return errorFlg;

    }

    /**
     * 商品選択チェック<br/>
     *
     * @return true:選択あり false:選択なし
     */
    private boolean isGoodsSelect(DetailsUpdateModel detailsUpdateModel) {

        List<OrderGoodsUpdateItem> goodsItems = detailsUpdateModel.getOrderReceiverItem().getOrderGoodsUpdateItems();
        for (Iterator<OrderGoodsUpdateItem> goodsIte = goodsItems.iterator(); goodsIte.hasNext(); ) {
            OrderGoodsUpdateItem goodsItem = goodsIte.next();
            if (goodsItem.isGoodsCheck()) {
                return true;
            }
        }
        return false;
    }

    /**
     * コンビニプルダウン作成
     */
    private void createConvenienceSelect(DetailsUpdateModel detailsUpdateModel) {
        detailsUpdateModel.setConvenienceAllList(convenienceLogic.getConvenienceList());
        Map<String, String> convenienceMap = new LinkedHashMap<>();
        for (ConvenienceEntity convenience : detailsUpdateModel.getConvenienceAllList()) {
            if (HTypeUseConveni.ON == convenience.getUseFlag()) {
                convenienceMap.put(convenience.getConveniCode().toString(), orderUtility.getConveniName(convenience));
            }
        }
        detailsUpdateModel.setConvenienceItems(convenienceMap);
    }

    /**
     * 割引対象金額がクーポンの適用金額を満たしているかをチェックする。<br />
     *
     * <pre>
     * クーポン適用金額に割引対象金額が満たなかった場合エラー。
     * </pre>
     *
     * @param coupon          クーポンエンティティ
     * @param receiveOrderDto 受注DTO
     * @return 判定結果
     */
    private boolean checkDiscountLowerOrderPrice(CouponEntity coupon, ReceiveOrderDto receiveOrderDto) {

        if (coupon == null) {
            return false;
        }
        // 割引対象金額を取得するを取得する
        BigDecimal targetPrice = receiveOrderDto.getOrderSettlementEntity().getGoodsPriceTotal();

        // クーポン適用金額を取得する
        BigDecimal discountLowerOrderPrice = coupon.getDiscountLowerOrderPrice();

        // クーポン適用金額に割引対象金額が満たなかった場合エラーとする
        if (targetPrice.compareTo(discountLowerOrderPrice) < 0) {
            return true;
        }
        return false;
    }

    /**
     * コンポーネント値初期化
     *
     * @param detailsUpdateModel
     */
    private void initComponentValue(DetailsUpdateModel detailsUpdateModel) {
        detailsUpdateModel.setUpdateInvoiceAttachmentFlagItems(
                        EnumTypeUtil.getEnumMap(HTypeInvoiceAttachmentFlag.class));
        detailsUpdateModel.setOrderPrefectureItems(EnumTypeUtil.getEnumMap(HTypePrefectureType.class));
        detailsUpdateModel.setReceiverPrefectureItems(EnumTypeUtil.getEnumMap(HTypePrefectureType.class));
        detailsUpdateModel.setUpdateOrderSexItems(EnumTypeUtil.getEnumMap(HTypeOrderSex.class));
    }

    /**********************************************************************************
     * 受注修正確認
     **********************************************************************************/

    /**
     * 受注確認画面表示
     *
     * @param detailsUpdateModel
     * @param error
     * @param redirectAttrs
     * @param model
     * @return
     */
    @GetMapping(value = "/confirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/confirm")
    public String doLoadConfirm(DetailsUpdateModel detailsUpdateModel,
                                BindingResult error,
                                RedirectAttributes redirectAttrs,
                                Model model) {

        // ブラウザバックの場合、処理しない
        if (detailsUpdateModel.getModifiedReceiveOrder() == null) {
            return "redirect:/error";
        }

        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttrs, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 直アクセスチェック
        checkDirectAccecc(detailsUpdateModel, redirectAttrs, model);

        // 不正操作チェック
        illegalOperationCheck(detailsUpdateModel);

        if (hasOrderError(detailsUpdateModel)) {
            return "redirect:/order/detailsupdate/?from=confirm";
        }

        ReceiveOrderDto modified = detailsUpdateModel.getModifiedReceiveOrder();

        // コンビニ名称リストをセット
        createConvenienceSelectConfirm(detailsUpdateModel);

        detailsupdateHelper.toPage(detailsUpdateModel, modified);

        boolean senderSkipFlag = PropertiesUtil.getSystemPropertiesValueToBool("amazon.sender.skip");
        if (HTypeSettlementMethodType.AMAZON_PAYMENT == detailsUpdateModel.getOriginalReceiveOrder()
                                                                          .getSettlementMethodEntity()
                                                                          .getSettlementMethodType()
            && senderSkipFlag) {
            detailsUpdateModel.setDisplayMemberInfo(false);
        } else {
            detailsUpdateModel.setDisplayMemberInfo(true);
        }

        // 商品点数チェック
        if (detailsUpdateModel.getOrderGoodsCountTotal().equals(BigDecimal.ZERO)) {
            addMessage("AOX001301", redirectAttrs, model);
        }

        // お届け希望日チェック
        if (modified.getOrderDeliveryDto().getDeliveryMethodEntity().getPossibleSelectDays() == 0) {
            if (modified.getOrderDeliveryDto().getOrderDeliveryEntity().getReceiverDate() != null) {
                // 配送方法がお届け希望日選択不可で、お届け希望日を指定している場合はアラート表示
                this.addMessage("AOX001302", new Object[] {modified.getOrderDeliveryDto()
                                                                   .getOrderDeliveryEntity().getOrderConsecutiveNo()},
                                redirectAttrs, model
                               );
            }
        }

        // ※1 督促/期限切れメール送信フラグを退避
        // 受注修正時は決済マスタの督促/期限切れメールの値は参照しない

        detailsupdateHelper.setDiff(detailsUpdateModel);

        // ※2 督促/期限切れメール送信フラグ退避の復元
        // 受注修正時は決済マスタの督促/期限切れメールの値は参照しない

        // 同一商品内に複数の税率が混在するかチェック
        List<CheckMessageDto> checkMessageDtoList = orderGoodsMixedTaxCheckLogic.checkOrderGoodsMixedTax(modified,
                                                                                                         OrderDetailsController.MSGCD_MULTIPLE_TAX_RATE_IN_SINGLE_GOODS
                                                                                                        );
        // 複数の税率が混在する場合、警告を表示
        for (CheckMessageDto errorList : checkMessageDtoList) {
            addInfoMessage(errorList.getMessageId(), errorList.getArgs(), redirectAttrs, model);
        }

        return "order/details/confirm";
    }

    /**
     * 「キャンセル」ボタン押下時の処理<br/>
     *
     * @param detailsUpdateModel
     * @param error
     * @param redirectAttributes
     * @param model
     * @return 受注詳細修正ページ
     */
    @PostMapping(value = "/confirm/", params = "doCancel")
    public String doCancelConfirm(DetailsUpdateModel detailsUpdateModel,
                                  BindingResult error,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        detailsUpdateModel.getModifiedReceiveOrder()
                          .getOrderBillEntity()
                          .setEmergencyFlag(detailsUpdateModel.getOriginalReceiveOrder()
                                                              .getOrderBillEntity()
                                                              .getEmergencyFlag());
        redirectAttributes.addFlashAttribute(FLASH_FROM_CONFIRM, true);

        // 受注詳細修正ページへ遷移
        return "redirect:/order/detailsupdate/";
    }

    /**
     * 修正内容を反映する<br/>
     * 修正処理実行
     *
     * @param detailsUpdateModel
     * @param redirectAttributes
     * @param sessionStatus
     * @param model
     * @return 受注詳細ページ
     */
    @PostMapping(value = "/confirm/", params = "doOnceUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "order/details/confirm")
    public String doOnceUpdate(DetailsUpdateModel detailsUpdateModel,
                               RedirectAttributes redirectAttributes,
                               SessionStatus sessionStatus,
                               Model model) {

        // 実行前処理
        String check = preDoAction(detailsUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 直アクセスチェック
        checkDirectAccecc(detailsUpdateModel, redirectAttributes, model);

        // 不正操作チェック
        illegalOperationCheck(detailsUpdateModel);

        // 督促等メール送信フラグの設定
        // 受注修正時は決済マスタの督促/期限切れメールの値は参照しない

        // 購入可能チェック
        checkOrderError(detailsUpdateModel, redirectAttributes, model);

        // 請求情報の不整合チェック処理の初期処理
        ReceiveOrderDto receiveOrderDto = detailsUpdateModel.getModifiedReceiveOrder();
        settlememtMismatchCheckLogic.initOrderUpdate(receiveOrderDto.getOrderSummaryEntity().getOrderCode());

        try {
            // 受注修正処理
            receiveOrderUpdateLogic.execute(receiveOrderDto, null, HTypeSiteType.BACK, null, null, null,
                                            getCommonInfo().getCommonInfoAdministrator().getAdministratorLastName(),
                                            getCommonInfo().getCommonInfoAdministrator().getAdministratorFirstName()
                                           );
            // 再検索フラグをセット
            redirectAttributes.addFlashAttribute(DetailsUpdateModel.FLASH_MD, DetailsUpdateController.MODE_LIST);
            // 請求情報の不整合チェック
            settlememtMismatchCheckLogic.execute();

            // Modelをセッションより破棄
            sessionStatus.setComplete();

            // 2009/10/23 戻り先ページを受注詳細へ変更
            redirectAttributes.addFlashAttribute(DetailsUpdateModel.FLASH_ORDERCODE, detailsUpdateModel.getOrderCode());
            return "redirect:/order/details/";

        } catch (Throwable th) {
            LOGGER.error("例外処理が発生しました", th);
            // 請求情報の不整合チェック
            settlememtMismatchCheckLogic.execute(th);
            if (th instanceof AppLevelListException) {
                List<AppLevelException> errorList = ((AppLevelListException) th).getErrorList();
                for (AppLevelException ae : errorList) {
                    addErrorMessage(ae.getMessageCode(), ae.getArgs(), ae);
                }
                throwMessage();
            }

            throw th;
        }

    }

    /**
     * コンビニプルダウン作成
     *
     * @param detailsUpdateModel
     */
    private void createConvenienceSelectConfirm(DetailsUpdateModel detailsUpdateModel) {
        detailsUpdateModel.setConvenienceAllList(convenienceLogic.getConvenienceList());
        Map<String, String> convenienceMap = new LinkedHashMap<>();
        for (ConvenienceEntity convenience : detailsUpdateModel.getConvenienceAllList()) {
            if (HTypeUseConveni.ON == convenience.getUseFlag()) {
                convenienceMap.put(convenience.getConveniCode(), orderUtility.getConveniName(convenience));
            }
        }
        detailsUpdateModel.setConvenienceItemsConfirm(UIComponentUtil.getItemList(convenienceMap));
    }

    /**
     * 注文チェックを行い、エラーが含まれているかをチェック
     *
     * @param detailsUpdateModel
     * @return true...エラーあり / false...エラーなし
     */
    private boolean hasOrderError(DetailsUpdateModel detailsUpdateModel) {
        // #2200 START
        // setOrderMessageDto();
        // ロード時は受注金額の再計算を行う
        setOrderMessageDto(detailsUpdateModel, true);
        // #2200 END
        return hasErrorMessage();
    }

    /**
     * 不正操作チェック
     *
     * @param detailsUpdateModel
     */
    private String illegalOperationCheck(DetailsUpdateModel detailsUpdateModel) {
        if (detailsUpdateModel.isHistoryDetailsFlag()) {
            throwMessage(DetailsUpdateController.MSGCD_PARAM_ERROR);
            return "redirect:/order/details/";
        }
        return "";
    }

    /**
     * 注文チェックを行い、エラーの場合はExceptionをthrowする
     *
     * @param detailsUpdateModel
     * @param redirectAttributes
     * @param model
     */
    private void checkOrderError(DetailsUpdateModel detailsUpdateModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        // #2200 START
        // setOrderMessageDto();
        // 確定時は受注金額の再計算を行わない
        // 確認画面で見せた金額で登録する必要があるため
        setOrderMessageDto(detailsUpdateModel, false);
        // #2200 END
        checkOrderMessageDto(detailsUpdateModel, redirectAttributes, model);
    }

    /**
     * 不正アクセスエラーチェック
     *
     * @param detailsUpdateModel
     * @return
     */
    private String checkDirectAccecc(DetailsUpdateModel detailsUpdateModel,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        ReceiveOrderDto original = detailsUpdateModel.getOriginalReceiveOrder();
        ReceiveOrderDto modified = detailsUpdateModel.getModifiedReceiveOrder();
        if (modified == null || original == null) {
            addMessage("AOX001007", redirectAttributes, model);
            return "redirect:/error";
        }
        return "";
    }

    /**
     * 購入可能チェックを行い、結果の注文チェックメッセージDTOをPageクラスへセットする
     *
     * @param detailsUpdateModel
     * @param calculateFlag      計算フラグ
     */
    private void setOrderMessageDto(DetailsUpdateModel detailsUpdateModel, boolean calculateFlag) {
        ReceiveOrderDto original = detailsUpdateModel.getOriginalReceiveOrder();
        ReceiveOrderDto modified = detailsUpdateModel.getModifiedReceiveOrder();
        // #2200 START
        // OrderMessageDto orderMessageDto =
        // receiveOrderCheckLogic.execute(modified, original);
        OrderMessageDto orderMessageDto = receiveOrderCheckLogic.execute(modified, original, calculateFlag,
                                                                         detailsUpdateModel.isCommissionCalcFlag(),
                                                                         detailsUpdateModel.isCarriageCalcFlag(), null,
                                                                         HTypeSiteType.BACK, null,
                                                                         getCommonInfo().getCommonInfoAdministrator()
                                                                                        .getAdministratorId()
                                                                        );
        // #2200 END

        detailsUpdateModel.setOrderMessageDto(orderMessageDto);
    }

    /**
     * Pageクラスにセットされている注文チェックメッセージDTOをチェック
     * エラーが含まれている場合はエラーをthrowする。
     *
     * @param detailsUpdateModel
     * @param redirectAttributes
     */
    private void checkOrderMessageDto(DetailsUpdateModel detailsUpdateModel,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {
        OrderMessageDto orderMessageDto = detailsUpdateModel.getOrderMessageDto();
        if (orderMessageDto == null || !orderMessageDto.hasErrorMessage()) {
            return;
        }

        List<CheckMessageDto> cmDtoList = orderMessageDto.getOrderMessageList();
        // エラーメッセージを表示
        if (cmDtoList != null && !cmDtoList.isEmpty()) {
            for (CheckMessageDto cmDto : cmDtoList) {
                addMessage(cmDto.getMessageId(), cmDto.getArgs(), redirectAttributes, model);
            }
            if (hasErrorMessage()) {
                throwMessage();
            }
        }
        // 商品系エラーの確認
        if (orderMessageDto.hasGoodsErrorMessage()) {
            throwMessage("AOX001001");
        }
    }

}
