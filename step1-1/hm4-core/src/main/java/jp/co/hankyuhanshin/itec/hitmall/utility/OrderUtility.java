/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAllocationDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePaymentMethod;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReservationDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUseConveni;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.OrderRegistable;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 受注業務ユーティリティクラス
 *
 * @author negishi
 * @author Kaneko (itec) 2012/08/20 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class OrderUtility {

    /**
     * 無料決済SEQ（設定値）
     */
    protected static final String FREE_SETTLEMENT_METHOD_SEQ = "free.settlement.method.seq";

    /**
     * 成人確認用　基準年齢
     */
    protected static final int ADULT_AGE = 20;

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderUtility.class);

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    // PDR Migrate Customization from here

    /**
     * ダミー配送方法SEQ
     */
    public static final String DUMMY_DELIVERY_METHOD_SEQ = "1001";

    /**
     * システムエラー発生
     * <code>MSGCD_SYSTEM_ERR</code>
     */
    protected static final String MSGCD_SYSTEM_ERR = "LOX000102";

    /**
     * 商品系ヘルパークラス
     */
    private final GoodsUtility goodsUtility;

    @Autowired
    public OrderUtility(ConversionUtility conversionUtility, DateUtility dateUtility, GoodsUtility goodsUtility) {
        this.conversionUtility = conversionUtility;
        this.dateUtility = dateUtility;
        this.goodsUtility = goodsUtility;
    }

    // PDR Migrate Customization to here

    /**
     * 注文エラーメール送信用パラメータ保持クラス<br/>
     *
     * @author sm21603
     */
    @Data
    protected static class OrderRegistAlertMailProperties {
        /**
         * メールサーバ
         */
        private String mailServer;

        /**
         * メールのFROM
         */
        private String mailFrom;

        /**
         * アラートメール受信者
         */
        private String[] recipients;

        /**
         * アラートメール対象外エラーコード
         */
        private List<String> excludeErrorCode;

        /**
         * メールに記載するシステム名
         */
        private String mailSystem;
    }

    /**
     * 注文エラーメール送信用パラメータ取得
     *
     * @return 注文エラーメール送信用パラメータ保持クラス
     */
    protected OrderRegistAlertMailProperties getOrderRegistAlertMailProperties() {
        OrderRegistAlertMailProperties mailProps = new OrderRegistAlertMailProperties();
        mailProps.setMailServer(PropertiesUtil.getSystemPropertiesValue("mail_server"));
        mailProps.setMailFrom(PropertiesUtil.getSystemPropertiesValue("mail_from"));
        mailProps.setRecipients(
                        PropertiesUtil.getSystemPropertiesValue("recipient").replaceAll("\"", "").split(" *, *"));
        mailProps.setExcludeErrorCode(new ArrayList<>());
        String excludeErrorCodes = PropertiesUtil.getSystemPropertiesValue("exclude_error_code");
        if (StringUtils.isNotEmpty(excludeErrorCodes)) {
            String[] excludeErrorCodeArray = excludeErrorCodes.replaceAll("\"", "").split(" *, *");
            mailProps.getExcludeErrorCode().addAll(Arrays.asList(excludeErrorCodeArray));
        }
        mailProps.setMailSystem(PropertiesUtil.getSystemPropertiesValue("mail_system"));
        return mailProps;
    }

    /**
     * 受注コードを生成します<br/>
     * <pre>
     * メソッド内で現在日時を取得してしまうと、１登録処理でこのメソッドが複数呼ばれた場合
     * 受注コードが変わってしまう可能性があるので、同じ現在日時を渡して下さい。
     * </pre>
     *
     * @param orderSeq    受注SEQ
     * @param currentTime 現在日時
     * @return YYMMDD + 受注SEQの下6桁（6桁より少ない場合は頭0詰めの6桁）
     */
    public String generateOrderCode(Integer orderSeq, Timestamp currentTime) {

        String date = conversionUtility.toYmd(currentTime);
        date = date.replaceAll("/", "").substring(2);

        // 受注コードプレフィックスをシステムプロパティから取得
        String orderCodePrefix = PropertiesUtil.getSystemPropertiesValue("order.code.prefix");
        if (orderCodePrefix.length() > 3) {
            // 3桁の場合は超過部分を切り捨て
            orderCodePrefix = orderCodePrefix.substring(0, 3);
        }
        String orderSeqStr = orderSeq.toString();
        if (orderSeqStr.length() > 6 - orderCodePrefix.length()) {
            orderSeqStr = orderSeqStr.substring((orderSeqStr.length() - 6 + orderCodePrefix.length()));
        } else {
            orderSeqStr = StringUtils.leftPad(orderSeqStr, 6 - orderCodePrefix.length(), '0');
        }

        return orderCodePrefix + date + orderSeqStr;
    }

    /**
     * 購入者が会員であるか判定する。
     *
     * @param order 受注DTO
     * @return 購入者が会員である場合 true
     */
    public boolean isMember(String memberInfoId, HTypeSiteType siteType, ReceiveOrderDto order) {
        if (siteType.isBack()) {
            // 管理サイトの場合、受注ご注文主に会員SEQが設定されているかで判断する。
            // ゲストの場合、新規受注時：会員SEQがnull, 受注修正時:会員SEQが0となる

            // 受注ご注文主の会員SEQ
            Integer memberInfoSeq = order.getOrderPersonEntity().getMemberInfoSeq();
            return memberInfoSeq != null && memberInfoSeq != 0;
        } else {
            // フロントの場合、ログインしているかどうかで判断する。
            return StringUtils.isNotEmpty(memberInfoId);
        }
    }

    /**
     * 購入者の会員SEQを取得する。
     *
     * @param memberInfoId         会員ID
     * @param memberInfoSeqRequest 会員情報SEQ
     * @param siteType             サイト区分
     * @param order                受注DTO
     * @return バックの場合、ご注文主情報の会員SEQ。フロントでログインしている場合、共通情報の会員SEQ。
     * それ以外の場合と、入力値不正の場合はnull。
     */
    public Integer getOrderMemberInfoSeq(String memberInfoId,
                                         Integer memberInfoSeqRequest,
                                         HTypeSiteType siteType,
                                         ReceiveOrderDto order) {
        // 購入者の会員SEQ
        Integer memberInfoSeq = null;
        // 入力値チェック
        if (order == null) {
            return memberInfoSeq;
        }

        // バックの場合、ご注文主情報の会員SEQ設定
        if (siteType.isBack() && order.getOrderPersonEntity() != null) {
            memberInfoSeq = order.getOrderPersonEntity().getMemberInfoSeq();
            // フロントでログインしている場合、共通情報の会員SEQ設定
        } else if (StringUtils.isNotEmpty(memberInfoId)) {
            memberInfoSeq = memberInfoSeqRequest;
        }
        return memberInfoSeq;
    }

    /**
     * 受注登録に失敗した旨を管理者へ通知するメールを送信する
     *
     * @param order     受注DTO
     * @param throwable 例外
     * @return 送信成功:true、送信失敗：false
     */
    public boolean sendAdministratorErrorMail(ReceiveOrderDto order, Throwable throwable) {
        CommunicateUtility communicateUtility = ApplicationContextUtility.getBean(CommunicateUtility.class);

        OrderRegistAlertMailProperties props = getOrderRegistAlertMailProperties();
        String message;
        if (throwable instanceof AppLevelListException) {
            StringBuilder sb = new StringBuilder();
            AppLevelListException list = (AppLevelListException) throwable;
            for (AppLevelException ex : list.getErrorList()) {
                if (communicateUtility.containsCode(ex.getMessageCode(), props.excludeErrorCode)) {
                    continue;
                }
                sb.append(ex.getMessage() + "\r\n");
            }
            if (sb.length() == 0) {
                return true;// メールを送る必要がない場合はreturn true
            }
            message = sb.toString();
        } else {
            message = throwable.toString();
        }
        return sendAdministratorErrorMail(order, OrderRegistable.MSGCD_ORDER_REGIST_FAIL, message, props);
    }

    /**
     * 受注登録に失敗した旨を管理者へ通知するメールを送信する
     *
     * @param order     受注DTO
     * @param messageId エラーメッセージID
     * @param message   エラーメッセージ
     * @param props     注文エラーメール送信用パラメータ
     * @return 送信成功:true、送信失敗：false
     */
    public boolean sendAdministratorErrorMail(ReceiveOrderDto order,
                                              String messageId,
                                              String message,
                                              OrderRegistAlertMailProperties props) {
        try {
            HTypeSettlementMethodType settlement = order.getOrderSettlementEntity().getSettlementMethodType();
            MulPayBillEntity bill = order.getMulPayBillEntity();

            StringBuilder errorMsg = new StringBuilder();
            if (StringUtil.isNotEmpty(messageId)) {
                errorMsg.append(AppLevelFacesMessageUtil.getAllMessage(messageId, null).getMessage() + "\r\n");
            }
            if (StringUtil.isNotEmpty(message)) {
                errorMsg.append(message);
            }

            Map<String, String> valueMap = new HashMap<>();
            String orderCode = order.getOrderSummaryEntity().getOrderCode();

            if (settlement == null) {
                valueMap.put("SETTLEMENTNAME", "");
            } else {
                valueMap.put("SETTLEMENTNAME", settlement.getLabel());
            }

            String accessId = "(受注番号：" + orderCode + ")";
            if (bill != null && bill.getAccessId() != null) {
                accessId = bill.getAccessId() + "(受注番号：" + orderCode + ")";
            }
            valueMap.put("ACCESSID", accessId);

            valueMap.put("ERROR", errorMsg.toString());
            if (LOGGER.isDebugEnabled()) {
                valueMap.put("DEBUG", "1");
            } else {
                valueMap.put("DEBUG", "0");
            }

            // メールに記載するシステム名
            valueMap.put("SYSTEM", props.getMailSystem());

            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);
            Map<String, Object> mailContentsMap = new HashMap<>();

            mailContentsMap.put("mailContentsMap", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.ORDER_REGIST_ALERT);
            mailDto.setFrom(props.getMailFrom());
            mailDto.setToList(Arrays.asList(props.getRecipients()));
            mailDto.setSubject("【" + props.getMailSystem() + " 要確認】注文処理エラー報告");
            mailDto.setMailContentsMap(mailContentsMap);

            MailSendService mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
            mailSendService.execute(mailDto);

            LOGGER.info("管理者へ通知メールを送信しました。");
        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);
            return false;
        }
        return true;
    }

    /**
     * コンビニ名を返却する<br/>
     * 利用不可能なコンビニ名には(旧)を付与する
     *
     * @param convenience コンビニエンティティ
     * @return String コンビニ名
     */
    public String getConveniName(ConvenienceEntity convenience) {
        if (convenience == null) {
            return "";
        }
        String conveniName = "";
        if (HTypeUseConveni.ON == convenience.getUseFlag()) {
            conveniName = convenience.getConveniName();
        } else {
            conveniName = "(旧)" + convenience.getConveniName();
        }
        return conveniName;
    }

    /**
     * 受注Dtoにセットされている全商品の、商品エンティティリストを取得
     *
     * @param receiveOrderDto 受注Dto
     * @return 商品エンティティリスト
     */
    public List<OrderGoodsEntity> getALLGoodsEntityList(ReceiveOrderDto receiveOrderDto) {

        List<OrderGoodsEntity> orderGoodsEntityList = new ArrayList<>();
        if (receiveOrderDto.getOrderDeliveryDto() == null) {
            return orderGoodsEntityList;
        }

        if (CollectionUtil.isEmpty(receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList())) {
            return orderGoodsEntityList;
        }
        for (OrderGoodsEntity orderGoodsEntity : receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList()) {
            orderGoodsEntityList.add(orderGoodsEntity);
        }

        return orderGoodsEntityList;
    }

    /**
     * 受注Dtoにセットされている全商品の、商品エンティティリストを取得
     *
     * @param orderGoodsEntityList 受注商品エンティティリスト
     * @return 数量0の商品を除いた受注商品エンティティリスト
     */
    public List<OrderGoodsEntity> getGoodsEntityListExceptCountZero(List<OrderGoodsEntity> orderGoodsEntityList) {

        List<OrderGoodsEntity> orderGoodsEntityExceptCountZeroList = new ArrayList<>();
        if (CollectionUtil.isEmpty(orderGoodsEntityList)) {
            return orderGoodsEntityExceptCountZeroList;
        }

        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            BigDecimal goodsCount = orderGoodsEntity.getGoodsCount();
            // 数量０は対象外
            if (goodsCount == null || goodsCount.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            orderGoodsEntityExceptCountZeroList.add(orderGoodsEntity);
        }
        return orderGoodsEntityExceptCountZeroList;
    }

    /**
     * 注文種別判定（注文単位）
     *
     * @param receiveOrderDto 受注DTO
     * @return 注文種別
     */
    public HTypeOrderType getOrderType(ReceiveOrderDto receiveOrderDto) {
        // 通常注文
        return HTypeOrderType.NORMAL;
    }

    /**
     * 受注状態より出荷状態を判定する<br/>
     *
     * @param orderStatus 受注状態
     * @return 出荷状態
     */
    public HTypeShipmentStatus getShipmentStatusByOrderStatus(HTypeOrderStatus orderStatus) {

        HTypeShipmentStatus shipmentStatus = HTypeShipmentStatus.UNSHIPMENT;

        if (HTypeOrderStatus.SHIPMENT_COMPLETION == orderStatus) {
            shipmentStatus = HTypeShipmentStatus.SHIPPED;
        }

        return shipmentStatus;
    }

    /**
     * 各配送情報の出荷状態より受注状態を判定する<br/>
     *
     * @param orderStatus      受注状態
     * @param orderDeliveryDto 受注配送Dto
     * @return 受注状態
     */
    public HTypeOrderStatus getOrderStatusByOrderDelivery(HTypeOrderStatus orderStatus,
                                                          OrderDeliveryDto orderDeliveryDto) {

        if (orderDeliveryDto == null) {
            return orderStatus;
        }

        if (HTypeShipmentStatus.SHIPPED == orderDeliveryDto.getOrderDeliveryEntity().getShipmentStatus()) {
            orderStatus = HTypeOrderStatus.SHIPMENT_COMPLETION;
        }

        return orderStatus;
    }

    /**
     * 受注商品エンティティリストから商品合計金額を算出する<br/>
     *
     * @param orderGoodsEntityList 受注商品エンティティリスト
     * @return 商品合計金額
     */
    public BigDecimal getGoodsPriceTotal(List<OrderGoodsEntity> orderGoodsEntityList) {

        BigDecimal goodsPriceTotal = BigDecimal.ZERO;

        if (CollectionUtil.isEmpty(orderGoodsEntityList)) {
            return goodsPriceTotal;
        }

        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            BigDecimal goodsPrice = orderGoodsEntity.getGoodsPrice().multiply(orderGoodsEntity.getGoodsCount());
            goodsPriceTotal = goodsPriceTotal.add(goodsPrice);
        }

        return goodsPriceTotal;
    }

    /**
     * 受注Dtoから商品合計数量を算出する<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @return 商品合計数量(子商品は除く)
     */
    public BigDecimal getGoodsCountTotal(ReceiveOrderDto receiveOrderDto) {

        BigDecimal goodsCountTotal = BigDecimal.ZERO;

        for (OrderGoodsEntity orderGoodsEntity : receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList()) {
            goodsCountTotal = goodsCountTotal.add(orderGoodsEntity.getGoodsCount());
        }
        return goodsCountTotal;
    }

    /**
     * 選択した配送方法の配送方法SEQリストを作成する<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @return 選択した配送方法のの配送方法SEQリスト
     */
    public List<Integer> createSelectDeliveryMethodSeqList(ReceiveOrderDto receiveOrderDto) {

        Set<Integer> deliveryMethodSeqSet = new LinkedHashSet<>();

        deliveryMethodSeqSet.add(receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity().getDeliveryMethodSeq());

        // 配送方法SEQリストを作成
        List<Integer> deliveryMethodSeqList = new ArrayList<>();
        for (Integer deliveryMethodSeq : deliveryMethodSeqSet) {
            deliveryMethodSeqList.add(deliveryMethodSeq);
        }

        return deliveryMethodSeqList;
    }

    /**
     * 配送方法の表示名を返す<br/>
     *
     * @param entity   配送方法Entity
     * @param siteType サイト種別
     * @return 表示名
     */
    public String getDeliveryMethodName(DeliveryMethodEntity entity, HTypeSiteType siteType) {
        return getViewName(entity.getDeliveryMethodDisplayNamePC(), entity.getDeliveryMethodDisplayNameMB(),
                           entity.getDeliveryMethodDisplayNamePC(), entity.getDeliveryMethodName(), siteType
                          );
    }

    /**
     * 決済方法の表示名を返す<br/>
     *
     * @param entity   決済方法Entity
     * @param siteType サイト種別
     * @return 表示名
     */
    public String getSettlementMethodName(SettlementMethodEntity entity, HTypeSiteType siteType) {
        return getViewName(entity.getSettlementMethodDisplayNamePC(), entity.getSettlementMethodDisplayNameMB(),
                           entity.getSettlementMethodDisplayNamePC(), entity.getSettlementMethodName(), siteType
                          );
    }

    /**
     * サイト種別に対応する名前を返す<br/>
     *
     * @param namePC   PC名
     * @param nameMB   MB名
     * @param nameSP   SP名
     * @param nameBack バック名
     * @param siteType サイト種別
     * @return 対応する名前
     */
    public String getViewName(String namePC, String nameMB, String nameSP, String nameBack, HTypeSiteType siteType) {
        if (siteType.isFrontPC()) {
            return namePC;
        } else if (siteType.isFrontMB()) {
            return nameMB;
        } else if (siteType.isFrontSP()) {
            return nameSP;
        } else {
            return nameBack;
        }
    }

    // PDR Migrate Customization from here

    /**
     * 予約配送フラグ判定
     *
     * @param receiveOrderDto  受注DTO
     * @param orderDeliveryDto 受注配送DTO
     * @return 予約配送フラグ
     */
    public HTypeReservationDeliveryFlag getReservationDeliveryFlag(ReceiveOrderDto receiveOrderDto,
                                                                   OrderDeliveryDto orderDeliveryDto) {
        return HTypeReservationDeliveryFlag.OFF;
    }

    // PDR Migrate Customization to here

    /**
     * 無料配送の決済方法 SEQを取得する。
     *
     * @return 無料決済方法SEQ
     */
    public Integer getFreeSettlementMethodSeq() {
        return PropertiesUtil.getSystemPropertiesValueToInt(FREE_SETTLEMENT_METHOD_SEQ);
    }

    /**
     * 配送追跡URLの取得
     *
     * @param deliveryMethodEntity 配送方法
     * @param orderDeliveryEntity  受注配送
     * @return 配送追跡URL
     */
    public String getDeliveryChaseURL(DeliveryMethodEntity deliveryMethodEntity,
                                      OrderDeliveryEntity orderDeliveryEntity) {

        // 配送追跡URLの設定が無い場合は無し
        if (StringUtil.isEmpty(deliveryMethodEntity.getDeliveryChaseURL())) {
            return "";
        }

        // 出荷日が無い場合は無し
        if (orderDeliveryEntity.getShipmentDate() == null) {
            return "";
        }

        // 伝票番号が無い場合は無し
        if (StringUtil.isEmpty(orderDeliveryEntity.getDeliveryCode())) {
            return "";
        }

        // 配送追跡URLの表示期間が無い場合は無し
        if (deliveryMethodEntity.getDeliveryChaseURLDisplayPeriod() == null) {
            return "";
        }

        // 出荷日が未来日なら無し
        if (dateUtility.isAfterCurrentTime(orderDeliveryEntity.getShipmentDate())) {
            return "";
        }

        // 配送追跡URLの表示期間が、期間内、または、無期限ならURLを返す
        int deliveryChaseURLDisplayPeriod =
                        conversionUtility.toInteger(deliveryMethodEntity.getDeliveryChaseURLDisplayPeriod());
        Timestamp targetDay = dateUtility.getAmountDayTimestamp(deliveryChaseURLDisplayPeriod, true,
                                                                orderDeliveryEntity.getShipmentDate()
                                                               );
        if (deliveryChaseURLDisplayPeriod == 0 || dateUtility.isAfterCurrentTime(targetDay)) {
            return MessageFormat.format(
                            deliveryMethodEntity.getDeliveryChaseURL(), orderDeliveryEntity.getDeliveryCode());

        }
        return "";
    }

    /**
     * 成年判定<br/>
     * 生年月日が成年であるかどうかを判定<br/>
     *
     * @param birthday 生年月日
     * @return 判定結果 true:成年, false:未成年
     */
    public boolean isAdult(Timestamp birthday) {
        // 生年月日(yyyyMMdd)
        Calendar birthDayYmd = Calendar.getInstance();
        // 現在年月日(yyyyMMdd)
        Calendar nowDayYmd = Calendar.getInstance();

        birthDayYmd.setTime(birthday);
        nowDayYmd.setTime(new Timestamp(System.currentTimeMillis()));
        // 生年月日に下限年齢を加算
        birthDayYmd.add(Calendar.YEAR, ADULT_AGE);

        if (nowDayYmd.compareTo(birthDayYmd) >= 0) {
            // 下限年齢以上の場合
            return true;
        }

        return false;
    }

    /**
     * 成年判定<br/>
     * 年齢が成年であるかどうかを判定<br/>
     *
     * @param age 年齢
     * @return 判定結果　 true:成年, false:未成年
     */
    public boolean isAdult(Integer age) {
        // 年齢が20歳を超えているか

        return (age != null && age.compareTo(OrderUtility.ADULT_AGE) >= 0);
    }

    /**
     * 成年判定<br/>
     * 生年月日と年齢の両方が成年であるかどうかを判定<br/>
     * 年齢がnullの場合は、生年月日だけで判定する
     *
     * @param birthday 生年月日
     * @param age      年齢
     * @return 判定結果　 true:成年, false:未成年
     */
    public boolean isAdultCheck(Timestamp birthday, Integer age) {
        boolean adultFlag = isAdult(birthday);
        if (adultFlag && age != null) {
            adultFlag = isAdult(age);
        }
        return adultFlag;
    }

    // PDR Migrate Customization from here

    /**
     * 受注商品エンティティリストから商品合計金額(税抜)を算出する<br/>
     *
     * @param orderGoodsEntityList 受注商品エンティティリスト
     * @return 商品合計金額(税抜)
     */
    public BigDecimal getGoodsPriceTotalNoTax(List<OrderGoodsEntity> orderGoodsEntityList) {

        BigDecimal goodsPriceTotal = BigDecimal.ZERO;

        if (CollectionUtil.isEmpty(orderGoodsEntityList)) {
            return goodsPriceTotal;
        }

        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            goodsPriceTotal = goodsPriceTotal.add(
                            orderGoodsEntity.getGoodsPrice().multiply(orderGoodsEntity.getGoodsCount()));
        }

        return goodsPriceTotal;
    }

    /**
     * WEB-API連携用に支払方法区分を変換します。
     *
     * @param settlementMethodType 支払方法区分
     * @return WEB-API連携用支払方法区分
     */
    public HTypePaymentMethod conversionPaymentMethod(String settlementMethodType) {

        if (HTypeSettlementMethodType.CONVENIENCE_POSTALTRANSFER.getValue().equals(settlementMethodType)) {
            // コンビニ・郵便振込み
            return HTypePaymentMethod.CONVENIENCE;
        }

        if (HTypeSettlementMethodType.RECEIPT_PAYMENT.getValue().equals(settlementMethodType)) {
            // 代金引換
            return HTypePaymentMethod.CASH_ON_DELIVERY;
        }

        if (HTypeSettlementMethodType.CREDIT.getValue().equals(settlementMethodType)) {
            // クレジットカード
            return HTypePaymentMethod.CREDIT_CARD;
        }

        if (HTypeSettlementMethodType.MONTHLY_BILLING.getValue().equals(settlementMethodType)) {
            // 月締請求
            return HTypePaymentMethod.MONTHLY_BILLING;
        }

        if (HTypeSettlementMethodType.AUTOMATIC_WITHDRAWAL.getValue().equals(settlementMethodType)) {
            // 口座自動引落
            return HTypePaymentMethod.AUTOMATIC_WITHDRAWAL;
        }

        // 2023-renew No32 from here
        if (HTypeSettlementMethodType.DISCOUNT.getValue().equals(settlementMethodType)) {
            // 請求なし
            return HTypePaymentMethod.FREE;
        }
        // 2023-renew No32 to here

        // 上記以外はコンビニ・郵便振込みに変換
        return HTypePaymentMethod.CONVENIENCE;
    }

    // 2023-renew No14 from here

    /**
     * HM側の決済タイプに支払方法区分を変換します。
     * ※HM側に存在しない決済はNULLで返却する
     *
     * @param paymentType WEB-API連携用支払方法区分
     * @return 支払方法区分（決済タイプ）
     */
    public String conversionSettlementMethod(String paymentType) {

        if (HTypePaymentMethod.CONVENIENCE.getValue().equals(paymentType)) {
            // コンビニ・郵便振込み
            return HTypeSettlementMethodType.CONVENIENCE_POSTALTRANSFER.getValue();
        }

        if (HTypePaymentMethod.CASH_ON_DELIVERY.getValue().equals(paymentType)) {
            // 代金引換
            return HTypeSettlementMethodType.RECEIPT_PAYMENT.getValue();
        }

        if (HTypePaymentMethod.CREDIT_CARD.getValue().equals(paymentType)) {
            // クレジットカード
            return HTypeSettlementMethodType.CREDIT.getValue();
        }

        if (HTypePaymentMethod.MONTHLY_BILLING.getValue().equals(paymentType)) {
            // 月締請求
            return HTypeSettlementMethodType.MONTHLY_BILLING.getValue();
        }

        if (HTypePaymentMethod.AUTOMATIC_WITHDRAWAL.getValue().equals(paymentType)) {
            // 口座自動引落
            return HTypeSettlementMethodType.AUTOMATIC_WITHDRAWAL.getValue();
        }

        if (HTypePaymentMethod.FREE.getValue().equals(paymentType)) {
            // 請求なし
            return HTypeSettlementMethodType.DISCOUNT.getValue();
        }

        // 上記以外はNULLで返却（HM側に存在しない決済）
        return null;
    }

    // 2023-renew No14 to here

    /**
     * 受注商品エンティティから<br/>
     * 商品コードリストを作成します。
     *
     * @param orderGoodsEntityList 受注商品エンティティ
     * @return 商品コードリスト
     */
    public List<String> getGoodsCodeList(List<OrderGoodsEntity> orderGoodsEntityList) {

        List<String> goodsCodeList = new ArrayList<>();
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            goodsCodeList.add(orderGoodsEntity.getGoodsCode());
        }
        return goodsCodeList;
    }

    /**
     * エラーメッセージ表示で使用する「商品表示名（規格１/規格２)」を作成します。<br/>
     *
     * @param orderGoodsEntity 受注商品クラス
     * @return 商品表示名（規格１/規格２)
     */
    public String createErrDispGoodsName(OrderGoodsEntity orderGoodsEntity) {
        // PDR Migrate Customization from here
        return this.createErrDispGoodsName(goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(orderGoodsEntity),
                                           orderGoodsEntity.getUnitValue1(), orderGoodsEntity.getUnitValue2()
                                          );
        // PDR Migrate Customization to here
    }

    /**
     * エラーメッセージ表示で使用する「商品表示名（規格１/規格２)」を作成します。<br/>
     *
     * @param namePC     商品表示PC
     * @param unitValue1 規格値1
     * @param unitValue2 規格値2
     * @return 商品表示名（規格１/規格２)
     */
    public String createErrDispGoodsName(String namePC, String unitValue1, String unitValue2) {
        StringBuilder displayGoodsName = new StringBuilder();

        displayGoodsName.append(namePC);

        if (unitValue1 != null) {
            displayGoodsName.append("(");
            // 規格管理ありの場合は規格値１は必須項目なので必ず取得できる
            displayGoodsName.append(unitValue1);
            // 規格値２の値がNULLの場合は商品表示名（規格値１）のみをエラーメッセージに表示する
            // 規格値２の値が存在する場合は規格値２をエラーメッセージに表示する
            if (unitValue2 != null) {
                displayGoodsName.append("/");
                displayGoodsName.append(unitValue2);
            }
            displayGoodsName.append(")");
        }

        return displayGoodsName.toString();
    }

    /**
     * 出荷予定日（メッセージ用）を取得します。
     * ※実質はお届け希望日のこと（受注用の出荷予定日ではない）
     *
     * @param receiveOrderDto 受注DTO
     * @return 出荷予定日
     */
    public String getDeliveryDate(ReceiveOrderDto receiveOrderDto) {

        String deliveryDate;

        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        if (HTypeAllocationDeliveryType.DELIVER_NOW.equals(receiveOrderDto.getAllocationDeliveryType())) {
            // 今すぐお届け
            deliveryDate = dateUtility.formatYmdWithSlash(orderDeliveryDto.getOrderDeliveryEntity().getReceiverDate());
        } else if (HTypeAllocationDeliveryType.RESERVABLE.equals((receiveOrderDto).getAllocationDeliveryType())) {
            // 取りおき
            deliveryDate = orderDeliveryDto.getDeliveryDate();
        } else {
            // 入荷次第お届け
            deliveryDate = HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.getLabel();
        }
        return deliveryDate;
    }

    // PDR Migrate Customization to here
}
