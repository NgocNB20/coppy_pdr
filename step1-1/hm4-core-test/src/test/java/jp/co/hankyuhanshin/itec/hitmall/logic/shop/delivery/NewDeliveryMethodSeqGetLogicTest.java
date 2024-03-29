package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryMethodDao;
import org.junit.jupiter.api.Assertions;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewDeliveryMethodSeqGetLogicTest {

    @Autowired
    NewDeliveryMethodSeqGetLogic newDeliveryMethodSeqGetLogic;

    @MockBean
    DeliveryMethodDao deliveryMethodDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        int result = 111;

        // モック設定
        doReturn(result).when(deliveryMethodDao).getDeliveryMethodSeqNextVal();

        // 試験実行
        Integer actual = newDeliveryMethodSeqGetLogic.execute();

        // 戻り値及び呼び出し検証
        verify(deliveryMethodDao, times(1)).getDeliveryMethodSeqNextVal();
        Assertions.assertEquals(result, actual);
    }
}
