package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.news.NewsDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;

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
public class NewsDeleteLogicImplTest {

    @Autowired
    NewsDeleteLogic logic;

    @MockBean
    private NewsDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        int result = 1;
        NewsEntity entity = new NewsEntity();
        entity.setNewsSeq(1);
        entity.setShopSeq(1);

        // モック設定
        doReturn(result).when(dao).delete((NewsEntity) any(Object.class));

        // 試験実行
        int actual = logic.execute(entity);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).delete((NewsEntity) any(Object.class));
        assertThat(actual).isEqualTo(result);
    }

}
