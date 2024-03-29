package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGmoReleaseFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderAgeType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReservationDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTaxRateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTotalingType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderInfoMasterDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryMethodDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.tax.TaxRateEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.impl.BillPriceCalculateLogicImpl;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
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
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.utility.CalculatePriceUtility;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillPriceCalculateLogicTest {

    @Autowired
    BillPriceCalculateLogic billPriceCalculateLogic;

    @MockBean
    private CalculatePriceUtility calculatePriceUtility;

    @MockBean
    private OrderUtility orderUtility;

    @Autowired
    BillPriceCalculateLogicImpl billPriceCalculateLogicImpl;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        OrderSettlementEntity orderSettlementEntity = new OrderSettlementEntity();
        orderSettlementEntity.setBeforeDiscountOrderPrice(BigDecimal.TEN);
        orderSettlementEntity.setCouponDiscountPrice(BigDecimal.ONE);
        orderSettlementEntity.setPreCouponDiscountPrice(BigDecimal.ONE);
        OrderSettlementEntity orderNextSettlementEntity = new OrderSettlementEntity();

        orderPersonEntity.setPrefectureType(HTypePrefectureType.AICHI);
        orderPersonEntity.setOrderAgeType(HTypeOrderAgeType.AGE_10TO19);
        orderPersonEntity.setOrderSex(HTypeOrderSex.FEMALE);

        TaxRateEntity taxRateEntity = new TaxRateEntity();
        taxRateEntity.setTaxSeq(99);
        taxRateEntity.setRate(new BigDecimal("5"));
        taxRateEntity.setRateType(HTypeTaxRateType.REDUCED);
        taxRateEntity.setOrderDisplay(99);
        taxRateEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        taxRateEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        Map<HTypeTaxRateType, TaxRateEntity> taxRateMaster = new HashMap<>();
        taxRateMaster.put(HTypeTaxRateType.STANDARD, taxRateEntity);

        OrderInfoMasterDto orderInfoMasterDto = new OrderInfoMasterDto();
        orderInfoMasterDto.setTaxRateMaster(taxRateMaster);

        List<OrderAdditionalChargeEntity> orderAdditionalChargeEntityList = new ArrayList<>();
        OrderAdditionalChargeEntity orderAdditionalChargeEntity = new OrderAdditionalChargeEntity();
        orderAdditionalChargeEntityList.add(orderAdditionalChargeEntity);
        ReceiveOrderDto receiveOrderDto = new ReceiveOrderDto();
        receiveOrderDto.setOrderPersonEntity(orderPersonEntity);
        receiveOrderDto.setOrderSummaryEntity(orderSummaryEntity);
        receiveOrderDto.setOrderSettlementEntity(orderSettlementEntity);
        receiveOrderDto.setOrderNextSettlementEntity(orderNextSettlementEntity);
        receiveOrderDto.setPeriodicOrderFlag(true);
        receiveOrderDto.setOrderAdditionalChargeEntityList(orderAdditionalChargeEntityList);
        receiveOrderDto.setMasterDto(orderInfoMasterDto);
        boolean carriageCalcFlag = true;

        OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
        orderDeliveryEntity.setOrderDeliveryVersionNo(1);
        orderDeliveryEntity.setOrderConsecutiveNo(1);
        orderDeliveryEntity.setShipmentStatus(HTypeShipmentStatus.UNSHIPMENT);
        orderDeliveryEntity.setReservationDeliveryFlag(HTypeReservationDeliveryFlag.OFF);
        orderDeliveryEntity.setPlanDate(null);
        orderDeliveryEntity.setShipmentDate(null);

        DeliveryMethodEntity deliveryMethodEntity = new DeliveryMethodEntity();
        deliveryMethodEntity.setShopSeq(1);
        deliveryMethodEntity.setDeliveryMethodName("a");
        deliveryMethodEntity.setDeliveryMethodDisplayNamePC("b");
        deliveryMethodEntity.setDeliveryMethodDisplayNameMB("c");

        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();
        orderGoodsEntity.setOrderGoodsVersionNo(1);
        orderGoodsEntity.setOrderConsecutiveNo(1);
        orderGoodsEntity.setOrderDisplay(1);
        orderGoodsEntity.setTotalingType(HTypeTotalingType.ORDER);
        orderGoodsEntity.setSalesFlag(HTypeSalesFlag.ON);
        orderGoodsEntity.setGoodsGroupCode("series-1193");
        orderGoodsEntity.setGoodsGroupName("レジン用小筆");
        orderGoodsEntity.setTaxRate(BigDecimal.TEN);
        List<OrderGoodsEntity> listOrderGoodsEntity = new ArrayList<>();
        listOrderGoodsEntity.add(orderGoodsEntity);

        List<CheckMessageDto> result = null;

        OrderBillEntity orderBillEntity = new OrderBillEntity();
        orderBillEntity.setOrderBillVersionNo(1);
        orderBillEntity.setBillStatus(HTypeBillStatus.BILL_CLAIM);
        orderBillEntity.setBillPrice(BigDecimal.TEN);
        orderBillEntity.setSettlementMethodSeq(1011);
        orderBillEntity.setSettlementCommission(BigDecimal.TEN);
        orderBillEntity.setEmergencyFlag(HTypeEmergencyFlag.OFF);
        orderBillEntity.setGmoReleaseFlag(HTypeGmoReleaseFlag.NORMAL);

        OrderDeliveryDto orderDeliveryDto = new OrderDeliveryDto();
        orderDeliveryDto.setOrderDeliveryEntity(orderDeliveryEntity);
        orderDeliveryDto.setDeliveryMethodEntity(deliveryMethodEntity);
        orderDeliveryDto.setOrderGoodsEntityList(listOrderGoodsEntity);
        receiveOrderDto.setOrderDeliveryDto(orderDeliveryDto);
        receiveOrderDto.setOrderBillEntity(orderBillEntity);

        // モック設定
        doNothing().when(calculatePriceUtility)
                   .setOrderTaxPrice(any(ReceiveOrderDto.class), any(OrderSettlementEntity.class));
        doReturn(BigDecimal.TEN).when(calculatePriceUtility)
                                .getTaxIncludedBeforeDiscountOrderPrice(any(ReceiveOrderDto.class));
        // 試験実行
        List<CheckMessageDto> actual = billPriceCalculateLogic.execute(receiveOrderDto, carriageCalcFlag);

        // 戻り値及び呼び出し検証
        verify(calculatePriceUtility, times(2)).setOrderTaxPrice(
                        any(ReceiveOrderDto.class), any(OrderSettlementEntity.class));
        verify(calculatePriceUtility, times(1)).getTaxIncludedBeforeDiscountOrderPrice(any(ReceiveOrderDto.class));
        assertThat(actual).isEqualTo(result);
    }
}
