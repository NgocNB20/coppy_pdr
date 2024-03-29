package jp.co.hankyuhanshin.itec.hitmall.service.goods.goods;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsCsvDownloadLogic;

/**
 * Logic/Service移行：商品検索CSVダウンロードサービス
 * 作成日：2021/03/23
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsCsvDownloadServiceTest {

    @Autowired
    GoodsCsvDownloadService service;

    @MockBean
    private GoodsCsvDownloadLogic logic;

    //    @Test
    //    @Order(1)
    //    public void executeTest() {
    //
    //        // 想定値設定
    //        List<Integer> goodsSeqList = Arrays.asList(1, 2);
    //        int result = 1;
    //
    //        // モック設定
    //        doReturn(result).when(logic).execute(any(List.class));
    //
    //        // 試験実行
    //        int actual = service.execute(goodsSeqList);
    //
    //        // 戻り値及び呼び出し検証
    //        verify(logic, times(1)).execute(any(List.class));
    //        assertThat(actual).isEqualTo(result);
    //    }
}
