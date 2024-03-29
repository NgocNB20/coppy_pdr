package jp.co.hankyuhanshin.itec.hitmall.service.goods.goods;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;

/**
 * Logic/Service移行：商品CSVアップロードコードチェック
 * 作成日：2021/03/23
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsCsvCodeCheckServiceTest {

    @Autowired
    GoodsCsvCodeCheckService service;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        String code = "1";
        boolean result = true;

        // 試験実行
        service.init();
        boolean actual = service.canSaveGoodsGroupCode(code);

        // 戻り値及び呼び出し検証
        assertThat(actual).isEqualTo(result);
    }
}
