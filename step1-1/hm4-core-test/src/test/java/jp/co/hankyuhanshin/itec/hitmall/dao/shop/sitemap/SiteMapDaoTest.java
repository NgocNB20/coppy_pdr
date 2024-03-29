package jp.co.hankyuhanshin.itec.hitmall.dao.shop.sitemap;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOutputFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUrlType;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.sitemap.SiteMapEntity;
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
public class SiteMapDaoTest {

    @Autowired
    SiteMapDao siteMapDao;

    @Test
    @Order(1)
    public void insert() {
        SiteMapEntity entity = makeSiteMapEntity();
        int result = siteMapDao.insert(entity);
        Assertions.assertNotNull(siteMapDao.getEntity(99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = siteMapDao.delete(siteMapDao.getEntity(99));
        Assertions.assertNull(siteMapDao.getEntity(99));
    }

    private SiteMapEntity makeSiteMapEntity() {
        SiteMapEntity entity = new SiteMapEntity();
        entity.setSiteMapSeq(99);
        entity.setSiteMapName("1");
        entity.setSiteType(HTypeSiteType.FRONT_PC);
        entity.setOutputFileName("1");
        entity.setOutputFlag(HTypeOutputFlag.ON);
        entity.setUrlType(HTypeUrlType.GOODS_DETAIL);
        entity.setLoc("1");
        entity.setChangefreq("1");
        entity.setPriority(new BigDecimal("0"));
        entity.setNote("1");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
