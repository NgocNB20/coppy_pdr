package jp.co.hankyuhanshin.itec.hitmall.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;

/**
 * Logic/Service移行：決済方法選択可能リスト取得サービス
 * 作成日：2021/03/24
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettlementMethodSelectListGetServiceTest {

    @Autowired
    SettlementMethodSelectListGetService service;

    @MockBean
    private CalculatePriceUtility calculatePriceUtility;

    @MockBean
    private OrderUtility orderUtility;

    @MockBean
    private SettlementMethodListGetLogic settlementMethodListGetLogic;

    @SuppressWarnings("unchecked")
    @Test
    @Order(1)
    public void executeTest() {

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
        orderDeliveryEntity.setReceiverZipCode("A");
        orderDeliveryEntity.setReceiverPrefecture("A");
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
        orderSettlementEntity.setGoodsPriceTotal(BigDecimal.TEN);
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
        receiveOrderDto.setPeriodicOrderFlag(true);
        receiveOrderDto.setOrderNextSettlementEntity(orderSettlementEntity);

        Boolean allowCreditFlag = true;

        SettlementSearchForDaoConditionDto conditionDto = new SettlementSearchForDaoConditionDto();
        List<Integer> deliveryMethodSeqList = Arrays.asList(1, 2);
        List<SettlementDto> result = new ArrayList<>();

        // モック設定
        doReturn(conditionDto).when(calculatePriceUtility)
                              .getSettlementSearchForDaoConditionDto(any(ReceiveOrderDto.class), any(CommonInfo.class),
                                                                     any(OrderSettlementEntity.class)
                                                                    );
        doReturn(deliveryMethodSeqList).when(orderUtility)
                                       .createSelectDeliveryMethodSeqList(any(ReceiveOrderDto.class));
        doReturn(result).when(settlementMethodListGetLogic)
                        .execute(any(SettlementSearchForDaoConditionDto.class), any(), any(BigDecimal.class), any(),
                                 any(List.class), any(BigDecimal.class)
                                );

        // 試験実行
        List<SettlementDto> actual = service.execute(receiveOrderDto, allowCreditFlag);

        // 戻り値及び呼び出し検証
        verify(calculatePriceUtility, times(1)).getSettlementSearchForDaoConditionDto(
                        any(ReceiveOrderDto.class), any(CommonInfo.class), any(OrderSettlementEntity.class));
        verify(orderUtility, times(1)).createSelectDeliveryMethodSeqList(any(ReceiveOrderDto.class));
        verify(settlementMethodListGetLogic, times(1)).execute(
                        any(SettlementSearchForDaoConditionDto.class), any(), any(BigDecimal.class), any(),
                        any(List.class), any(BigDecimal.class)
                                                              );
        assertThat(actual).isEqualTo(result);
    }
}
