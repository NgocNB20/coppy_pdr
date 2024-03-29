package jp.co.hankyuhanshin.itec.hitmall.dao.shop;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAutoApprovalFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
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
public class ShopDaoTest {

    @Autowired
    ShopDao shopDao;

    @Test
    @Order(1)
    public void insert() {
        Assertions.assertNotNull(shopDao.getEntity(1001));
    }

    private ShopEntity makeShopEntity() {
        ShopEntity entity = new ShopEntity();
        entity.setShopId("1");
        entity.setShopNamePC("1");
        entity.setShopNameMB("1");
        entity.setUrlPC("1");
        entity.setUrlMB("1");
        entity.setShopOpenStatusPC(HTypeOpenStatus.OPEN);
        entity.setShopOpenStatusMB(HTypeOpenStatus.OPEN);
        entity.setShopOpenStartTimePC(new Timestamp(new java.util.Date().getTime()));
        entity.setShopOpenEndTimePC(new Timestamp(new java.util.Date().getTime()));
        entity.setShopOpenStartTimeMB(new Timestamp(new java.util.Date().getTime()));
        entity.setShopOpenEndTimeMB(new Timestamp(new java.util.Date().getTime()));
        entity.setMetaDescription("1");
        entity.setMetaKeyword("1");
        entity.setPointAddBasePriceDefault(new BigDecimal("0"));
        entity.setPointAddRateDefault(new BigDecimal("0"));
        entity.setPointStartTime1(new Timestamp(new java.util.Date().getTime()));
        entity.setPointEndTime1(new Timestamp(new java.util.Date().getTime()));
        entity.setPointAddBasePrice1(new BigDecimal("0"));
        entity.setPointAddRate1(new BigDecimal("0"));
        entity.setPointStartTime2(new Timestamp(new java.util.Date().getTime()));
        entity.setPointEndTime2(new Timestamp(new java.util.Date().getTime()));
        entity.setPointAddBasePrice2(new BigDecimal("0"));
        entity.setPointAddRate2(new BigDecimal("0"));
        entity.setMemberRankSetDate(new Timestamp(new java.util.Date().getTime()));
        entity.setMemberRankPurchaseStartTime(new Timestamp(new java.util.Date().getTime()));
        entity.setVersionNo(0);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        entity.setAutoApprovalFlag(HTypeAutoApprovalFlag.ON);
        entity.setReviewDefaultPoint(new BigDecimal("0"));
        return entity;
    }
}
