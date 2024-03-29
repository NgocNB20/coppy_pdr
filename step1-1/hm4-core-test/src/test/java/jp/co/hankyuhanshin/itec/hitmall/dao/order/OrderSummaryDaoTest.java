package jp.co.hankyuhanshin.itec.hitmall.dao.order;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCarrierType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailRequired;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderAgeType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePointActivateFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePointType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRepeatPurchaseType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSend;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeWaitingFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderSummaryDaoTest {

    @Autowired
    OrderSummaryDao orderSummaryDao;

    @Test
    @Order(1)
    public void insert() {
        OrderSummaryEntity entity = makeOrderSummaryEntity();
        int result = orderSummaryDao.insert(entity);
        Assertions.assertNotNull(orderSummaryDao.getEntity(99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = orderSummaryDao.delete(orderSummaryDao.getEntity(99));
        Assertions.assertNull(orderSummaryDao.getEntity(99));
    }

    private OrderSummaryEntity makeOrderSummaryEntity() {
        OrderSummaryEntity entity = new OrderSummaryEntity();
        entity.setUsePoint(new BigDecimal("1"));
        entity.setOrderSeq(99);
        entity.setOrderVersionNo(99);
        entity.setOrderCode("1");
        entity.setOrderType(HTypeOrderType.PERIODIC);
        entity.setOrderTime(new Timestamp(new java.util.Date().getTime()));
        entity.setSalesTime(new Timestamp(new java.util.Date().getTime()));
        entity.setCancelTime(new Timestamp(new java.util.Date().getTime()));
        entity.setSalesFlag(HTypeSalesFlag.ON);
        entity.setCancelFlag(HTypeCancelFlag.ON);
        entity.setWaitingFlag(HTypeWaitingFlag.ON);
        entity.setOrderStatus(HTypeOrderStatus.GOODS_PREPARING);
        entity.setBeforeDiscountOrderPrice(new BigDecimal("0"));
        entity.setOrderPrice(new BigDecimal("0"));
        entity.setReceiptPriceTotal(new BigDecimal("0"));
        entity.setOrderSiteType(HTypeSiteType.FRONT_SP);
        entity.setOrderDeviceType(HTypeDeviceType.SP);
        entity.setCarrierType(HTypeCarrierType.DOCOMO);
        entity.setPeriodicOrderSeq(99);
        entity.setSettlementMethodSeq(99);
        entity.setTaxSeq(99);
        entity.setMemberInfoSeq(99);
        entity.setMemberRank(HTypeMemberRank.BRONZE);
        entity.setPrefectureType(HTypePrefectureType.HOKKAIDO);
        entity.setOrderSex(HTypeOrderSex.MALE);
        entity.setOrderAgeType(HTypeOrderAgeType.AGE_10TO19);
        entity.setRepeatPurchaseType(HTypeRepeatPurchaseType.MEMBER_FIRST);
        entity.setSettlementMailRequired(HTypeMailRequired.REQUIRED);
        entity.setReminderSentFlag(HTypeSend.SENT);
        entity.setExpiredSentFlag(HTypeSend.SENT);
        entity.setPointActivateFlag(HTypePointActivateFlag.ON);
        entity.setUserAgent("1");
        entity.setFreeAreaKey("1");
        entity.setOrderPointAddBasePrice(new BigDecimal("0"));
        entity.setOrderPointAddRate(new BigDecimal("0"));
        entity.setShopSeq(99);
        entity.setVersionNo(99);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        entity.setOrderGoodsVersionNo(99);
        entity.setPointSeq(99);
        entity.setPointVersionNo(99);
        entity.setCouponDiscountPrice(new BigDecimal("0"));
        entity.setPointDiscountPrice(new BigDecimal("0"));
        entity.setPointType(HTypePointType.ACTIVATE);
        entity.setPoint(new BigDecimal("0"));
        entity.setTotalAcquisitionPoint(new BigDecimal("0"));
        entity.setSettlementMethodName("1");
        entity.setSettlementMethodDisplayNamePC("1");
        entity.setSettlementMethodDisplayNameMB("1");
        entity.setReceiverTimeZone("1");
        entity.setOrderName("1");
        entity.setOrderKana("1");
        entity.setOrderTel("1");
        entity.setOrderContactTel("1");
        entity.setOrderMail("1");
        entity.setReceiverName("1");
        entity.setReceiverKana("1");
        entity.setReceiverTel("1");
        entity.setOrderConsecutiveNo(99);
        entity.setDeliveryCode("1");
        entity.setShipmentStatus("1");
        entity.setDeliveryNote("1");
        entity.setReceiptTime(new Timestamp(new java.util.Date().getTime()));
        entity.setEmergencyFlag(HTypeEmergencyFlag.ON);
        entity.setPaymentStatus("1");
        entity.setDeliveryMethodName("1");
        entity.setOrderStatusForSearchResult("1");
        return entity;
    }
}
