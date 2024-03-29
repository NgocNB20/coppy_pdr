package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderAgeType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSend;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePointActivateFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;

import java.math.BigDecimal;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeWaitingFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRepeatPurchaseType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailRequired;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCarrierType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePointType;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory.OrderHistoryListDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSummarySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryListGetLogic;
import org.junit.jupiter.api.Assertions;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderHistoryListGetServiceTest {

    @Autowired
    OrderHistoryListGetService orderHistoryListGetService;

    @MockBean
    OrderSummaryListGetLogic orderSummaryListGetLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        List<OrderSummaryEntity> result = new ArrayList<>();
        OrderSummaryEntity entity = new OrderSummaryEntity();
        entity.setUsePoint(new BigDecimal("0"));
        entity.setOrderSeq(1);
        entity.setOrderVersionNo(0);
        entity.setOrderCode("");
        entity.setOrderType(HTypeOrderType.NORMAL);
        entity.setOrderTime(new Timestamp(new java.util.Date().getTime()));
        entity.setSalesTime(new Timestamp(new java.util.Date().getTime()));
        entity.setCancelTime(new Timestamp(new java.util.Date().getTime()));
        entity.setSalesFlag(HTypeSalesFlag.OFF);
        entity.setCancelFlag(HTypeCancelFlag.OFF);
        entity.setWaitingFlag(HTypeWaitingFlag.OFF);
        entity.setOrderStatus(HTypeOrderStatus.PAYMENT_CONFIRMING);
        entity.setBeforeDiscountOrderPrice(new BigDecimal("0"));
        entity.setOrderPrice(new BigDecimal("0"));
        entity.setReceiptPriceTotal(new BigDecimal("0"));
        entity.setOrderSiteType(HTypeSiteType.FRONT_PC);
        entity.setOrderDeviceType(HTypeDeviceType.PC);
        entity.setCarrierType(HTypeCarrierType.PC);
        entity.setPeriodicOrderSeq(0);
        entity.setSettlementMethodSeq(0);
        entity.setTaxSeq(0);
        entity.setMemberInfoSeq(0);
        entity.setMemberRank(HTypeMemberRank.GUEST);
        entity.setPrefectureType(HTypePrefectureType.HOKKAIDO);
        entity.setOrderSex(HTypeOrderSex.UNKNOWN);
        entity.setOrderAgeType(HTypeOrderAgeType.AGE_0TO9);
        entity.setRepeatPurchaseType(HTypeRepeatPurchaseType.GUEST);
        entity.setSettlementMailRequired(HTypeMailRequired.NO_NEED);
        entity.setReminderSentFlag(HTypeSend.UNSENT);
        entity.setExpiredSentFlag(HTypeSend.UNSENT);
        entity.setPointActivateFlag(HTypePointActivateFlag.OFF);
        entity.setUserAgent("");
        entity.setFreeAreaKey("");
        entity.setOrderPointAddBasePrice(new BigDecimal("0"));
        entity.setOrderPointAddRate(new BigDecimal("0"));
        entity.setShopSeq(0);
        entity.setVersionNo(0);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        entity.setOrderGoodsVersionNo(0);
        entity.setPointSeq(0);
        entity.setPointVersionNo(0);
        entity.setCouponDiscountPrice(new BigDecimal("0"));
        entity.setPointDiscountPrice(new BigDecimal("0"));
        entity.setPointType(HTypePointType.TEMPORARY);
        entity.setPoint(new BigDecimal("0"));
        entity.setTotalAcquisitionPoint(new BigDecimal("0"));
        entity.setSettlementMethodName("test");
        entity.setSettlementMethodDisplayNamePC("");
        entity.setSettlementMethodDisplayNameMB("");
        entity.setReceiverTimeZone("");
        entity.setOrderName("");
        entity.setOrderKana("");
        entity.setOrderTel("");
        entity.setOrderContactTel("");
        entity.setOrderMail("");
        entity.setReceiverName("");
        entity.setReceiverKana("");
        entity.setReceiverTel("");
        entity.setOrderConsecutiveNo(0);
        entity.setDeliveryCode("");
        entity.setShipmentStatus("");
        entity.setDeliveryNote("");
        entity.setReceiptTime(new Timestamp(new java.util.Date().getTime()));
        entity.setEmergencyFlag(HTypeEmergencyFlag.OFF);
        entity.setPaymentStatus("");
        entity.setDeliveryMethodName("");
        entity.setOrderStatusForSearchResult("");

        result.add(entity);

        // モック設定
        doReturn(result).when(orderSummaryListGetLogic).execute(any(OrderSummarySearchForDaoConditionDto.class));

        // 試験実行
        OrderSummarySearchForDaoConditionDto conditionDto = new OrderSummarySearchForDaoConditionDto();
        conditionDto.setMemberInfoSeq(123);
        List<OrderHistoryListDto> actual = orderHistoryListGetService.execute(conditionDto);

        // 戻り値及び呼び出し検証
        verify(orderSummaryListGetLogic, times(1)).execute(any(OrderSummarySearchForDaoConditionDto.class));
        Assertions.assertEquals(
                        result.get(0).getSettlementMethodName(),
                        actual.get(0).getSettlementMethodEntity().getSettlementMethodName()
                               );
    }
}
