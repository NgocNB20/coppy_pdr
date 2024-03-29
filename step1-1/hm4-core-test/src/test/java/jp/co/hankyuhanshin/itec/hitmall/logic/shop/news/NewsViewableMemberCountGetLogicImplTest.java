package jp.co.hankyuhanshin.itec.hitmall.logic.shop.news;

import static org.assertj.core.api.Assertions.assertThat;
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
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.news.NewsViewableMemberDao;

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
public class NewsViewableMemberCountGetLogicImplTest {

    @Autowired
    NewsViewableMemberCountGetLogic logic;

    @MockBean
    private NewsViewableMemberDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        int result = 1;
        Integer newsSeq = 1;

        // モック設定
        doReturn(result).when(dao).getCountByNewsSeq(newsSeq);

        // 試験実行
        int actual = logic.execute(newsSeq);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getCountByNewsSeq(newsSeq);
        assertThat(actual).isEqualTo(result);
    }

}
