package jp.co.hankyuhanshin.itec.hitmall.service.order;

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
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;

/**
 * Logic/Service移行： 出荷リスト登録サービス
 * 作成日：2021/03/24
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShipmentListRegistServiceTest {

    @Autowired
    ShipmentListRegistService service;

    @MockBean
    private ShipmentCompleteMailBatchRegistService mailBatchRegistService;

    @SuppressWarnings("unchecked")
    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        List<ShipmentRegistDto> shipmentRegistDtoList = new ArrayList<>();
        List<CheckMessageDto> result = new ArrayList<>();

        // モック設定
        doReturn(1).when(mailBatchRegistService).execute(any(List.class));

        // 試験実行
        List<CheckMessageDto> actual = service.execute(shipmentRegistDtoList);

        // 戻り値及び呼び出し検証
        verify(mailBatchRegistService, times(1)).execute(any(List.class));
        assertThat(actual).isEqualTo(result);
    }
}
