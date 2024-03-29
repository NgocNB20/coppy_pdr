/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.validation;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.NumberUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.AbstractReserveModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.ReserveDetailItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.ReserveItem;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.validation.group.ReserveGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods.GoodsReserveModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.OrderReserveModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.validator.HDateValidator;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * セールde予約画面の動的バリデータクラス
 */
@Data
@Component
// 2023-renew No14 from here
public class ReserveValidator implements SmartValidator {

    /**
     * セールde予約商品情報（取りおき商品情報）
     */
    protected static final String FIELD_NAME_RESERVE_INFORMATION_ITEM_LIST = "reserveItem.reserveDetailItemList[";

    /**
     * 商品数量
     */
    protected static final String FIELD_NAME_INPUT_GOODS_COUNT = "].inputGoodsCount";

    /**
     * 取りおきお届け希望日
     */
    protected static final String FIELD_NAME_RESERVE_DELIVERY_DATE = "].reserveDeliveryDate";

    /**
     * エラーコード：数値以外を入力した場合エラー
     */
    public static final String NOT_NUMBER_MESSAGE_ID =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberValidator.NOT_NUMBER_detail";

    /**
     * エラーコード：マイナス値を入力した場合エラー
     */
    public static final String MINUS_NUMBER_MESSAGE_ID =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberValidator.MINUS_NUMBER_detail";

    /**
     * エラーコード：整数以外を入力した場合エラー
     */
    public static final String FRACTION_MESSAGE_ID_MAX_ZERO =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberLengthValidator.FRACTION_MAX_ZERO_detail";

    /**
     * エラーコード：範囲外を入力した場合エラー（1～999）
     */
    public static final String NOT_IN_RANGE_MESSAGE_ID =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HNumberRangeValidator.NOT_IN_RANGE_detail";

    /**
     * エラーコード：お届け希望日に入力があり、数量が未入力の場合エラー
     */
    public static final String MSGCD_NOT_EMPTY =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HRequiredValidator.INPUT_detail";

    /**
     * エラーコード：お届け希望日が予約可能範囲外
     */
    public static final String MSGCD_ERROR_RESERVE_DELIVERY_DATE_OUT_OF_RANGE = "PDR-2023RENEW-14-003-E";

    /**
     * エラーコード：お届け希望日の重複チェック
     */
    public static final String MSGCD_ERROR_RESERVE_DELIVERY_DATE_OVERLAP = "PDR-0028-011-A-E";

    /**
     * FRACTION数
     */
    public static final Integer ORDER_GOODS_COUNT_MAX_FRACTION = 0;

    /**
     * 受注業務ユーティリティクラス
     */
    private final OrderUtility orderUtility;

    /**
     * 変換ユーティリティクラス
     */
    private final ConversionUtility conversionUtility;

    /**
     * 商品系ヘルパークラス
     */
    private final GoodsUtility goodsUtility;

    /**
     * 日付関連ユーティリティクラス
     */
    private final DateUtility dateUtility;

    /**
     * コンストラク
     *
     * @param orderUtility      受注業務ユーティリティクラス
     * @param conversionUtility 変換ユーティリティクラス
     * @param goodsUtility      商品系ヘルパークラス
     * @param dateUtility      日付関連ユーティリティクラス
     */
    @Autowired
    public ReserveValidator(OrderUtility orderUtility,
                            ConversionUtility conversionUtility,
                            GoodsUtility goodsUtility,
                            DateUtility dateUtility) {
        this.orderUtility = orderUtility;
        this.conversionUtility = conversionUtility;
        this.goodsUtility = goodsUtility;
        this.dateUtility = dateUtility;
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {

        if (Objects.equals(validationHints, null)) {
            // 対象groupがない場合、チェックしない
            return;
        }

        if (!ReserveGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupが、ReserveGroup以外の場合、チェックしない
            return;
        }

        AbstractReserveModel reserveModel = AbstractReserveModel.class.cast(target);
        if (CollectionUtil.isEmpty(reserveModel.getReserveItem().getReserveDetailItemList())) {
            return;
        }

        // 数値関連Helper取得
        NumberUtility numberUtility = ApplicationContextUtility.getBean(NumberUtility.class);
        // お届け希望日重複チェック用リスト
        List<String> reserveDeliveryDateList = new ArrayList<>();
        // エラー表示商品名
        String errDispGoodsName =
                        orderUtility.createErrDispGoodsName(reserveModel.getGoodsName(), reserveModel.getUnitValue1(),
                                                            reserveModel.getUnitValue2()
                                                           );
        // 予約可能開始日
        Timestamp possibleReserveFromDay = conversionUtility.toTimeStamp(reserveModel.getPossibleReserveFromDay());
        // 予約可能終了日
        Timestamp possibleReserveToDay = conversionUtility.toTimeStamp(reserveModel.getPossibleReserveToDay());
        // カレンダー選択不可日付List
        List<Timestamp> calendarNotSelectDateList = new ArrayList<>();
        for (String calendarNotSelectDate : reserveModel.getCalendarNotSelectDateList()) {
            calendarNotSelectDateList.add(conversionUtility.toTimeStamp(calendarNotSelectDate));
        }

        for (int i = 0; i < reserveModel.getReserveItem().getReserveDetailItemList().size(); i++) {

            ReserveDetailItem reserveDetailItem = reserveModel.getReserveItem().getReserveDetailItemList().get(i);

            // お届け希望日が未入力の場合、チェック不要
            if (StringUtil.isEmpty(reserveDetailItem.getReserveDeliveryDate())) {
                continue;
            }

            // 数量
            String inputGoodsCount = reserveDetailItem.getInputGoodsCount();
            String fieldNameInputGoodsCount =
                            FIELD_NAME_RESERVE_INFORMATION_ITEM_LIST + i + FIELD_NAME_INPUT_GOODS_COUNT;

            // お届け希望日
            String reserveDeliveryDate = reserveDetailItem.getReserveDeliveryDate();
            String fieldNameReserveDeliveryDate =
                            FIELD_NAME_RESERVE_INFORMATION_ITEM_LIST + i + FIELD_NAME_RESERVE_DELIVERY_DATE;

            // 数量の入力チェック
            checkInputGoodsCount:
            if (inputGoodsCount == null) {
                // お届け希望日に入力があり、数量が未入力の場合エラー
                errors.rejectValue(fieldNameInputGoodsCount, MSGCD_NOT_EMPTY, new String[] {"数量"}, null);
                break checkInputGoodsCount;
            } else {
                // 数値かどうか
                if (!numberUtility.isNumber(inputGoodsCount)) {
                    // 数量が数値でない場合
                    errors.rejectValue(fieldNameInputGoodsCount, NOT_NUMBER_MESSAGE_ID, new String[] {"数量"}, null);
                    break checkInputGoodsCount;
                }
                // 負の数値かどうか
                if (inputGoodsCount.charAt(0) == '-') {
                    // マイナス値を入力した場合エラー
                    errors.rejectValue(fieldNameInputGoodsCount, MINUS_NUMBER_MESSAGE_ID, new String[] {"数量"}, null);
                    break checkInputGoodsCount;
                }
                // 整数以外を入力した場合エラー
                String fraction = inputGoodsCount.substring(inputGoodsCount.indexOf('.') + 1);
                if (inputGoodsCount.indexOf('.') != -1) {
                    if (fraction.length() > ORDER_GOODS_COUNT_MAX_FRACTION) {
                        errors.rejectValue(fieldNameInputGoodsCount, FRACTION_MESSAGE_ID_MAX_ZERO, new String[] {"数量"},
                                           null
                                          );
                        break checkInputGoodsCount;
                    }
                }
                // 1～999以外を入力した場合エラー
                if (Integer.parseInt(inputGoodsCount) < 1 || Integer.parseInt(inputGoodsCount) > 999) {
                    errors.rejectValue(fieldNameInputGoodsCount, NOT_IN_RANGE_MESSAGE_ID,
                                       new String[] {"1", "999", "数量"}, null
                                      );
                    break checkInputGoodsCount;
                }
            }

            // お届け希望日の入力チェック
            checkReserveDeliveryDate:
            if (reserveDeliveryDate != null) {
                // 同じお届け希望日が複数選択されている場合エラー
                if (reserveDeliveryDateList.contains(reserveDeliveryDate)) {
                    errors.rejectValue(fieldNameReserveDeliveryDate, MSGCD_ERROR_RESERVE_DELIVERY_DATE_OVERLAP,
                                       new String[] {errDispGoodsName}, null
                                      );
                    break checkReserveDeliveryDate;
                } else {
                    // 重複チェック用リストに保持
                    reserveDeliveryDateList.add(reserveDeliveryDate);
                }
                // 選択したセールde予約のお届け希望日が予約可能範囲外 又は カレンダー選択不可日付に含まれる場合エラー
                if (dateUtility.isDate(reserveDeliveryDate, HDateValidator.DEFAULT_DATE_PATTERN)) {
                    Timestamp reserveDeliveryDateTimestamp = conversionUtility.toTimeStamp(reserveDeliveryDate);
                    if (reserveDeliveryDateTimestamp == null || (possibleReserveFromDay != null
                                                                 && !reserveDeliveryDateTimestamp.equals(
                                    possibleReserveFromDay) && reserveDeliveryDateTimestamp.before(
                                    possibleReserveFromDay)) || (possibleReserveToDay != null
                                                                 && !reserveDeliveryDateTimestamp.equals(
                                    possibleReserveToDay) && reserveDeliveryDateTimestamp.after(possibleReserveToDay))
                        || calendarNotSelectDateList.contains(reserveDeliveryDateTimestamp)) {
                        errors.rejectValue(fieldNameReserveDeliveryDate,
                                           MSGCD_ERROR_RESERVE_DELIVERY_DATE_OUT_OF_RANGE
                                          );
                        break checkReserveDeliveryDate;
                    }
                }
            }
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // GoodsReserveModel／OrderReserveModelがサポート対象
        // 加えて、各Modelと画面間引き渡し項目である「ReserveItem」も対象に含める
        return GoodsReserveModel.class.isAssignableFrom(clazz) || OrderReserveModel.class.isAssignableFrom(clazz)
               || ReserveItem.class.isAssignableFrom(clazz) || OrderCommonModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }

}
// 2023-renew No14 to here
