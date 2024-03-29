/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation;

import jp.co.hankyuhanshin.itec.hitmall.front.base.newfaces.FacesMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.NumberUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.ReserveItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.ConfirmModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.DeliveryController;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.DeliveryModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.PaymentModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.ReceiverModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.DeliveryNowItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.validation.group.DeliveryGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Objects;

/**
 * 配送方法設定画面：「決済方法を選択する」ボタン押下処理 Validator
 */
@Data
@Component
// PDR Migrate Customization from here
public class DeliveryValidator implements SmartValidator {

    /**
     * 今すぐお届け分
     */
    protected static final String FIELD_NAME_DELIVERY_NOW_ITEMS = "deliveryNowItems[";

    /**
     * 商品数量
     */
    protected static final String FIELD_NAME_ORDER_GOODS_COUNT = "].orderGoodsCount";

    /**
     * 配達時間指定 ヤマト
     */
    protected static final String FIELD_NAME_ORDER_RECEIVER_TIME_ZONE_YAMATO = "receiverTimeZoneYamato";

    /**
     * 配達指定時間日本郵便
     */
    protected static final String FIELD_NAME_ORDER_RECEIVER_TIME_ZONE_JAPAN_POST = "receiverTimeZoneJapanPost";

    /**
     * マイナスの数値だった場合（マイナス不許可だった場合）
     */
    public static final String MINUS_NUMBER_MESSAGE_ID =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberValidator.MINUS_NUMBER_detail";

    /**
     * 数値でない場合
     */
    public static final String NOT_NUMBER_MESSAGE_ID =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberValidator.NOT_NUMBER_detail";

    /**
     * エラーコード：整数
     */
    public static final String FRACTION_MESSAGE_ID_MAX_ZERO =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberLengthValidator.FRACTION_MAX_ZERO_detail";

    /**
     * エラーコード：整数
     */
    public static final String INTEGRAL_MESSAGE_ID_DETAIL =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberLengthValidator.INTEGRAL_detail";

    /**
     * 範囲外
     */
    public static final String NOT_IN_RANGE_MESSAGE_ID =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberRangeValidator.NOT_IN_RANGE_detail";

    /**
     * 空でない
     */
    public static final String MSGCD_NOT_EMPTY = "javax.validation.constraints.NotEmpty.message";

    /**
     * FRACTION数
     */
    public static final Integer ORDER_GOODS_COUNT_MAX_FRACTION = 0;

    /**
     * INTEGRAL
     */
    public static final int INTEGRAL_DEFAULT_MIN = 1;

    /**
     * 受注業務ユーティリティクラス
     */
    private final OrderUtility orderUtility;

    /**
     * コンストラク
     *
     * @param orderUtility      受注業務ユーティリティクラス
     */
    @Autowired
    public DeliveryValidator(OrderUtility orderUtility) {
        this.orderUtility = orderUtility;
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {

        if (Objects.equals(validationHints, null)) {
            // 対象groupがない場合、チェックしない
            return;
        }

        if (!DeliveryGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupが、DeliveryGroup以外の場合、チェックしない
            return;
        }

        DeliveryModel deliveryModel = DeliveryModel.class.cast(target);

        if (deliveryModel.isViewReceiverTimeZoneYamato() && StringUtil.isEmpty(
                        deliveryModel.getReceiverTimeZoneYamato())) {
            errors.rejectValue(FIELD_NAME_ORDER_RECEIVER_TIME_ZONE_YAMATO, MSGCD_NOT_EMPTY);
        }

        if (deliveryModel.isViewReceiverTimeZoneJapanPost() && StringUtil.isEmpty(
                        deliveryModel.getReceiverTimeZoneJapanPost())) {
            errors.rejectValue(FIELD_NAME_ORDER_RECEIVER_TIME_ZONE_JAPAN_POST, MSGCD_NOT_EMPTY);
        }

        // 今すぐお届け商品の入力チェック
        checkDeliveryNowItems(deliveryModel, errors);

    }

    @Override
    public boolean supports(Class<?> clazz) {
        // DeliveryModelがサポート対象
        // 加えて、各Modelと画面間引き渡し項目である「CartDto」も対象に含める
        //
        // ＜各ModelとCartDtoを対象に含める理由＞
        // 各ModelとCartDtoをFlashAttribute渡しにした場合、OrderControllerのInitBinderで落ちてしまうため・・・
        // supports対象でないDtoが渡されると、「バリデータチェックスルー」ではなく、「不正なオブジェクトが渡された」ということで例外発生する
        // ⇒各ModelとCartDtoも一旦supports対象にしつつ、validateをスルーさせる必要があるようだ・・・
        return OrderCommonModel.class.isAssignableFrom(clazz) || CartDto.class.isAssignableFrom(clazz)
               || ReceiverModel.class.isAssignableFrom(clazz) || DeliveryModel.class.isAssignableFrom(clazz)
               || PaymentModel.class.isAssignableFrom(clazz) || ConfirmModel.class.isAssignableFrom(clazz)
               || ReserveItem.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }

    /**
     * 今すぐお届け分の入力チェック
     *
     * @param deliveryModel 配送方法選択画面Model
     * @param errors        エラー
     */
    public void checkDeliveryNowItems(DeliveryModel deliveryModel, Errors errors) {

        // 今すぐお届け商品が存在しない場合はスキップ
        if (deliveryModel.getDeliveryNowItems() == null || deliveryModel.getDeliveryNowItems().isEmpty()) {
            return;
        }

        // 数値関連Helper取得
        NumberUtility numberUtility = ApplicationContextUtility.getBean(NumberUtility.class);

        // 商品ごとに数量チェックかける
        for (int deliveryNowCount = 0;
             deliveryNowCount < deliveryModel.getDeliveryNowItems().size(); deliveryNowCount++) {

            String fieldNameDeliveryNow = FIELD_NAME_DELIVERY_NOW_ITEMS + deliveryNowCount;
            DeliveryNowItem deliveryNowItem = deliveryModel.getDeliveryNowItems().get(deliveryNowCount);

            String orderGoodsCount = deliveryNowItem.getOrderGoodsCount();

            // エラー表示商品名
            String errDispGoodsName = orderUtility.createErrDispGoodsName(deliveryNowItem.getGoodsName(),
                                                                          deliveryNowItem.getUnitValue1(),
                                                                          deliveryNowItem.getUnitValue2()
                                                                         );

            // 数量が入力されているかどうか
            if (deliveryNowItem.getOrderGoodsCount() == null) {
                errors.rejectValue(FIELD_NAME_DELIVERY_NOW_ITEMS + deliveryNowCount + FIELD_NAME_ORDER_GOODS_COUNT,
                                   DeliveryController.MSGCD_INPUT_ERR_COUNT_NULL, new String[] {errDispGoodsName}, null
                                  );
                continue;
            }
            // 数値かどうか
            if (!numberUtility.isNumber(orderGoodsCount)) {
                // 数量が数値でない場合
                errors.rejectValue(fieldNameDeliveryNow + FIELD_NAME_ORDER_GOODS_COUNT, NOT_NUMBER_MESSAGE_ID,
                                   new String[] {"「数量」の値(" + orderGoodsCount + ")"}, null
                                  );
                continue;
            }
            // 負の数値かどうか
            if (orderGoodsCount.charAt(0) == '-') {
                // 数量が整数でない場合
                errors.rejectValue(fieldNameDeliveryNow + FIELD_NAME_ORDER_GOODS_COUNT, MINUS_NUMBER_MESSAGE_ID,
                                   new String[] {"数量"}, null
                                  );
                continue;
            }
            String orderGoodsCountDigits = StringUtils.getDigits(orderGoodsCount);
            String fraction = orderGoodsCount.substring(orderGoodsCount.indexOf('.') + 1);
            boolean integralSuccess = validateIntegral(orderGoodsCountDigits);
            if (orderGoodsCount.indexOf('.') != -1) {
                if (fraction.length() > ORDER_GOODS_COUNT_MAX_FRACTION) {
                    String message = FacesMessageUtil.getMessage(FRACTION_MESSAGE_ID_MAX_ZERO, new String[] {"数量"})
                                                     .getDetail();
                    errors.rejectValue(fieldNameDeliveryNow + FIELD_NAME_ORDER_GOODS_COUNT,
                                       DeliveryController.MSGCD_INPUT_ERR_ANY_NOW,
                                       new String[] {message, errDispGoodsName}, null
                                      );
                    continue;
                }
            }
            if (!integralSuccess) {
                String message = FacesMessageUtil.getMessage(INTEGRAL_MESSAGE_ID_DETAIL,
                                                             new String[] {"数量", String.valueOf(INTEGRAL_DEFAULT_MIN),
                                                                             getIntegerMaxValueString()}
                                                            ).getDetail();
                errors.rejectValue(fieldNameDeliveryNow + FIELD_NAME_ORDER_GOODS_COUNT,
                                   DeliveryController.MSGCD_INPUT_ERR_ANY_NOW, new String[] {message, errDispGoodsName},
                                   null
                                  );
                continue;
            }
            if (Integer.parseInt(orderGoodsCount) < 1 || Integer.parseInt(orderGoodsCount) > 999) {
                String message = FacesMessageUtil.getMessage(NOT_IN_RANGE_MESSAGE_ID, new String[] {"1", "999", "数量"})
                                                 .getDetail();
                errors.rejectValue(fieldNameDeliveryNow + FIELD_NAME_ORDER_GOODS_COUNT,
                                   DeliveryController.MSGCD_INPUT_ERR_ANY_NOW, new String[] {message, errDispGoodsName},
                                   null
                                  );
                continue;
            }
        }
    }

    /**
     * 整数の最大値を取得する
     *
     * @return 文字列
     */
    private static String getIntegerMaxValueString() {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(Integer.MAX_VALUE);
    }

    /**
     * 整数部の桁数チェック
     *
     * @param digits digits
     * @return true..OK / false..NG
     */
    protected boolean validateIntegral(String digits) {
        if (digits.length() < INTEGRAL_DEFAULT_MIN || String.valueOf(Integer.MAX_VALUE).length() < digits.length()) {
            return false;
        }
        return true;
    }

}
// PDR Migrate Customization to here
