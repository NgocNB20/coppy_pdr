/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderGoodsResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringConversionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 受注検索画面Helper<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class OrderHelper {

    /**
     * 日付関連Utility取得
     */
    private final DateUtility dateUtility;

    /**
     * 変換Utility取得
     */
    private final ConversionUtility conversionUtility;

    @Autowired
    public OrderHelper(DateUtility dateUtility, ConversionUtility conversionUtility) {
        this.dateUtility = dateUtility;
        this.conversionUtility = conversionUtility;
    }

    /**
     * 時間種別<br/>
     *
     * @param conditionDto 検索条件Dto
     * @return 時間種別
     */
    protected String getTimeType(OrderSearchConditionDto conditionDto) {

        // 指定なし 又は数字でない場合 null
        if (!StringUtil.isNumber(conditionDto.getTimeType())) {
            return null;
        }

        // 時間で
        String timeType = null;
        Integer type = Integer.valueOf(conditionDto.getTimeType());
        if (type < 1) {
            /* 0 */
            timeType = null;
        } else if (type < 4) {
            /* 1～3 */
            timeType = "1";
        } else if (type < 7) {
            /* 4～6 */
            timeType = "2";
        } else if (type < 10) {
            /* 7～9 */
            timeType = "3";
        } else if (type < 13) {
            /* 10～12 */
            timeType = "4";
        } else if (type < 16) {
            /* 13～15 */
            timeType = "5";
        } else if (type < 19) {
            /* 16～18 */
            timeType = "6";
        } else if (type < 22) {
            /* 19～21 */
            timeType = "7";
        }
        return timeType;
    }

    /**
     * 検索結果をページに反映<br/>
     *
     * @param resultDtoList 検索結果リスト
     * @param orderModel    受注検索ページ
     */
    public void toPageForSearch(List<OrderSearchOrderResultDto> resultDtoList,
                                OrderModel orderModel,
                                OrderSearchConditionDto orderSearchConditionDto) {

        // オフセット + 1をNoにセット
        int index = orderSearchConditionDto.getPageInfo().getOffset() + 1;
        List<OrderModelItem> resultItems = new ArrayList<>();
        Iterator<OrderSearchOrderResultDto> ite = resultDtoList.iterator();
        for (; ite.hasNext(); index++) {
            OrderModelItem orderModelItem = ApplicationContextUtility.getBean(OrderModelItem.class);
            OrderSearchOrderResultDto resultDto = ite.next();

            orderModelItem.setResultNo(Integer.valueOf(index));

            // 受注SEQ
            orderModelItem.setResultOrderSeq(resultDto.getOrderSeq());
            // 受注履歴連番
            orderModelItem.setResultOrderVersionNo(resultDto.getOrderVersionNo());
            // 注文連番
            orderModelItem.setResultOrderConsecutiveNo(resultDto.getOrderConsecutiveNo());
            // 受注番号
            orderModelItem.setOrderCode(resultDto.getOrderCode());
            // 受注種別
            orderModelItem.setOrderType(resultDto.getOrderType().getValue());
            // 受注日時
            orderModelItem.setResultOrderTime(resultDto.getOrderTime());
            // 受注状態
            orderModelItem.setResultOrderStatusForSearchResult(resultDto.getOrderStatusForSearchResult());
            // ご注文主氏名
            orderModelItem.setResultOrderName(resultDto.getOrderName());
            // お届け先氏名
            orderModelItem.setResultReceiverName(resultDto.getReceiverName());
            // 受注金額
            orderModelItem.setResultOrderPrice(resultDto.getOrderPrice());
            // 決済方法
            orderModelItem.setResultSettlementMethod(resultDto.getSettlementMethodName());
            //伝票番号
            orderModelItem.setRegisterDeliveryCode(resultDto.getDeliveryCode());
            // 出荷日
            if (resultDto.getShipmentdate() != null) {
                orderModelItem.setShippedDate(resultDto.getShipmentdate());
            }
            // 出荷状態
            String shipmentStatus = resultDto.getShipmentStatus();
            if (shipmentStatus != null) {
                orderModelItem.setResultShipmentStatus(shipmentStatus);
                HTypeShipmentStatus shipmentStatusType =
                                EnumTypeUtil.getEnumFromValue(HTypeShipmentStatus.class, shipmentStatus);
                if (HTypeShipmentStatus.UNSHIPMENT.equals(shipmentStatusType)) {
                    orderModelItem.setShipmentStatusStyleClass(orderModel.getUnShipmentStyleClass());
                } else if (HTypeShipmentStatus.SHIPPED.equals(shipmentStatusType)) {
                    orderModelItem.setShipmentStatusStyleClass(orderModel.getShipedStyleClass());
                }
            }
            // 入金状態
            String paymentStatus = resultDto.getPaymentStatus();
            orderModelItem.setResultPaymentStatus(paymentStatus);
            if ("1".equals(paymentStatus)) {
                orderModelItem.setPaymentStatusStyleClass(orderModel.getUnPaidStyleClass());
            } else if ("2".equals(paymentStatus)) {
                orderModelItem.setPaymentStatusStyleClass(orderModel.getPaidStyleClass());
            } else if ("3".equals(paymentStatus)) {
                orderModelItem.setPaymentStatusStyleClass(orderModel.getPayingStyleClass());
            }

            // 入金累計
            orderModelItem.setResultReceiptPriceTotal(resultDto.getReceiptPriceTotal());
            // 入金日時
            orderModelItem.setResultReceiptTime(resultDto.getReceiptTime());
            // 配送方法
            orderModelItem.setResultDeliveryMethod(resultDto.getDeliveryMethodName());
            // サイト
            if (resultDto.getOrderSiteType() != null) {
                orderModelItem.setResultOrderSiteType(resultDto.getOrderSiteType().getValue());
            }
            // 備考
            if (resultDto.getDeliveryNote() != null) {
                int endIndex = 10;
                if (endIndex > resultDto.getDeliveryNote().length()) {
                    orderModelItem.setResultDeliveryNote(resultDto.getDeliveryNote());
                } else {
                    orderModelItem.setResultDeliveryNote(resultDto.getDeliveryNote().substring(0, endIndex));
                }
            }
            // 予約配送
            if (resultDto.getReservationDeliveryFlag() == null) {
                orderModelItem.setReservationDeliveryFlag(null);
            } else {
                orderModelItem.setReservationDeliveryFlag(resultDto.getReservationDeliveryFlag().getValue());
            }

            resultItems.add(orderModelItem);
        }
        orderModel.setResultItems(resultItems);
    }

    /**
     * 検索結果をページに反映(商品検索用)<br/>
     *
     * @param resultList              検索結果リスト
     * @param orderModel              受注検索ページ
     * @param orderSearchConditionDto 検索条件
     */
    public void toPageForSearchGoods(List<OrderSearchOrderGoodsResultDto> resultList,
                                     OrderModel orderModel,
                                     OrderSearchConditionDto orderSearchConditionDto) {
        // オフセット + 1をNoにセット
        int index = orderSearchConditionDto.getPageInfo().getOffset() + 1;

        List<OrderModelItem> resultItems = new ArrayList<>();
        List<Integer> sessionGoodsSeqList = new ArrayList<>();

        for (OrderSearchOrderGoodsResultDto orderSearchOrderGoodsResultDto : resultList) {
            OrderModelItem orderModelItem = ApplicationContextUtility.getBean(OrderModelItem.class);

            orderModelItem.setResultNo(conversionUtility.toInteger(index++));

            /** 商品SEQ */
            orderModelItem.setResultGoodsSeq(orderSearchOrderGoodsResultDto.getGoodsSeq());
            /** 商品管理番号 */
            orderModelItem.setResultGoodsGroupCode(orderSearchOrderGoodsResultDto.getGoodsGroupCode());
            /** 商品番号 */
            orderModelItem.setResultGoodsCode(orderSearchOrderGoodsResultDto.getGoodsCode());
            /** JANコード */
            orderModelItem.setResultJanCode(orderSearchOrderGoodsResultDto.getJanCode());
            /** カタログコード */
            orderModelItem.setResultCatalogCode(orderSearchOrderGoodsResultDto.getCatalogCode());
            /** 商品名 */
            orderModelItem.setResultGoodsName(orderSearchOrderGoodsResultDto.getGoodsGroupName());
            /** 規格１*/
            orderModelItem.setResultUnitValue1(orderSearchOrderGoodsResultDto.getUnitValue1());
            /** 規格２*/
            orderModelItem.setResultUnitValue2(orderSearchOrderGoodsResultDto.getUnitValue2());
            /** 商品個別配送種別 */
            orderModelItem.setResultIndividualDeliveryType(
                            orderSearchOrderGoodsResultDto.getIndividualDeliveryType().getValue());
            /** 商品単価 */
            orderModelItem.setResultGoodsPrice(orderSearchOrderGoodsResultDto.getGoodsPrice());
            /** 購入数 */
            orderModelItem.setResultGoodsCount(orderSearchOrderGoodsResultDto.getGoodsCount());

            resultItems.add(orderModelItem);
            sessionGoodsSeqList.add(orderSearchOrderGoodsResultDto.getGoodsSeq());
        }
        orderModel.setResultItems(resultItems);
        orderModel.setSessionGoodsSeqList(sessionGoodsSeqList);
    }

    /**
     * 受注検索一覧用条件Dtoをページにセット
     *
     * @param orderModel 受注検索ページ
     */
    public void toOrderSearchListConditionDtoForPage(OrderModel orderModel) {
        orderModel.setOrderSearchConditionDto(toOrderSearchListConditionDto(orderModel));
    }

    /**
     * 受注検索一覧用条件Dto作成
     *
     * @param orderModel 受注検索ページ
     * @return orderSearchListConditionDto 受注検索一覧用条件Dto
     */
    public OrderSearchConditionDto toOrderSearchListConditionDto(OrderModel orderModel) {

        OrderSearchConditionDto orderSearchListConditionDto =
                        ApplicationContextUtility.getBean(OrderSearchConditionDto.class);
        // メッセージコードリスト初期化
        orderModel.setMsgCodeList(new ArrayList<>());
        orderModel.setMsgArgMap(new HashMap<>());

        // サイト
        orderSearchListConditionDto.setOrderSiteTypeList(Arrays.asList(orderModel.getOrderSiteTypeArray()));
        // 受注番号
        orderSearchListConditionDto.setOrderCode(orderModel.getConditionOrderCode());
        // 受注種別
        if (orderModel.getOrderTypeArray() != null && orderModel.getOrderTypeArray().length > 0) {
            orderSearchListConditionDto.setOrderTypeList(Arrays.asList(orderModel.getOrderTypeArray()));
        }
        // 受注番号（複数番号検索用）
        if (orderModel.getConditionOrderCodeList() != null && StringUtil.isNotEmpty(
                        orderModel.getConditionOrderCodeList().replaceAll("[\\s|　]", ""))) {
            // 検索に有効な文字列が存在する場合
            // 選択肢区切り文字を設定ファイルから取得
            String divchar = PropertiesUtil.getSystemPropertiesValue("order.search.order.code.list.divchar");
            String orderCodeList = orderModel.getConditionOrderCodeList();
            // 空白を削除する
            orderCodeList = orderCodeList.replaceAll("[ 　\t\\x0B\f]", "");
            // 2つ以上連続した改行コードを1つにまとめる
            orderCodeList = orderCodeList.replaceAll("(" + divchar + "){2,}", "\n");
            // 先頭または最後尾の改行コードを削除する
            orderCodeList = orderCodeList.replaceAll("^[" + divchar + "]+|[" + divchar + "]$", "");
            // 検索用複数番号を配列化する
            String[] orderCodeArray = orderCodeList.split(divchar);
            if (orderCodeArray.length > 0) {
                orderSearchListConditionDto.setOrderCodeList(Arrays.asList(orderCodeArray));

                // 受注番号桁数チェック
                for (String orderCode : orderCodeArray) {
                    if (orderCode.length() > 12) {
                        orderModel.getMsgCodeList().add("AOX000114E");
                        orderModel.getMsgArgMap().put("AOX000114E", new String[] {"受注番号(複数検索用)"});
                        break;
                    }
                }

                // 受注番号数チェック(最大番号数はプロパティから取得)
                if (orderCodeArray.length > OrderModel.CONDITION_ORDER_CODE_LIST_LIMIT) {
                    orderModel.getMsgCodeList().add("AOX000115E");
                    orderModel.getMsgArgMap()
                              .put("AOX000115E", new String[] {Integer.toString(
                                              OrderModel.CONDITION_ORDER_CODE_LIST_LIMIT), "受注番号(複数検索用)"});
                }
            }
        }

        // 支払期限切れ
        orderSearchListConditionDto.setTimeLimitOver(orderModel.getTimeLimitOver());
        // 受注状態
        // ココでの受注状態は検索条件の受注状態を表す（core/src/main/resources/selectMap.dicon を参照）
        if ("5".equals(orderModel.getOrderStatus())) {
            // キャンセル
            orderSearchListConditionDto.setCancelFlag("1");
        } else if ("6".equals(orderModel.getOrderStatus())) {
            // 保留中
            orderSearchListConditionDto.setCancelFlag("0");
            orderSearchListConditionDto.setWaitingFlag("1");
        } else if ("7".equals(orderModel.getOrderStatus())) {
            // 請求決済エラー
            orderSearchListConditionDto.setEmergencyFlag("1");
        } else if ("8".equals(orderModel.getOrderStatus())) {
            // キャンセル以外
            orderSearchListConditionDto.setCancelFlag("0");
        } else if (orderModel.getOrderStatus() != null) {
            // 「入金確認中」、「商品準備中」、「出荷完了」
            orderSearchListConditionDto.setCancelFlag("0");
            orderSearchListConditionDto.setWaitingFlag("0");
            orderSearchListConditionDto.setEmergencyFlag("0");
            orderSearchListConditionDto.setOrderStatus(orderModel.getOrderStatus());
        }
        // 期間
        Integer timetype = conversionUtility.toInteger(orderModel.getTimeType());

        if (isNotSearchPeriodTime(orderModel.getTimeType())) {
            // 検索対象項目が時間指定なしの項目の場合、期間の時間を初期化する
            // From（時間）
            orderModel.setTimeFrom(null);
            // To（時間）
            orderModel.setTimeTo(null);
        } else {
            if (timetype != null) {
                // 検索対象項目が時間指定あり、かつ時間が未入力の場合、期間の時間を補完する
                // From（時間）
                if (!StringUtil.isEmpty(orderModel.getDateFrom()) && StringUtil.isEmpty(orderModel.getTimeFrom())) {
                    orderModel.setTimeFrom("00:00");
                }
                // To（時間）
                if (!StringUtil.isEmpty(orderModel.getDateTo()) && StringUtil.isEmpty(orderModel.getTimeTo())) {
                    orderModel.setTimeTo("23:59");
                }
            }
        }

        Timestamp fromTime = null;
        Timestamp toTime = null;
        if (!StringUtil.isEmpty(orderModel.getDateFrom())) {
            fromTime = conversionUtility.toTimeStampWithDefaultHms(orderModel.getDateFrom(),
                                                                   conversionUtility.addStartSecond(
                                                                                   orderModel.getTimeFrom()),
                                                                   ConversionUtility.DEFAULT_START_TIME
                                                                  );
        }
        if (!StringUtil.isEmpty(orderModel.getDateTo())) {
            toTime = conversionUtility.toTimeStampWithDefaultHms(orderModel.getDateTo(),
                                                                 conversionUtility.addEndSecond(orderModel.getTimeTo()),
                                                                 ConversionUtility.DEFAULT_END_TIME
                                                                );
        }

        if ((fromTime != null || toTime != null) && timetype == null) {
            orderModel.getMsgCodeList().add("AOX000105");
        }

        Integer type = null;
        if (timetype != null) {
            switch (timetype) {
                case 1: // 受注日時
                    type = 1;
                    break;
                case 2: // 出荷登録日時
                    type = 4;
                    break;
                case 3: // 入金日時
                    type = 7;
                    break;
                case 4: // 出金日時
                    type = 10;
                    break;
                case 5: // 更新日時
                    type = 13;
                    break;
                case 6: // 支払期限日
                    type = 16;
                    break;
                case 7: // お届け希望日
                    type = 19;
                    break;
                default:
                    type = null;
                    break;
            }

            if (fromTime != null && toTime != null) {
            } else if (fromTime != null) {
                type++;
                toTime = null;
            } else if (toTime != null) {
                type += 2;
                fromTime = null;
            } else {
                type = null;
                fromTime = null;
                toTime = null;
                orderModel.getMsgCodeList().add("AOX000101");
            }
            if (type != null) {
                orderSearchListConditionDto.setTimeType(type.toString());
            } else {
                orderSearchListConditionDto.setTimeType(null);
            }
            orderSearchListConditionDto.setTimeFrom(fromTime);
            orderSearchListConditionDto.setTimeTo(toTime);
        }

        // 会員情報
        // 会員SEQ
        if (StringUtil.isNotEmpty(orderModel.getMemberInfoSeq())) {
            orderSearchListConditionDto.setMemberInfoSeq(Integer.valueOf(orderModel.getMemberInfoSeq()));
        }

        // お客様／お届け先情報
        // 顧客
        if ("1".equals(orderModel.getOrderPerson())) {
            // お客様 //
            // 名前
            String orderName = null;
            if (orderModel.getOrderName() != null) {
                orderName = orderModel.getOrderName().replace(" ", "").replace("　", "");
                orderSearchListConditionDto.setOrderName(orderName);
            }
            // TEL
            if (orderModel.getOrderTel() != null) {
                orderSearchListConditionDto.setOrderTel(orderModel.getOrderTel());
            }
            // E-MAIL
            if (orderModel.getOrderMail() != null) {
                orderSearchListConditionDto.setOrderMail(orderModel.getOrderMail());
            }
        } else if ("2".equals(orderModel.getOrderPerson())) {
            // お届け先 //
            // 名前
            String orderName = null;
            if (orderModel.getOrderName() != null) {
                orderName = orderModel.getOrderName().replace(" ", "").replace("　", "");
            }
            orderSearchListConditionDto.setReceiverName(orderName);
            // TEL
            orderSearchListConditionDto.setReceiverTel(orderModel.getOrderTel());
            if (orderModel.getOrderName() == null && orderModel.getOrderTel() == null) {
                orderModel.getMsgCodeList().add("AOX000102");
            }
            if (orderModel.getOrderMail() != null) {
                orderModel.getMsgCodeList().add("AOX000107");
            }
        } else if (orderModel.getOrderName() != null || orderModel.getOrderTel() != null
                   || orderModel.getOrderMail() != null) {
            orderModel.getMsgCodeList().add("AOX000106");
        }

        // 商品情報
        // 商品管理番号
        orderSearchListConditionDto.setGoodsGroupCode(orderModel.getGoodsGroupCode());
        // 商品番号
        orderSearchListConditionDto.setGoodsCode(orderModel.getGoodsCode());
        // JANコード
        orderSearchListConditionDto.setJanCode(orderModel.getJanCode());
        // カタログコード
        orderSearchListConditionDto.setCatalogCode(orderModel.getCatalogCode());
        // 商品名
        orderSearchListConditionDto.setGoodsGroupName(orderModel.getGoodsGroupName());
        // キャンセルした商品を含むフラグ
        orderSearchListConditionDto.setIncludeCancelGoodsFlag(orderModel.isIncludeCancelGoodsFlag());
        // 販売開始日
        if (StringUtil.isNotEmpty(orderModel.getSaleStartDateFrom())) {
            orderSearchListConditionDto.setSaleStartTimeFrom(
                            conversionUtility.toTimeStampWithDefaultHms(orderModel.getSaleStartDateFrom(),
                                                                        conversionUtility.addStartSecond(
                                                                                        orderModel.getSaleStartTimeFrom()),
                                                                        ConversionUtility.DEFAULT_START_TIME
                                                                       ));
        }
        // 販売開始時間
        if (StringUtil.isNotEmpty(orderModel.getSaleStartDateTo())) {
            orderSearchListConditionDto.setSaleStartTimeTo(
                            conversionUtility.toTimeStampWithDefaultHms(orderModel.getSaleStartDateTo(),
                                                                        conversionUtility.addEndSecond(
                                                                                        orderModel.getSaleStartTimeTo()),
                                                                        ConversionUtility.DEFAULT_END_TIME
                                                                       ));
        }

        // 配送方法
        if (orderModel.getDelivery() != null && !"".equals(orderModel.getDelivery())) {
            orderSearchListConditionDto.setDeliveryMethodSeq(Integer.valueOf(orderModel.getDelivery()));
        }
        // 伝票番号
        orderSearchListConditionDto.setDeliveryCode(orderModel.getDeliveryCode());

        // 出荷状態
        if (orderModel.getShipmentStatus() != null && orderModel.getShipmentStatus().length > 0) {
            orderSearchListConditionDto.setShipmentStatus(Arrays.asList(orderModel.getShipmentStatus()));
        } else {
            orderSearchListConditionDto.setShipmentStatus(null);
        }

        // 決済方法
        if (orderModel.getSettlementSelect().intValue() == 0) {
            // 決済方法
            if (orderModel.getSettlememnt() != null && !"".equals(orderModel.getSettlememnt())) {
                orderSearchListConditionDto.setSettlementMethodSeq(Integer.valueOf(orderModel.getSettlememnt()));
            }
        } else {
            // 請求種別
            orderSearchListConditionDto.setBillType(orderModel.getBillType());
        }
        // 決済方法
        orderSearchListConditionDto.setSettlementSelect(orderModel.getSettlementSelect());
        // 請求状態
        orderSearchListConditionDto.setBillStatus(orderModel.getBillStatus());
        // 入金状態
        orderSearchListConditionDto.setPaymentStatus(orderModel.getPaymentStatus());

        // クーポン
        orderSearchListConditionDto.setCouponSelect(orderModel.getCouponSelect());
        if (OrderModel.COUPON_SELECT_COUPON_ID.equals(orderModel.getCouponSelect())) {
            // クーポンID
            orderSearchListConditionDto.setCouponId(orderModel.getCoupon());
        } else {
            // クーポンコード
            orderSearchListConditionDto.setCouponCode(orderModel.getCoupon());
        }

        // 処理フラグ
        orderSearchListConditionDto.setShipmentSearch(checkBoxCheck(orderModel.isShipmentRegister()));

        return orderSearchListConditionDto;
    }

    /**
     * ページ遷移情報設定<br/>
     *
     * @param orderModel 受注検索ページ
     */
    public void toOrderSearchListConditionDtoDisplayChange(OrderModel orderModel) {
        // 検索条件にページ情報設定
        if (orderModel.getOrderSearchConditionDto() != null) {
            orderModel.getOrderSearchConditionDto().setPageInfo(orderModel.getOrderSearchConditionDto().getPageInfo());
        }
        // 検索条件作成
        toOrderSearchListConditionDtoForPage(orderModel);

    }

    /**
     * 会員詳細画面から受注検索画面に遷移した場合、会員SEQで検索を行う<br/>
     * 必要な値をセットしておく
     *
     * @param orderModel 受注検索ページ
     */
    public void toOrderSearchWhenMemberInfoDetails(OrderModel orderModel) {
        orderModel.setIndividualDeliveryType(false);
    }

    /**
     * 出荷登録処理内容のリストを作成
     *
     * @param orderModel 受注検索ページ
     * @return 出荷登録DTOリスト
     */
    public List<ShipmentRegistDto> toShipmentRegistDtoForRegist(OrderModel orderModel) {

        List<ShipmentRegistDto> paymentRegistDtoList = new ArrayList<>();

        List<OrderModelItem> resultItems = orderModel.getResultItems();
        if (resultItems == null) {
            return null;
        }

        for (OrderModelItem orderModelItem : resultItems) {
            if (orderModelItem.isResultOrderCheck()) {
                ShipmentRegistDto shipmentRegistDto = ApplicationContextUtility.getBean(ShipmentRegistDto.class);
                shipmentRegistDto.setOrderCode(orderModelItem.getOrderCode());
                shipmentRegistDto.setOrderConsecutiveNo(orderModelItem.getResultOrderConsecutiveNo());
                shipmentRegistDto.setShipmentDate(
                                conversionUtility.toTimeStamp(orderModelItem.getRegisterShipmentDate()));
                shipmentRegistDto.setDeliveryCode(orderModelItem.getRegisterDeliveryCode());
                shipmentRegistDto.setSendMailFlag(orderModelItem.getNotSendMailFlag());
                paymentRegistDtoList.add(shipmentRegistDto);
            }
        }

        return paymentRegistDtoList;
    }

    /**
     * 処理結果のメッセージを作成<br/>
     *
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     * @param orderModel  受注検索ページ
     */
    public void setFinishPageItem(String messageCode, Object[] args, OrderModel orderModel) {
        List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();
        orderModel.setCheckMessageItems(checkMessageDtoList);
        addFinishPageItem(messageCode, args, orderModel);
    }

    /**
     * 処理結果のメッセージを追加<br/>
     *
     * @param messageCode メッセージコード
     * @param args        メッセージ引数
     * @param orderModel  受注検索ページ
     */
    public void addFinishPageItem(String messageCode, Object[] args, OrderModel orderModel) {
        List<CheckMessageDto> checkMessageDtoList = orderModel.getCheckMessageItems();
        if (checkMessageDtoList == null) {
            checkMessageDtoList = new ArrayList<>();
        }
        String message = AppLevelFacesMessageUtil.getAllMessage(messageCode, args).getMessage();
        CheckMessageDto checkMessageDto = ApplicationContextUtility.getBean(CheckMessageDto.class);
        checkMessageDto.setMessage(message);
        checkMessageDto.setMessageId(messageCode);
        checkMessageDtoList.add(checkMessageDto);
    }

    /*
     * クラス内共通処理
     */

    /**
     * 単一チェックボックスのプロパティの値をチェックする<br/>
     *
     * @param checkboxProperty 単一チェックボックスのプロパティ
     * @return true:"1",false:"0"
     */
    protected String checkBoxCheck(Boolean checkboxProperty) {
        String res = "0";
        if (checkboxProperty != null && checkboxProperty) {
            res = "1";
        }
        return res;
    }

    /**
     * メソッド概要<br/>
     * メソッドの説明・概要<br/>
     *
     * @param map Items
     * @param seq SEQ
     * @return ラベル
     */
    protected String getItemName(Map<String, String> map, Integer seq) {
        if (map.containsValue(seq.toString())) {
            return map.get("label").toString();
        }
        return null;
    }

    /**
     * メソッド概要<br/>
     * メソッドの説明・概要<br/>
     *
     * @param map Items
     * @param seq SEQ
     * @return ラベル
     */
    protected String getItemNameStr(Map<String, String> map, Integer seq) {
        if (map.containsValue(seq.toString())) {
            return map.get("label").toString();
        }
        return null;
    }

    /**
     * 選択された受注を受注SEQリストに変換します
     *
     * @param orderModel OrderModel
     * @return List&lt;Integer&gt;
     */
    public List<Integer> convertToListForSearch(OrderModel orderModel) {
        List<OrderModelItem> resultItems = orderModel.getResultItems();
        List<Integer> orderSeqList = new ArrayList<>();
        if (resultItems != null && !resultItems.isEmpty()) {
            for (OrderModelItem orderModelItem : resultItems) {
                if (orderModelItem.isResultOrderCheck()) {
                    if (orderModelItem.getResultOrderSeq() == null) {
                        continue;
                    }
                    orderSeqList.add(orderModelItem.getResultOrderSeq());
                }
            }
        }

        return orderSeqList;
    }

    /**
     * 選択された受注を受注SEQ + 注文連番のリストに変換します
     *
     * @param orderModel OrderModel
     * @return List&lt;Integer&gt;
     */
    public List<String> convertToListForSearchShipment(OrderModel orderModel) {
        List<OrderModelItem> resultItems = orderModel.getResultItems();
        List<String> orderSeqList = new ArrayList<>();
        if (resultItems != null && !resultItems.isEmpty()) {
            for (OrderModelItem orderModelItem : resultItems) {
                if (orderModelItem.isResultOrderCheck()) {
                    if (orderModelItem.getResultOrderSeq() == null) {
                        continue;
                    }
                    orderSeqList.add(StringConversionUtil.toString(orderModelItem.getResultOrderSeq())
                                     + StringConversionUtil.toString(orderModelItem.getResultOrderConsecutiveNo()));
                }
            }
        }

        return orderSeqList;
    }

    /**
     * 「\」を「\\」エスケープします
     *
     * @param target String
     * @return String
     */
    public String escapeStr(String target) {
        if (StringUtil.isEmpty(target)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < target.length(); i++) {
            if (target.charAt(i) == '\\') {
                builder.append("\\\\");
            } else {
                builder.append(target.charAt(i));
            }
        }
        return builder.toString();
    }

    /**
     * 単一チェックボックスのプロパティの値をチェックする<br/>
     *
     * @param value 値
     * @return true:"1",false:"0"
     */
    protected Boolean checkBoxCheck(String value) {
        if (value != null && "1".equals(value)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 検索条件：期間で時間指定の有無を判定する<br/>
     *
     * @param periodType 期間種別
     * @return true:時間指定なし,false:時間指定あり
     */
    protected boolean isNotSearchPeriodTime(String periodType) {
        // 検索条件：期間の期間種別が「支払期限日」、「お届け希望日」の場合、時間指定なし
        if ("6".equals(periodType) || "7".equals(periodType)) {
            return true;
        }
        return false;
    }

}
