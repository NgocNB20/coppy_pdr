package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderInfoMasterDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetQuantityDiscountResultRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetQuantityDiscountResultResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetQuantityDiscountResultResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetQuantityDiscountResultLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderQuantityDiscountLogicTest {

    @Autowired
    OrderQuantityDiscountLogic orderQuantityDiscountLogic;

    @MockBean
    WebApiGetQuantityDiscountResultLogic webApiGetQuantityDiscountResultLogic;

    @Test
    void execute() throws IOException {

        // Setup data
        Integer goodsSeq = 1;
        // 想定値設定
        ReceiveOrderDto receiveOrderDto = new ReceiveOrderDto();

        SettlementMethodEntity entity = new SettlementMethodEntity();
        entity.setSettlementMethodType(HTypeSettlementMethodType.CREDIT);
        entity.setSettlementMethodSeq(1);

        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        orderSummaryEntity.setOrderSeq(1);
        orderSummaryEntity.setShopSeq(1);
        orderSummaryEntity.setMemberInfoSeq(1);

        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();
        orderGoodsEntity.setGoodsSeq(goodsSeq);
        orderGoodsEntity.setOrderGoodsVersionNo(1);
        orderGoodsEntity.setGoodsCode("goodsCode");
        orderGoodsEntity.setGoodsPrice(new BigDecimal("1000"));
        List<OrderGoodsEntity> listOrderGoodsEntity = new ArrayList<>();
        listOrderGoodsEntity.add(orderGoodsEntity);

        OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
        orderDeliveryEntity.setReceiverZipCode("A");
        orderDeliveryEntity.setReceiverPrefecture("A");

        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        goodsDetailsDto.setGoodsSeq(goodsSeq);
        goodsDetailsDto.setPurchasedMax(BigDecimal.TEN);

        Map<Integer, GoodsDetailsDto> goodsMaster = new HashMap<>();
        goodsMaster.put(goodsSeq, goodsDetailsDto);

        OrderInfoMasterDto orderInfoMasterDto = new OrderInfoMasterDto();
        orderInfoMasterDto.setGoodsMaster(goodsMaster);

        receiveOrderDto.setMasterDto(orderInfoMasterDto);

        OrderDeliveryDto orderDeliveryDto = new OrderDeliveryDto();
        orderDeliveryDto.setOrderGoodsEntityList(listOrderGoodsEntity);
        orderDeliveryDto.setOrderDeliveryEntity(orderDeliveryEntity);

        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        orderPersonEntity.setCustomerNo(1001);

        OrderSettlementEntity orderSettlementEntity = new OrderSettlementEntity();
        orderSettlementEntity.setSettlementMethodType(HTypeSettlementMethodType.RECEIPT_PAYMENT);
        orderSettlementEntity.setBillType(HTypeBillType.POST_CLAIM);

        receiveOrderDto.setSettlementMethodEntity(entity);
        receiveOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        receiveOrderDto.setOrderDeliveryDto(orderDeliveryDto);
        receiveOrderDto.setOrderSettlementEntity(orderSettlementEntity);
        receiveOrderDto.setPeriodicOrderFlag(true);
        receiveOrderDto.setNextSettlementMethodEntity(entity);
        receiveOrderDto.setOrderPersonEntity(orderPersonEntity);

        WebApiGetConsumptionTaxRateResponseDetailDto taxRateDto = new WebApiGetConsumptionTaxRateResponseDetailDto();
        taxRateDto.setTaxRate(new BigDecimal("5"));

        Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> taxRateMap = new HashMap<>();
        taxRateMap.put("goodsCode", taxRateDto);

        List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();

        // Mock
        WebApiGetQuantityDiscountResultRequestDto reqDto = new WebApiGetQuantityDiscountResultRequestDto();
        reqDto.setCustomerNo("1001");
        reqDto.setGoodsCode("goodsCode");
        reqDto.setQuantity("0");

        WebApiGetQuantityDiscountResultResponseDetailDto quantityDiscount =
                        new WebApiGetQuantityDiscountResultResponseDetailDto();
        quantityDiscount.setGoodsCode("goodsCode");
        quantityDiscount.setSalePrice(new BigDecimal("1000"));

        List<WebApiGetQuantityDiscountResultResponseDetailDto> quantityDiscountList = new ArrayList<>();
        quantityDiscountList.add(quantityDiscount);

        WebApiGetQuantityDiscountResultResponseDto resDto = new WebApiGetQuantityDiscountResultResponseDto();
        resDto.setInfo(quantityDiscountList);

        // Mock
        doReturn(resDto).when(webApiGetQuantityDiscountResultLogic).execute(reqDto);

        // Verify
        Assertions.assertDoesNotThrow(
                        () -> orderQuantityDiscountLogic.execute(receiveOrderDto, taxRateMap, checkMessageDtoList));
    }
}
