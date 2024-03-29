package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockReserveUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.CommonInfoUtility;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReceiveOrderRegisterLogicTest {

    @Autowired
    ReceiveOrderRegisterLogic receiveOrderRegisterLogic;

    @MockBean
    /** 新規受注SEQ取得ロジック */ private NewOrderSeqGetLogic newOrderSeqGetLogic;

    @MockBean
    /** 在庫情報注文確保更新ロジック */ private StockReserveUpdateLogic stockReserveUpdateLogic;

    /**
     * 共通情報Utility
     */
    @MockBean
    private CommonInfoUtility commonInfoUtility;

    /**
     * 受注サマリ登録ロジック
     */
    @MockBean
    private OrderSummaryRegistLogic orderSummaryRegistLogic;

    /**
     * 受注ご注文主登録ロジック
     */
    @MockBean
    private OrderPersonRegistLogic orderPersonRegistLogic;

    /**
     * 受注配送登録ロジック
     */
    @MockBean
    private OrderDeliveryRegistLogic orderDeliveryRegistLogic;

    /**
     * 受注決済登録ロジック
     */
    @MockBean
    private OrderSettlementRegistLogic orderSettlementRegistLogic;

    /**
     * 受注請求登録ロジック
     */
    @MockBean
    private OrderBillRegistLogic orderBillRegistLogic;

    /**
     * 受注インデックス登録ロジック
     */
    @MockBean
    private OrderIndexRegistLogic orderIndexRegistLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        ReceiveOrderDto receiveOrderDto = new ReceiveOrderDto();
        SettlementMethodEntity entity = new SettlementMethodEntity();
        entity.setSettlementMethodType(HTypeSettlementMethodType.CREDIT);
        entity.setSettlementMethodSeq(1);
        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        orderSummaryEntity.setOrderSeq(1);
        orderSummaryEntity.setShopSeq(1);
        orderSummaryEntity.setMemberInfoSeq(1);

        List<OrderGoodsEntity> listOrderGoodsEntity = new ArrayList<>();
        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();
        orderGoodsEntity.setOrderGoodsVersionNo(1);
        listOrderGoodsEntity.add(orderGoodsEntity);
        OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
        OrderDeliveryDto orderDeliveryDto = new OrderDeliveryDto();
        orderDeliveryDto.setOrderGoodsEntityList(listOrderGoodsEntity);
        orderDeliveryDto.setOrderDeliveryEntity(orderDeliveryEntity);
        OrderTempDto orderTempDto = new OrderTempDto();
        orderTempDto.setExpire("A");
        orderTempDto.setSecurityCode("B");
        orderTempDto.setRegistCredit(true);
        orderTempDto.setUseRegistCardFlg(true);
        orderTempDto.setToken("AAAA");

        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        MulPayBillEntity mulPayBillEntity = new MulPayBillEntity();
        mulPayBillEntity.setForward("GG");
        OrderBillEntity orderBillEntity = new OrderBillEntity();

        OrderSettlementEntity orderSettlementEntity = new OrderSettlementEntity();
        orderSettlementEntity.setSettlementMethodType(HTypeSettlementMethodType.RECEIPT_PAYMENT);
        orderSettlementEntity.setBillType(HTypeBillType.POST_CLAIM);
        receiveOrderDto.setSettlementMethodEntity(entity);
        receiveOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        receiveOrderDto.setOrderDeliveryDto(orderDeliveryDto);
        receiveOrderDto.setOrderSettlementEntity(orderSettlementEntity);
        receiveOrderDto.setPeriodicOrderFlag(true);
        receiveOrderDto.setNextSettlementMethodEntity(entity);
        receiveOrderDto.setOrderTempDto(orderTempDto);
        receiveOrderDto.setOrderPersonEntity(orderPersonEntity);
        receiveOrderDto.setMulPayBillEntity(mulPayBillEntity);
        receiveOrderDto.setOrderBillEntity(orderBillEntity);

        // モック設定
        doReturn(1).when(newOrderSeqGetLogic).execute();
        doReturn(1).when(orderSummaryRegistLogic).execute(any(OrderSummaryEntity.class));
        doReturn(0).when(stockReserveUpdateLogic).execute(any(Integer.class), any(Integer.class));
        doReturn(false).when(commonInfoUtility).isSiteBack(any(CommonInfo.class));
        doReturn(1).when(orderPersonRegistLogic).execute(any(OrderPersonEntity.class));
        doReturn(1).when(orderDeliveryRegistLogic).execute(any(OrderDeliveryEntity.class));
        doReturn(1).when(orderSettlementRegistLogic).execute(any(OrderSettlementEntity.class));
        doReturn(1).when(orderBillRegistLogic).execute(any(OrderBillEntity.class));
        doReturn(1).when(orderIndexRegistLogic).execute(any(OrderIndexEntity.class));

        // 試験実行
        receiveOrderRegisterLogic.execute(receiveOrderDto);

        // 戻り値及び呼び出し検証
        verify(newOrderSeqGetLogic, times(1)).execute();
        verify(orderSummaryRegistLogic, times(1)).execute(any(OrderSummaryEntity.class));
        verify(stockReserveUpdateLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        verify(commonInfoUtility, times(1)).isSiteBack(any(CommonInfo.class));
        verify(orderPersonRegistLogic, times(1)).execute(any(OrderPersonEntity.class));
        verify(orderDeliveryRegistLogic, times(1)).execute(any(OrderDeliveryEntity.class));
        verify(orderSettlementRegistLogic, times(1)).execute(any(OrderSettlementEntity.class));
        verify(orderBillRegistLogic, times(1)).execute(any(OrderBillEntity.class));
        verify(orderIndexRegistLogic, times(1)).execute(any(OrderIndexEntity.class));
    }
}
