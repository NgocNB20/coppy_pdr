package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleAreaEntity;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryImpossibleAreaGetLogicTest {

    @Autowired
    DeliveryImpossibleAreaGetLogic deliveryImpossibleAreaGetLogic;

    @MockBean
    DeliveryImpossibleAreaDao deliveryImpossibleAreaDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        DeliveryImpossibleAreaEntity result = new DeliveryImpossibleAreaEntity();
        result.setDeliveryMethodSeq(1);

        // モック設定
        doReturn(result).when(deliveryImpossibleAreaDao).getEntity(any(Integer.class), any(String.class));

        // 試験実行
        DeliveryImpossibleAreaEntity actual = deliveryImpossibleAreaGetLogic.execute(1, "5550005");

        // 戻り値及び呼び出し検証
        verify(deliveryImpossibleAreaDao, times(1)).getEntity(any(Integer.class), any(String.class));
        Assertions.assertEquals(result, actual);
    }
}
