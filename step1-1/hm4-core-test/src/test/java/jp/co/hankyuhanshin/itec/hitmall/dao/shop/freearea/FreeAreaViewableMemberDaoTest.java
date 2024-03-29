package jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaViewableMemberEntity;
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
public class FreeAreaViewableMemberDaoTest {

    @Autowired
    FreeAreaViewableMemberDao freeAreaViewableMemberDao;

    @Test
    @Order(1)
    public void insert() {
        FreeAreaViewableMemberEntity entity = makeFreeAreaViewableMemberEntity();
        int result = freeAreaViewableMemberDao.insert(entity);
        Assertions.assertNotNull(freeAreaViewableMemberDao.getEntity(99, 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = freeAreaViewableMemberDao.delete(freeAreaViewableMemberDao.getEntity(99, 99));
        Assertions.assertNull(freeAreaViewableMemberDao.getEntity(99, 99));
    }

    private FreeAreaViewableMemberEntity makeFreeAreaViewableMemberEntity() {
        FreeAreaViewableMemberEntity entity = new FreeAreaViewableMemberEntity();
        entity.setFreeAreaSeq(99);
        entity.setMemberInfoSeq(99);
        entity.setShopSeq(99);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
