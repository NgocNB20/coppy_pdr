package jp.co.hankyuhanshin.itec.hitmall.dao.order.goods;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTotalingType;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
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
public class OrderGoodsDaoTest {

    @Autowired
    OrderGoodsDao orderGoodsDao;

    @Test
    @Order(1)
    public void insert() {
        OrderGoodsEntity entity = makeOrderGoodsEntity();
        int result = orderGoodsDao.insert(entity);
        Assertions.assertNotNull(orderGoodsDao.getEntity(99, 99, 99, 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = orderGoodsDao.delete(orderGoodsDao.getEntity(99, 99, 99, 99));
        Assertions.assertNull(orderGoodsDao.getEntity(99, 99, 99, 99));
    }

    private OrderGoodsEntity makeOrderGoodsEntity() {
        OrderGoodsEntity entity = new OrderGoodsEntity();
        entity.setOrderSeq(99);
        entity.setOrderGoodsVersionNo(99);
        entity.setOrderConsecutiveNo(99);
        entity.setGoodsSeq(99);
        entity.setOrderDisplay(99);
        entity.setTotalingType(HTypeTotalingType.SALES);
        entity.setSalesFlag(HTypeSalesFlag.ON);
        entity.setProcessTime(new Timestamp(new java.util.Date().getTime()));
        entity.setGoodsGroupCode("1");
        entity.setGoodsCode("1");
        entity.setJanCode("1");
        entity.setCatalogCode("1");
        entity.setGoodsTaxType(HTypeGoodsTaxType.OUT_TAX);
        entity.setTaxRate(new BigDecimal("1"));
        entity.setGoodsPrice(new BigDecimal("1"));
        entity.setPreGoodsCount(new BigDecimal("1"));
        entity.setGoodsCount(new BigDecimal("1"));
        entity.setUnitValue1("1");
        entity.setUnitValue2("1");
        entity.setIndividualDeliveryType(HTypeIndividualDeliveryType.ON);
        entity.setDeliveryType("1");
        entity.setShopSeq(99);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
