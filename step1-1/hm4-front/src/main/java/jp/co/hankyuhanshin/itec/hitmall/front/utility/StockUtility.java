/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.utility;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAllocationDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryNowDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDependingOnReceiptGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderReserveDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.goods.OrderGoodsEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * #006 03_取りおきサービス<br/>
 *
 * <pre>
 * 商品在庫情報ユーティリティクラス
 * </pre>
 *
 * @author satoh
 */
// PDR Migrate Customization from here
@Component
public class StockUtility {

    /** システムエラー発生 */
    public static final String MSGCD_SYSTEM_ERR = "LOX000102";

    /** 購入制限エラーメッセージ */
    public static final String MSGCD_SALE_NG = "PDR-0005-001-A-";

    /** 在庫チェックエラー (お届け先情報設定画面用) */
    public static final String MSGCD_STOCK_SHORTAGE = "PDR-0028-003-A-";

    /** 在庫チェックエラー (注文確認画面用)  */
    public static final String MSGCD_STOCK_ERROR = "PDR-0003-003-A-";

    /** 入庫次第振り分け数に変動が発生 */
    public static final String MSGCD_DEPENDING_ON_RECEIPT = "PDR-0030-002-A-";

    /**
     * 受注業務ユーティリティクラス
     */
    private final OrderUtility orderUtility;

    /**
     * GoodsUtility
     */
    private final GoodsUtility goodsUtility;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    @Autowired
    public StockUtility(OrderUtility orderUtility, GoodsUtility goodsUtility, DateUtility dateUtility) {
        this.orderUtility = orderUtility;
        this.goodsUtility = goodsUtility;
        this.dateUtility = dateUtility;
    }

    /**
     * 商品を 「今すぐお届け」、「取りおき」、「入荷次第お届け」に振り分けを行います。
     *
     * @param receiveOrderDto     受注Dtoクラス
     * @param stockMapMaster      商品在庫情報
     * @param checkMessageDtoList エラーメッセージ用List
     * @param messageCode         振り分け時のメッセージ
     */
    public void allocationDelivery(ReceiveOrderDto receiveOrderDto,
                                   Map<String, WebApiGetStockResponseDetailDto> stockMapMaster,
                                   List<CheckMessageDto> checkMessageDtoList,
                                   String messageCode) {

        OrderDeliveryDto orderDeliveryDto = receiveOrderDto.getOrderDeliveryDto();

        // 在庫情報をコピー
        Map<String, WebApiGetStockResponseDetailDto> stockMap = copyStockMap(stockMapMaster);

        // 今すぐお届け分
        List<OrderDeliveryNowDto> orderDeliveryNowDtoList = new ArrayList<>();
        // 取りおき分
        List<OrderReserveDeliveryDto> reserveDeliveryDtoList = new ArrayList<>();
        // 入荷次第お届け分
        List<OrderDependingOnReceiptGoodsDto> dependingOnReceiptGoodsDtoList = new ArrayList<>();

        Map<Integer, GoodsDetailsDto> goodsDetailMap = receiveOrderDto.getMasterDto().getGoodsMaster();
        for (OrderGoodsEntity orderGoodsEntity : orderDeliveryDto.getOrderGoodsEntityList()) {

            // 商品詳細情報
            GoodsDetailsDto goodsDetailsDto = goodsDetailMap.get(orderGoodsEntity.getGoodsSeq());

            // API取得結果MAP用に、商品コードのkpを削除する
            String goodsCode =
                            goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(orderGoodsEntity.getGoodsCode());

            // 入荷予定日を設定
            if (stockMap.get(goodsCode).getStockDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                orderGoodsEntity.setStockDate(
                                dateUtility.toTimestampValue(sdf.format(stockMap.get(goodsCode).getStockDate()),
                                                             DateUtility.YMD
                                                            ));
            }

            // 入荷次第お届け可否を設定
            orderGoodsEntity.setDeliveryYesNo(stockMap.get(goodsCode).getDeliveryYesNo());

            // 在庫管理フラグを設定
            orderGoodsEntity.setStockManagementFlag(goodsDetailsDto.getStockManagementFlag());

            // 2023-renew No14 from here
            // セールde予約商品の場合
            if (HTypeReserveDeliveryFlag.ON.equals(orderGoodsEntity.getReserveFlag())) {
                OrderReserveDeliveryDto deliveryDto = new OrderReserveDeliveryDto();
                deliveryDto.setOrderGoodsEntity(copyOrderGoodsEntity(orderGoodsEntity));
                // 取りおき情報DTOクラスに詳細情報を追加
                reserveDeliveryDtoList.add(deliveryDto);

            }
            // 今すぐお届け商品の場合
            else {
                // 在庫数情報
                WebApiGetStockResponseDetailDto stockDto = stockMap.get(goodsCode);
                // 在庫不足数 チェック
                BigDecimal stockShortageCount = calculationStockShortage(stockDto, orderGoodsEntity.getGoodsCount());

                if (HTypeStockManagementFlag.OFF.equals(goodsDetailsDto.getStockManagementFlag()) || (
                                HTypeStockManagementFlag.ON.equals(goodsDetailsDto.getStockManagementFlag())
                                && "1".equals(stockDto.getDeliveryYesNo()))) {
                    // 売切り対象外の商品
                    // 今すぐお届け／入荷次第お届けに振り分け
                    allocationStockManagementOff(stockShortageCount, orderDeliveryNowDtoList,
                                                 dependingOnReceiptGoodsDtoList, orderGoodsEntity,
                                                 stockDto.getStockDate(), goodsDetailsDto.getStockManagementFlag()
                                                );

                } else {
                    // 今すぐお届けに振り分け
                    allocationStockManagementOn(orderDeliveryNowDtoList, orderGoodsEntity);
                }
            }
            // 2023-renew No14 to here
        }

        // 入荷次第お届け数チェック
        // 振り分け数に変更があった場合はメッセージを表示
        checkAllocationDependingOnReceiptGoods(orderDeliveryDto.getOrderDependingOnReceiptGoodsDtoList(),
                                               dependingOnReceiptGoodsDtoList, checkMessageDtoList, messageCode
                                              );

        // 今すぐお届け分
        orderDeliveryDto.setOrderDeliveryNowDtoList(orderDeliveryNowDtoList);
        // 取りおき分
        orderDeliveryDto.setOrderReserveDeliveryDtoList(reserveDeliveryDtoList);
        // 入荷次第お届け分
        orderDeliveryDto.setOrderDependingOnReceiptGoodsDtoList(dependingOnReceiptGoodsDtoList);
    }

    /**
     * 今すぐお届け不可の商品数を計算します。
     * WEB-APIで取得した在庫数の減算を行います。
     *
     * @param stockDto        商品在庫数
     * @param orderGoodsCount 注文数
     * @return 今すぐお届け不可の商品数
     */
    private BigDecimal calculationStockShortage(WebApiGetStockResponseDetailDto stockDto, BigDecimal orderGoodsCount) {

        BigDecimal dependingOnReceiptCount = BigDecimal.ZERO;

        // 在庫数が0の場合
        if (stockDto.getStockQuantity() == null || BigDecimal.ZERO.compareTo(stockDto.getStockQuantity()) == 0) {
            // 注文数を全て今すぐお届け不可の商品数に設定
            return orderGoodsCount;
        }

        if (stockDto.getStockQuantity().compareTo(orderGoodsCount) < 0) {
            dependingOnReceiptCount = (stockDto.getStockQuantity().subtract(orderGoodsCount)).negate();
            stockDto.setStockQuantity(BigDecimal.ZERO);
            return dependingOnReceiptCount;
        }

        // 在庫数の減算
        stockDto.setStockQuantity(stockDto.getStockQuantity().subtract(orderGoodsCount));

        return dependingOnReceiptCount;
    }

    /**
     * 売切り対象外の商品を
     * 今すぐお届け 入荷次第お届けに振り分けます。
     *
     * @param stockShortageCount             在庫不足数 (不足でない場合は0)
     * @param orderDeliveryNowDtoList        今すぐお届け分
     * @param dependingOnReceiptGoodsDtoList 入荷次第お届け分
     * @param orderGoodsEntity               受注商品エンティティ
     * @param stockDate                      入荷予定日
     * @param stockManagementFlag            在庫管理フラグ
     */
    private void allocationStockManagementOff(BigDecimal stockShortageCount,
                                              List<OrderDeliveryNowDto> orderDeliveryNowDtoList,
                                              List<OrderDependingOnReceiptGoodsDto> dependingOnReceiptGoodsDtoList,
                                              OrderGoodsEntity orderGoodsEntity,
                                              Timestamp stockDate,
                                              HTypeStockManagementFlag stockManagementFlag) {
        // 売切り対象外の商品

        // 在庫不足数が 0 以上の場合
        if (BigDecimal.ZERO.compareTo(stockShortageCount) < 0) {

            OrderDependingOnReceiptGoodsDto dependingOnReceiptGoodsDto = new OrderDependingOnReceiptGoodsDto();
            // 受注商品DTOをコピー
            dependingOnReceiptGoodsDto.setOrderGoodsEntity(copyOrderGoodsEntity(orderGoodsEntity));

            // 商品数量を入荷次第お届け数に上書き
            dependingOnReceiptGoodsDto.getOrderGoodsEntity().setGoodsCount(stockShortageCount);
            dependingOnReceiptGoodsDto.setStockDate(stockDate);
            dependingOnReceiptGoodsDto.setStockManagementFlag(stockManagementFlag);
            // 入荷次第お届け数 設定
            dependingOnReceiptGoodsDtoList.add(dependingOnReceiptGoodsDto);

        }

        // 今すぐお届け分 設定
        OrderDeliveryNowDto deliveryNowDto = new OrderDeliveryNowDto();

        deliveryNowDto.setOrderGoodsEntity(copyOrderGoodsEntity(orderGoodsEntity));
        deliveryNowDto.getOrderGoodsEntity()
                      .setGoodsCount(orderGoodsEntity.getGoodsCount().subtract(stockShortageCount));

        // 今すぐお届け分が0個以上の場合は設定
        if (BigDecimal.ZERO.compareTo(deliveryNowDto.getOrderGoodsEntity().getGoodsCount()) != 0) {
            orderDeliveryNowDtoList.add(deliveryNowDto);
        }

    }

    /**
     * 売り切り対象の商品を
     * 今すぐお届けに振り分けます。
     *
     * @param orderDeliveryNowDtoList 今すぐお届け分
     * @param orderGoodsEntity        受注商品エンティティ
     */
    private void allocationStockManagementOn(List<OrderDeliveryNowDto> orderDeliveryNowDtoList,
                                             OrderGoodsEntity orderGoodsEntity) {

        // 売切り対象の商品
        OrderDeliveryNowDto deliveryNowDto = new OrderDeliveryNowDto();
        deliveryNowDto.setOrderGoodsEntity(copyOrderGoodsEntity(orderGoodsEntity));
        orderDeliveryNowDtoList.add(deliveryNowDto);
    }

    /**
     * 入荷次第お届け数に変動があった場合は
     * メッセージを設定します。
     *
     * @param preDependingOnReceiptGoodsDtoList 前回振り分けた入荷次第お届け商品リスト
     * @param dependingOnReceiptGoodsDtoList    今回振り分けられた 入荷次第お届け商品リスト
     * @param checkMessageDtoList               エラーメッセージ用List
     * @param messageCode                       設定するメッセージコード
     */
    private void checkAllocationDependingOnReceiptGoods(List<OrderDependingOnReceiptGoodsDto> preDependingOnReceiptGoodsDtoList,
                                                        List<OrderDependingOnReceiptGoodsDto> dependingOnReceiptGoodsDtoList,
                                                        List<CheckMessageDto> checkMessageDtoList,
                                                        String messageCode) {

        // 前回、今回振り分け分が存在しない場合
        if ((dependingOnReceiptGoodsDtoList == null || dependingOnReceiptGoodsDtoList.isEmpty()) && (
                        preDependingOnReceiptGoodsDtoList == null || preDependingOnReceiptGoodsDtoList.isEmpty())) {
            // 処理終了
            return;
        }

        // メッセージ重複を防止するため、商品コードをキーにチェックを行う
        Set<String> messageSet = new LinkedHashSet<>();
        for (CheckMessageDto messageDto : checkMessageDtoList) {
            messageSet.add(messageDto.getArgs()[0].toString());
        }

        // 入荷次第お届けが存在する場合は常にメッセージを表示する
        for (OrderDependingOnReceiptGoodsDto dependingOnReceiptGoodsDto : dependingOnReceiptGoodsDtoList) {
            CheckMessageDto checkMessageDto = new CheckMessageDto();
            checkMessageDto.setMessageId(messageCode);
            checkMessageDto.setArgs(new String[] {dependingOnReceiptGoodsDto.getKey(),
                            orderUtility.createErrDispGoodsName(dependingOnReceiptGoodsDto.getOrderGoodsEntity())});

            if (!messageSet.contains(dependingOnReceiptGoodsDto.getKey())) {
                checkMessageDtoList.add(checkMessageDto);
            }
        }
    }

    /**
     * 在庫情報数のチェックを行います。
     * (お届け先情報設定画面用)
     *
     * @param receiveOrderDto     受注Dto
     * @param checkMessageDtoList エラーメッセージ用List
     * @param stockMapMaster      在庫情報
     */
    public void checkStockByDelivery(ReceiveOrderDto receiveOrderDto,
                                     List<CheckMessageDto> checkMessageDtoList,
                                     Map<String, WebApiGetStockResponseDetailDto> stockMapMaster) {

        // 在庫情報をコピー
        Map<String, WebApiGetStockResponseDetailDto> stockMap = copyStockMap(stockMapMaster);

        // 受注商品リスト
        List<OrderGoodsEntity> orderGoodsEntityList = receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList();

        // 商品詳細MAP
        Map<Integer, GoodsDetailsDto> goodsDetailMap = receiveOrderDto.getMasterDto().getGoodsMaster();

        // 2023-renew No14 from here
        // セールde予約を含むかどうかをMapに保持
        Map<String, Boolean> receiveFlagMap = new HashMap<>();
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            String goodsCode =
                            goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(orderGoodsEntity.getGoodsCode());
            if (receiveFlagMap.containsKey(goodsCode)) {
                // 既にMapに存在する商品コードの場合、セールde予約商品の場合はフラグONに更新
                if (HTypeReserveDeliveryFlag.ON.equals(orderGoodsEntity.getReserveFlag())) {
                    receiveFlagMap.put(goodsCode, true);
                }
            } else {
                // Mapに存在しない商品コードの場合、フラグON／OFFを保持
                receiveFlagMap.put(goodsCode, HTypeReserveDeliveryFlag.ON.equals(orderGoodsEntity.getReserveFlag()));
            }
        }
        // 在庫数（減算前）を保持するためのMAP
        Map<String, BigDecimal> initStockQuantityMap = new HashMap<>();
        // エラーメッセージ重複確認用リスト
        ArrayList<String> messageList = new ArrayList<>();

        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {

            // 商品詳細情報
            GoodsDetailsDto goodsDetailsDto = goodsDetailMap.get(orderGoodsEntity.getGoodsSeq());

            // API取得結果MAP用に、商品コードのkpを削除する
            String goodsCode =
                            goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(orderGoodsEntity.getGoodsCode());
            WebApiGetStockResponseDetailDto stockDto = stockMap.get(goodsCode);

            if (goodsDetailsDto == null || stockDto == null) {
                // 必要な情報がない場合 エラー
                CheckMessageDto checkMessageDto = new CheckMessageDto();
                checkMessageDto.setMessageId(MSGCD_SYSTEM_ERR);
                checkMessageDtoList.add(checkMessageDto);
                return;
            }

            // 在庫数（減算前）を保持
            if (!initStockQuantityMap.containsKey(goodsCode)) {
                initStockQuantityMap.put(goodsCode, stockDto.getStockQuantity());
            }

            // 売り切り対象商品の場合の在庫チェック
            if (HTypeStockManagementFlag.ON.equals(goodsDetailsDto.getStockManagementFlag())) {
                if (receiveFlagMap.get(goodsCode)) {
                    // セールde予約を含む場合、そのまま在庫チェックを実施
                    stockDto.setStockQuantity(checkStockManagementFlagOn(orderGoodsEntity, stockDto.getStockQuantity(),
                                                                         initStockQuantityMap.get(goodsCode),
                                                                         checkMessageDtoList, messageList
                                                                        ) ?
                                                              stockDto.getStockQuantity()
                                                                      .subtract(orderGoodsEntity.getGoodsCount()) :
                                                              BigDecimal.ZERO);
                } else {
                    // セールde予約を含まない場合、入荷次第お届け可否=不可ならば在庫チェックを実施
                    if (!"1".equals(stockDto.getDeliveryYesNo())) {
                        stockDto.setStockQuantity(
                                        checkStockManagementFlagOn(orderGoodsEntity, stockDto.getStockQuantity(),
                                                                   initStockQuantityMap.get(goodsCode),
                                                                   checkMessageDtoList, messageList
                                                                  ) ?
                                                        stockDto.getStockQuantity()
                                                                .subtract(orderGoodsEntity.getGoodsCount()) :
                                                        BigDecimal.ZERO);
                    }
                }
            }

            // 在庫チェックのエラー有無を確認
            for (CheckMessageDto checkMessageDto : checkMessageDtoList) {
                String msgCd = checkMessageDto.getMessageId();
                if (MSGCD_STOCK_SHORTAGE.equals(msgCd)) {
                    continue;
                }
            }

            // 購入制限チェック
            if (stockMap.get(goodsCode).getSaleYesNo().equals(1)) {
                // 購入不可時エラー処理
                addCheckMessageDtoList(
                                checkMessageDtoList, messageList, MSGCD_SALE_NG, new String[] {stockMap.get(
                                                goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                                                                orderGoodsEntity.getGoodsCode())).getSaleNgMessage()});
                continue;
            }

        }
        // 2023-renew No14 to here
    }

    /**
     * 商品の在庫チェックを行います。
     *
     * @param orderGoodsEntity    受注商品
     * @param stockCount          在庫数
     * @param initStockCount      在庫数（減算前）
     * @param checkMessageDtoList チェックメッセージDTOList
     * @param stockNgMessageList  在庫チェックエラーメッセージ重複確認用リスト
     * @return true...在庫有り/false...在庫なし
     */
    private boolean checkStockManagementFlagOn(OrderGoodsEntity orderGoodsEntity,
                                               BigDecimal stockCount,
                                               BigDecimal initStockCount,
                                               List<CheckMessageDto> checkMessageDtoList,
                                               ArrayList<String> stockNgMessageList) {
        // 2023-renew No14 from here
        // 在庫チェック
        if (stockCount.compareTo(orderGoodsEntity.getGoodsCount()) < 0) {
            // 在庫不足時エラー処理
            addCheckMessageDtoList(checkMessageDtoList, stockNgMessageList, MSGCD_STOCK_SHORTAGE,
                                   new String[] {orderUtility.createErrDispGoodsName(orderGoodsEntity),
                                                   String.valueOf(initStockCount)}
                                  );
            return false;
        }
        // 2023-renew No14 to here
        return true;
    }

    // 2023-renew No14 from here

    /**
     * エラーメッセージを追加
     * ※ただし、重複する場合は追加しない
     *
     * @param checkMessageDtoList チェックメッセージDTOList
     * @param messageList         エラーメッセージ重複確認用リスト
     * @param messageCode         メッセージコード
     * @param args                メッセージ引数
     */
    private void addCheckMessageDtoList(List<CheckMessageDto> checkMessageDtoList,
                                        ArrayList<String> messageList,
                                        String messageCode,
                                        String[] args) {
        // 重複チェック用KEY文言生成
        // ※メッセージコード＋「:」＋メッセージ引数
        StringBuilder key = new StringBuilder();
        key.append(messageCode);
        if (ObjectUtils.isNotEmpty(args)) {
            key.append(":").append(Arrays.toString(args));
        }

        // 重複確認用リストに存在しない場合のみ、メッセージを追加
        if (!messageList.contains(key.toString())) {
            CheckMessageDto checkMessageDto = new CheckMessageDto();
            checkMessageDto.setMessageId(messageCode);
            checkMessageDto.setArgs(args);
            checkMessageDtoList.add(checkMessageDto);
            messageList.add(key.toString());
        }
    }

    // 2023-renew No14 to here

    /**
     * 在庫情報数のチェックを行います。
     * (注文内容確認画面用)
     *
     * @param receiveOrderDtoList 受注Dtoリスト
     * @param checkMessageDtoList エラーメッセージ用List
     * @param stockMapMaster      在庫情報
     */
    public void checkStockByConfirm(List<ReceiveOrderDto> receiveOrderDtoList,
                                    List<CheckMessageDto> checkMessageDtoList,
                                    Map<String, WebApiGetStockResponseDetailDto> stockMapMaster) {

        // 在庫情報をコピー
        Map<String, WebApiGetStockResponseDetailDto> stockMap = copyStockMap(stockMapMaster);

        // 在庫数チェック用商品
        Map<String, OrderGoodsEntity> stockCheckGoodsMap = new HashMap<>();

        // 入荷次第お届け分受注DTO
        ReceiveOrderDto receiveOrderDtoListByDependingOnReceipt = null;

        // 今すぐ＋取り置きチェック用商品(初期表示)
        List<String> deliverNowGoodsCodeList = new ArrayList<>();
        List<String> reservableGoodsCodeList = new ArrayList<>();

        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {

            for (OrderGoodsEntity entity : receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList()) {

                // 商品詳細MAP
                Map<Integer, GoodsDetailsDto> goodsDetailMap = receiveOrderDto.getMasterDto().getGoodsMaster();
                // 商品詳細情報
                GoodsDetailsDto goodsDetailsDto = goodsDetailMap.get(entity.getGoodsSeq());
                // 入荷次第お届け分
                if (HTypeAllocationDeliveryType.DEPENDING_ON_RECEIPT.equals(
                                receiveOrderDto.getAllocationDeliveryType())) {
                    boolean deliveryYesFlg = false;
                    WebApiGetStockResponseDetailDto stockDto =
                                    stockMap.get(goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                                                    entity.getGoodsCode()));

                    if (stockDto == null || goodsDetailsDto == null) {
                        receiveOrderDtoListByDependingOnReceipt = receiveOrderDto;
                        continue;
                    }

                    // 入荷次第お届け可または売り切り対象外の場合
                    if ("1".equals(stockDto.getDeliveryYesNo()) || HTypeStockManagementFlag.OFF.equals(
                                    goodsDetailsDto.getStockManagementFlag())) {
                        deliveryYesFlg = true;
                    }
                    if (deliveryYesFlg) {
                        receiveOrderDtoListByDependingOnReceipt = receiveOrderDto;
                        continue;
                    }
                }

                // 今すぐ
                if (HTypeAllocationDeliveryType.DELIVER_NOW.equals(receiveOrderDto.getAllocationDeliveryType())) {
                    deliverNowGoodsCodeList.add(entity.getGoodsCode());
                }
                // 取りおき
                if (HTypeAllocationDeliveryType.RESERVABLE.equals(receiveOrderDto.getAllocationDeliveryType())) {
                    reservableGoodsCodeList.add(entity.getGoodsCode());
                }

                // 取りおき かつ 売り切り対象外
                if (HTypeAllocationDeliveryType.RESERVABLE.equals(receiveOrderDto.getAllocationDeliveryType())
                    && HTypeStockManagementFlag.OFF.equals(goodsDetailsDto.getStockManagementFlag())) {
                    // スキップ
                    continue;
                }

                // 同梱商品の場合
                if (entity.isBundleFlag()) {
                    // スキップ
                    continue;
                }

                // 在庫チェックの必要な商品数を算出
                if (stockCheckGoodsMap.get(
                                goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(entity.getGoodsCode()))
                    != null) {
                    OrderGoodsEntity goodsEntity = stockCheckGoodsMap.get(
                                    goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(entity.getGoodsCode()));
                    goodsEntity.setGoodsCount(goodsEntity.getGoodsCount().add(entity.getGoodsCount()));
                    stockCheckGoodsMap.put(entity.getGoodsCode(), goodsEntity);

                } else {
                    stockCheckGoodsMap.put(entity.getGoodsCode(), copyOrderGoodsEntity(entity));
                }
            }

        }

        // 在庫数チェック
        for (String goodsCode : stockCheckGoodsMap.keySet()) {
            // 在庫数取得API結果DTOにはkpつき商品コードがないため、kpを削除して取得
            WebApiGetStockResponseDetailDto stockDto =
                            stockMap.get(goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsCode));

            OrderGoodsEntity orderGoodsEntity = stockCheckGoodsMap.get(goodsCode);

            if (stockDto == null) {
                // 必要な情報がない場合 エラー
                CheckMessageDto checkMessageDto = new CheckMessageDto();
                checkMessageDto.setMessageId(MSGCD_SYSTEM_ERR);
                checkMessageDtoList.add(checkMessageDto);
                return;
            }

            // 在庫不足数
            BigDecimal stockShortageCount = calculationStockShortage(stockDto, orderGoodsEntity.getGoodsCount());
            if (BigDecimal.ZERO.compareTo(stockShortageCount) == 0) {
                // 在庫あり スキップ
                continue;
            }

            if (receiveOrderDtoListByDependingOnReceipt != null) {
                // 売切り対象外
                for (OrderGoodsEntity entity : receiveOrderDtoListByDependingOnReceipt.getOrderDeliveryDto()
                                                                                      .getOrderGoodsEntityList()) {
                    if (orderGoodsEntity.getGoodsCode().equals(entity.getGoodsCode())) {
                        // 入荷次第お届け分と差異がない場合
                        if (stockShortageCount.compareTo(entity.getGoodsCount()) == 0) {
                            // スキップ
                            continue;
                        }
                    }
                }
            }

            // 在庫が不足していた場合はエラー
            CheckMessageDto checkMessageDto = new CheckMessageDto();
            checkMessageDto.setMessageId(MSGCD_STOCK_ERROR);
            checkMessageDto.setArgs(new String[] {goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                            orderGoodsEntity.getGoodsCode()), orderUtility.createErrDispGoodsName(orderGoodsEntity)});
            // 2023-renew No14 from here
            checkMessageDto.setError(true);
            // 2023-renew No14 to here
            checkMessageDtoList.add(checkMessageDto);
        }
    }

    /**
     * 購入制限のチェックを行います。
     * (注文内容確認画面用)
     *
     * @param receiveOrderDtoList 受注Dtoリスト
     * @param checkMessageDtoList エラーメッセージ用List
     * @param stockMapMaster      在庫情報
     */
    public void checkPurchaseByConfirm(List<ReceiveOrderDto> receiveOrderDtoList,
                                       List<CheckMessageDto> checkMessageDtoList,
                                       Map<String, WebApiGetStockResponseDetailDto> stockMapMaster) {

        // 在庫情報をコピー
        Map<String, WebApiGetStockResponseDetailDto> stockMap = copyStockMap(stockMapMaster);

        // 在庫数チェック用商品
        Map<String, OrderGoodsEntity> stockCheckGoodsMap = new HashMap<>();

        // 今すぐ＋取り置きチェック用商品(初期表示)
        List<String> deliverNowGoodsCodeList = new ArrayList<>();
        List<String> reservableGoodsCodeList = new ArrayList<>();
        List<String> bundleGoodsCodeList = new ArrayList<>();

        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {

            for (OrderGoodsEntity entity : receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList()) {

                // 同梱商品の場合
                if (entity.isBundleFlag()) {
                    // 購入制限チェックをスキップするために保持
                    bundleGoodsCodeList.add(entity.getGoodsCode());
                }

                // 今すぐ
                if (HTypeAllocationDeliveryType.DELIVER_NOW.equals(receiveOrderDto.getAllocationDeliveryType())) {
                    deliverNowGoodsCodeList.add(entity.getGoodsCode());
                }
                // 取りおき
                if (HTypeAllocationDeliveryType.RESERVABLE.equals(receiveOrderDto.getAllocationDeliveryType())) {
                    reservableGoodsCodeList.add(entity.getGoodsCode());
                }

                // 在庫チェックの必要な商品数を算出
                if (stockCheckGoodsMap.get(
                                goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(entity.getGoodsCode()))
                    != null) {
                    OrderGoodsEntity goodsEntity = stockCheckGoodsMap.get(
                                    goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(entity.getGoodsCode()));
                    goodsEntity.setGoodsCount(goodsEntity.getGoodsCount().add(entity.getGoodsCount()));
                    stockCheckGoodsMap.put(entity.getGoodsCode(), goodsEntity);

                } else {
                    stockCheckGoodsMap.put(entity.getGoodsCode(), copyOrderGoodsEntity(entity));
                }
            }
        }

        // 購入制限エラーメッセージ重複確認用リスト
        ArrayList<String> saleNgMessageList = new ArrayList<>();

        // 購入制限チェック
        for (String goodsCode : stockCheckGoodsMap.keySet()) {

            // 同梱商品の場合
            if (bundleGoodsCodeList.contains(goodsCode)) {
                // スキップ
                continue;
            }

            // 在庫数取得API結果DTOにはkpつき商品コードがないため、kpを削除して取得
            WebApiGetStockResponseDetailDto stockDto =
                            stockMap.get(goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsCode));

            // 在庫チェックのエラー有無を確認
            for (CheckMessageDto checkMessageDto : checkMessageDtoList) {
                String msgCd = checkMessageDto.getMessageId();
                if (MSGCD_DEPENDING_ON_RECEIPT.equals(msgCd) || MSGCD_STOCK_ERROR.equals(msgCd)) {
                    return;
                }
            }

            // 在庫チェックのエラーがなければ購入制限チェック
            if (stockDto.getSaleYesNo().equals(1)) {
                // 販売可否判定結果が購入不可の場合
                if (!saleNgMessageList.contains(stockDto.getSaleNgMessage())) {
                    CheckMessageDto checkMessageDto = new CheckMessageDto();
                    // 購入不可時エラー処理
                    checkMessageDto.setMessageId(MSGCD_SALE_NG);
                    checkMessageDto.setArgs(new String[] {stockDto.getSaleNgMessage()});
                    // 2023-renew No14 from here
                    checkMessageDto.setError(true);
                    // 2023-renew No14 to here
                    checkMessageDtoList.add(checkMessageDto);
                    saleNgMessageList.add(stockDto.getSaleNgMessage());
                }
            }
        }
    }

    /**
     * 引数で指定された受注商品クラスを
     * ディープコピーしたものを返却します。
     *
     * @param orderGoodsEntity 受注商品クラス
     * @return コピーされた受注商品クラス
     */
    public OrderGoodsEntity copyOrderGoodsEntity(OrderGoodsEntity orderGoodsEntity) {
        // 受注商品情報をコピー
        return CopyUtil.deepCopy(orderGoodsEntity);
    }

    /**
     * 引数で指定された商品在庫情報を
     * ディープコピーしたものを返却します。
     *
     * @param stockMap 商品在庫情報
     * @return コピーされた商品在庫情報
     */
    public Map<String, WebApiGetStockResponseDetailDto> copyStockMap(Map<String, WebApiGetStockResponseDetailDto> stockMap) {

        Map<String, WebApiGetStockResponseDetailDto> copyStockMap = new HashMap<>();

        // MAP内の要素を全て取り出す
        for (String key : stockMap.keySet()) {
            // 在庫情報をコピー
            copyStockMap.put(
                            key, CopyUtil.deepCopy(
                                            stockMap.get(goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(
                                                            key))));
        }
        return copyStockMap;
    }

    /**
     * 引数で指定された入荷次第お届け商品情報を
     * ディープコピーしたものを返却します。
     *
     * @param dependingOnReceiptGoodsDtoList 入荷次第お届け商品情報
     * @return コピーされた入荷次第お届け商品情報
     */
    public List<OrderDependingOnReceiptGoodsDto> copyDependingOnReceiptGoodsDtoList(List<OrderDependingOnReceiptGoodsDto> dependingOnReceiptGoodsDtoList) {

        List<OrderDependingOnReceiptGoodsDto> copyList = new ArrayList<>();

        for (OrderDependingOnReceiptGoodsDto dependingOnReceiptGoodsDto : dependingOnReceiptGoodsDtoList) {
            copyList.add(CopyUtil.deepCopy(dependingOnReceiptGoodsDto));
        }

        return copyList;
    }

    /**
     * 引数で指定された取りおき商品情報を
     * ディープコピーしたものを返却します。
     *
     * @param reserveDeliveryDtoList 取りおき商品情報
     * @return コピーされた取りおき商品情報
     */
    public List<OrderReserveDeliveryDto> copyReserveDeliveryDtoList(List<OrderReserveDeliveryDto> reserveDeliveryDtoList) {
        List<OrderReserveDeliveryDto> copyList = new ArrayList<>();

        for (OrderReserveDeliveryDto reserveDeliveryDto : reserveDeliveryDtoList) {
            copyList.add(CopyUtil.deepCopy(reserveDeliveryDto));
        }

        return copyList;
    }

    /**
     * 引数で指定された今すぐお届け商品情報を
     * ディープコピーしたものを返却します。
     *
     * @param orderDeliveryNowDtoList 今すぐお届け商品情報
     * @return コピーされた今すぐお届け商品情報
     */
    public List<OrderDeliveryNowDto> copyDeliveryNowDtoList(List<OrderDeliveryNowDto> orderDeliveryNowDtoList) {

        List<OrderDeliveryNowDto> copyList = new ArrayList<>();

        for (OrderDeliveryNowDto reserveDeliveryDto : orderDeliveryNowDtoList) {
            copyList.add(CopyUtil.deepCopy(reserveDeliveryDto));
        }
        return copyList;
    }

}
// PDR Migrate Customization to here
