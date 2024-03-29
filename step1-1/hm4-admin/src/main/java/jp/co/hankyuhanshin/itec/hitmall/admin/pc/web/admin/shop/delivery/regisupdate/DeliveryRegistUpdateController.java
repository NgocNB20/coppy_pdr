package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.regisupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.regisupdate.validation.DeliveryRegistUpdateValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryMethodDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodDataCheckService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodDetailsGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.BigDecimalConversionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.IntegerConversionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.NumberUtility;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/delivery")
@Controller
@SessionAttributes(value = "deliveryRegistUpdateModel")
@PreAuthorize("hasAnyAuthority('SETTING:8')")
public class DeliveryRegistUpdateController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryRegistUpdateController.class);

    /**
     * メッセージコード：不正操作
     */
    protected static final String MSGCD_ILLEGAL_OPERATION = "AYD000201";

    /**
     * メッセージコード：金額別送料設定がされていない
     */
    protected static final String MSGCD_NO_SETTING_AMOUNT_CARRIAGE = "AYD000204";

    /**
     * メッセージコード：金額別送料設定にショップ別最大決済金額よりも大きいものがなかった。
     */
    protected static final String MSGCD_ILLEGAL_MAX_AMOUNT_CARRIAGE = "AYD000205";

    /**
     * メッセージコード：上限金額に同金額のものはないか
     */
    protected static final String MSGCD_SAME_AMOUNT_CARRIAGE = "AYD000206";

    /**
     * メッセージコード：不足金額は割引送料が0円の場合のみ表示可能
     */
    protected static final String MSGCD_NO_SHORTFALL_DISPLAY = "AYD000208";

    /**
     * メッセージコード：配送追跡URLの伝票番号引換コードエラー
     */
    protected static final String MSGCD_CHASEURL_ERROR = "AYD000801";

    /**
     * 入力チェック：不足金額比較用
     */
    private static final BigDecimal ZERO = new BigDecimal(0);

    /**
     * 配送方法詳細取得サービス
     */
    private final DeliveryMethodDetailsGetService deliveryMethodDetailsGetService;

    /**
     * 配送方法データチェックサービス
     */
    private final DeliveryMethodDataCheckService deliveryMethodDataCheckService;

    /**
     * 配送方法登録・更新入力画面Helper
     */
    private final DeliveryRegistUpdateHelper deliveryRegistUpdateHelper;

    /**
     * 配送方法登録サービス
     */
    private final DeliveryMethodRegistService deliveryMethodRegistService;

    /**
     * 配送方法更新サービス
     */
    private final DeliveryMethodUpdateService deliveryMethodUpdateService;

    /**
     * 配送登録更新の動的バリデータ
     */
    private final DeliveryRegistUpdateValidator deliveryRegistUpdateValidator;

    /**
     * システムプロパティから最大決済金額を取得するためのキー
     */
    protected static final String ORDER_MAX_AMOUNT_KEY = "order.max.amount";

    @Autowired
    public DeliveryRegistUpdateController(DeliveryMethodDetailsGetService deliveryMethodDetailsGetService,
                                          DeliveryMethodDataCheckService deliveryMethodDataCheckService,
                                          DeliveryRegistUpdateHelper deliveryRegistUpdateHelper,
                                          DeliveryMethodRegistService deliveryMethodRegistService,
                                          DeliveryMethodUpdateService deliveryMethodUpdateService,
                                          DeliveryRegistUpdateValidator deliveryRegistUpdateValidator) {
        this.deliveryMethodDetailsGetService = deliveryMethodDetailsGetService;
        this.deliveryMethodDataCheckService = deliveryMethodDataCheckService;
        this.deliveryRegistUpdateHelper = deliveryRegistUpdateHelper;
        this.deliveryMethodRegistService = deliveryMethodRegistService;
        this.deliveryMethodUpdateService = deliveryMethodUpdateService;
        this.deliveryRegistUpdateValidator = deliveryRegistUpdateValidator;
    }

    @InitBinder(value = "deliveryRegistUpdateModel")
    public void initBinder(WebDataBinder error) {
        // メール件名の動的バリデータをセット
        error.addValidators(deliveryRegistUpdateValidator);
    }

    /**
     * 初期処理
     *
     * @return null
     */
    @GetMapping(value = "/registupdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/registupdate/index")
    public String doLoadIndex(@RequestParam(required = false) String dmcd,
                              @RequestParam(required = false) Optional<String> from,
                              DeliveryRegistUpdateModel deliveryRegistUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        if (from.isPresent() && from.get().equals("confirm")) {
            return "delivery/registupdate/index";
        }

        // 数値関連Helper取得
        NumberUtility numberUtility = ApplicationContextUtility.getBean(NumberUtility.class);

        // 初期遷移の時。（確認画面からの遷移でない場合）
        if (!deliveryRegistUpdateModel.isEditFlag()) {
            clearModel(DeliveryRegistUpdateModel.class, deliveryRegistUpdateModel, model);

            DeliveryMethodDetailsDto deliveryMethodDetailsDto =
                            ApplicationContextUtility.getBean(DeliveryMethodDetailsDto.class);

            // 更新モード
            if (!StringUtil.isEmpty(dmcd) && numberUtility.isNumber(dmcd)) {
                Integer deliveryMethodSeq = IntegerConversionUtil.toInteger(dmcd);

                // 配送方法詳細取得サービス実行
                deliveryMethodDetailsDto = deliveryMethodDetailsGetService.execute(deliveryMethodSeq);
            }
            deliveryRegistUpdateHelper.toPageForLoadIndex(deliveryRegistUpdateModel, deliveryMethodDetailsDto);
        }

        // 修正画面の場合、画面用配送方法SEQを設定
        if (!StringUtil.isEmpty(dmcd) && numberUtility.isNumber(dmcd)) {
            deliveryRegistUpdateModel.setScDeliveryMethodSeq(deliveryRegistUpdateModel.getDeliveryMethodDetailsDto()
                                                                                      .getDeliveryMethodEntity()
                                                                                      .getDeliveryMethodSeq());
        }

        deliveryRegistUpdateModel.setEditFlag(false);

        // 実行前処理
        String check = preDoAction(deliveryRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        return "delivery/registupdate/index";
    }

    /**
     * 「配送種別」プルダウンを変更した時の処理
     *
     * @return null
     */
    @PostMapping(value = "/registupdate", params = "doDisplayChange")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/registupdate/index")
    public String doDisplayChange(DeliveryRegistUpdateModel deliveryRegistUpdateModel,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        // 実行前処理
        String check = preDoAction(deliveryRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 不正操作チェック
        if (!deliveryRegistUpdateModel.isNormality()) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }
        return "delivery/registupdate/index";
    }

    /**
     * 「確認」ボタン押下処理
     *
     * @return null
     */
    @PostMapping(value = "/registupdate", params = "doConfirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/registupdate/index")
    public String doConfirm(@Validated(ConfirmGroup.class) DeliveryRegistUpdateModel deliveryRegistUpdateModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        // 実行前処理
        String check = preDoAction(deliveryRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (error.hasErrors()) {
            return "delivery/registupdate/index";
        }

        // 不正操作チェック
        if (!deliveryRegistUpdateModel.isNormality()) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        // 配送追跡URLチェック
        if (inputDeliveryChaseURLCheck(deliveryRegistUpdateModel)) {
            throwMessage(MSGCD_CHASEURL_ERROR);
        }

        // 入力情報を変換
        deliveryRegistUpdateHelper.toPageForConfirmIndex(deliveryRegistUpdateModel);

        // 入力内容チェック
        checkContents(deliveryRegistUpdateModel);

        // 確認画面へ遷移
        return "redirect:/delivery/registupdate/confirm";
    }

    /**
     * 初期処理
     *
     * @return null
     */
    @GetMapping(value = "/registupdate/confirm")
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/delivery/registupdate/?from=confirm")
    protected String doLoadConfirm(DeliveryRegistUpdateModel deliveryRegistUpdateModel,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        // ブラウザバックの場合、処理しない
        if (deliveryRegistUpdateModel.getDeliveryMethodEntity() == null) {
            return "redirect:/delivery/";
        }

        // 実行前処理
        String check = preDoAction(deliveryRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 確認画面に直接飛んできた
        if (deliveryRegistUpdateModel.getDeliveryMethodEntity().getDeliveryMethodName() == null) {
            throwMessage(MSGCD_ILLEGAL_OPERATION);
        }

        // ページへ変換
        deliveryRegistUpdateHelper.toPageForLoadConfirm(deliveryRegistUpdateModel);

        // ※金額別送料は現在未使用
        if (deliveryRegistUpdateModel.getDeliveryMethodType().equals(HTypeDeliveryMethodType.AMOUNT.getValue())) {
            // 金額別送料設定リストを上限金額でソート
            sortAmountCarriage(deliveryRegistUpdateModel);
        }

        // 修正の場合、画面用配送方法SEQを設定
        Integer scDeliveryMethodSeq = null;
        try {
            scDeliveryMethodSeq = deliveryRegistUpdateModel.getDeliveryMethodDetailsDto()
                                                           .getDeliveryMethodEntity()
                                                           .getDeliveryMethodSeq();
        } catch (Exception e) {
            // NULLチェックを省く目的のtry-catchであるため、例外キャッチ時のログだけ吐いて処理は行わない
            LOGGER.error("例外処理が発生しました", e);
        }
        if (scDeliveryMethodSeq != null) {
            deliveryRegistUpdateModel.setScDeliveryMethodSeq(scDeliveryMethodSeq);
        }

        return "delivery/registupdate/confirm";
    }

    /**
     * 登録処理
     *
     * @return 配送方法設定画面
     */
    @PostMapping(value = "/registupdate/confirm", params = "doOnceRegist")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/registupdate/confirm")
    public String doOnceRegist(DeliveryRegistUpdateModel deliveryRegistUpdateModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               SessionStatus sessionStatus,
                               Model model) {

        // 実行前処理
        String check = preDoAction(deliveryRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (error.hasErrors()) {
            return "delivery/registupdate/index";
        }

        // 不正操作
        if (!deliveryRegistUpdateModel.isNormality()) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/registupdate";
        }

        // ページから配送方法DTOに変換
        DeliveryMethodDetailsDto deliveryMethodDetailsDto =
                        deliveryRegistUpdateHelper.toDeliveryMethodDtoForConfirm(deliveryRegistUpdateModel);

        // 配送方法登録サービス実行
        deliveryMethodRegistService.execute(deliveryMethodDetailsDto);

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "redirect:/delivery/";
    }

    /**
     * 更新処理
     *
     * @return 配送方法設定画面
     */
    @PostMapping(value = "/registupdate/confirm", params = "doOnceUpdate")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/registupdate/confirm")
    public String doOnceUpdate(@Validated DeliveryRegistUpdateModel deliveryRegistUpdateModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               SessionStatus sessionStatus,
                               Model model) {

        // 実行前処理
        String check = preDoAction(deliveryRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        if (error.hasErrors()) {
            return "delivery/registupdate/index";
        }

        // 不正操作
        if (!deliveryRegistUpdateModel.isNormality()) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/registupdate";
        }

        // ページから配送方法DTOに変換
        DeliveryMethodDetailsDto deliveryMethodDetailsDto =
                        deliveryRegistUpdateHelper.toDeliveryMethodDtoForConfirm(deliveryRegistUpdateModel);

        // 配送方法更新サービス実行
        deliveryMethodUpdateService.execute(deliveryMethodDetailsDto);

        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "redirect:/delivery/";
    }

    /**
     * 「キャンセル」ボタン押下処理
     *
     * @return 配送方法登録・更新入力画面
     */
    @PostMapping(value = "/registupdate/confirm", params = "doIndex")
    @HEHandler(exception = AppLevelListException.class, returnView = "delivery/registupdate/confirm")
    public String doIndex(DeliveryRegistUpdateModel deliveryRegistUpdateModel,
                          RedirectAttributes redirectAttributes,
                          Model model) {

        // 実行前処理
        String check = preDoAction(deliveryRegistUpdateModel, redirectAttributes, model);
        if (StringUtils.isNotEmpty(check)) {
            return check;
        }

        // 不正操作
        if (!deliveryRegistUpdateModel.isNormality()) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/registupdate";
        }
        deliveryRegistUpdateModel.setEditFlag(true);
        return "redirect:/delivery/registupdate";
    }

    /**
     * 配送追跡URLのエラーチェック
     *
     * @return true エラー /false 正常
     */
    private boolean inputDeliveryChaseURLCheck(DeliveryRegistUpdateModel deliveryRegistUpdateModel) {
        if (StringUtil.isNotEmpty(deliveryRegistUpdateModel.getDeliveryChaseURL())) {
            try {
                MessageFormat.format(deliveryRegistUpdateModel.getDeliveryChaseURL(), "");
            } catch (IllegalArgumentException e) {
                LOGGER.error("例外処理が発生しました", e);
                return true;
            }
        }
        return false;
    }

    /**
     * アクション実行前処理
     */
    public String preDoAction(DeliveryRegistUpdateModel deliveryRegistUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        // 不正操作チェック
        return checkIllegalOperation(deliveryRegistUpdateModel, redirectAttributes, model);
    }

    /**
     * 不正操作チェック
     *
     * @param deliveryRegistUpdateModel
     * @param redirectAttributes
     * @param model
     */
    protected String checkIllegalOperation(DeliveryRegistUpdateModel deliveryRegistUpdateModel,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {
        Integer scDeliveryMethodSeq = deliveryRegistUpdateModel.getScDeliveryMethodSeq();
        Integer dbDeliveryMethodSeq = null;
        if (deliveryRegistUpdateModel.getDeliveryMethodDetailsDto() != null
            && deliveryRegistUpdateModel.getDeliveryMethodDetailsDto().getDeliveryMethodEntity() != null) {
            dbDeliveryMethodSeq = deliveryRegistUpdateModel.getDeliveryMethodDetailsDto()
                                                           .getDeliveryMethodEntity()
                                                           .getDeliveryMethodSeq();
        }

        boolean isError = false;

        // 登録画面にも関わらず、配送方法SEQのDB情報を保持している場合エラー
        if (scDeliveryMethodSeq == null && dbDeliveryMethodSeq != null) {
            isError = true;

            // 修正画面にも関わらず、配送方法SEQのDB情報を保持していない場合エラー
        } else if (scDeliveryMethodSeq != null && dbDeliveryMethodSeq == null) {
            isError = true;

            // 画面用配送方法SEQとDB用配送方法SEQが異なる場合エラー
        } else if (scDeliveryMethodSeq != null && !scDeliveryMethodSeq.equals(dbDeliveryMethodSeq)) {
            isError = true;
        }

        if (isError) {
            addMessage(MSGCD_ILLEGAL_OPERATION, redirectAttributes, model);
            return "redirect:/delivery/";
        }

        return null;
    }

    /**
     * 入力内容チェック
     *
     * @param deliveryRegistUpdateModel DeliveryRegistUpdateModel
     */
    protected void checkContents(DeliveryRegistUpdateModel deliveryRegistUpdateModel) {

        DeliveryMethodEntity deliveryMethodEntity = deliveryRegistUpdateModel.getDeliveryMethodEntity();

        List<AppLevelException> errorList = new ArrayList<>();

        // 配送方法データチェックサービス実行
        try {
            deliveryMethodDataCheckService.execute(deliveryMethodEntity);
        } catch (AppLevelListException e) {
            LOGGER.error("例外処理が発生しました", e);
            errorList.addAll(e.getErrorList());
        }

        // ※金額別送料は現在未使用
        if (deliveryRegistUpdateModel.getDeliveryMethodType().equals(HTypeDeliveryMethodType.AMOUNT.getValue())) {
            // 金額別送料のチェック
            try {
                checkAmountCarriage(deliveryRegistUpdateModel);
            } catch (AppLevelListException e) {
                LOGGER.error("例外処理が発生しました", e);
                errorList.addAll(e.getErrorList());
            }
        }

        // 不足金額表示チェック
        try {
            checkShortfallDisplay(deliveryRegistUpdateModel);
        } catch (AppLevelListException e) {
            LOGGER.error("例外処理が発生しました", e);
            errorList.addAll(e.getErrorList());
        }

        if (!errorList.isEmpty()) {
            throw new AppLevelListException(errorList);
        }

    }

    /**
     * 不足金額表示チェック<br/>
     */
    private void checkShortfallDisplay(DeliveryRegistUpdateModel deliveryRegistUpdateModel) {
        BigDecimal discountCarriage = BigDecimalConversionUtil.toBigDecimal(
                        deliveryRegistUpdateModel.getLargeAmountDiscountCarriage());
        if (deliveryRegistUpdateModel.isShortfallDisplayFlag() && (discountCarriage == null
                                                                   || discountCarriage.compareTo(ZERO) != 0)) {
            // フロントでの送料無料メッセージは送料が無料の場合のみ表示するため、表示かつ高額割引送料が0円以外の場合はエラー
            addErrorMessage(MSGCD_NO_SHORTFALL_DISPLAY);
            throwMessage();
        }
    }

    /**
     * 金額別送料のチェック<br/>
     * <pre>
     * ・最低１組の入力が必要。
     * ・ショップ別最大決済金額よりも大きいものがあるか。
     * ・上限金額に同金額のものはないか。
     * </pre>
     */
    protected void checkAmountCarriage(DeliveryRegistUpdateModel deliveryRegistUpdateModel) {
        // ショップ別最大決済金額をシステムプロパティから取得
        BigDecimal orderMaxAmount = BigDecimalConversionUtil.toBigDecimal(
                        PropertiesUtil.getSystemPropertiesValue(ORDER_MAX_AMOUNT_KEY));
        boolean setting = false;
        boolean maxAmountCarriage = false;
        boolean sameAmountCarriage = false;
        List<String> amountCarriageList = new ArrayList<>();

        // ショップ別最大決済金額が0の時は無制限。チェックをしない。
        // if (orderMaxAmount.compareTo(BigDecimal.ZERO) <= 0) {
        // maxAmountCarriage = true;
        // }
        // 配送方法（金額別）の上限金額はショップ最大決済金額よりも小さい可能性もあるため、チェックをしないようにする。
        maxAmountCarriage = true;

        for (DeliveryAmountCarriageItem amountCarriageItem : deliveryRegistUpdateModel.getDeliveryAmountCarriageItems()) {
            String maxPriceStr = amountCarriageItem.getMaxPrice();
            if (!StringUtil.isEmpty(maxPriceStr)) {

                if (!setting) {
                    setting = true;
                }

                if (!sameAmountCarriage) {
                    if (amountCarriageList.contains(maxPriceStr)) {
                        sameAmountCarriage = true;
                        addErrorMessage(MSGCD_SAME_AMOUNT_CARRIAGE);
                    } else {
                        amountCarriageList.add(maxPriceStr);
                    }
                }

                BigDecimal amountCarriage = BigDecimalConversionUtil.toBigDecimal(maxPriceStr);

                // ショップ別最大決済金額よりも大きいものがあった
                if (!maxAmountCarriage && amountCarriage.compareTo(BigDecimal.ZERO) > 0
                    && amountCarriage.compareTo(orderMaxAmount) >= 0) {
                    maxAmountCarriage = true;
                }
            }
        }

        if (!setting) {
            addErrorMessage(MSGCD_NO_SETTING_AMOUNT_CARRIAGE);
        } else if (!maxAmountCarriage) {
            Object[] args = {orderMaxAmount};
            addErrorMessage(MSGCD_ILLEGAL_MAX_AMOUNT_CARRIAGE, args);
        }

        if (hasErrorMessage()) {
            throwMessage();
        }
    }

    /**
     * 金額別送料設定リストを上限金額でソート（昇順）
     */
    protected void sortAmountCarriage(DeliveryRegistUpdateModel deliveryRegistUpdateModel) {
        List<DeliveryAmountCarriageItem> amountCarriageItems =
                        deliveryRegistUpdateModel.getDeliveryAmountCarriageItems();
        DeliveryAmountCarriageItem amountCarriageItem = null;
        DeliveryAmountCarriageItem nextAmountCarriageItem = null;
        BigDecimal maxPrice = null;
        BigDecimal nextMaxPrice = null;
        boolean adjust = false;

        for (int i = 0; i < amountCarriageItems.size(); i++) {
            if (i == amountCarriageItems.size() - 1) {
                if (adjust) {
                    // ソート完了するまで再起
                    sortAmountCarriage(deliveryRegistUpdateModel);
                }

                // ソート完了

            } else {
                amountCarriageItem = amountCarriageItems.get(i);
                nextAmountCarriageItem = amountCarriageItems.get(i + 1);

                if (StringUtil.isEmpty(amountCarriageItem.getMaxPrice()) && !StringUtil.isEmpty(
                                nextAmountCarriageItem.getMaxPrice())) {
                    amountCarriageItems.add(i, amountCarriageItems.remove(i + 1));
                    adjust = true;

                } else if (!StringUtil.isEmpty(amountCarriageItem.getMaxPrice()) && !StringUtil.isEmpty(
                                nextAmountCarriageItem.getMaxPrice())) {
                    maxPrice = BigDecimalConversionUtil.toBigDecimal(amountCarriageItem.getMaxPrice());
                    nextMaxPrice = BigDecimalConversionUtil.toBigDecimal(nextAmountCarriageItem.getMaxPrice());
                    if (maxPrice.compareTo(nextMaxPrice) > 0) {
                        amountCarriageItems.add(i, amountCarriageItems.remove(i + 1));
                        adjust = true;
                    }
                }
            }
        }
    }
}
