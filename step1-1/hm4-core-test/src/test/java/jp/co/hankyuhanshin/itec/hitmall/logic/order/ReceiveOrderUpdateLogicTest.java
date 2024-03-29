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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailRequired;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeWaitingFlag;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReceiveOrderUpdateLogicTest {

    @Autowired
    ReceiveOrderUpdateLogic receiveOrderUpdateLogic;

    /**
     * 受注サマリ排他取得ロジック
     */
    @MockBean
    private OrderSummaryGetForUpdateLogic orderSummaryGetForUpdateLogic;

    /**
     * 受注インデックス取得ロジック
     */
    @MockBean
    private OrderIndexGetLogic orderIndexGetLogic;

    /**
     * 受注ご注文主取得ロジック
     */
    @MockBean
    private OrderPersonGetLogic orderPersonGetLogic;

    /**
     * 受注配送取得ロジック
     */
    @MockBean
    private OrderDeliveryGetLogic orderDeliveryGetLogic;

    /**
     * 受注決済取得ロジック
     */
    @MockBean
    private OrderSettlementGetLogic orderSettlementGetLogic;

    /**
     * 受注請求取得ロジック
     */
    @MockBean
    private OrderBillGetLogic orderBillGetLogic;

    /**
     * 受注インデックス登録ロジック
     */
    @MockBean
    private OrderIndexRegistLogic orderIndexRegistLogic;

    @Test
    @Order(1)
    public void executeCancelOrderUpdate() {

        // 想定値設定
        SettlementMethodEntity entity = new SettlementMethodEntity();
        entity.setSettlementMethodType(HTypeSettlementMethodType.CREDIT);
        entity.setSettlementMethodSeq(1);
        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        orderSummaryEntity.setOrderSeq(1);
        orderSummaryEntity.setShopSeq(1);
        orderSummaryEntity.setMemberInfoSeq(1);
        orderSummaryEntity.setOrderCode("AA");
        orderSummaryEntity.setOrderVersionNo(1);
        orderSummaryEntity.setWaitingFlag(HTypeWaitingFlag.OFF);

        List<OrderGoodsEntity> listOrderGoodsEntity = new ArrayList<>();
        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();
        orderGoodsEntity.setOrderGoodsVersionNo(1);
        listOrderGoodsEntity.add(orderGoodsEntity);
        OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
        orderDeliveryEntity.setOrderDeliveryVersionNo(1);
        List<OrderDeliveryEntity> listOrderDeliveryEntity = new ArrayList<>();
        listOrderDeliveryEntity.add(orderDeliveryEntity);
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
        orderPersonEntity.setOrderPersonVersionNo(1);
        MulPayBillEntity mulPayBillEntity = new MulPayBillEntity();
        mulPayBillEntity.setForward("GG");
        OrderBillEntity orderBillEntity = new OrderBillEntity();
        orderBillEntity.setEmergencyFlag(HTypeEmergencyFlag.OFF);

        OrderSettlementEntity orderSettlementEntity = new OrderSettlementEntity();
        orderSettlementEntity.setSettlementMethodType(HTypeSettlementMethodType.RECEIPT_PAYMENT);
        orderSettlementEntity.setBillType(HTypeBillType.POST_CLAIM);
        ReceiveOrderDto receiveOrderDto = new ReceiveOrderDto();

        OrderIndexEntity orderIndexEntity = new OrderIndexEntity();
        orderIndexEntity.setOrderVersionNo(1);
        orderIndexEntity.setSettlementMailRequired(HTypeMailRequired.REQUIRED);
        orderIndexEntity.setOrderSettlementVersionNo(1);
        orderIndexEntity.setOrderPersonVersionNo(1);
        orderIndexEntity.setOrderDeliveryVersionNo(1);
        orderIndexEntity.setOrderBillVersionNo(1);

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
        receiveOrderDto.setOrderIndexEntity(orderIndexEntity);
        receiveOrderDto.setRecoveryAuthoryFlag(true);

        // モック設定
        doReturn(orderIndexEntity).when(orderIndexGetLogic).execute(any(Integer.class), any(Integer.class));
        doReturn(orderSummaryEntity).when(orderSummaryGetForUpdateLogic)
                                    .execute(any(String.class), any(Integer.class), any(Integer.class));
        doReturn(orderPersonEntity).when(orderPersonGetLogic).execute(any(Integer.class), any(Integer.class));
        doReturn(listOrderDeliveryEntity).when(orderDeliveryGetLogic).execute(any(Integer.class), any(Integer.class));
        doReturn(orderSettlementEntity).when(orderSettlementGetLogic).execute(any(Integer.class), any(Integer.class));
        doReturn(orderBillEntity).when(orderBillGetLogic).execute(any(Integer.class), any(Integer.class));
        doReturn(1).when(orderIndexRegistLogic).execute(any(OrderIndexEntity.class));

        // 試験実行
        receiveOrderUpdateLogic.executeCancelOrderUpdate(receiveOrderDto);

        // 戻り値及び呼び出し検証
        verify(orderIndexGetLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        verify(orderSummaryGetForUpdateLogic, times(1)).execute(
                        any(String.class), any(Integer.class), any(Integer.class));
        verify(orderPersonGetLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        verify(orderDeliveryGetLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        verify(orderSettlementGetLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        verify(orderBillGetLogic, times(1)).execute(any(Integer.class), any(Integer.class));
        verify(orderIndexRegistLogic, times(1)).execute(any(OrderIndexEntity.class));
    }
}
