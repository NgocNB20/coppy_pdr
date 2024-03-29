package jp.co.hankyuhanshin.itec.hitmall.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.OrderTransformHelper;
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
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.order.impl.ReceiveOrderGetServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Logic/Service移行：注文受付完了メール送信サービス
 * 作成日：2021/03/24
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderCompleteMailSendServiceTest {

    @Autowired
    OrderCompleteMailSendService service;

    @MockBean
    private ReceiveOrderGetServiceImpl logic;

    @MockBean
    private OrderTransformHelper tranform;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer orderSeq = 1;
        Integer orderVersionNo = 1;
        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        orderPersonEntity.setOrderMail("mail");
        ReceiveOrderDto receiveOrderDto = new ReceiveOrderDto();
        receiveOrderDto.setOrderPersonEntity(orderPersonEntity);

        boolean result = true;

        // モック設定
        doReturn(receiveOrderDto).when(logic).execute(any(Integer.class), any(Integer.class));

        Map<String, String> mailContentsMap = new HashMap<>();
        doReturn(mailContentsMap).when(tranform).toValueMap(any());
        // 試験実行
        boolean actual = service.execute(orderSeq, orderVersionNo);

        // 戻り値及び呼び出し検証
        verify(logic, times(1)).execute(any(Integer.class), any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }

    @Test
    @Order(2)
    public void executeListTest() {

        // 想定値設定
        Integer orderSeq = 1;
        Integer orderVersionNo = 1;
        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        orderPersonEntity.setOrderMail("mail");
        ReceiveOrderDto receiveOrderDto = new ReceiveOrderDto();
        receiveOrderDto.setOrderPersonEntity(orderPersonEntity);
        List<ReceiveOrderDto> receiveOrderDtoLst = new ArrayList<>();
        receiveOrderDtoLst.add(receiveOrderDto);
        boolean result = true;
        Map<String, String> mailContentsMap = new HashMap<>();
        doReturn(mailContentsMap).when(tranform).toValueMap(any());

        // 試験実行
        boolean actual = service.execute(receiveOrderDtoLst);

        assertThat(actual).isEqualTo(result);
    }
}
