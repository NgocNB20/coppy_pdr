package jp.co.hankyuhanshin.itec.hitmall.dao.order.member;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.member.SimultaneousOrderExclusionEntity;
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
public class SimultaneousOrderExclusionDaoTest {

    @Autowired
    SimultaneousOrderExclusionDao simultaneousOrderExclusionDao;

    @Test
    @Order(1)
    public void insert() {
        SimultaneousOrderExclusionEntity entity = makeSimultaneousOrderExclusionEntity();
        int result = simultaneousOrderExclusionDao.insert(entity);
        Assertions.assertNotNull(simultaneousOrderExclusionDao.getEntity(99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = simultaneousOrderExclusionDao.delete(simultaneousOrderExclusionDao.getEntity(99));
        Assertions.assertNull(simultaneousOrderExclusionDao.getEntity(99));
    }

    private SimultaneousOrderExclusionEntity makeSimultaneousOrderExclusionEntity() {
        SimultaneousOrderExclusionEntity entity = new SimultaneousOrderExclusionEntity();
        entity.setMemberInfoSeq(99);
        entity.setVersionNo(99);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
