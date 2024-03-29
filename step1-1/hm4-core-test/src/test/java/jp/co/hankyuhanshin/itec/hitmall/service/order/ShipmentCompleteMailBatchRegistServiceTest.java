package jp.co.hankyuhanshin.itec.hitmall.service.order;

import static org.assertj.core.api.Assertions.assertThat;

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

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;

/**
 * Logic/Service移行：出荷完了メール自動送信サービス(非同期)
 * 作成日：2021/03/24
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShipmentCompleteMailBatchRegistServiceTest {

    @Autowired
    ShipmentCompleteMailBatchRegistService service;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        List<ShipmentRegistDto> shipmentRegistDtoList = new ArrayList<>();
        int result = 0;

        // 試験実行
        int actual = service.execute(shipmentRegistDtoList);

        // 戻り値及び呼び出し検証
        assertThat(actual).isEqualTo(result);
    }
}
