package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news;

import java.sql.Timestamp;
import java.util.Date;

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
import org.springframework.boot.test.mock.mockito.MockBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.news.NewsDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

/**
 * Logic/Service移行：会員に紐付く問い合わせの存在チェック
 * 作成日：2021/03/19
 *
 * @author Nguyen Hong Son (VTI VietNam Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewsUpdateLogicImplTest {

    @Autowired
    NewsUpdateLogic logic;

    @MockBean
    private NewsDao dao;

    @Test
    @Order(1)
    public void executeTest() {
        NewsEntity entity = new NewsEntity();
        entity.setNewsTime(new Timestamp(new Date().getTime()));
        entity.setNewsOpenStatusPC(HTypeOpenStatus.NO_OPEN);
        entity.setNewsOpenStatusMB(HTypeOpenStatus.NO_OPEN);
        entity.setNewsSeq(1);
        entity.setShopSeq(1);

        doReturn(1).when(dao).update(any(NewsEntity.class));

        // モック設定
        int actual = logic.execute(entity);
        Assertions.assertEquals(1, actual);
    }
}
