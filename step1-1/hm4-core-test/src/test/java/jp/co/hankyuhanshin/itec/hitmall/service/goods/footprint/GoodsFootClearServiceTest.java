package jp.co.hankyuhanshin.itec.hitmall.service.goods.footprint;

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
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint.GoodsFootPrintClearLogic;

/**
 * Service移行：商品情報を削除します
 * 作成日：2021/03/22
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsFootClearServiceTest {

    @Autowired
    GoodsFootClearService service;

    @MockBean
    private GoodsFootPrintClearLogic logic;

    @Test
    @Order(1)
    public void execute1Test() {

        // モック設定
        doReturn(1).when(logic).execute(any(Integer.class), any(String.class));

        // 試験実行
        service.execute();

        // 戻り値及び呼び出し検証
        verify(logic, times(1)).execute(any(Integer.class), any(String.class));
    }
}
