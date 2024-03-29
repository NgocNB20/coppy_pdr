package jp.co.hankyuhanshin.itec.hitmall.dao.shop.news;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
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
public class NewsDaoTest {

    @Autowired
    NewsDao newsDao;

    @Test
    @Order(1)
    public void insert() {
        NewsEntity entity = makeNewsEntity();
        int result = newsDao.insert(entity);
        Assertions.assertNotNull(newsDao.getEntityByShopSeq(99, 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = newsDao.delete(newsDao.getEntityByShopSeq(99, 99));
        Assertions.assertNull(newsDao.getEntityByShopSeq(99, 99));
    }

    private NewsEntity makeNewsEntity() {
        NewsEntity entity = new NewsEntity();
        entity.setNewsSeq(99);
        entity.setShopSeq(99);
        entity.setTitlePC("1");
        entity.setTitleSP("1");
        entity.setTitleMB("1");
        entity.setNewsBodyPC("1");
        entity.setNewsBodySP("1");
        entity.setNewsBodyMB("1");
        entity.setNewsUrlPC("1");
        entity.setNewsUrlSP("1");
        entity.setNewsUrlMB("1");
        entity.setNewsOpenStatusPC(HTypeOpenStatus.NO_OPEN);
        entity.setNewsOpenStatusMB(HTypeOpenStatus.NO_OPEN);
        entity.setNewsOpenStartTimePC(new Timestamp(new java.util.Date().getTime()));
        entity.setNewsOpenEndTimePC(new Timestamp(new java.util.Date().getTime()));
        entity.setNewsOpenStartTimeMB(new Timestamp(new java.util.Date().getTime()));
        entity.setNewsOpenEndTimeMB(new Timestamp(new java.util.Date().getTime()));
        entity.setNewsTime(new Timestamp(new java.util.Date().getTime()));
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        entity.setNewsNotePC("1");
        entity.setNewsNoteSP("1");
        entity.setNewsNoteMB("1");
        return entity;
    }
}
