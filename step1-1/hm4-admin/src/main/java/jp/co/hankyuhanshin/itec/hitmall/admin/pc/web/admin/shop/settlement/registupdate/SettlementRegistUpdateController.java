package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.settlement.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.settlement.registupdate.validation.SettlementRegistUpdateValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailRequired;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodCommissionType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodPriceCommissionFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementMethodDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.SettlementMethodConfigGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.SettlementMethodConfigurationCheckService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.SettlementMethodRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement.SettlementMethodUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hitmall.utility.CouponUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SettlementRegistUpdateController Class
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/settlement")
@Controller
@SessionAttributes(value = "settlementRegistUpdateModel")
@PreAuthorize("hasAnyAuthority('SETTING:8')")
public class SettlementRegistUpdateController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SettlementRegistUpdateController.class);

    /**
     * メッセージコード_最大支払期限猶予日数超過
     */
    protected static final String MSG_CD_PAY_LIMIT_ERR = "AYC000214";
    /**
     * メッセージコード_不正アクセス
     */
    protected static final String MSG_CD_ILLEGAL = "AYC000215";
    /**
     * メッセージコード_利用可能最大金額超過
     */
    protected static final String MSG_CD_EXCEED_MAX_PURCHASED_PRICE = "AYC000216";
    /**
     * 決済方法と支払期限猶予日数のプロパティキーのマップ（支払期限のチェックで利用）
     */
    protected static final Map<String, String[]> PAYMENT_TIME_LIMIT_DAY_COUNT_MAP = new HashMap<>();
    /**
     * 決済方法と利用可能最大金額のプロパティキーのマップ（利用可能最大金額のチェックで利用）
     */
    protected static final Map<String, String[]> MAX_PURCHASED_PRICE_MAP = new HashMap<>();

    static {
        /*
         * ↓は支払期限のチェックで利用する。マップには、決済タイプの値をキー、String配列を値として設定する。
         * String配列には、左から、決済タイプラベル、支払期限猶予日数のプロパティキー、を設定する。
         * 支払期限猶予日数のプロパティキーはバックのシステムプロパティファイルに記述している。
         * 今後、支払期限のチェックを行う決済方法が増える場合には、当マップに設定を追加すること。
         */
        PAYMENT_TIME_LIMIT_DAY_COUNT_MAP.put(HTypeSettlementMethodType.CONVENIENCE.getValue(),
                                             new String[] {HTypeSettlementMethodType.CONVENIENCE.getLabel(),
                                                             "settlement.payment.term.day.convenience"}
                                            );
        PAYMENT_TIME_LIMIT_DAY_COUNT_MAP.put(HTypeSettlementMethodType.PAY_EASY.getValue(),
                                             new String[] {HTypeSettlementMethodType.PAY_EASY.getLabel(),
                                                             "settlement.payment.term.day.payeasy"}
                                            );
        PAYMENT_TIME_LIMIT_DAY_COUNT_MAP.put(HTypeSettlementMethodType.CREDIT.getValue(),
                                             new String[] {HTypeSettlementMethodType.CREDIT.getLabel(),
                                                             "settlement.payment.term.day.credit"}
                                            );

        /*
         * ↓は利用可能最大金額のチェックで利用する。マップには、決済タイプの値をキー、String配列を値として設定する。
         * String配列には、左から、決済タイプラベル、利用可能最大金額のプロパティキー、を設定する。
         * 利用可能最大金額のプロパティキーはバックのシステムプロパティファイルに記述している。
         * 今後、利用可能最大金額のチェックを行う決済方法が増える場合には、当マップに設定を追加すること。
         */
        MAX_PURCHASED_PRICE_MAP.put(HTypeSettlementMethodType.CREDIT.getValue(),
                                    new String[] {HTypeSettlementMethodType.CREDIT.getLabel(),
                                                    "settlement.amount.max.credit"}
                                   );
        MAX_PURCHASED_PRICE_MAP.put(HTypeSettlementMethodType.CONVENIENCE.getValue(),
                                    new String[] {HTypeSettlementMethodType.CONVENIENCE.getLabel(),
                                                    "settlement.amount.max.convenience"}
                                   );
        MAX_PURCHASED_PRICE_MAP.put(HTypeSettlementMethodType.PAY_EASY.getValue(),
                                    new String[] {HTypeSettlementMethodType.PAY_EASY.getLabel(),
                                                    "settlement.amount.max.payeasy"}
                                   );
    }

    private final DeliveryMethodDao deliveryMethodDao;

    /**
     * 決済方法詳細設定DXO
     */
    private final SettlementRegistUpdateHelper settlementRegistUpdateHelper;

    /**
     * 決済方法詳細設定取得サービス
     */
    private final SettlementMethodConfigGetService settlementMethodConfigGetService;

    /**
     * 決済方法登録サービス
     */
    private final SettlementMethodRegistService settlementMethodRegistService;

    /**
     * 決済方法更新サービス
     */
    private final SettlementMethodUpdateService settlementMethodUpdateService;

    /**
     * 決済方法設定チェックサービス
     */
    private final SettlementMethodConfigurationCheckService settlementMethodConfigurationCheckService;

    /**
     * クーポン関連ユーティリティクラス
     */
    private final CouponUtility couponUtility;

    /**
     * 受注関連ユーティリティ
     */
    private final OrderUtility orderUtility;

    /**
     * 決済方法登録更新の動的バリデータ
     */
    private final SettlementRegistUpdateValidator settlementRegistUpdateValidator;

    /**
     * 確認画面から
     */
    public static final String FLASH_FROM_CONFIRM = "fromConfirm";

    @Autowired
    public SettlementRegistUpdateController(DeliveryMethodDao deliveryMethodDao,
                                            SettlementRegistUpdateHelper settlementRegistUpdateHelper,
                                            SettlementMethodConfigGetService settlementMethodConfigGetService,
                                            SettlementMethodRegistService settlementMethodRegistService,
                                            SettlementMethodUpdateService settlementMethodUpdateService,
                                            SettlementMethodConfigurationCheckService settlementMethodConfigurationCheckService,
                                            CouponUtility couponUtility,
                                            OrderUtility orderUtility,
                                            SettlementRegistUpdateValidator settlementRegistUpdateValidator) {
        this.deliveryMethodDao = deliveryMethodDao;
        this.settlementRegistUpdateHelper = settlementRegistUpdateHelper;
        this.settlementMethodConfigGetService = settlementMethodConfigGetService;
        this.settlementMethodRegistService = settlementMethodRegistService;
        this.settlementMethodUpdateService = settlementMethodUpdateService;
        this.settlementMethodConfigurationCheckService = settlementMethodConfigurationCheckService;
        this.couponUtility = couponUtility;
        this.orderUtility = orderUtility;
        this.settlementRegistUpdateValidator = settlementRegistUpdateValidator;
    }

    @InitBinder(value = "settlementRegistUpdateModel")
    public void initBinder(WebDataBinder error) {
        // 決済方法登録更新の動的バリデータをセット
        error.addValidators(settlementRegistUpdateValidator);
    }

    /**
     * 画面表示処理
     *
     * @return 自画面
     */
    @GetMapping(value = "/registupdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/registupdate/index")
    public String doLoadIndex(@RequestParam(required = false) String seq,
                              @RequestParam(required = false) String mode,
                              SettlementRegistUpdateModel settlementRegistUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        initComponentValue(settlementRegistUpdateModel);
        if (!model.containsAttribute(FLASH_FROM_CONFIRM)) {
            clearModel(SettlementRegistUpdateModel.class, settlementRegistUpdateModel, model);
            initComponentValue(settlementRegistUpdateModel);
        }

        Integer configMode = settlementRegistUpdateModel.getConfigMode();

        // モデルリセット
        if (StringUtils.equals(mode, "new")) {
            settlementRegistUpdateModel.setFromConfirm(false);
        }

        if (seq != null) {
            Integer settlementMethodSeq = Integer.valueOf(seq);

            if (isDiscountSettlement(settlementMethodSeq)) {
                // 決済方法SEQが「全額クーポン払い」の場合は不正アクセスのため、エラー画面に遷移
                addMessage(MSG_CD_ILLEGAL, redirectAttributes, model);
                return "redirect:/error";
            }

            // 決済方法DTO取得処理
            SettlementMethodDto settlementMethodDto = settlementMethodConfigGetService.execute(settlementMethodSeq);

            if (settlementMethodDto == null) {
                addMessage("AYC000203", redirectAttributes, model);
                return "redirect:/settlement/";
            }
            HTypeOpenDeleteStatus openstatus = settlementMethodDto.getSettlementMethodEntity().getOpenStatusPC();
            if (HTypeOpenDeleteStatus.DELETED == openstatus) {
                addMessage("AYC000203", redirectAttributes, model);
                return "redirect:/settlement/";
            }

            // 決済方法詳細情報画面反映
            settlementRegistUpdateHelper.toPage(settlementRegistUpdateModel, settlementMethodDto);

            // 更新前決済情報の格納
            settlementRegistUpdateModel.setSettlementMethodEntity(
                            settlementRegistUpdateModel.getSettlementMethodDto().getSettlementMethodEntity());
            settlementRegistUpdateModel.setSettlementMethodPriceCommissionEntityList(
                            settlementRegistUpdateModel.getSettlementMethodDto()
                                                       .getSettlementMethodPriceCommissionEntityList());
            settlementRegistUpdateModel.setConfigMode(2);

        } else {
            if (settlementRegistUpdateModel.isFromConfirm()) {
                return "settlement/registupdate/index";
            } else {
                settlementRegistUpdateModel.setConfigMode(1);
            }
        }

        settlementRegistUpdateHelper.toSettlementMethodTypeItems(settlementRegistUpdateModel);

        settlementRegistUpdateModel.setFromConfirm(false);

        // 実行前処理
        String check = preDoAction("doLoadIndex", settlementRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        return "settlement/registupdate/index";
    }

    /**
     * 確認画面へ遷移<br/>
     *
     * @return 決済方法設定：確認画面
     */
    @PostMapping(value = "/registupdate", params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/registupdate/index")
    public String doConfirm(@Validated(ConfirmGroup.class) SettlementRegistUpdateModel settlementRegistUpdateModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        // 実行前処理
        String check = preDoAction("doConfirm", settlementRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (hasErrorMessage()) {
            throwMessage();
        }

        if (error.hasErrors()) {
            return "settlement/registupdate/index";
        }

        if (settlementRegistUpdateModel.isRegist()) {
            // 新規登録の場合は配送方法名称をセット
            settlementRegistUpdateHelper.setDeliveryMethodName(settlementRegistUpdateModel);
        }
        // 決済種別を保持
        // settlementRegistUpdateModel.resetCommissionType();

        // 手数料リストソート
        sortCommissionItems(settlementRegistUpdateModel);

        // 高額割引きチェック
        checkLargeAmountDiscount(settlementRegistUpdateModel);

        // 通知メールの送信可否と請求種別・決済種別のチェック
        checkSendMail(settlementRegistUpdateModel);

        // 購入可能金額のチェック
        checkPurchasedPrice(settlementRegistUpdateModel);

        // 支払期限のチェック
        checkPaymentTimeLimitDayCount(settlementRegistUpdateModel);

        if (hasErrorMessage()) {
            throwMessage();
        }

        // 設定可能チェック
        SettlementMethodDto settlementMethodDto =
                        settlementRegistUpdateHelper.pageToSettlementMethodDto(settlementRegistUpdateModel);
        settlementMethodConfigurationCheckService.execute(settlementMethodDto);

        // 更新後の決済方法DTOを設定
        settlementRegistUpdateModel.setSettlementMethodDto(settlementMethodDto);

        return "redirect:/settlement/registupdate/confirm";
    }

    /**
     * 高額割引入力チェック<br/>
     * 高額割引下限金額、高額割引手数料が最大購入金額を超えていないかをチェックする
     */
    protected void checkLargeAmountDiscount(SettlementRegistUpdateModel settlementRegistUpdateModel) {

        if (HTypeSettlementMethodPriceCommissionFlag.FLAT
            != settlementRegistUpdateModel.getSettlementMethodPriceCommissionFlag()) {
            return;
        }

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        BigDecimal price = conversionUtility.toBigDecimal(settlementRegistUpdateModel.getLargeAmountDiscountPrice());
        BigDecimal commission =
                        conversionUtility.toBigDecimal(settlementRegistUpdateModel.getLargeAmountDiscountCommission());
        BigDecimal maxPurchasedPrice =
                        conversionUtility.toBigDecimal(settlementRegistUpdateModel.getMaxPurchasedPrice());
        String strType;

        // if (HTypeSettlementMethodCommissionType.FLAT_YEN ==
        // indexPage.getCommissionType()) {
        strType = "Yen";
        // } else {
        // strType = "Percentage";
        // }

        String maxPurchasedPriceTitle = getHtmlTitle("maxPurchasedPrice".concat(strType));

        if (price != null && price.compareTo(maxPurchasedPrice) > 0) {
            String htmlTitle = getHtmlTitle("largeAmountDiscountPrice".concat(strType));
            this.addErrorMessage("AYC000207", new Object[] {htmlTitle, maxPurchasedPriceTitle});
        }
        if (commission != null && commission.compareTo(maxPurchasedPrice) > 0) {
            String htmlTitle = getHtmlTitle("largeAmountDiscountCommission".concat(strType));
            this.addErrorMessage("AYC000207", new Object[] {htmlTitle, maxPurchasedPriceTitle});
        }

    }

    /**
     * メール送信可否と決済方法・請求方法の相関チェック<br/>
     */
    protected void checkSendMail(SettlementRegistUpdateModel settlementRegistUpdateModel) {
        // 決済種別が「クレジット」でない、かつ 請求種別が「後請求」で、通知メールが「送信する」になっている場合、 エラー
        if (!HTypeSettlementMethodType.CREDIT.getValue().equals(settlementRegistUpdateModel.getSettlementMethodType())
            && HTypeMailRequired.REQUIRED.getValue().equals(settlementRegistUpdateModel.getSettlementMailRequired())
            && HTypeBillType.POST_CLAIM.getValue().equals(settlementRegistUpdateModel.getBillType())) {
            this.addErrorMessage("AYC000212");
        }
    }

    /**
     * 購入可能最小金額、最大金額の相関チェック<br/>
     */
    protected void checkPurchasedPrice(SettlementRegistUpdateModel settlementRegistUpdateModel) {
        boolean checkNeedFlg = false;
        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        BigDecimal maxPriceForeCmp = conversionUtility.toBigDecimal(settlementRegistUpdateModel.getMaxPurchasedPrice());
        // 金額別手数料設定の場合は画面表示用に金利用可能最大金額を設定する
        List<SettlementRegistUpdateModelItem> priceCommissonItemList =
                        settlementRegistUpdateModel.getPriceCommissionItemList();
        if (priceCommissonItemList != null) {
            maxPriceForeCmp = BigDecimal.ZERO;
            for (SettlementRegistUpdateModelItem item : priceCommissonItemList) {
                BigDecimal maxPrice = conversionUtility.toBigDecimal(item.getMaxPrice());
                BigDecimal commission = conversionUtility.toBigDecimal(item.getCommission());
                if (maxPrice != null && commission != null) {
                    // 上限金額
                    if (maxPriceForeCmp.compareTo(maxPrice) < 0) {
                        maxPriceForeCmp = maxPrice;
                    }
                    checkNeedFlg = true;
                }
            }
        } else {
            checkNeedFlg = true;
        }

        // 決済種別が「クレジット」or「コンビニ」or「ペイジー」のとき、利用可能最小購入金額が1円未満の場合エラー

        // 比較用の利用可能最小購入金額
        BigDecimal getMinPurchasedPriceComp = BigDecimal.ONE;

        if ((HTypeSettlementMethodType.CREDIT.getValue().equals(settlementRegistUpdateModel.getSettlementMethodType()))
            || (HTypeSettlementMethodType.CONVENIENCE.getValue()
                                                     .equals(settlementRegistUpdateModel.getSettlementMethodType()))
            || (HTypeSettlementMethodType.PAY_EASY.getValue()
                                                  .equals(settlementRegistUpdateModel.getSettlementMethodType())
                || (HTypeSettlementMethodType.AMAZON_PAYMENT.getValue()
                                                            .equals(settlementRegistUpdateModel.getSettlementMethodType())))) {
            if (((conversionUtility.toBigDecimal(settlementRegistUpdateModel.getMinPurchasedPrice())
                                   .compareTo(getMinPurchasedPriceComp)) < 0)) {
                this.addErrorMessage("AYC000213");
            }
        }

        // 最小可能購入金額が最大購入金額 より大きい場合はエラー
        if (checkNeedFlg && conversionUtility.toBigDecimal(settlementRegistUpdateModel.getMinPurchasedPrice())
                                             .compareTo(maxPriceForeCmp) > 0) {
            if (HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT
                == settlementRegistUpdateModel.getSettlementMethodPriceCommissionFlag()) {
                // 金額別の場合
                this.addErrorMessage("AYC000211");
            } else {
                // 一律の場合
                this.addErrorMessage("AYC000210");
            }
        }

        // 利用可能最大金額のチェック
        checkMaxPurchasedPrice(settlementRegistUpdateModel);
    }

    /**
     * 利用可能最大金額のチェック
     */
    protected void checkMaxPurchasedPrice(SettlementRegistUpdateModel settlementRegistUpdateModel) {
        String[] maxPurchasedPriceArray =
                        MAX_PURCHASED_PRICE_MAP.get(settlementRegistUpdateModel.getSettlementMethodType());
        if (maxPurchasedPriceArray != null && settlementRegistUpdateModel.getMaxPurchasedPrice() != null) {
            String settlementMethodName = maxPurchasedPriceArray[0];
            String maxPurchasedPricePropertyKey = maxPurchasedPriceArray[1];
            // 変換Utility取得
            ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
            BigDecimal maxAmount =
                            new BigDecimal(PropertiesUtil.getSystemPropertiesValue(maxPurchasedPricePropertyKey));
            if (conversionUtility.toBigDecimal(settlementRegistUpdateModel.getMaxPurchasedPrice()).compareTo(maxAmount)
                > 0) {
                this.addErrorMessage(MSG_CD_EXCEED_MAX_PURCHASED_PRICE, new Object[] {settlementMethodName, maxAmount});
            }
        }
    }

    /**
     * 支払期限のチェック<br/>
     */
    protected void checkPaymentTimeLimitDayCount(SettlementRegistUpdateModel settlementRegistUpdateModel) {
        String[] paymentTimeLimitDayCountArray =
                        PAYMENT_TIME_LIMIT_DAY_COUNT_MAP.get(settlementRegistUpdateModel.getSettlementMethodType());
        if (paymentTimeLimitDayCountArray != null && HTypeBillType.PRE_CLAIM.getValue()
                                                                            .equals(settlementRegistUpdateModel.getBillType())) {
            String settlementMethodName = paymentTimeLimitDayCountArray[0];
            String paymentTimeLimitDayCountPropertyKey = paymentTimeLimitDayCountArray[1];
            // 比較用の支払期限猶予日数
            BigDecimal paymentTimeLimitDayCountComp = new BigDecimal(PropertiesUtil.getSystemPropertiesValue(
                            paymentTimeLimitDayCountPropertyKey));

            // 変換Utility取得
            ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
            if ((conversionUtility.toBigDecimal(settlementRegistUpdateModel.getPaymentTimeLimitDayCount())
                                  .compareTo(paymentTimeLimitDayCountComp) > 0)) {
                this.addErrorMessage(MSG_CD_PAY_LIMIT_ERR,
                                     new Object[] {settlementMethodName, paymentTimeLimitDayCountComp}
                                    );
            }
        }
    }

    /**
     * コンポーネントのtitle属性を取得<br/>
     *
     * @param componentId コンポーネントID
     * @return title属性値
     */
    protected String getHtmlTitle(String componentId) {
        // UIComponent component = getComponent(indexPage.getDefaultFormId(),
        // componentId);
        // return ((THtmlInputText) component).getTitle();
        return "";
    }

    /**
     * 削除ステータスであるかを取得<br/>
     *
     * @param strStatus 公開状態の選択値
     * @return true=削除、false=削除以外
     */
    protected boolean isDeleteStatus(String strStatus) {
        if (strStatus != null) {
            HTypeOpenDeleteStatus status = EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class, strStatus);
            if (HTypeOpenDeleteStatus.DELETED == status) {
                return true;
            }
        }
        return false;
    }

    /**
     * 金額別手数料リストを合計金額の昇順にソート<br/>
     */
    protected void sortCommissionItems(SettlementRegistUpdateModel settlementRegistUpdateModel) {

        if (HTypeSettlementMethodPriceCommissionFlag.EACH_AMOUNT
            != settlementRegistUpdateModel.getSettlementMethodPriceCommissionFlag()) {
            return;
        }

        List<SettlementRegistUpdateModelItem> commissionItems =
                        settlementRegistUpdateModel.getPriceCommissionItemList();
        List<SettlementRegistUpdateModelItem> sortItems = new ArrayList<>();

        if (commissionItems == null) {
            return;
        }

        int count = 0;
        int idx = 0;
        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        for (SettlementRegistUpdateModelItem item : commissionItems) {
            BigDecimal maxPrice = conversionUtility.toBigDecimal(item.getMaxPrice());
            idx++;
            if (maxPrice == null) {
                continue;
            }
            boolean notAdd = true;
            for (int index = 0; index < sortItems.size(); index++) {
                SettlementRegistUpdateModelItem sortitem = sortItems.get(index);
                BigDecimal price = conversionUtility.toBigDecimal(sortitem.getMaxPrice());
                int jadge = maxPrice.compareTo(price);
                if (jadge < 0 && notAdd) {
                    sortItems.add(index, item);
                    notAdd = false;
                    count++;
                } else if (jadge == 0) {
                    // 同額チェック
                    this.addErrorMessage("AYC000202", new Object[] {idx});
                }
            }
            if (notAdd) {
                sortItems.add(item);
                count++;
            }
        }

        if (!hasErrorMessage()) {
            for (; count < 10; count++) {
                SettlementRegistUpdateModelItem item =
                                ApplicationContextUtility.getBean(SettlementRegistUpdateModelItem.class);
                sortItems.add(item);
            }
            if (HTypeSettlementMethodCommissionType.EACH_AMOUNT_YEN
                == settlementRegistUpdateModel.getCommissionType()) {
                settlementRegistUpdateModel.setPriceCommissionYen(sortItems);
            }
            // else {
            // indexPage.setPriceCommissionPercentage(sortItems);
            // }
        }
    }

    /**
     * 決済方法手数料種別変更<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/registupdate", params = "doChangeComissonType")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/registupdate/index")
    public String doChangeComissonType(@ModelAttribute("settlementRegistUpdateModel")
                                                       SettlementRegistUpdateModel settlementRegistUpdateModel,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {

        // 実行前処理
        String check = preDoAction("doChangeComissonType", settlementRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        settlementRegistUpdateHelper.changeComissonFlag(settlementRegistUpdateModel);
        return "settlement/registupdate/index";
    }

    /**
     * 決済方法種別変更<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/registupdate", params = "doChangeSettlementMethodType")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/registupdate/index")
    public String doChangeSettlementMethodType(@ModelAttribute("settlementRegistUpdateModel")
                                                               SettlementRegistUpdateModel settlementRegistUpdateModel,
                                               RedirectAttributes redirectAttributes,
                                               Model model) {

        // 実行前処理
        String check = preDoAction("doChangeSettlementMethodType", settlementRegistUpdateModel, redirectAttributes,
                                   model
                                  );
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        return "settlement/registupdate/index";
    }

    /**
     * 請求種別変更<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/registupdate", params = "doChangeBillType")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/registupdate/index")
    public String doChangeBillType(SettlementRegistUpdateModel settlementRegistUpdateModel,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        // 実行前処理
        String check = preDoAction("doChangeBillType", settlementRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        return "settlement/registupdate/index";
    }

    /**
     * PC値を携帯値へ半角変換して反映<br/>
     *
     * @return 自画面
     */
    @PostMapping(value = "/registupdate", params = "doCopyToMobile")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/registupdate/index")
    public String doCopyToMobile(SettlementRegistUpdateModel settlementRegistUpdateModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        // 実行前処理
        String check = preDoAction("doCopyToMobile", settlementRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 反映
        settlementRegistUpdateHelper.toPageForCopyToMobile(settlementRegistUpdateModel);
        return "settlement/registupdate/index";
    }

    /**
     * アクション実行前処理
     *
     * @param settlementRegistUpdateModel
     * @param redirectAttributes
     * @param model
     */
    public String preDoAction(String actionName,
                              SettlementRegistUpdateModel settlementRegistUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if ("doLoadIndex".equals(actionName)) {
            Integer configMode = settlementRegistUpdateModel.getConfigMode();
            if (configMode != null) {
                return illegalOperationCheck(settlementRegistUpdateModel, redirectAttributes, model);
            }
        } else {
            return illegalOperationCheck(settlementRegistUpdateModel, redirectAttributes, model);
        }
        return null;
    }

    /**
     * 全額決済かどうか判定する<br/>
     *
     * @param settlementMethodSeq 決済方法種別SEQ
     * @return 全額割引の場合true、 それ以外はfalse
     */
    protected boolean isDiscountSettlement(Integer settlementMethodSeq) {
        // 全額クーポン払い時の決済方法
        Integer couponSeq = couponUtility.getCouponSettlementMethodSeq();
        // 無料お支払決済方法
        Integer freeSeq = orderUtility.getFreeSettlementMethodSeq();

        if (couponSeq.compareTo(settlementMethodSeq) == 0 ||
            // pointSeq.compareTo(settlementMethodSeq) == 0 ||
            freeSeq.compareTo(settlementMethodSeq) == 0) {
            return true;
        }
        return false;
    }

    /**
     * 画面初期処理
     *
     * @return 自画面
     */
    @GetMapping(value = "/registupdate/confirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/registupdate/confirm")
    public String doLoadConfirm(SettlementRegistUpdateModel settlementRegistUpdateModel,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        // ブラウザバックの場合、処理しない
        if (settlementRegistUpdateModel.getSettlementMethodDto() == null) {
            return "redirect:/error";
        }

        // 実行前処理
        String check = preDoAction("doLoadConfirm", settlementRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        settlementRegistUpdateModel.setFromConfirm(true);

        String settType = settlementRegistUpdateModel.getSettlementMethodType();
        String billType = settlementRegistUpdateModel.getBillType();
        if (settType == null && billType == null) {
            addMessage("AYC000303", redirectAttributes, model);
            return "redirect:/error";
        }

        settlementRegistUpdateHelper.toPageForLoad(settlementRegistUpdateModel);

        return "settlement/registupdate/confirm";
    }

    /**
     * 登録処理<br/>
     *
     * @return 決済方法設定ページ
     */
    @PostMapping(value = "/registupdate/confirm", params = "doOnceRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/registupdate/confirm")
    public String doOnceRegist(SettlementRegistUpdateModel settlementRegistUpdateModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               SessionStatus sessionStatus,
                               Model model) {

        // 実行前処理
        String check = preDoAction("doOnceRegist", settlementRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        SettlementMethodDto settlementMethodDto =
                        settlementRegistUpdateHelper.pageToSettlementMethodDtoConfirm(settlementRegistUpdateModel);

        // 決済方法詳細設定チェック
        settlementMethodConfigurationCheckService.execute(settlementMethodDto);

        if (settlementMethodRegistService.execute(settlementMethodDto) == 0) {
            // 登録失敗
            this.throwMessage("AYC000301");
        }

        settlementRegistUpdateModel.setFromConfirm(false);

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "redirect:/settlement/";
    }

    /**
     * 更新処理<br/>
     *
     * @return 決済方法設定ページ
     */
    @PostMapping(value = "/registupdate/confirm", params = "doOnceUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "settlement/registupdate/confirm")
    public String doOnceUpdate(SettlementRegistUpdateModel settlementRegistUpdateModel,
                               RedirectAttributes redirectAttributes,
                               SessionStatus sessionStatus,
                               Model model) {

        // 実行前処理
        String check = preDoAction("doOnceUpdate", settlementRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        SettlementMethodDto settlementMethodDto =
                        settlementRegistUpdateHelper.pageToSettlementMethodDtoConfirm(settlementRegistUpdateModel);

        // 決済方法詳細設定チェック
        // settlementMethodConfigurationCheckService.execute(settlementMethodDto);

        if (settlementMethodUpdateService.execute(settlementMethodDto) == 0) {
            // 更新失敗
            this.throwMessage("AYC000302");
        }

        settlementRegistUpdateModel.setFromConfirm(false);

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "redirect:/settlement/";
    }

    /**
     * 戻る<br/>
     *
     * @return 決済方法詳細設定ページ
     */
    @PostMapping(value = "/registupdate/confirm", params = "doCancel")
    public String doCancel(SettlementRegistUpdateModel settlementRegistUpdateModel,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        // 実行前処理
        String check = preDoAction("doCancel", settlementRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }
        redirectAttributes.addFlashAttribute(FLASH_FROM_CONFIRM, true);
        settlementRegistUpdateModel.setFromConfirm(true);
        return "redirect:/settlement/registupdate";
    }

    /**
     * 不正操作チェック
     */
    protected String illegalOperationCheck(SettlementRegistUpdateModel settlementRegistUpdateModel,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {
        Integer pageSettlementMethodSeq = settlementRegistUpdateModel.getSettlementMethodSeq();
        Integer configMode = settlementRegistUpdateModel.getConfigMode();
        boolean isError = false;
        if (configMode == null) {
            configMode = 0;
        }

        if (pageSettlementMethodSeq == null) {
            if (configMode.intValue() != 1) {
                isError = true;
            }
        } else {
            if (configMode.intValue() != 2) {
                isError = true;
            } else {
                try {
                    SettlementMethodEntity settlementMethodEntity =
                                    settlementRegistUpdateModel.getSettlementMethodDto().getSettlementMethodEntity();
                    Integer modSettlementMethodSeq = settlementMethodEntity.getSettlementMethodSeq();

                    if (modSettlementMethodSeq.intValue() != pageSettlementMethodSeq.intValue()) {
                        isError = true;
                    }
                } catch (NullPointerException e) {
                    LOGGER.error("例外処理が発生しました", e);
                    isError = true;
                }
            }
        }
        if (isError) {
            addMessage("AYC000303", redirectAttributes, model);
            return "redirect:/error";
        }

        return null;
    }

    private void initComponentValue(SettlementRegistUpdateModel settlementRegistUpdateModel) {
        // Set settlement method type
        Map<String, String> settlementMethodTypeMap = new HashMap<>();
        settlementMethodTypeMap.put(HTypeSettlementMethodType.CONVENIENCE.getValue(),
                                    HTypeSettlementMethodType.CONVENIENCE.getLabel()
                                   );
        settlementMethodTypeMap.put(
                        HTypeSettlementMethodType.PAY_EASY.getValue(), HTypeSettlementMethodType.PAY_EASY.getLabel());
        settlementMethodTypeMap.put(
                        HTypeSettlementMethodType.CREDIT.getValue(), HTypeSettlementMethodType.CREDIT.getLabel());
        settlementMethodTypeMap.put(HTypeSettlementMethodType.RECEIPT_PAYMENT.getValue(),
                                    HTypeSettlementMethodType.RECEIPT_PAYMENT.getLabel()
                                   );
        settlementMethodTypeMap.put(HTypeSettlementMethodType.CONVENIENCE_POSTALTRANSFER.getValue(),
                                    HTypeSettlementMethodType.CONVENIENCE_POSTALTRANSFER.getLabel()
                                   );
        settlementMethodTypeMap.put(HTypeSettlementMethodType.AUTOMATIC_WITHDRAWAL.getValue(),
                                    HTypeSettlementMethodType.AUTOMATIC_WITHDRAWAL.getLabel()
                                   );
        settlementMethodTypeMap.put(HTypeSettlementMethodType.MONTHLY_BILLING.getValue(),
                                    HTypeSettlementMethodType.MONTHLY_BILLING.getLabel()
                                   );
        settlementRegistUpdateModel.setSettlementMethodTypeItems(settlementMethodTypeMap);

        // Set bill type
        settlementRegistUpdateModel.setBillTypeItems(EnumTypeUtil.getEnumMap(HTypeBillType.class));

        // Set settlement method commission type
        settlementRegistUpdateModel.setSettlementMethodCommissionTypeItems(
                        EnumTypeUtil.getEnumMap(HTypeSettlementMethodCommissionType.class));

        // Set settlement mail required
        settlementRegistUpdateModel.setSettlementMailRequiredItems(EnumTypeUtil.getEnumMap(HTypeMailRequired.class));

        // Set Open Status PC
        settlementRegistUpdateModel.setOpenStatusPCItems(EnumTypeUtil.getEnumMap(HTypeOpenDeleteStatus.class));

        // Set Delivery Method Seq
        Map<String, String> deliveryMethodSeqMap = new HashMap<>();
        List<DeliveryMethodEntity> list = deliveryMethodDao.getDeliveryMethodList(1001);

        for (DeliveryMethodEntity deliveryMethod : list) {
            deliveryMethodSeqMap.put(
                            deliveryMethod.getDeliveryMethodSeq().toString(), deliveryMethod.getDeliveryMethodName());
        }
        settlementRegistUpdateModel.setDeliveryMethodSeqItems(deliveryMethodSeqMap);

    }

}
