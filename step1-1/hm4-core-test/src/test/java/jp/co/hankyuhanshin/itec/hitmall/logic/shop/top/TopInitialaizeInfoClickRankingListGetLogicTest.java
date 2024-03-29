package jp.co.hankyuhanshin.itec.hitmall.logic.shop.top;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.top.GoodsRankingDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.top.GoodsRankingEntity;

/**
 * Logic/Service移行：トップ画面初期表示用Logic
 * 作成日：2021/03/18
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TopInitialaizeInfoClickRankingListGetLogicTest {

    @Autowired
    TopInitialaizeInfoClickRankingListGetLogic logic;

    @MockBean
    private GoodsRankingDao dao;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer shopSeq = 1;

        List<GoodsRankingEntity> result = new ArrayList<>();

        // モック設定
        doReturn(result).when(dao).getClickRanking(any(Integer.class));

        // 試験実行
        List<GoodsRankingEntity> actual = logic.execute(shopSeq);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getClickRanking(any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }

}
