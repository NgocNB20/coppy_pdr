package jp.co.hankyuhanshin.itec.hitmall.dao.order.settlement;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCarrierType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderAgeType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRepeatPurchaseType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTotalingType;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
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
public class OrderSettlementDaoTest {

    @Autowired
    OrderSettlementDao orderSettlementDao;

    @Test
    @Order(1)
    public void insert() {
        OrderSettlementEntity entity = makeOrderSettlementEntity();
        int result = orderSettlementDao.insert(entity);
        Assertions.assertNotNull(orderSettlementDao.getEntity(99, 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = orderSettlementDao.delete(orderSettlementDao.getEntity(99, 99));
        Assertions.assertNull(orderSettlementDao.getEntity(99, 99));
    }

    private OrderSettlementEntity makeOrderSettlementEntity() {
        OrderSettlementEntity entity = new OrderSettlementEntity();
        entity.setUsePoint(new BigDecimal("1"));
        entity.setPreUsePoint(new BigDecimal("1"));
        entity.setOrderSeq(99);
        entity.setOrderSettlementVersionNo(99);
        entity.setOrderCode("1");
        entity.setProcessTime(new Timestamp(new java.util.Date().getTime()));
        entity.setProcessYmd("1");
        entity.setProcessYm("1");
        entity.setOrderDate(new Timestamp(new java.util.Date().getTime()));
        entity.setSalesTime(new Timestamp(new java.util.Date().getTime()));
        entity.setOrderSiteType(HTypeSiteType.FRONT_PC);
        entity.setOrderDeviceType(HTypeDeviceType.PC);
        entity.setCarrierType(HTypeCarrierType.DOCOMO);
        entity.setSettlementMethodSeq(99);
        entity.setSettlementMethodType(HTypeSettlementMethodType.AMAZON_PAYMENT);
        entity.setCreditCompanyCode("1");
        entity.setCreditCompany("1");
        entity.setTotalingType(HTypeTotalingType.SALES);
        entity.setSalesFlag(HTypeSalesFlag.ON);
        entity.setBillType(HTypeBillType.POST_CLAIM);
        entity.setRepeatPurchaseType(HTypeRepeatPurchaseType.MEMBER_FIRST);
        entity.setPrefectureType(HTypePrefectureType.YAMAGATA);
        entity.setOrderSex(HTypeOrderSex.MALE);
        entity.setOrderAgeType(HTypeOrderAgeType.AGE_0TO9);
        entity.setBeforeDiscountOrderPrice(new BigDecimal("0"));
        entity.setOrderPrice(new BigDecimal("0"));
        entity.setTaxPrice(new BigDecimal("0"));
        entity.setStandardTaxPrice(new BigDecimal("0"));
        entity.setReducedTaxPrice(new BigDecimal("0"));
        entity.setStandardTaxTargetPrice(new BigDecimal("0"));
        entity.setReducedTaxTargetPrice(new BigDecimal("0"));
        entity.setGoodsPriceTotal(new BigDecimal("0"));
        entity.setGoodsCostTotal(new BigDecimal("0"));
        entity.setSettlementCommission(new BigDecimal("0"));
        entity.setCarriage(new BigDecimal("0"));
        entity.setOthersPriceTotal(new BigDecimal("0"));
        entity.setCouponDiscountPrice(new BigDecimal("0"));
        entity.setPreBeforeDiscountOrderPrice(new BigDecimal("0"));
        entity.setPreOrderPrice(new BigDecimal("0"));
        entity.setPreTaxPrice(new BigDecimal("0"));
        entity.setPreGoodsPriceTotal(new BigDecimal("0"));
        entity.setPreGoodsCostTotal(new BigDecimal("0"));
        entity.setPreSettlementCommission(new BigDecimal("0"));
        entity.setPreCarriage(new BigDecimal("0"));
        entity.setPreOthersPriceTotal(new BigDecimal("0"));
        entity.setPreCouponDiscountPrice(new BigDecimal("0"));
        entity.setShopSeq(99);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        entity.setPointDiscountPrice(new BigDecimal("0"));
        entity.setPrePointDiscountPrice(new BigDecimal("0"));
        return entity;
    }
}
