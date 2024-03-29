package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsTaxType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
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

import java.math.BigDecimal;
import java.sql.Timestamp;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsGroupDaoTest {

    @Autowired
    GoodsGroupDao goodsGroupDao;

    @Test
    @Order(1)
    public void insert() {
        GoodsGroupEntity entity = makeGoodsGroupEntity();
        int result = goodsGroupDao.insert(entity);
        Assertions.assertNotNull(goodsGroupDao.getEntity(999));
    }

    @Test
    @Order(2)
    public void getGoodsGroupByCode() {
        Assertions.assertNotNull(goodsGroupDao.getGoodsGroupByCode(999, "999", null, HTypeSiteType.FRONT_PC,
                                                                   HTypeOpenDeleteStatus.NO_OPEN
                                                                  ));
    }

    @Test
    @Order(3)
    public void delete() {
        int result = goodsGroupDao.delete(goodsGroupDao.getEntity(999));
        Assertions.assertNull(goodsGroupDao.getEntity(999));
    }

    private GoodsGroupEntity makeGoodsGroupEntity() {
        GoodsGroupEntity entity = new GoodsGroupEntity();
        entity.setGoodsGroupSeq(999);
        entity.setGoodsGroupCode("999");
        entity.setWhatsnewDate(new Timestamp(System.currentTimeMillis()));
        entity.setGoodsOpenStatusPC(HTypeOpenDeleteStatus.OPEN);
        entity.setGoodsTaxType(HTypeGoodsTaxType.IN_TAX);
        entity.setTaxRate(new BigDecimal(2));
        entity.setAlcoholFlag(HTypeAlcoholFlag.ALCOHOL);
        entity.setSnsLinkFlag(HTypeSnsLinkFlag.OFF);
        entity.setShopSeq(999);
        entity.setVersionNo(1);
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return entity;
    }
}
