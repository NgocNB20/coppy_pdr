package jp.co.hankyuhanshin.itec.hitmall.service.shop.news;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.util.Date;

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
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.news.NewsRegistLogic;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewsRegistServiceTest {

    @Autowired
    NewsRegistService service;

    @MockBean
    private NewsRegistLogic newsRegistLogic;

    @Test
    @Order(1)
    public void execute1Test() {
        NewsEntity entity = new NewsEntity();
        entity.setNewsTime(new Timestamp(new Date().getTime()));
        entity.setNewsOpenStatusPC(HTypeOpenStatus.NO_OPEN);
        entity.setNewsOpenStatusMB(HTypeOpenStatus.NO_OPEN);
        entity.setShopSeq(1);
        int result = 1;

        doReturn(result).when(newsRegistLogic).execute((NewsEntity) any(Object.class));

        int actual = service.execute(entity);

        verify(newsRegistLogic, times(1)).execute((NewsEntity) any(Object.class));
        assertThat(actual).isEqualTo(result);
    }
}
