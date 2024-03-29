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
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryListGetSearchForUpdateLogic;

/**
 * Logic/Service移行：検索条件入金登録サービス
 * 作成日：2021/03/24
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderReceiptOfMoneySearchRegistServiceTest {

    @Autowired
    OrderReceiptOfMoneySearchRegistService service;

    @MockBean
    private OrderSummaryListGetSearchForUpdateLogic logic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        OrderSearchConditionDto conditionDto = new OrderSearchConditionDto();
        conditionDto.setShopSeq(1);
        List<OrderSummaryEntity> orderSummaryEntityList = new ArrayList<>();
        CheckMessageDto checkMessageDto = new CheckMessageDto();
        checkMessageDto.setMessageId("SOO002103");
        checkMessageDto.setMessage("");
        checkMessageDto.setError(false);
        List<CheckMessageDto> result = new ArrayList<>();
        result.add(checkMessageDto);

        // モック設定
        doReturn(orderSummaryEntityList).when(logic).execute(any(OrderSearchConditionDto.class));

        // 試験実行
        List<CheckMessageDto> actual = service.execute(conditionDto);

        // 戻り値及び呼び出し検証
        verify(logic, times(1)).execute(any(OrderSearchConditionDto.class));
        assertThat(actual).isInstanceOf(List.class);
    }
}
