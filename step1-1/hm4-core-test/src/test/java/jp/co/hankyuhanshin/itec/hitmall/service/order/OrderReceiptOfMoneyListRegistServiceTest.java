package jp.co.hankyuhanshin.itec.hitmall.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
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
import jp.co.hankyuhanshin.itec.hitmall.dto.order.PaymentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryListGetForUpdateLogic;

/**
 * Logic/Service移行：入金リスト登録サービス
 * 作成日：2021/03/24
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderReceiptOfMoneyListRegistServiceTest {

    @Autowired
    OrderReceiptOfMoneyListRegistService service;

    @MockBean
    private OrderSummaryListGetForUpdateLogic orderSummaryListGetForUpdateLogic;

    @SuppressWarnings("unchecked")
    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        PaymentRegistDto paymentRegistDto = new PaymentRegistDto();
        List<PaymentRegistDto> paymentRegistDtoList = new ArrayList<>();
        paymentRegistDtoList.add(paymentRegistDto);
        List<OrderSummaryEntity> orderSummaryEntityList = new ArrayList<>();

        // モック設定
        doReturn(orderSummaryEntityList).when(orderSummaryListGetForUpdateLogic)
                                        .execute(any(List.class), any(Integer.class));

        try {

            // 試験実行
            service.execute(paymentRegistDtoList);
            fail("例外がスローされなかった。");
        } catch (Exception e) {

            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).isNull();
        }

        // 戻り値及び呼び出し検証
        verify(orderSummaryListGetForUpdateLogic, times(1)).execute(any(List.class), any(Integer.class));
    }
}
