/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTotalingType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockOrderReserveUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderGoodsReserveService;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 受注修正商品確保処理サービス
 *
 * @author YAMAGUCHI
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class OrderGoodsReserveServiceImpl extends AbstractShopService implements OrderGoodsReserveService {

    /**
     * 受注商品リスト取得ロジック
     */
    private final OrderGoodsListGetLogic orderGoodsListGetLogic;

    /**
     * 受注商品登録ロジック
     */
    private final OrderGoodsRegistLogic orderGoodsRegistLogic;

    /**
     * 在庫確保ロジック
     */
    private final StockOrderReserveUpdateLogic stockOrderReserveUpdateLogic;

    /**
     * 受注関連ユーティリティ
     */
    private final OrderUtility orderUtility;

    @Autowired
    public OrderGoodsReserveServiceImpl(OrderGoodsListGetLogic orderGoodsListGetLogic,
                                        OrderGoodsRegistLogic orderGoodsRegistLogic,
                                        StockOrderReserveUpdateLogic stockOrderReserveUpdateLogic,
                                        OrderUtility orderUtility) {

        this.orderGoodsListGetLogic = orderGoodsListGetLogic;
        this.orderGoodsRegistLogic = orderGoodsRegistLogic;
        this.stockOrderReserveUpdateLogic = stockOrderReserveUpdateLogic;
        this.orderUtility = orderUtility;
    }

    /**
     * 実行メソッド
     *
     * @param receiveOrderDto  受注Dto
     * @param orderIndexEntity 受注インデックス
     * @param processTime      処理時間
     * @return 変更後の受注商品の履歴番号
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Integer execute(ReceiveOrderDto receiveOrderDto, OrderIndexEntity orderIndexEntity, Timestamp processTime) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("receiveOrderDto", receiveOrderDto);
        ArgumentCheckUtil.assertNotNull("orderIndexEntity", orderIndexEntity);
        ArgumentCheckUtil.assertNotNull("processTime", processTime);

        // 受注商品リスト登録
        Integer addcount = registOrderGoodsList(receiveOrderDto, orderIndexEntity, processTime);

        Integer newOrderGoodsVersionNo =
                        receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList().get(0).getOrderGoodsVersionNo();
        if (addcount != null && addcount != 0) {
            if (newOrderGoodsVersionNo != null) {
                // 在庫確保
                Integer orderSeq = orderIndexEntity.getOrderSeq();

                int count = 0;

                OrderDeliveryEntity orderDeliveryEntity =
                                receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity();
                int mode = StockOrderReserveUpdateLogic.RESERVE;
                if (HTypeShipmentStatus.SHIPPED == orderDeliveryEntity.getShipmentStatus()) {
                    mode = 9;
                }
                count = count + stockOrderReserveUpdateLogic.execute(orderSeq, newOrderGoodsVersionNo,
                                                                     orderDeliveryEntity.getOrderConsecutiveNo(), mode
                                                                    );

                if (addcount != count) {
                    this.throwMessage(MSGCD_ERROR);
                }
            }
        }

        if (newOrderGoodsVersionNo != null) {
            orderIndexEntity.setOrderGoodsVersionNo(newOrderGoodsVersionNo);
        }

        if (addcount == null) {
            // 受注商品に変更が無い場合、nullを返す。
            return null;
        } else {
            // 受注商品に変更があった場合、対応する受注商品の履歴番号を返す。
            return newOrderGoodsVersionNo;
        }
    }

    /**
     * 受注商品情報登録処理
     * 受注商品情報に変更が無い場合は、nullを返す。
     *
     * @param receiveOrderDto  受注Dto
     * @param orderIndexEntity 受注インデックス
     * @param processTime      処理時間
     * @return 数量が増えた商品の種類数(変更が無い場合はnullを返す)
     */
    protected Integer registOrderGoodsList(ReceiveOrderDto receiveOrderDto,
                                           OrderIndexEntity orderIndexEntity,
                                           Timestamp processTime) {

        Integer shopSeq = orderIndexEntity.getShopSeq();
        Integer orderSeq = orderIndexEntity.getOrderSeq();
        Integer orderGoodsVersionNo = orderIndexEntity.getOrderGoodsVersionNo();

        // 前回の受注商品リスト取得
        List<OrderGoodsEntity> preOrderGoodsEntityList = orderGoodsListGetLogic.execute(orderSeq, orderGoodsVersionNo);
        // 今回の受注商品リスト取得
        List<OrderGoodsEntity> orderGoodsEntityList = orderUtility.getALLGoodsEntityList(receiveOrderDto);
        // 出荷状態Mapを作成する。
        Map<Integer, HTypeShipmentStatus> shipmentStatusMap = createShipmentStatus(receiveOrderDto);

        List<String> diffList = DiffUtil.diff(preOrderGoodsEntityList, orderGoodsEntityList);

        // 相違点が見つからない場合は処理を抜けてnullを返す
        if (diffList == null) {
            return null;
        } else {
            for (int index = 0; index < diffList.size(); ) {
                String diff = diffList.get(index);
                // preGoodsCountは受注可能チェック処理で更新されている且つgoodsCountで数量の変更は確認できるので
                // diffリストから削除する
                if (diff.endsWith(".preGoodsCount")) {
                    diffList.remove(diff);
                } else {
                    index++;
                }
            }
            if (diffList.isEmpty()) {
                return null;
            }
        }

        // 今回の受注商品リストを登録
        Integer newOrderGoodsVersionNo = orderGoodsVersionNo + 1;
        HTypeSalesFlag salesflag = null;

        List<String> addgoodsList = new ArrayList<>();
        Map<String, BigDecimal> goodsCountMap = new HashMap<>();
        Map<String, BigDecimal> unShipmentGoodsCountMap = new HashMap<>();

        Iterator<OrderGoodsEntity> preOrderGoodsIte = preOrderGoodsEntityList.iterator();
        int orderDisplay = 1;

        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {

            if (orderGoodsEntity.getShopSeq() == null) {
                orderGoodsEntity.setShopSeq(shopSeq);
                orderGoodsEntity.setPreGoodsCount(BigDecimal.ZERO);
                orderGoodsEntity.setSalesFlag(salesflag);
            } else {
                if (preOrderGoodsIte.hasNext()) {
                    OrderGoodsEntity preOrderGoodsEntity = preOrderGoodsIte.next();
                    orderGoodsEntity.setPreGoodsCount(preOrderGoodsEntity.getGoodsCount());
                    if (salesflag == null) {
                        salesflag = preOrderGoodsEntity.getSalesFlag();
                    }
                }
            }
            orderGoodsEntity.setOrderSeq(orderSeq);
            orderGoodsEntity.setOrderGoodsVersionNo(newOrderGoodsVersionNo);
            orderGoodsEntity.setProcessTime(processTime);
            orderGoodsEntity.setOrderDisplay(Integer.valueOf(orderDisplay));
            orderGoodsEntity.setTotalingType(HTypeTotalingType.CHANGE);
            orderGoodsRegistLogic.execute(orderGoodsEntity);

            BigDecimal mapCount = goodsCountMap.get(
                            orderGoodsEntity.getOrderConsecutiveNo().toString() + orderGoodsEntity.getGoodsSeq()
                                                                                                  .toString());
            if (mapCount != null) {
                BigDecimal sumCount = orderGoodsEntity.getGoodsCount().subtract(orderGoodsEntity.getPreGoodsCount());
                goodsCountMap.put(
                                orderGoodsEntity.getOrderConsecutiveNo().toString() + orderGoodsEntity.getGoodsSeq()
                                                                                                      .toString(),
                                mapCount.add(sumCount)
                                 );
            } else {
                BigDecimal sumCount = orderGoodsEntity.getGoodsCount().subtract(orderGoodsEntity.getPreGoodsCount());
                goodsCountMap.put(
                                orderGoodsEntity.getOrderConsecutiveNo().toString() + orderGoodsEntity.getGoodsSeq()
                                                                                                      .toString(),
                                sumCount
                                 );
            }
            BigDecimal addcount = orderGoodsEntity.getGoodsCount().subtract(orderGoodsEntity.getPreGoodsCount());
            if (HTypeShipmentStatus.SHIPPED == shipmentStatusMap.get(orderGoodsEntity.getOrderConsecutiveNo())
                && addcount.compareTo(BigDecimal.ZERO) > 0 && !addgoodsList.contains(
                            orderGoodsEntity.getOrderConsecutiveNo().toString() + orderGoodsEntity.getGoodsSeq()
                                                                                                  .toString())) {
                addgoodsList.add(orderGoodsEntity.getOrderConsecutiveNo().toString() + orderGoodsEntity.getGoodsSeq()
                                                                                                       .toString());
            }

            if (HTypeShipmentStatus.UNSHIPMENT == shipmentStatusMap.get(orderGoodsEntity.getOrderConsecutiveNo())) {
                BigDecimal unShipmentMapCount = unShipmentGoodsCountMap.get(
                                orderGoodsEntity.getOrderConsecutiveNo().toString() + orderGoodsEntity.getGoodsSeq()
                                                                                                      .toString());
                if (unShipmentMapCount != null) {
                    BigDecimal sumCount =
                                    orderGoodsEntity.getGoodsCount().subtract(orderGoodsEntity.getPreGoodsCount());
                    unShipmentGoodsCountMap.put(
                                    orderGoodsEntity.getOrderConsecutiveNo().toString() + orderGoodsEntity.getGoodsSeq()
                                                                                                          .toString(),
                                    unShipmentMapCount.add(sumCount)
                                               );
                } else {
                    BigDecimal sumCount =
                                    orderGoodsEntity.getGoodsCount().subtract(orderGoodsEntity.getPreGoodsCount());
                    unShipmentGoodsCountMap.put(
                                    orderGoodsEntity.getOrderConsecutiveNo().toString() + orderGoodsEntity.getGoodsSeq()
                                                                                                          .toString(),
                                    sumCount
                                               );
                }
            }

            orderDisplay++;
        }

        Iterator<String> ite = unShipmentGoodsCountMap.keySet().iterator();
        for (; ite.hasNext(); ) {
            String orderConsecutiveNoGoodsseq = ite.next();
            BigDecimal count = unShipmentGoodsCountMap.get(orderConsecutiveNoGoodsseq);
            if (count.compareTo(BigDecimal.ZERO) > 0) {
                addgoodsList.add(orderConsecutiveNoGoodsseq);
            }
        }

        return addgoodsList.size();
    }

    /**
     * 配送毎の出荷状態を保持するMapを作成する<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @return 出荷状態Map
     */
    protected Map<Integer, HTypeShipmentStatus> createShipmentStatus(ReceiveOrderDto receiveOrderDto) {

        Map<Integer, HTypeShipmentStatus> shipmentStatusMap = new HashMap<>();

        OrderDeliveryEntity entity = receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity();
        shipmentStatusMap.put(entity.getOrderConsecutiveNo(), entity.getShipmentStatus());

        return shipmentStatusMap;
    }
}
