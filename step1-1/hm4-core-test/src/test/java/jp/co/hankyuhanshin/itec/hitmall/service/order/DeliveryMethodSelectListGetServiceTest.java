package jp.co.hankyuhanshin.itec.hitmall.service.order;

import static org.assertj.core.api.Assertions.assertThat;
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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.GoodsSearchResultListForOrderRegistGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodSelectListGetLogic;

/**
 * Logic/Service移行：配送方法選択可能リスト取得
 * 作成日：2021/03/24
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryMethodSelectListGetServiceTest {

    @Autowired
    DeliveryMethodSelectListGetService service;

    @MockBean
    private DeliveryMethodSelectListGetLogic deliveryMethodSelectListGetLogic;

    @MockBean
    private GoodsSearchResultListForOrderRegistGetLogic inquiryGroupGetLogic;

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

        Integer receiverNo = 0;

        List<DeliveryDto> result = new ArrayList<>();

        // モック設定
        doReturn(result).when(deliveryMethodSelectListGetLogic)
                        .execute(any(DeliverySearchForDaoConditionDto.class), any(), any(), any());

        // 試験実行
        List<DeliveryDto> actual = service.execute(receiveOrderDto);

        // 戻り値及び呼び出し検証
        verify(deliveryMethodSelectListGetLogic, times(1)).execute(
                        any(DeliverySearchForDaoConditionDto.class), any(), any(), any());
        assertThat(actual).isEqualTo(result);
    }
}
