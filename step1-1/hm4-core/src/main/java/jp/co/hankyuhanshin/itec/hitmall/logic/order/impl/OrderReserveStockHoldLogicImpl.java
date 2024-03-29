/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTotalingType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.OrderRegistable;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockReserveUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderReserveStockHoldLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 在庫確保ロジック
 *
 * @author yt23807
 */
@Component
public class OrderReserveStockHoldLogicImpl extends AbstractShopLogic implements OrderReserveStockHoldLogic {

    /**
     * 受注商品登録ロジック
     */
    private final OrderGoodsRegistLogic orderGoodsRegistLogic;

    /**
     * 在庫情報注文確保更新ロジック
     */
    private final StockReserveUpdateLogic stockReserveUpdateLogic;

    /**
     * 変換Utlility
     */
    private final ConversionUtility conversionUtility;

    /**
     * 受注Utility
     */
    private final OrderUtility orderUtility;

    /**
     * コンストラクタ
     *
     * @param orderGoodsRegistLogic
     * @param stockReserveUpdateLogic
     * @param conversionUtility
     * @param orderUtility
     */
    @Autowired
    public OrderReserveStockHoldLogicImpl(OrderGoodsRegistLogic orderGoodsRegistLogic,
                                          StockReserveUpdateLogic stockReserveUpdateLogic,
                                          ConversionUtility conversionUtility,
                                          OrderUtility orderUtility) {
        this.orderGoodsRegistLogic = orderGoodsRegistLogic;
        this.stockReserveUpdateLogic = stockReserveUpdateLogic;
        this.conversionUtility = conversionUtility;
        this.orderUtility = orderUtility;
    }

    /**
     * 実行メソッド
     *
     * @param receiveOrderDto 受注Dto
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void execute(ReceiveOrderDto receiveOrderDto) {

        // ★補足★
        // 本クラスは、別トランザクションを有効化するために、HIT-MALLVer4で追加したLogicである
        // 
        // ※詳細経緯※
        // @Transactional(propagation = Propagation.REQUIRES_NEW)　は、呼び出し時にクラスをまたぐ必要がありそう
        // ※同一クラス内でのメソッド呼び出しの場合、別トランザクションになってくれなかった。。（動作確認実績あり）
        // そのため、HIT-MALLVer3ではAbstractOrderRegisterLogicに書かれていた処理を、ここに移植した

        // 受注商品登録
        List<Integer> orderGoodsSeqList = registOrderGoods(receiveOrderDto);

        // 在庫引当処理
        if (receiveOrderDto.getOrderDeliveryDto() == null || receiveOrderDto.getOrderDeliveryDto() == null) {
            // 配送リストが無いため処理できない
            throwMessage(OrderRegistable.MSGCD_UPDATE_STOCK_FAIL);
        }
        if (receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList() == null
            || receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList().size() == 0) {
            // 商品リストが無いため処理できない
            throwMessage(OrderRegistable.MSGCD_UPDATE_STOCK_FAIL);
        }
        /* 受注商品連番 */
        Integer orderGoodsVersionNo =
                        receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList().get(0).getOrderGoodsVersionNo();

        int updateStockCount =
                        updateStockReserve(receiveOrderDto.getOrderSummaryEntity().getOrderSeq(), orderGoodsVersionNo);

        // 商品SEQリストの件数と、在庫更新件数が一致しているかどうか
        if (orderGoodsSeqList.size() != updateStockCount) {
            // 一致しないのでエラー。処理終了。
            throwMessage(OrderRegistable.MSGCD_UPDATE_STOCK_FAIL);
        }
    }

    /**
     * 受注商品登録<br/>
     *
     * @param receiveOrderDto 受注DTO
     * @return 受注商品SEQリスト
     */
    @Override
    public List<Integer> registOrderGoods(ReceiveOrderDto receiveOrderDto) {
        // 注文DTOから注文商品リストを取得
        List<OrderGoodsEntity> orderGoodsEntityList = orderUtility.getALLGoodsEntityList(receiveOrderDto);
        // 表示順カウンターの初期化
        int orderDisplay = 1;
        // 受注商品SEQリスト作成
        List<Integer> orderGoodsSeqList = new ArrayList<>();

        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            // 受注商品エンティティ登録準備
            orderDisplay = makeOrderGoodsEntityForInsert(receiveOrderDto, orderDisplay, orderGoodsEntity);

            // 受注商品登録ロジック実行
            orderGoodsRegistLogic.execute(orderGoodsEntity);

            // 商品SEQリストに追加済みかどうか
            if (!orderGoodsSeqList.contains(orderGoodsEntity.getGoodsSeq())) {
                // 追加してない商品SEQなので追加する
                orderGoodsSeqList.add(orderGoodsEntity.getGoodsSeq());
            }
        }

        return orderGoodsSeqList;
    }

    /**
     * 受注商品エンティティの登録準備を行う。<br/>
     * <pre>
     * 受注商品エンティティに登録に必要な値を設定する。
     * 表示順を返す。
     * </pre>
     *
     * @param receiveOrderDto  受注DTO
     * @param orderDisplay     表示順
     * @param orderGoodsEntity 受注商品エンティティ
     * @param customParams     案件用引数
     * @return 表示順
     */
    protected int makeOrderGoodsEntityForInsert(ReceiveOrderDto receiveOrderDto,
                                                int orderDisplay,
                                                OrderGoodsEntity orderGoodsEntity,
                                                Object... customParams) {
        // 受注サマリエンティティを取得
        OrderSummaryEntity orderSummaryEntity = receiveOrderDto.getOrderSummaryEntity();

        // ショップSEQ
        orderGoodsEntity.setShopSeq(1001);
        // 受注SEQ
        orderGoodsEntity.setOrderSeq(orderSummaryEntity.getOrderSeq());
        // 受注商品連番(『１固定』
        orderGoodsEntity.setOrderGoodsVersionNo(1);
        // 表示順
        orderGoodsEntity.setOrderDisplay(orderDisplay++);
        // 集計種別 注文受付時の状態は「受注」
        orderGoodsEntity.setTotalingType(HTypeTotalingType.ORDER);
        // 売上フラグ 注文受付時のフラグは「未売上」
        orderGoodsEntity.setSalesFlag(HTypeSalesFlag.OFF);
        // 処理日（ミリ秒まで）
        orderGoodsEntity.setProcessTime(orderSummaryEntity.getOrderTime());
        return orderDisplay;
    }

    /**
     * 在庫引当処理
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @return 更新件数
     */
    protected int updateStockReserve(Integer orderSeq, Integer orderGoodsVersionNo) {
        return stockReserveUpdateLogic.execute(orderSeq, orderGoodsVersionNo);
    }

}
