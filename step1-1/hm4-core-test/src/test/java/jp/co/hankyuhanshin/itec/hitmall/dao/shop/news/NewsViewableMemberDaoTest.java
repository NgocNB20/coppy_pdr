package jp.co.hankyuhanshin.itec.hitmall.dao.shop.news;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsViewableMemberEntity;
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
public class NewsViewableMemberDaoTest {

    @Autowired
    NewsViewableMemberDao newsViewableMemberDao;

    @Test
    @Order(1)
    public void insert() {
        NewsViewableMemberEntity entity = makeNewsViewableMemberEntity();
        int result = newsViewableMemberDao.insert(entity);
        Assertions.assertNotNull(newsViewableMemberDao.getEntity(99, 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = newsViewableMemberDao.delete(newsViewableMemberDao.getEntity(99, 99));
        Assertions.assertNull(newsViewableMemberDao.getEntity(99, 99));
    }

    private NewsViewableMemberEntity makeNewsViewableMemberEntity() {
        NewsViewableMemberEntity entity = new NewsViewableMemberEntity();
        entity.setNewsSeq(99);
        entity.setMemberInfoSeq(99);
        entity.setShopSeq(99);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
