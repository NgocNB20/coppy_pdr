package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.news.NewsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.NewsSearchForBackDaoConditionDto;
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
public class NewsListGetLogicImplTest {

    @Autowired
    NewsListGetLogic logic;

    @MockBean
    private NewsDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        List<NewsEntity> result = new ArrayList<>();
        NewsSearchForBackDaoConditionDto newsSearchForBackDaoConditionDto = new NewsSearchForBackDaoConditionDto();

        // モック設定
        doReturn(result).when(dao)
                        .getSearchNewsForBackList((NewsSearchForBackDaoConditionDto) any(Object.class),
                                                  any(SelectOptions.class)
                                                 );

        // 試験実行
        List<NewsEntity> actual = logic.execute(newsSearchForBackDaoConditionDto);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getSearchNewsForBackList(
                        (NewsSearchForBackDaoConditionDto) any(Object.class), any(SelectOptions.class));
        assertThat(actual).isEqualTo(result);
    }
}
